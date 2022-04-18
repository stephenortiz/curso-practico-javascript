package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Impuesto;
import com.pollosbucanero.wsclient.util.Constantes;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ImpuestoDao {
  
  public static List<Impuesto> listar(Connection conexion) throws SQLException {
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(new Date());
    String agnoJuliano = "";
    String diaJuliano  = ""; 
    
    agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);

    diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT CASE WHEN taitm = '0' THEN 'Y' ELSE '' || taitm END AS taitm, tataxa, tatxr2" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4008" +
      "    WHERE tatxa1 = 'VENTAS' and taefdj > "+agnoJuliano+diaJuliano+"" );
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<Impuesto> impuestos = new ArrayList<Impuesto>();
    
    while(resultado.next()) {
      Impuesto impuesto = new Impuesto();
      impuesto.setCodigo(resultado.getString("taitm"));
      impuesto.setDescripcion(resultado.getString("tataxa"));
      impuesto.setFactor(BigDecimal.valueOf(resultado.getInt("tatxr2")/1000.0));
      
      impuestos.add(impuesto);
    }
    
    resultado.close();
    
    sentencia.close();
    
    return impuestos;
  
  }

}
