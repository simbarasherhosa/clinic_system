import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.PasswordAuthentication;
import java.sql.*;
import java.util.Objects;

public class Login extends JDialog{
    private JPanel frmLogin;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox cmbRank;
    private JButton btnOK;
    private JButton btnClear;
    private JButton btnExit;

    public Login(JFrame parent){
        super(parent);
        setTitle("LOGIN FORM");
        setContentPane(frmLogin);
        setMinimumSize(new Dimension(500, 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cmbRank.addItem("Admin");
        cmbRank.addItem("Employee");

        btnOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String rank = cmbRank.getActionCommand();
                String password = String.valueOf(txtPassword.getPassword());

                user = getUser(username, rank,  password);

                if (user != null){
                    dispose();
                }else{
                    JOptionPane.showMessageDialog(null, "Invalid username, rank or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        setVisible(true);
    }
    public User user;
    private User getUser(String username, String rank, String password){
        User user = null;

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM login WHERE Username =? AND Password =? AND Rank =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            String myRank = (String) cmbRank.getSelectedItem();
            preparedStatement.setString(3, myRank);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                user = new User();
                user.username = resultSet.getString("Username");
                user.rank = resultSet.getString("Rank");
                user.password = resultSet.getString("Password");

                if (Objects.equals(myRank, "Admin")){
                    Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
                    setVisible(false);
                    admin_main_menu.setVisible(true);
                }else {
                    Main_Menu main_menu = new Main_Menu(null);
                    setVisible(false);
                    main_menu.setVisible(true);
                }
            }

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
//            System.out.println("CANNOT CONNECT");
        }

        return user;
    }

    public static void main(String[] args) {
        Login login = new Login(null);
        User user = login.user;
    }
}
