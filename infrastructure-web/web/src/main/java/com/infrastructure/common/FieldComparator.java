package com.infrastructure.common;

import java.lang.reflect.Field;
import java.util.Comparator;

/**
 * Created by suyl on 2016/5/11.
 */
@SuppressWarnings("unchecked")
public class FieldComparator implements Comparator {

    @Override
    public int compare(Object o1, Object o2) {
        Field fieldOne = (Field)o1;
        Field fieldTwo = (Field)o2;
        ExcelAnnotation annotationOne = fieldOne.getAnnotation(ExcelAnnotation.class);
        ExcelAnnotation annotationTwo = fieldTwo.getAnnotation(ExcelAnnotation.class);
        return annotationOne.id()-annotationTwo.id();
    }
}
