package controlador;

import static controlador.Conexion.conexion;
import static controlador.Conexion.conectar;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import modelo.ArrayPedidos;
import modelo.ArrayProductos;
import modelo.Pedido;
import modelo.Producto;

public class Controlador {
    
    public Controlador() { }

    public void RegistrarUsuario(String nombre, int contra, String num_tlf, String credito){
        
         Producto pr;

         conectar();
         ResultSet rs = null;         

         try {         
             
                PreparedStatement pst1 = conexion.prepareStatement("SELECT id FROM usuarios WHERE nombre = ?"); 
                pst1.setString(1, nombre);
                rs = pst1.executeQuery();
                
                if( rs.next() ){
                
                    SQLException ex = null;
                     Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                     JOptionPane.showMessageDialog(null, "Nombre de usuario existente.");

                } else {

                    // INSERTAR NUEVO USUARIO
                    PreparedStatement pst;
                    pst = conexion.prepareStatement("INSERT INTO usuarios (nombre, contra, num_tlf, credito) VALUES (?,?,?,?)");
                    pst.setString(1, nombre);
                    pst.setString(2, contra+"");
                    pst.setString(3, num_tlf);
                    pst.setString(4, credito);
                    pst.executeUpdate();

                    PreparedStatement pst2= conexion.prepareStatement("SELECT id FROM usuarios WHERE nombre = ?"); 
                    pst2.setString(1, nombre);
                    rs = pst2.executeQuery();
                    rs.next();
                    
                    // CREACIÓN DE LA LISTA POR DEFECTO
                    PreparedStatement pst3 = conexion.prepareStatement("INSERT INTO listas (titulo, admin) VALUES (?,?)");
                    pst3.setString(1, "Lista 1");
                    pst3.setInt(2, rs.getInt("id"));
                    pst3.executeUpdate();
                    
                    JOptionPane.showMessageDialog(null, "Usuario creado correctamente");
                }
      
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al crear el nuevo usuario.");
         }
  
    }  
    
    
    public int IniciarSesion(String nombre, int contra){
        
         Producto pr;
         int id=0;

         conectar();
         ResultSet rs = null;         

         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT id, contra FROM usuarios WHERE nombre = ? AND contra= ?");
                pst.setString(1, nombre);
                pst.setString(2, contra+"");
                rs = pst.executeQuery();
                
                if(rs.next()) {    
                    
                     id= rs.getInt("id");
                    
                    return id;
                    
                } else {                   	
                    JOptionPane.showMessageDialog(null,"Usuario inexistente");
                }
      
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al crear el nuevo usuario.");
         }

