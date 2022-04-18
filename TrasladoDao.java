package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.modelo.Traslado;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TrasladoDao {
  
  public static List<Traslado> listarPorSucursalYFecha(Sucursal sucursal, Date fechaInicial, Date fechaFinal, Connection conexion) throws SQLException {
    
    String agnoJuliano = "";
    String diaJuliano  = "";
    
    Calendar calendar = Calendar.getInstance();
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT pddoco, pddcto, pditm, pdmcu, pddrqj, pdtrdj, pduorg, pduom, pdlotn" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4311" +
      "    WHERE pddcto = 'OT'" +
      "      AND pdmcu  = ?" +
      "      AND pdtrdj BETWEEN ? AND ?");
    
    sentencia.setString(1, sucursal.getCodigo());
    
    calendar.setTime(fechaInicial);
    
    agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);
    
    diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
    
    sentencia.setInt(2, Integer.parseInt(agnoJuliano + diaJuliano));
    
    calendar.setTime(fechaFinal);
    
    agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);
    
    diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
    
    sentencia.setInt(3, Integer.parseInt(agnoJuliano + diaJuliano));
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<Traslado> traslados = new ArrayList<Traslado>();
    
    while(resultado.next()) {
      Traslado traslado = new Traslado();
      traslado.setNumeroOrden(resultado.getLong("pddoco"));
      traslado.setTipoOrden(resultado.getString("pddcto"));
      traslado.getArticulo().setCodigo(resultado.getInt("pditm"));
      traslado.getSucursal().setCodigo(resultado.getString("pdmcu"));
      
      calendar.set(Calendar.YEAR, 1900 + (int)(resultado.getInt("pddrqj") / 1000));
      calendar.set(Calendar.MONTH, 0);
      calendar.set(Calendar.DAY_OF_YEAR, 1);
      calendar.add(Calendar.DAY_OF_YEAR, resultado.getInt("pddrqj") % 1000 - 1);
      
      traslado.setFechaSolicitud(calendar.getTime());
      
      calendar.set(Calendar.YEAR, 1900 + (int)(resultado.getInt("pdtrdj") / 1000));
      calendar.set(Calendar.MONTH, 0);
      calendar.set(Calendar.DAY_OF_YEAR, 1);
      calendar.add(Calendar.DAY_OF_YEAR, resultado.getInt("pdtrdj") % 1000 - 1);
      
      traslado.setFechaOrden(calendar.getTime());
      
      traslado.setCantidadPrimaria(resultado.getBigDecimal("pduorg"));
      traslado.setUnidadMedidadPrimaria(resultado.getString("pduom"));
      traslado.setNumeroLote(resultado.getString("pdlotn"));
      
      traslados.add(traslado);
    }
    
    resultado.close();
    
    sentencia.close();
    
    return traslados;
  
  }

}
