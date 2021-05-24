package modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class ArrayPedidos implements Serializable{

    public ArrayList<Pedido> arrayPedidos;
    
    public ArrayPedidos (){
        arrayPedidos = new ArrayList<>();
    }
    
    public Pedido getPedido(int p){
        return arrayPedidos.get(p);
    }
    public void setPedido (Pedido ped){
        arrayPedidos.add(ped);
    }
    public void eliminar (int p) {
        arrayPedidos.remove(p);     
    } 
        public int longitud (){
        return arrayPedidos.size();
    }
}
