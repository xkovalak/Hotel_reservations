package cz.muni.fi.pv168.podzim2020.group05.team1.filters;

import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;

import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.RowFilter;

public class RoomsFilter extends RowFilter {

    private JTextField roomNumberField;
    private JComboBox<TypeOfRoom> typeOfRoomComboBox;
    private JComboBox<String> bedsComboBox;
    private JTextField priceFromField;
    private JTextField priceToField;
    private JRadioButton freeRadioButton;
    private JRadioButton occupiedRadioButton;

    public RoomsFilter(JTextField roomNumberField, JComboBox<TypeOfRoom> typeOfRoomComboBox,
                       JComboBox<String> bedsComboBox, JTextField priceFromField, JTextField priceToField,
                       JRadioButton freeRadioButton, JRadioButton occupiedRadioButton) {
        this.roomNumberField = roomNumberField;
        this.typeOfRoomComboBox = typeOfRoomComboBox;
        this.bedsComboBox = bedsComboBox;
        this.priceFromField = priceFromField;
        this.priceToField = priceToField;
        this.freeRadioButton = freeRadioButton;
        this.occupiedRadioButton = occupiedRadioButton;
    }

    @Override
    public boolean include(Entry entry) {
        String number = entry.getStringValue(0);
        String typeOfRoom= entry.getStringValue(1);
        String beds = entry.getStringValue(2);
        String price = entry.getStringValue(3);
        String free = entry.getStringValue(4);
        return number.contains(roomNumberField.getText()) &&
                (typeOfRoomComboBox.getSelectedItem().toString().equals("") ||
                        typeOfRoom.equals(typeOfRoomComboBox.getSelectedItem().toString())) &&
                (bedsComboBox.getSelectedItem().toString().equals("") || beds.equals(bedsComboBox.getSelectedItem())) &&
                isPriceBetween(price, priceFromField.getText(), priceToField.getText()) &&
                ((!freeRadioButton.isSelected() && !occupiedRadioButton.isSelected()) || (freeRadioButton.isSelected() &&
                        free.equals("true")) || (occupiedRadioButton.isSelected() && free.equals("false")));
    }

    private boolean isPriceBetween(String price, String priceFrom, String priceTo) {
        if (priceFrom.equals("") && priceTo.equals("")) {
            return true;
        }

        double doublePriceFrom;
        try {
            doublePriceFrom = Double.parseDouble(priceFrom);
        } catch (NumberFormatException ex) {
            doublePriceFrom = Double.MIN_VALUE;
        }

        double doublePriceTo;
        try {
            doublePriceTo = Double.parseDouble(priceTo);
        } catch (NumberFormatException ex) {
            doublePriceTo = Double.MAX_VALUE;
        }

        double doublePrice = 0;
        try {
            doublePrice = Double.parseDouble(price);
        } catch (NumberFormatException ex) {
            // Otherwise 0 lefts
        }

        return doublePrice >= doublePriceFrom && doublePrice <= doublePriceTo;
    }
}
