package pojos;
// Generated 16-ene-2020 10:32:45 by Hibernate Tools 4.3.1



/**
 * Punto generated by hbm2java
 */
public class Punto  implements java.io.Serializable {


     private int id;
     private Producto producto;
     private Short puntos;

    public Punto() {
    }

	
    public Punto(int id) {
        this.id = id;
    }
    public Punto(int id, Producto producto, Short puntos) {
       this.id = id;
       this.producto = producto;
       this.puntos = puntos;
    }
   
    public int getId() {
        return this.id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    public Producto getProducto() {
        return this.producto;
    }
    
    public void setProducto(Producto producto) {
        this.producto = producto;
    }
    public Short getPuntos() {
        return this.puntos;
    }
    
    public void setPuntos(Short puntos) {
        this.puntos = puntos;
    }




}


