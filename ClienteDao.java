package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Persona;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDao {
  
  public static List<Persona> listarPorSucursal(Sucursal sucursal, Connection conexion) throws SQLException {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT aian8, abat1, abalph, aitrar, abtax, aladd1, wpph1, aizon, aiac02, aiac11, aiac12, aiac15," +
      "       CASE WHEN adfvtr IS NULL THEN 0 ELSE adfvtr END AS adfvtr" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F03012" +
      "  INNER JOIN " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F0101" +
      "    ON(aian8 = aban8)" +
      "  LEFT OUTER JOIN " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4072" +
      "    ON(aian8 = adan8" +
      "      AND adast = 'DESPIE')" +
      "  LEFT OUTER JOIN " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F0115" +
      "    ON(aban8 = wpan8" +
      "      AND wpidln = 0" +
      "      AND wprck7 = 1" +
      "      AND wpcnln = 0)" +
      "  LEFT OUTER JOIN " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F0116" +
      "    ON(aban8 = alan8)" +
      "    WHERE TRIM(aiac12) = ?" +
      "      AND (alan8, aleftb) IN( " +
      "        SELECT alan8, MAX(aleftb)" +
      "          FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F0116" +
      "            GROUP BY alan8)" +
      "    ORDER BY aian8");
    
    sentencia.setString(1, sucursal.getCodigo().trim());
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<Persona> clientes = new ArrayList<Persona>();
    
    Persona cliente = null;
    
    while(resultado.next()) {
      
      if(cliente == null || !cliente.getCodigo().equals(resultado.getInt("aian8"))) {
        cliente = new Persona();
        cliente.setCodigo(resultado.getInt("aian8"));
        cliente.setTipoBusqueda(resultado.getString("abat1"));
        cliente.setNombre(resultado.getString("abalph"));
        cliente.setCondicionPago(resultado.getString("aitrar"));
        cliente.setNroDocumento(resultado.getString("abtax"));
        cliente.setDireccion(resultado.getString("aladd1"));
        cliente.setTelefono(resultado.getString("wpph1"));
        cliente.setZona(resultado.getString("aizon"));
        cliente.setCategoria2(resultado.getString("aiac02"));
        cliente.setCategoria11(resultado.getString("aiac11"));
        cliente.setSucursal(new Sucursal());
        cliente.getSucursal().setCodigo(resultado.getString("aiac12"));
        cliente.setCategoria15(resultado.getString("aiac15"));
        cliente.setDesctoPieFactura(resultado.getBigDecimal("adfvtr"));

        clientes.add(cliente);
      }
      
    }
    
    resultado.close();
    
    sentencia.close();
    
    return clientes;
  
  }

}
