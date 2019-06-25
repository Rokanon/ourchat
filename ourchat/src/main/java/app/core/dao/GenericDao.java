package app.core.dao;

import app.core.dao.annotations.Column;
import app.core.dao.annotations.Table;
import app.core.dao.connection.ConnectionManager;
import app.core.dao.enums.ConnectionType;
import app.core.dao.enums.FieldType;
import app.core.dao.interfaces.Transformable;
import app.core.utils.Config;
import app.core.utils.ConfigKeys;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import app.core.utils.TimeMesure;
import java.lang.reflect.InvocationTargetException;
import ourchat.ourchat.base.Model;

/**
 * Not fully implemented yet
 *
 * @param <Dto> generic type representing underlying database table TODO: handle
 * multi-threading, performance improvements, {@code Logger} instead of
 * {@code System::out} and etc...
 */
public abstract class GenericDao<Dto extends Serializable> {

    private final Dto dto = newDto();

    protected abstract Dto newDto();

    private String tableName;
    private String primaryKey;
    private ConnectionType connectionType;
    private String commaSeparatedColumns;
    private final List<Field> fields = new ArrayList<>();
    private final List<String> columns = new ArrayList<>();
    private final Map<String, FieldType> columnMapping = new HashMap<>();
    private final Map<String, Integer> columnIndexes = new HashMap<>();
    private final Map<String, Field> columnField = new HashMap<>();

    private static final int BATCH_SIZE = Config.getInt(ConfigKeys.BATCH_SIZE);

    private static final Object LOCK = new Object();

    private static final Logger LOGGER = Logger.getLogger(GenericDao.class.getName());

    public GenericDao() {
        this.init();
    }

    /**
     * Populates {@code Dao} data structures needed for later use such as
     * {@code tableName, java.lang.reflect.Field, commaSeparatedColumns}
     * {@code columnMapping}(maps column name to its type),
     * {@code columnIndexes}(maps column name to its index),
     * {@code columnField}(maps column name to its
     * {@code java.lang.reflect.Field} type to minimize unnecessary reflection
     * calls
     */
    private void init() {
        this.configTableMetadata();
        this.configColumnMetadata();
    }

