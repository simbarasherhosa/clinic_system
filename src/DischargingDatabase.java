import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DischargingDatabase extends JFrame{
    private JTextField txtTreated;
    private JTextArea txtaRemarks;
    private JTextField txtPatid;
    private JButton btnSearch;
    private JButton btnBack;
    private JPanel frmDischargingDatabase;

    public DischargingDatabase(JFrame parent){
        setTitle("Discharging Database");
        setContentPane(frmDischargingDatabase);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
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
            String sql = "SELECT * FROM patient_registration WHERE patient_id = '" + txtPatid.getText() + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtTreated.setText(rs.getString("is_patient_treated"));
                txtaRemarks.setText(rs.getString("remarks"));

            }else{
                JOptionPane.showMessageDialog(null, "Patient not found!", "Error", JOptionPane.ERROR_MESSAGE);
            }

    } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Failed, please try again.", "Message", JOptionPane.INFORMATION_MESSAGE);

        }
    }
    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(true);
    }
    public static void main(String[] args) {
        DischargingDatabase dischargingDatabase = new DischargingDatabase(null);
    }
}
