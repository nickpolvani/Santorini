package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.bean.options.GodOptions;
import it.polimi.ingsw.model.god.GodDescription;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ChooseGodPanel extends ActivePanel {

    GUI gui;
    Map<GodDescription, ImageIcon> imageMap = new HashMap<>();

    public ChooseGodPanel(GUI gui, GodOptions currentOptions) {
        super();
        this.gui = gui;

        DefaultListModel<GodDescription> listModel = new DefaultListModel<>();

        for (GodDescription godDescription : currentOptions.getGodsToChoose()) {
            listModel.addElement(godDescription);
            String imagePath = null;
            try {
                imagePath = new File(".").getCanonicalPath() + "/src/main/resources/images/god/" +
                        godDescription.getName() + ".png";
            } catch (IOException e) {
                e.printStackTrace();
            }
            ImageIcon godImage = new ImageIcon(imagePath, godDescription.getName());
            godImage = new ImageIcon(godImage.getImage().getScaledInstance(84, 141, Image.SCALE_SMOOTH));
            imageMap.put(godDescription, godImage);
        }
        JList<GodDescription> list = new JList<>(listModel);
        list.setCellRenderer(new GodChoiceRenderer());
        JScrollPane scrollPane = new JScrollPane(list);


        JButton confirmButton = new JButton("confirm");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder selectedGods = new StringBuilder();
                for (GodDescription selectedGodDescription : list.getSelectedValuesList()) {
                    selectedGods.append(selectedGodDescription.getName()).append(", ");
                }
                selectedGods.delete(selectedGods.length() - 2, selectedGods.length());  //remove ", " at the end
                gui.notify(new String(selectedGods));
            }
        });


        this.setLayout(new BorderLayout());
        this.add(textLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(confirmButton, BorderLayout.SOUTH);

    }

    @Override
    protected void showMessage(String message) {
        textLabel.setText(message.split(">")[0]);
    }

    private class GodChoiceRenderer extends JLabel implements ListCellRenderer<GodDescription> {
        public GodChoiceRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends GodDescription> list, GodDescription godDescription, int index, boolean isSelected, boolean cellHasFocus) {
            setText(godDescription.getDescriptionOfPower());
            setIcon(imageMap.get(godDescription));
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            return this;
        }

    }
}
