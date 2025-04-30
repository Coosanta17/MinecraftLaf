package net.coosanta.minecraftlaf.demo;

import net.coosanta.minecraftlaf.MinecraftLookAndFeel;

import javax.swing.*;
import java.awt.*;

public class ButtonTest extends JPanel {
    public ButtonTest() {
        setLayout(new GridLayout());
        add(new JButton("Click me!"));
        add(new JButton("Click me again!"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MinecraftLookAndFeel.set();
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.add(new ButtonTest());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
