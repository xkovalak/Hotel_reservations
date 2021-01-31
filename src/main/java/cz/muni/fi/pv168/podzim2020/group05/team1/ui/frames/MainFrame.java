package cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.actions.AddReservationAction;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.cards.MainCard;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.menu.MenuBar;
import cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels.*;
import cz.muni.fi.pv168.podzim2020.group05.team1.utilities.Icons;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;

public class MainFrame extends JFrame {

    private static final I18N I18n = new I18N(MainFrame.class);

    private static InfoPanel infoPanel = new InfoPanel();

    private MainCard mainCard = new MainCard();
    private MenuBar menuBar = new MenuBar();
    private JTabbedPane tabbedPane = new JTabbedPane();

    public MainFrame() {
        this.setTitle(I18n.getString("title"));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(Color.DARK_GRAY);
        this.setSize(1280, 720);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

        ImageIcon hotelIcon = (ImageIcon) Icons.HOTEL_ICON;
        this.setIconImage(hotelIcon.getImage());

        this.setJMenuBar(menuBar);
        this.add(infoPanel, BorderLayout.SOUTH);

        menuBarEditFunctionality();
        createTabs();
    }

    private void createTabs() {
        tabbedPane.add(I18n.getString("reservationCard"), mainCard.getReservations());
        tabbedPane.add(I18n.getString("roomCard"), mainCard.getRooms());
        tabbedPane.add(I18n.getString("peopleCard"), mainCard.getPeople());

        tabbedPane.setIconAt(0, getScaledImageIcon((ImageIcon)Icons.RESERVATION_ICON, 15,15));
        tabbedPane.setIconAt(1, getScaledImageIcon((ImageIcon)Icons.ROOMS_ICON, 15,15));
        tabbedPane.setIconAt(2, getScaledImageIcon((ImageIcon)Icons.PEOPLE_ICON, 15,15));

        this.add(tabbedPane);
    }

    public ImageIcon getScaledImageIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void menuBarEditFunctionality(){
        this.menuBar.getMenuEdit().getItemAddReservation().addActionListener(new AddReservationAction());
    }

    public MainCard getMainCard() {
        return mainCard;
    }

    public static InfoPanel getInfoPanel() {
        return infoPanel;
    }
}
