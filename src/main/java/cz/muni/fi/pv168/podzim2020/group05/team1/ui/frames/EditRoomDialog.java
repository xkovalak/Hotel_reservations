package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.enums.TypeOfRoom;
import cz.muni.fi.pv168.podzim2020.group05.team1.models.RoomModel;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.ConfirmEditRoomAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.ButtonWithButtonComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.ComboboxWithStringComponent;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.components.TextboxWithStringComponent;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

public class EditRoomDialog extends BaseJDialog {

    private static I18N I18n = new I18N(EditRoomDialog.class);

    private RoomModel roomModel;
    private JLabel number;
    private ComboboxWithStringComponent<TypeOfRoom> typeOfRoom;
    private ComboboxWithStringComponent<String> beds;
    private TextboxWithStringComponent price;
    private ButtonWithButtonComponent buttons;
    private JCheckBox freeCheckBox;

    public EditRoomDialog(RoomModel roomModel) {
        super(I18n.getString("title"), Color.DARK_GRAY, 300, 275);

        this.roomModel = roomModel;

        setLayout(new GridLayout(6, 2));
        setResizable(false);
        SetTextView();
        setRoomData();
    }

    @Override
    protected void SetTextView() {
        add(new JLabel(I18n.getString("number") + " : "));
        number = new JLabel("");
        add(number);
        typeOfRoom = new ComboboxWithStringComponent<TypeOfRoom>(this, I18n.getString("typeOfRoom") + " : ", List.of(TypeOfRoom.valuesWithoutEmpty()));
        beds = new ComboboxWithStringComponent<String>(this, I18n.getString("beds") + " : ", List.of("1", "2", "3", "4", "5", "6", "7", "8"));
        price = new TextboxWithStringComponent(this, I18n.getString("price") + " : ", 15);

        freeCheckBox = new JCheckBox(I18n.getString("free"));
        freeCheckBox.setBackground(number.getBackground());
        freeCheckBox.setHorizontalTextPosition(SwingConstants.LEFT);
        freeCheckBox.setIconTextGap(10);
        add(freeCheckBox);

        add(new JLabel());

        buttons = new ButtonWithButtonComponent(this, I18n.getString("ok"), I18n.getString("cancel"));
        buttons.getFirstButton().addActionListener(new ConfirmEditRoomAction(this));
        buttons.getSecondButton().addActionListener(e -> this.dispose());
    }

    private void setRoomData() {
        number.setText(String.valueOf(roomModel.getNumber()));
        typeOfRoom.setValue(roomModel.getTypeOfRoom());
        beds.setValue(String.valueOf(roomModel.getBeds()));
        price.setField(String.valueOf(roomModel.getPrice()));
        freeCheckBox.setSelected(roomModel.isFree());
    }

    public String getNumber() {
        return number.getText();
    }

    public ComboboxWithStringComponent<TypeOfRoom> getTypeOfRoom() {
        return typeOfRoom;
    }

    public ComboboxWithStringComponent<String> getBeds() {
        return beds;
    }

    public TextboxWithStringComponent getPrice() {
        return price;
    }

    public JCheckBox getFreeCheckBox() {
        return freeCheckBox;
    }
}
