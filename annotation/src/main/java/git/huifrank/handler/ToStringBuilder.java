package git.huifrank.handler;


import java.util.function.Function;


public @interface ToStringBuilder {

    Class<? extends PropertiesMapper> mapper();

    String[] propertyName() default "";
}
