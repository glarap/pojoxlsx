package com.sebastian.pojoxlsx;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * método utiles para la creación de archivos xlsx.
 * 
 * @author Sebastián Ávila A.
 *
 */
final class PojoXlsxUtil {
  private PojoXlsxUtil() {}

  /**
   * agrega un campo valor a la columna indicada.
   * 
   * @param column indice de la columna actual
   * @param row fila en la que se agrega la columna
   * @param element elemento que se tiene que evaluar para obtener el valor
   * @param field campo del elemento que se quiere agregar
   * @throws IllegalArgumentException cuando el campo del elemento no se pudo agregar
   */
  static <T> void addValue(final int column, final XSSFRow row, final T element, final Field field,
      final XSSFWorkbook wb) {
    final XSSFCell cell = row.createCell(column);

    try {
      field.setAccessible(true);
      final Object valor = field.get(element);
      System.out.println(valor);
      if (valor instanceof String) {
        cell.setCellValue((String) valor);
      } else if (valor instanceof Integer) {
        cell.setCellValue((Integer) valor);
      } else if (valor instanceof Boolean) {
        cell.setCellValue(((Boolean) valor).toString());
      } else if (valor instanceof Calendar) {
        dateFormat(cell, wb);
        cell.setCellValue((Calendar) valor);
      } else if (valor instanceof Date) {
        dateFormat(cell, wb);
        cell.setCellValue((Date) valor);
      } else if (valor instanceof Double) {
        cell.setCellValue((Double) valor);
      } else if (valor instanceof LocalDateTime) {
        dateFormat(cell, wb);
        cell.setCellValue((LocalDateTime) valor);
      } else if (valor instanceof LocalDate) {
        cell.setCellValue((LocalDate) valor);
        dateFormat(cell, wb);
      } else if (valor instanceof RichTextString) {
        cell.setCellValue((RichTextString) valor);
      }
    } catch (Exception e) {
      throw new IllegalArgumentException("el campo indicado no puede ser obtenido");
    }
  }



  /**
   * agrega los header a la hoja de xlsx utilizando los datos de {@link XlsxCellHeader}.
   * 
   * - por defecto los nombres de las columnas son los campos
   * 
   * - por defecto la letra del header está en negrita
   * 
   * @param row fila donde se agrega el titulo
   * @param campos campos que se agregan como headers
   * @param wb origen xlsx
   */
  static void addColumnTitle(final XSSFRow row, final Field[] campos, final XSSFWorkbook wb) {
    int columna = 0;
    XSSFCellStyle fuenteNegrita = wb.createCellStyle();
    final XSSFFont df = wb.createFont();
    df.setFontHeightInPoints((short) 10);
    df.setFontName("Arial");
    df.setColor(IndexedColors.BLACK.getIndex());
    df.setBold(true);
    df.setItalic(false);
    fuenteNegrita.setFont(df);
    for (Field c : campos) {
      boolean bold = true;
      final XSSFCell cell = row.createCell(columna++);
      final XlsxCellHeader ann = c.getAnnotation(XlsxCellHeader.class);
      if (ann != null) {
        if (ann.bold()) {
          bold = true;
        } else {
          bold = false;
        }
        if (ann.name() != null && !ann.name().isEmpty()) {
          cell.setCellValue(ann.name());
        } else {
          cell.setCellValue(c.getName());
        }
      }
      if (bold) {
        cell.setCellStyle(fuenteNegrita);
      }
    }
  }

  private static final void dateFormat(final XSSFCell cell, final XSSFWorkbook wb) {
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat((short) 14);
    cell.setCellStyle(cellStyle);
  }
}
