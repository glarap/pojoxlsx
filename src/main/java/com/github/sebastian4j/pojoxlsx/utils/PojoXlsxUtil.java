package com.github.sebastian4j.pojoxlsx.utils;

import com.github.sebastian4j.pojoxlsx.XlsxCellBody;
import com.github.sebastian4j.pojoxlsx.XlsxCellHeader;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import org.apache.poi.ss.usermodel.CellStyle;
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
 */
public final class PojoXlsxUtil {
  private PojoXlsxUtil() {}

  private static boolean omitir(final Field field) {
    return Modifier.isTransient(field.getModifiers());
  }

  private static void addDate(
      final Calendar calendar, final Optional<String> formato, final XSSFCell cell) {
    final Date cal = calendar.getTime();
    if (formato.isPresent()) {
      cell.setCellValue(DateTimeUtil.format(cal, formato.get()));
    } else {
      cell.setCellValue(DateTimeUtil.format(cal));
    }
  }

  private static void addDate(
      final Date date, final Optional<String> formato, final XSSFCell cell) {
    if (formato.isPresent()) {
      cell.setCellValue(DateTimeUtil.format(date, formato.get()));
    } else {
      cell.setCellValue(DateTimeUtil.format(date));
    }
  }

  private static void addDate(
      final LocalDate ld, final Optional<String> formato, final XSSFCell cell) {
    if (formato.isPresent()) {
      cell.setCellValue(DateTimeUtil.format(ld, formato.get()));
    } else {
      cell.setCellValue(DateTimeUtil.format(ld));
    }
  }

  private static void addDate(
      final LocalDateTime ld, final Optional<String> formato, final XSSFCell cell) {
    if (formato.isPresent()) {
      cell.setCellValue(DateTimeUtil.format(ld, formato.get()));
    } else {
      cell.setCellValue(DateTimeUtil.format(ld));
    }
  }
  /**
   * agrega un campo valor a la columna indicada.
   *
   * @param column indice de la columna actual
   * @param row fila en la que se agrega la columna
   * @param element elemento que se tiene que evaluar para obtener el valor
   * @param field campo del elemento que se quiere agregar
   * @param wb contenedor de las celdas
   * @throws IllegalArgumentException cuando el campo del elemento no se pudo agregar
   */
  public static <T> boolean addValue(
      final int column,
      final XSSFRow row,
      final T element,
      final Field field,
      final XSSFWorkbook wb) {
    boolean agregado = false;
    if (!omitir(field)) {
      final XSSFCell cell = row.createCell(column);
      try {
        final XlsxCellBody ann = field.getAnnotation(XlsxCellBody.class);
        short fontSize = AnnotationUtil.fontSize(ann);
        short fontColor = AnnotationUtil.fontColor(ann);
        Optional<String> formato = AnnotationUtil.dateFormat(ann);
        field.setAccessible(true);
        final Object valor = field.get(element);
        if (valor != null) {
          if (DateTimeUtil.hasEpochLong(ann)) {
            cell.setCellValue(DateTimeUtil.format(valor, ann));
          } else if (valor instanceof String) {
            cell.setCellValue((String) valor);
          } else if (valor instanceof Integer) {
            generarNumero((Integer) valor, ann, cell);
          } else if (valor instanceof Boolean) {
            cell.setCellValue(((Boolean) valor).toString());
          } else if (valor instanceof Calendar) {
            dateFormat(cell, wb);
            addDate((Calendar) valor, formato, cell);
          } else if (valor instanceof Date) {
            dateFormat(cell, wb);
            addDate((Date) valor, formato, cell);
          } else if (valor instanceof Double) {
            generarNumero((Double) valor, ann, cell);
          } else if (valor instanceof LocalDateTime) {
            dateFormat(cell, wb);
            addDate((LocalDateTime) valor, formato, cell);
          } else if (valor instanceof LocalDate) {
            dateFormat(cell, wb);
            addDate((LocalDate) valor, formato, cell);
          } else if (valor instanceof RichTextString) {
            cell.setCellValue((RichTextString) valor);
          }
          final XSSFCellStyle fuente = wb.createCellStyle();
          final XSSFFont df = wb.createFont();
          normalFont(df, fontSize, fuente, fontColor);
          cell.setCellStyle(fuente);
        }
        agregado = true;
      } catch (Exception e) {
        throw new IllegalArgumentException("el campo indicado no puede ser obtenido", e);
      }
    }
    return agregado;
  }

  private static void generarNumero(
      final Double valor, final XlsxCellBody ann, final XSSFCell cell) {
    if (valor != null && AnnotationUtil.formatNumber(ann)) {
      cell.setCellValue(NumberFormat.getInstance().format(valor));
    } else {
      cell.setCellValue(valor);
    }
  }

  private static void generarNumero(
      final Integer valor, final XlsxCellBody ann, final XSSFCell cell) {
    generarNumero(Double.valueOf(valor), ann, cell);
  }

  /**
   * agrega los header a la hoja de xlsx utilizando los datos de {@link XlsxCellHeader}.
   *
   * <p>- por defecto los nombres de las columnas son los campos
   *
   * <p>- por defecto la letra del header está en negrita
   *
   * @param row fila donde se agrega el titulo
   * @param campos campos que se agregan como headers
   * @param wb origen xlsx
   */
  public static void addColumnTitle(
      final XSSFRow row, final Field[] campos, final XSSFWorkbook wb) {
    int columna = 0;
    for (Field c : campos) {
      if (!omitir(c)) {
        final XSSFCellStyle fuente = wb.createCellStyle();
        final XSSFFont df = wb.createFont();
        boolean bold = true;
        final XSSFCell cell = row.createCell(columna++);
        final XlsxCellHeader ann = c.getAnnotation(XlsxCellHeader.class);
        short fontSize = 10;
        short fontColor = 0;
        if (ann != null) {
          fontSize = ann.fontSize();
          fontColor = ann.fontColor();
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
        } else {
          cell.setCellValue(c.getName());
        }
        if (bold) {
          boldFont(df, fontSize, fuente, fontColor);
          cell.setCellStyle(fuente);
        } else {
          normalFont(df, fontSize, fuente, fontColor);
          cell.setCellStyle(fuente);
        }
      }
    }
  }

  private static void boldFont(
      final XSSFFont df, short fontSize, final XSSFCellStyle style, final short fontColor) {
    df.setFontHeightInPoints(fontSize);
    df.setFontName("Arial");
    df.setColor(fontColor);
    df.setBold(true);
    df.setItalic(false);
    style.setFont(df);
  }

  private static void normalFont(
      final XSSFFont df, short fontSize, final XSSFCellStyle style, final short fontColor) {
    df.setFontHeightInPoints(fontSize);
    df.setFontName("Arial");
    df.setColor(fontColor);
    df.setBold(false);
    df.setItalic(false);
    style.setFont(df);
  }

  private static void dateFormat(final XSSFCell cell, final XSSFWorkbook wb) {
    CellStyle cellStyle = wb.createCellStyle();
    cellStyle.setDataFormat((short) 14);
    cell.setCellStyle(cellStyle);
  }
}
