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
    private DefaultTableModel modelClientes;
    private ServicioCliente servicioCliente;
    public Menu() throws SQLException {


        initComponents();
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
    private void createUIComponents() throws SQLException {
        // TODO: place custom component creation code here
        this.modelClientes=new DefaultTableModel(0,0);
        final String[] COLUMNAS={"ID","Nombre","Apellido","Cedula","Direccion","Telefono","email"};

        modelClientes.setColumnIdentifiers(COLUMNAS);
        this.tableClientes=new JTable(modelClientes);

        listarClientes();
    }

    private void listarClientes(){
        this.servicioCliente=new ServicioCliente();
        try {
            modelClientes.setRowCount(0);
            var clientes=servicioCliente.listaClientes();
            clientes.forEach(cliente->{
                Object[] filaClientes={
                    cliente.getIdCliente(),
                            cliente.getNombre(),
                            cliente.getApellido(),
                            String.valueOf(cliente.getCedula()),
                            cliente.getDireccion(),
                            String.valueOf(cliente.getTelefono()),
                            cliente.getEmail()
                };
                modelClientes.addRow(filaClientes);
            });

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

