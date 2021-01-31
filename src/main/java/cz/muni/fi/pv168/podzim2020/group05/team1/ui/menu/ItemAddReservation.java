package cz.muni.fi.pv168.podzim2020.group05.team1.ui.menu;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import java.awt.Image;
import java.awt.event.KeyEvent;

public class ItemAddReservation extends JMenuItem {

    private static I18N I18n = new I18N(ItemAddReservation.class);

    public ItemAddReservation() {
        this.setText(I18n.getString("addReservation"));
        this.setMnemonic(KeyEvent.VK_A); // A for add

        ImageIcon icon = (ImageIcon) Icons.ADD_ICON;
        Image newImg = icon.getImage().getScaledInstance(10, 10,  java.awt.Image.SCALE_SMOOTH);
        this.setIcon(new ImageIcon(newImg));
    }
}
