package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import javax.swing.JDialog;
import java.awt.*;

public abstract class BaseJDialog extends JDialog {

    public BaseJDialog(String windowDescription, Color color, int width, int height){
        SetBasicSettings(windowDescription, color, width, height);
    }

    private void SetBasicSettings(String windowDescription, Color color, int width, int height){
        this.setTitle(windowDescription);
        this.setBackground(color);
        this.setSize(new Dimension(width, height));
        this.setModalityType(ModalityType.TOOLKIT_MODAL);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
    }

    protected abstract void SetTextView();
}
