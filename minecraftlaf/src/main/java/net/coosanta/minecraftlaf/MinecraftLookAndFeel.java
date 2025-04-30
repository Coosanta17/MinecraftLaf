package net.coosanta.minecraftlaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

public class MinecraftLookAndFeel extends BasicLookAndFeel {
    public static final Font mojangles;

    static {
        try {
            mojangles = loadMinecraftFonts();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public MinecraftLookAndFeel() {
        super();
    }

    @Override
    public String getName() {
        return "Minecraft Look and Feel";
    }

    @Override
    public String getID() {
        return "minecraftlaf";
    }

    @Override
    public String getDescription() {
        return "Minecraft UI imitation";
    }

    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);
        table.put("ButtonUI", MinecraftButtonUI.class.getName());
    }

    @Override
    protected void initComponentDefaults(UIDefaults table) {
        super.initComponentDefaults(table);

        table.put("defaultFont", mojangles);
    }

    private static Font loadMinecraftFonts() throws IOException {
        Font mojangles = loadFont("/fonts/mojangles.ttf");
        Font unifont = loadFont("/fonts/unifont-16.0.03.otf");
        return createCompositeFont(mojangles, unifont);
    }

    private static Font loadFont(String path) throws IOException {
        try {
            InputStream is = MinecraftLookAndFeel.class.getResourceAsStream(path);
            if (is != null) {
                return Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 18f);
            }
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
        throw new IOException("Failed to load font '" + path + "'.");
    }

    private static Font createCompositeFont(Font primary, Font fallback) {
        // TODO: Fix Mojangles not being used.
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.registerFont(primary);
        ge.registerFont(fallback);

        return primary;
    }

    public static void set() {
        try {
            UIManager.setLookAndFeel(new MinecraftLookAndFeel());
        } catch (UnsupportedLookAndFeelException e) {
            // TODO
            e.printStackTrace();
        }
    }
}
