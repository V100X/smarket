package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ArrayProductos implements Serializable{
    public ArrayList<Producto> arrayProd;
    
    public ArrayProductos (){
        arrayProd = new ArrayList<>();
    }
    
    public Producto getProd(int p){
        return arrayProd.get(p);
    }
    public void setProd (Producto prod){
        arrayProd.add(prod);
    }
    public void eliminar (int p) {
        arrayProd.remove(p);     
    } 
        public int longitud (){
        return arrayProd.size();
    }
        
}
