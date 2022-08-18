package com.github.sebastian4j.pojoxlsx;

import com.github.sebastian4j.pojoxlsx.utils.PojoXlsxUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.lang.reflect.Field;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

/**
 * realiza la trasformación de un listado de Pojos a xlsx.
 *
 * @author Sebastián Ávila A.
 */
public final class PojoXlsx {

    private PojoXlsx() {
    }

    /**
     * realiza la conversión de un listado de objetos a un archivo xlsx.
     *
     * @param data  datos que tienen que ser convertidos
     * @param title titulo de la hoja
     * @param os    donde se escribe el reporte generado
     * @throws IOException error al crear el archivo de salida
     */
    public static <T> void transform(List<T> data, final String title, final OutputStream os, final ZoneId zoneId)
            throws IOException {
        transform(data, title, os, null, zoneId, Optional.empty());
    }

    public static <T> void transform(List<T> data, final String title, final OutputStream os) throws IOException {
        transform(data, title, os, null, null, Optional.empty());
    }

    public static <T> void transform(
            List<T> data, final String title, final OutputStream os, final XlsxCellStyleCallback fs)
            throws IOException {
        transform(data, title, os, fs, null, Optional.empty());
    }

    /**
     * realiza la conversión de un listado de objetos a un archivo xlsx.
     *
     * @param data  datos que tienen que ser convertidos
     * @param title titulo de la hoja
     * @param os    donde se escribe el reporte generado
     * @param fs    estilos que se pueden aplicar a columnas
     * @throws IOException              error al crear el archivo de salida
     * @throws IllegalArgumentException la data es null o está vacio
     */
    public static <T> void transform(
            List<T> data, final String title, final OutputStream os, final XlsxCellStyleCallback fs,
            final ZoneId zoneId, final Optional<ImageSheet> image)
            throws IOException {
        if (data == null) {
            throw new IllegalArgumentException("sin datos para procesar");
        }


        int filas = 0;
        try (final XSSFWorkbook wb = new XSSFWorkbook();) {
            final XSSFSheet hoja = wb.createSheet(title);
            boolean conCabeceras = false;
            if (!data.isEmpty()) {
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
                        if (PojoXlsxUtil.addValue(columna, filas, row, d, c, wb, fs, zoneId)) {
                            columna++;
                        }
                    }
                }
            } else {
                XSSFRow row = hoja.createRow(0);
                final XSSFCell cell = row.createCell(0);
                cell.setCellValue("No se encontraron datos para los criterios seleccionados");

            }
                if (image.isPresent()) {
                    addImage(image.get(), wb);
                }
                wb.write(os);
            }
            System.gc();
        }
        private static void addImage ( final ImageSheet image, final XSSFWorkbook wb) throws IOException {
            final XSSFSheet hoja2 = wb.createSheet(image.getSheetName());
            try (InputStream is = image.getImage()) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[0xFFFF];
                for (int len = is.read(buffer); len != -1; len = is.read(buffer)) {
                    baos.write(buffer, 0, len);
                }
                byte[] bytes = baos.toByteArray();
                int imageid = wb.addPicture(bytes, Workbook.PICTURE_TYPE_PNG);
                XSSFDrawing drawing = hoja2.createDrawingPatriarch();
                XSSFClientAnchor ironManAnchor = new XSSFClientAnchor();
                ironManAnchor.setCol1(image.getCol1());
                ironManAnchor.setCol2(image.getCol2());
                ironManAnchor.setRow1(image.getRow1());
                ironManAnchor.setRow2(image.getRow2());
                drawing.createPicture(ironManAnchor, imageid);
            }
        }
    }
