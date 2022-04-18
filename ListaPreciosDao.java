package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Articulo;
import com.pollosbucanero.wsclient.modelo.DetalleListaPrecios;
import com.pollosbucanero.wsclient.modelo.ListaPrecios;
import com.pollosbucanero.wsclient.modelo.Persona;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ListaPreciosDao {
  
  public static List<ListaPrecios> listar(ListaPrecios filtro, Connection conexion) throws SQLException, ParseException {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT pddoco, pdeftj, pdan8, pditm, imuom4, pdlprc, pduprc" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F59LSTPRC" +
      "    WHERE pdeftj BETWEEN ? AND ?" +
      "    ORDER BY pddoco");
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(filtro.getFecha());
    calendar.set(Calendar.HOUR_OF_DAY, 0);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.SECOND, 0);
    calendar.set(Calendar.MILLISECOND, 0);
    
    sentencia.setTimestamp(1, new Timestamp(calendar.getTimeInMillis()));
    
    calendar.set(Calendar.HOUR_OF_DAY, 23);
    calendar.set(Calendar.MINUTE, 59);
    calendar.set(Calendar.SECOND, 59);
    calendar.set(Calendar.MILLISECOND, 999);
    
    sentencia.setTimestamp(2, new Timestamp(calendar.getTimeInMillis()));
    
    ResultSet resultado = sentencia.executeQuery();
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyy-MM-dd HH:mm:ss.SSS");
    
    List<ListaPrecios> listasPrecios = new ArrayList<ListaPrecios>();
    
    ListaPrecios listaPrecios = null;
    
    while(resultado.next()) {
      
      if(listaPrecios == null || !listaPrecios.getId().equals(resultado.getLong("pddoco"))) {
        listaPrecios = new ListaPrecios();
        listaPrecios.setId(resultado.getLong("pddoco"));
        listaPrecios.setFecha(formatoFecha.parse(resultado.getString("pdeftj")));
        listaPrecios.setCliente(new Persona());
        listaPrecios.getCliente().setCodigo(resultado.getInt("pdan8"));
        
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("pditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("imuom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("pdlprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("pduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      
        listasPrecios.add(listaPrecios);
      }
      else {
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("pditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("imuom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("pdlprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("pduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      }
      
    }
    
    resultado.close();
    
    sentencia.close();
    
    return listasPrecios;
  
  }

}
