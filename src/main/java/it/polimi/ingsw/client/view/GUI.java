package it.polimi.ingsw.client.view;

import it.polimi.ingsw.model.IslandBoard;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GUI extends View {

    IslandBoard board;

    JPanel gamePanel;

    JPanel setupPanel;

    JPanel chooseGodPanel;

    public GUI() {
        this.board = null;
    }


    @Override
    public void showMessage(String message) {

    }

    @Override
    public void printBoard(IslandBoard board) {

    }

    @Override
    public void readInput() {

    }


    @Override
    public void start() {

    }

    @Override
    public void close() {

    }

    public static void main(String[] args) throws IOException {
        GUI gui = new GUI();
        gui.createAndShowGUI();
    }

    public void createAndShowGUI() throws IOException {
        JFrame frame = new JFrame("Santorini");

        frame.setLayout(new BorderLayout());
        JPanel textPanel = new JPanel();

        JLabel jlabel = new JLabel("Welcome to Santorini");
        textPanel.add(jlabel);
        String path = new File(".").getCanonicalPath();

        final BufferedImage bufferedImage = ImageIO.read(new File(path + "/src/main/resources/images/SantoriniBoard.png"));

        BoardPanel boardPanel = new BoardPanel(bufferedImage);

        boardPanel.setBounds(0, 0, 600, 600);

        frame.setBounds(0, 0, 700, 700);


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(textPanel, BorderLayout.NORTH);
        frame.add(boardPanel, BorderLayout.CENTER);


        frame.setVisible(true);

    }

}
