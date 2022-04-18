package com.pollosbucanero.wsclient.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionUtil {
  
  public static Connection getConexionJDE() {
    
    String SERVIDOR  = "bucn-racscan1";
    int PUERTO       = 1521;
    String NOMBRE_BD = "jderac";
  
    Connection conexion = null;
    
    try {
      Class.forName("oracle.jdbc.OracleDriver");

      conexion = DriverManager.getConnection("jdbc:oracle:thin:@//" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_BD, "jde_sywork", "jd3_syw0rk");
    }
    catch(ClassNotFoundException e) {
      System.out.print("Driver de conexión no encontrado");
    }
    catch(SQLException e) {
      System.out.print("No se pudo efectuar la conexión: " + e.getMessage());
    }
  
    return conexion;
  
  }
  
  public static Connection getConexionPOS() {
    
    String SERVIDOR  = "localhost";
    int PUERTO       = 5432;
    String NOMBRE_BD = "pos_data";
  
    Connection conexion = null;
    
    try {
      Class.forName("org.postgresql.Driver");

      conexion = DriverManager.getConnection("jdbc:postgresql://" + SERVIDOR + ":" + PUERTO + "/" + NOMBRE_BD, "postgres", "psDta2015*");
    }
    catch(ClassNotFoundException e) {
      System.out.print("Driver de conexión no encontrado");
    }
    catch(SQLException e) {
      System.out.print("No se pudo efectuar la conexión: " + e.getMessage());
    }
  
    return conexion;
  
  }

}
