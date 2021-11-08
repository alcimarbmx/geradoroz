/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package geradoroz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author alcimar
 */
public class CalendarioFormat {

    public String SO_gerada() {
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    public String SO_gerada(String formato) {
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat(formato);
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    /*
    public String DiaAtual_gerada(){
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("dd/MM/yyyy");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    public String Hora(){
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("HH:mm:ss");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    public String DataMes(){
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("MM");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    public String DataDia(){
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("dd");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }
    public String DataAno(){
        int res = 0;
        Locale.setDefault(new Locale("pt", "BR"));
        SimpleDateFormat dataf = new SimpleDateFormat("yyyy");
        
        Date d = new Date();

        String timeAtual = dataf.format(d);
        System.out.println(timeAtual);
        return "" + timeAtual;
    }*/
}
