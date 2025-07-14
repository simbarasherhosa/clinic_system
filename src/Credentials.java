import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Credentials extends JFrame{
    private JTextField txtUsername;
    private JPasswordField txtpPassword;
    private JPasswordField txtpConfirm;
    private JButton btnSave;
    private JButton btnBack;
    private JPanel frmCredentials;
    private JComboBox cmbRank;
    private JButton btnDone;

    public Credentials(JFrame parent){
        setTitle("TREATMENT");
        setContentPane(frmCredentials);
        setMinimumSize(new Dimension(450, 300));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        cmbRank.addItem("Employee");
        cmbRank.addItem("Admin");

        btnDone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                done();
            }
        });
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                back();
            }
        });
        btnSave.setEnabled(false);

        setVisible(true);
    }

    private void done(){
        String user = txtUsername.getText();
        String password = String.valueOf(txtpPassword.getPassword());
        String rank = cmbRank.getActionCommand();
        String confirmPassword = String.valueOf(txtpConfirm.getPassword());

        if (user.isEmpty() || password.isEmpty() || rank.isEmpty()){
            JOptionPane.showMessageDialog(null, "Please enter all fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        if (!password.equals(confirmPassword)){
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
            btnSave.setEnabled(false);
        }else{
            btnSave.setEnabled(true);
        }
    }
    private void save(){

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO login (username, rank, password)" +
                    "VALUES (?,?,?)";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, txtUsername.getText());
            String myRank = (String) cmbRank.getSelectedItem();
            preparedStatement.setString(2, myRank);
            preparedStatement.setString(3, String.valueOf(txtpPassword.getPassword()));

            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Employee details saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Employee details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }
            setVisible(false);

            conn.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void back(){
        Add_Employee add_employee = new Add_Employee(null);
        setVisible(false);
        add_employee.setVisible(true);
    }

    public static void main(String[] args) {
        Credentials credentials = new Credentials(null);
    }
}
