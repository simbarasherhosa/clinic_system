import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Treatment extends JFrame{
    private JPanel frmTreatment;
    private JTextField txtPatId;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JComboBox cmbIllness;
    private JComboBox cmbDrug;

    public Treatment(JFrame parent){

        setTitle("TREATMENT");
        setContentPane(frmTreatment);
        setMinimumSize(new Dimension(300, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cmbDrug.addItem("Penicillin");
        cmbIllness.addItem("Malaria");

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

    public void save(){

        String patid = txtPatId.getText();
        String myIlnness = cmbIllness.getActionCommand();
        String myDrug = cmbDrug.getActionCommand();

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            if (patid.isEmpty() || myDrug.isEmpty() || myIlnness.isEmpty()){
                JOptionPane.showMessageDialog(this, "Please enter all fields.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }else{
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO treatment (patient_id, illness, drug)" +
                        "VALUES (?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, txtPatId.getText());
                String illness = (String)cmbIllness.getSelectedItem();
                preparedStatement.setString(2, illness);
                String drug = (String) cmbDrug.getSelectedItem();
                preparedStatement.setString(3, drug);

//            preparedStatement.executeUpdate();

                int dataRecorded = preparedStatement.executeUpdate();

                if (dataRecorded > 0){

                    JOptionPane.showMessageDialog(this, "Patient details saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
                }else{
                    JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
                }

                conn.close();
                stmt.close();

            }


        } catch (Exception e) {
//            throw new RuntimeException(e);
            JOptionPane.showMessageDialog(this, "An error occurred, please try again!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    private void clear(){
        txtPatId.setText("");
//        cmbIllness.getActionCommand();
//        cmbDrug.getActionCommand();
    }
    public void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Treatment treatment = new Treatment(null);
    }
}
