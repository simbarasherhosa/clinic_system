import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_Menu extends JFrame{
    private JPanel frmMainMenu;
    private JButton btnRegistration;
    private JButton btnScreening;
    private JButton btnPayment;
    private JButton btnTreatment;
    private JButton btnDischarging;
    private JButton btnBedAllocation;

    public Main_Menu(JFrame parent) {

        setTitle("Main Menu");
        setContentPane(frmMainMenu);
        setMinimumSize(new Dimension(530, 400));
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // MenuBar, Menu Item, menu
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Interface");
        JMenu exitMenu = new JMenu("Exit");

        JMenuItem otherIllness = new JMenuItem("Other Illness");
        JMenuItem medicalAct = new JMenuItem("Medical Activities");
        JMenuItem exit = new JMenuItem("Exit");

        otherIllness.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Other_Illness other_illness = new Other_Illness(null);
                other_illness.setVisible(true);
                setVisible(false);
            }
        });
        medicalAct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Medical_Activities medical_activities = new Medical_Activities(null);
                medical_activities.setVisible(true);
                setVisible(false);
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            System.exit(0);
            }
        });

        fileMenu.add(otherIllness);
        fileMenu.add(medicalAct);
        exitMenu.add(exit);
        menuBar.add(fileMenu);
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
                Bed_Allocation bed_allocation = new Bed_Allocation(null);
                setVisible(false);
                bed_allocation.setVisible(true);
            }
        });
    }
    public void registration(){
        Registration registration = new Registration(null);
        setVisible(false);
        registration.setVisible(true);
    }
    private void screening(){
        Screening screening = new Screening(null);
        setVisible(false);
        screening.setVisible(true);
    }
    private void treatment(){
        Treatment treatment = new Treatment(null);
        setVisible(false);
        treatment.setVisible(true);
    }
    private void payment(){
        Payment payment = new Payment(null);
        setVisible(false);
        payment.setVisible(true);
    }
    private void discharging(){
        Discharging discharging = new Discharging(null);
        setVisible(false);
        discharging.setVisible(true);
    }

    public static void main(String[] args) {
        Main_Menu main_menu = new Main_Menu(null);
    }
}
