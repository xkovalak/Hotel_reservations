package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.FilterLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;
import cz.muni.fi.pv168.podzim2020.group05.team1.filters.RoomsFilter;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomItemListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomKeyListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.CheckOutAction;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RoomsFilterPanel extends BaseFilterPanel {

    private static I18N I18n = new I18N(RoomsFilterPanel.class);

    private JTextField roomNumberTextField;
    private JComboBox<TypeOfRoom> typeOfRoomComboBox;
    private JComboBox<String> bedsComboBox;
    private JTextField priceFromTextField;
    private JTextField priceToTextField;
    private JRadioButton freeRadioButton;
    private JRadioButton occupiedRadioButton;
    private boolean isSelectedFreeRB = false;
    private boolean isSelectedOccRB = false;
    private JButton checkOut;

    public RoomsFilterPanel() {
        super();
    }

    @Override
    protected void setupLogic() {
        CustomKeyListener keyListener = new CustomKeyListener(TableType.ROOMS_TABLE);
        CustomItemListener itemListener = new CustomItemListener(TableType.ROOMS_TABLE);

        roomNumberTextField.addKeyListener(keyListener);
        typeOfRoomComboBox.addItemListener(itemListener);
        bedsComboBox.addItemListener(itemListener);
        priceFromTextField.addKeyListener(keyListener);
        priceToTextField.addKeyListener(keyListener);
        freeRadioButton.addItemListener(itemListener);
        occupiedRadioButton.addItemListener(itemListener);

        FilterLayer.getInstance().setRoomsFilter(new RoomsFilter(roomNumberTextField, typeOfRoomComboBox,
                bedsComboBox, priceFromTextField, priceToTextField, freeRadioButton, occupiedRadioButton));

        setupRadioButtonsLogic();
    }

    @Override
    protected void buildFilterUI() {
        GridBagConstraints constraints = new GridBagConstraints();

        this.setLayout(new GridBagLayout());

        constraints.insets = new Insets(5, 5, 15, 5);

        //ROOM label
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 4;
        this.add(new JLabel(I18n.getString("room") + ":"), constraints);

        //ROOM text field
        constraints.gridx = 4;
        constraints.gridy = 0;
        constraints.gridwidth = 5;
        roomNumberTextField = new JTextField();
        this.add(roomNumberTextField, constraints);

        //TYPE OF ROOM label
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        this.add(new JLabel(I18n.getString("typeOfRoom") + ":"), constraints);

        //TYPE OF ROOM combo box
        constraints.gridx = 4;
        constraints.gridy = 1;
        constraints.gridwidth = 5;
        typeOfRoomComboBox = new JComboBox<>(TypeOfRoom.values());
        this.add(typeOfRoomComboBox, constraints);

        //BEDS label
        constraints.gridx = 0;
        constraints.gridy = 2;
        constraints.gridwidth = 4;
        this.add(new JLabel(I18n.getString("beds") + ":"), constraints);

        //BEDS combo box
        constraints.gridx = 4;
        constraints.gridy = 2;
        constraints.gridwidth = 5;
        bedsComboBox = new JComboBox<>(new String[] {"", "1", "2", "3", "4", "5", "6", "7", "8"});
        this.add(bedsComboBox, constraints);

        //PRICE label
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 4;
        constraints.gridheight = 2;
        constraints.fill = GridBagConstraints.BOTH;
        this.add(new JLabel(I18n.getString("price") + ":"), constraints);

        //FROM label
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        this.add(new JLabel(I18n.getString("from") + ":"), constraints);

        //FROM text field
        constraints.gridx = 2;
        constraints.gridy = 5;
        constraints.gridwidth = 3;
        priceFromTextField = new JTextField();
        priceFromTextField.setMargin(new Insets(0, 0, 0, 10));
        this.add(priceFromTextField, constraints);

        //TO label
        constraints.gridx = 5;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        this.add(new JLabel(I18n.getString("to") + ":"), constraints);

        //TO text field
        constraints.gridx = 7;
        constraints.gridy = 5;
        constraints.gridwidth = 2;
        priceToTextField = new JTextField();
        this.add(priceToTextField, constraints);

        //FREE radioButton
        constraints.gridx = 0;
        constraints.gridy = 7;
        constraints.gridwidth = 4;
        freeRadioButton = new JRadioButton(I18n.getString("free"));
        freeRadioButton.setBackground(this.getBackground());
        this.add(freeRadioButton, constraints);

        //OCCUPIED radioButton
        constraints.gridx = 4;
        occupiedRadioButton = new JRadioButton(I18n.getString("occupied"));
        occupiedRadioButton.setBackground(this.getBackground());
        this.add(occupiedRadioButton, constraints);

        constraints.gridx = 0;
        constraints.gridwidth = 9;
        constraints.gridy = 9;
        checkOut = new JButton(I18n.getString("checkOut"));
        checkOut.setAction(new CheckOutAction());
        this.add(checkOut, constraints);
    }

    private void setupRadioButtonsLogic() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(freeRadioButton);
        buttonGroup.add(occupiedRadioButton);

        freeRadioButton.addActionListener(x -> {
            if (!isSelectedOccRB && !isSelectedFreeRB) {
                isSelectedFreeRB = true;
            } else if (isSelectedFreeRB) {
                isSelectedFreeRB = false;
                buttonGroup.clearSelection();
            } else {
                isSelectedFreeRB = true;
                isSelectedOccRB = false;
            }
        });
        occupiedRadioButton.addActionListener(x -> {
            if (!isSelectedOccRB && !isSelectedFreeRB) {
                isSelectedOccRB = true;
            } else if (isSelectedOccRB) {
                isSelectedOccRB = false;
                buttonGroup.clearSelection();
            } else {
                isSelectedOccRB = true;
                isSelectedFreeRB = false;
            }
        });
    }
}
