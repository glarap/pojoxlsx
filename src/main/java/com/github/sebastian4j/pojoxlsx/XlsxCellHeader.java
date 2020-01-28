package com.github.sebastian4j.pojoxlsx;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * propiedades para las celdar de cabeceras.
 *
 * @author Sebastián Ávila A.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface XlsxCellHeader {

  /**
   * nombre que se define a la celda (por defecto es el nombre del campo).
   *
   * @return nombre de la cabecera
   */
  public String name() default "";

  /**
   * indica si la celda queda bold.
   *
   * @return true si hay que dejar bold la cabecera
   */
  public boolean bold() default true;
}
