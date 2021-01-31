package cz.muni.fi.pv168.podzim2020.group05.team1.listeners;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.tables.PeopleTable;

import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PeopleListSelectionListener implements ListSelectionListener {

    JButton accommodate;
    PeopleTable table;

    public PeopleListSelectionListener(JButton accommodate, PeopleTable table) {
        this.accommodate = accommodate;
        this.table = table;
    }

    @Override
    public void valueChanged(ListSelectionEvent event) {
        var selectionModel = (ListSelectionModel) event.getSource();
        var indices = selectionModel.getSelectedIndices();

        if (indices.length == 0) {
            accommodate.setEnabled(false);
            return;
        }

        for (int i : indices) {
            if (!table.getValueAt(i, 4).equals("X")) {
                accommodate.setEnabled(false);
                return;
            }
        }
        accommodate.setEnabled(true);
    }
}
