package com.github.sebastian4j.pojoxlsx.utils;

import com.github.sebastian4j.pojoxlsx.XlsxCellBody;
import java.util.Optional;

/**
 * métodos útiles para las anotaciones
 *
 * @author Sebastián Ávila A.
 */
public final class AnnotationUtil {
	private AnnotationUtil() {}
	
	public static Optional<String> dateFormat(final XlsxCellBody ann) {
		Optional<String> format = Optional.empty();
		if (ann != null && ann.dateFormat() != null && !ann.dateFormat().isEmpty()) {
			format = Optional.of(ann.dateFormat());
		}
		return format;
	}
	/**
	 * obtiene el tamaño de la fuente en la propiedad <i>fontSize</i>, por defecto es 10.
	 * 
	 * @param ann donde buscar la propiedad
	 * @return el tamaño de la fuente, predefinido 10
	 */
	public static short fontSize(final XlsxCellBody ann) {
		short fz = 10;
		if (ann != null) {
			fz = ann.fontSize();
		}
		return fz;
	}
	
	/**
	 * obtiene el color de la fuente indicado en la propiedad <i>fontColor.</i>
	 * 
	 * @param ann donde buscar la propiedad
	 * @return color de la fuente, predefinido 0 (negro)
	 */
	public static short fontColor(final XlsxCellBody ann) {
		short fz = 0;
		if (ann != null) {
			fz = ann.fontColor();
		}
		return fz;
	}
	
	/**
	 * obtiene si es necesario formatear el número en la propiedad <i>formatNumber</i>.
	 * 
	 * @param ann donde buscar la propiedad 
	 * @return 
	 */
	public static boolean formatNumber(final XlsxCellBody ann) {
		boolean formatear = false;
		if (ann != null) {
			formatear = ann.formatNumber();
		}
		return formatear;
	}
}
