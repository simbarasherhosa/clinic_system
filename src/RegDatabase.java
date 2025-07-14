import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class RegDatabase extends JFrame{
    private JPanel frmRegDatabase;
    private JTextField txtPatId;
    private JTextField txtLname;
    private JTextField txtName;
    private JTextField txtGender;
    private JTextField txtDOB;
    private JTextField txtContact;
    private JTextField txtAddress;
    private JTextField txtKin;
    private JButton btnSearch;
    private JButton btnBack;
    private JTextField txtId;
    private JTextField txtLastName;

    public RegDatabase(JFrame parent){
        setTitle("Registration Database");
        setContentPane(frmRegDatabase);
        setMinimumSize(new Dimension(580, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        setVisible(true);
    }

    private void search(){
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM patient_registration WHERE patient_id = '" + txtPatId.getText() + "' OR last_name = '" + txtLname.getText() + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtGender.setText(rs.getString("gender"));
                txtName.setText(rs.getString("name"));
                txtAddress.setText(rs.getString("home_address"));
                txtContact.setText(rs.getString("contact_details"));
                txtKin.setText(rs.getString("next_of_kin"));
                txtDOB.setText(rs.getString("dob"));
                txtLastName.setText(rs.getString("last_name"));
                txtId.setText(rs.getString("patient_id"));

            }else{
                JOptionPane.showMessageDialog(null, "Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

        }catch (SQLException e){
                JOptionPane.showMessageDialog(null, "Failed, please try again.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        RegDatabase regDatabase = new RegDatabase(null);

    }
}
