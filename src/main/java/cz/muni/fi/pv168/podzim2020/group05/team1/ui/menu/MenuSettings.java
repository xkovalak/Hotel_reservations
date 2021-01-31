package cz.muni.fi.pv168.podzim2020.group05.team1.ui.menu;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;

import javax.swing.JMenu;
import java.awt.event.KeyEvent;

public class MenuSettings extends JMenu {

    private static I18N I18n = new I18N(MenuSettings.class);

    public MenuSettings() {
        this.setText(I18n.getString("settings"));
        this.setMnemonic(KeyEvent.VK_S); // Alt + s for settings
    }
}
