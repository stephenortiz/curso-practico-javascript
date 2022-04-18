package com.pollosbucanero.wsclient.modelo;

import java.math.BigDecimal;

public class Impuesto {

  private String codigo;
  private String descripcion;
  private String tipoValorFactor;
  private BigDecimal factor;

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

  public String getTipoValorFactor() {
    return tipoValorFactor;
  }

  public void setTipoValorFactor(String tipoValorFactor) {
    this.tipoValorFactor = tipoValorFactor;
  }

  public BigDecimal getFactor() {
    return factor;
  }

  public void setFactor(BigDecimal factor) {
    this.factor = factor;
  }

}
