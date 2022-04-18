package com.pollosbucanero.wsclient.modelo;

import java.math.BigDecimal;

public class Persona {

  private Integer codigo;
  private String tipoBusqueda;
  private String nombre;
  private String condicionPago;
  private BigDecimal desctoPieFactura;
  private String nroDocumento;
  private String direccion;
  private String telefono;
  private String categoria2;
  private String zona;
  private String categoria11;
  private Sucursal sucursal;
  private String categoria15;

  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getTipoBusqueda() {
    return tipoBusqueda;
  }

  public void setTipoBusqueda(String tipoBusqueda) {
    this.tipoBusqueda = tipoBusqueda;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getCondicionPago() {
    return condicionPago;
  }

  public void setCondicionPago(String condicionPago) {
    this.condicionPago = condicionPago;
  }

  public BigDecimal getDesctoPieFactura() {
    return desctoPieFactura;
  }

  public void setDesctoPieFactura(BigDecimal desctoPieFactura) {
    this.desctoPieFactura = desctoPieFactura;
  }

  public String getNroDocumento() {
    return nroDocumento;
  }

  public void setNroDocumento(String nroDocumento) {
    this.nroDocumento = nroDocumento;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getCategoria2() {
    return categoria2;
  }

  public void setCategoria2(String categoria2) {
    this.categoria2 = categoria2;
  }

  public String getZona() {
    return zona;
  }

  public void setZona(String zona) {
    this.zona = zona;
  }

  public String getCategoria11() {
    return categoria11;
  }

  public void setCategoria11(String categoria11) {
    this.categoria11 = categoria11;
  }

  public Sucursal getSucursal() {
    return sucursal;
  }

  public void setSucursal(Sucursal sucursal) {
    this.sucursal = sucursal;
  }

  public String getCategoria15() {
    return categoria15;
  }

  public void setCategoria15(String categoria15) {
    this.categoria15 = categoria15;
  }

}
