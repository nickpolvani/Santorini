package it.polimi.ingsw.client.view.GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Panel containing welcome image
 */
public class WelcomeImg extends JPanel {
    private BufferedImage img;

    public WelcomeImg() {
        try {
            String imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/WelcomeToSantorini.png";
            img = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            JLabel label = new JLabel("Welcome to Santorini!!");
            this.add(label);
            e.printStackTrace();
        }

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int imgWidth = img.getWidth(null);
        int imgHeight = img.getHeight(null);

        double imgAspect = (double) imgHeight / imgWidth;

        int canvasWidth = this.getWidth();
        int canvasHeight = this.getHeight();

        double canvasAspect = (double) canvasHeight / canvasWidth;

        int x1 = 0; // top left X position
        int y1 = 0; // top left Y position
        int x2 = 0; // bottom right X position
        int y2 = 0; // bottom right Y position

        if (imgWidth < canvasWidth && imgHeight < canvasHeight) {
            // the image is smaller than the canvas
            x1 = (canvasWidth - imgWidth) / 2;
            y1 = (canvasHeight - imgHeight) / 2;
            x2 = imgWidth + x1;
            y2 = imgHeight + y1;

        } else {
            if (canvasAspect > imgAspect) {
                y1 = canvasHeight;
                // keep image aspect ratio
                canvasHeight = (int) (canvasWidth * imgAspect);
                y1 = (y1 - canvasHeight) / 2;
            } else {
                x1 = canvasWidth;
                // keep image aspect ratio
                canvasWidth = (int) (canvasHeight / imgAspect);
                x1 = (x1 - canvasWidth) / 2;
            }
            x2 = canvasWidth + x1;
            y2 = canvasHeight + y1;
        }
        g.drawImage(img, x1, y1, x2, y2, 0, 0, imgWidth, imgHeight, null);
        g.dispose();
    }
}
