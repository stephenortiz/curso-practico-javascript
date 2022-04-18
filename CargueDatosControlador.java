package com.pollosbucanero.wsclient.control;

import com.pollosbucanero.wsclient.db.ConexionUtil;
import com.pollosbucanero.wsclient.modelo.InstrumentoPago;
import com.pollosbucanero.wsclient.modelo.InstrumentoPagoAutorizado;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CargueDatosControlador {
  
  public static void cargarMaestros(Boolean sistemaAlterno) throws Exception {
  
    Connection conexion = ConexionUtil.getConexionPOS();
    
    FileInputStream flujoArchivo = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    
    Statement sentencia = null;
    
    String linea = "";
    
    try {
      
      if(conexion != null) {
        conexion.setAutoCommit(false);
        
        sentencia = conexion.createStatement();
        
        conexion.setAutoCommit(false);
        
        sentencia.executeUpdate("DELETE FROM articulo");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/articulos.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);
        
        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }
        
        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Maestro de articulos cargado exitosamente.");
        
        sentencia.executeUpdate("DELETE FROM articulo_sucursal");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/articulos_sucursal.sql");
        isr = new InputStreamReader(flujoArchivo);
        br = new BufferedReader(isr);
        
        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }
        
        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Maestro de sucursal-articulo cargado exitosamente.");
        
        sentencia.executeUpdate("DELETE FROM direccion WHERE drcan8 NOT LIKE 'AN%'");
        
        if(!sistemaAlterno) {
          flujoArchivo = new FileInputStream("interfaz/JDE/clientes.sql");
        }
        else {
          flujoArchivo = new FileInputStream("interfaz/JDE/clientes_alterno.sql");
        }
        
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);
        
        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }
        
        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Maestro de clientes cargado exitosamente.");
        
        sentencia.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      
      conexion.close();
    }
  
  }
  
  public static void cargarPrecios(Boolean sistemaAlterno) throws Exception {
    
    Connection conexion = ConexionUtil.getConexionPOS();
    
    FileInputStream flujoArchivo = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    
    Statement sentencia = null;
    
    String linea = "";
  
    try {
      
      if(conexion != null) {
        sentencia = conexion.createStatement();
        
        conexion.setAutoCommit(false);
        
        sentencia.executeUpdate("DELETE FROM listaprecios");
        
        if(!sistemaAlterno) {
          flujoArchivo = new FileInputStream("interfaz/JDE/listasprecios.sql");
        }
        else {
          flujoArchivo = new FileInputStream("interfaz/JDE/listasprecios_alterno.sql");
        }
        
        isr = new InputStreamReader(flujoArchivo);
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        sentencia.executeUpdate("DELETE FROM detlllistaprecios");
        
        if(!sistemaAlterno) {
          flujoArchivo = new FileInputStream("interfaz/JDE/detalleslistasprecios.sql");
        }
        else {
          flujoArchivo = new FileInputStream("interfaz/JDE/detalleslistasprecios_alterno.sql");
        }
        
        isr = new InputStreamReader(flujoArchivo);
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Listas de precios del dia cargadas exitosamente.");
        
        sentencia.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      
      conexion.close();
    }
  
  }
  
  public static void cargarInventario(String codigoSucursal, Date fecha) throws Exception {
  
    Connection conexion = ConexionUtil.getConexionPOS();
    
    FileInputStream flujoArchivo = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    
    Statement sentencia = null;
    
    String linea = "";
    
    DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
  
    try {
      
      if(conexion != null) {
        sentencia = conexion.createStatement();
        
        conexion.setAutoCommit(false);
        
        flujoArchivo = new FileInputStream("interfaz/JDE/traslados" + codigoSucursal + "_" + formatoFecha.format(fecha) + ".sql");
        isr = new InputStreamReader(flujoArchivo);
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Existencias cargadas exitosamente.");
        
        sentencia.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      
      conexion.close();
    }
  
  }
  
  public static void cargarConstantes() throws Exception {
  
    Connection conexion = ConexionUtil.getConexionPOS();
    
    FileInputStream flujoArchivo = null;
    InputStreamReader isr = null;
    BufferedReader br = null;
    
    Statement sentencia = null;
    
    String linea = "";
    
    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
  
    try {
      
      if(conexion != null) {
        sentencia = conexion.createStatement();
        
        conexion.setAutoCommit(false);
        
        sentencia.executeUpdate("DELETE FROM sucursal");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/sucursales.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Tabla de sucursales cargada exitosamente.");
        
        sentencia.executeUpdate("DELETE FROM instrumentopago");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/instrumentospago.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Tabla de instrumentos de pago cargada exitosamente.");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/instrumentospagoautorizados.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        ResultSet rsltInstrmntsAutrzdPunto = sentencia.executeQuery(
          "SELECT itpgauid, itpgausucscd, itpgauinstrmntcd, itpgaufechinicial" +
          "  FROM instrmntpagoautrzd" +
          "  ORDER BY itpgausucscd ASC, itpgauinstrmntcd ASC, itpgaufechinicial ASC, itpgauid ASC");
        
        PreparedStatement deleteInstrmtsAutorizados = conexion.prepareStatement(
          "DELETE FROM instrmntpagoautrzd WHERE itpgauid = ?");
        
        InstrumentoPagoAutorizado instrumentoPagoAutorizado = null;
        
        while(rsltInstrmntsAutrzdPunto.next()) {
        
          if(instrumentoPagoAutorizado == null ||
               !instrumentoPagoAutorizado.getSucursal().getCodigo().equals(rsltInstrmntsAutrzdPunto.getString("itpgausucscd")) ||
               !instrumentoPagoAutorizado.getInstrumento().getCodigo().equals(rsltInstrmntsAutrzdPunto.getString("itpgauinstrmntcd")) ||
               !instrumentoPagoAutorizado.getFechaInicial().equals(formatoFecha.parse(rsltInstrmntsAutrzdPunto.getString("itpgaufechinicial")))) {
            instrumentoPagoAutorizado = new InstrumentoPagoAutorizado();
            instrumentoPagoAutorizado.setId(rsltInstrmntsAutrzdPunto.getInt("itpgauid"));
            instrumentoPagoAutorizado.setSucursal(new Sucursal());
            instrumentoPagoAutorizado.getSucursal().setCodigo(rsltInstrmntsAutrzdPunto.getString("itpgausucscd"));
            instrumentoPagoAutorizado.setInstrumento(new InstrumentoPago());
            instrumentoPagoAutorizado.getInstrumento().setCodigo(rsltInstrmntsAutrzdPunto.getString("itpgauinstrmntcd"));
            instrumentoPagoAutorizado.setFechaInicial(formatoFecha.parse(rsltInstrmntsAutrzdPunto.getString("itpgaufechinicial")));
          }
          else {
            deleteInstrmtsAutorizados.setInt(1, rsltInstrmntsAutrzdPunto.getInt("itpgauid"));

            deleteInstrmtsAutorizados.executeUpdate();
          }
          
        }
        
        rsltInstrmntsAutrzdPunto.close();
        
        deleteInstrmtsAutorizados.close();
          
        conexion.commit();
        
        System.out.println("Tabla de instrumentos de pago autorizados por punto cargada exitosamente.");
       
        sentencia.executeUpdate("DELETE FROM impuesto");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/impuestos.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("Tabla de impuestos cargada exitosamente.");
        
        /*
        sentencia.executeUpdate("DELETE FROM udc");
        
        flujoArchivo = new FileInputStream("interfaz/JDE/udcs.sql");
        isr = new InputStreamReader(flujoArchivo, "UTF8");
        br = new BufferedReader(isr);

        while((linea = br.readLine()) != null) {
          sentencia.execute(linea);
        }

        br.close();
        isr.close();
        flujoArchivo.close();
        
        conexion.commit();
        
        System.out.println("UDCs cargada exitosamente.");
        
        sentencia.close();
        */
        
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      
      conexion.close();
    }
  
  }
  
           }
