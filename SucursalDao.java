package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.InstrumentoPagoAutorizado;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SucursalDao {
  
  public static List<Sucursal> listar(Connection conexion) throws SQLException {
    
    PreparedStatement sntcSucursales = conexion.prepareStatement(
      "SELECT mcmcu, mcdl01, mcrmcu1, mcstyl" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F0006" +
      "    WHERE mcmcu LIKE '      PV%PT'" +
      "       OR mcstyl = 'PV'");
    
    ResultSet rsltSucursales = sntcSucursales.executeQuery();
    
    PreparedStatement sntcInstrumentosAutorizados = conexion.prepareStatement(
      "SELECT pyq70ryin, pyemcu, pycefj, pycxpj, pymcu, pyobj, pysub" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.FQ7000PY" +
      "    WHERE pyemcu = ?");
    
    List<Sucursal> sucursales = new ArrayList<Sucursal>();
    
    Calendar calendar = Calendar.getInstance();
    
    while(rsltSucursales.next()) {
      Sucursal sucursal = new Sucursal();
      sucursal.setCodigo(rsltSucursales.getString("mcmcu"));
      sucursal.setDescripcion(rsltSucursales.getString("mcdl01"));
      sucursal.setUnidadNegocio(rsltSucursales.getString("mcrmcu1"));
      sucursal.setTipoUnidadNegocio(rsltSucursales.getString("mcstyl"));
      
      sntcInstrumentosAutorizados.setString(1, sucursal.getCodigo());
      
      ResultSet rsltInstrumentosAutorizados = sntcInstrumentosAutorizados.executeQuery();
      
      while(rsltInstrumentosAutorizados.next()) {
        InstrumentoPagoAutorizado instrumentoPagoAutorizado = new InstrumentoPagoAutorizado();
        instrumentoPagoAutorizado.getInstrumento().setCodigo(rsltInstrumentosAutorizados.getString("pyq70ryin"));
        
        calendar.set(Calendar.YEAR, 1900 + (int)(rsltInstrumentosAutorizados.getInt("pycefj") / 1000));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, rsltInstrumentosAutorizados.getInt("pycefj") % 1000 - 1);
        
        instrumentoPagoAutorizado.setFechaInicial(calendar.getTime());
        
        calendar.set(Calendar.YEAR, 1900 + (int)(rsltInstrumentosAutorizados.getInt("pycxpj") / 1000));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, rsltInstrumentosAutorizados.getInt("pycxpj") % 1000 - 1);
        
        instrumentoPagoAutorizado.setFechaFinal(calendar.getTime());
        
        instrumentoPagoAutorizado.setUnidadNegocio(rsltInstrumentosAutorizados.getString("pymcu"));
        instrumentoPagoAutorizado.setCuentaObjetivo(rsltInstrumentosAutorizados.getString("pyobj"));
        instrumentoPagoAutorizado.setCuentaAuxiliar(rsltInstrumentosAutorizados.getString("pysub"));
        
        sucursal.getInstrumentosPago().add(instrumentoPagoAutorizado);
      }
      
      rsltInstrumentosAutorizados.close();
      
      sucursales.add(sucursal);
    }
    
    rsltSucursales.close();
    
    sntcSucursales.close();
    
    return sucursales;
  
  }

}
