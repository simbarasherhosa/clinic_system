//import javafx.stage.Screen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Screening extends JFrame{
    private JTextField txtPatId;
    private JTextField txtTemp;
    private JTextField txtHeartbeat;
    private JTextField txtBP;
    private JTextArea txtaRemarks;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JComboBox cmbSkinStatus;
    private JPanel frmScreening;

    public Screening(JFrame parent){
        setTitle("SCREENING");
        setContentPane(frmScreening);
        setMinimumSize(new Dimension(530, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cmbSkinStatus.addItem("Normal");
        cmbSkinStatus.addItem("Dry");

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
        setVisible(true);
    }

    private void save(){
        String patId = txtPatId.getText();
        String temp = txtTemp.getText();
        String heartbeat = txtHeartbeat.getText();
        String bp = txtBP.getText();
        String skin = cmbSkinStatus.getActionCommand();
        String remarks = txtaRemarks.getText();


        if (patId.isEmpty() || temp.isEmpty() || heartbeat.isEmpty() || bp.isEmpty() || skin.isEmpty() || remarks.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO screening (patient_id, temperature, heartbeat_rate_per_min, blood_pressure, skin_status, remarks)" +
                    " VALUES (?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtPatId.getText());
            preparedStatement.setString(2, txtTemp.getText());
            preparedStatement.setString(3, txtHeartbeat.getText());
            preparedStatement.setString(4, txtBP.getText());
            preparedStatement.setString(5, (String) cmbSkinStatus.getSelectedItem());
            preparedStatement.setString(6, txtaRemarks.getText());

//            preparedStatement.executeUpdate();
            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Patient details saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
            stmt.close();

        } catch (Exception e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "An error occured, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void clear(){
        txtPatId.setText("");
        txtTemp.setText("");
        txtHeartbeat.setText("");
        txtBP.setText("");
        txtaRemarks.setText("");
    }
    private void  back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }
    public static void main(String[] args) {
        Screening screening = new Screening(null);
    }

}
