package app.dao;

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

import app.dao.annotations.Column;
import app.dao.annotations.Table;
import app.dao.enums.FieldType;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import utils.ClassUtils;
import utils.DateUtils;

public abstract class GenericDao<T extends Serializable> {

    protected T dto = newDto();

    protected abstract T newDto();

    private String tableName;
    private String commaSeparatedColumnNames;
    private Field[] fields;
    private Map<String, FieldType> columnMapping = new HashMap<>();
    private Map<String, Field> fieldValueMappings = new HashMap<>();

    private Connection connection;

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
        return dto.getClass().getDeclaredFields();
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
            ResultSet resultSet = connection.createStatement()
                    .executeQuery("SELECT * FROM " + tableName + " WHERE ID= " + id);
            if (resultSet.next()) {
                return fromResultSet(resultSet);
            }
        } catch (SQLException exception) {
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
                break;
        }
        return null;
    }

    public T insert(T dto) {
        try {
            StringBuilder psSql = new StringBuilder("INSERT INTO ");
            psSql.append(tableName).append("(").append(commaSeparatedColumnNames).append(") ");
            psSql.append("VALUES ");
            psSql.append(makeValuesClause(dto));

            PreparedStatement ps = connection.prepareStatement(psSql.toString(), Statement.RETURN_GENERATED_KEYS);
            ps.executeUpdate();
        } catch (SQLException exception) {
            // TODO Auto-generated catch block
        }
        return dto;
    }

    private String makeValuesClause(T dto) {
        StringBuilder val = new StringBuilder("(");
        List<String> values = new ArrayList<>();
        columnMapping.keySet().forEach(key -> {
            Field field = fieldValueMappings.get(key);
            try {
                values.add(toPreparedStatementValue(field.get(dto)));
            } catch (Exception e) {
                e.printStackTrace();
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
