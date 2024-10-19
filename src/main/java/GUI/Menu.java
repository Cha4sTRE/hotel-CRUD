package GUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import modelo.Cliente;
import modelo.datos.ServicioCliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame {

    private JPanel mainPanel;
    private JPanel panel1;
    private JTable tableClientes;
    private JButton btnEliminar;
    private JButton btnActualizar;
    private JMenuItem btnSalir;
    private JMenuItem btnNuevo;

    private DefaultTableModel modelClientes= new DefaultTableModel();
    public Menu() throws SQLException {

        ServicioCliente servicioCliente= new ServicioCliente();
        initComponents();
        inicTable(servicioCliente);
        btnSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        btnNuevo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NuevoCliente nuevoCliente= new NuevoCliente();
                nuevoCliente.setVisible(true);
                dispose();
            }
        });
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id;
                String nombre="";
                try {
                    var clienteEliminar=servicioCliente.listaClientes().get(tableClientes.getSelectedRow());
                    for(var cliente:servicioCliente.listaClientes()){
                        if(cliente.getIdCliente()==clienteEliminar.getIdCliente()){
                            id=cliente.getIdCliente();
                            nombre=cliente.getNombre();
                            servicioCliente.eliminarCliente(new Cliente(id));
                            modelClientes.removeRow(tableClientes.getSelectedRow());
                        }
                    }
                    JOptionPane.showMessageDialog(null,"Cliente "+nombre+" eliminado");
                    servicioCliente.listaClientes().forEach(System.out::println);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }


            }
        });
        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idCliente=servicioCliente.listaClientes().get(tableClientes.getSelectedRow()).getIdCliente();
                    NuevoCliente nuevoCliente= new NuevoCliente();
                    nuevoCliente.setVisible(true);
                    dispose();
                    nuevoCliente.actualizarCliente(new Cliente(idCliente),servicioCliente);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
    }
    private void initComponents() {
        setContentPane(mainPanel);
        setSize(800, 600);
        setTitle("Menu");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }
    public void inicTable(ServicioCliente servicioCliente) throws SQLException {
        final String[] COLUMNAS={"ID","Nombre","Apellido","Cedula","Direccion","Telefono","email"};
        tableClientes.setEnabled(false);
        //inicializamos un objeto con los array de las fillas
        List<Object[]> datos= new ArrayList<>();
        //insertamos los array al instante para evitar llamar a arrayCliente durante la construccion de la tabla
        for(int i=0;i<servicioCliente.listaClientes().size();i++){
            datos.add(arrayClientes(i,servicioCliente));
        }
        modelClientes.setColumnIdentifiers(COLUMNAS);
        tableClientes.setModel(modelClientes);
        //insertamos cada uno de los arrays del arraylist
        for(Object[] fila:datos){
            modelClientes.addRow(fila);
        }
        // Vuelve a habilitar la tabla y refresca la vista
        tableClientes.setEnabled(true);
        tableClientes.updateUI();  // Redibuja la tabla una vez que todas las filas se han agregado

    }

    private String[] arrayClientes(int i,ServicioCliente servicioCliente){
        try {
            var cliente=servicioCliente.listaClientes().get(i);
            String[] clientesArray=new String[7];
            clientesArray[0]=String.valueOf(cliente.getIdCliente());
            clientesArray[1]=cliente.getNombre();
            clientesArray[2]=cliente.getApellido();
            clientesArray[3]=String.valueOf(cliente.getCedula());
            clientesArray[4]=cliente.getDireccion();
            clientesArray[5]=String.valueOf(cliente.getTelefono());
            clientesArray[6]=cliente.getEmail();

            return clientesArray;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {
        FlatMacDarkLaf.setup();
        Menu menu = new Menu();
        menu.initComponents();

    }

}

