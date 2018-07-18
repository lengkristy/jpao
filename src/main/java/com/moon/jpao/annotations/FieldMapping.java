package com.moon.jpao.annotations;

import java.lang.annotation.*;

/**
 * 查询字段与javabean字段映射
 * @author 代浩然
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface FieldMapping {

    /**数据库查询出的字段名称*/
    String queryFileName() default "";
}
