package cz.muni.fi.pv168.podzim2020.group05.team1.ui.menu;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;

import javax.swing.JMenu;
import java.awt.event.KeyEvent;

public class MenuEdit extends JMenu {

    private static I18N I18n = new I18N(MenuEdit.class);

    private ItemAddReservation itemAddReservation = new ItemAddReservation();

    public MenuEdit() {
        this.setText(I18n.getString("edit"));
        this.setMnemonic(KeyEvent.VK_E); // Alt + e for edit

        this.add(itemAddReservation);
    }

    public ItemAddReservation getItemAddReservation() {
        return itemAddReservation;
    }
}
