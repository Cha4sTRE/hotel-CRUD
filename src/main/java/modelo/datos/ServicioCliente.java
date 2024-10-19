package modelo.datos;

import modelo.Cliente;
import static modelo.coneccion.DataBase.coneccion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioCliente implements IServicioCliente{
    @Override
    public List<Cliente> listaClientes() throws SQLException {
        List<Cliente> listaClientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection conexion= coneccion();
        String sentenciaSql="SELECT * FROM clientes ORDER BY id";
        ps = conexion.prepareStatement(sentenciaSql);
        rs=ps.executeQuery();
        while(rs.next()){
            Cliente cliente= new Cliente();
            cliente.setIdCliente(Integer.parseInt(rs.getString("id")));
            cliente.setNombre(rs.getString("nombre"));
            cliente.setApellido(rs.getString("apellido"));
            cliente.setCedula(Integer.parseInt(rs.getString("cedula")));
            cliente.setTelefono(Integer.parseInt(rs.getString("telefono")));
            cliente.setDireccion(rs.getString("direccion"));
            cliente.setEmail(rs.getString("email"));
            listaClientes.add(cliente);
        }
        conexion.close();
        return listaClientes;
    }

    @Override
    public boolean buscarCliente(Cliente cliente) throws SQLException{

        PreparedStatement ps;
        ResultSet rs;
        Connection conexion= coneccion();
        String sentenciaSql="SELECT * FROM clientes WHERE id=?";

        try{

            ps=conexion.prepareStatement(sentenciaSql);
            ps.setInt(1,cliente.getIdCliente());
            rs=ps.executeQuery();
            if(rs.next()){
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setCedula(Integer.parseInt(rs.getString("cedula")));
                cliente.setTelefono(Integer.parseInt(rs.getString("telefono")));
                cliente.setDireccion(rs.getString("direccion"));
                cliente.setEmail(rs.getString("email"));
                return true;
            }
        }catch (Exception e){
            System.out.println("error al cuscar cliente: "+e.getMessage());
        }finally {
            conexion.close();
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) throws SQLException{

        PreparedStatement ps;
        ResultSet rs;
        Connection conexion= coneccion();
        String sentenciaSql="INSERT INTO clientes(nombre,apellido,cedula,telefono,direccion,email) "+" VALUES(?,?,?,?,?,?)";
        try{

            ps=conexion.prepareStatement(sentenciaSql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getCedula());
            ps.setInt(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getEmail());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("error al guardar cliente "+e.getMessage());
        }finally {
            conexion.close();
        }

        return false;
    }

    @Override
    public boolean actualizarCliente(Cliente cliente) throws SQLException{

        PreparedStatement ps;
        Connection conexion= coneccion();
        String sentenciaSql="UPDATE clientes SET nombre=?,apellido=?,cedula=?,telefono=?,direccion=?,email=? "+" WHERE id=?";

        try{

            ps=conexion.prepareStatement(sentenciaSql);
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setInt(3, cliente.getCedula());
            ps.setInt(4, cliente.getTelefono());
            ps.setString(5, cliente.getDireccion());
            ps.setString(6, cliente.getEmail());
            ps.setInt(7, cliente.getIdCliente());
            ps.execute();
            return true;

        }catch (Exception e){
            System.out.println("error al actualizar cliente: "+e.getMessage());
        }finally {
            conexion.close();
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) throws SQLException{
        Connection conexion= coneccion();
        try{
            PreparedStatement ps=conexion.prepareStatement("DELETE FROM clientes WHERE id=?");
            ps.setInt(1,cliente.getIdCliente());
            ps.execute();
            return true;
        }catch (Exception e){
            System.out.println("ha ocurrido un error al eliminar: "+e.getMessage());
        }finally {
            conexion.close();
        }


        return false;
    }
}
