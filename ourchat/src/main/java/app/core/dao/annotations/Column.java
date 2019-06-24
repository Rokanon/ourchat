package app.core.dao.annotations;

import app.core.dao.enums.FieldType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Column} annotation is used for mapping fields of a model
 * class(annotated with {@code Table}) to the corresponding column of a database
 * table
 */
@Target(value = {ElementType.FIELD})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Column {

    /**
     * Name of a database column
     */
    String name();

    /**
     * Used for {@code PreparedStatement::setXXX} mapping
     */
    FieldType type() default FieldType.UNKNOWN;

    boolean primaryKey() default false;
}
