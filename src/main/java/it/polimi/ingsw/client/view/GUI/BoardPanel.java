package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.model.IslandBoard;
import it.polimi.ingsw.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class BoardPanel extends JLayeredPane {

    private TileButton[][] tileButtons;
    private Tile.IndexTile selectedTile;
    private Icon selectedButtonIcon;
    private boolean doubleSelection;
    private GUI gui;
    private GamePanel gamePanel;

    public BoardPanel(GUI gui, GamePanel gamePanel) {
        this.gui = gui;
        this.gamePanel = gamePanel;
        this.tileButtons = new TileButton[5][5];
        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/SantoriniBoard.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon boardImage = new ImageIcon(imagePath);
        boardImage = new ImageIcon(boardImage.getImage().getScaledInstance(800, 800, Image.SCALE_SMOOTH));
        JLabel boardLabel = new JLabel(boardImage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                tileButtons[row][col] = new TileButton();
                buttonPanel.add(tileButtons[row][col]);
            }
        }
        this.setPreferredSize(new Dimension(800, 800));
        this.add(boardLabel, Integer.valueOf(0));
        this.add(buttonPanel, Integer.valueOf(1));
        boardLabel.setBounds(0, 0, 800, 800);
        buttonPanel.setBounds((800 - 595) / 2, (800 - 595) / 2, 595, 595);
        this.repaint();
    }

    public void updateBoard(IslandBoard board) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                this.tileButtons[row][col].setTile(board.getTile(row, col));
            }
        }
    }

    public void setDoubleSelection(boolean doubleSelection) {
        this.doubleSelection = doubleSelection;
    }

    private void selectTile(Tile.IndexTile indexTile) {
        if (doubleSelection) {
            if (selectedTile == null) {
                selectedTile = indexTile;
                TileButton chosenButton = tileButtons[indexTile.getRow()][indexTile.getCol()];
                selectedButtonIcon = chosenButton.getIcon();
                chosenButton.setIcon(null);
                chosenButton.setOpaque(true);
                chosenButton.setBackground(gamePanel.getPlayerColor());
            } else {
                TileButton selectedButton = tileButtons[selectedTile.getRow()][selectedTile.getCol()];
                selectedButton.setOpaque(false);
                selectedButton.setIcon(this.selectedButtonIcon);
                gui.notify(selectedTile.toString() + "-" + indexTile.toString());
                selectedTile = null;
                selectedButtonIcon = null;

            }
        } else {
            gui.notify(indexTile.toString());
        }
    }

    public void highlight(Color playerColor, Collection<Tile.IndexTile> tilesToChoose) {
        for (Tile.IndexTile index : tilesToChoose) {
            tileButtons[index.getRow()][index.getCol()].setBorder(BorderFactory.createLineBorder(playerColor, 2));
        }
    }

    public void removeHighlight() {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                this.tileButtons[row][col].setBorder(null);
            }
        }
    }


    private class TileButton extends JButton {
        private Tile tile;


        public TileButton() {
            this.setSize(800 / 5, 800 / 5);
            this.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectTile(tile.getIndex());
                }
            });
        }

        public void setTile(Tile tile) {
            this.tile = tile;

            String imagePath = null;
            try {
                imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/";
            } catch (IOException e) {
                e.printStackTrace();
            }

            StringBuilder imageToLoad = new StringBuilder();
            if (tile.getBuilding().getDome()) {
                imageToLoad.append("dome");
            } else {
                int level = tile.getBuilding().getLevel().getLevelInt();
                imageToLoad.append("level").append(level);
                if (tile.getCurrentWorker() != null) {
                    imageToLoad.append("_with_");
                    String workerColor = tile.getCurrentWorker().getColor().getMessage().toLowerCase();
                    imageToLoad.append(workerColor).append("_worker");
                }
            }
            ImageIcon tileImage = new ImageIcon(imagePath + imageToLoad.toString() + ".png");
            tileImage = new ImageIcon(tileImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH));
            this.setIcon(tileImage);
        }
    }
}
