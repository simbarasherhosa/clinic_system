import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.*;
import java.text.SimpleDateFormat;

public class Registration extends JFrame{

    private JPanel frmRegistration;
    private JTextField txtName;
    private JTextField txtLname;
    private JComboBox cmbGender;
    private JTextField txtDOB;
    private JTextField txtKin;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JTextField txtPatId;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClear;

    public Registration(JFrame parent){
        setTitle("REGISTRATION");
        setContentPane(frmRegistration);
        setMinimumSize(new Dimension(530, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cmbGender.addItem("Male");
        cmbGender.addItem("Female");

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });setVisible(true);
    }

    private void save() {
        String name = txtName.getText();
        String last_name = txtLname.getText();
        String dob = txtDOB.getText();
        String gender = cmbGender.getActionCommand();
        String contact_details = txtContact.getText();
        String home_address = txtAddress.getText();
        String next_of_kin = txtKin.getText();


        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            if (name.isEmpty() || last_name.isEmpty() || dob.isEmpty() || gender.isEmpty() || contact_details.isEmpty() || home_address.isEmpty() || next_of_kin.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter all fields", "Message", JOptionPane.ERROR_MESSAGE);

            }else {


            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO patient_registration (name, last_name, gender, dob, contact_details, home_address, next_of_kin)" +
                    " VALUES (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtName.getText());
            preparedStatement.setString(2, txtLname.getText());
            preparedStatement.setString(3, (String) cmbGender.getSelectedItem());
            preparedStatement.setString(4, txtDOB.getText());
            preparedStatement.setString(5, txtContact.getText());
            preparedStatement.setString(6, txtAddress.getText());
            preparedStatement.setString(7, txtKin.getText());
//            preparedStatement.executeUpdate();
            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0) {

                JOptionPane.showMessageDialog(this, "Patient details successfully saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }


            //////////////////////
            try {
                Statement statement = conn.createStatement();
                String selectSql = "SELECT * FROM patient_registration WHERE last_name = '" + txtLname.getText() + "' AND dob = '" + txtDOB.getText() + "'";
                ResultSet rs = statement.executeQuery(selectSql);

                if (rs.next()) {
                    txtPatId.setText(rs.getString("patient_id"));
                    JOptionPane.showMessageDialog(this, "Allocated patient ID is " + txtPatId.getText() +".", "ID allocation", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException | HeadlessException e) {
//                throw new RuntimeException(e);
                JOptionPane.showMessageDialog(this, "An error occured, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            ////////////////////
                conn.close();
                stmt.close();
            }
        } catch (Exception e) {
//                throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "An error occured, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

        }

    private void clear(){
        txtName.setText("");
        txtLname.setText("");
        txtDOB.setText("");
        txtContact.setText("");
        txtAddress.setText("");
        txtKin.setText("");
        txtPatId.setText("");
    }
    private void  back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Registration registration = new Registration(null);
    }
}