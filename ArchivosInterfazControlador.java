package com.pollosbucanero.wsclient.control;

import com.pollosbucanero.wsclient.db.OrdenVentaEDIDao;
import com.pollosbucanero.wsclient.db.ArticuloDao;
import com.pollosbucanero.wsclient.db.ClienteDao;
import com.pollosbucanero.wsclient.db.ConexionUtil;
import com.pollosbucanero.wsclient.db.ImpuestoDao;
import com.pollosbucanero.wsclient.db.InstrumentoPagoDao;
import com.pollosbucanero.wsclient.db.ListaPreciosDao;
import com.pollosbucanero.wsclient.db.PreciosBaseDao;
import com.pollosbucanero.wsclient.db.SucursalDao;
import com.pollosbucanero.wsclient.db.TrasladoDao;
import com.pollosbucanero.wsclient.db.UDCDao;
import com.pollosbucanero.wsclient.modelo.Articulo;
import com.pollosbucanero.wsclient.modelo.ArticuloSucursal;
import com.pollosbucanero.wsclient.modelo.DetalleListaPrecios;
import com.pollosbucanero.wsclient.modelo.Impuesto;
import com.pollosbucanero.wsclient.modelo.InstrumentoPago;
import com.pollosbucanero.wsclient.modelo.InstrumentoPagoAutorizado;
import com.pollosbucanero.wsclient.modelo.ListaPrecios;
import com.pollosbucanero.wsclient.modelo.Persona;
import com.pollosbucanero.wsclient.modelo.PreciosBase;
import com.pollosbucanero.wsclient.modelo.Sucursal;
import com.pollosbucanero.wsclient.modelo.Traslado;
import com.pollosbucanero.wsclient.modelo.UDC;
import com.pollosbucanero.wsclient.util.Constantes;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import oracle.net.aso.f;

public class ArchivosInterfazControlador {
  
