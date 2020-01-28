package com.sebastian.pojoxlsx.utils;

import com.sebastian.pojoxlsx.XlsxCellBody;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * metodos utiles para formatear fechas.
 *
 * @author Sebastián Ávila A.
 */
public final class DateTimeUtil {
  private DateTimeUtil() {}

  public static boolean hasEpochLong(final XlsxCellBody ann) {
    return ann != null && ann.epochLong();
  }

  public static String format(Object milisegundos, final XlsxCellBody ann) {
    double milis = 0;
    if (milisegundos.getClass() == Long.class) {
      milis = (Long) milisegundos;
    } else if (milisegundos.getClass() == Double.class) {
      milis = (Double) milisegundos;
    }
    final ZonedDateTime zdt =
        ZonedDateTime.ofInstant(Instant.ofEpochMilli((long) milis), zona(ann));
    return hasFormat(ann) ? zdt.format(DateTimeFormatter.ofPattern(ann.dateFormat())) : zdt.toString();
  }

	/**
	 * obtiene si se indicó un formato para la fecha
	 * @param ann anotación con el detalle del formato
	 * @return true si existe un formato indicado
	 */
  private static boolean hasFormat(final XlsxCellBody ann) {
    return ann.dateFormat() != null && !ann.dateFormat().isEmpty();
  }

	/**
	 * obtiene el id de la zona para el formato de la fecha, por defecto es UTC.
	 * 
	 * @param ann datos del formato del campo
	 * @return id de zona para ser utilizado
	 */
  private static ZoneId zona(final XlsxCellBody ann) {
    final String predefinido = ann.zoneId();
    ZoneId zona = ZoneId.of("UTC");
    if (predefinido != null && !predefinido.isEmpty()) {
      zona = ZoneId.of(predefinido);
    }
    return zona;
  }
}
