package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.PreciosBase;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class PreciosBaseDao {
    
    public static PreciosBase precioPorBolsa (String codigo, Connection conexion) throws SQLException {
        
        Calendar calendar = Calendar.getInstance();
        
        PreparedStatement sentencia = conexion.prepareStatement(
      "  SELECT bpitm,bpeftj, bpuprc,bpuom" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.f4106" +
      "  WHERE bpitm = ?");
        
         sentencia.setString(1, codigo);
         
         ResultSet resultado = sentencia.executeQuery();
         PreciosBase preciosBase = new PreciosBase();
         
         while(resultado.next()) {  
             preciosBase.setItm(resultado.getString("bpitm"));
             
              calendar.set(Calendar.YEAR, 1900 + (int)(resultado.getInt("bpeftj") / 1000));
              calendar.set(Calendar.MONTH, 0);
              calendar.set(Calendar.DAY_OF_YEAR, 1);
              calendar.add(Calendar.DAY_OF_YEAR, resultado.getInt("bpeftj") % 1000 - 1);
             
             preciosBase.setEftj(calendar.getTime());
             preciosBase.setUprc(resultado.getBigDecimal("bpuprc"));
             preciosBase.setUom(resultado.getString("bpuom"));
         }
         resultado.close();
        
        return preciosBase;
        
    }
    
}
