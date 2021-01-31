package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.BaseTable;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import java.awt.event.ActionEvent;

public abstract class BaseAction extends AbstractAction {
    protected final BaseTable table;

    public BaseAction(String text){
        super(text);
        this.table = null;
    }

    public BaseAction(String text, Icon icon) {
        super(text, icon);
        this.table = null;
    }

    public BaseAction(String text, Icon icon, BaseTable table) {
        super(text, icon);
        this.table = table;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);
}
