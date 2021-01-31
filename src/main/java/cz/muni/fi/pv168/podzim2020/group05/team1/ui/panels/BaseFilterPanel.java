package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.ColorPicker;

import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * Class representing left-side area, where filtering feature is provided.
 * Inheriting classes are required to implement buildFilterUI() and setupLogic() since they are called
 * here in this class.
 */
public abstract class BaseFilterPanel extends JPanel {

    public BaseFilterPanel() {
        this.setPreferredSize(new Dimension(200, 600));
        this.setBackground(ColorPicker.MAIN_COLOR);
        buildFilterUI();
        setupLogic();
    }

    protected abstract void buildFilterUI();

    protected abstract void setupLogic();
}
