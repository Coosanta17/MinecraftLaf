package net.coosanta.minecraftlaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class MinecraftButtonUI extends BasicButtonUI {

    private BufferedImage buttonImage;
    private BufferedImage buttonHoverImage;
    private BufferedImage buttonDisabledImage;

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        loadButtonImages();

        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);
        button.setBorderPainted(false);
    }

    private void loadButtonImages() {
        try {
            buttonImage = ImageIO.read(loadResource("/icons/button/test/9-slice_button_test.png")); // FIXME
            buttonHoverImage = ImageIO.read(loadResource("/icons/button/button_highlighted.png"));
            buttonDisabledImage = ImageIO.read(loadResource("/icons/button/button_disabled.png"));
        } catch (IOException e) {
            System.err.println("Error loading button images: " + e.getMessage());
        }
    }

    private InputStream loadResource(String path) {
        InputStream is = getClass().getResourceAsStream(path);
        if (is == null) {
            throw new NullPointerException("Cannot load resource '" + path + "'.");
        }
        return is;
    }

    @Override
    public void paint(Graphics graphics, JComponent content) {
        AbstractButton button = (AbstractButton) content;
        ButtonModel model = button.getModel();

        Graphics2D g2d = (Graphics2D) graphics.create();

        BufferedImage img;
        if (!button.isEnabled()) {
            img = buttonDisabledImage;
        } else if (model.isRollover()) {
            img = buttonHoverImage;
        } else {
            img = buttonImage;
        }

        drawButtonBackground(g2d, button, img);
        g2d.dispose();


        super.paint(graphics, content);
    }

    private void drawButtonBackground(Graphics2D g, AbstractButton button, BufferedImage img) {
        int width = button.getWidth();
        int height = button.getHeight();

        final int EDGE_SIZE = 2;
        final int BOTTOM_EDGE_SIZE = 3;

        // Left edge
        g.drawImage(img,
                0, 0, EDGE_SIZE, height,
                0, 0, EDGE_SIZE, img.getHeight(),
                null);

        // Middle (tiled if necessary)
        int middleWidth = img.getWidth() - (EDGE_SIZE * 2);
        int remainingWidth = width - (EDGE_SIZE * 2);

        if (remainingWidth > 0) {
            if (remainingWidth > middleWidth) {
                // Tile it
                int fullTiles = remainingWidth / middleWidth;
                int remainder = remainingWidth % middleWidth;

                for (int i = 0; i < fullTiles; i++) {
                    g.drawImage(img,
                            EDGE_SIZE + (i * middleWidth), 0,
                            EDGE_SIZE + ((i + 1) * middleWidth), height,
                            EDGE_SIZE, 0,
                            img.getWidth() - EDGE_SIZE, img.getHeight(),
                            null);
                }

                if (remainder > 0) {
                    g.drawImage(img,
                            EDGE_SIZE + (fullTiles * middleWidth), 0,
                            width - EDGE_SIZE, height,
                            EDGE_SIZE, 0,
                            EDGE_SIZE + remainder, img.getHeight(),
                            null);
                }
            } else {
                // Crop it
                g.drawImage(img,
                        EDGE_SIZE, 0,
                        width - EDGE_SIZE, height,
                        EDGE_SIZE, 0,
                        EDGE_SIZE + remainingWidth, img.getHeight(),
                        null);
            }

            // Right edge
            g.drawImage(img,
                    width - EDGE_SIZE, 0,
                    width, height,
                    img.getWidth() - EDGE_SIZE, 0,
                    img.getWidth(), img.getHeight(),
                    null);
        }
    }
}
