package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class GamePanel extends ActivePanel {

    private final BoardPanel boardPanel;
    private JPanel textPanel;
    private JButton yesButton;
    private JButton noButton;
    private Color playerColor;

    JTextArea textArea = new JTextArea(3, 58);

    public GamePanel(GUI gui) {
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
                yesButton.setVisible(false);
                noButton.setVisible(false);
            }
        });

        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gui.notify("no");
                yesButton.setVisible(false);
                noButton.setVisible(false);
            }
        });
        textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textPanel.setLayout(new BorderLayout());

        textArea.setEditable(false);
        textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));

        textPanel.add(textArea, BorderLayout.NORTH);

        textPanel.add(yesButton, BorderLayout.WEST);

        textPanel.add(noButton, BorderLayout.EAST);


        this.boardPanel = new BoardPanel(gui, this);


        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        this.add(textPanel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        this.add(boardPanel, c);
        yesButton.setVisible(false);
        noButton.setVisible(false);


    }

    protected JPanel getTextPanel() {
        return textPanel;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }


    public void highlight(Collection<Tile.IndexTile> tilesToChoose) {
        boardPanel.highlight(playerColor, tilesToChoose);
    }

    public void setPlayerColor(Color color) {
        this.playerColor = color;
    }

    public void showChoiceButtons(boolean value) {
        textPanel.add(yesButton, BorderLayout.WEST);
        textPanel.add(noButton, BorderLayout.EAST);
        yesButton.setVisible(value);
        noButton.setVisible(value);
    }

    @Override
    protected void showMessage(String message) {
        this.textArea.setText(message);
    }
}
