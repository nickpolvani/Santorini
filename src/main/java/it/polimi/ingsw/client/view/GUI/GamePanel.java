package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.ChooseOptions;
import it.polimi.ingsw.bean.options.Options;
import it.polimi.ingsw.bean.options.PlaceWorkersOptions;
import it.polimi.ingsw.bean.options.TileOptions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends ActivePanel {

    private final BoardPanel boardPanel;
    private JPanel textPanel;
    private JButton yesButton;
    private JButton noButton;
    private Color playerColor;

    public GamePanel(GUI gui, Options currentOptions) {
        super();

        textPanel = new JPanel();
        yesButton = new JButton("Yes");
        noButton = new JButton("No");
        yesButton.setBackground(Color.GREEN);
        noButton.setBackground(Color.RED);
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.notify("yes");
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.notify("no");
            }
        });
        textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textPanel.setLayout(new BorderLayout());


        textPanel.add(textLabel, BorderLayout.NORTH);

        textPanel.add(yesButton, BorderLayout.WEST);

        textPanel.add(noButton, BorderLayout.EAST);


        this.boardPanel = new BoardPanel(gui, this);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        this.add(textPanel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        this.add(boardPanel, c);
        setCurrentOptions(currentOptions);

    }

    @Override
    public void setCurrentOptions(Options currentOptions) {
        this.currentOptions = currentOptions;
        if (currentOptions instanceof PlaceWorkersOptions) {
            switch (((PlaceWorkersOptions) currentOptions).getColor()) {
                case RED:
                    this.playerColor = Color.RED;
                    break;
                case BLUE:
                    this.playerColor = Color.BLUE;
                    break;
                case YELLOW:
                    this.playerColor = Color.YELLOW;
                    break;
            }
            boardPanel.setDoubleSelection(true);
        } else {
            boardPanel.setDoubleSelection(false);
        }
        if (currentOptions instanceof ChooseOptions) {
            yesButton.setVisible(true);
            noButton.setVisible(true);
        } else {
            yesButton.setVisible(false);
            noButton.setVisible(false);
        }
        if (currentOptions instanceof TileOptions) {
            boardPanel.highlight(playerColor, ((TileOptions) currentOptions).getTilesToChoose());
        } else {
            boardPanel.removeHighlight();
        }
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }


}
