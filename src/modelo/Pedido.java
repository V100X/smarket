package modelo;

import java.io.Serializable;

public class Pedido implements Serializable {

    private String titlulo;
    private String fecha;
    private double coste;

    public Pedido(String titlulo, String fecha, double coste) {
        this.titlulo = titlulo;
        this.fecha = fecha;
        this.coste = coste;
    }
    
    public Pedido() {
        this.titlulo = null;
        this.fecha = null;
        this.coste = 0;
    }

public String getTitlulo() {
return titlulo;
}

public void setTitlulo(String titlulo) {
this.titlulo = titlulo;
}

public String getFecha() {
return fecha;
}

public void setFecha(String fecha) {
this.fecha = fecha;
}

public double getCoste() {
return coste;
}

public void setCoste(double coste) {
this.coste = coste;
}
    
    
}
