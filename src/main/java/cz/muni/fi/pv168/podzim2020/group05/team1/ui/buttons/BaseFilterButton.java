package cz.muni.fi.pv168.podzim2020.group05.team1.ui.buttons;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;

public abstract class BaseFilterButton extends BaseButton {

    public BaseFilterButton(String mainText, ImageIcon icon, String toolTip) {
        super(mainText, icon);
        setFilterLook(toolTip);
    }

    private void setFilterLook(String toolTip) {
        this.setToolTipText("make new reservation");
        this.setBorderPainted(false);
        this.setHorizontalTextPosition(JButton.CENTER);
        this.setVerticalTextPosition(JButton.BOTTOM);
        this.setFont(new Font("Comic Sans", Font.BOLD, 15));
        this.setToolTipText(toolTip);
    }
}
