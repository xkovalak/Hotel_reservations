package cz.muni.fi.pv168.podzim2020.group05.team1.models;

import java.text.SimpleDateFormat;

public abstract class BaseModel {

    private static final SimpleDateFormat SDT = new SimpleDateFormat("dd:MM:yyy");

    protected long id;
}
