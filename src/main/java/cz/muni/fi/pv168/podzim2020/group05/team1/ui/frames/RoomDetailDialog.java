package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.viewmodels.PersonTableViewModel;

import javax.swing.JLabel;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class RoomDetailDialog extends BaseJDialog {

    private static I18N I18n = new I18N(RoomDetailDialog.class);

    private RoomModel room;

    public RoomDetailDialog(RoomModel room) {
        super(I18n.getString("title"), Color.DARK_GRAY, 300, 200);

        this.room = room;

        setLayout(new GridBagLayout());
        setResizable(false);
        SetTextView();
    }

    @Override
    protected void SetTextView() {
        var people = ((PersonTableViewModel) ServiceLayer.getInstance().getPeopleTable().getModel()).getContext().stream()
                .filter(person -> person.getRoom().equals(String.valueOf(room.getNumber())))
                .collect(Collectors.toCollection(ArrayList::new));

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 1;
        constraints.weighty = 1;
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel(I18n.getString("room") + " : "), constraints);

        constraints.gridx = 1;
        add(new JLabel(Integer.toString(room.getNumber())), constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel(I18n.getString("currentState") + " : "), constraints);

        constraints.gridx = 1;
        var state = room.isFree() ? I18n.getString("free") : I18n.getString("reserved");
        add(new JLabel(state), constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel(I18n.getString("typeOfRoom") + " : "), constraints);

        constraints.gridx = 1;
        add(new JLabel(room.getTypeOfRoom().toString()), constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel(I18n.getString("people") + " : "), constraints);

        constraints.fill = GridBagConstraints.EAST;
        constraints.gridx = 1;
        for (var person : people) {
            add(new JLabel(person.getName() + " " + person.getSurname()), constraints);
            constraints.gridy++;
        }
    }
}
