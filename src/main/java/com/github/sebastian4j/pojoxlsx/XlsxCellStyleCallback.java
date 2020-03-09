package com.github.sebastian4j.pojoxlsx;

import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 * permite realiar operaciones especificas para estilo de celdas del documento.
 *
 * @author Sebastián Ávila A.
 */
@FunctionalInterface
public interface XlsxCellStyleCallback {
  /**
	 * aplicar estilos especificos según indices o valores.
	 * 
   * @param col indice de la columna del documento que está en proceso (comienza desde 0)
	 * @param row indice de la fila que está en proceso
   * @param font fuente a la que se le aplican los estulos
   * @param val valor que está siendo procesado (valores no nulos)
   */
  boolean apply(final int col, final int row, final XSSFFont font, final Object val);
}
