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
    private int boardSide;
    private int gridSide;

    private Tile.IndexTile poseidonTile;

    private JButton poseidonButtons[] = new JButton[3];
    private Color playerColor;

    public BoardPanel(GUI gui, GamePanel gamePanel) {
        this.gui = gui;
        this.gamePanel = gamePanel;
        this.tileButtons = new TileButton[5][5];

        boardSide = (int) (Math.min(gui.getScreenSize().getHeight(), gui.getScreenSize().getWidth()) * 0.75);
        gridSide = (int) (boardSide * 595 / 800);


        String imagePath = null;
        try {
            imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/SantoriniBoard.png";
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon boardImage = new ImageIcon(imagePath);
        boardImage = new ImageIcon(boardImage.getImage().getScaledInstance(boardSide, boardSide, Image.SCALE_SMOOTH));
        JLabel boardLabel = new JLabel(boardImage);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 5));
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                tileButtons[row][col] = new TileButton();
                buttonPanel.add(tileButtons[row][col]);
            }
        }
        this.setPreferredSize(new Dimension(boardSide, boardSide));
        this.add(boardLabel, Integer.valueOf(0));
        this.add(buttonPanel, Integer.valueOf(1));
        boardLabel.setBounds(0, 0, boardSide, boardSide);
        buttonPanel.setBounds((boardSide - gridSide) / 2, (boardSide - gridSide) / 2, gridSide, gridSide);
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
        this.playerColor = playerColor;
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

    public void disablePoseidonBuild(Collection<Tile.IndexTile> tilesToChoose) {
        poseidonTile = null;
        for (JButton poseidonButton : poseidonButtons) {
            poseidonButton.setVisible(false);
        }
        for (Tile.IndexTile indexTile : tilesToChoose) {
            TileButton tileButton = tileButtons[indexTile.getRow()][indexTile.getCol()];
            tileButton.removeActionListener(tileButton.poseidonActionListener);
            tileButton.addActionListener(tileButton.defaultActionListener);
        }
    }

    public void enablePoseidonBuild(Collection<Tile.IndexTile> tilesToChoose) {
        gui.showMessage("Select the tile where you want to build and how many levels you want to build");
        int i = 1;
        for (int j = 0; j < poseidonButtons.length; j++) {
            poseidonButtons[j] = new JButton(String.valueOf(i)) {
                @Override
                public Dimension getPreferredSize() {
                    return new Dimension(150, 35);
                }
            };
            poseidonButtons[j].setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
            int finalI = i;
            poseidonButtons[j].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    gui.notify(poseidonTile.toString() + "-" + finalI);
                    disablePoseidonBuild(tilesToChoose);
                }
            });
            i++;
        }

        for (Tile.IndexTile index : tilesToChoose) {
            TileButton tileButton = tileButtons[index.getRow()][index.getCol()];
            tileButton.removeActionListener(tileButton.defaultActionListener);
            tileButton.addActionListener(tileButton.poseidonActionListener);
        }
        highlight(playerColor, tilesToChoose);

    }

    private void showPoseidonButtons(int levelsToBuild) {
        for (JButton poseidonButton : poseidonButtons) {
            poseidonButton.setVisible(false);
        }
        gamePanel.getTextPanel().add(poseidonButtons[0], BorderLayout.WEST);
        gamePanel.getTextPanel().add(poseidonButtons[1], BorderLayout.CENTER);
        gamePanel.getTextPanel().add(poseidonButtons[2], BorderLayout.EAST);
        for (int j = 0; j < 3 && j < levelsToBuild; j++) {
            poseidonButtons[j].setVisible(true);
        }
    }


    private class TileButton extends JButton {
        private Tile tile;

        private ActionListener defaultActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectTile(tile.getIndex());
            }
        };

        private ActionListener poseidonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                poseidonTile = tile.getIndex();
                showPoseidonButtons(4 - tile.getBuildingLevel());
            }
        };

        public TileButton() {
            this.setSize(gridSide / 5, gridSide / 5);
            this.addActionListener(defaultActionListener);
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
