package ru.wg.web.controllers.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

public class SheetHelper {

	private Sheet _sheet;
	private Map<Integer, Row> _rows = new HashMap<Integer, Row>();

	public SheetHelper(Sheet aSheet) {
		_sheet = aSheet;
	}

	/**
	 * Добавление ячейки в книгу
	 * 
	 * @param aValue
	 *            значение
	 * @param aRowNum
	 *            строка
	 * @param aColumn
	 *            столбец
	 * @param aExpandRowNum
	 *            на сколько объединить строк
	 * @param aExpandColumn
	 *            на сколько объединить столбец
	 * @param aStyle
	 *            стиль
	 */
	public Cell addCell(Object aValue, int aRowNum, int aColumn,
			int aExpandRowNum, int aExpandColumn, CellStyle aStyle) {
		Row row = getRow(aRowNum);
		Cell cell = row.createCell(aColumn);

		cell.setCellValue(String.valueOf(aValue));

		if (aStyle != null) {
			cell.setCellStyle(aStyle);
		}

		if (aExpandRowNum > 0 || aExpandColumn > 0) {
			if (aStyle != null) {
				for (int rowI = aRowNum; rowI <= aRowNum + aExpandRowNum; rowI++) {
					for (int colI = aColumn; colI <= aColumn + aExpandColumn; colI++) {
						if (!(rowI == aRowNum && colI == aColumn)) {
							addCell("", rowI, colI, aStyle);
						}
					}
				}

			}

			_sheet.addMergedRegion(new CellRangeAddress(aRowNum, aRowNum
					+ aExpandRowNum, aColumn, aColumn + aExpandColumn));
		}
		return cell;
	}

	/**
	 * Добавление ячейки в книгу
	 * 
	 * @param aValue
	 *            значение
	 * @param aRowNum
	 *            строка
	 * @param aColumn
	 *            столбец
	 * @param aStyle
	 *            стиль
	 */
	public Cell addCell(Object aValue, int aRowNum, int aColumn,
			CellStyle aStyle) {

		return addCell(aValue, aRowNum, aColumn, 0, 0, aStyle);
	}

	/**
	 * @return the _row
	 */
	public Row getRow(int aRowNum) {
		Row row = _rows.get(aRowNum);

		if (row == null) {
			row = _sheet.createRow(aRowNum);
			_rows.put(aRowNum, row);
		}

		return row;
	}

	/**
	 * Установка ширины столбца
	 * 
	 * @param aColumn
	 *            столбец
	 * @param aWidth
	 *            ширина в символах
	 * @see Sheet#setColumnWidth
	 */
	public void setColumnWidth(int aColumn, int aWidth) {
		_sheet.setColumnWidth(aColumn, aWidth * 256);
	}

	/**
	 * @return the _sheet
	 */
	public Sheet getSheet() {
		return _sheet;
	}

	public void createFreezePane(int colSplit, int rowSplit) {
		_sheet.createFreezePane(colSplit, rowSplit);

	}

}
