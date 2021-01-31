package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.LayoutManager;

public abstract class BasePanel extends JPanel {

    public BasePanel(Color color, Dimension size, LayoutManager layout){
        this.setBackground(color);
        this.setPreferredSize(size);
        this.setLayout(layout);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
    }
}
