package cz.muni.fi.pv168.podzim2020.group05.team1.filters;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.RowFilter;

public class PeopleFilter extends RowFilter {

    private final JTextField nameField;
    private final JTextField surnameField;
    private final JTextField phoneNumberField;
    private final JTextField roomField;
    private final JCheckBox checkBoxAccommodated;

    public PeopleFilter(JTextField nameField, JTextField surnameField,
                        JTextField phoneNumberField, JTextField roomField, JCheckBox checkBoxAccommodated) {
        this.nameField = nameField;
        this.surnameField = surnameField;
        this.phoneNumberField = phoneNumberField;
        this.roomField = roomField;
        this.checkBoxAccommodated = checkBoxAccommodated;
    }

    @Override
    public boolean include(Entry entry) {
        String firstName = entry.getStringValue(0).toLowerCase();
        String lastName = entry.getStringValue(1).toLowerCase();
        String phone = entry.getStringValue(3).toLowerCase();
        String room = entry.getStringValue(4).toLowerCase();
        boolean result = firstName.contains(nameField.getText().toLowerCase()) &&
                lastName.contains(surnameField.getText().toLowerCase()) &&
                phone.contains(phoneNumberField.getText().toLowerCase()) &&
                room.contains(roomField.getText().toLowerCase());
        if (checkBoxAccommodated.isSelected()) {
            return result && !room.contains("-");
        }
        return result;
    }
}
