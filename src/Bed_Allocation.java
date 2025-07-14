import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Bed_Allocation extends JFrame{
    private JPanel frmBedAllocation;
    private JTextField txtPatId;
    private JComboBox cmbICU;
    private JTextField txtWard;
    private JTextField txtBed;
    private JTextArea txtaCause;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JComboBox cmbSector;
    private JTextField txtStatus;
    private JButton btnSearch;
    private JTextField txtBedNum;


    public Bed_Allocation(JFrame parent){
        setTitle("REGISTRATION");
        setContentPane(frmBedAllocation);
        setMinimumSize(new Dimension(530, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cmbSector.addItem("Maternity");
        cmbSector.addItem("Surgery");
        cmbSector.addItem("Natal Care");

        cmbICU.addItem("No");
        cmbICU.addItem("Yes");

        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnClearAll.addActionListener(new ActionListener() {
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
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        cmbSector.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboSearch();
            }
        });
        btnClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        txtBedNum.setVisible(false);

        setVisible(true);
    }
    private void comboSearch(){
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();
            String sector = (String) cmbSector.getSelectedItem();
            String sql = "SELECT * FROM bed_allocation WHERE sector = '" + sector + "' AND status = 'vacant'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
//                txtBedNum.setText(rs.getString("bed_number"));
                txtBed.setText(rs.getString("bed"));
                txtWard.setText(rs.getString("ward"));
                txtStatus.setText(rs.getString("status"));
            }else{
                JOptionPane.showMessageDialog(this, "All rooms are fully booked.", "Message", JOptionPane.INFORMATION_MESSAGE);
                clear();
            }
//            txtBedNum.setText(rs.getString("bed_number"));
            btnSave.setEnabled(true);
        } catch (SQLException e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "Consult the head director.", "Message", JOptionPane.INFORMATION_MESSAGE);
            clear();
        }
    }
    private void search() {
    }
    public void save(){

        String patId = txtPatId.getText();
        String icu = cmbICU.getActionCommand();
        String sector = cmbSector.getActionCommand();
        String bed = txtBed.getText();
        String ward = txtWard.getText();
        String cause = txtaCause.getText();


        if (patId.isEmpty() || icu.isEmpty() || sector.isEmpty() || bed.isEmpty() || ward.isEmpty() || cause.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all fields.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
//            String sql = "INSERT INTO bed_allocation (patient_id, is_icu_needed, sector, ward, bed, status, cause_or_description)" +
//                    "VALUES (?,?,?,?,?, 'vacant',?)";
//            String sql = "UPDATE bed_allocation SET (patient_id, is_icu_needed, sector, ward, bed, status, cause_or_description)" +
//                    "VALUES (?,?,?,?,?, 'occupied', ?) WHERE ward = '" + txtWard.getText() + "' AND bed = '" + txtBed.getText() + "'";
            String sql = "UPDATE bed_allocation SET patient_id = '" +txtPatId.getText()+"', is_icu_needed = '" + cmbICU.getSelectedItem() + "'," +
                    "sector = '" + cmbSector.getSelectedItem() + "', ward = '" + txtWard.getText() +"', bed = '" + txtBed.getText() + "'," +
                    "status = 'Booked', cause_or_description = '" + txtaCause.getText() +"'" +
                    "WHERE bed = '" + txtBed.getText()+ "'";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
//            preparedStatement.setString(1, txtPatId.getText());
//            String icu_needed = (String) cmbICU.getSelectedItem();
//            preparedStatement.setString(2, icu_needed);
//            String mySector = (String) cmbSector.getSelectedItem();
//            preparedStatement.setString(3, mySector);
//            preparedStatement.setString(4, txtWard.getText());
//            preparedStatement.setString(5, txtBed.getText());
//            preparedStatement.setString(6, txtStatus.getText());
//            preparedStatement.setString(6, txtaCause.getText());

//            preparedStatement.execute();

//                String sql3= "UPDATE bed_allocation SET status = 'Booked' WHERE sector = '" + cmbSector.getSelectedItem() + "' AND status='" + txtStatus.getText()+ "' AND " +
//                        "bed = '" + txtBed.getText() + "'";
//            String sql3= "UPDATE bed_allocation SET status = 'Booked' WHERE bed_number = '" + txtBedNum.getText()+ "'";
//            PreparedStatement prepareStatement =conn.prepareStatement(sql3);
            int addedRow = preparedStatement.executeUpdate();


            if (addedRow > 0){
                JOptionPane.showMessageDialog(this, "Patient record saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else{
                JOptionPane.showMessageDialog(this, "Patient record was not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }
            clear();
            stmt.close();
            conn.close();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "An error occured, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    private void clear(){
        txtaCause.setText("");
        txtStatus.setText("");
        txtBedNum.setText("");
        txtBed.setText("");
        txtWard.setText("");
        txtPatId.setText("");
        btnSave.setEnabled(false);
    }
    public void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Bed_Allocation bed_allocation = new Bed_Allocation(null);
    }
}
