import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class BedAlloDatabase extends JFrame{
    private JTextField txtICU;
    private JTextArea txtaCause;
    private JTextField txtPatId;
    private JButton btnSearch;
    private JButton btnBack;
    private JPanel frmBedAllocDatabase;
    private JTextField txtSector;
    private JTextField txtWard;
    private JTextField txtBed;
    private JTextField txtStatus;

    public BedAlloDatabase(JFrame parent){
        setTitle("Bed Allocation Database");
        setContentPane(frmBedAllocDatabase);
        setMinimumSize(new Dimension(550, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        btnBack.addActionListener(new ActionListener() {
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
            String sql = "SELECT * FROM bed_allocation WHERE patient_id = '" + txtPatId.getText() + "'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtBed.setText(rs.getString("bed"));
                txtWard.setText(rs.getString("ward"));
                txtStatus.setText(rs.getString("status"));
                txtICU.setText(rs.getString("is_icu_needed"));
                txtaCause.setText(rs.getString("cause_or_description"));
            }else{
                JOptionPane.showMessageDialog(this, "All rooms are fully booked.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "Consult the head director.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        BedAlloDatabase bedAlloDatabase = new BedAlloDatabase(null);
    }

}
