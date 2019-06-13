package ourchat.ourchat.dao;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import ourchat.ourchat.dao.annotations.Column;
import ourchat.ourchat.dao.annotations.Table;
import ourchat.ourchat.dao.enums.FieldType;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import app.utils.ClassUtils;
import app.utils.ConnectionProperties;
import app.utils.DateUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class GenericDao<T extends Serializable> {

    protected T dto = newDto();

    protected abstract T newDto();

    private String tableName;
    private String commaSeparatedColumnNames;
    private Field[] fields;
    private Map<String, FieldType> columnMapping = new HashMap<>();
    private Map<String, Field> fieldValueMappings = new HashMap<>();

    private Connection connection;

    public GenericDao() {
        this(ConnectionProperties.createConnection());
    }

    public GenericDao(Connection connection) {
        this.connection = connection;
        this.init();
    }

    private void init() {
        this.tableName = getTableName();
        this.fields = getFields();
        this.fillMappings();
        this.commaSeparatedColumnNames = makeCommaSeparatedColumnNames();
    }

    //<editor-fold defaultstate="collapsed" desc="init methods">
    /**
     * Catches the table annotation of database model T
     *
     * @return name of table in database
     */
    private String getTableName() {
        try {
            Annotation annotation = dto.getClass().getAnnotation(Table.class);
            return annotation.annotationType().getDeclaredMethod("name").invoke(annotation, (Object[]) null).toString();
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchMethodException | SecurityException | InvocationTargetException exception) {
            return null;
        }
    }

    private Field[] getFields() {
        Class clazz = dto.getClass();
        List<Field> fields = new ArrayList<>();
        while (!clazz.equals(Object.class)) {
            for (Field declaredField : clazz.getDeclaredFields()) {
                fields.add(declaredField);
            }
            clazz = clazz.getSuperclass();
        }
        Field[] fieldsArray = new Field[fields.size()];
        for (int i = 0; i < fields.size(); i++) {
            fieldsArray[i] = fields.get(i);
        }

        return fieldsArray;
    }

    private void fillMappings() {
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(Column.class);
            if (annotation != null) {
                try {
                    String columnName = annotation.annotationType().getDeclaredMethod("name").invoke(annotation, (Object[]) null).toString();
                    FieldType fieldType = (FieldType) annotation.annotationType().getDeclaredMethod("type").invoke(annotation, (Object[]) null);
                    columnMapping.put(columnName, fieldType);
                    field.setAccessible(true);
                    fieldValueMappings.put(columnName, field);
                } catch (Exception e) {
                }
            }
        }
    }

    private String makeCommaSeparatedColumnNames() {
        return columnMapping.keySet().stream().collect(Collectors.joining(","));
    }
    // </editor-fold> 

    public T getById(long id) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM " + tableName + " WHERE ID= " + id);
            if (resultSet.next()) {
                return fromResultSet(resultSet);
            }
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            closeConnection();
        }
        return null;
    }

    private T fromResultSet(ResultSet rs) {
        T innerDto = newDto();
        columnMapping.entrySet().stream().forEach((Entry<String, FieldType> entry) -> {
            Field field = fieldValueMappings.get(entry.getKey());
            try {
                field.setAccessible(Boolean.TRUE);
                field.set(innerDto, getObjectFromResultSet(entry, rs));
            } catch (IllegalAccessException | IllegalArgumentException | SecurityException | SQLException exception) {
//                field.set(innerDto, (Object) null);
            }
        });
        return innerDto;
    }

    private Object getObjectFromResultSet(Entry<String, FieldType> entry, ResultSet rs) throws SQLException {
        switch (entry.getValue()) {
            case UNKNOWN:
                return rs.getObject(entry.getKey());
            case STRING:
                return rs.getString(entry.getKey());
            case LONG:
                return rs.getLong(entry.getKey());
            case DATE:
                return new Date(rs.getTimestamp(entry.getKey()).getTime());
            case BOOLEAN:
                return rs.getBoolean(entry.getKey());
            default:
                return null;
        }

    }

    public T insert(T dto) {
        try {
            StringBuilder psSql = new StringBuilder("INSERT INTO ");
            psSql.append(tableName).append("(").append(commaSeparatedColumnNames).append(") ");
            psSql.append("VALUES ");
            psSql.append(makeValuesClause(dto));

            PreparedStatement ps = connection.prepareStatement(psSql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();

            return dto;
        } catch (SQLException ex) {
            Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            closeConnection();
        }
    }

    private String makeValuesClause(T dto) {
        StringBuilder val = new StringBuilder("(");
        List<String> values = new ArrayList<>();
        columnMapping.keySet().forEach(key -> {
            Field field = fieldValueMappings.get(key);
            try {
                values.add(toPreparedStatementValue(field.get(dto)));
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(GenericDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        val.append(values.stream().collect(Collectors.joining(","))).append(")");
        return val.toString();
    }

    private String toPreparedStatementValue(Object object) {
        if (ClassUtils.isString(object.getClass())) {
            return surroundStringQuotes(object.toString());
        } else if (object.getClass().equals(Boolean.class)) {
            return object == Boolean.TRUE ? "1" : "0";
        } else if (ClassUtils.isWrapper(object.getClass())) {
            return object.toString();
        } else if (object.getClass().equals(Date.class)) {
            return surroundStringQuotes(DateUtils.formatDate((Date) object));
        } else {
            return null;
        }
    }

    private String surroundStringQuotes(String string) {
        return "'" + string + "'";
    }

    private void closeConnection() {
        ConnectionProperties.closeConnection(connection);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Table: ").append(tableName).append(", Columns: {\n");
        columnMapping.entrySet().forEach((e) -> {
            sb.append(e.getKey()).append(":").append(e.getValue()).append(",\n");
        });
        sb.append("}");

        return sb.toString();
    }

}
