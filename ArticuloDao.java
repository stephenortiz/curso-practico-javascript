package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Articulo;
import com.pollosbucanero.wsclient.modelo.ArticuloSucursal;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticuloDao {
  
  public static List<Articulo> listarPorSucursal(Sucursal sucursal, Connection conexion) throws SQLException {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT imitm, imlitm, imaitm, imdsc1, imdsc2, imsrtx, imdual, imuom1, imuom4, imglpt, ibmcu, ibtax1, ibstkt" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4101" +
      "  INNER JOIN " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4102" +           
      "  ON(imitm = ibitm)" +
      "  WHERE ibmcu = ? and  imglpt in ('PT50','PT51','PT60','PT61','PT62')" + 
      "  UNION ALL SELECT imitm, imlitm, imaitm, imdsc1, imdsc2, imsrtx, imdual, imuom1, imuom4, imglpt,"+
            "TO_NCHAR(?) as ibmcu , TO_NCHAR('Y') as ibtax1, TO_NCHAR('S') as ibstkt"+
            " FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4101 WHERE imitm = '124178' or imitm = '124271' ORDER BY imitm ");
    
    sentencia.setString(1, sucursal.getCodigo());
    sentencia.setString(2, sucursal.getCodigo());
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<Articulo> articulos = new ArrayList<Articulo>();
    
    Articulo articulo = null;
    
    while(resultado.next()) {
      
      if(articulo == null || !articulo.getCodigo().equals(resultado.getLong("imitm"))) {
        articulo = new Articulo();
        articulo.setCodigo(resultado.getInt("imitm"));
        articulo.setCodigo2(resultado.getString("imlitm"));
        articulo.setCodigo3(resultado.getString("imaitm"));
        articulo.setDescripcion(resultado.getString("imdsc1"));
        articulo.setDescripcion2(resultado.getString("imdsc2"));
        articulo.setTextoBusqueda(resultado.getString("imsrtx"));
        articulo.setTipoUnidadMedida(resultado.getString("imdual"));
        articulo.setUnidadMedida(resultado.getString("imuom1"));
        articulo.setUnidadMedidaPrecio(resultado.getString("imuom4"));
        articulo.setProductoTerminado(resultado.getString("imglpt"));
        
        ArticuloSucursal articuloSucursal = new ArticuloSucursal();
        articuloSucursal.getSucursal().setCodigo(resultado.getString("ibmcu"));
        articuloSucursal.getIva().setCodigo(resultado.getString("ibtax1"));
        
        articulo.getSucursales().add(articuloSucursal);

        articulos.add(articulo);
      }
      else {
        ArticuloSucursal articuloSucursal = new ArticuloSucursal();
        articuloSucursal.getSucursal().setCodigo(resultado.getString("ibmcu"));
        articuloSucursal.getIva().setCodigo(resultado.getString("ibtax1"));
        
        articulo.getSucursales().add(articuloSucursal);
      }
      
    }
    
    resultado.close();
    
    sentencia.close();
    
    return articulos;
  
  }

}
