import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Objects;

public class Other_Illness extends JFrame{
    private JTextField txtPatId;
    private JComboBox cmbIllness;
    private JTextField txtMedFee;
    private JTextField txtMedFeePaid;
    private JTextField txtMedFeeOwed;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JPanel frmUnadmitted_Illness;
    private JComboBox cmbPaymentForm;
    private JComboBox cmbMedicalAid;
    private JComboBox cmbCurrency;
    private JComboBox cmbMedAidName;

    public Other_Illness(JFrame parent){
        setTitle("Other Illness");
        setContentPane(frmUnadmitted_Illness);
        setMinimumSize(new Dimension(650, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(parent);

        cmbMedicalAid.addItem("No");
        cmbMedicalAid.addItem("CIMAS");
        cmbMedicalAid.addItem("Nyaradzo");
        cmbMedicalAid.addItem("Ecosure");
        cmbMedicalAid.addItem("Haven Life");

        cmbCurrency.addItem("USD");
        cmbCurrency.addItem("ZWL");
        cmbCurrency.addItem("Euro");
        cmbCurrency.addItem("YEN");
        cmbCurrency.addItem("Pound");

        cmbPaymentForm.addItem("Cash");
        cmbPaymentForm.addItem("Cheque");
        cmbPaymentForm.addItem("Eco-Cash");
        cmbPaymentForm.addItem("Medical Aid");

        cmbIllness.addItem("");
        cmbIllness.addItem("Malaria");
        cmbIllness.addItem("Cancer");
        cmbIllness.addItem("Tuberculosis");
        cmbIllness.addItem("HIV/AIDS");

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
        txtMedFeeOwed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                calcMedFee();
            }
        });

        cmbIllness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                illnessAmount();
            }
        });
        setVisible(true);

    }

    public void calcMedFee(){
        double medFee = Double.parseDouble(txtMedFee.getText());
        double medFeePaid = Double.parseDouble(txtMedFeePaid.getText());
        double medFeeOwed = medFee - medFeePaid;

        txtMedFeeOwed.setText(String.valueOf(medFeeOwed));
    }
    public void save(){
        String patid = txtPatId.getText();
        String illness = cmbIllness.getActionCommand();
        String medFee = txtMedFee.getText();
        String medFeePaid = txtMedFeePaid.getText();
        String medFeeOwed = txtMedFeeOwed.getText();


        if (patid.isEmpty() || illness.isEmpty()||medFee.isEmpty()||medFeePaid.isEmpty()||medFeeOwed.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all fields", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO other_illness (patient_id, medical_aid, form_of_payment, currency, illness, medication_fee, medication_fee_paid, medication_fee_owed)" +
                    "VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, txtPatId.getText());
            String medAid = (String) cmbMedicalAid.getSelectedItem();
            preparedStatement.setString(2, medAid);
            String payForm = (String) cmbPaymentForm.getSelectedItem();
            preparedStatement.setString(3, payForm);
            String curr = (String) cmbCurrency.getSelectedItem();
            preparedStatement.setString(4, curr);
            String illnss = (String) cmbMedicalAid.getSelectedItem();
            preparedStatement.setString(5, illnss);
            preparedStatement.setString(6, txtMedFee.getText());
            preparedStatement.setString(7, txtMedFeePaid.getText());
            preparedStatement.setString(8, txtMedFeeOwed.getText());

            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Patient details successfully saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void clear(){
        txtPatId.setText("");
//        cmbIllness.getActionCommand();
        txtMedFee.setText("");
        txtMedFeePaid.setText("");
        txtMedFeeOwed.setText("");
    }
    public void illnessAmount(){
        String myIllness = (String) cmbIllness.getSelectedItem();
        switch (myIllness){
            case "Malaria" : txtMedFee.setText("100");
                break;
            case "Cancer" : txtMedFee.setText("1000");
                break;
            case "Tuberculosis" : txtMedFee.setText("300");
                break;
            case "HIV/AIDS" : txtMedFee.setText("140");
                break;
            default: txtMedFee.setText("");
        }
    }
    private void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
    }

    public static void main(String[] args) {
        Other_Illness other_illness = new Other_Illness(null);
    }
}
