package com.pollosbucanero.wsclient.control;

import com.pollosbucanero.wsclient.db.ConexionUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class PugarSistemaControlador {

  public static void purgarDetallePrecios(Long IdListaPrecio) throws Exception {

    Connection conexion = ConexionUtil.getConexionPOS();
    Statement sentencia = null;

    if (conexion != null) {

      conexion.setAutoCommit(false);

      PreparedStatement deleteDetalleListraPrecios = conexion.prepareStatement(
              "DELETE FROM detlllistaprecios WHERE dtlsprcdocmntid = ?");

      deleteDetalleListraPrecios.setLong(1, IdListaPrecio);
      deleteDetalleListraPrecios.executeUpdate();
      deleteDetalleListraPrecios.close();
    }

    conexion.commit();
    conexion.close();

  }

  public static void purgarPrecios(Long IdListaPrecio) throws Exception {
    Connection conexion = ConexionUtil.getConexionPOS();
    Statement sentencia = null;

    if (conexion != null) {
      conexion.setAutoCommit(false);

      PreparedStatement ListraPrecios = conexion.prepareStatement(
              "DELETE FROM listaprecios WHERE lstprcdocmntid = ?");

      ListraPrecios.setLong(1, IdListaPrecio);
      ListraPrecios.executeUpdate();
      ListraPrecios.close();


    }
    conexion.commit();
    conexion.close();

  }
}
