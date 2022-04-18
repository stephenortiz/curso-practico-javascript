package com.pollosbucanero.wsclient.db;

import com.pollosbucanero.wsclient.modelo.Articulo;
import com.pollosbucanero.wsclient.modelo.DetalleListaPrecios;
import com.pollosbucanero.wsclient.modelo.ListaPrecios;
import com.pollosbucanero.wsclient.modelo.Persona;
import com.pollosbucanero.wsclient.modelo.UDC;
import com.pollosbucanero.wsclient.util.Constantes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class OrdenVentaEDIDao {
  
  private static String[] columnasEncabezadoOrden = new String[] {
      "SYEDTY", "SYEDSQ", "SYEKCO", "SYEDOC", "SYEDCT", "SYEDLN", "SYEDST",
      "SYEDFT", "SYEDDT", "SYEDER", "SYEDDL", "SYEDSP", "SYEDBT", "SYPNID",
      "SYOFRQ", "SYNXDJ", "SYSSDJ", "SYTPUR", "SYKCOO", "SYDOCO", "SYDCTO",
      "SYSFXO", "SYMCU", "SYCO", "SYOKCO", "SYOORN", "SYOCTO", "SYRKCO",
      "SYRORN", "SYRCTO", "SYAN8", "SYSHAN", "SYPA8", "SYDRQJ", "SYTRDJ",
      "SYPDDJ", "SYOPDJ", "SYADDJ", "SYCNDJ", "SYPEFJ", "SYPPDJ", "SYPSDJ",
      "SYVR01", "SYVR02", "SYDEL1", "SYDEL2", "SYINMG", "SYPTC", "SYRYIN",
      "SYASN", "SYPRGP", "SYTRDC", "SYPCRT", "SYTXA1", "SYEXR1", "SYTXCT",
      "SYATXT", "SYPRIO", "SYBACK", "SYSBAL", "SYNTR", "SYANBY", "SYCARS",
      "SYMOT", "SYCOT", "SYROUT", "SYSTOP", "SYZON", "SYCNID", "SYFRTH",
      "SYAFT", "SYRCD", "SYOTOT", "SYTOTC", "SYWUMD", "SYVUMD", "SYAUTN",
      "SYCACT", "SYCEXP", "SYCRMD", "SYCRRM", "SYCRCD", "SYCRR", "SYLNGP",
      "SYFAP", "SYFCST", "SYORBY", "SYTKBY", "SYURCD", "SYURDT", "SYURAT",
      "SYURAB", "SYURRF", "SYTORG", "SYUSER", "SYPID", "SYJOBN", "SYUPMJ",
      "SYTDAY", "SYIR01", "SYIR02", "SYIR03", "SYIR04", "SYIR05", "SYVR03",
      "SYSOOR", "SYPMDT", "SYRSDT", "SYRQSJ", "SYPSTM", "SYPDTT", "SYOPTT",
      "SYDRQT", "SYADTM", "SYADLJ", "SYPBAN", "SYITAN", "SYFTAN", "SYDVAN",
      "SYDOC1", "SYDCT4","SYCORD", "SYBSC", "SYBCRC", "SYRSHT", "SYHOLD",
      "SYFUF1", "SYAUFT", "SYAUFI", "SYOPBO", "SYOPTC", "SYOPLD", "SYOPBK",
      "SYOPSB", "SYOPPS", "SYOPPL", "SYOPMS", "SYOPSS", "SYOPBA", "SYOPLL",
      "SYPRAN8", "SYPRCIDLN", "SYOPPID", "SYCCIDLN", "SYSDATTN", "SYSHCCIDLN", "SYSPATTN",
      "SYOTIND", "SYEXVAR0", "SYEXVAR1", "SYEXVAR4", "SYEXVAR5", "SYEXVAR6", "SYEXVAR7",
      "SYEXVAR12", "SYEXVAR13", "SYEXNM0", "SYEXNM1", "SYEXNM2", "SYEXNMP0", "SYEXNMP1",
      "SYEXNMP2", "SYEXDT0", "SYEXDT1", "SYEXDT2", "SYPOHP01", "SYPOHP02", "SYPOHP03",
      "SYPOHP04", "SYPOHP05", "SYPOHP06", "SYPOHP07", "SYPOHP08", "SYPOHP09", "SYPOHP10",
      "SYPOHP11", "SYPOHP12", "SYPOHC01", "SYPOHC02", "SYPOHC03", "SYPOHC04", "SYPOHC05",
      "SYPOHC06", "SYPOHC07", "SYPOHC08", "SYPOHC09", "SYPOHC10", "SYPOHC11", "SYPOHC12",
      "SYPOHD01", "SYPOHD02", "SYPOHAB01", "SYPOHAB02", "SYPOHP13", "SYPOHU01", "SYPOHU02",
      "SYRETI", "SYCLASS01", "SYCLASS02", "SYCLASS03", "SYCLASS04", "SYCLASS05", "SYGAN8",
      "SYGSHAN", "SYGPA8", "SYGCARS", "SYGPBAN", "SYGITAN", "SYGFTAN", "SYGDVAN",
      "SYGPRAN8"
    };
  
  private static String[] valoresEncabezadoOrden = new String[] {
      " ", "0", "00001", "1", "SG", "0", "850   ",
      "          ", "0", "R", "0", " ", "               ", "               ",
      " ", "0", "0", "00", "     ", "0", "  ",
      "   ", "      BF01PT", "     ", "00001", "1       ", "SG", "     ",
      "        ", "  ", "30003", "30003", "0", "0", "0",
      "0", "0", "0", "0", "0", "0", "0",
      "                         ", "                         ", "                              ", "                              ", "          ", "   ", " ",
      "        ", "        ", "0", "0", "          ", "  ", "                    ",
      " ", " ", " ", " ", "  ", "0", "0",
      "   ", "   ", "   ", "   ", "   ", "                    ", "   ",
      " ", "   ", "0", "0", "  ", "  ", "          ",
      "                         ", "0", " ", " ", "   ", "0", "  ",
      "0", "0", "          ", "          ", "  ", "0", "0",
      "0", "               ", "JORDONEZ  ", "JORDONEZ  ", "PUNTOVE   ", "LOCAL   ", "115026",
      "145412", "                              ", "                              ", "                              ", "                              ", "                              ", "                         ",
      "0", "0", "0", "0", "0", "0", "0",
      "0", "0", "0", "0", "0", "0", "0",
      "0", "  ", "0", "          ", "   ", "0", "  ",
      "Y", " ", " ", "                              ", "0", "0", "0",
      "0", "                              ", "N", "N", "N", "N", "N",
      "0", "0", "0", "0", "                                                  ", "0", "                                                  ",
      " ", "null", "null", "null", "null", "null", "null",
      "null", "null", "0", "0", "0", "0", "0",
      "0", "null", "null", "null", " ", " ", " ",
      " ", " ", " ", " ", " ", " ", " ",
      " ", " ", "   ", "   ", "   ", "   ", "   ",
      "   ", "          ", "          ", "          ", "          ", "          ", "          ",
      "0", "0", "0", "0", "                              ", "null", "null",
      " ", "   ", "   ", "   ", "   ", "   ", "0",
      "0", "0", "0", "0", "0", "0", "0",
      "0"
    };
  
  private static String[] columnasDetalleOrden = new String[] {
      "SZEDTY", "SZEDSQ", "SZEKCO", "SZEDOC", "SZEDCT", "SZEDLN", "SZEDST",
      "SZEDFT", "SZEDDT", "SZEDER", "SZEDDL", "SZEDSP", "SZEDBT", "SZPNID",
      "SZKCOO", "SZDOCO", "SZDCTO", "SZLNID", "SZSFXO", "SZMCU", "SZCO",
      "SZOKCO", "SZOORN", "SZOCTO", "SZOGNO", "SZRKCO", "SZRORN", "SZRCTO",
      "SZRLLN", "SZDMCT", "SZDMCS", "SZAN8", "SZSHAN", "SZPA8", "SZDRQJ",
      "SZTRDJ", "SZPDDJ", "SZOPDJ", "SZADDJ", "SZIVD", "SZCNDJ", "SZDGL",
      "SZRSDJ", "SZPEFJ", "SZPPDJ", "SZPSDJ", "SZVR01", "SZVR02", "SZITM",
      "SZLITM", "SZAITM", "SZCITM", "SZLOCN", "SZLOTN", "SZFRGD", "SZTHGD",
      "SZFRMP", "SZTHRP", "SZEXDP", "SZDSC1", "SZDSC2", "SZLNTY", "SZNXTR",
      "SZLTTR", "SZEMCU", "SZRLIT", "SZKTLN", "SZCPNT", "SZRKIT", "SZKTP",
      "SZSRP1", "SZSRP2", "SZSRP3", "SZSRP4", "SZSRP5", "SZPRP1", "SZPRP2",
      "SZPRP3", "SZPRP4", "SZPRP5", "SZUOM", "SZUORG", "SZSOQS", "SZSOBK",
      "SZSOCN", "SZSONE", "SZUOPN", "SZQTYT", "SZQRLV", "SZCOMM", "SZOTQY",
      "SZUPRC", "SZAEXP", "SZAOPN", "SZPROV", "SZTPC", "SZAPUM", "SZLPRC",
      "SZUNCS", "SZECST", "SZCSTO", "SZTCST", "SZINMG", "SZPTC", "SZRYIN",
      "SZDTBS", "SZTRDC", "SZFUN2", "SZASN", "SZPRGR", "SZCLVL", "SZDSPR",
      "SZDSFT", "SZFAPP", "SZCADC", "SZKCO", "SZDOC", "SZDCT", "SZODOC",
      "SZODCT", "SZOKC", "SZPSN", "SZDELN", "SZTAX1", "SZTXA1", "SZEXR1",
      "SZATXT", "SZPRIO", "SZRESL", "SZBACK", "SZSBAL", "SZAPTS", "SZLOB",
      "SZEUSE", "SZDTYS", "SZNTR", "SZVEND", "SZANBY", "SZCARS", "SZMOT",
      "SZCOT", "SZROUT", "SZSTOP", "SZZON", "SZCNID", "SZFRTH", "SZAFT",
      "SZFUF1", "SZFRTC", "SZFRAT", "SZRATT", "SZSHCM", "SZSHCN", "SZSERN",
      "SZUOM1", "SZPQOR", "SZUOM2", "SZSQOR", "SZUOM4", "SZITWT", "SZWTUM",
      "SZITVL", "SZVLUM", "SZRPRC", "SZORPR", "SZORP", "SZCMGP", "SZCMGL",
      "SZGLC", "SZCTRY", "SZFY", "SZSTTS", "SZSO01", "SZSO02", "SZSO03",
      "SZSO04", "SZSO05", "SZSO06", "SZSO07", "SZSO08", "SZSO09", "SZSO10",
      "SZSO11", "SZSO12", "SZSO13", "SZSO14", "SZSO15", "SZACOM", "SZCMCG",
      "SZRCD", "SZGRWT", "SZGWUM", "SZANI", "SZAID", "SZOMCU", "SZOBJ",
      "SZSUB", "SZLT", "SZSBL", "SZSBLT", "SZLCOD", "SZUPC1", "SZUPC2",
      "SZUPC3", "SZSWMS", "SZUNCD", "SZCRMD", "SZCRCD", "SZCRR", "SZFPRC",
      "SZFUP", "SZFEA", "SZFUC", "SZFEC", "SZURCD", "SZURDT", "SZURAT",
      "SZURAB", "SZURRF", "SZTORG", "SZUSER", "SZPID", "SZJOBN", "SZUPMJ",
      "SZTDAY", "SZIR01", "SZIR02", "SZIR03", "SZIR04", "SZIR05", "SZSOOR",
      "SZDEID", "SZPSIG", "SZRLNU", "SZPMDT", "SZRLTM", "SZRLDJ", "SZDRQT",
      "SZADTM", "SZOPTT", "SZPDTT", "SZPSTM", "SZPMTN", "SZBSC", "SZCBSC",
      "SZDVAN", "SZRFRV", "SZSHPN", "SZPRJM", "SZHOLD", "SZPMTO", "SZDUAL",
      "SZPODC01", "SZPODC02", "SZPODC03", "SZPODC04", "SZJBCD", "SZSRQTY", "SZSRUOM",
      "SZCFGFL", "SZGAN8", "SZGSHAN", "SZGPA8", "SZGVEND", "SZGCARS", "SZGDVAN",
      "SZPMPN"
    };
    
  private static String[] valoresDetalleOrden = new String[] {
      " ", "0", "00001", "1", "SG", "1000", "      ",
      "          ", "0", " ", "0", " ", "               ", "               ",
      "     ", "0", "  ", "0", "   ", "      BF01PT", "     ",
      "0001 ", "1       ", "SG", "0", "     ", "        ", "  ",
      "0", "            ", "0", "30003", "30003", "30003", "0",
      "0", "0", "0", "0", "115026", "0", "0",
      "0", "0", "0", "0", "                         ", "                         ", "0",
      "30721                    ", "                         ", "                         ", "PRIMARI             ", "201501260003                  ", "   ", "   ",
      "0", "0", "0", "                              ", "                              ", "  ", "620",
      "580", "            ", "        ", "0", "0", "0", "0",
      "   ", "   ", "   ", "   ", "   ", "   ", "   ",
      "   ", "   ", "   ", "  ", "1000", "0", "0",
      "0", "0", "0", "0", "0", " ", " ",
      "51000000", "5100000", "0", "1", " ", "  ", "0",
      "0", "0", " ", "0", "          ", "   ", " ",
      " ", "0", "0", "        ", "        ", "   ", "0",
      " ", " ", "0", "00001", "46", "1C", "0",
      "  ", "     ", "0", "0", " ", "          ", "  ",
      " ", " ", " ", " ", " ", " ", "   ",
      "   ", "  ", "  ", "30003", "0", "0", "   ",
      "   ", "   ", "   ", "   ", "                    ", "   ", " ",
      " ", " ", "          ", " ", "   ", "   ", "                              ",
      "  ", "0", "  ", "0", "  ", "0", "  ",
      "0", "  ", "        ", "        ", " ", "  ", " ",
      "    ", "0", "0", "  ", " ", " ", " ",
      " ", " ", " ", " ", " ", " ", " ",
      " ", " ", " ", " ", " ", " ", "        ",
      "   ", "0", "  ", "                             ", "0000000 ", "            ", "      ",
      "        ", "  ", "        ", " ", "  ", "  ", "  ",
      "  ", " ", " ", " ", "   ", "0", "0",
      "0", "0", "0", "0", "  ", "0", "0",
      "0", "               ", "JORDONEZ  ", "JORDONEZ  ", "PUNTOVE   ","LOCAL   ", "115027",
      "82400", "                              ", "                              ", "                              ", "                              ", "                              ", "0",
      "0", "                              ", "          ", "0", "0", "0", "0",
      "0", "0", "0", "0", "            ", "          ", "          ",
      "0", "   ", "0", "0", "  ", " ", " ",
      "   ", "   ", "          ", "          ", "      ", "0", "  ", 
      " ", "0", "0", "0", "0", "0", "0",
      "                              "
    };
  
  public static void insertar(Persona cliente, List<Articulo> articulos, Connection conexion, Date fechaEfectiva) throws Exception {
    
    Calendar calendar = Calendar.getInstance();
    
    String agnoEfectivaJuliano = "";
    String diaEfectivaJuliano = "";
     
    String agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);

    String diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaJuliano       += calendar.get(calendar.DAY_OF_YEAR);
    
    String horaJuliana = "" + (calendar.get(Calendar.HOUR_OF_DAY)*10000 + calendar.get(Calendar.MINUTE)*100 + calendar.get(Calendar.SECOND));
    
    calendar.add(Calendar.DAY_OF_YEAR,1);
    
    String diaSiguienteJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaSiguienteJuliano       += calendar.get(calendar.DAY_OF_YEAR);
    
    if(fechaEfectiva != null){
        
        Calendar calendarEfectiva = Calendar.getInstance();
        calendarEfectiva.setTime(fechaEfectiva);
        
        agnoEfectivaJuliano += "" + (calendarEfectiva.get(calendarEfectiva.YEAR) - 1900);
        
        diaEfectivaJuliano   = calendarEfectiva.get(calendarEfectiva.DAY_OF_YEAR) < 10 ? "00" : calendarEfectiva.get(calendarEfectiva.DAY_OF_YEAR) < 100 ? "0" : "";
        diaEfectivaJuliano  += calendarEfectiva.get(calendarEfectiva.DAY_OF_YEAR);
        
    }
    
    String sqlInsert = "";
    
    int idTransaccion = 1;
    
    PreparedStatement stncMaxNroDocumentoEdi = conexion.prepareStatement(
      "SELECT MAX(syedoc) AS max_edi" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F47011" +
      "    WHERE syedct = 'PH'");
    
    ResultSet rsltMaxNroDocumentoEdi = stncMaxNroDocumentoEdi.executeQuery();
    
    while(rsltMaxNroDocumentoEdi.next()) {
      idTransaccion = rsltMaxNroDocumentoEdi.getInt("max_edi") + 1;
    }
    
    rsltMaxNroDocumentoEdi.close();
    
    stncMaxNroDocumentoEdi.close();
    
    int nroDetalle = 1;
    
    sqlInsert = "INSERT INTO " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F47011( ";

    for(int i = 0; i < columnasEncabezadoOrden.length; i++) {

      if(i != 0) {
        sqlInsert += ", ";
      }

      sqlInsert += columnasEncabezadoOrden[i];

    }

    sqlInsert += ") VALUES( ";

    for(int i = 0; i < columnasEncabezadoOrden.length; i++) {

      if(i != 0) {
        sqlInsert += ", ";
      }

      sqlInsert += "?";

    }

    sqlInsert += ")";
    
    PreparedStatement insertEncabezadoEDI = conexion.prepareStatement(sqlInsert);
    
    sqlInsert = "INSERT INTO " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F47012( ";

    for(int i = 0; i < columnasDetalleOrden.length; i++) {

      if(i != 0) {
        sqlInsert += ", ";
      }

      sqlInsert += columnasDetalleOrden[i];

    }

    sqlInsert += ") VALUES( ";

    for(int i = 0; i < columnasDetalleOrden.length; i++) {

      if(i != 0) {
        sqlInsert += ", ";
      }

      sqlInsert += "?";

    }

    sqlInsert += " )";
    
    PreparedStatement insertDetalleEDI = conexion.prepareStatement(sqlInsert);
    
    for(int i = 0; i < columnasEncabezadoOrden.length; i++) {

      if(valoresEncabezadoOrden[i].equals("null")) {
        insertEncabezadoEDI.setNull(i + 1, Types.NULL);
      }
      else if(columnasEncabezadoOrden[i].equals("SYEDOC")) {
        insertEncabezadoEDI.setInt(i + 1, idTransaccion);
      }
      else if(columnasEncabezadoOrden[i].equals("SYEDCT")) {
        insertEncabezadoEDI.setString(i + 1, "PH");
      }
      else if(columnasEncabezadoOrden[i].equals("SYMCU")) {
        insertEncabezadoEDI.setString(i + 1, "      PV04PT");
      }
      else if(columnasEncabezadoOrden[i].equals("SYAN8") ||
              columnasEncabezadoOrden[i].equals("SYSHAN")) {
        insertEncabezadoEDI.setString(i + 1, "" + cliente.getCodigo());
      }
      else if(columnasEncabezadoOrden[i].equals("SYTORG") ||
              columnasEncabezadoOrden[i].equals("SYUSER")) {
        insertEncabezadoEDI.setString(i + 1, "ADMIN");
      }
      else if(columnasEncabezadoOrden[i].equals("SYUPMJ") ||
              columnasEncabezadoOrden[i].equals("SYDRQJ") ||
              columnasEncabezadoOrden[i].equals("SYTRDJ")) {
          if (fechaEfectiva == null) {
              insertEncabezadoEDI.setString(i + 1, agnoJuliano + diaSiguienteJuliano);
          } else {
              insertEncabezadoEDI.setString(i + 1, agnoEfectivaJuliano + diaEfectivaJuliano);
          }
      }
      else if(columnasEncabezadoOrden[i].equals("SYTDAY")) {
        insertEncabezadoEDI.setString(i + 1, horaJuliana);
      }
      else if(columnasEncabezadoOrden[i].equals("SYEDSP")) {
        insertEncabezadoEDI.setString(i + 1, "N");
      }
      else if(columnasEncabezadoOrden[i].equals("SYPEFJ")) {
          if (fechaEfectiva == null) {
              insertEncabezadoEDI.setString(i + 1, agnoJuliano + diaSiguienteJuliano);
          } else {
              insertEncabezadoEDI.setString(i + 1, agnoEfectivaJuliano + diaEfectivaJuliano);
          }
      }
      else {
        insertEncabezadoEDI.setString(i + 1, valoresEncabezadoOrden[i]);
      }

    }

    insertEncabezadoEDI.executeUpdate();
    
    for(Articulo articulo : articulos) {
      
      for(int i = 0; i < columnasDetalleOrden.length; i++) {

        if(valoresDetalleOrden[i].equals("null")) {
          insertEncabezadoEDI.setNull(i + 1, Types.NULL);
        }
        else if(columnasDetalleOrden[i].equals("SZEDOC")) {
          insertDetalleEDI.setInt(i + 1, idTransaccion);
        }
        else if(columnasDetalleOrden[i].equals("SZEDCT")) {
          insertDetalleEDI.setString(i + 1, "PH");
        }
        else if(columnasDetalleOrden[i].equals("SZEDLN")) {
          insertDetalleEDI.setString(i + 1, "" + (nroDetalle * 1000));
        }
        else if(columnasDetalleOrden[i].equals("SZMCU")) {
          insertDetalleEDI.setString(i + 1, "      PV04PT");
        }
        else if(columnasDetalleOrden[i].equals("SZAN8") ||
                columnasDetalleOrden[i].equals("SZSHAN") ||
                columnasDetalleOrden[i].equals("SZPA8") ||
                columnasDetalleOrden[i].equals("SZVEND")) {
          insertDetalleEDI.setString(i + 1, "" + cliente.getCodigo());
        }
        else if(columnasDetalleOrden[i].equals("SZUPMJ") ||
                columnasDetalleOrden[i].equals("SZTRDJ") ||
                columnasDetalleOrden[i].equals("SZDRQJ")) {
            if (fechaEfectiva == null) {
                insertDetalleEDI.setString(i + 1, agnoJuliano + diaSiguienteJuliano);
            } else {
                insertDetalleEDI.setString(i + 1, agnoEfectivaJuliano + diaEfectivaJuliano);
            }
        }
        else if(columnasDetalleOrden[i].equals("SZLITM")) {
          insertDetalleEDI.setString(i + 1, articulo.getCodigo2());
        }
        else if(columnasDetalleOrden[i].equals("SZLOCN")) {
          insertDetalleEDI.setString(i + 1, "RECEP");
        }
        else if(columnasDetalleOrden[i].equals("SZLOTN")) {
          insertDetalleEDI.setString(i + 1, "");
        }
        else if(columnasDetalleOrden[i].equals("SZUORG")) {
          insertDetalleEDI.setString(i + 1, "100");
        }
        else if(columnasDetalleOrden[i].equals("SZUOM")) {
          insertDetalleEDI.setString(i + 1, articulo.getUnidadMedidaPrecio());
        }
        else if(columnasDetalleOrden[i].equals("SZUPRC")) {
          insertDetalleEDI.setString(i + 1, "0");
        }
        else if(columnasDetalleOrden[i].equals("SZUOM4")) {
          insertDetalleEDI.setString(i + 1, articulo.getUnidadMedidaPrecio());
        }
        else if(columnasDetalleOrden[i].equals("SZPROV")) {
          insertDetalleEDI.setString(i + 1, " ");
        }
        else if(columnasDetalleOrden[i].equals("SZAEXP")) {
          insertDetalleEDI.setString(i + 1, "0");
        }
        else if(columnasDetalleOrden[i].equals("SZSQOR")) {
          insertDetalleEDI.setString(i + 1, "0");
        }
        else if(columnasDetalleOrden[i].equals("SZTORG") ||
                columnasDetalleOrden[i].equals("SZUSER")) {
          insertDetalleEDI.setString(i + 1, "ADMIN");
        }
        else if(columnasDetalleOrden[i].equals("SZTDAY")) {
          insertDetalleEDI.setString(i + 1, horaJuliana);
        }
        else if(columnasDetalleOrden[i].equals("SZPEFJ")) {
            if (fechaEfectiva == null) {
                insertDetalleEDI.setString(i + 1, agnoJuliano + diaSiguienteJuliano);
            } else {
                insertDetalleEDI.setString(i + 1, agnoEfectivaJuliano + diaEfectivaJuliano);
            }      
        }
        else {
          insertDetalleEDI.setString(i + 1, valoresDetalleOrden[i]);
        }
      
      }
      
      insertDetalleEDI.executeUpdate();
      
      nroDetalle++;
      
    }
  
  }
  
  public static List<ListaPrecios> listar(ListaPrecios filtro, Connection conexion) throws Exception {
    
    PreparedStatement sentencia = conexion.prepareStatement(
      "SELECT sddoco, sddrqj, sdan8, sditm, sduom4, sduprc, sduprc" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.F4211" +
      "    WHERE sddrqj = ?" +
      "      AND sddcto = ?" +
      "  ORDER BY sddoco ASC");
    
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(filtro.getFecha());
    
    String agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);
    
    String diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
    diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
    
    sentencia.setInt(1, Integer.parseInt(agnoJuliano + diaJuliano));
    sentencia.setString(2, "SP");
    
    ResultSet resultado = sentencia.executeQuery();
    
    List<ListaPrecios> listasPrecios = new ArrayList<ListaPrecios>();
    
    ListaPrecios listaPrecios = null;
    
    while(resultado.next()) {
      
      if(listaPrecios == null || !listaPrecios.getId().equals(resultado.getLong("sddoco"))) {
        listaPrecios = new ListaPrecios();
        listaPrecios.setId(resultado.getLong("sddoco"));
        
        calendar.set(Calendar.YEAR, 1900 + (int)(resultado.getInt("sddrqj") / 1000));
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        calendar.add(Calendar.DAY_OF_YEAR, resultado.getInt("sddrqj") % 1000 - 1);
        
        listaPrecios.setFecha(calendar.getTime());
        
        listaPrecios.setCliente(new Persona());
        listaPrecios.getCliente().setCodigo(resultado.getInt("sdan8"));
        
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("sditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("sduom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("sduprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("sduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      
        listasPrecios.add(listaPrecios);
      }
      else {
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("sditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("sduom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("sduprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("sduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      }
      
    }
    
    resultado.close();
    
    sentencia.close();
    
    return listasPrecios;
  
  }
  
  
  
  public static List<ListaPrecios> listarPorListas(ListaPrecios filtro, Connection conexion) throws Exception {
      
      PreparedStatement sentencia = conexion.prepareStatement(
      "  SELECT ukid,drky, pddoco, pddcto, pdan8, pditm, imuom4, pdlprc, pduprc, pdeftj" +
      "  FROM " + Constantes.PREFIJO_AMBIENTE_BD + "DTA.v58lstprc" +
      "  WHERE pdeftj >= ?" +
      "  AND pddcto = ?" +
      "  ORDER BY pddoco, pdeftj, drky");
      
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(filtro.getFecha());
    
      String agnoJuliano = "" + (calendar.get(Calendar.YEAR) - 1900);
    
      String diaJuliano = calendar.get(calendar.DAY_OF_YEAR) < 10 ? "00" : calendar.get(calendar.DAY_OF_YEAR) < 100 ? "0" : "";
      diaJuliano += calendar.get(calendar.DAY_OF_YEAR);
    
      sentencia.setDate(1, new java.sql.Date(filtro.getFecha().getTime()));
      sentencia.setString(2, "SF");
     
      ResultSet resultado = sentencia.executeQuery();
      
//    ArrayList<UDC> listaUDCClientes = (ArrayList<UDC>) UDCDao.listarClientesPOS(conexion);
    
      List<ListaPrecios> listasPrecios = new ArrayList<ListaPrecios>();
      List<ListaPrecios> listasPreciosClientes = new ArrayList<ListaPrecios>();
    
      ListaPrecios listaPrecios = null;
      
      while(resultado.next()) {
                  
        if(listaPrecios == null || !listaPrecios.getId().equals(resultado.getLong("ukid"))) {
        listaPrecios = new ListaPrecios();
        listaPrecios.setId(resultado.getLong("ukid"));
        
//        calendar.set(Calendar.YEAR, 1900 + (int)(resultado.getInt("pdeftj") / 1000));
//        calendar.set(Calendar.MONTH, 0);
//        calendar.set(Calendar.DAY_OF_YEAR, 1);
//        calendar.add(Calendar.DAY_OF_YEAR, resultado.getInt("pdeftj") % 1000 - 1);
        
        listaPrecios.setFecha(resultado.getDate("pdeftj"));
        
        listaPrecios.setCliente(new Persona());
        listaPrecios.getCliente().setCodigo(resultado.getInt("drky"));
        
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("pditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("imuom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("pduprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("pduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      
        listasPrecios.add(listaPrecios);
      }
      else {
        DetalleListaPrecios detalleListaPrecios = new DetalleListaPrecios();
        detalleListaPrecios.setArticulo(new Articulo());
        detalleListaPrecios.getArticulo().setCodigo(resultado.getInt("pditm"));
        detalleListaPrecios.setUnidadMedida(resultado.getString("imuom4"));
        detalleListaPrecios.setPrecioUnitarioNormal(resultado.getBigDecimal("pduprc"));
        detalleListaPrecios.setPrecioUnitarioAjustado(resultado.getBigDecimal("pduprc"));
        
        listaPrecios.getDetalles().add(detalleListaPrecios);
      }
          
      }

      resultado.close();
    
      sentencia.close();
    
      return listasPrecios;
      
  }

}