        return 0;
    }
    
 
    
    public ArrayList getUserInfo(int id){   
        
         ArrayList<String> usuInfo = new ArrayList<>();

         conectar();
         ResultSet rs = null;         
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM usuarios WHERE id = ?"); 
            pst.setInt(1, id);
            rs = pst.executeQuery();
            
                rs.next();
                usuInfo.add(rs.getString("nombre"));
                usuInfo.add(rs.getString("num_tlf"));
                usuInfo.add( rs.getString("credito"));           
                
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al obtener la información.");
         }

        return usuInfo;
    }
    
    
    public ArrayProductos CargarTablaFav(int idUsu){
        
         ArrayProductos apr = new ArrayProductos();
         Producto pr;
        
         conectar();
         ResultSet rs = null;
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos JOIN favoritos ON productos.id = favoritos.id_prod WHERE id_usu = ?"); 
            pst.setInt(1, idUsu);
            rs = pst.executeQuery();
            
            while(rs.next()) {
                pr = new Producto();
                
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setSeccion(rs.getString("seccion"));
                pr.setImg(rs.getString("img"));
                
                apr.setProd(pr);
            }
        
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return apr;
    }
    

    
    public ArrayProductos CargarTablaFavBySecc(int idUsu, String seccion){
        
         ArrayProductos apr = new ArrayProductos();
         Producto pr;
        
         conectar();
         ResultSet rs = null;
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos JOIN favoritos ON productos.id = favoritos.id_prod WHERE id_usu = ? AND productos.seccion = ?"); 
            pst.setInt(1, idUsu);
            pst.setString(2, seccion);
            rs = pst.executeQuery();
            
            while(rs.next()) {
                pr = new Producto();
                
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setSeccion(rs.getString("seccion"));
                pr.setImg(rs.getString("img"));
                
                apr.setProd(pr);
            }
        
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return apr;
    }
    
    
    public void InsertFav(int idProd, int idUsu){
    
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO favoritos (id_usu, id_prod) VALUES (?,?)"); 
            pst.setInt(1, idUsu);
            pst.setInt(2, idProd);
            pst.execute();       
        
         } catch (SQLException ex) {
            //Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Este producto ya se encuentra entre tus favoritos.");
        }
         
    }
    
    
        public void DeleteProdFav(int idProd, int idUsu){
    
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("DELETE FROM favoritos WHERE id_usu = ? AND id_prod = ?"); 
            pst.setInt(1, idUsu);
            pst.setInt(2, idProd);
            pst.execute();       
        
         } catch (SQLException ex) {
            //Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Este producto no se encuentra entre tus favoritos.");
        }
         
    }
    
    
    public ArrayProductos CargarTablaProd(){
        
         ArrayProductos apr = new ArrayProductos();
         Producto pr;
        
         conectar();
         ResultSet rs = null;
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos"); 
            rs = pst.executeQuery();
            
            while(rs.next()) {
                pr = new Producto();
                
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setSeccion(rs.getString("seccion"));
                pr.setImg(rs.getString("img"));
                
                apr.setProd(pr);
            }

         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return apr;
    }
    
    
    
    public ArrayList getSecciones(){
         
         ArrayList<String> secc = new ArrayList<>();

         conectar();
         ResultSet rs = null;         
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM secciones"); 
            rs = pst.executeQuery();
            
            while(rs.next()) {
                
                    secc.add(rs.getString("seccion"));
            }
        
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return secc;
    }
    
    
    
        public ArrayProductos CargarTablaSec(String cat){
        
         ArrayProductos apr = new ArrayProductos();
         Producto pr;
        
         conectar();
         ResultSet rs = null;
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM productos WHERE seccion = ?"); 
            pst.setString(1, cat);
            rs = pst.executeQuery();
            
            while(rs.next()) {
                pr = new Producto();
                
                pr.setId(rs.getInt("id"));
                pr.setNombre(rs.getString("nombre"));
                pr.setPrecio(rs.getDouble("precio"));
                pr.setSeccion(rs.getString("seccion"));
                pr.setImg(rs.getString("img"));
                
                apr.setProd(pr);
            }
        
         } catch (SQLException ex) {
            
            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return apr;
    }
       
        
        public ArrayList getIdList(int idUsu){
        
            ArrayList<Integer> idList = new ArrayList<>();
        
            conectar();
            ResultSet rs, rs2 = null;
          
            try {         
               // OBTENGO IDs DE LISTAS ADMINISTRADAS
               PreparedStatement pst = conexion.prepareStatement("SELECT id FROM listas WHERE admin = ?"); 
               pst.setInt(1, idUsu);
               rs = pst.executeQuery();
            
               while (rs.next()){
            
                   idList.add(rs.getInt("id"));
               }
               
               // OBTENGO IDs DE LISTAS COMPARTIDAS
               PreparedStatement pst2 = conexion.prepareStatement("SELECT id_list FROM usu_list WHERE id_usu = ?"); 
               pst2.setInt(1, idUsu);
               rs = pst2.executeQuery();
            
               while (rs.next()){
            
                   idList.add(rs.getInt("id_list"));
               }
               
               
            } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
            }

            return idList;
        }
        
        
        public ArrayList getUserListsName(int idUsu){
        
         ArrayList<String> titulos = new ArrayList<>();
         ArrayList<Integer> idList = getIdList(idUsu);
                 
         conectar();
         ResultSet rs= null;
             
            try {         

                for ( int x = 0; x < idList.size(); x++){
        
                    PreparedStatement pst = conexion.prepareStatement("SELECT titulo FROM listas WHERE id = ?"); 
                    pst.setInt(1, idList.get(x));
                    rs= pst.executeQuery();
                
                    rs.next();
                    
                        titulos.add(rs.getString("titulo"));                    
                }    

                    
            } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
            }
        
         return titulos;
        }
        
        
        
        public ArrayProductos CargarTablaList(int cBoxPos, int idUsu){
        
         ArrayList<Integer> idList = getIdList(idUsu);
         ArrayProductos apr = new ArrayProductos();
         Producto pr;
        
         conectar();
         ResultSet rs, rs2 = null;
         int idLista = 0;
 
         try {         

            
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT id_prod, uds FROM prod_list WHERE id_list = ?"); 
            pst.setInt(1, idList.get(cBoxPos));
            rs = pst.executeQuery();
       
            while(rs.next()) {
            
                    PreparedStatement pst2;
                    pst2 = conexion.prepareStatement("SELECT * FROM productos WHERE id = ?"); 
                    pst2.setInt(1, rs.getInt("id_prod"));
                    rs2 = pst2.executeQuery();
            
                    rs2.next();
                    
                        pr = new Producto();
                        pr.setId(rs2.getInt("id"));
                        pr.setNombre(rs2.getString("nombre"));
                        pr.setPrecio(rs2.getDouble("precio"));
                        pr.setSeccion(rs2.getString("seccion"));
                        pr.setImg(rs2.getString("img"));
                        pr.setUds(rs.getInt("uds"));

                        apr.setProd(pr);   
            }
                     
        
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
        }
         
        return apr;
    }
              
        
        public boolean isSharedList(int cBoxPos, int idUsu){
        
         boolean sharedList = false;        
         ArrayList<Integer> idList = getIdList(idUsu);
                 
         conectar();
         ResultSet rs= null;
             
         try {
    
                PreparedStatement pst = conexion.prepareStatement("SELECT id_usu FROM usu_list WHERE id_list = ?"); 
                pst.setInt(1, idList.get(cBoxPos));
                rs= pst.executeQuery();
            
                if (rs.next()) {
                
                    sharedList = true;
                }
                
         } catch (SQLException ex) {
                Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
         }

         return sharedList;
        }
        
        
        public String getDateList(int cBoxPos, int idUsu){
        
         ArrayList<Integer> idList = getIdList(idUsu);
         String fecha = null;
         
         conectar();
         ResultSet rs = null;
 
         try {         

            
            PreparedStatement pst;
            pst = conexion.prepareStatement("SELECT fecha_envio FROM listas WHERE id = ?"); 
            pst.setInt(1, idList.get(cBoxPos));
            rs = pst.executeQuery();
            
            rs.next();
            
            fecha = rs.getString("fecha_envio");
            
         } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
         }

         return fecha;
        }
        
        
        public void InsertProdList(int idUsu, int cBoxPos, int idProd, int uds){
        
         ArrayList<Integer> idList = getIdList(idUsu);
        
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("INSERT INTO prod_list (id_list, id_prod, uds) VALUES (?,?,?)"); 
            pst.setInt(1, idList.get(cBoxPos));
            pst.setInt(2, idProd);
            pst.setInt(3, uds);
            pst.execute();       
        
         } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "No se ha podido añadir el producto a lista.");
         }
        }
        
        
        public void DeleteProdList(int idUsu, int cBoxPos, int idProd){
        
         ArrayList<Integer> idList = getIdList(idUsu);
        
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("DELETE FROM prod_list WHERE id_list = ? AND id_prod = ?"); 
            pst.setInt(1, idList.get(cBoxPos));
            pst.setInt(2, idProd);
            pst.execute();       
        
         } catch (SQLException ex) {
            //Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Este producto no se encuentra en la lista.");
         }
        }
        
        
        public void UpdateValorLista(int idUsu, int cBoxPos, double valor){
        
         ArrayList<Integer> idList = getIdList(idUsu);
         
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("UPDATE listas SET valor = ? WHERE id = ?"); 
            pst.setDouble(1, valor);
            pst.setInt(2, idList.get(cBoxPos));
            pst.executeUpdate();         
        
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "No se ha podido actualizar el valor de la  lista.");
         }
        }
        
        
         public void CrearLista(int idUsu, String titulo){
        
         conectar();
         ResultSet rs = null;
         
         try {         

            PreparedStatement pst = conexion.prepareStatement("SELECT id FROM listas WHERE titulo = ? AND admin = ?"); 
            pst.setString(1, titulo);
            pst.setInt(2, idUsu);
            rs = pst.executeQuery();

            if( rs.next() ){
                
                //SQLException ex = null;
                //Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Este título ya ha sido usado en una de tus listas.");

            } else {
             
                PreparedStatement pst2 = conexion.prepareStatement("INSERT INTO listas (titulo, admin) VALUES (?,?)"); 
                pst2.setString(1, titulo);
                pst2.setInt(2, idUsu);
                pst2.execute();  

                JOptionPane.showMessageDialog(null, "Lista creada con éxito.");    
            }
            
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
         }
        }
        
         
         
         public ArrayList getIdListAdmin(int idUsu){
        
            ArrayList<Integer> idListAdmin = new ArrayList<>();
        
            conectar();
            ResultSet rs = null;
          
            try {         
               // OBTENER IDs DE LISTAS ADMINISTRADAS
               PreparedStatement pst = conexion.prepareStatement("SELECT id FROM listas WHERE admin = ?"); 
               pst.setInt(1, idUsu);
               rs = pst.executeQuery();
            
               while (rs.next()){
            
                   idListAdmin.add(rs.getInt("id"));
               }
               
             } catch (SQLException ex) {

                    Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                    JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
             }

            return idListAdmin;
        }
        
        
         
        public ArrayList getAdminListsName(int idUsu){  
        
         ArrayList<String> titulos = new ArrayList<>();
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
                 
         conectar();
         ResultSet rs= null;
             
         try {         

            for ( int x = 0; x < idListAdmin.size(); x++){
                // OBTENER EL NOMBRE DE LAS LISTAS DEL USUARIO QUE HA INICIADO SESIÓN
                PreparedStatement pst = conexion.prepareStatement("SELECT titulo FROM listas WHERE id = ?"); 
                pst.setInt(1, idListAdmin.get(x));
                rs= pst.executeQuery();
            
                while(rs.next()) {  
                
                    titulos.add(rs.getString("titulo"));
                }
                
            }    
         
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
         }

         return titulos;
        }
        
        
        public void DeleteList(int idUsu, int cBoxPos){
        
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
        
         conectar();
         ResultSet rs = null;
         
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("DELETE FROM listas WHERE id = ?"); 
            pst.setInt(1, idListAdmin.get(cBoxPos));
            pst.execute();       
            
            JOptionPane.showMessageDialog(null, "Lista eliminada correctamente.");
        
         } catch (SQLException ex) {
            //Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
            JOptionPane.showMessageDialog(null, "Este producto no se encuentra en la lista.");
         }
        
        }
        
        
        
        public ArrayList getListParticipants(int idUsu, int cBoxPos){
        
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
         ArrayList<String> usuarios = new ArrayList<>();
        
         conectar();
         ResultSet rs, rs2 = null;
         
         try {    
                PreparedStatement pst = conexion.prepareStatement("SELECT id_usu  FROM usu_list WHERE id_list= ?"); 
                pst.setInt(1, idListAdmin.get(cBoxPos));
                rs = pst.executeQuery();
        
                while (rs.next()){

                PreparedStatement pst2 = conexion.prepareStatement("SELECT nombre  FROM usuarios WHERE id= ?"); 
                pst2.setInt(1, rs.getInt("id_usu"));
                rs2 = pst2.executeQuery();
                
                rs2.next();
                usuarios.add(rs2.getString("nombre"));
                
                }
                
         } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
         }
         
         return usuarios;
        }
        
        
        public void AgregarUsuList (int idUsu, String nomUsu, int cBoxPos){
         
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
         
         conectar();
         ResultSet rs = null;
         
         try {    
                PreparedStatement pst = conexion.prepareStatement("SELECT id FROM usuarios WHERE nombre = ?"); 
                pst.setString(1, nomUsu);
                rs = pst.executeQuery();
        
                rs.next();
                
                PreparedStatement pst2 = conexion.prepareStatement("INSERT INTO usu_list (id_usu, id_list) VALUES (?,?)"); 
                pst2.setInt(1, rs.getInt("id"));
                pst2.setInt(2, idListAdmin.get(cBoxPos));
                pst2.execute();  
    
         } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Usuario no existe.");
         }
        }
        
        
        public void QuitarUsuList(int idUsu, int cBoxPos, String nomUsu){  
        
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
        
         conectar();
         ResultSet rs = null;
         
         try {    
                PreparedStatement pst = conexion.prepareStatement("SELECT id FROM usuarios WHERE nombre = ?"); 
                pst.setString(1, nomUsu);
                rs = pst.executeQuery();
        
                rs.next();
                
                PreparedStatement pst2 = conexion.prepareStatement("DELETE FROM usu_list WHERE id_usu = ? AND id_list = ?"); 
                pst2.setInt(1, rs.getInt("id"));
                pst2.setInt(2, idListAdmin.get(cBoxPos));
                pst2.execute();    
    
         } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
         }
        }
        
        
        public void UpdateFechaList(String fecha, int periodo, boolean vaciar, int idUsu, int cBoxPos){
        
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
         conectar();
         ResultSet rs = null;
         
         try {    
                PreparedStatement pst = conexion.prepareStatement("UPDATE listas SET fecha_envio= ?, periodo = ?, vaciar = ? WHERE id = ?"); 
                pst.setString(1, fecha);
                pst.setInt(2, periodo);
                pst.setBoolean(3, vaciar);
                pst.setInt(4, idListAdmin.get(cBoxPos));
                pst.executeUpdate();  

                JOptionPane.showMessageDialog(null, "Datos de envío actualizados.");
    
         } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Error al ctualizar datos de pedido.");
         }        
        }
        
        
        public ArrayList getInfoPedido(int idUsu, int cBoxPos){
        
         ArrayList<Integer> idListAdmin = getIdListAdmin(idUsu);
         ArrayList<String> infoFecha = new ArrayList<>();
        
         conectar();
         ResultSet rs = null;
         
         try {    
                PreparedStatement pst = conexion.prepareStatement("SELECT fecha_envio, periodo, vaciar FROM listas WHERE id = ?"); 
                pst.setInt(1, idListAdmin.get(cBoxPos));
                rs = pst.executeQuery();
        
                rs.next();
                
                infoFecha.add(0, rs.getString("fecha_envio"));
                infoFecha.add(1, String.valueOf(rs.getInt("periodo")));
                infoFecha.add(2, String.valueOf(rs.getBoolean("vaciar")));
    
         } catch (SQLException ex) {

                Logger.getLogger(Controlador.class.getName()).log(Level.INFO, null, ex);
                JOptionPane.showMessageDialog(null, "Error al insertar nueva lista.");
         }
         
         return infoFecha;
        }
        
        
        public ArrayPedidos CargarPedidos(int idUsu){        
        
         ArrayPedidos ape = new ArrayPedidos();
         Pedido pe;
        
         conectar();
         ResultSet rs,rs2 = null;         
             
         try {         
             
            PreparedStatement pst = conexion.prepareStatement("SELECT * FROM pedidos JOIN listas ON pedidos.id_list = listas.id WHERE listas.admin = ? ORDER BY fecha DESC"); 
            pst.setInt(1, idUsu);
            rs = pst.executeQuery();
            
            while(rs.next()) {
            
                PreparedStatement pst2 = conexion.prepareStatement("SELECT titulo FROM listas WHERE id = ?"); 
                pst2.setInt(1, rs.getInt("id_list"));
                rs2 = pst2.executeQuery();
                
                rs2.next();
                
                pe= new Pedido();
                pe.setTitlulo(rs2.getString("titulo"));
                pe.setFecha(rs.getString("fecha"));
                pe.setCoste(rs.getDouble("coste"));
                
                ape.setPedido(pe);
            }
        
         } catch (SQLException ex) {

            Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error al cargar tabla.");
         }
         
         return ape;
        }
        
}
