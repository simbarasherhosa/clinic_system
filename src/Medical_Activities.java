import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

public class Medical_Activities extends JFrame{
    private JPanel frmMedicalActivities;
    public JTextField txtPatId;
    private JTextArea txtaActivity;
    private JButton btnAddAmount;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    public JTextField txtActivityAmount;
    private JTextField txtAmountPaid;
    private JTextField txtAmountOwed;
    private JComboBox cmbPaymentForm;
    private JComboBox cmbMedicalAid;
    private JComboBox cmbCurrency;
    private JComboBox cmbActivity;
    private JSpinner daysSpinner;
    private JLabel totalCostLabel;

    public Medical_Activities(JFrame parent){
        setTitle("MEDICAL ACTIVITY(IES)");
        setContentPane(frmMedicalActivities);
        setMinimumSize(new Dimension(700, 450));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        cmbActivity.addItem("Physiotherapy");
        cmbActivity.addItem("Exercises");
        cmbActivity.addItem("Occupational Therapy");
        cmbActivity.addItem("Speech Therapy");


        txtActivityAmount.setText("0");
        txtAmountPaid.setText("0");
        txtActivityAmount.setEditable(false);

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

        txtAmountOwed.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                calAmountOwed();
            }
        });
        btnAddAmount.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                amountAdd();
            }
        });
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
    private void calAmountOwed(){
        double a = Double.parseDouble(txtActivityAmount.getText());
        double b = Double.parseDouble(txtAmountPaid.getText());
        double c = a - b;

        txtAmountOwed.setText(String.valueOf(c));
    }


    public void amountAdd(){
        HashMap<String, Double> activityPriceMap = new HashMap<>();

        activityPriceMap.put("Physiotherapy", 50.0);
        activityPriceMap.put("Exercises", 60.0);
        activityPriceMap.put("Occupational Therapy", 500.0);
        activityPriceMap.put("Speech Therapy", 130.0);


        String selectedActivity = (String) cmbActivity.getSelectedItem();
        double price = activityPriceMap.get(selectedActivity);
        int days = (int) daysSpinner.getValue();
        double totalCost = price * days;
        totalCostLabel.setText("Total Cost: " + totalCost);

        double newAmount = totalCost;
        double oldAmout = Double.parseDouble(txtActivityAmount.getText());
        double totalAmount = newAmount + oldAmout;
        txtActivityAmount.setText(String.valueOf(totalAmount));

        String activityDetails = selectedActivity + "-" + days + " days";
        txtaActivity.append(activityDetails + "\n");
    }


    public void save(){
        String patid = txtPatId.getText();
        String med_act = txtaActivity.getText();

        if (patid.isEmpty() || med_act.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all fields.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO medical_activities (patient_id,  medical_aid, form_of_payment, currency, medical_activity, activity_amount, amount_paid, amount_owed)" +
                    "VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtPatId.getText());

            String medAid = (String) cmbMedicalAid.getSelectedItem();
            preparedStatement.setString(2, medAid);
            String payForm = (String) cmbPaymentForm.getSelectedItem();
            preparedStatement.setString(3, payForm);
            String curr = (String) cmbCurrency.getSelectedItem();
            preparedStatement.setString(4, curr);

            preparedStatement.setString(5, txtaActivity.getText());
            preparedStatement.setString(6, txtActivityAmount.getText());
            preparedStatement.setString(7, txtAmountPaid.getText());
            preparedStatement.setString(8, txtAmountOwed.getText());

            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Patient details saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }

            conn.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void back(){
        Main_Menu main_menu = new Main_Menu(null);
        setVisible(false);
        main_menu.setVisible(true);
    }
    public void clear(){
        txtActivityAmount.setText("0");
//        txtPatId.setText("");
        txtPatId.setText("");
        txtaActivity.setText("");
    }
    public static void main(String[] args) {
        Medical_Activities medical_activities = new Medical_Activities(null);

    }
}
