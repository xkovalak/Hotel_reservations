package cz.muni.fi.pv168.podzim2020.group05.team1.filters;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithDatePicker;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.RowFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ReservationsFilter extends RowFilter implements ActionListener {

    private static I18N I18n = new I18N(ReservationsFilter.class);

    private JTextField field;
    private TextboxWithDatePicker dateFrom;
    private TextboxWithDatePicker dateTo;
    private JToggleButton filterByDateButton;
    private List<RowFilter<Object, Object>> filters;
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    public ReservationsFilter(JTextField field, TextboxWithDatePicker dateFrom,
                              TextboxWithDatePicker dateTo, JToggleButton filterByDateButton) {
        this.field = field;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.filterByDateButton = filterByDateButton;
        filters = new ArrayList<>(2);
    }

    @Override
    public boolean include(Entry entry) {
        if (!filterByDateButton.isSelected()) {
            return RowFilter.regexFilter(field.getText()).include(entry);
        } else {
            return RowFilter.andFilter(filters).include(entry);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String sDate1 = dateFrom.getDateString();
        String sDate2 = dateTo.getDateString();

        if (sDate1.isEmpty() && sDate2.isEmpty()) { // jumps to regex mode
            filterByDateButton.setSelected(false);
            ServiceLayer.getInstance().refreshTableRowSorter(TableType.RESERVATIONS_TABLE);
            return;
        }

        if (sDate1.isEmpty()) {
            sDate1 = new SimpleDateFormat(DATE_FORMAT).format(new Date(System.currentTimeMillis()));
        } else if (sDate2.isEmpty()) {
            sDate2 = "12.12.2050";
        }

        try {

            var date1 = new SimpleDateFormat(DATE_FORMAT).parse(sDate1);
            var date2 = new SimpleDateFormat(DATE_FORMAT).parse(sDate2);

            if (!date2.after(date1)) { // jumps to regex mode
                filterByDateButton.setSelected(false);
                dateTo.getDatePicker().getJFormattedTextField().setValue(null);
                warningMessage(I18n.getString("InvalidOrderOfDates"));
            } else {
                filters = new ArrayList<>(2);
                filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.AFTER, date1, 1));
                filters.add(RowFilter.dateFilter(RowFilter.ComparisonType.BEFORE, date2, 2));
            }
            ServiceLayer.getInstance().refreshTableRowSorter(TableType.RESERVATIONS_TABLE);

        } catch (ParseException ex) {
            System.err.println("Parsing error filter reservation: " + ex);
            warningMessage(I18n.getString("parsingError"));
        }
    }

    private void warningMessage(String msg) {
        JOptionPane.showMessageDialog(null, msg, I18n.getString("warningTitle"), JOptionPane.WARNING_MESSAGE);
    }
}
