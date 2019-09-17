package git.huifrank.handler;

public @interface ToStringBuilder {

    Class<? extends ToStringHandler> handler();

    String[] propertyName() default "";
}
