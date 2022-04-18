package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.UDC;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UDCDao {
  
  public static List<UDC> listar(Connection conexion) throws SQLException {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT drsy, drrt, drky, drdl01, drdl02" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "CTL.F0005" +
      "    WHERE (drsy = '01' AND drrt = '11')" +
      "       OR (drsy = '40' AND drrt = 'ZN')");
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<UDC> udcs = new ArrayList<UDC>();
    
    while(resultado.next()) {
      UDC udc = new UDC();
      udc.setCodigoProducto(resultado.getString("drsy"));
      udc.setUdc(resultado.getString("drrt"));
      udc.setCodigo(resultado.getString("drky"));
      udc.setDescripcion(resultado.getString("drdl01"));
      udc.setDescripcion2(resultado.getString("drdl02"));
      
      udcs.add(udc);
    }
    
    resultado.close();
    
    sentencia.close();
    
    return udcs;
  
  }
  
    public static List<UDC> listarClientesPOS(Connection conexion) throws SQLException {
       
        PreparedStatement sentencia = conexion.prepareStatement(
        "SELECT drsy, drrt, drky, drdl01, drdl02" +
        "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "CTL.F0005" +
        "    WHERE (drsy = '58' AND drrt = 'LP')");
    
        ResultSet resultado = sentencia.executeQuery();
        
        List<UDC> udcs = new ArrayList<UDC>();
        
        while(resultado.next()) {
        UDC udc = new UDC();
        udc.setCodigoProducto(resultado.getString("drsy"));
        udc.setUdc(resultado.getString("drrt"));
        udc.setCodigo(resultado.getString("drky"));
        udc.setDescripcion(resultado.getString("drdl01"));
        udc.setDescripcion2(resultado.getString("drdl02"));
      
        udcs.add(udc);
        }
    
        return udcs; 
    }

}
