package controlador;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    public static Connection conexion = null;
     public static Connection conectar(){
        
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/smarket?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", "root", "");
                    
        }catch(Exception e){
            e.printStackTrace();
        }
        return conexion;
    }    
}
