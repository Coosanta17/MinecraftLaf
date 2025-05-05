package net.coosanta.minecraftlaf.demo;

import net.coosanta.minecraftlaf.MinecraftLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class GeneralDemo extends JPanel {
    public GeneralDemo() {
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Basic Controls", createBasicControlsPanel());
        tabbedPane.addTab("Text Components", createTextComponentsPanel());
        tabbedPane.addTab("Lists and Tables", createDataComponentsPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createBasicControlsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Buttons section
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBorder(BorderFactory.createTitledBorder("Buttons"));
        buttonPanel.add(new JButton("Standard Button"));
        JButton disabledButton = new JButton("Disabled Button");
        disabledButton.setEnabled(false);
        buttonPanel.add(disabledButton);
        buttonPanel.add(new JToggleButton("Toggle Button"));
        panel.add(buttonPanel);

        // Checkboxes and radio buttons
        JPanel selectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("Selection Controls"));
        selectionPanel.add(new JCheckBox("Checkbox"));
        selectionPanel.add(new JCheckBox("Selected Checkbox", true));

        ButtonGroup group = new ButtonGroup();
        JRadioButton radio1 = new JRadioButton("Option 1", true);
        JRadioButton radio2 = new JRadioButton("Option 2");
        group.add(radio1);
        group.add(radio2);
        selectionPanel.add(radio1);
        selectionPanel.add(radio2);
        panel.add(selectionPanel);

        // Sliders
        JPanel sliderPanel = new JPanel(new BorderLayout());
        sliderPanel.setBorder(BorderFactory.createTitledBorder("Sliders"));
        JSlider slider = new JSlider(0, 100, 50);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        sliderPanel.add(slider);
        panel.add(sliderPanel);

        return panel;
    }

    private JPanel createTextComponentsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Text fields
        JPanel textFieldPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        textFieldPanel.setBorder(BorderFactory.createTitledBorder("Text Fields"));

        textFieldPanel.add(new JLabel("Standard Text Field:"));
        textFieldPanel.add(new JTextField("Edit me"));

        textFieldPanel.add(new JLabel("Password Field:"));
        textFieldPanel.add(new JPasswordField("secret"));

        textFieldPanel.add(new JLabel("Disabled Field:"));
        JTextField disabledField = new JTextField("Disabled");
        disabledField.setEnabled(false);
        textFieldPanel.add(disabledField);

        panel.add(textFieldPanel);

        // Text area
        JPanel textAreaPanel = new JPanel(new BorderLayout());
        textAreaPanel.setBorder(BorderFactory.createTitledBorder("Text Area"));

        JTextArea textArea = new JTextArea(5, 20);
        textArea.setText("This is a text area\nwith multiple lines\nto test word wrapping and other features");
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textAreaPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        panel.add(textAreaPanel);

        return panel;
    }

    private JPanel createDataComponentsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Combo box
        JPanel comboPanel = new JPanel(new BorderLayout());
        comboPanel.setBorder(BorderFactory.createTitledBorder("Combo Box"));
        String[] options = {"Option 1", "Option 2", "Option 3", "Option 4"};
        JComboBox<String> comboBox = new JComboBox<>(options);
        comboPanel.add(comboBox, BorderLayout.NORTH);
        panel.add(comboPanel);

        // List
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("List"));
        JList<String> list = new JList<>(new String[] {
                "Item 1", "Item 2", "Item 3", "Item 4", "Item 5",
                "Item 6", "Item 7", "Item 8"
        });
        listPanel.add(new JScrollPane(list), BorderLayout.CENTER);
        panel.add(listPanel);

        // Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Table"));
        String[] columnNames = {"Name", "Score", "Rank"};
        Object[][] data = {
                {"Steve", 350, "Miner"},
                {"Alex", 420, "Explorer"},
                {"Creeper", 0, "Mob"},
                {"Enderman", 500, "Boss"},
                {"Villager", 50, "NPC"}
        };
        JTable table = new JTable(data, columnNames);
        tablePanel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(tablePanel);

        return panel;
    }

    public static void launchDemo() {
        SwingUtilities.invokeLater(() -> {
            MinecraftLookAndFeel.set();
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new GeneralDemo());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    // For standalone testing
    public static void main(String[] args) {
        launchDemo();
    }
}
