import javax.swing.*;
import javax.swing.plaf.nimbus.State;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Add_Bed_or_Sector extends JFrame{
    private JPanel frmAddBedorSector;
    private JTextField txtSector;
    private JTextField txtNumOfBeds;
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;
    private JTextField txtSectorNum;
    private JButton btnSearch;
    private JTextField txtSearch;
    private JTextField txtAddBeds;
    private JButton btnAdd;

    public Add_Bed_or_Sector(JFrame parent){
        setTitle("Add Bed or Sector");
        setContentPane(frmAddBedorSector);
        setMinimumSize(new Dimension(530, 300));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnSearch.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                search();
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
        btnClearAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clear();
            }
        });
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add();
            }
        });
        setVisible(true);
    }
    final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
    final String USERNAME = "root";
    final String PASSWORD = "";

    private void search() {

        try {

            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement statement = conn.createStatement();

//            WHERE sector = '" + txtSector.getText() + "' AND ward = '" + txtSectorNum.getText() + "'

            String sql = "SELECT MAX(bed) FROM bed_allocation AS bed";
            ResultSet rs = statement.executeQuery(sql);
//            txtSearch.setText(rs.getString("bed"));

            if (rs.next()){
                txtSearch.setText(rs.getString("bed"));

            }else{
                JOptionPane.showMessageDialog(this, "This is a new sector.", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
            btnSave.setEnabled(true);
        }

        catch (SQLException e) {
            throw new RuntimeException(e);
//            JOptionPane.showMessageDialog(this, "Consult the head director.", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }


        private void save() {
//        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
//        final String USERNAME = "root";
//        final String PASSWORD = "";

        double myBedNum = Double.parseDouble(txtNumOfBeds.getText());
        int dataRecorded = 0;
        for (int bedNum = 1; bedNum <= myBedNum; bedNum++) {

            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

                Statement stmt = conn.createStatement();
                String sql = "INSERT INTO bed_allocation (patient_id, is_icu_needed, sector, ward, bed, status, cause_or_description)" +
                        "VALUES ('','',?,?,?,'vacant','')";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);

                preparedStatement.setString(1, txtSector.getText());
                preparedStatement.setString(2, txtSectorNum.getText());

                txtNumOfBeds.setText(String.valueOf(bedNum));
                preparedStatement.setString(3, txtNumOfBeds.getText());

                dataRecorded = preparedStatement.executeUpdate();

                conn.close();
                stmt.close();

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
        if (dataRecorded > 0) {

            JOptionPane.showMessageDialog(this, "Patient details saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Patient details not saved", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    We need a way of getting the maximum number of beds and increment from that number. Good way is starting with a for loop, or while
    private void add(){
        do {
            try {
                Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                Statement statement = conn.createStatement();

                String sql = "SELECT MAX(bed) FROM bed_allocation WHERE sector = '" + txtSector.getText() + "'";
                ResultSet resultSet = statement.executeQuery(sql);

                if (resultSet.next()){
                    int dataRecorded = 0;
                    txtNumOfBeds.setText(String.valueOf(resultSet.getInt("bed")));
                    for (int bedNum = Integer.parseInt(txtNumOfBeds.getText()); bedNum <= Integer.parseInt(txtNumOfBeds.getText()); bedNum++){


                        Statement stmt = conn.createStatement();
                        String insertSql = "INSERT INTO bed_allocation (patient_id, is_icu_needed, sector, ward, bed, status, cause_or_description)" +
                                "VALUES ('','',?,?,?,'vacant','')";
                        PreparedStatement preparedStatement = conn.prepareStatement(insertSql);

                        preparedStatement.setString(1, txtSector.getText());
                        preparedStatement.setString(2, txtSectorNum.getText());

                        txtNumOfBeds.setText(String.valueOf(bedNum));
                        preparedStatement.setString(3, txtNumOfBeds.getText());

                        dataRecorded = preparedStatement.executeUpdate();

                        conn.close();
                        stmt.close();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }while ();
    }

    private void clear(){
        txtNumOfBeds.setText("");
        txtSector.setText("");
        txtSearch.setText("");
        txtSectorNum.setText("");
    }
    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        setVisible(false);
        admin_main_menu.setVisible(false);
    }

    public static void main(String[] args) {
        Add_Bed_or_Sector add_bed_or_sector = new Add_Bed_or_Sector(null);
    }
}
