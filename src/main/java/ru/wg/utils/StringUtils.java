package ru.wg.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;

/**
 * Класс
 */
public class StringUtils {

    /** Поле класса */
    private static final String CR = "\n";

    /** Поле класса */
    private static final String CLOSE_OBJECT = "}" + CR;

    /** Поле класса */
    private static final String OPEN_OBJECT = "{";

    /** Поле класса */
    private static final String CLOSE_LIST = "]" + CR;

    /** Поле класса */
    private static final String OPEN_LIST = "[";

    /** Поле класса */
    private static final String STEP_PREFIX = "  ";

    /**
     * Метод
     */
    public static String mapToString(HashMap<String, Object[]> aMap) {
        StringBuilder ret = new StringBuilder();

        for (Entry<String, Object[]> item : aMap.entrySet()) {
            if (ret.length() != 0) {
                ret.append(CR);
            }

            ret.append(item.getKey()).append("={");

            for (Object o : item.getValue()) {
                ret.append(String.valueOf(o)).append(",");
            }

            ret.append(item.getKey()).append("}");
        }

        return ret.toString();
    }

    /**
     * Метод
     */
    public static String mapToString(Object aObject, String aPrefix) {
        if (aObject == null) {
            return "null";
        }
        StringBuilder ret = new StringBuilder();
        List<Object> aWatchedObjects = new ArrayList<Object>();
        mapToString(ret, aObject, aPrefix, aWatchedObjects);
        aWatchedObjects.clear();
        return ret.toString();
    }

    /**
     * Метод
     */
    public static String mapToString(Object aObject) {
        return mapToString(aObject, "");
    }

    /**
     * Метод
     */
    public static String toString(Object aObject) {
        return mapToString(aObject);
    }

    /**
     * Добавляет
     * 
     * @param aString
     * @param aObject
     * @param aPrefix
     */
    public static void mapToString(StringBuilder aString, Object aObject,
            String aPrefix, List<Object> aWatchedObjects) {
        if (aObject == null) {
            aString.append("null").append(CR);
            return;
        }
        String nextPrefix = aPrefix + STEP_PREFIX;

        if (aObject instanceof Object[]) {
            aString.append(OPEN_LIST);
            if (((Object[]) aObject).length > 0) {
                aString.append(CR);
                for (Object o : (Object[]) aObject) {
                    aString.append(nextPrefix);
                    mapToString(aString, o, nextPrefix, aWatchedObjects);
                }
                aString.append(aPrefix);
            }
            aString.append(CLOSE_LIST);
        } else if (aObject instanceof Iterable) {
            aString.append(OPEN_LIST);
            Iterator<?> iter = ((Iterable<?>) aObject).iterator();
            if (iter.hasNext()) {
                aString.append(CR);
                while (iter.hasNext()) {
                    aString.append(nextPrefix);
                    mapToString(aString, iter.next(), nextPrefix,
                            aWatchedObjects);
                }
                aString.append(aPrefix);
            }
            aString.append(CLOSE_LIST);
        } else if (aObject instanceof Map) {
            aString.append(OPEN_OBJECT);
            if (((Map<?, ?>) aObject).size() > 0) {
                aString.append(CR);
                for (Entry<?, ?> item : ((Map<?, ?>) aObject).entrySet()) {
                    aString.append(nextPrefix).append(item.getKey())
                            .append("=");
                    mapToString(aString, item.getValue(), nextPrefix,
                            aWatchedObjects);
                }
                aString.append(aPrefix);
            }
            aString.append(CLOSE_OBJECT);
        } else {
            Class<? extends Object> mapClass = aObject.getClass();
            if (mapClass.isPrimitive()) {
                aString.append(String.valueOf(aObject)).append(CR);
            } else if (mapClass == String.class) {

                aString.append("\"").append(String.valueOf(aObject))
                        .append("\"").append(CR);
            } else if (ReflectionUtils.isRedefinedToStringMethod(mapClass)) {
                aString.append("(").append(mapClass.getSimpleName());
                
                if (!(aObject instanceof Number)) {
                    aString.append("#toString");
                }
                
                aString.append(") ").append(String.valueOf(aObject)).append(CR);
            } else {
                aString.append("(").append(mapClass.getSimpleName())
                        .append("@")
                        .append(Integer.toHexString(aObject.hashCode()))
                        .append(")");
                if (aWatchedObjects.contains(aObject)) {
                    aString.append(" Already viewed.").append(CR);
                } else {
                    aWatchedObjects.add(aObject);
                    mapToString(aString, UtilsMessage.toParametersMap(aObject,
                            mapClass, Object.class), nextPrefix,
                            aWatchedObjects);
                }
            }
        }
    }

    /**
     * Удаляет дубликаты из массив строк
     * 
     * @param массив строк
     * @return массив строк
     */
    public static String[] removeDublicate(String[] aArrayString) {
        Set<String> set = new TreeSet<String>();

        for (String s : aArrayString) {
            set.add(s);
        }

        return set.toArray(new String[] {});
    }
}
