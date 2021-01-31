package cz.muni.fi.pv168.podzim2020.group05.team1.utilities;

public class Tuple<T> {

    private T first;
    private T second;

    public Tuple(T first, T second){
        this.first = first;
        this.second = second;
    }

    public T getFirst(){
        return this.first;
    }

    public T getSecond(){
        return this.second;
    }
}
