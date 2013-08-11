package bad.robot.radiate.ui;

import bad.robot.radiate.Status;
import bad.robot.radiate.monitor.Observer;
import bad.robot.radiate.teamcity.SanitisedException;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static java.awt.AWTEvent.KEY_EVENT_MASK;
import static java.awt.Color.darkGray;
import static javax.swing.UIManager.getSystemLookAndFeelClassName;

public class SwingUi extends JFrame implements Ui, Observer {

    private final Set<StatusPanel> panels = new HashSet<>();
    private final TransparentDialog exceptions = new TransparentDialog("History", this);

    public SwingUi() throws HeadlessException {
        setLayout(new ChessboardLayout(panels));
        setupWindowing();
        setupEventListeners();
        setupExceptionsDisplay();
    }

    private void setupWindowing() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLookAndFeel();
        setTitle("Radiate");
        getContentPane().setBackground(darkGray);
        setSize(700, 500);
//        setUndecorated(true);
//        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
    }

    private void setupEventListeners() {
        getToolkit().addAWTEventListener(new ExitOnEscape(), KEY_EVENT_MASK);
        getToolkit().addAWTEventListener(new MaximiseToggle(this), KEY_EVENT_MASK);
    }

    private void setupExceptionsDisplay() {
        exceptions.makeResizeable();
        exceptions.hideCloseButton();
        exceptions.getJDialog().setSize(300, 350);
        exceptions.getJDialog().setLocationRelativeTo(null);
        exceptions.getJDialog().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public Observer createStatusPanel() {
        StatusPanel panel = new StatusPanel(this);
        panels.add(panel);
        return panel;
    }

    @Override
    public void start() {
        setVisible(true);
    }

    private void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(getSystemLookAndFeelClassName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Status status) {
    }

    @Override
    public void update(Exception exception) {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        for (int i = 0; i < 100; i++)
            panel.add(createLabel(new SanitisedException(exception).getMessage()));
        panel.setOpaque(false);
        exceptions.getJDialog().add(panel);
        exceptions.getJDialog().setVisible(true);

//        JPanel panel = new JPanel();
//        panel.setLayout(new FlowLayout());
//        for (int i = 0; i < 100; i++)
//            panel.add(createLabel(new SanitisedException(exception).getMessage()));
//        JScrollPane scroll = new JScrollPane(panel);
//        scroll.setPreferredSize(panel.getSize());
//        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        scroll.setOpaque(false);
//        exceptions.getJDialog().add(scroll);
//        exceptions.getJDialog().setVisible(true);
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setUI(new TransparentLabelUI());
        return label;
    }

}