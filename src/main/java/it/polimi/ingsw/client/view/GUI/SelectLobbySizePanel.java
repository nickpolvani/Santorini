package it.polimi.ingsw.client.view.GUI;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class SelectLobbySizePanel extends ActivePanel {

    private final JButton b1;
    private final JButton b2;

    public SelectLobbySizePanel(GUI gui) {
        super();
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        b1 = new JButton("2 Players");
        b2 = new JButton("3 Players");

        b1.addActionListener(e -> gui.notify("2"));

        b2.addActionListener(e -> gui.notify("3"));

        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/WelcomeToSantorini.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon image = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel(image);

        c.insets = new Insets(0, 200, 20, 20);
        c.anchor = GridBagConstraints.NORTH;
        c.gridx = 0;
        c.gridy = 0;
        this.add(imageLabel, c);


        c.insets = new Insets(20, 20, 20, 20);
        c.anchor = GridBagConstraints.CENTER;
        c.ipadx = 40;
        c.ipady = 20;
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 3;
        this.add(textLabel, c);

        c.insets = new Insets(20, 0, 20, 0);
        c.gridwidth = 1;
        c.gridx = 0;
        c.gridy = 2;
        b1.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        this.add(b1, c);

        c.gridx = 1;
        c.gridy = 2;
        b2.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        this.add(b2, c);
    }

    @Override
    public void noReply() {
        this.b1.setVisible(false);
        this.b2.setVisible(false);
        this.remove(this.b1);
        this.remove(this.b2);

    }


}
