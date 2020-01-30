package com.github.sebastian4j.pojoxlsx.utils;

import com.github.sebastian4j.pojoxlsx.XlsxCellBody;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * metodos utiles para formatear fechas.
 *
 * @author Sebastián Ávila A.
 */
public final class DateTimeUtil {
	/** px por pojo-xlax y no tener conflictos. */
  private static final String DATE_FORMAT = "px-date-format";

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
    return hasFormat(ann)
        ? zdt.format(DateTimeFormatter.ofPattern(ann.dateFormat()))
        : zdt.toString();
  }

  /**
   * obtiene si se indicó un formato para la fecha
   *
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

  public static String format(final LocalDate ld, final String pattern) {
    return ld.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String format(final LocalDateTime ld, final String pattern) {
    return ld.format(DateTimeFormatter.ofPattern(pattern));
  }

  public static String format(final LocalDate ld) {
    final String formatProperty = System.getProperty(DATE_FORMAT);
    String formateado;
    if (formatProperty != null) {
      formateado = ld.format(DateTimeFormatter.ofPattern(formatProperty));
    } else {
      formateado = ld.format(DateTimeFormatter.ISO_DATE);
    }
    return formateado;
  }

  public static String format(final LocalDateTime ld) {
    final String formatProperty = System.getProperty(DATE_FORMAT);
    String formateado;
    if (formatProperty != null) {
      formateado = ld.format(DateTimeFormatter.ofPattern(formatProperty));
    } else {
      formateado = ld.format(DateTimeFormatter.ISO_DATE_TIME);
    }
    return formateado;
  }

  public static String format(Date date, final String pattern) {
    String formateado = "";
    if (pattern != null && date != null) {
      SimpleDateFormat sdf = new SimpleDateFormat(pattern);
      formateado = sdf.format(date);
    }
    return formateado;
  }

  public static String format(Date date) {
    final String formatProperty = System.getProperty(DATE_FORMAT);
    String formateado;
    if (formatProperty != null) {
      formateado = format(date, formatProperty);
    } else {
      formateado =
          DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, Locale.getDefault())
              .format(date);
    }
    return formateado;
  }
}
