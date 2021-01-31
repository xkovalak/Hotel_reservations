package cz.muni.fi.pv168.podzim2020.group05.team1.ui.components;

import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Tuple;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import java.awt.Dimension;

public class TextboxWithStringComponent{
    private JLabel Label;
    private JTextField Field;

    public TextboxWithStringComponent(JDialog dialog, String descText, int textFieldColumns){
        Label = new JLabel();
        Label.setText(descText);
        Field = new JTextField(textFieldColumns);

        dialog.add(Label);
        dialog.add(Field);
    }

    public TextboxWithStringComponent(JDialog dialog, String descText, int textFieldColumns, String value){
        this(dialog, descText, textFieldColumns);
        Field.setText(value);
    }

    //TODO
    public TextboxWithStringComponent(JDialog dialog, String descText, Dimension size,
                                      Tuple<SpringLayout.Constraints> labelC, Tuple<SpringLayout.Constraints> textFieldC){
        Label = new JLabel();
        Label.setText(descText);
        Field = new JTextField();
        Field.setPreferredSize(size);

        dialog.add(Label, labelC);
        dialog.add(Field, textFieldC);
    }

    public String getField(){
        return Field.getText();
    }

    public void setField(String field) {
        Field.setText(field);
    }
}
