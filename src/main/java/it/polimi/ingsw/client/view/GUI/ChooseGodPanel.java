package it.polimi.ingsw.client.view.GUI;

import it.polimi.ingsw.model.god.GodDescription;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChooseGodPanel extends ActivePanel {

    private final Map<GodDescription, ImageIcon> imageMap = new HashMap<>();

    public ChooseGodPanel(GUI gui, List<GodDescription> godsToChoose) {
        super();
        DefaultListModel<GodDescription> listModel = new DefaultListModel<>();
        for (GodDescription godDescription : godsToChoose) {
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
        list.setSelectionModel(new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                if (super.isSelectedIndex(index0)) {
                    super.removeSelectionInterval(index0, index1);
                } else {
                    super.addSelectionInterval(index0, index1);
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(list);
        JButton confirmButton = new JButton("confirm");
        confirmButton.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        confirmButton.setBackground(Color.RED);
        confirmButton.addActionListener(e -> {
            StringBuilder selectedGods = new StringBuilder();
            for (GodDescription selectedGodDescription : list.getSelectedValuesList()) {
                selectedGods.append(selectedGodDescription.getName()).append(", ");
            }
            selectedGods.delete(selectedGods.length() - 2, selectedGods.length());  //remove ", " at the end
            gui.notify(new String(selectedGods));
        });

        this.setLayout(new BorderLayout());
        this.add(textLabel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(confirmButton, BorderLayout.SOUTH);

    }

    @Override
    protected void showMessage(String message) {
        textLabel.setText(message.split(">")[0]);
        textLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
    }

    private class GodChoiceRenderer extends JLabel implements ListCellRenderer<GodDescription> {
        public GodChoiceRenderer() {
            setOpaque(true);
            setBorder(BorderFactory.createLineBorder(Color.BLACK));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends GodDescription> list, GodDescription godDescription, int index, boolean isSelected, boolean cellHasFocus) {
            setText(godDescription.getName() + ": " + godDescription.getDescriptionOfPower());
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
