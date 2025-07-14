import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class Delete_Bed_or_Sector extends JFrame{
    private JPanel frmDeleteBedorSector;
    private JComboBox cmbSector;
    private JTextField txtSectorNum;
    private JTextField txtNumOfBeds; // use this to delete certain number of beds
    private JButton btnSave;
    private JButton btnBack;
    private JButton btnClearAll;

    public Delete_Bed_or_Sector(JFrame parent){
        setTitle("Delete Bed or Sector");
        setContentPane(frmDeleteBedorSector);
        setMinimumSize(new Dimension(530, 300));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cmbSector.addItem("Surgery");
        cmbSector.addItem("Maternity");

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
        });setVisible(true);
    }
    private void save() {

        final String DB_URL = "jdbc:mysql://localhost/rhosa_clinic_db?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "DELETE FROM bed_allocation WHERE sector = '" + cmbSector.getSelectedItem() + "' OR ward = '" + txtSectorNum.getText() + "'";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            int dataRecorded = preparedStatement.executeUpdate();
            if (dataRecorded > 0) {

                JOptionPane.showMessageDialog(this, "Beds successfully removed", "Saved", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Beds failed to be removed, try again!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            conn.close();
            stmt.close();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void clear(){
        txtSectorNum.setText("");
        txtNumOfBeds.setText("");
    }
    private void back(){
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
        admin_main_menu.setVisible(true);
        setVisible(false);
    }

    public static void main(String[] args) {
        Delete_Bed_or_Sector delete_bed_or_sector = new Delete_Bed_or_Sector(null);
    }
}

