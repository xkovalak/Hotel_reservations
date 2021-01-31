package cz.muni.fi.pv168.podzim2020.group05.team1;

import cz.muni.fi.pv168.podzim2020.group05.team1.ui.frames.MainFrame;

import java.awt.Dimension;
import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(Main::showUI);
    }

    public static void showUI() {
        MainFrame mainFrame = new MainFrame();
        mainFrame.setMinimumSize(new Dimension(600, 400));
    }
}
