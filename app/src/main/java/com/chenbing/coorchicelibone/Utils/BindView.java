package com.chenbing.coorchicelibone.Utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author coorchice
 * @date 2019/01/08
 */

/**
 * 只能作用于属性上
 */
@Target(ElementType.FIELD)
/**
 * 可以作用于运行时
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    /**
     * value 可以直接写值在括号中
     * 如果是别的名称，使用时可能是这样的：@BindView(id = R.id.tv)
     */
    int value() default -1;
}
