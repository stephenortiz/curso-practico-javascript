package com.pollosbucanero.wsclient.modelo;

import java.math.BigDecimal;

public class DetalleListaPrecios {
  
  private Long id;
  private Articulo articulo;
  private String unidadMedida;
  private BigDecimal precioUnitarioNormal;
  private BigDecimal precioUnitarioAjustado;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Articulo getArticulo() {
    return articulo;
  }

  public void setArticulo(Articulo articulo) {
    this.articulo = articulo;
  }

  public String getUnidadMedida() {
    return unidadMedida;
  }

  public void setUnidadMedida(String unidadMedida) {
    this.unidadMedida = unidadMedida;
  }

  public BigDecimal getPrecioUnitarioNormal() {
    return precioUnitarioNormal;
  }

  public void setPrecioUnitarioNormal(BigDecimal precioUnitarioNormal) {
    this.precioUnitarioNormal = precioUnitarioNormal;
  }

  public BigDecimal getPrecioUnitarioAjustado() {
    return precioUnitarioAjustado;
  }

  public void setPrecioUnitarioAjustado(BigDecimal precioUnitarioAjustado) {
    this.precioUnitarioAjustado = precioUnitarioAjustado;
  }

}
