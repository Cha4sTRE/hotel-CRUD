package GUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import modelo.Cliente;
import modelo.datos.IServicioCliente;
import modelo.datos.ServicioCliente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class NuevoCliente extends JFrame{

    private JPanel ventanaNewCliente;
    private JPanel panel1;
    private JPanel formulario;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCedula;
    private JTextField txtTelefono;
    private JTextField txtDireccion;
    private JTextField txtEmail;
    private JButton btnGuardar;
    private JButton btnActualizar;
    private JLabel titulo2;
    private JLabel titulo1;
    private Menu menu;
    public NuevoCliente(Menu menu) {
        this.menu = menu;
        btnActualizar.enable(false);
        btnActualizar.setVisible(false);
        IServicioCliente servicioCliente= new ServicioCliente();
        FlatMacDarkLaf.setup();
        initComponents();
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                int cedula = Integer.parseInt(txtCedula.getText());
                int telefono = Integer.parseInt(txtTelefono.getText());
                String direccion = txtDireccion.getText();
                String email = txtEmail.getText();
                try {
                    boolean insert= servicioCliente.agregarCliente(new Cliente(nombre,apellido,cedula,telefono,direccion,email));
                    if(insert){
                        JOptionPane.showMessageDialog(null, "Cliente agregado exitosamente");
                        dispose();
                        menu.listarClientes();
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
    public void actualizarCliente(Cliente cliente,IServicioCliente servicioCliente){
        btnGuardar.setEnabled(false);
        btnGuardar.setVisible(false);
        btnActualizar.setVisible(true);
        try {
            boolean encontrado=servicioCliente.buscarCliente(cliente);
            titulo1.setText("Actualizar Cliente");
            titulo2.setText(cliente.getNombre());
            btnActualizar.addActionListener(e -> {
                if(encontrado){

                    String nombre = txtNombre.getText();
                    String apellido = txtApellido.getText();
                    int cedula = Integer.parseInt(txtCedula.getText());
                    int telefono = Integer.parseInt(txtTelefono.getText());
                    String direccion = txtDireccion.getText();
                    String email = txtEmail.getText();
                    boolean actualizado= false;
                    try {
                        actualizado = servicioCliente.actualizarCliente(new Cliente(cliente.getIdCliente(),nombre,apellido,cedula,telefono,direccion,email));
                        if(actualizado){
                            JOptionPane.showMessageDialog(null, "Cliente actualizado exitosamente");
                            menu.listarClientes();
                            dispose();
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }

                }
            });

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }



    }

    private void initComponents(){

        setContentPane(ventanaNewCliente);
        setSize(700, 500);
        setTitle("Nuevo Cliente");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);

    }

    };
