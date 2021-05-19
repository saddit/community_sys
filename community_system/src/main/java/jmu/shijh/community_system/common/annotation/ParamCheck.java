package jmu.shijh.community_system.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 1.
 * 'exclude'、'include'只能使用其中一个,如果两个都用，那么只使用’include‘ <br/>
 * 2.
 * 仅适用于指定方法的参数名以及参数对象的属性名且方法参数名不能与对象属性名相同<br/>
 * 3.
 * 如果需要检查的项为String可以指定regexp项进行正则检查，默认不进行
 * 4.
 * 不使用正则：参数为空或者NULL则阻断<br/>
 * 使用正则：命中则阻断
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamCheck {
    String value() default "参数不能为空或格式有误";
    String regexp() default "";
    /*使用正则表达检查时 是否允许NULL或空字符串通行*/
    boolean empty() default false;
    String[] include() default {};
    String[] exclude() default {};
}
