package ru.wg.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилиты для непосредственной работы с полями и методами объекта
 */
public class ReflectionUtils {

    /**
     * Создает экземпляр класса.
     *
     * @param name полное имя класса
     * @return экземпляр класса
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     */
    public static Object createObject(String name)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(name).newInstance();
    }

    /**
     * Вызов метода объекта с параметрами.
     *
     * @param object объект
     * @param method имя метода
     * @param params параметры
     * @return результат вызова метода
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object callMethod(Object object, String method, Object... params)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        List<Class<?>> parameterTypes = new ArrayList<Class<?>>();

        if ((params != null) && (params.length > 0)) {
            for (Object o : params) {
                parameterTypes.add(o.getClass());
            }
        }

        return object.getClass()
                .getDeclaredMethod(method, parameterTypes.toArray(new Class<?>[] {}))
                .invoke(object, params);
    }

    /**
     * Вызов метода объекта.
     *
     * @param object объект
     * @param method имя метода
     * @return результат вызова метода
     * @throws IllegalArgumentException
     * @throws SecurityException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     */
    public static Object callMethod(Object object, String method)
            throws IllegalArgumentException, SecurityException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {

        return callMethod(object, method, (Object[]) null);
    }

    /**
     * Присвоение значения полю объекта.
     *
     * @param object объект
     * @param name имя поля
     * @param value значение
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setDeclaredField(Object object, String name, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field field = object.getClass().getDeclaredField(name);
        AccessibleObject.setAccessible(new Field[] {field}, true);
        field.set(object, value);
    }

    /**
     * Присвоение значения полю объекта через вызов сетора.
     *
     * @param object объект
     * @param name имя поля. Для него ищется метод "set" + name.
     * @param value значение
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException если не найден метод "set" + name.
     * @throws InvocationTargetException
     */
    public static void setDeclaredMethodField(Object object, String name, Object value)
            throws SecurityException, NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Method[] methods = object.getClass().getMethods();
        String mName = "set" + name;
        Method method = null;
        for (Method m : methods) {
            if (mName.equals(m.getName())) {
                method = m;
                break;
            }
        }

        if (method == null) {
            throw new NoSuchMethodException(mName);
        }

        Class<?>[] types = method.getParameterTypes();

        if (types.length != 1) {
            throw new IllegalArgumentException(
                    "Method " + mName + " has more than one parameter, but " + types.length + ".");
        }

        callMethod(object, mName, MessageUtils.getParseObject(value, types[0]));
    }

    /**
     * Получение значения поля объекта.
     *
     * @param object объект
     * @param name имя поля
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueField(Object object, String name) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(name);
        AccessibleObject.setAccessible(new Field[] {field}, true);
        return field.get(object);
    }

    /**
     * Получение значения первого поля объекта.
     *
     * @param object объект
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValueFirstField(Object object) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (object == null) {
            return null;
        }

        Field[] fields = object.getClass().getDeclaredFields();
        if (fields.length == 0) {
            throw new NoSuchFieldException("Object " + object + " no has fields.");
        }

        AccessibleObject.setAccessible(new Field[] {fields[0]}, true);
        return fields[0].get(object);
    }

    /**
     * Получение всех полей класса и его родителей.
     *
     * @param clazz класс
     * @return массив полей
     */
    public static Field[] getAllClassFields(Class<?> clazz) {
        return getAllClassFieldsToClass(clazz, Object.class);
    }

    /**
     * Получение всех полей класса и его родителей до определенного родительского класса.
     *
     * @param clazz класс
     * @param stopClass класс до которого нужно получить поля не включая этот класс
     * @return массив полей
     */
    public static Field[] getAllClassFieldsToClass(Class<?> clazz, Class<?> stopClass) {
        Field[] fields = clazz.getDeclaredFields();
        Class<?> sClazz = clazz.getSuperclass();

        if ((sClazz != stopClass) && (sClazz != Object.class)) {
            Field[] sFields = getAllClassFieldsToClass(sClazz, stopClass);
            if (sFields.length > 0) {
                Field[] newFields = new Field[fields.length + sFields.length];
                System.arraycopy(fields, 0, newFields, 0, fields.length);
                System.arraycopy(sFields, 0, newFields, fields.length, sFields.length);
                fields = newFields;
            }
        }
        return fields;
    }

    /**
     * Проверяет переопределен ли метод toString()
     *
     * @see {@link Object#toString()}
     * @param clazz
     * @return
     */
    public static boolean isRedefinedToStringMethod(Class<?> clazz) {
        Method fields;
        try {
            fields = clazz.getDeclaredMethod("toString");
        } catch (Exception e) {
            return false;
        }

        return fields.getDeclaringClass() != Object.class;
    }

}
