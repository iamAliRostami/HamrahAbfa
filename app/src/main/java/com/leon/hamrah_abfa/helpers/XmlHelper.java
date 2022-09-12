package com.leon.hamrah_abfa.helpers;

import java.lang.reflect.Field;
import java.util.HashMap;

public class XmlHelper {

    public static void setRColor(Class rClass, String rFieldName, Object newValue) {
        setR(rClass, "color", rFieldName, newValue);
    }

    public static void setRString(Class rClass, String rFieldName, Object newValue) {
        setR(rClass, "string", rFieldName, newValue);
    }

    public static void setR(Class rClass, String innerClassName, String rFieldName, Object newValue) {
        setStatic(rClass.getName() + "$"  + innerClassName, rFieldName, newValue);
    }
    public static void setStatic(String aClassName, String staticFieldName, Object toSet) {
        try {
            setStatic(Class.forName(aClassName), staticFieldName, toSet);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void setStatic(Class<?> aClass, String staticFieldName, Object toSet) {
        try {
            Field declaredField = aClass.getDeclaredField(staticFieldName);
            declaredField.setAccessible(true);
            declaredField.set(null, toSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
