package cz.muni.fi.pv168.podzim2020.group05.team1.ui.panels;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;

import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class InfoPanel extends BasePanel {

    private static I18N I18n = new I18N(InfoPanel.class);

    private final DateFormat dateFormat = new SimpleDateFormat("E HH:mm:ss   dd.MM.yyyy");

    private JLabel timeCurrent = new JLabel();
    private JLabel roomsCurrent = new JLabel();
    private JLabel peopleCurrent = new JLabel();

    private String time;

    public InfoPanel() {
        super(new Color(223, 245, 242), new Dimension(100, 30), new GridLayout(1,3));

        addLabels();
        setFontLabels();

        startTime();
    }

    private void startTime() {
        Thread t1 = new Thread(() -> {
            while(true) {
                try {
                    Thread.sleep(1000);
                    Calendar cal = Calendar.getInstance();
                    time = dateFormat.format(cal.getTime());
                    timeCurrent.setText(time);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t1.start();
    }

    public void updateRoomCapacity(int occupiedRooms, int allRooms) {
        roomsCurrent.setText(I18n.getString("roomsOccupied") + ": " + occupiedRooms + "/" + allRooms);
    }

    public void updateCurrentPeople(int i) {
        peopleCurrent.setText(I18n.getString("currentCustomers") + ": " + i);
    }

    private void setFontLabels() {
        timeCurrent.setFont(new Font("Serif", Font.BOLD, 18));
        peopleCurrent.setFont(new Font("Serif", Font.BOLD, 18));
        roomsCurrent.setFont(new Font("Serif", Font.BOLD, 18));
    }

    private void addLabels() {
        peopleCurrent.setHorizontalAlignment(JLabel.CENTER);
        this.add(peopleCurrent, BorderLayout.CENTER);

        roomsCurrent.setHorizontalAlignment(JLabel.CENTER);
        this.add(roomsCurrent, BorderLayout.CENTER);

        timeCurrent.setHorizontalAlignment(JLabel.CENTER);
        this.add(timeCurrent, BorderLayout.CENTER);
    }
}
