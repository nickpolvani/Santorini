package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.model.god.GodDescription;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

/**
 * Panel shown to the user after god selection, it is used when the players place their workers and remains
 * active until the game ends. It contains a BoardPanel
 */
public class GamePanel extends ActivePanel {

    private final BoardPanel boardPanel;
    private final JPanel textPanel;
    private final JButton yesButton;
    private final JButton noButton;
    private final JTextArea textArea = new JTextArea(3, 60);
    private Color playerColor;

    public GamePanel(GUI gui) {
        super();
        textPanel = new JPanel();
        yesButton = new JButton("Yes") {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(150, 35);
            }
        };
        noButton = new JButton("No") {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(150, 35);
            }
        };

        yesButton.setBackground(Color.GREEN);
        noButton.setBackground(Color.RED);
        yesButton.addActionListener(e -> {
            gui.notify("yes");
            yesButton.setVisible(false);
            noButton.setVisible(false);
        });
        yesButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));

        noButton.addActionListener(e -> {
            gui.notify("no");
            yesButton.setVisible(false);
            noButton.setVisible(false);
        });
        noButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));

        textPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textPanel.setLayout(new BorderLayout(150, 0));

        textArea.setEditable(false);
        textArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));

        textPanel.add(textArea, BorderLayout.NORTH);

        textPanel.add(yesButton, BorderLayout.WEST);

        textPanel.add(noButton, BorderLayout.EAST);

        this.boardPanel = new BoardPanel(gui, this);
        GodLabel godLabel = new GodLabel(gui.getPlayerGod());

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.gridy = 0;
        c.gridx = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        this.add(textPanel, c);
        c.insets = new Insets(10, 0, 0, 0);
        c.gridy = 1;
        c.gridx = 0;
        c.anchor = GridBagConstraints.CENTER;
        this.add(boardPanel, c);
        c.gridx = 1;
        this.add(godLabel, c);
        this.setOpaque(true);
        this.setBackground(Color.CYAN);
        yesButton.setVisible(false);
        noButton.setVisible(false);
        gui.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        gui.frame.setSize(gui.getScreenSize());
        gui.frame.setResizable(false);
    }

    protected JPanel getTextPanel() {
        return textPanel;
    }

    public Color getPlayerColor() {
        return playerColor;
    }

    public void setPlayerColor(Color color) {
        this.playerColor = color;
    }

    public BoardPanel getBoardPanel() {
        return boardPanel;
    }

    public void highlight(Collection<Tile.IndexTile> tilesToChoose) {
        boardPanel.highlight(playerColor, tilesToChoose);
    }

    /**
     * displays buttons used to perform a choice
     *
     * @param value true makes the buttons visible
     */
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

    private static class GodLabel extends JLabel {
        GodLabel(GodDescription god) {
            String imagePath = null;
            try {
                imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/god/" +
                        god.getName() + ".png";
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageIcon godImage = new ImageIcon(imagePath);
            godImage = new ImageIcon(godImage.getImage().getScaledInstance(300, 504, Image.SCALE_SMOOTH));
            this.setIcon(godImage);
        }
    }
}
