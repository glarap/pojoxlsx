package com.github.sebastian4j.pojoxlsx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.poi.ss.usermodel.IndexedColors;

/**
 * propiedades para las celdar del cuerpo del archivo.
 *
 * @author Sebastián Ávila A.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XlsxCellBody {
  /**
   * indica si el contenido del elemento tiene que ser representado como una fecha.
   *
   * @return si es true se transformará a un string representando la fecha
   */
  public boolean epochLong() default false;
  /**
   * si se indica se utiliza para el formato de salida de la fecha cuando epochLong es true.
   *
   * @return el formato definido para la fecha cuando epochLong es utilizado, en otro caso se ignora
   */
  public String dateFormat() default "";
  /**
   * id de zona, ejemplo: America/Santiago. Es UTC por defecto, se evalúa cuando epochLong es true.
   *
   * @return el id de la zona para el formato de la fecha cuando es epochLong true
   */
  public String zoneId() default "UTC";

  /**
   * define el tamaño de la fuente del body de la tabla.
   *
   * @return tamaño de la fuente
   */
  public short fontSize() default 10;

  /**
   * define el color de la fuente según la definicion en {@link IndexedColors}.
   *
   * @return tamaño de la fuente
   */
  public short fontColor() default 0;

  /**
   * indica si es necesario formatear un numero.
   *
   * @return true is se tiene que formatear el número
   */
  public boolean formatNumber() default false;
}
