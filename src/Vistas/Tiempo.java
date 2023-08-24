/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Vistas;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author Pc
 */
public class Tiempo {
    
    LocalDate now = LocalDate.now();
    int year = now.getYear();
    int dia = now.getDayOfMonth();
    int month = now.getMonthValue();
    String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    
    String FechaCompleta = "Hoy es "+dia+" de "+meses[month - 1]+" de "+year;

}
