package cz.muni.fi.pv168.podzim2020.group05.team1.utilities;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.net.URL;

public class Icons {
    public static final Icon ADD_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/add-icon.png");
    public static final Icon DELETE_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/delete-icon.png");
    public static final Icon EDIT_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/edit-icon.png");
    public static final Icon HOTEL_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/hotel.png");
    public static final Icon PEOPLE_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/people-icon.png");
    public static final Icon RESERVATION_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/reservation-icon.png");
    public static final Icon ROOMS_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/rooms-icon.png");
    public static final Icon SEARCH_ICON = loadResource("cz/muni/fi/pv168/podzim2020/group05/team1/search-icon-small.png");


    private static ImageIcon loadResource(String name) {
        URL url = ClassLoader.getSystemResource(name);
        if (url == null) {
            throw new RuntimeException("Icon " + name + " not found!");
        }
        return new ImageIcon(url);
    }
}
