import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class PaymentDatabase extends JFrame{
    private JTextField txtPatId;
    private JTextField txtAdmFee;
    private JTextField txtAdmTime;
    private JTextField txtFeePaid;
    private JTextField txtAdmFeeOwed;
    private JTextField txtMedFee;
    private JTextField txtMedFeePaid;
    private JTextField txtMedFeeOwed;
    private JTextField txtTotalPaid;
    private JTextField txtTotalOwed;
    private JButton btnBack;
    private JTextField txtPaymentForm;
    private JTextField txtCurrency;
    private JTextField txtMedActivities;
    private JTextField txtOtherIllness;
    private JTextField txtIllness;
    private JTextField txtMedAid;
    private JButton btnSearch;
    private JPanel frmPaymentDatabase;
    private JTextField txtMedAidName;

    public PaymentDatabase(JFrame parent){
        setTitle("Payment Database");
        setContentPane(frmPaymentDatabase);
        setMinimumSize(new Dimension(700, 600));
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
            String sql = "SELECT * FROM payment WHERE patient_id = '" + txtPatId.getText() +"'";
            ResultSet rs = statement.executeQuery(sql);

            if (rs.next()){
                txtMedAid.setText(rs.getString("medical_aid"));
                txtMedAidName.setText(rs.getString("medical_aid_name"));
                txtPaymentForm.setText(rs.getString("form_of_payment"));
                txtCurrency.setText(rs.getString("currency"));
                txtAdmFee.setText(rs.getString("admition_fee"));
                txtAdmTime.setText(rs.getString("admition_time_in_days"));
                txtAdmFeeOwed.setText(rs.getString("admition_fee_owed"));
                txtFeePaid.setText(rs.getString("admition_fee_paid"));
                txtIllness.setText((rs.getString("illness")));
                txtMedFee.setText((rs.getString("medication_fee")));
                txtMedFeePaid.setText((rs.getString("medication_fee_paid")));
                txtMedFeeOwed.setText((rs.getString("medication_fee_owed")));
                txtMedActivities.setText((rs.getString("medical_activities")));
                txtOtherIllness.setText((rs.getString("other_illness")));
                txtTotalPaid.setText((rs.getString("total_amount_paid")));
                txtTotalOwed.setText((rs.getString("total_amount_owed")));
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
        PaymentDatabase paymentDatabase = new PaymentDatabase(null);
    }
}
