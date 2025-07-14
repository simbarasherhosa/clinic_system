import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ScreenDatabase extends JFrame{
    private JTextField txtTemp;
    private JButton btnBack;
    private JTextField txtPatId;
    private JPanel frmScreenDB;
    private JButton btnSearch;
    private JTextField txtHearbeat;
    private JTextField txtSkin;
    private JTextArea txtaRemarks;
    private JTextField txtBP;

    public ScreenDatabase(JFrame parent){
        setTitle("Screening Database");
        setContentPane(frmScreenDB);
        setMinimumSize(new Dimension(450,300));
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
            String sql = "SELECT * FROM screening WHERE patient_id = '" + txtPatId.getText() + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtaRemarks.setText(rs.getString("remarks"));
                txtHearbeat.setText(rs.getString("heartbeat_rate_per_min"));
                txtSkin.setText(rs.getString("skin_status"));
                txtTemp.setText(rs.getString("temperature"));
                txtBP.setText(rs.getString("blood_pressure"));

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
        ScreenDatabase screenDatabase = new ScreenDatabase(null);
    }
}
