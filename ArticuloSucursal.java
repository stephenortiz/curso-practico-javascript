package com.pollosbucanero.wsclient.modelo;

public class ArticuloSucursal {
  
  private Articulo articulo;
  private Sucursal sucursal;
  private Impuesto iva;
  
  public ArticuloSucursal() {
    sucursal = new Sucursal();
    iva      = new Impuesto();
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

  public Impuesto getIva() {
    return iva;
  }

  public void setIva(Impuesto iva) {
    this.iva = iva;
  }

}
