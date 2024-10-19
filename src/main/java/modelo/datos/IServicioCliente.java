package modelo.datos;

import modelo.Cliente;

import java.sql.SQLException;
import java.util.List;

public interface IServicioCliente {

    //CRUD
    List<Cliente> listaClientes() throws SQLException;
    boolean buscarCliente(Cliente cliente) throws SQLException;
    boolean agregarCliente(Cliente cliente) throws SQLException;
    boolean actualizarCliente(Cliente cliente) throws SQLException;
    boolean eliminarCliente(Cliente cliente) throws SQLException;

}
