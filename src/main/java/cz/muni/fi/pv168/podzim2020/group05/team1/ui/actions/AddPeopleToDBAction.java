package cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions;

import cz.muni.fi.pv168.podzim2020.group05.team1.models.PersonModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;
import cz.muni.fi.pv168.podzim2020.group05.team1.data.ServiceLayer;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class AddPeopleToDBAction extends BaseAction {

    private ArrayList<PersonModel> people;

    public AddPeopleToDBAction(ArrayList people) {
        super("Add people to DB", Icons.ADD_ICON);

        this.people = people;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (PersonModel person : this.people){
            ServiceLayer.getInstance().createNewPerson(new PersonModel(person.getName(), person.getSurname(), person.getEmail(),
                    person.getPhoneNumber()));
        }
    }
}
