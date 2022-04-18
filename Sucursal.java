package com.pollosbucanero.wsclient.modelo;

import java.util.ArrayList;
import java.util.List;

public class Sucursal {
  
  private String codigo;
  private String descripcion;
  private String unidadNegocio;
  private String tipoUnidadNegocio;
  
  private List<InstrumentoPagoAutorizado> instrumentosPago;
  
  public Sucursal() {
    instrumentosPago = new ArrayList<InstrumentoPagoAutorizado>();
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

  public String getUnidadNegocio() {
    return unidadNegocio;
  }

  public void setUnidadNegocio(String unidadNegocio) {
    this.unidadNegocio = unidadNegocio;
  }

  public String getTipoUnidadNegocio() {
    return tipoUnidadNegocio;
  }

  public void setTipoUnidadNegocio(String tipoUnidadNegocio) {
    this.tipoUnidadNegocio = tipoUnidadNegocio;
  }

  public List<InstrumentoPagoAutorizado> getInstrumentosPago() {
    return instrumentosPago;
  }

  public void setInstrumentosPago(List<InstrumentoPagoAutorizado> instrumentosPago) {
    this.instrumentosPago = instrumentosPago;
  }
  
  public boolean equals(Object o) {
    
    if(o != null) {
      return o instanceof Sucursal && this.codigo.equals(((Sucursal) o).getCodigo());
    }
    
    return false;
    
  }

}
