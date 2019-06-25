package app.core.dao.annotations;

import app.core.dao.enums.ConnectionType;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * {@code Table} annotation is used for mapping models to a database tables
 */
@Target(value = {ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface Table {

    /**
     * Indicates table name in a database corresponding to the model annotated
     * with {@code Table}
     */
    String name();

    /**
     * Depending on a consumer/producer data source of model
     */
    ConnectionType connectionType() default ConnectionType.CONSUMER;
}
