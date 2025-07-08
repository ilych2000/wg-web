/**
 *
 */
package ru.wg.web.controllers.utils;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;

/**
 * @author Илья
 */
public class CellStyleBuilder {

    private CellStyle _cellStyle;

    /**
     *
     */
    public CellStyleBuilder(CellStyle aCellStyle) {
        _cellStyle = aCellStyle;
    }

    /**
     * @return the _cellStyle
     */
    public CellStyle get() {
        return _cellStyle;
    }

    public CellStyleBuilder toCenter() {
        _cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        _cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return this;
    }

    public CellStyleBuilder toHorizontalCenter() {
        _cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return this;
    }

    public CellStyleBuilder toVerticalCenter() {
        _cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        return this;
    }

    public CellStyleBuilder setBorder() {

        _cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        _cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
        _cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        _cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
        _cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        _cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
        _cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        _cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
        return this;
    }

    public CellStyleBuilder wrapText() {
        _cellStyle.setWrapText(true);
        return this;
    }

    public CellStyleBuilder rotateLeft() {
        _cellStyle.setRotation((short) 90);
        return this;
    }

    public CellStyleBuilder alignTop() {
        _cellStyle.setVerticalAlignment(CellStyle.VERTICAL_TOP);
        return this;
    }
}
