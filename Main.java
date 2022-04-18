package com.pollosbucanero.wsclient;

import com.pollosbucanero.wsclient.control.ArchivosInterfazControlador;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

  public static void main(final String[] args) throws Exception {

    try {
      int opcion = Integer.parseInt(args[0]);

      switch(opcion) {
        case 0:
          System.out.println("Generando archivos de constantes del sistema POS ...");

          ArchivosInterfazControlador.generarPlanosConstantes();

          break;

        case 1:
          System.out.println("Generando archivos de maestros del sistema POS ...");

          ArchivosInterfazControlador.generarPlanosMaestros();

          break;

        case 2:
          System.out.println("Generando archivos de precios del sistema POS ...");

          ArchivosInterfazControlador.generarPlanosPrecios(null);

          break;

        case 3:
          System.out.println("Generando archivos de existencias del sistema POS ...");

          ArchivosInterfazControlador.generarPlanosInventario();

          break;

        case 4:
          System.out.println("Insertando pedidos para extraer precios ...");
          
          ArchivosInterfazControlador.insertarPedidosEDIPrecios(null);

          break;

        case 5:
          System.out.println("Insertando pedidos para extraer precios ...");

          int an8Cliente = Integer.parseInt(args[1]);

          ArchivosInterfazControlador.insertarPedidoEDIPrecios(an8Cliente,null); 

          break;

        case 6:
          System.out.println("Insertando ventas POS ...");

          String archivoSQL = args[1];
          ArchivosInterfazControlador.insertarVentasJDE(archivoSQL);

          break;
            
        case 7:
          System.out.println("Purgando Ventas procesadas ...");  
          ArchivosInterfazControlador.purgaVentasYPreciosJDEProcesados();
          break;
            
        case 8:
          System.out.println("Insertando pedidos con fecha efectiva para extraer precios ...");
          Date fechaEfectivaPedidos = (new SimpleDateFormat("yyyy-MM-dd")).parse(args[1]);
          ArchivosInterfazControlador.insertarPedidosEDIPrecios(fechaEfectivaPedidos);
        break;
            
        case 9:
          System.out.println("Insertando pedido cliente "+args[1]+" con fecha efectiva para extraer precios ...");  
          int an8Clientes = Integer.parseInt(args[1]);
          Date fechaEfectivaPedido = (new SimpleDateFormat("yyyy-MM-dd")).parse(args[2]);
          ArchivosInterfazControlador.insertarPedidoEDIPrecios(an8Clientes,fechaEfectivaPedido);

        break;
            
        case 10:
          System.out.println("Generando listas de precios del sistema POS....");
          ArchivosInterfazControlador.generarPlanosPreciosLista();

        break;
            
        case 11:
          System.out.println("Generando archivos de precios del sistema POS por fecha ingresada ... ");
          Date fechaPrecios = (new SimpleDateFormat("yyyy-MM-dd")).parse(args[1]);
          ArchivosInterfazControlador.generarPlanosPrecios(fechaPrecios);  
          break;
            
        case 12:
          System.out.println("Generando listas de precios del sistema POS Rompiendo por cliente ....");         
          ArchivosInterfazControlador.generarPlanosPreciosListaPorCliente();
          

        break;

        default:
          System.out.println("Seleccione una opcion valida por favor");
      }   
    }
    catch(Exception e) {
      System.out.println("Error: " + e.getMessage());
    }


  }
  
}        