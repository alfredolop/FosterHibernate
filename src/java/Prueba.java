
import pojos.Punto;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author alfredolop
 */
public class Prueba {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int n = 9;
        Punto punto = new Punto();
        // Set
        punto.setPuntos(new Integer(n).shortValue());
        // o
        punto.setPuntos(Short.valueOf(n+""));
        // Si String
        String nn = "9";
        punto.setPuntos(Short.valueOf(nn));

        // Get
        int puntos = punto.getPuntos().intValue();
        
    }
    
}
