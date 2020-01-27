package com.sebastian.pojoxlsx;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * test para la clase {@link PojoXlsx}.
 * 
 * @author Sebastián Ávila A.
 *
 */
public class PojoXlsxTest {



  @Test
  void obtengoDatosXlsx() throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final List<Persona> personas = new ArrayList<>();
    Date date = new Date();
    date.setDate(date.getDate() + 1);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    personas.add(new Persona("sebastian", 4, false, cal, new Date(), 21.3, LocalDateTime.now(),
        LocalDate.now()));
    personas.add(new Persona("elfo", 6, true, Calendar.getInstance(), new Date(), 32.3,
        LocalDateTime.now(), LocalDate.now()));
    Assertions.assertThatCode(() -> {
      PojoXlsx.transform(personas, "reporte personas", baos);
    }).doesNotThrowAnyException();
    final File f = new File("salida.xlsx");
    try (FileOutputStream fos = new FileOutputStream(f)) {
      fos.write(baos.toByteArray());
    }
  }
}
