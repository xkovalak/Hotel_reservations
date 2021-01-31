package cz.muni.fi.pv168.podzim2020.group05.team1.ui.components;

import javax.swing.JButton;
import javax.swing.JDialog;
import java.awt.*;

public class ButtonWithButtonComponent {
    private JButton first;
    private JButton second;

    public ButtonWithButtonComponent(JDialog dialog, String first, String second){
        setButtons(first, second);

        dialog.add(this.first);
        dialog.add(this.second);
    }

    public ButtonWithButtonComponent(JDialog dialog, String txt) {
        this.first = new JButton(txt);
        dialog.add(first);
    }

    public ButtonWithButtonComponent(JDialog dialog, String first, String second, GridBagConstraints gbc){
        setButtons(first, second);

        gbc.gridy += 1;
        gbc.gridx = 0;
        dialog.add(this.first, gbc);

        gbc.gridx = 1;
        dialog.add(this.second, gbc);
    }

    private void setButtons(String first, String second){
        this.first = new JButton(first);
        this.second = new JButton(second);

        this.first.setPreferredSize(new Dimension(100, 50));
        this.second.setPreferredSize(new Dimension(100, 50));
    }

    public JButton getFirstButton(){
        return first;
    }

    public JButton getSecondButton(){
        return second;
    }
}
