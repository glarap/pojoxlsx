package com.github.sebastian4j.pojoxlsx;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * test para la clase {@link PojoXlsx}.
 *
 * @author Sebastián Ávila A.
 */
public class PojoXlsxTest {

  @Test
  void obtengoDatosXlsx() throws IOException {
    System.setProperty("px-date-format", "dd-yyyy");
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    final List<Persona> personas = new ArrayList<>();
    Date date = new Date();
    date.setDate(date.getDate() + 1);
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    personas.add(
        new Persona(
            "sebastian",
            4,
            false,
            cal,
            new Date(),
            1293921.309,
            LocalDateTime.now(),
            LocalDate.now(),
            1580214962000L));
    personas.add(
        new Persona(
            "elfo",
            6,
            true,
            Calendar.getInstance(),
            new Date(),
            32.3,
            LocalDateTime.now(),
            LocalDate.now(),
            1580214962000L));
    final Persona nonulo =
        new Persona(
            "nulo",
            0,
            true,
            Calendar.getInstance(),
            new Date(),
            32.3,
            LocalDateTime.now(),
            LocalDate.now(),
            1580214962000L);

    final Persona resumen = new Persona();
    resumen.setEdad(10);

    nonulo.setNulo(1d);
    personas.add(nonulo);
    personas.add(resumen);
    System.out.println("personas: " + personas.size());

    Assertions.assertThatCode(
            () -> {
              PojoXlsx.transform(
                  personas,
                  "reporte personas",
                  baos,
                  ZoneId.of("America/Santiago"),
                  Optional.empty()
              );
            })
        .doesNotThrowAnyException();
    final File f = new File("salida.xlsx");
    try (FileOutputStream fos = new FileOutputStream(f)) {
      fos.write(baos.toByteArray());
    }
  }
}
