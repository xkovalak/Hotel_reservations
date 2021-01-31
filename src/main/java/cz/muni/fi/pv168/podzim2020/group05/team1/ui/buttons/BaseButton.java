package cz.muni.fi.pv168.podzim2020.group05.team1.ui.buttons;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Image;

public abstract class BaseButton extends JButton {

    public BaseButton(String mainText, ImageIcon icon){
        Image newImg = icon.getImage().getScaledInstance(40, 40,  java.awt.Image.SCALE_SMOOTH);
        setLook(mainText, new ImageIcon(newImg));
    }

    private void setLook(String mainText, ImageIcon icon){
        this.setText(mainText);
        this.setIcon(icon);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(true);
        this.setSelected(true);
        this.setFocusable(false);
    }
}
