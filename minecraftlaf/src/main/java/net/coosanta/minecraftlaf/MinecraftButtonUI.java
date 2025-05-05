package net.coosanta.minecraftlaf;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;

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
        // FIXME. NOT WOroking
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
        // Comments are provided because this code is super vague.
        int width = button.getWidth();
        int height = button.getHeight();

        int LEFT_AND_RIGHT_EDGE = 2;
        int TOP_EDGE = 2;
        int BOTTOM_EDGE = 3;

        // Draw corners
        // Top-left
        g.drawImage(img, 0, 0, LEFT_AND_RIGHT_EDGE, TOP_EDGE, 0, 0, LEFT_AND_RIGHT_EDGE, TOP_EDGE, null);

        // Top-right
        g.drawImage(img, width - LEFT_AND_RIGHT_EDGE, 0, width, TOP_EDGE,
                img.getWidth() - LEFT_AND_RIGHT_EDGE, 0, img.getWidth(), TOP_EDGE, null);

        // Bottom-left
        g.drawImage(img, 0, height - BOTTOM_EDGE, LEFT_AND_RIGHT_EDGE, height,
                0, img.getHeight() - BOTTOM_EDGE, LEFT_AND_RIGHT_EDGE, img.getHeight(), null);

        // Bottom-right
        g.drawImage(img, width - LEFT_AND_RIGHT_EDGE, height - BOTTOM_EDGE, width, height,
                img.getWidth() - LEFT_AND_RIGHT_EDGE, img.getHeight() - BOTTOM_EDGE,
                img.getWidth(), img.getHeight(), null);

        // Draw top edge without corners
        int middleWidth = img.getWidth() - (LEFT_AND_RIGHT_EDGE * 2);
        int remainingWidth = width - (LEFT_AND_RIGHT_EDGE * 2);

        if (remainingWidth > 0) {
            drawHorizontalEdge(g, img, LEFT_AND_RIGHT_EDGE, 0, remainingWidth, TOP_EDGE,
                    LEFT_AND_RIGHT_EDGE, 0, middleWidth, TOP_EDGE);

            // Draw bottom edge without corners
            drawHorizontalEdge(g, img, LEFT_AND_RIGHT_EDGE, height - BOTTOM_EDGE, remainingWidth, BOTTOM_EDGE,
                    LEFT_AND_RIGHT_EDGE, img.getHeight() - BOTTOM_EDGE, middleWidth, BOTTOM_EDGE);
        }

        // Calculate middle height considering different top and bottom edges
        int imgMiddleHeight = img.getHeight() - (TOP_EDGE + BOTTOM_EDGE);
        int remainingHeight = height - (TOP_EDGE + BOTTOM_EDGE);

        if (remainingHeight > 0) {
            // Draw left edge without corners
            drawVerticalEdge(g, img, 0, TOP_EDGE, LEFT_AND_RIGHT_EDGE, remainingHeight,
                    0, TOP_EDGE, LEFT_AND_RIGHT_EDGE, imgMiddleHeight);

            // Draw right edge without corners
            drawVerticalEdge(g, img, width - LEFT_AND_RIGHT_EDGE, TOP_EDGE, LEFT_AND_RIGHT_EDGE, remainingHeight,
                    img.getWidth() - LEFT_AND_RIGHT_EDGE, TOP_EDGE, LEFT_AND_RIGHT_EDGE, imgMiddleHeight);
        }

        if (remainingWidth > 0 && remainingHeight > 0) {
            drawCenterArea(g, img, LEFT_AND_RIGHT_EDGE, TOP_EDGE, remainingWidth, remainingHeight,
                    LEFT_AND_RIGHT_EDGE, TOP_EDGE, middleWidth, imgMiddleHeight);
        }
    }

    private void drawHorizontalEdge(Graphics2D g, BufferedImage img,
                                    int dx1, int dy1, int dw, int dh,
                                    int sx1, int sy1, int sw, int sh) {
        if (dw > sw) {
            // Tile it
            int fullTiles = dw / sw;
            int remainder = dw % sw;

            for (int i = 0; i < fullTiles; i++) {
                g.drawImage(img,
                        dx1 + (i * sw), dy1, dx1 + ((i + 1) * sw), dy1 + dh,
                        sx1, sy1, sx1 + sw, sy1 + sh, null);
            }

            if (remainder > 0) {
                g.drawImage(img,
                        dx1 + (fullTiles * sw), dy1, dx1 + dw, dy1 + dh,
                        sx1, sy1, sx1 + remainder, sy1 + sh, null);
            }
        } else {
            // Crop it
            g.drawImage(img,
                    dx1, dy1, dx1 + dw, dy1 + dh,
                    sx1, sy1, sx1 + dw, sy1 + sh, null);
        }
    }

    private void drawVerticalEdge(Graphics2D g, BufferedImage img,
                                  int dx1, int dy1, int dw, int dh,
                                  int sx1, int sy1, int sw, int sh) {
        if (dh > sh) {
            // Tile it
            int fullTiles = dh / sh;
            int remainder = dh % sh;

            for (int i = 0; i < fullTiles; i++) {
                g.drawImage(img,
                        dx1, dy1 + (i * sh), dx1 + dw, dy1 + ((i + 1) * sh),
                        sx1, sy1, sx1 + sw, sy1 + sh, null);
            }

            if (remainder > 0) {
                g.drawImage(img,
                        dx1, dy1 + (fullTiles * sh), dx1 + dw, dy1 + dh,
                        sx1, sy1, sx1 + sw, sy1 + remainder, null);
            }
        } else {
            // Crop it
            g.drawImage(img,
                    dx1, dy1, dx1 + dw, dy1 + dh,
                    sx1, sy1, sx1 + sw, sy1 + dh, null);
        }
    }

    private void drawCenterArea(Graphics2D g, BufferedImage img,
                                int dx1, int dy1, int dw, int dh,
                                int sx1, int sy1, int sw, int sh) {
        if (dw > sw && dh > sh) {
            // Tile both directions
            int horizontalTiles = dw / sw;
            int horizontalRemainder = dw % sw;
            int verticalTiles = dh / sh;
            int verticalRemainder = dh % sh;

            // Draw full tiles
            for (int y = 0; y < verticalTiles; y++) {
                for (int x = 0; x < horizontalTiles; x++) {
                    g.drawImage(img,
                            dx1 + (x * sw), dy1 + (y * sh),
                            dx1 + ((x + 1) * sw), dy1 + ((y + 1) * sh),
                            sx1, sy1, sx1 + sw, sy1 + sh, null);
                }

                // Draw horizontal remainder for this row
                if (horizontalRemainder > 0) {
                    g.drawImage(img,
                            dx1 + (horizontalTiles * sw), dy1 + (y * sh),
                            dx1 + dw, dy1 + ((y + 1) * sh),
                            sx1, sy1, sx1 + horizontalRemainder, sy1 + sh, null);
                }
            }

            // Draw vertical remainder row
            if (verticalRemainder > 0) {
                for (int x = 0; x < horizontalTiles; x++) {
                    g.drawImage(img,
                            dx1 + (x * sw), dy1 + (verticalTiles * sh),
                            dx1 + ((x + 1) * sw), dy1 + dh,
                            sx1, sy1, sx1 + sw, sy1 + verticalRemainder, null);
                }

                // Draw bottom-right corner remainder
                if (horizontalRemainder > 0) {
                    g.drawImage(img,
                            dx1 + (horizontalTiles * sw), dy1 + (verticalTiles * sh),
                            dx1 + dw, dy1 + dh,
                            sx1, sy1, sx1 + horizontalRemainder, sy1 + verticalRemainder, null);
                }
            }
        } else if (dw > sw) {
            // Only tile horizontally
            drawHorizontalEdge(g, img, dx1, dy1, dw, dh, sx1, sy1, sw, dh);
        } else if (dh > sh) {
            // Only tile vertically
            drawVerticalEdge(g, img, dx1, dy1, dw, dh, sx1, sy1, dw, sh);
        } else {
            // Crop both directions
            g.drawImage(img,
                    dx1, dy1, dx1 + dw, dy1 + dh,
                    sx1, sy1, sx1 + dw, sy1 + dh, null);
        }
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        return new Dimension(200, 20);
    }
}
