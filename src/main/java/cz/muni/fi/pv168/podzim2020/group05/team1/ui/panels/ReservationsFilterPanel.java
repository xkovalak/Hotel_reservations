package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.FilterLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.filters.ReservationsFilter;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomKeyListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.AddReservationAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.CancelReservationAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.buttons.AddButton;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.buttons.CancelButton;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithDatePicker;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;

public class ReservationsFilterPanel extends BaseFilterPanel {

    private static I18N I18n = new I18N(ReservationsFilterPanel.class);

    private AddButton buttonAdd;
    private CancelButton buttonCancel;
    private JToggleButton filterDateButton;

    private JTextField textSearch;

    private TextboxWithDatePicker dateFrom;
    private TextboxWithDatePicker dateTo;


    public ReservationsFilterPanel() {
        super();
    }

    @Override
    protected void setupLogic() {
        buttonAdd.addActionListener(new AddReservationAction());
        buttonCancel.addActionListener(new CancelReservationAction());

        KeyListener listener = new CustomKeyListener(TableType.RESERVATIONS_TABLE);
        textSearch.addKeyListener(listener);

        ReservationsFilter filter = new ReservationsFilter(textSearch, dateFrom, dateTo, filterDateButton);
        FilterLayer.getInstance().setReservationsFilter(filter);
        filterDateButton.addActionListener(filter);
    }

    @Override
    protected void buildFilterUI() {
        buttonAdd = new AddButton();
        buttonCancel = new CancelButton();

        GridBagConstraints constraints = new GridBagConstraints();

        this.setLayout(new GridBagLayout());

        constraints.insets = new Insets(3, 2, 15, 2);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        //ID label
        constraints.gridx = 0;
        constraints.gridy = 0;
        JLabel searchLabel = new JLabel(I18n.getString("search"));
        searchLabel.setIcon(Icons.SEARCH_ICON);
        searchLabel.setFont(new Font ("Helvetica Neue", Font.BOLD, 15));
        this.add(searchLabel, constraints);

        //ID text field
        constraints.gridx = 2;
        constraints.gridy = 0;
        textSearch = new JTextField();
        textSearch.setPreferredSize(new Dimension(100, 20));
        this.add(textSearch, constraints);

        //Date from
        constraints.gridy = 2;
        dateFrom = new TextboxWithDatePicker(this, I18n.getString("dateFrom") + ": ", constraints);

        //DateTo
        constraints.gridy = 3;
        dateTo = new TextboxWithDatePicker(this, I18n.getString("dateTo") + ": ", constraints);

        //Buttons
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridwidth = 3;
        constraints.fill = GridBagConstraints.BOTH;
        filterDateButton = new JToggleButton(I18n.getString("filterByDates"));
        this.add(filterDateButton, constraints);

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 8;
        this.add(buttonAdd, constraints);

        constraints.gridx = 2;
        this.add(buttonCancel, constraints);
    }
}
