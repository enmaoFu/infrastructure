package com.infrastructure.common;

import java.lang.annotation.*;

/**
 * Excel实体BEAN的属性注解
 * Created by suyl on 2016/5/11.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelAnnotation {

    String name();//Excel列名

    int width() default 10;//Excel列宽

    int id() default 0;//Excel列ID

    boolean isStatistics() default false;//是否是复杂表头

    boolean isParam() default false;    //是否需要被统计

    int rank(); //表头层级

    boolean isMergedCell() default false; //是否需要合并单元格

    boolean mergedCell() default  false;  //合并单元格数目,只需注释一份

    int leftOrRight() default 0;//用于同一层级表单左右区分:0 左，1 右 , -1 其他

    boolean isStatic() default false; //用户表单中的静态栏目
}
