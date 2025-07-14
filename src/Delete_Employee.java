import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Delete_Employee extends JFrame{
    private JTextField txtEmpId;
    private JTextField txtFname;
    private JTextField txtLname;
    private JButton btnDelete;
    private JButton btnBack;
    private JPanel frmDeleteEmployee;
    private JTextField txtGender;
    private JButton btnSearch;
    private JTextField txtUsername;
    private JPasswordField txtpPassword;


    public Delete_Employee(JFrame parent){
        setTitle("Delete Employee");
        setContentPane(frmDeleteEmployee);
        setMinimumSize(new Dimension(530, 380));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                delete();
            }
        });
//        btnNext.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                next();
//            }
//        });

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });

        setVisible(true);
    }

    public void delete(){
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM employees WHERE employee_ID = '" + txtEmpId.getText() + "' OR last_name = '" + txtLname.getText() + "'";
            String sql1 = "DELETE FROM login WHERE Username = '" + txtUsername.getText() + "' AND Password = '" + String.valueOf(txtpPassword.getPassword()) + "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            PreparedStatement preparedStatement1 = conn.prepareStatement(sql1);
            preparedStatement1.executeUpdate();
            int dataRecorded = preparedStatement.executeUpdate();
            if (dataRecorded > 0) {

                JOptionPane.showMessageDialog(this, "Employee successfully deleted", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Employee failed to be deleted, try again!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            conn.close();
            stmt.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void search(){
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM employees WHERE employee_ID = '" + txtEmpId.getText() + "' OR last_name = '" + txtLname.getText() + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtFname.setText(rs.getString("name"));
                txtGender.setText(rs.getString("sex"));
                JOptionPane.showMessageDialog(this, "Employee found", "Message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Employee not found.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            btnDelete.setEnabled(true);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Unable to connect to the database, try again or call the IT technician.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Delete_Employee delete_employee = new Delete_Employee(null);
    }
}
