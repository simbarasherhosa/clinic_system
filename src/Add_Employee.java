import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Add_Employee extends JFrame{
    private JTextField txtName;
    private JComboBox cmbMaritalStatus;
    private JButton btnSave;
    private JButton btnClearAll;
    private JButton btnBack;
    private JPanel frmAddEmployee;
    private JTextField txtLname;
    private JTextField txtDOB;
    private JTextField txtIdNum;
    private JComboBox cmbSex;
    private JComboBox cmbDepartment;
    private JTextField txtAddress;
    private JTextField txtEmail;
    private JTextField txtNationality;
    private JTextField txtSalary;
    private JButton btnDone;

    public Add_Employee(JFrame parent){
        setTitle("Employees");
        setContentPane(frmAddEmployee);
        setMinimumSize(new Dimension(650, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        btnSave.setEnabled(false);

        cmbMaritalStatus.addItem("Single");
        cmbMaritalStatus.addItem("Married");
        cmbMaritalStatus.addItem("Widowed");

        cmbSex.addItem("Female");
        cmbSex.addItem("Male");

        cmbDepartment.addItem("Surgery");
        cmbDepartment.addItem("Gynecology");
        cmbDepartment.addItem("IT");
        cmbDepartment.setEditable(true);

        btnDone.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                done();
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
    private void done(){
        Credentials credentials = new Credentials(null);
        credentials.setVisible(true);
        btnSave.setEnabled(true);
    }
    private void clear(){
        txtName.setText("");
//        cmbSex.setSelectedItem(-1);
//        cmbDepartment.getActionCommand();
        txtDOB.setText("");
        txtIdNum.setText("");
        txtAddress.setText("");
        txtNationality.setText("");
        txtSalary.setText("");
        txtLname.setText("");
//        cmbMaritalStatus.getActionCommand();
        txtEmail.setText("");
    }
    private void save(){

        String name = txtName.getText();
        String sex = cmbSex.getActionCommand();
        String department = cmbDepartment.getActionCommand();
        String dob = txtDOB.getText();
        String idNum = txtIdNum.getText();
        String address = txtAddress.getText();
        String nationality = txtNationality.getText();
        String salary = txtSalary.getText();
        String last_name = txtLname.getText();
        String maritalStatus = cmbMaritalStatus.getActionCommand();
        String email = txtEmail.getText();

        if (name.isEmpty() || sex.isEmpty() || department.isEmpty()|| dob.isEmpty()||idNum.isEmpty()||address.isEmpty()||email.isEmpty()||nationality.isEmpty()||salary.isEmpty()||last_name.isEmpty()||maritalStatus.isEmpty()){
            JOptionPane.showMessageDialog(this, "Please enter all NECESSARY fields", "Message", JOptionPane.INFORMATION_MESSAGE);
        }

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "INSERT INTO employees (name, last_name, dob, id_number, address, sex, department, email_address, marital_status, nationality, salary)" +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, txtName.getText());
            preparedStatement.setString(2, txtLname.getText());
            preparedStatement.setString(3, txtDOB.getText());
            preparedStatement.setString(4, txtIdNum.getText());
            preparedStatement.setString(5, txtAddress.getText());
            String gender = (String) cmbSex.getSelectedItem();
            preparedStatement.setString(6, gender);
            String deptmnt = (String) cmbDepartment.getSelectedItem();
            preparedStatement.setString(7, deptmnt);
            preparedStatement.setString(8, txtEmail.getText());
            String status = (String) cmbMaritalStatus.getSelectedItem();
            preparedStatement.setString(9, status);
            preparedStatement.setString(10, txtNationality.getText());
            preparedStatement.setString(11, txtSalary.getText());

            int dataRecorded = preparedStatement.executeUpdate();

            if (dataRecorded > 0){

                JOptionPane.showMessageDialog(this, "Employee details successfully saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(this, "Employee details not saved", "Error", JOptionPane.ERROR_MESSAGE);
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(true);
    }

    public static void main(String[] args) {
        Add_Employee add_employee = new Add_Employee(null);
    }
}
