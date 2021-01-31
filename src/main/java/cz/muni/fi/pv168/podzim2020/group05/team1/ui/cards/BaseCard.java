package cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels.BaseFilterPanel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import java.awt.Color;

public abstract class BaseCard extends JPanel {

    private BaseFilterPanel filter;

    public BaseCard() {
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        this.setLayout(new BorderLayout());
    }

    protected void addTable(BaseTable table){
        this.add(new JScrollPane(table), BorderLayout.CENTER);
    }

    protected void addFilter(BaseFilterPanel filterPanel){
        this.filter = filterPanel;

        this.add(filterPanel, BorderLayout.WEST);
    }

    public BaseFilterPanel getFilter(){
        return filter;
    }
}
