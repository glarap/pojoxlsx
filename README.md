### Pojo XLXS

librería para convertir listado de Pojos a un xlsx. Utiliza una lista de elementos que son transformados en forma secuencial en una sola hoja xlsx.


### Como funciona?
* crear una pojo, por ejemplo:

```
class Persona {
  private String nombre;
  private int edad;
  private boolean vivo;
  private Calendar nacimiento;
  private Date fallecimiento;
  private double peso;
  private LocalDateTime despierta;
  private LocalDate acuesta;
  
  // getter y setter
}
```
* Usar transient en un campo para omitirlo
* Usar un List para juntar instancias de Persona:
```
final List<Persona> personas = new ArrayList<>();
personas.add(persona1);
personas.add(persona2);
...
```
 * Utilizar la clase **PojoXlsx**:
 ```
final ByteArrayOutputStream baos = new ByteArrayOutputStream();

PojoXlsx.transform(personas, "título de la hoja", baos);

try (FileOutputStream fos = new FileOutputStream(f)) {
      fos.write(baos.toByteArray());
}
 ```
### Personalización cabeceras
Utilizando la anotación @XlsxCellHeader es posible modificar el comportamiento de la generación de las cabeceras de la hoja generada:
```
@XlsxCellHeader(name = "Se Acostó a las", bold = false)
private LocalDate acuesta;
```
* name será el nombre de la columna (por defecto es el nombre del campo)
* bold indica si se ocupa la letra en negrita (true por defecto)

### Personalización body
los elementos del body se pueden personalizar utilizando la anotación XlsxCellBody:
* epochLong: si es true se considera el valor del campo como los milisegundos de la fecha (unix timestamp).
* zoneId: si epochLong es true se puede indicar la zona en la cual tiene que ser convertido, por defecto es UTC (ZonedDateTime).
* dateFormat: si epochLong es true se puede indicar el formato que se utilizará para la salida del ZonedDateTime que es internamente utilizado.

### Dependencias
* org.apache.poi