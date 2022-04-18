package com.pollosbucanero.wsclient.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ListaPrecios {
  
  private Long id;
  private Date fecha;
  private Persona cliente;
  
  private List<DetalleListaPrecios> detalles;
  
  public ListaPrecios() {
    detalles = new ArrayList<DetalleListaPrecios>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFecha() {
    return fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public Persona getCliente() {
    return cliente;
  }

  public void setCliente(Persona cliente) {
    this.cliente = cliente;
  }

  public List<DetalleListaPrecios> getDetalles() {
    return detalles;
  }

  public void setDetalles(List<DetalleListaPrecios> detalles) {
    this.detalles = detalles;
  }

}
