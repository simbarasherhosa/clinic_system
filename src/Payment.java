import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Payment extends JFrame{
    private JPanel frmPayment;
    public JTextField txtPatId;
    public JComboBox cmbPaymentForm;
    public JComboBox cmbMedicalAid;
    public JComboBox cmbCurrency;
    public JTextField txtAdmFee;
    public JTextField txtAdmTime;
    private JComboBox cmbMedActivites;
    private JComboBox cmbOtherIllness;
    public JTextField txtTotalPaid;
    public JTextField txtFeePaid;
    public JTextField txtAdmFeeOwed;
    public JComboBox cmbIllness;
    public JTextField txtMedFee;
    private JTextField txtMedFeePaid;
    private JTextField txtMedFeeOwed;
    public JTextField txtTotalOwed;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JComboBox cmbMedAidName;

    public Payment(JFrame parent) {
        setTitle("PAYMENT");
        setContentPane(frmPayment);
        setMinimumSize(new Dimension(700, 600));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        txtAdmFee.setText("0");
        txtMedFee.setText("0");
        txtAdmTime.setText("0");
        txtFeePaid.setText("0");
        txtMedFeePaid.setText("0");

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

        cmbOtherIllness.addItem("No");
        cmbOtherIllness.addItem("Yes");

        cmbMedActivites.addItem("No");
        cmbMedActivites.addItem("Yes");

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
        txtAdmFeeOwed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                calAdmition();
            }
        });

        cmbIllness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                illnessAmount();
            }
        });

        txtMedFeeOwed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                calcMedFee();
            }
        });

        txtTotalOwed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                totalOwed();
            }
        });
        txtTotalPaid.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                totalPaid();
            }
        });

        setVisible(true);
    }
    public void totalPaid(){
        double medFeePaid = Double.parseDouble(txtMedFeePaid.getText());
        double admFeePaid = Double.parseDouble(txtFeePaid.getText());

        double totalAmountPaid = medFeePaid + admFeePaid;
        txtTotalPaid.setText(String.valueOf(totalAmountPaid));
    }
    public void totalOwed(){
        double admOwed = Double.parseDouble(txtAdmFeeOwed.getText());
        double medOwed = Double.parseDouble(txtMedFeeOwed.getText());
        double totalAmountOwed = admOwed + medOwed;
        txtTotalOwed.setText(String.valueOf(totalAmountOwed));

        if (txtTotalOwed.getText().contains("-")){
            txtTotalOwed.setForeground(Color.RED);
        }else{
            txtTotalOwed.setForeground(Color.BLACK);
        }

    }
    public void calcMedFee(){
        double medFee = Double.parseDouble(txtMedFee.getText());
        double medFeePaid = Double.parseDouble(txtMedFeePaid.getText());
        double medFeeOwed = medFee - medFeePaid;

        txtMedFeeOwed.setText(String.valueOf(medFeeOwed));
    }
    public void calAdmition(){

        double admFee = Double.parseDouble(txtAdmFee.getText());
        double admFeePaid = Double.parseDouble(txtFeePaid.getText());
        double admTime = Double.parseDouble(txtAdmTime.getText());

        double thePaid = admFee * admTime;
        double diff = thePaid - admFeePaid;

        txtAdmFeeOwed.setText(String.valueOf(diff));

        if (admTime == 0){
            double fee4 = admFeePaid - admFee;
            txtAdmFeeOwed.setText(String.valueOf(fee4));
        }
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

    public void save(){
        String patid = txtPatId.getText();
        String medicalAid = cmbMedicalAid.getActionCommand();
        String paymentForm = cmbPaymentForm.getActionCommand();
        String currency = cmbCurrency.getActionCommand();
        String admFee = txtAdmFee.getText();
        String admTime = txtAdmTime.getText();
        String admFeePaid = txtFeePaid.getText();
        String admFeeOwed = txtAdmFeeOwed.getText();
        String illness = cmbIllness.getActionCommand();
        String medFee = txtMedFee.getText();
        String medFeePaid = txtMedFeePaid.getText();
        String medFeeOwed = txtMedFeeOwed.getText();
        String medActivites = cmbMedActivites.getActionCommand();
        String otherIllness = cmbOtherIllness.getActionCommand();
        String totalPaid = txtTotalPaid.getText();
        String totalOwed = txtTotalOwed.getText();

        if (patid.isEmpty() || medicalAid.isEmpty() || paymentForm.isEmpty()||currency.isEmpty() || admFee.isEmpty()||admTime.isEmpty()||admFeePaid.isEmpty()||admFeeOwed.isEmpty()||illness.isEmpty()||medFee.isEmpty()||medFeePaid.isEmpty()||medFeeOwed.isEmpty()||medActivites.isEmpty()||otherIllness.isEmpty()||totalOwed.isEmpty()||totalPaid.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all NECESSARY fields", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO payment (patient_id, medical_aid, form_of_payment, currency, admition_fee, admition_time_in_days, admition_fee_paid, admition_fee_owed, illness, medication_fee, medication_fee_paid, medication_fee_owed, medical_activities, other_illness, total_amount_paid, total_amount_owed)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, txtPatId.getText());

            String medAid = (String) cmbMedicalAid.getSelectedItem();
            preparedStatement.setString(2, medAid);
            String payForm = (String) cmbPaymentForm.getSelectedItem();
            preparedStatement.setString(3, payForm);
            String curr = (String) cmbCurrency.getSelectedItem();
            preparedStatement.setString(4, curr);
            preparedStatement.setString(5, txtAdmFee.getText());
            preparedStatement.setString(6, txtAdmTime.getText());
            preparedStatement.setString(7, txtFeePaid.getText());
            preparedStatement.setString(8, txtAdmFeeOwed.getText());
            String illnss = (String) cmbIllness.getSelectedItem();
            preparedStatement.setString(9, illnss);
            preparedStatement.setString(10, txtMedFee.getText());
            preparedStatement.setString(11, txtMedFeePaid.getText());
            preparedStatement.setString(12, txtMedFeeOwed.getText());
            String medAct = (String) cmbMedActivites.getSelectedItem();
            preparedStatement.setString(13, medAct);
            String otherIll = (String) cmbOtherIllness.getSelectedItem();
            preparedStatement.setString(14, otherIll);
            preparedStatement.setString(15, txtTotalPaid.getText());
            preparedStatement.setString(16, txtTotalOwed.getText());

            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Patient details successfully saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
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
        txtMedFee.setText("");
        txtMedFeePaid.setText("");
        txtMedFeeOwed.setText("");
        txtAdmFee.setText("");
        txtAdmTime.setText("");
        txtFeePaid.setText("");
        txtAdmFeeOwed.setText("");
        txtTotalPaid.setText("");
        txtTotalOwed.setText("");
    }

    public void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Payment payment = new Payment(null);
    }
}