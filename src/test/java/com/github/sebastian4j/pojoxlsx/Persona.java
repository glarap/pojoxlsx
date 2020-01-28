package com.github.sebastian4j.pojoxlsx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

class Persona {
  @XlsxCellHeader(name = "Nombre")
  private String nombre;
  @XlsxCellHeader(name = "Edad")
  private int edad;
  @XlsxCellHeader(name = "Vivo")
  private boolean vivo;
  @XlsxCellHeader(name = "Nacimiento")
  private Calendar nacimiento;
  @XlsxCellHeader(name = "Fallecimiento")
  private Date fallecimiento;
  @XlsxCellHeader(name = "Peso Actual")
  private double peso;
  @XlsxCellHeader(name = "Despertó a las")
  private LocalDateTime despierta;
  @XlsxCellHeader(name = "Se Acostó a las", bold = false)
  private LocalDate acuesta;
	@XlsxCellBody(epochLong = true)
	private double fecha;
  @XlsxCellBody(epochLong = true, zoneId = "America/Santiago", dateFormat = "::dd::MM::yyyy:: HH<>mm<>ss")
	@XlsxCellHeader(name = "Fecha Long")
	private double fechaLong;

  public Persona(String nombre, int edad, boolean vivo, Calendar nacimiento, Date fallecimiento,
      double peso, LocalDateTime despierta, LocalDate acuesta, final long fecha) {
    super();
    this.nombre = nombre;
    this.edad = edad;
    this.vivo = vivo;
    this.nacimiento = nacimiento;
    this.fallecimiento = fallecimiento;
    this.peso = peso;
    this.despierta = despierta;
    this.acuesta = acuesta;
		this.fecha = fecha;
		this.fechaLong = fecha;
  }

	public double getFechaLong() {
		return fechaLong;
	}

	public void setFechaLong(double fechaLong) {
		this.fechaLong = fechaLong;
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
  public void setEdad(int edad) {
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
  public void setVivo(boolean vivo) {
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
  public void setPeso(double peso) {
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

	public void setFecha(double fecha) {
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
