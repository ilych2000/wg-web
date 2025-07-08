package ru.wg.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

/**
 * Класс
 */
public class UtilsMessage {

    /** Логер */
    public static final Logger LOG = Logger.getLogger(UtilsMessage.class);

    /**
     * Возвращает Map полей объекта с родителями, кроме статических и <b>transient</b>.
     *
     * @param object
     * @param startClass
     * @param stopClass
     * @return Map
     */
    public static Map<String, Object> toParametersMap(Object object, Class<?> startClass,
            Class<?> stopClass) {
        return toParametersMap(object, startClass, stopClass, null);
    }

    /**
     * Возвращает Map полей объекта с родителями, кроме статических и <b>transient</b>.
     *
     * @param object
     * @param startClass
     * @param stopClass
     * @param namingFieldStrategy
     * @return Map
     */
    public static Map<String, Object> toParametersMap(Object object, Class<?> startClass,
            Class<?> stopClass, MessageUtils.ObjectFieldNamingStrategy namingFieldStrategy) {

        Map<String, Object> ret = new HashMap<String, Object>();

        if (object == null) {
            return ret;
        }

        if (startClass == null) {
            startClass = object.getClass();
        }

        if (stopClass == null) {
            stopClass = Object.class;
        }

        if (namingFieldStrategy == null) {
            namingFieldStrategy = MessageUtils.OBJECT_FIELD_NAMING_STRATEGY_DEFAULT;
        }

        Field[] fields = ReflectionUtils.getAllClassFieldsToClass(startClass, stopClass);
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            if (!Modifier.isStatic(field.getModifiers())
                    && !Modifier.isTransient(field.getModifiers())) {
                Object o = null;

                String name = namingFieldStrategy.getName(field);

                try {
                    o = field.get(object);

                    if ((o != null) && (o instanceof Date)) {
                        o = DateUtils.getDateFormatString((Date) o, DateUtils.DATE_FORMAT_DATE);
                    } else if ((o != null) && (o instanceof XMLGregorianCalendar)) {
                        o = ((XMLGregorianCalendar) o).toXMLFormat();
                    }

                    ret.put(name, o);
                } catch (Exception e) {
                    LOG.error("toParametersMap field name '" + name + "' set to '" + o + "'", e);
                }
            }
        }
        return ret;
    }

}
