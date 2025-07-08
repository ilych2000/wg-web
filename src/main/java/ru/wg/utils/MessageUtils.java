package ru.wg.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Класс
 */
public class MessageUtils {

    /** Логер */
    private static final Logger LOG = Logger.getLogger(MessageUtils.class);

    /** Поле класса */
    private static final Map<Class<?>, TransParser> PARSING_MAP =
            new HashMap<Class<?>, MessageUtils.TransParser>() {

                /** Поле класса */
                private static final long serialVersionUID = 1L;

                {
                    TransParser booleanParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Boolean parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Boolean.valueOf(String.valueOf(value));
                        }
                    };
                    TransParser booleanParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Boolean parse(Object value) {
                            if (value == null) {
                                return false;
                            }

                            return Boolean.valueOf(String.valueOf(value));
                        }
                    };

                    TransParser stringParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public String parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return String.valueOf(value);
                        }
                    };

                    TransParser bigIntegerParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public BigInteger parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return new BigInteger(String.valueOf(value));
                        }
                    };

                    TransParser intParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Integer parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Integer.parseInt(String.valueOf(value));
                        }
                    };

                    TransParser intParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Integer parse(Object value) {
                            if (value == null) {
                                return 0;
                            }

                            return Integer.parseInt(String.valueOf(value));
                        }
                    };

                    TransParser byteParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Byte parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Byte.parseByte(String.valueOf(value));
                        }
                    };
                    TransParser byteParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Byte parse(Object value) {
                            if (value == null) {
                                return 0;
                            }

                            return Byte.parseByte(String.valueOf(value));
                        }
                    };

                    TransParser byteArrayParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public byte[] parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return String.valueOf(value).getBytes();
                        }
                    };

                    TransParser shortParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Short parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Short.parseShort(String.valueOf(value));
                        }
                    };

                    TransParser shortParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Short parse(Object value) {
                            if (value == null) {
                                return 0;
                            }

                            return Short.parseShort(String.valueOf(value));
                        }
                    };

                    TransParser doubleParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Double parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Double.parseDouble(String.valueOf(value));
                        }
                    };

                    TransParser doubleParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Double parse(Object value) {
                            if (value == null) {
                                return 0d;
                            }

                            return Double.parseDouble(String.valueOf(value));
                        }
                    };

                    TransParser floatParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Float parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Float.parseFloat(String.valueOf(value));
                        }
                    };

                    TransParser floatParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Float parse(Object value) {
                            if (value == null) {
                                return 0f;
                            }

                            return Float.parseFloat(String.valueOf(value));
                        }
                    };

                    TransParser longParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Long parse(Object value) {
                            if (value == null) {
                                return null;
                            }

                            return Long.parseLong(String.valueOf(value));
                        }
                    };

                    TransParser longParserPrim = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Long parse(Object value) {
                            if (value == null) {
                                return 0l;
                            }

                            return Long.parseLong(String.valueOf(value));
                        }
                    };

                    TransParser dateParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public Date parse(Object value)
                                throws ParseException, DatatypeConfigurationException {
                            if (value == null) {
                                return null;
                            }

                            if (value instanceof XMLGregorianCalendar) {
                                return DateUtils.getDateFomXMLGregorianCalendar(
                                        (XMLGregorianCalendar) value);
                            }

                            String sValue = String.valueOf(value);

                            try {
                                return DateUtils.getDateFomXMLGregorianCalendarString(sValue);
                            } catch (IllegalArgumentException e) {
                                return DateUtils.getDateFormatString(sValue);
                            }
                        }
                    };

                    TransParser xmlGregorianCalendarParser = new TransParser() {

                        /**
                         * {@inheritDoc}
                         */
                        @Override
                        public XMLGregorianCalendar parse(Object value)
                                throws DatatypeConfigurationException, ParseException {
                            if (value == null) {
                                return null;
                            }

                            if (value instanceof java.util.Date) {
                                return DateUtils.getXMLGregorianCalendar((Date) value);
                            }

                            String sValue = String.valueOf(value);

                            try {
                                return DateUtils.getXMLGregorianCalendarByString(sValue);
                            } catch (IllegalArgumentException e) {
                                return DateUtils.getXMLGregorianCalendar(
                                        DateUtils.getDateFormatString(sValue));
                            }
                        }
                    };

                    put(boolean.class, booleanParserPrim);
                    put(Boolean.class, booleanParser);
                    put(java.lang.String.class, stringParser);
                    put(java.math.BigInteger.class, bigIntegerParser);
                    put(int.class, intParserPrim);
                    put(Integer.class, intParser);
                    put(byte.class, byteParserPrim);
                    put(byte[].class, byteArrayParser);
                    put(Byte.class, byteParser);
                    put(short.class, shortParserPrim);
                    put(Short.class, shortParser);
                    put(double.class, doubleParserPrim);
                    put(Double.class, doubleParser);
                    put(float.class, floatParserPrim);
                    put(Float.class, floatParser);
                    put(long.class, longParserPrim);
                    put(Long.class, longParser);
                    put(java.util.Date.class, dateParser);
                    put(javax.xml.datatype.XMLGregorianCalendar.class, xmlGregorianCalendarParser);
                }
            };

    /**
     * Возвращает значения полей класса в виде Map
     *
     * @param object - экземпляр объекта источника
     * @return Map
     */
    public static Map<String, Object> toParametersMap(Object object) {
        return toParametersMap(object, false, null);
    }

    /**
     * @deprecated Используйте {@link ReflectionUtils#getAllClassFields}
     * @param clazz
     * @return массив Field
     */
    @Deprecated
    public static Field[] getAllFields(Class<?> clazz) {
        return ReflectionUtils.getAllClassFields(clazz);
    }

    /**
     * Возвращает значения полей класса в виде Map
     *
     * @param object - экземпляр объекта источника
     * @param nameToUpperCase использовать заглавные именя полей объекта
     * @param prefix префикс имени поля
     * @return Map
     */
    public static Map<String, Object> toParametersMap(Object object, boolean nameToUpperCase,
            String prefix) {
        Map<String, Object> ret = new HashMap<String, Object>();
        if (object == null) {
            return ret;
        }

        if ((prefix != null) && !prefix.isEmpty()) {
            prefix += "_";
        } else {
            prefix = "";
        }

        Field[] fields = ReflectionUtils.getAllClassFields(object.getClass());
        AccessibleObject.setAccessible(fields, true);
        for (Field field : fields) {
            Object o = null;

            String fieldName = prefix + field.getName();

            if (nameToUpperCase) {
                fieldName = fieldName.toUpperCase();
            }
            try {
                o = field.get(object);
                if ((o != null) && !(o.getClass().isPrimitive() || (o instanceof String))) {
                    if (o instanceof BigInteger) {
                        o = ((BigInteger) o).toString();
                    } else if (o instanceof XMLGregorianCalendar) {
                        o = DateUtils.getDateFomXMLGregorianCalendar((XMLGregorianCalendar) o);
                    }
                }
                ret.put(fieldName, o);
            } catch (Exception e) {
                LOG.error("toParametersMap field name '" + fieldName + "' set to '" + o + "'", e);
            }
        }
        return ret;
    }

    /**
     * Заполняет поля объекта из {@link Map}
     *
     * @param map {@link Map} источник
     * @param object объект для заполнения
     */
    public static void fillFromMap(Map<String, Object> map, Object object) {
        fillFromMap(map, object, null, null);
    }

    /**
     * Заполняет поля объекта из {@link Map} не создавая объекты полей не указаных в map.
     *
     * @param map {@link Map} источник
     * @param object объект для заполнения
     * @param prefix префикс имени поля - родительское поле этого объекта
     * @param mappingParams {@link Map} для преобразования ключа из map в путь до поля класса
     */
    public static void fillFromMap(Map<String, Object> map, Object object, String prefix,
            Map<String, String> mappingParams) {
        if ((object == null) || (map == null)) {
            return;
        }

        if ((prefix != null) && !prefix.isEmpty()) {
            prefix += "_";
        } else {
            prefix = "";
        }

        Field[] fields = ReflectionUtils.getAllClassFields(object.getClass());
        AccessibleObject.setAccessible(fields, true);

        for (Field field : fields) {
            Object o = null;

            String nameField = OBJECT_FIELD_NAMING_STRATEGY_DEFAULT.getName(field).toUpperCase();

            if (mappingParams != null) {
                String newNameField = mappingParams.get((prefix + nameField).toUpperCase());
                if (newNameField != null) {
                    nameField = newNameField;
                }
            }

            if (map.containsKey(nameField)) {
                try {
                    o = map.get(nameField);
                    if (o != null) {
                        Class<?> type = field.getType();

                        if (!type.isAssignableFrom(o.getClass())) {
                            String value = String.valueOf(o);

                            if (type.getSimpleName().equals("byte[]")) {
                                o = Base64.decodeBase64(value);
                            } else if (type == java.util.Date.class) {
                                try {
                                    o = DateUtils.getDateFomXMLGregorianCalendarString(value);
                                } catch (IllegalArgumentException e) {
                                    try {
                                        o = DateUtils.getDateFormatString(value);
                                    } catch (ParseException e1) {
                                        throw new Exception(e + "\n" + e1);
                                    }
                                }
                            } else if (isParseClass(type)) {
                                o = getParseObject(o, type);
                            }
                        }
                    }
                    field.set(object, o);
                } catch (Exception e) {
                    LOG.error("fillFromMap set " + nameField + "=" + o, e);
                }
            }
        }
    }

    /**
     * Метод
     */
    public static boolean fillFullObjectFromMap(Map<String, Object> map, Object object,
            String prefix, Map<String, String> mappingParams) {
        return fillFullObjectFromMap(map, object, prefix, mappingParams, null);
    }

    /**
     * Заполняет поля объекта из {@link Map} рекурсивно с воссозданием всех полей.
     *
     * @param map {@link Map} источник
     * @param object объект для заполнения
     * @param prefix префикс имени поля - родительское поле этого объекта
     * @param mappingParams {@link Map} для преобразования ключа из map в путь до поля класса
     * @param namingFieldStrategy реализация интерфейса {@link ObjectFieldNamingStrategy}
     * @return
     */
    public static boolean fillFullObjectFromMap(Map<String, Object> map, Object object,
            String prefix, Map<String, String> mappingParams,
            ObjectFieldNamingStrategy namingFieldStrategy) {
        boolean isChanged = false;
        if ((object == null) || (map == null)) {
            return isChanged;
        }

        if ((prefix != null) && !prefix.isEmpty()) {
            prefix += "_";
        } else {
            prefix = "";
        }

        if (namingFieldStrategy == null) {
            namingFieldStrategy = OBJECT_FIELD_NAMING_STRATEGY_DEFAULT;
        }

        Field[] fields = ReflectionUtils.getAllClassFields(object.getClass());
        AccessibleObject.setAccessible(fields, true);

        for (Field field : fields) {
            Object o = null;
            boolean isMaped = false;
            Class<?> type = field.getType();
            String nameMaping = (prefix + namingFieldStrategy.getName(field)).toUpperCase();

            if (mappingParams != null) {
                if (mappingParams.containsKey(nameMaping)
                        && map.containsKey(mappingParams.get(nameMaping))) {
                    o = map.get(mappingParams.get(nameMaping));
                    isMaped = true;
                }
            } else if (map.containsKey(nameMaping)) {
                o = map.get(nameMaping);
                isMaped = true;
            }

            /**
             * Класс
             */
            if (!isParseClass(type) && !type.isInterface()) {
                try {
                    if (isMaped) {
                        if ((o == null) || type.isAssignableFrom(o.getClass())) {
                            field.set(object, o);
                            isChanged = isChanged || (o != null);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Field " + nameMaping + " set object to " + o);
                            }
                        } else if (LOG.isDebugEnabled()) {
                            LOG.debug("Field " + nameMaping + " is class '" + type.getSimpleName()
                                    + "' and not Assignable to '" + o.getClass().getSimpleName()
                                    + "'");
                        }
                    }
                    Object newObject = field.get(object);

                    boolean isNewClass = false;

                    if (newObject == null) {
                        newObject = field.getType().newInstance();
                        isNewClass = true;
                        if (LOG.isDebugEnabled()) {
                            LOG.debug("Field " + nameMaping + " create  newInstance");
                        }
                    } else if (LOG.isDebugEnabled()) {
                        LOG.debug("Field " + nameMaping + " already exists");
                    }

                    boolean isFillChanged = fillFullObjectFromMap(map, newObject, nameMaping,
                            mappingParams, namingFieldStrategy);
                    isChanged = isFillChanged || isChanged;

                    if (isNewClass) {
                        if (isFillChanged) {
                            field.set(object, newObject);
                            if (LOG.isDebugEnabled()) {
                                LOG.debug("Field " + nameMaping + " set newInstance");
                            }
                        } else if (LOG.isDebugEnabled()) {
                            LOG.debug("Field " + nameMaping
                                    + " not set newInstance - not new fields.");
                        }
                    }

                } catch (Exception e) {
                    LOG.error("Create Instance of " + nameMaping + "(" + type.getSimpleName() + ")"
                            + ". Message: " + e);
                    continue;
                }
            } else if (isMaped) {
                o = getParseObject(o, type);
                try {
                    field.set(object, o);
                    isChanged = true;
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Field " + nameMaping + " set to " + o);
                    }
                } catch (Exception e) {
                    LOG.error("fillFromMap set " + nameMaping + "=" + o, e);
                }
            } else if (LOG.isDebugEnabled()) {
                LOG.debug("Field " + nameMaping + " not maped.");
            }
        }
        return isChanged;
    }

    /**
     * Проверка типа класса на возможность парсить методом {@link #getParseObject}
     *
     * @param type тип класса
     * @return
     */
    public static boolean isParseClass(Class<?> type) {
        return PARSING_MAP.containsKey(type) || type.isEnum();
    }

    /**
     * Парсинг объекта в определенный тип класса
     *
     * @param oldObject объект
     * @param type класс
     * @return новый объект
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public static Object getParseObject(Object oldObject, Class<?> type) {

        Object o = oldObject;
        if (PARSING_MAP.containsKey(type)) {
            try {
                if ((o == null) || !type.isAssignableFrom(o.getClass())) {
                    o = PARSING_MAP.get(type).parse(o);
                }
            } catch (Exception e) {
                if (LOG.isDebugEnabled()) {
                    LOG.error("parse '" + o + "' to " + type.getSimpleName(), e);
                } else {
                    LOG.error("parse '" + o + "' to " + type.getSimpleName() + " : " + e);
                }
            }
        } else if (type.isEnum()) {
            o = Enum.valueOf((Class<Enum>) type, o.toString());
        }

        return o;
    }

    /**
     * Парсинг объекта в определенный тип класса
     *
     * @param oldObject объект
     * @param type класс
     * @return новый объект
     * @throws Exception
     */
    public static Object del_getTypedObject(Object oldObject, Class<?> type) throws Exception {
        if (oldObject == null) {
            return oldObject;
        }

        Object o = oldObject;
        String value = String.valueOf(o);
        if (type.getSimpleName().equals("byte[]")) {
            o = Base64.decodeBase64(value);
        } else if (type == java.util.Date.class) {
            try {
                o = DateUtils.getDateFomXMLGregorianCalendarString(value);
            } catch (IllegalArgumentException e) {
                try {
                    o = DateUtils.getDateFormatString(value);
                } catch (ParseException e1) {
                    throw new Exception(e + "\n" + e1);
                }
            }
        } else {
            o = getParseObject(oldObject, type);
        }
        return o;
    }

    /** Поле класса */
    public interface TransParser {

        Object parse(Object value) throws Exception;
    }

    /**
     * Метод тестирования мапинга значений на поля класса.
     *
     * @param args
     */
    @SuppressWarnings("serial")
    public static void main(String[] args) {
        PropertyConfigurator.configure("D:\\log4j.properties");

        System.out.println("Begin");
        String prefix = "";

        TestXX object = new TestXX();
        System.out.println("TestXX = " + object);
        Map<String, Object> map = new HashMap<String, Object>() {

            {

                put("BPARAM1", "T221rue");
                put("BPARAM2", true);

            }
        };

        Map<String, String> mappingParams = new HashMap<String, String>() {

            {

                put("BPARAM1", "BPARAM1");
                put("BPARAM2", "BPARAM2");

            }
        };

        MessageUtils.fillFullObjectFromMap(map, object, prefix, mappingParams);

        System.out.println("TestXX = " + object);
        System.out.println("End.");

    }

    /**
     * Класс
     */
    public static class TestXX {

        boolean bParam1;

        Boolean bParam2;

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("TestXX [\n  bParam1=");
            builder.append(bParam1);
            builder.append(",\n  bParam2=");
            builder.append(bParam2);
            builder.append("\n]");
            return builder.toString();
        }

    }

    /**
     * Интерфейс для различных реализаций алгоритма получения имени поля из класса {@link Field}.
     */
    public static abstract class ObjectFieldNamingStrategy {

        /**
         * Получение имени поля класса
         *
         * @param field {@link Field}
         * @param defaultName имя по умолчанию
         * @return имя
         */
        public abstract String getName(Field field, String defaultName);

        /**
         * Возвращает имя поля класса из метода {@link #getName(Field, String)} где значение по
         * умолчанию {@link Field#getName()}
         *
         * @param field {@link Field}
         * @return имя
         */
        public String getName(Field field) {
            if (field == null) {
                return null;
            }

            return getName(field, field.getName());
        }
    }

    /**
     * Реализация интерфейса {@link ObjectFieldNamingStrategy} - получение имени поля по умолчанию
     * {@link Field#getName()}
     */
    public static ObjectFieldNamingStrategy OBJECT_FIELD_NAMING_STRATEGY_DEFAULT =
            new ObjectFieldNamingStrategy() {

                /**
                 * Возвращает имя по умолчанию <b>defaultName</b> или {@link Field#getName()}
                 *
                 * @param field {@link Field}
                 * @param defaultName имя по умолчанию
                 * @return имя
                 */
                @Override
                public String getName(Field field, String defaultName) {
                    if (defaultName == null) {
                        defaultName = field.getName();
                    }

                    return defaultName;
                }
            };

}
