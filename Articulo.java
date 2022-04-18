package com.pollosbucanero.wsclient.modelo;

import java.util.ArrayList;
import java.util.List;

public class Articulo {
  
  private Integer codigo;
  private String codigo2;
  private String codigo3;
  private String descripcion;
  private String descripcion2;
  private String textoBusqueda;
  private String tipoUnidadMedida;
  private String unidadMedida;
  private String unidadMedidaPrecio;
  private String productoTerminado;
  
  private List<ArticuloSucursal> sucursales;
  
  public Articulo() {
    sucursales = new ArrayList<ArticuloSucursal>();
  }
  
  public Integer getCodigo() {
    return codigo;
  }

  public void setCodigo(Integer codigo) {
    this.codigo = codigo;
  }

  public String getCodigo2() {
    return codigo2;
  }

  public void setCodigo2(String codigo2) {
    this.codigo2 = codigo2;
  }

  public String getCodigo3() {
    return codigo3;
  }

  public void setCodigo3(String codigo3) {
    this.codigo3 = codigo3;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public String getDescripcion2() {
    return descripcion2;
  }

  public void setDescripcion2(String descripcion2) {
    this.descripcion2 = descripcion2;
  }

  public String getTextoBusqueda() {
    return textoBusqueda;
  }

  public void setTextoBusqueda(String textoBusqueda) {
    this.textoBusqueda = textoBusqueda;
  }

  public String getTipoUnidadMedida() {
    return tipoUnidadMedida;
  }

  public void setTipoUnidadMedida(String tipoUnidadMedida) {
    this.tipoUnidadMedida = tipoUnidadMedida;
  }

  public String getUnidadMedida() {
    return unidadMedida;
  }

  public void setUnidadMedida(String unidadMedida) {
    this.unidadMedida = unidadMedida;
  }

  public String getUnidadMedidaPrecio() {
    return unidadMedidaPrecio;
  }

  public void setUnidadMedidaPrecio(String unidadMedidaPrecio) {
    this.unidadMedidaPrecio = unidadMedidaPrecio;
  }

  public List<ArticuloSucursal> getSucursales() {
    return sucursales;
  }

  public void setSucursales(List<ArticuloSucursal> sucursales) {
    this.sucursales = sucursales;
  }

  public String getProductoTerminado() {
    return productoTerminado;
  }

  public void setProductoTerminado(String productoTerminado) {
    this.productoTerminado = productoTerminado;
  }

  public boolean equals(Object o) {
    
    if(o != null) {
      return o instanceof Articulo && this.codigo.equals(((Articulo) o).getCodigo());
    }
    
    return false;
    
  }

}
