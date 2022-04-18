package com.pollosbucanero.wsclient.modelo;

public class InstrumentoPago {

  private String codigo;
  private String descripcion;

  public InstrumentoPago() {
    descripcion = "";
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }
  
}