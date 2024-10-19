package modelo.coneccion;

import java.sql.Connection;
import java.sql.DriverManager;

public class DataBase {

    public static Connection coneccion(){

        Connection conexion=null;
        String db="hotel_fesc";
        String url="jdbc:mysql://localhost:3306/"+db;
        String user="root";
        String password="12345";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion= DriverManager.getConnection(url,user,password);
        }catch (Exception e){
            System.out.println("error de coneccion: "+e);
        }
        return conexion;
    }


}
