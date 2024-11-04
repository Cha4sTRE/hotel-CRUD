package GUI;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;

public class Habitacion extends JFrame {
    private JPanel panel1;
    private JLabel titulo2;
    private JLabel titulo1;
    private JPanel formulario;
    private JButton btnGuardar;
    private JTextArea txtDescripcion;
    private JComboBox estado1;
    private JCheckBox reservar;
    private JPanel panelHab;
    private JTextField txtPrecio;
    private JSpinner spinner1;

    public Habitacion() {
        FlatMacDarkLaf.setup();
        initComponents();
    }

    private void initComponents() {
        setContentPane(panelHab);
        setSize(700, 500);
        setTitle("Habitacines");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

}
