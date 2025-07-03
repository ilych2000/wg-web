package ru.wg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;

public class DateUtils {

	/** Логер */
	private static final Logger LOG = Logger.getLogger(DateUtils.class);

	/** Поле класса */
	private static DatatypeFactory DATA_TYPE_FACTORY_INSTANCE = null;

	static {
		try {
			DATA_TYPE_FACTORY_INSTANCE = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			LOG.error(
					"DATA_TYPE_FACTORY_INSTANCE = DatatypeFactory.newInstance()",
					e);
		}
	}

	/** Формат даты без временем по умолчанию <b>{@value} </b> */
	public static final String DATE_FORMAT_DATE = "dd.MM.yyyy";

	/** Формат времени по умолчанию <b>{@value} </b> */
	public static final String DATE_FORMAT_TIME = "HH:mm:ss";

	/** Формат даты со временем по умолчанию <b>{@value} </b> */
	public static final String DATE_FORMAT_DEFAULT = DATE_FORMAT_DATE + " "
			+ DATE_FORMAT_TIME;

	/** Поле класса */
	public static final long MSEC_IN_HOUR = 3600000;

	/** Поле класса */
	public static final long MSEC_IN_DAY = 24 * 3600000;

	/** Поле класса */
	private static final ThreadLocal<SimpleDateFormat> PATERN_DATE_FORMAT_DEFAULT = new ThreadLocal<SimpleDateFormat>() {

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		}
	};

	/**
	 * Метод
	 */
	public static String getBetweenDateString(Date dat1, Date dat2) {
		if (dat1 != null && dat2 != null) {
			return getIntervalDateString(dat1.getTime() - dat2.getTime());
		}

		return null;
	}

	/**
	 * Метод
	 */
	public static String getIntervalDateString(long intervalMSec) {
		String formatValueDate = null;
		long btw = intervalMSec / 1000;

		long ss = btw % 60;
		btw = btw / 60;

		long mm = btw % 60;
		btw = btw / 60;

		formatValueDate = ((mm > 9) ? "" : "0") + mm + ":"
				+ ((ss > 9) ? "" : "0") + ss;

		if (btw != 0) {
			long hh = btw % 24;
			btw = btw / 24;
			formatValueDate = ((hh > 9) ? "" : "0") + hh + ":"
					+ formatValueDate;

			if (btw != 0) {
				formatValueDate = btw + " сут. " + formatValueDate;
			}
		}
		return formatValueDate;
	}

	/**
	 * Метод
	 */
	public static String getDateFormatString(Date fromDate) {
		return getDateFormatString(fromDate, DATE_FORMAT_DEFAULT);
	}

	/**
	 * Метод
	 */
	public static String getDateFormatString(Date dat, String format) {
		if (dat == null) {
			return null;
		}

		if (format == null) {
			format = DATE_FORMAT_DEFAULT;
		}

		return getSimpleDateFormat(format).format(dat.getTime());
	}

	/**
	 * Метод
	 */
	public static String getDateReFormatString(String date, String format1,
			String format2) throws ParseException {
		return getDateFormatString(getDateFormatString(date, format1), format2);
	}

	/**
	 * Метод
	 */
	public static Date getDateFormatString(String sDate) throws ParseException {
		return DateUtils.getDateFormatString(sDate, null);
	}

	/**
	 * Метод
	 */
	public static Date getDateFormatString(String sDate, String format)
			throws ParseException {
		if (sDate == null || sDate.isEmpty()) {
			return null;
		}

		if (format == null) {
			format = DATE_FORMAT_DEFAULT;
		}

		format = format.substring(0, sDate.length() - 1);

		try {
			return getSimpleDateFormat(format).parse(sDate);
		} catch (ParseException e) {
			LOG.error("DateUtils.getDateFormatString dateFormat.parse('"
					+ sDate + "' : '" + format + "')", e);
			throw new ParseException(
					"DateUtils.getDateFormatString dateFormat.parse(" + sDate
							+ " : " + format + "). " + e.getMessage(),
					e.getErrorOffset());
		}
	}

	/**
	 * Метод
	 */
	public static SimpleDateFormat getSimpleDateFormat(String format) {
		if (format == null || DATE_FORMAT_DEFAULT.equals(format)) {
			return PATERN_DATE_FORMAT_DEFAULT.get();
		}

		return new SimpleDateFormat(format);
	}

	/**
	 * <p>
	 * Create a new XMLGregorianCalendar by parsing the String as a lexical
	 * representation.
	 * </p>
	 * <p>
	 * Parsing the lexical string representation is defined in <a
	 * href="http://www.w3.org/TR/xmlschema-2/#dateTime-order">XML Schema 1.0
	 * Part 2, Section 3.2.[7-14].1, <em>Lexical Representation</em>.</a>
	 * </p>
	 * <p>
	 * The string representation may not have any leading and trailing
	 * whitespaces.
	 * </p>
	 * <p>
	 * The parsing is done field by field so that the following holds for any
	 * lexically correct String x:
	 * </p>
	 * 
	 * <pre>
	 * newXMLGregorianCalendar(x).toXMLFormat().equals(x)
	 * </pre>
	 * <p>
	 * Except for the noted lexical/canonical representation mismatches listed
	 * in <a href="http://www.w3.org/2001/05/xmlschema-errata#e2-45"> XML Schema
	 * 1.0 errata, Section 3.2.7.2</a>.
	 * </p>
	 * 
	 * @param sDate
	 * @return Date created from the <code>lexicalRepresentation</code>.
	 * @throws DatatypeConfigurationException
	 */
	public static Date getDateFomXMLGregorianCalendarString(String sDate)
			throws DatatypeConfigurationException {
		XMLGregorianCalendar ret = DATA_TYPE_FACTORY_INSTANCE
				.newXMLGregorianCalendar(sDate);
		ret.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		return ret.toGregorianCalendar().getTime();
	}

	/**
	 * Получение {@link XMLGregorianCalendar} без временной зоны из даты
	 * 
	 * @param aDate
	 *            {@link Date}
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date aDate)
			throws DatatypeConfigurationException {

		return getXMLGregorianCalendar(aDate, false);
	}

	/**
	 * Получение {@link XMLGregorianCalendar} из даты
	 * 
	 * @param aDate
	 *            {@link Date}
	 * @param aWithTimeZone
	 *            учитывать временную зону
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendar(Date aDate,
			boolean aWithTimeZone) throws DatatypeConfigurationException {
		if (aDate == null) {
			return null;
		}

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(aDate);

		XMLGregorianCalendar ret = DATA_TYPE_FACTORY_INSTANCE
				.newXMLGregorianCalendar(c);

		if (!aWithTimeZone) {
			ret.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
		return ret;
	}

	/**
	 * Получение {@link XMLGregorianCalendar} без временной зоны из строкового
	 * представления даты в XML формате
	 * 
	 * @param aXmlDateString
	 *            строковое представление даты в XML формате
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendarByString(
			String aXmlDateString) throws DatatypeConfigurationException {

		return getXMLGregorianCalendarByString(aXmlDateString, false);
	}

	/**
	 * Получение {@link XMLGregorianCalendar} из строкового представления даты в
	 * XML формате
	 * 
	 * @param aXmlDateString
	 *            строковое представление даты в XML формате
	 * @param aWithTimeZone
	 *            учитывать временную зону
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendarByString(
			String aXmlDateString, boolean aWithTimeZone)
			throws DatatypeConfigurationException {
		if (aXmlDateString == null || aXmlDateString.isEmpty()) {
			return null;
		}

		XMLGregorianCalendar ret = DATA_TYPE_FACTORY_INSTANCE
				.newXMLGregorianCalendar(aXmlDateString);

		if (!aWithTimeZone) {
			ret.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
		return ret;
	}

	/**
	 * Получение {@link XMLGregorianCalendar} из строкового представления даты и
	 * шаблона даты
	 * 
	 * @param aDateString
	 *            строковое представление даты
	 * @param aPatern
	 *            шаблон даты. Если <b>null</b> то будет использоваться формат
	 *            {@value #DATE_FORMAT_DEFAULT}
	 * @param aWithTimeZone
	 *            указывать временную зону
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 * @throws ParseException
	 */
	public static XMLGregorianCalendar getXMLGregorianCalendarByDateString(
			String aDateString, String aPatern, boolean aWithTimeZone)
			throws ParseException {

		if (aDateString == null || aDateString.isEmpty()) {
			return null;
		}

		if (aPatern == null) {
			aPatern = DATE_FORMAT_DEFAULT;
		}

		GregorianCalendar c = new GregorianCalendar();
		c.setTime(getDateFormatString(aDateString, aPatern));

		XMLGregorianCalendar ret = DATA_TYPE_FACTORY_INSTANCE
				.newXMLGregorianCalendar(c);

		if (!aWithTimeZone) {
			ret.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
		}
		return ret;
	}

	/**
	 * Текущая дата в формате {@link XMLGregorianCalendar} без временной зоны
	 * 
	 * @return {@link XMLGregorianCalendar}
	 * @throws DatatypeConfigurationException
	 */
	public static XMLGregorianCalendar getCurrentXMLGregorianCalendar() {
		try {
			XMLGregorianCalendar ret = getXMLGregorianCalendar(new Date());
			ret.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			return ret;
		} catch (DatatypeConfigurationException e) {
			// Такого быть не должно
			LOG.error("", e);
		}
		return null;
	}

	/**
	 * @param date
	 * @return Дата
	 * @throws DatatypeConfigurationException
	 */
	public static Date getDateFomXMLGregorianCalendar(XMLGregorianCalendar date) {
		if (date == null) {
			return null;
		}
		return date.toGregorianCalendar().getTime();
	}

	public static String getDateFormatString(long msec,
			SimpleDateFormat dateFormat) {
		return dateFormat.format(msec);
	}

}
