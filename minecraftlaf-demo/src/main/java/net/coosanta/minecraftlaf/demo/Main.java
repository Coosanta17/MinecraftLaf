package net.coosanta.minecraftlaf.demo;

import net.coosanta.minecraftlaf.MinecraftLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static JFrame frame = new JFrame();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinecraftLookAndFeel.set();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("Minecraft Look and Feel Demo");
            frame.setSize(new Dimension(800, 600));
            frame.setContentPane(makePanel());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JPanel makePanel() {
        JPanel mainPanel = new JPanel(new FlowLayout());

        JButton generalDemo = new JButton("General Demo");
        generalDemo.addActionListener(e -> {
            frame.setContentPane(new GeneralDemo());
            frame.revalidate();
            frame.repaint();
        });

        JButton buttonTest = new JButton("Button Test");
        buttonTest.addActionListener(e -> {
            frame.setContentPane(new ButtonTest());
            frame.revalidate();
            frame.repaint();
        });

        mainPanel.add(generalDemo);
        mainPanel.add(buttonTest);

        return mainPanel;
    }
}
