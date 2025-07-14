import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Discharging extends JFrame{
    private JPanel frmDischarging;
    public JTextField txtPatId;
    public JComboBox cmbTreated;
    public JTextArea txtaRemarks;
    public JButton btnSave;
    public JButton btnBack;
    public JButton btnClearAll;

    public Discharging(JFrame parent){
        setTitle("DISCHARGING");
        setContentPane(frmDischarging);
        setMinimumSize(new Dimension(400, 300));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cmbTreated.addItem("No");
        cmbTreated.addItem("Yes");

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

        cmbTreated.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isTreated();
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
    private void isTreated(){
        if (cmbTreated.getSelectedItem() == "Yes"){
            txtaRemarks.setText("Patient is treated");
            txtaRemarks.setForeground(Color.BLACK);
        }else {
            txtaRemarks.setText("");
        }
    }
    public void save(){
        String patId = txtPatId.getText();
        String treated = cmbTreated.getActionCommand();
        String remarks = txtaRemarks.getText();

        if (patId.isEmpty() || treated.isEmpty() || remarks.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all fields.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";
        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO discharging (patient_id, is_patient_treated, remarks)" +
                    "VALUES (?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtPatId.getText());
            String patientTreated = (String) cmbTreated.getSelectedItem();
            preparedStatement.setString(2, patientTreated);
            preparedStatement.setString(3, txtaRemarks.getText());
            preparedStatement.execute();
//            int addedRow = preparedStatement.executeUpdate();

            Statement statement = conn.createStatement();
            String bed_removal_sql = "UPDATE bed_allocation SET patient_id = '', is_icu_needed = ''" +
                    "status = 'vacant', cause_or_description = '' WHERE patient_id = '" + txtPatId.getText()+ "'";

//            Statement statement = conn.createStatement();
            PreparedStatement preparedStatement1 = conn.prepareStatement(bed_removal_sql);

            int addedRow1 = preparedStatement1.executeUpdate();

            if (addedRow1 > 0){
                JOptionPane.showMessageDialog(this, "Patient record saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Patient record was not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }
            stmt.close();
            conn.close();
        } catch (Exception e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "An error occured, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

    }
    private void clear(){
        txtPatId.setText("");
        txtaRemarks.setText("");
    }
    public void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Discharging discharging = new Discharging(null);
    }
}
