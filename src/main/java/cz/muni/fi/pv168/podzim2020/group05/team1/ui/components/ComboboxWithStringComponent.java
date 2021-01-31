package cz.muni.fi.pv168.podzim2020.group05.team1.ui.components;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.BaseJDialog;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import java.util.List;

public class ComboboxWithStringComponent<T> {

    private JLabel Label;
    private JComboBox<T> ComboBox;

    public ComboboxWithStringComponent(BaseJDialog dialog, String descText, java.util.List<T> comboBoxInput){
        Label = new JLabel();
        Label.setText(descText);
        ComboBox = new JComboBox<>();
        addItemsToComboBox(comboBoxInput);

        dialog.add(Label);
        dialog.add(ComboBox);
    }

    public ComboboxWithStringComponent(BaseJDialog dialog, String descText, JComboBox<T> comboBoxInput){
        Label = new JLabel();
        Label.setText(descText);
        ComboBox = comboBoxInput;

        dialog.add(Label);
        dialog.add(ComboBox);
    }

    private void addItemsToComboBox(List<T> comboBoxInput){
        for (T item : comboBoxInput){
            ComboBox.addItem(item);
        }
    }

    public void setValue(T value) {
        ComboBox.setSelectedItem(value);
    }

    public JLabel GetLabel(){
        return Label;
    }

    public JComboBox<T> GetNumField(){
        return ComboBox;
    }

    public T getSelectedItem() {
        return (T) ComboBox.getSelectedItem();
    }
}
