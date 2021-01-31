package cz.muni.fi.pv168.podzim2020.group05.team1.ui.components;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.util.Properties;

public class TextboxWithDatePicker {

    private static I18N I18n = new I18N(TextboxWithDatePicker.class);

    private JLabel label;
    private JDatePickerImpl datePicker;
    UtilDateModel model = new UtilDateModel();


    public TextboxWithDatePicker(JPanel panel, String text, GridBagConstraints constraints) {
        label = new JLabel(text);

        Properties p = new Properties();
        p.put("text.today", I18n.getString("today"));
        p.put("text.month", I18n.getString("month"));
        p.put("text.year", I18n.getString("year"));

        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);

        datePicker.getComponent(0).setPreferredSize(new Dimension(65, 20));
        datePicker.getComponent(0).setFocusable(false);

        datePicker.getComponent(1).setPreferredSize(new Dimension(15,20));

        constraints.gridx = 0;
        panel.add(label, constraints);

        constraints.gridx = 2;
        panel.add(datePicker, constraints);
    }

    public TextboxWithDatePicker(JDialog dialog, String text) {
        label = new JLabel(text);

        Properties p = new Properties();
        p.put("text.today", I18n.getString("today"));
        p.put("text.month", I18n.getString("month"));
        p.put("text.year", I18n.getString("year"));

        JDatePanelImpl datePanel = new JDatePanelImpl(model);
        datePicker = new JDatePickerImpl(datePanel);

        dialog.add(label);
        dialog.add(datePicker);
    }

    public String getDateString() {
        return datePicker.getJFormattedTextField().getText();
    }

    public JDatePickerImpl getDatePicker() {
        return datePicker;
    }
}
