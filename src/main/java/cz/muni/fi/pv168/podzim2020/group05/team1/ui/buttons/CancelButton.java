package cz.muni.fi.pv168.podzim2020.group05.team1.ui.buttons;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.ImageIcon;

public class CancelButton extends BaseFilterButton {

    private static I18N I18n = new I18N(CancelButton.class);

    public CancelButton() {
        super(I18n.getString("cancel"), (ImageIcon) Icons.DELETE_ICON, I18n.getString("cancelReservation"));
    }
}
