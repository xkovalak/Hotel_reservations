package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.FilterLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TableType;
import cz.muni.fi.pv168.podzim2020.group05.team1.filters.PeopleFilter;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomItemListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.listeners.CustomKeyListener;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.AccommodateAction;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyListener;

public class PeopleFilterPanel extends BaseFilterPanel {

    private static I18N I18n = new I18N(PeopleFilterPanel.class);

    private JTextField textFirstName;
    private JTextField textSurname;
    private JTextField textPhoneNumber;
    private JTextField textRoom;
    private JCheckBox checkBoxAccommodated;
    private JButton buttonAccommodate;

    public PeopleFilterPanel() {
        super();
    }

    @Override
    protected void setupLogic() {
        KeyListener listener = new CustomKeyListener(TableType.PEOPLE_TABLE);

        textFirstName.addKeyListener(listener);
        textSurname.addKeyListener(listener);
        textPhoneNumber.addKeyListener(listener);
        textRoom.addKeyListener(listener);
        checkBoxAccommodated.addItemListener(new CustomItemListener(TableType.PEOPLE_TABLE));
        buttonAccommodate.addActionListener(e -> buttonAccommodate.setEnabled(false));

        FilterLayer.getInstance().setPeopleFilter(
                new PeopleFilter(textFirstName, textSurname, textPhoneNumber, textRoom, checkBoxAccommodated));
    }

    @Override
    protected void buildFilterUI() {
        GridBagConstraints constraints = new GridBagConstraints();

        this.setLayout(new GridBagLayout());

        constraints.insets = new Insets(5, 2, 15, 2);
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = 2;
        constraints.gridheight = 1;

        //First name label
        constraints.gridx = 0;
        constraints.gridy = 0;
        this.add(new JLabel(I18n.getString("firstName") + ":"), constraints);

        //First name text field
        constraints.gridx = 2;
        constraints.gridy = 0;
        textFirstName = new JTextField();
        textFirstName.setPreferredSize(new Dimension(100,20));
        this.add(textFirstName, constraints);

        //Last name label
        constraints.gridx = 0;
        constraints.gridy = 1;
        this.add(new JLabel(I18n.getString("surname") + ":"), constraints);

        //Last name text field
        constraints.gridx = 2;
        constraints.gridy = 1;
        textSurname = new JTextField();
        this.add(textSurname, constraints);

        //Phone number label
        constraints.gridx = 0;
        constraints.gridy = 2;
        this.add(new JLabel(I18n.getString("phone") + ":"), constraints);

        //Phone number field
        constraints.gridx = 2;
        constraints.gridy = 2;
        textPhoneNumber = new JTextField();
        this.add(textPhoneNumber, constraints);

        //Room label
        constraints.gridx = 0;
        constraints.gridy = 3;
        this.add(new JLabel(I18n.getString("room") + ":"), constraints);

        //Room field
        constraints.gridx = 2;
        constraints.gridy = 3;
        textRoom = new JTextField();
        this.add(textRoom, constraints);

        //Accommodated checkbox
        constraints.gridx = 0;
        constraints.gridy = 4;
        constraints.gridwidth = 4;
        checkBoxAccommodated = new JCheckBox(I18n.getString("accommodated") + ":");
        checkBoxAccommodated.setBackground(this.getBackground());
        checkBoxAccommodated.setHorizontalTextPosition(SwingConstants.LEFT);
        checkBoxAccommodated.setIconTextGap(10);
        this.add(checkBoxAccommodated, constraints);

        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridwidth = 4;
        buttonAccommodate = new JButton("Accommodate");
        this.add(buttonAccommodate, constraints);
        buttonAccommodate.setAction(new AccommodateAction());
        buttonAccommodate.setEnabled(false);
    }

    public JButton getButtonAccommodate() {
        return buttonAccommodate;
    }
}
