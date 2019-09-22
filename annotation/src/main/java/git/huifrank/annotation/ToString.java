package git.huifrank.annotation;


import git.huifrank.handler.DefaultPropertyMapper;
import git.huifrank.handler.PropertiesMapper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface ToString {


    ToStringMapper[] handler() ;


    @Target({ElementType.TYPE,ElementType.ANNOTATION_TYPE})
    @Retention(RetentionPolicy.SOURCE)
    @interface ToStringMapper {

        Class<? extends PropertiesMapper> mapper() default DefaultPropertyMapper.class;

        String[] propertyName() default "";
    }
}
