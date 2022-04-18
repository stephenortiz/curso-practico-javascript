package com.pollosbucanero.wsclient.modelo;

import java.util.Date;

public class InstrumentoPagoAutorizado {
  
  private Integer id;
  private Sucursal sucursal;
  private InstrumentoPago instrumento;
  private Date fechaInicial;
  private Date fechaFinal;
  private String unidadNegocio;
  private String cuentaObjetivo;
  private String cuentaAuxiliar;
  
  public InstrumentoPagoAutorizado() {
    instrumento = new InstrumentoPago();
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Sucursal getSucursal() {
    return sucursal;
  }

  public void setSucursal(Sucursal sucursal) {
    this.sucursal = sucursal;
  }

  public InstrumentoPago getInstrumento() {
    return instrumento;
  }

  public void setInstrumento(InstrumentoPago instrumento) {
    this.instrumento = instrumento;
  }

  public Date getFechaInicial() {
    return fechaInicial;
  }

  public void setFechaInicial(Date fechaInicial) {
    this.fechaInicial = fechaInicial;
  }

  public Date getFechaFinal() {
    return fechaFinal;
  }

  public void setFechaFinal(Date fechaFinal) {
    this.fechaFinal = fechaFinal;
  }

  public String getUnidadNegocio() {
    return unidadNegocio;
  }

  public void setUnidadNegocio(String unidadNegocio) {
    this.unidadNegocio = unidadNegocio;
  }

  public String getCuentaObjetivo() {
    return cuentaObjetivo;
  }

  public void setCuentaObjetivo(String cuentaObjetivo) {
    this.cuentaObjetivo = cuentaObjetivo;
  }

  public String getCuentaAuxiliar() {
    return cuentaAuxiliar;
  }

  public void setCuentaAuxiliar(String cuentaAuxiliar) {
    this.cuentaAuxiliar = cuentaAuxiliar;
  }

}
