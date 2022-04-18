package com.pollosbucanero.wsclient.modelo;

import java.math.BigDecimal;
import java.util.Date;

public class Traslado {
  
  private Long id;
  private Long numeroOrden;
  private String tipoOrden;
  private Articulo articulo;
  private Sucursal sucursal;
  private Date fechaSolicitud;
  private Date fechaOrden;
  private BigDecimal cantidadPrimaria;
  private String unidadMedidadPrimaria;
  private BigDecimal cantidadSecundaria;
  private String unidadMedidadSecundaria;
  private String numeroLote;
  
  public Traslado() {
    articulo = new Articulo();
    sucursal = new Sucursal();
  }

  public Long getId() {
    return id;
  }

  public Long getNumeroOrden() {
    return numeroOrden;
  }

  public void setNumeroOrden(Long numeroOrden) {
    this.numeroOrden = numeroOrden;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTipoOrden() {
    return tipoOrden;
  }

  public void setTipoOrden(String tipoOrden) {
    this.tipoOrden = tipoOrden;
  }

  public Articulo getArticulo() {
    return articulo;
  }

  public void setArticulo(Articulo articulo) {
    this.articulo = articulo;
  }

  public Sucursal getSucursal() {
    return sucursal;
  }

  public void setSucursal(Sucursal sucursal) {
    this.sucursal = sucursal;
  }

  public Date getFechaSolicitud() {
    return fechaSolicitud;
  }

  public void setFechaSolicitud(Date fechaSolicitud) {
    this.fechaSolicitud = fechaSolicitud;
  }

  public Date getFechaOrden() {
    return fechaOrden;
  }

  public void setFechaOrden(Date fechaOrden) {
    this.fechaOrden = fechaOrden;
  }

  public BigDecimal getCantidadPrimaria() {
    return cantidadPrimaria;
  }

  public void setCantidadPrimaria(BigDecimal cantidadPrimaria) {
    this.cantidadPrimaria = cantidadPrimaria;
  }

  public String getUnidadMedidadPrimaria() {
    return unidadMedidadPrimaria;
  }

  public void setUnidadMedidadPrimaria(String unidadMedidadPrimaria) {
    this.unidadMedidadPrimaria = unidadMedidadPrimaria;
  }

  public BigDecimal getCantidadSecundaria() {
    return cantidadSecundaria;
  }

  public void setCantidadSecundaria(BigDecimal cantidadSecundaria) {
    this.cantidadSecundaria = cantidadSecundaria;
  }

  public String getUnidadMedidadSecundaria() {
    return unidadMedidadSecundaria;
  }

  public void setUnidadMedidadSecundaria(String unidadMedidadSecundaria) {
    this.unidadMedidadSecundaria = unidadMedidadSecundaria;
  }

  public String getNumeroLote() {
    return numeroLote;
  }

  public void setNumeroLote(String numeroLote) {
    this.numeroLote = numeroLote;
  }

}
