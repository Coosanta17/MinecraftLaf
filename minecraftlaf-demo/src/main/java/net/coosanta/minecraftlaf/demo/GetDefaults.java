package net.coosanta.minecraftlaf.demo;

import javax.swing.*;

import java.util.List;

public class GetDefaults {
    public static void main(String[] args) {
        UIDefaults uiDefaults = UIManager.getDefaults();

        List<String> keys =
                uiDefaults.keySet()
                        .stream()
                        .map(Object::toString)
                        .sorted()
                        .toList();

        keys.forEach((k) -> {
            if (k.endsWith("UI")) {
                System.out.println(k);
            }
        });
    }
}
