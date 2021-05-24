package modelo;

import java.io.Serializable;

public class Producto implements Serializable{
    
    private int id;
    private String nombre;
    private double precio;
    private String seccion;
    private String img;
    private int uds;

    
    public Producto(int id, String nombre, double precio, String seccion, String img, int uds) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.seccion = seccion;
        this.img = img;
        this.uds = uds;
    }

    public Producto() {
         this.id = 0;
        this.nombre = null;
        this.precio = 0;
        this.seccion = null;
        this.img = null;
        this.uds = 0;
    }

public int getId() {
return id;
}

public void setId(int id) {
this.id = id;
}

public String getNombre() {
return nombre;
}

public void setNombre(String nombre) {
this.nombre = nombre;
}

public double getPrecio() {
return precio;
}

public void setPrecio(double precio) {
this.precio = precio;
}

public String getSeccion() {
return seccion;
}

public void setSeccion(String seccion) {
this.seccion = seccion;
}

public String getImg() {
return img;
}

public void setImg(String img) {
this.img = img;
}

public int getUds() {
return uds;
}

public void setUds(int uds) {
this.uds = uds;
}

  
    
}