    private void configTableMetadata() {
        try {
            Annotation tableMetadata = dto.getClass().getAnnotation(Table.class);
            this.tableName = tableMetadata.annotationType().getDeclaredMethod("name").invoke(tableMetadata, (Object[]) null).toString();
            this.connectionType = (ConnectionType) tableMetadata.annotationType().getDeclaredMethod("connectionType").invoke(tableMetadata, (Object[]) null);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private void configColumnMetadata() {
        int[] index = {0};
        Class clazz = dto.getClass();
        boolean rollOn = true;
        while (rollOn) { // cuz of inheritance of classes (ie. extends Model)
            for (Field field : clazz.getDeclaredFields()) {
                Annotation columnMetadata = field.getAnnotation(Column.class);
                if (columnMetadata != null) {
                    field.setAccessible(Boolean.TRUE);
                    fields.add(field);
                    try {
                        String columnName = columnMetadata.annotationType().getDeclaredMethod("name").invoke(columnMetadata, (Object[]) null).toString();
                        FieldType fieldType = (FieldType) columnMetadata.annotationType().getDeclaredMethod("type").invoke(columnMetadata, (Object[]) null);

                        columnMapping.put(columnName, fieldType);
                        columnIndexes.put(columnName, ++index[0]);
                        columnField.put(columnName, field);
                        columns.add(columnName);
                        boolean isPk;

                        try {
                            isPk = (boolean) columnMetadata.annotationType().getDeclaredMethod("primaryKey").invoke(columnMetadata, (Object[]) null);
                        } catch (NoSuchMethodException ex) {
                            isPk = Boolean.FALSE;
                        }
                        if (isPk) {
                            primaryKey = columnName;
                        }
                    } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException e) {
                        LOGGER.log(Level.SEVERE, e.getMessage());
                    }
                }
            }

            if (clazz.equals(Model.class)) {
                rollOn = false;
            }
            clazz = clazz.getSuperclass();
        }
        commaSeparatedColumns = columns.stream().collect(Collectors.joining(","));
    }

    public Dto getById(long id) {
        try (Connection conn = getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st
                        .executeQuery("SELECT " + commaSeparatedColumns + " FROM " + tableName + " WHERE ID=" + id);) {
            if (rs.next()) {
                return fromResultSet(rs);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    /* Used only on producer connections! */
    public long insert(Dto dto) {
        String insert = "INSERT INTO " + tableName + "(" + commaSeparatedColumns + ") " + " VALUES " + buildWildCards();
        String generatedColumns[] = {"ID"};
        try (Connection conn = getConnection();
                PreparedStatement ps = conn.prepareStatement(insert, generatedColumns);) {
            synchronized (this) {
                toPreparedStatement(ps, dto);
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    conn.commit();
                    return generatedKeys.getLong(1);
                }
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return 0;
    }

    public long insert(List<Dto> dtos) {
        long size = 0;
        String query = "INSERT INTO " + tableName + "(" + commaSeparatedColumns + ") VALUES " + buildWildCards();
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            dtos.parallelStream().forEach(dto -> {
                toPreparedStatementBatch(ps, dto);
            });

            size += ps.executeBatch().length;
            conn.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return size;
    }

    /**
     *
     * @param <T>
     * @param transformableStream
     * @return number of inserted rows
     */
    public <T extends Transformable<Dto>> long insert(Stream<T> transformableStream) {
        long size = 0;
        TimeMesure tm = new TimeMesure("Insert from " + Thread.currentThread().getId());
        String query = "INSERT INTO " + tableName + "(" + commaSeparatedColumns + ") VALUES " + buildWildCards();
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query);) {
            transformableStream.parallel().map(Transformable::transform).forEach(tmpDto -> {
                toPreparedStatementBatch(ps, tmpDto);
            });
            size += ps.executeBatch().length;
            conn.commit();
            tm.result();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return size;
    }

    public Dto update(Dto dto) {
        String update = buildUpdateStatement(primaryKey + "=" + getPrimaryKeyValue(dto));
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(update)) {
            toPreparedStatement(ps, dto);
            ps.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            // TODO: handle exception
        }

        return dto;
    }

    private Object getPrimaryKeyValue(Dto dto) {
        Object pkVal = null;
        try {
            pkVal = columnField.get(primaryKey).get(dto);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return pkVal;
    }

    private String buildUpdateStatement(String where) {
        StringBuilder update = new StringBuilder();
        update.append("UPDATE ").append(tableName).append(" SET");
        update.append(columns.stream().map(c -> (c + "=?")).collect(Collectors.joining(",", " ", " ")));
        if (where != null) {
            update.append("WHERE ").append(where);
        }
        LOGGER.info("UPDATE: " + update);
        return update.toString();
    }

    public long update(List<Dto> dtos) {
        long size = 0;
        String query = "UPDATE " + tableName + "(" + commaSeparatedColumns + ") VALUES " + buildWildCards();
        try (Connection conn = getConnection(); PreparedStatement ps = conn.prepareStatement(query)) {
            dtos.parallelStream().forEach(dto -> {
                toPreparedStatementBatch(ps, dto);
            });

            size += ps.executeBatch().length;
            conn.commit();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return size;
    }

    public List<Dto> loadList(String where, String orderBy, int offset, int limit) {
        List<Dto> dtos = new ArrayList<>();
        try (Connection conn = getConnection();
                Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = st.executeQuery(makeSql(where, orderBy, offset, limit))) {
            while (rs.next()) {
                dtos.add(fromResultSet(rs));
            }
            return dtos;
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    public List<Dto> loadList(String where, String orderBy) {
        return this.loadList(where, orderBy, -1, -1);
    }

    public List<Dto> loadList(String where, int offset, int limit) {
        return this.loadList(where, null, offset, limit);
    }

    public List<Dto> loadList(String where) {
        return this.loadList(where, null, -1, -1);
    }

    // HELPER METHODS...
    private String makeSql(String where, String orderBy, int offset, int limit) {
        StringBuilder sql = new StringBuilder("SELECT ");
        sql.append(commaSeparatedColumns).append(" FROM ").append(tableName);
        if (where != null) {
            sql.append(" WHERE ").append(where);
        }
        if (orderBy != null) {
            sql.append(" ORDER BY ").append(orderBy);
        } else {
            sql.append(" ORDER BY ").append("ID ASC"); // default ord
        }
        if (offset > 0) {
            sql.append(" OFFSET ").append(offset).append(" ROWS ");
        }
        if (limit > 0) {
            sql.append(" FETCH NEXT ").append(limit).append(" ROWS ONLY ");
        }
        System.out.println(sql.toString());
        return sql.toString();
    }

    private void toPreparedStatement(PreparedStatement ps, Dto dto) {
        columns.forEach(column -> {
            Field field = columnField.get(column);
            int index = columnIndexes.get(column);
            try {
                Object object = field.get(dto);
                if (object != null) {
                    ps.setObject(index, object);
                } else {
                    ps.setString(index, null);
                }

            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });
    }

    private void toPreparedStatementBatch(PreparedStatement ps, Dto dto) {
        toPreparedStatement(ps, dto);
        try {
            ps.addBatch();
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    private Dto fromResultSet(ResultSet rs) {
        Dto dto = newDto();
        columnMapping.entrySet().stream().forEach(entry -> {
            Field f = columnField.get(entry.getKey());
            try {
                f.set(dto, getObjectFromRs(entry, rs));
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, e.getMessage());
            }
        });

        return dto;
    }

    // TODO: handle all types
    private Object getObjectFromRs(Entry<String, FieldType> e, ResultSet rs) throws SQLException {
        switch (e.getValue()) {
            case LONG:
                return rs.getLong(e.getKey());
            case STRING:
                return rs.getString(e.getKey());
            case INT:
                return rs.getInt(e.getKey());
            case DATE:
                return rs.getDate(e.getKey());
            case BOOLEAN:
                return rs.getBoolean(e.getKey());
            case BLOB:
                return rs.getBytes(e.getKey());
            case UNKNOWN:
                return rs.getObject(e.getKey());
            default:
                break;
        }
        return null;
    }

    private String buildWildCards() {
        return fields.stream().map(f -> "?").collect(Collectors.joining(",", "(", ")"));
    }

    private Connection getConnection() {
        return ConnectionManager.getInstance().getConnection(connectionType);
    }
}