  public static void generarPlanosMaestros() throws Exception {
    
    Connection conexion = ConexionUtil.getConexionJDE();
    
    FileOutputStream flujoArchivo = null;
    
    FileOutputStream flujoArchivoAlterno = null;
    
    String sqlInsert = "";
    
    try {
      
      if(conexion != null) {
        List<Sucursal> sucursales = SucursalDao.listar(conexion);
        
        List<Articulo> articulos = new ArrayList<Articulo>();
        
        List<Persona> clientes = new ArrayList<Persona>();
        
        for(Sucursal sucursal : sucursales) {
          List<Articulo> articulosSucursal = ArticuloDao.listarPorSucursal(sucursal, conexion);
          
          for(Articulo articulo : articulosSucursal) {
            int indexOfArticulo = articulos.indexOf(articulo);
            
            if(indexOfArticulo >= 0) {
              articulos.get(indexOfArticulo).getSucursales().addAll(articulo.getSucursales());
            }
            else {
              articulos.add(articulo);
            }
          }
          
          List<Persona> clientesSucursal = ClienteDao.listarPorSucursal(sucursal, conexion);
          
          clientes.addAll(clientesSucursal);
        }

        conexion.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/articulos.sql");
        
        for(Articulo articulo : articulos) {
          sqlInsert =
            "INSERT INTO articulo( " +
            "  artclcodigo, artclcodigo2, artclcodigo3," +
            "  artcldescrpcn, artcldescrpcn2, artcltextbusqd," +
            "  artclundmedd, artcltipundmedd, artclprtdo ) VALUES( " +
            articulo.getCodigo() + ", '" + articulo.getCodigo2().trim() + "', '" + articulo.getCodigo3().trim() + "', '" +
            articulo.getDescripcion() + "', '" + articulo.getDescripcion2() + "', '" + articulo.getTextoBusqueda() + "', '" +
            articulo.getUnidadMedida() + "', '" + articulo.getTipoUnidadMedida() + "','" + articulo.getProductoTerminado() + "' );\n";
          
          flujoArchivo.write(sqlInsert.getBytes());
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/articulos_sucursal.sql");
        
        for(Articulo articulo : articulos) {
          
          for(ArticuloSucursal articuloSucursal : articulo.getSucursales()) {
            sqlInsert =
              "INSERT INTO articulo_sucursal( " +
              "  articulo_artclcodigo, sucursal_sucscodigo, artsucivaid" +
              "  ) VALUES( " +
              articulo.getCodigo() + ", '" + articuloSucursal.getSucursal().getCodigo() + "', '" + articuloSucursal.getIva().getCodigo() + "' );\n";

            flujoArchivo.write(sqlInsert.getBytes());
          }
          
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/clientes.sql");
        
        flujoArchivoAlterno = new FileOutputStream("actualizacion/clientes_alterno.sql");
        
        for(Sucursal sucursal : sucursales) {
        
          for(Persona cliente : clientes) {
            
            if(cliente.getSucursal().getCodigo().trim().equals(sucursal.getCodigo().trim())) {
              
              if(sucursal.getTipoUnidadNegocio().equals("PV")) {
                sqlInsert =
                     "INSERT INTO direccion( " +
                     "  drcan8, drctipobusqd," +
                     "  drcnombre, drccondpago," +
                     "  drcdctopiefactr ) VALUES( " +
                     cliente.getCodigo() + ", '" + cliente.getTipoBusqueda().trim() + "', '" +
                     cliente.getNombre().trim() + "', '" + (cliente.getCondicionPago() == null ? "" : cliente.getCondicionPago().trim()) + "', " +
                     (cliente.getDesctoPieFactura().doubleValue() / 10000) + ");\n";

                flujoArchivo.write(sqlInsert.getBytes());
              }
//              else {
//                sqlInsert =
//                    "INSERT INTO direccion( " +
//                    "  drcan8, drctipobusqd," +
//                    "  drcnombre, drccondpago," +
//                    "  drcnit, drcdireccion," +
//                    "  drcpbx, drccategoria2," +
//                    "  drczona, drccategoria11, " +
//                    "  drcsucursal, drccategoria15, " +
//                    "  drcdctopiefactr ) VALUES( " +
//                    cliente.getCodigo() + ", '" + cliente.getTipoBusqueda().trim() + "', '" +
//                    cliente.getNombre().trim() + "', '" + (cliente.getCondicionPago() == null ? "" : cliente.getCondicionPago().trim()) + "', '" +
//                    cliente.getNroDocumento().trim() + "', '" + (cliente.getDireccion() == null ? " null," : "'" + cliente.getDireccion().trim() + "', ") +
//                    (cliente.getTelefono() == null ? " null," : "'" + cliente.getTelefono().trim() + "', ") + "'" + cliente.getCategoria2().trim() + "', '" +
//                    (cliente.getZona() == null ? "" : cliente.getZona().trim()) + "', '" + cliente.getCategoria11().trim() + "', '" +
//                    cliente.getSucursal().getCodigo() + "', '" + cliente.getCategoria15().trim() + "', " +
//                    (cliente.getDesctoPieFactura().doubleValue() / 10000) + " );\n";
//
//                flujoArchivoAlterno.write(sqlInsert.getBytes());
//              }
  
              }

            }
            
          }
        
        flujoArchivoAlterno.close();
        
        flujoArchivo.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.close();
    }
    
  }
  
  public static void generarPlanosPrecios(Date fechaPrecios) throws Exception {
  
    Connection conexion = ConexionUtil.getConexionJDE();
    
    String sqlInsert = "";
    
    try {
      
      if(conexion != null) {
        ListaPrecios filtroListaPrecios = new ListaPrecios();
        
        if(fechaPrecios != null){
          filtroListaPrecios.setFecha(fechaPrecios);  
        }else{
          filtroListaPrecios.setFecha(new Date());  
        }
        
        List<ListaPrecios> listasPreciosPos = OrdenVentaEDIDao.listar(filtroListaPrecios, conexion);
        
        List<ListaPrecios> listasPreciosPlanC = ListaPreciosDao.listar(filtroListaPrecios, conexion);
        
        conexion.close();
        
        FileOutputStream flujoArchivoEncabezado = new FileOutputStream("actualizacion/listasprecios.sql");
        
        FileOutputStream flujoArchivoEncabezadoAlt = new FileOutputStream("actualizacion/listasprecios_alterno.sql");
        
        FileOutputStream flujoArchivoDetalles = new FileOutputStream("actualizacion/detalleslistasprecios.sql");
        
        FileOutputStream flujoArchivoDetallesAlt = new FileOutputStream("actualizacion/detalleslistasprecios_alterno.sql");
        
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        for(ListaPrecios listaPrecios : listasPreciosPos) {
          sqlInsert =
            "INSERT INTO listaprecios( " +
            "  lstprcdocmntid, lstprcfecha," +
            "  lstprcclientan8 ) VALUES( " +
            listaPrecios.getId() + ", '" + formatoFecha.format(listaPrecios.getFecha()) + "', " +
            listaPrecios.getCliente().getCodigo() + " );\n";
          
          flujoArchivoEncabezado.write(sqlInsert.getBytes());
        
          for(DetalleListaPrecios detalleListaPrecios : listaPrecios.getDetalles()) {
            sqlInsert =
              "INSERT INTO detlllistaprecios( " +
              "  dtlsprcdocmntid, dtlsprcartclcd," +
              "  dtlsprcundmedd, dtlsprcprecund," +
              "  dtlsprcprecundaj ) VALUES( " + 
              listaPrecios.getId() + ", " + detalleListaPrecios.getArticulo().getCodigo() + ", '" +
              detalleListaPrecios.getUnidadMedida() + "', " + (detalleListaPrecios.getPrecioUnitarioNormal().doubleValue()/10000) + ", " +
              (detalleListaPrecios.getPrecioUnitarioAjustado().doubleValue()/10000) + " );\n";
            
            flujoArchivoDetalles.write(sqlInsert.getBytes());
          }
        }
        
        for(ListaPrecios listaPrecios : listasPreciosPlanC) {
          sqlInsert =
            "INSERT INTO listaprecios( " +
            "  lstprcdocmntid, lstprcfecha," +
            "  lstprcclientan8 ) VALUES( " +
            listaPrecios.getId() + ", '" + formatoFecha.format(listaPrecios.getFecha()) + "', " +
            listaPrecios.getCliente().getCodigo() + " );\n";
          
          flujoArchivoEncabezadoAlt.write(sqlInsert.getBytes());
        
          for(DetalleListaPrecios detalleListaPrecios : listaPrecios.getDetalles()) {
            sqlInsert =
              "INSERT INTO detlllistaprecios( " +
              "  dtlsprcdocmntid, dtlsprcartclcd," +
              "  dtlsprcundmedd, dtlsprcprecund," +
              "  dtlsprcprecundaj ) VALUES( " + 
              listaPrecios.getId() + ", " + detalleListaPrecios.getArticulo().getCodigo() + ", '" +
              detalleListaPrecios.getUnidadMedida() + "', " + (detalleListaPrecios.getPrecioUnitarioNormal().doubleValue()/10000) + ", " +
              (detalleListaPrecios.getPrecioUnitarioAjustado().doubleValue()/10000) + " );\n";
            
            flujoArchivoDetallesAlt.write(sqlInsert.getBytes());
          }
        }
        
        flujoArchivoDetallesAlt.close();
        flujoArchivoDetalles.close();
        
        flujoArchivoEncabezadoAlt.close();
        flujoArchivoEncabezado.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.close();
    }
    
  }
  
  public static void insertarPedidosEDIPrecios(Date fechaEfectivo) throws Exception {
    
    Connection conexion = ConexionUtil.getConexionJDE();
    
    try {
      
      if(conexion != null) {
        List<Sucursal> sucursales = SucursalDao.listar(conexion);
        
        List<Articulo> articulos = new ArrayList<Articulo>();
        
        List<Persona> clientes = new ArrayList<Persona>();
        
        for(Sucursal sucursal : sucursales) {
          
          if(sucursal.getCodigo().contains("PV")) {
            List<Articulo> articulosSucursal = ArticuloDao.listarPorSucursal(sucursal, conexion);

            for(Articulo articulo : articulosSucursal) {

              if(!articulos.contains(articulo)) {
                articulos.add(articulo);
              }

            }
          }
          
          if(sucursal.getTipoUnidadNegocio().equals("PV")) {
            List<Persona> clientesSucursal = ClienteDao.listarPorSucursal(sucursal, conexion);

            clientes.addAll(clientesSucursal);
          }
        }
        
        for(Persona cliente : clientes) {
          conexion.setAutoCommit(false);
          
          OrdenVentaEDIDao.insertar(cliente, articulos, conexion, fechaEfectivo);
          
          conexion.commit();
        }
        
        conexion.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      conexion.close();
    }
    
  }
  
  public static void insertarPedidoEDIPrecios(Integer an8Cliente, Date fechaEfectiva) throws Exception {
    
    Persona cliente = new Persona();
    cliente.setCodigo(an8Cliente);
    
    Connection conexion = ConexionUtil.getConexionJDE();
    
    try {
      
      if(conexion != null) {
        List<Sucursal> sucursales = SucursalDao.listar(conexion);
        
        List<Articulo> articulos = new ArrayList<Articulo>();
        
        for(Sucursal sucursal : sucursales) {
          
          if(sucursal.getCodigo().contains("PV")) {
            List<Articulo> articulosSucursal = ArticuloDao.listarPorSucursal(sucursal, conexion);

            for(Articulo articulo : articulosSucursal) {

              if(!articulos.contains(articulo)) {
                articulos.add(articulo);
              }

            }
          }
          
        }
        
        conexion.setAutoCommit(false);

        OrdenVentaEDIDao.insertar(cliente, articulos, conexion,fechaEfectiva);
          
        conexion.commit();
        conexion.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.rollback();
      conexion.close();
    }
    
  }
  
  public static void generarPlanosInventario() throws Exception {
    
    Connection conexion = ConexionUtil.getConexionJDE();
    
    FileOutputStream flujoArchivo = null;
    
    String sqlInsert = "";
    
    Date fechaActual = new Date();
    
    try {
      
      if(conexion != null) {
        List<Sucursal> sucursales = SucursalDao.listar(conexion);
        
        List<Traslado> traslados = new ArrayList<Traslado>();
        
        for(Sucursal sucursal : sucursales) {
          
          if(sucursal.getCodigo().contains("PV")) {
            List<Traslado> trasladosSucursal = TrasladoDao.listarPorSucursalYFecha(sucursal, fechaActual, fechaActual, conexion);

            traslados.addAll(trasladosSucursal);
          }
          
        }
        
        conexion.close();
        
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Sucursal sucursal : sucursales) {
          
          if(sucursal.getCodigo().contains("PV")) {
            flujoArchivo = new FileOutputStream("actualizacion/traslados" + sucursal.getCodigo().trim() + "_" + formatoFecha.format(fechaActual) + ".sql");

            for(Traslado traslado : traslados) {

              if(traslado.getSucursal().equals(sucursal)) {
                sqlInsert =
                  "INSERT INTO inventario( " +
                  "  invnumor, invtipord, artclcodigo," +
                  "  invfechsoli, invfechord, " +
                  "  invcant, invunidadmedi, invnumelote, " +
                  "  invcantrest ) VALUES( " +
                  traslado.getNumeroOrden() + ", '" + traslado.getTipoOrden() + "', " +  traslado.getArticulo().getCodigo() +
                  ", '" + formatoFecha.format(traslado.getFechaSolicitud()) + "', '" + formatoFecha.format(traslado.getFechaOrden()) + "', " +
                  traslado.getCantidadPrimaria() + ", '" + traslado.getUnidadMedidadPrimaria() + "', '" + traslado.getNumeroLote().trim() + "', " +
                  traslado.getCantidadPrimaria() + " );\n";

                flujoArchivo.write(sqlInsert.getBytes());
              }

            }

            flujoArchivo.close();
          }
          
        }
      }
      
    } 
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.close();
    }
    
  }
  
  public static void generarPlanosConstantes() throws Exception {
  
    Connection conexion = ConexionUtil.getConexionJDE();
    
    FileOutputStream flujoArchivo = null;
    
    String sqlInsert = "";
    
    try {
      
      if(conexion != null) {
        List<Sucursal> sucursales = SucursalDao.listar(conexion);
        
        List<InstrumentoPago> instrumentosPago = InstrumentoPagoDao.listar(conexion);
        
        List<Impuesto> impuestos = ImpuestoDao.listar(conexion);
        
        List<UDC> udcs = UDCDao.listar(conexion);
        
        conexion.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/sucursales.sql");
        
        for(Sucursal sucursal : sucursales) {
          sqlInsert =
              "INSERT INTO sucursal( " +
              "  sucscodigo, sucsdescrpcn, sucsundnegocio" +
              "  ) VALUES( '" +
              sucursal.getCodigo() + "', '" + sucursal.getDescripcion().trim() + "', '" + sucursal.getUnidadNegocio() + "' );\n";

          flujoArchivo.write(sqlInsert.getBytes());
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/instrumentospago.sql");
        
        for(InstrumentoPago instrumentoPago : instrumentosPago) {
          sqlInsert =
              "INSERT INTO instrumentopago( " +
              "  itrmpgcodigo, itrmpgdescrpcn" +
              "  ) VALUES( '" +
              instrumentoPago.getCodigo().trim() + "', '" + instrumentoPago.getDescripcion().trim() + "' );\n";

          flujoArchivo.write(sqlInsert.getBytes());
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/instrumentospagoautorizados.sql");
        
        DateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        
        for(Sucursal sucursal : sucursales) {
          
          for(InstrumentoPagoAutorizado instrumentoPagoAutorizado : sucursal.getInstrumentosPago()) {
            sqlInsert =
                "INSERT INTO instrmntpagoautrzd( " +
                "  itpgauinstrmntcd, itpgausucscd," +
                "  itpgaufechinicial, itpgaufechfinal," +
                "  itpgauundnegocio, itpgauctaobj," +
                "  itpgauauxlr ) VALUES( '" +
                instrumentoPagoAutorizado.getInstrumento().getCodigo().trim() + "', '" + sucursal.getCodigo() + "', '" +
                formatoFecha.format(instrumentoPagoAutorizado.getFechaInicial()) + "', '" + formatoFecha.format(instrumentoPagoAutorizado.getFechaFinal()) + "', '" +
                instrumentoPagoAutorizado.getUnidadNegocio() + "', '" + instrumentoPagoAutorizado.getCuentaObjetivo() + "', '" +
                instrumentoPagoAutorizado.getCuentaAuxiliar() + "' );\n";

            flujoArchivo.write(sqlInsert.getBytes());
          }
          
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/impuestos.sql");
        
        for(Impuesto impuesto : impuestos) {
          sqlInsert = 
            "INSERT INTO impuesto( " +
            "  imptoid, imptodescrpcn," +
            "  imptofactr ) VALUES( '" +
            impuesto.getCodigo() + "', '" + impuesto.getDescripcion() + "', " +
            impuesto.getFactor().doubleValue() + " );\n";
          
          flujoArchivo.write(sqlInsert.getBytes());
        }
        
        flujoArchivo.close();
        
        flujoArchivo = new FileOutputStream("actualizacion/udcs.sql");
        
        for(UDC udc : udcs) {
          sqlInsert = 
            "INSERT INTO udc( " +
            "  udcproducto, udcudc," +
            "  udccodigo, udcdescrpcn," +
            "  udcdescrpcn2 ) VALUES( '" +
            udc.getCodigoProducto().trim() + "', '" + udc.getUdc() + "', '" +
            udc.getCodigo().trim() + "', '" + udc.getDescripcion() + "', '" +
            udc.getDescripcion2() + "' );\n";
          
          flujoArchivo.write(sqlInsert.getBytes());
        }
        
        flujoArchivo.close();
      }
      
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
      
      conexion.close();
    }
  
  }
  
    public static void insertarVentasJDE(String archivoSQL) throws Exception {

      Connection conexion = ConexionUtil.getConexionJDE();
      String s = new String();
      StringBuffer sb = new StringBuffer();

      try {
        
        String dir1 = System.getProperty("user.dir");
        FileReader fr = new FileReader(new File(dir1 + "/"+archivoSQL+""));
//        File dir = new File(dir1 + "/ventas/"+archivoSQL+"");
//        System.out.println("Archivos en la Carpeta: " + dir.getPath() + ":");
//        String[] ficheros = dir.list();
        
        if (fr == null) {
           System.out.println("El archivo no existe");
        }else{
          
//          for (int x = 0; x < ficheros.length; x++) {
          System.out.println("Procesando " + archivoSQL + "......");
//            FileReader fr = new FileReader(new File(dir + "/" + ficheros[x]));
          BufferedReader br = new BufferedReader(fr);

          while ((s = br.readLine()) != null) {
            sb.append(s);
          }
          br.close();

          String[] inst = sb.toString().split(";");
          Statement st = conexion.createStatement();
          for (int i = 0; i < inst.length; i++) {

            if (!inst[i].trim().equals("")) {
//              System.out.println(">>"+inst[i]);
              st.executeUpdate(inst[i]);
            }

          }
          System.out.println("Finalizado " + archivoSQL + "......");

//          }
        }
        
        conexion.commit();
        conexion.close();

      } catch (Exception e) {
        System.out.println("Error: " + e.getMessage());
        System.out.println("*** Error : " + e.toString());
        e.printStackTrace();
        conexion.rollback();
        conexion.close();
      }
    }
    
      public static void purgaVentasYPreciosJDEProcesados() throws Exception {
           
        Connection conexion = ConexionUtil.getConexionJDE();
        String sqlInsertVentas = "";
        String sqlInsertPrecios = "";
        String agnoJuliano = "";
        String diaJuliano = "";
        String ambiente = "PRODDTA";
        String tipoVentas = "PX";
        String tipoPrecios = "PH";
        String procesado = "Y";
        Date fechaActual = new Date();
        Calendar calendar = Calendar.getInstance();
        
        try {
            
            calendar.add(Calendar.DAY_OF_YEAR, -15);
            fechaActual.setTime(calendar.getTimeInMillis());
            agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);
            diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
            diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
            //Eliminar Detalle Precios
            PreparedStatement sentenciaPreciosDetalle = conexion.prepareStatement(
            "DELETE FROM " + ambiente + ".f47012" +
            "  WHERE  SZEDCT = ? AND SZEDSP = ? AND SZDRQJ < ? ");
            sentenciaPreciosDetalle.setString(1, tipoPrecios);
            sentenciaPreciosDetalle.setString(2, procesado);
            sentenciaPreciosDetalle.setString(3, agnoJuliano + diaJuliano);
   
            ResultSet resultadoDetallePrecios = sentenciaPreciosDetalle.executeQuery();
            System.out.println("Purgado precios detalle ...");
            sentenciaPreciosDetalle.close();
            resultadoDetallePrecios.close();
            
            //Eliminar Encabezado Precios
            PreparedStatement sentenciaPreciosEncabezado = conexion.prepareStatement(
            "DELETE FROM " + ambiente + ".f47011" +
            "  WHERE  SYEDCT = ? AND SYEDSP = ? AND SYDRQJ < ? ");
            sentenciaPreciosEncabezado.setString(1, tipoPrecios);
            sentenciaPreciosEncabezado.setString(2, procesado);
            sentenciaPreciosEncabezado.setString(3, agnoJuliano + diaJuliano);   
            
            ResultSet resultadoEncabezadoPrecios = sentenciaPreciosEncabezado.executeQuery();
            System.out.println("Purgado precios encabezado ...");
            sentenciaPreciosEncabezado.close();
            resultadoEncabezadoPrecios.close();
              
            //Eliminar Detalle Ventas
            PreparedStatement sentenciaVentasDetalle = conexion.prepareStatement(
            "DELETE FROM " + ambiente + ".f47012" +
            "  WHERE  SZEDCT = ? AND SZEDSP = ? AND SZDRQJ < ? ");
            sentenciaVentasDetalle.setString(1, tipoVentas);
            sentenciaVentasDetalle.setString(2, procesado);
            sentenciaVentasDetalle.setString(3, agnoJuliano + diaJuliano);   
            
            ResultSet resultadoDetalleVentas = sentenciaVentasDetalle.executeQuery();
            System.out.println("Purgado ventas detalle ...");
            sentenciaVentasDetalle.close();
            resultadoDetalleVentas.close();
            
            //Eliminar Encabezado Ventas
            PreparedStatement sentenciaVentasEncabezado = conexion.prepareStatement(
            "DELETE FROM " + ambiente + ".f47011" +
            "  WHERE  SYEDCT = ? AND SYEDSP = ? AND SYDRQJ < ? ");
            sentenciaVentasEncabezado.setString(1, tipoVentas);
            sentenciaVentasEncabezado.setString(2, procesado);
            sentenciaVentasEncabezado.setString(3, agnoJuliano + diaJuliano);
            
            ResultSet resultadoEncabezadoVentas = sentenciaVentasEncabezado.executeQuery();
            System.out.println("Purgado ventas encabezado ...");
            sentenciaVentasEncabezado.close();
            resultadoEncabezadoVentas.close();

            conexion.commit();
            conexion.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("*** Error : " + e.toString());
            e.printStackTrace();
            conexion.rollback();
            conexion.close();
        }
    }
      
      public static void generarPlanosPreciosLista() throws Exception {
      
          Connection conexion = ConexionUtil.getConexionJDE();
          String sqlInsert = "";
          
          try {
              
              ListaPrecios filtroListaPrecios = new ListaPrecios();
              Date miFecha= new Date();
              filtroListaPrecios.setFecha(miFecha);
              
              ArrayList<ListaPrecios> listasPreciosPos = (ArrayList<ListaPrecios>) OrdenVentaEDIDao.listarPorListas(filtroListaPrecios, conexion);
                      
              ArrayList<ListaPrecios> ListaPreciosPOSClientes = new ArrayList<ListaPrecios>();
              
                
              FileOutputStream flujoArchivoEncabezado = new FileOutputStream("actualizacion/listasprecios.sql");
              FileOutputStream flujoArchivoDetalles = new FileOutputStream("actualizacion/detalleslistasprecios.sql");
              
              SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                  
              for(ListaPrecios listaPrecios : listasPreciosPos) {
              sqlInsert =
              "INSERT INTO listaprecios( " +
              "  lstprcdocmntid, lstprcfecha," +
              "  lstprcclientan8 ) VALUES( " +
              listaPrecios.getId() + ", '" + formatoFecha.format(listaPrecios.getFecha()) + "', " +
              listaPrecios.getCliente().getCodigo() + " );\n";
          
              flujoArchivoEncabezado.write(sqlInsert.getBytes());
        
              for(DetalleListaPrecios detalleListaPrecios : listaPrecios.getDetalles()) {
              sqlInsert =
              "INSERT INTO detlllistaprecios( " +
              "  dtlsprcdocmntid, dtlsprcartclcd," +
              "  dtlsprcundmedd, dtlsprcprecund," +
              "  dtlsprcprecundaj ) VALUES( " + 
              listaPrecios.getId() + ", " + detalleListaPrecios.getArticulo().getCodigo() + ", '" +
              detalleListaPrecios.getUnidadMedida() + "', " + (detalleListaPrecios.getPrecioUnitarioNormal().doubleValue()/10000) + ", " +
              (detalleListaPrecios.getPrecioUnitarioAjustado().doubleValue()/10000) + " );\n";
            
              flujoArchivoDetalles.write(sqlInsert.getBytes());
          }
        }
              
              
              
              
          } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            conexion.rollback();
            conexion.close();
          }     
          
      }
      
       public static void generarPlanosPreciosListaPorCliente() throws Exception {
      
          Connection conexion = ConexionUtil.getConexionJDE();
          String sqlInsert = "";
          
          try {
              
              ListaPrecios filtroListaPrecios = new ListaPrecios();
              Date miFecha= new Date();
              filtroListaPrecios.setFecha(miFecha);
              
              ArrayList<ListaPrecios> listasPreciosPos = (ArrayList<ListaPrecios>) OrdenVentaEDIDao.listarPorListas(filtroListaPrecios, conexion);
              
              PreciosBase preciosBase = PreciosBaseDao.precioPorBolsa("124271", conexion);
              
              for (ListaPrecios listaPreciosList : listasPreciosPos){
                  ArrayList<ListaPrecios> listaPreciosPorCliente = new ArrayList<ListaPrecios>();   
                  
                 for (ListaPrecios listaPreciosIterar : listasPreciosPos){
                     
                     if(listaPreciosIterar.getCliente().getCodigo().equals(listaPreciosList.getCliente().getCodigo())){
                         
                      listaPreciosPorCliente.add(listaPreciosIterar);  
                     }
                     
                 }
               
//               File folder = new File("c:\\POS-data-wsclient\\actualizacion\\"+listaPreciosList.getCliente().getCodigo());
               File folder = new File("actualizacion/"+listaPreciosList.getCliente().getCodigo());
               if(!folder.exists()){
                   folder.mkdir();
               }
               
               FileOutputStream flujoArchivoEncabezado = new FileOutputStream("actualizacion/"+listaPreciosList.getCliente().getCodigo()+"/listasprecios.sql"); 
               FileOutputStream flujoArchivoDetalles = new FileOutputStream("actualizacion/"+listaPreciosList.getCliente().getCodigo()+"/detalleslistasprecios.sql");
               
               SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
               
             for(ListaPrecios listaPrecios : listaPreciosPorCliente) {
              sqlInsert =
              "INSERT INTO listaprecios( " +
              "  lstprcdocmntid, lstprcfecha," +
              "  lstprcclientan8 ) VALUES( " +
              listaPrecios.getId() + ", '" + formatoFecha.format(listaPrecios.getFecha()) + "', " +
              listaPrecios.getCliente().getCodigo() + " );\n";
          
              flujoArchivoEncabezado.write(sqlInsert.getBytes());
         
              sqlInsert =
              "INSERT INTO detlllistaprecios( " +
              "  dtlsprcdocmntid, dtlsprcartclcd," +
              "  dtlsprcundmedd, dtlsprcprecund," +
              "  dtlsprcprecundaj ) VALUES( " + 
              listaPrecios.getId() + ", " + preciosBase.getItm() + ", '" +
              preciosBase.getUom() + "', " + (preciosBase.getUprc().doubleValue()/10000) + ", " +
              (preciosBase.getUprc().doubleValue()/10000) + " );\n";
              
              flujoArchivoDetalles.write(sqlInsert.getBytes());
               
              for(DetalleListaPrecios detalleListaPrecios : listaPrecios.getDetalles()) {
              sqlInsert =
              "INSERT INTO detlllistaprecios( " +
              "  dtlsprcdocmntid, dtlsprcartclcd," +
              "  dtlsprcundmedd, dtlsprcprecund," +
              "  dtlsprcprecundaj ) VALUES( " + 
              listaPrecios.getId() + ", " + detalleListaPrecios.getArticulo().getCodigo() + ", '" +
              detalleListaPrecios.getUnidadMedida() + "', " + (detalleListaPrecios.getPrecioUnitarioNormal().doubleValue()/10000) + ", " +
              (detalleListaPrecios.getPrecioUnitarioAjustado().doubleValue()/10000) + " );\n";
            
              flujoArchivoDetalles.write(sqlInsert.getBytes());
          }
        }
               
               
               
              }
              
//              ArrayList<ListaPrecios> ListaPreciosPOSClientes = new ArrayList<ListaPrecios>();
//              
//                
//              FileOutputStream flujoArchivoEncabezado = new FileOutputStream("actualizacion/listasprecios.sql");
//              FileOutputStream flujoArchivoDetalles = new FileOutputStream("actualizacion/detalleslistasprecios.sql");
//              
//              SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
//                  
//              for(ListaPrecios listaPrecios : listasPreciosPos) {
//              sqlInsert =
//              "INSERT INTO listaprecios( " +
//              "  lstprcdocmntid, lstprcfecha," +
//              "  lstprcclientan8 ) VALUES( " +
//              listaPrecios.getId() + ", '" + formatoFecha.format(listaPrecios.getFecha()) + "', " +
//              listaPrecios.getCliente().getCodigo() + " );\n";
//          
//              flujoArchivoEncabezado.write(sqlInsert.getBytes());
//        
//              for(DetalleListaPrecios detalleListaPrecios : listaPrecios.getDetalles()) {
//              sqlInsert =
//              "INSERT INTO detlllistaprecios( " +
//              "  dtlsprcdocmntid, dtlsprcartclcd," +
//              "  dtlsprcundmedd, dtlsprcprecund," +
//              "  dtlsprcprecundaj ) VALUES( " + 
//              listaPrecios.getId() + ", " + detalleListaPrecios.getArticulo().getCodigo() + ", '" +
//              detalleListaPrecios.getUnidadMedida() + "', " + (detalleListaPrecios.getPrecioUnitarioNormal().doubleValue()/10000) + ", " +
//              (detalleListaPrecios.getPrecioUnitarioAjustado().doubleValue()/10000) + " );\n";
//            
//              flujoArchivoDetalles.write(sqlInsert.getBytes());
//          }
//        }
//              
//              
              
              
          } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            conexion.rollback();
            conexion.close();
          }     
          
      }

}
