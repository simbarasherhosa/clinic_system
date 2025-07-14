import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Admin_Main_Menu extends JFrame{
    private JButton btnRegistration;
    private JButton btnScreening;
    private JButton btnPayment;
    private JButton btnDischarging;
    private JButton btnBedAllocation;
    private JButton btnTreatment;
    private JPanel frmAdminMainMenu;

    public Admin_Main_Menu(JFrame parent) {

        setTitle("Admin Main Menu");
        setContentPane(frmAdminMainMenu);
        setMinimumSize(new Dimension(530, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // MenuBar, Menu Item, menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu insertMenu = new JMenu("Insert");
        JMenu deleteMenu = new JMenu("Delete");
        JMenu exitMenu = new JMenu("Exit");

        JMenuItem newEmployee = new JMenuItem("Add Employee");
        JMenuItem deleteEmployee = new JMenuItem("Delete Employee");
        JMenuItem insertBed = new JMenuItem("Add Bed");
        JMenuItem deleteBed = new JMenuItem("Delete Bed");
        JMenuItem exit = new JMenuItem("Exit");

        newEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add_Employee add_employee = new Add_Employee(null);
                add_employee.setVisible(true);
                setVisible(false);
            }
        });
        deleteEmployee.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete_Employee delete_employee = new Delete_Employee(null);
                delete_employee.setVisible(true);
                setVisible(false);
            }
        });
        insertBed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Add_Bed_or_Sector add_bed_or_sector = new Add_Bed_or_Sector(null);
                setVisible(false);
                add_bed_or_sector.setVisible(true);
            }
        });
        deleteBed.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Delete_Bed_or_Sector delete_bed_or_sector = new Delete_Bed_or_Sector(null);
                setVisible(false);
                delete_bed_or_sector.setVisible(true);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newEmployee);
        fileMenu.add(deleteEmployee);
        /////////////////////////////////////////////////////////
        insertMenu.add(insertBed);
        ///////////////////////////////////
        deleteMenu.add(deleteBed);
        ///////////////////////////////////
        exitMenu.add(exit);
        menuBar.add(fileMenu);
        menuBar.add(insertMenu);
        menuBar.add(deleteMenu);
        menuBar.add(exitMenu);
        this.setJMenuBar(menuBar);

        btnRegistration.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registration();
            }
        });

        btnScreening.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screening();
            }
        });

        btnTreatment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                treatment();
            }
        });

        btnDischarging.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                discharging();
            }
        });

        btnPayment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                payment();
            }
        });
        setVisible(true);
        btnBedAllocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BedAlloDatabase bed_allocation = new BedAlloDatabase(null);
                setVisible(false);
                bed_allocation.setVisible(true);
            }
        });
    }
    public void registration(){
        RegDatabase regDB = new RegDatabase(null);
        setVisible(false);
        regDB.setVisible(true);
    }
    private void screening(){
        ScreenDatabase screening = new ScreenDatabase(null);
        setVisible(false);
        screening.setVisible(true);
    }
    private void treatment(){
        Treatment treatment = new Treatment(null);
        setVisible(false);
        treatment.setVisible(true);
    }
    private void payment(){
        PaymentDatabase payment = new PaymentDatabase(null);
        setVisible(false);
        payment.setVisible(true);
    }
    private void discharging(){
        DischargingDatabase  discharging = new DischargingDatabase(null);
        setVisible(false);
        discharging.setVisible(true);
    }

    public static void main(String[] args) {
        Admin_Main_Menu admin_main_menu = new Admin_Main_Menu(null);
    }
}
