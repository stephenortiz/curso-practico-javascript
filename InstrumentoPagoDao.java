package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.InstrumentoPago;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InstrumentoPagoDao {
  
  public static List<InstrumentoPago> listar(Connection conexion) throws SQLException {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT drky, drdl01" +
      "  FROM PRODCTL.F0005" +
      "    WHERE drsy = 'Q70'" +
      "      AND drrt = 'IP'");
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<InstrumentoPago> instrumentosPago = new ArrayList<InstrumentoPago>();
    
    while(resultado.next()) {
      InstrumentoPago instrumentoPago = new InstrumentoPago();
      instrumentoPago.setCodigo(resultado.getString("drky"));
      instrumentoPago.setDescripcion(resultado.getString("drdl01"));
      
      instrumentosPago.add(instrumentoPago);
    }
    
    resultado.close();
    
    sentencia.close();
    
    return instrumentosPago;
  
  }

}
