package git.huifrank.annotation;


import git.huifrank.handler.ToStringBuilder;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE,ElementType.PACKAGE})
@Retention(RetentionPolicy.SOURCE)
public @interface ToString {


    ToStringBuilder[] handler() ;
}
