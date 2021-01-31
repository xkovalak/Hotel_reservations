package cz.muni.fi.pv168.podzim2020.group05.team1.ui.menu;

import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar {

    MenuEdit menuEdit = new MenuEdit();
    MenuSettings menuSettings = new MenuSettings();

    public MenuBar() {
        this.add(menuEdit);
        this.add(menuSettings);
    }

    public MenuEdit getMenuEdit() {
        return menuEdit;
    }

    public MenuSettings getMenuSettings() {
        return menuSettings;
    }
}
