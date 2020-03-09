package com.github.sebastian4j.pojoxlsx;

import com.github.sebastian4j.pojoxlsx.utils.PojoXlsxUtil;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * realiza la trasformación de un listado de Pojos a xlsx.
 *
 * @author Sebastián Ávila A.
 */
public final class PojoXlsx {

  private PojoXlsx() {}

  /**
   * realiza la conversión de un listado de objetos a un archivo xlsx.
   *
   * @param data datos que tienen que ser convertidos
   * @param title titulo de la hoja
   * @param os donde se escribe el reporte generado
   * @throws IOException error al crear el archivo de salida
   * @throws IllegalArgumentException la data es null o está vacio
   */
  public static <T> void transform(List<T> data, final String title, final OutputStream os)
      throws IOException {
    transform(data, title, os, null);
  }

  /**
   * realiza la conversión de un listado de objetos a un archivo xlsx.
   *
   * @param data datos que tienen que ser convertidos
   * @param title titulo de la hoja
   * @param os donde se escribe el reporte generado
   * @param fs estilos que se pueden aplicar a columnas
   * @throws IOException error al crear el archivo de salida
   * @throws IllegalArgumentException la data es null o está vacio
   */
  public static <T> void transform(
      List<T> data, final String title, final OutputStream os, final XlsxCellStyleCallback fs)
      throws IOException {
    if (data == null || data.isEmpty()) {
      throw new IllegalArgumentException("sin datos para procesar");
    }
    int filas = 0;
    try (final XSSFWorkbook wb = new XSSFWorkbook(); ) {
      final XSSFSheet hoja = wb.createSheet(title);
      boolean conCabeceras = false;
      for (T d : data) {
        int columna = 0;
        XSSFRow row = hoja.createRow(filas++);
        Field[] campos = d.getClass().getDeclaredFields();
        if (!conCabeceras) {
          PojoXlsxUtil.addColumnTitle(row, campos, wb);
          row = hoja.createRow(filas++);
          conCabeceras = true;
        }
        for (Field c : campos) {
          if (PojoXlsxUtil.addValue(columna, filas, row, d, c, wb, fs)) {
            columna++;
          }
        }
      }
      wb.write(os);
    }
    System.gc();
  }
}
