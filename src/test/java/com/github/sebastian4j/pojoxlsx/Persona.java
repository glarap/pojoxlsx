package com.github.sebastian4j.pojoxlsx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

class Persona {
	transient private int id;
  @XlsxCellHeader(name = "Nombre", fontSize = 20)
  private String nombre;
  @XlsxCellHeader(name = "Edad")
  private Integer edad;
  @XlsxCellHeader(name = "Vivo")
  private Boolean vivo;
  @XlsxCellHeader(name = "Nacimiento", fontColor = 7)
  private Calendar nacimiento;
  @XlsxCellHeader(name = "Fallecimiento", fontColor = 3)
	@XlsxCellBody(dateFormat = "dd-MM-yyyy HH:mm:ss")
  private Date fallecimiento;
  @XlsxCellHeader(name = "Peso Actual")
	@XlsxCellBody(fontColor = 10, formatNumber = true)
  private Double peso;
  @XlsxCellHeader(name = "Despertó a las")
  private LocalDateTime despierta;
  @XlsxCellHeader(name = "Se Acostó a las", bold = false)
  private LocalDate acuesta;
	@XlsxCellBody(epochLong = true)
	private Double fecha;
  @XlsxCellBody(epochLong = true, zoneId = "America/Santiago", dateFormat = "::dd::MM::yyyy:: HH<>mm<>ss", fontSize = 20)
	@XlsxCellHeader(name = "Fecha Long")
	private Double fechaLong;
	private Double nulo;

	public Persona() {}
	
  public Persona(String nombre, Integer edad, Boolean vivo, Calendar nacimiento, Date fallecimiento,
      Double peso, LocalDateTime despierta, LocalDate acuesta, final Long fecha) {
    super();
    this.nombre = nombre;
    this.edad = edad;
    this.vivo = vivo;
    this.nacimiento = nacimiento;
    this.fallecimiento = fallecimiento;
    this.peso = peso;
    this.despierta = despierta;
    this.acuesta = acuesta;
		this.fecha = Double.valueOf(fecha);
		this.fechaLong = Double.valueOf(fecha);
  }

	public double getFechaLong() {
		return fechaLong;
	}

	public void setFechaLong(Double fechaLong) {
		this.fechaLong = fechaLong;
	}

	public Double getNulo() {
		return nulo;
	}

	public void setNulo(Double nulo) {
		this.nulo = nulo;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

  /**
   * @return the nombre
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * @param nombre the nombre to set
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * @return the edad
   */
  public int getEdad() {
    return edad;
  }

  /**
   * @param edad the edad to set
   */
  public void setEdad(Integer edad) {
    this.edad = edad;
  }

  /**
   * @return the vivo
   */
  public boolean isVivo() {
    return vivo;
  }

  /**
   * @param vivo the vivo to set
   */
  public void setVivo(Boolean vivo) {
    this.vivo = vivo;
  }

  /**
   * @return the nacimiento
   */
  public Calendar getNacimiento() {
    return nacimiento;
  }

  /**
   * @param nacimiento the nacimiento to set
   */
  public void setNacimiento(Calendar nacimiento) {
    this.nacimiento = nacimiento;
  }

  /**
   * @return the fallecimiento
   */
  public Date getFallecimiento() {
    return fallecimiento;
  }

  /**
   * @param fallecimiento the fallecimiento to set
   */
  public void setFallecimiento(Date fallecimiento) {
    this.fallecimiento = fallecimiento;
  }

  /**
   * @return the peso
   */
  public double getPeso() {
    return peso;
  }

  /**
   * @param peso the peso to set
   */
  public void setPeso(Double peso) {
    this.peso = peso;
  }

  /**
   * @return the despierta
   */
  public LocalDateTime getDespierta() {
    return despierta;
  }

  /**
   * @param despierta the despierta to set
   */
  public void setDespierta(LocalDateTime despierta) {
    this.despierta = despierta;
  }

	public double getFecha() {
		return fecha;
	}

	public void setFecha(Double fecha) {
		this.fecha = fecha;
	}

  /**
   * @return the acuesta
   */
  public LocalDate getAcuesta() {
    return acuesta;
  }

  /**
   * @param acuesta the acuesta to set
   */
  public void setAcuesta(LocalDate acuesta) {
    this.acuesta = acuesta;
  }

}
