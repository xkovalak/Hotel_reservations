package cz.muni.fi.pv168.podzim2020.group05.team1.enums;

import cz.muni.fi.pv168.podzim2020.group05.team1.data.I18N;

import java.util.Arrays;

public enum TypeOfRoom {
    EMPTY("empty"),
    Taka_Fajna("takaFajna"),
    Taka_Nie_Fajna("takaNieFajna");

    private String text;

    private I18N I18n = new I18N(TypeOfRoom.class);

    TypeOfRoom(String text) {
        this.text = I18n.getString(text);
    }

    @Override
    public String toString() {
        return this.text;
    }

    public static TypeOfRoom[] valuesWithoutEmpty() {
        return Arrays.stream(TypeOfRoom.values()).filter(x -> !x.equals(EMPTY))
                .toArray(TypeOfRoom[]::new);
    }

    public static TypeOfRoom getEnum(String value) {
        for(TypeOfRoom v : values())
            if(v.text.equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}
