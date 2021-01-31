package cz.muni.fi.pv168.podzim2020.group05.team1.ui.components;

import javax.swing.JDialog;
import javax.swing.JLabel;
import java.awt.*;

public class StringWithStringComponent {

    private JLabel fLabel;
    private JLabel sLabel;

    public StringWithStringComponent(JDialog dialog, String firstLabel, String secondLabel) {
        setLabels(firstLabel, secondLabel);

        dialog.add(fLabel);
        dialog.add(sLabel);
    }

    public StringWithStringComponent(JDialog dialog, String firstLabel, String secondLabel, GridBagConstraints gbc){
        setLabels(firstLabel, secondLabel);

        gbc.gridy += 1;
        gbc.gridx = 0;
        dialog.add(fLabel, gbc);

        gbc.gridx = 1;
        dialog.add(sLabel, gbc);
    }

    private void setLabels(String firstLabel, String secondLabel){
        var fLabel = new JLabel();
        var sLabel = new JLabel();

        this.fLabel = fLabel;
        this.sLabel = sLabel;

        fLabel.setText(firstLabel);
        sLabel.setText(secondLabel);
    }

    public JLabel getfLabel() {
        return fLabel;
    }

    public JLabel getsLabel() {
        return sLabel;
    }
}
