package ru.cft.miner.view;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public enum GameImage {
    CLOSED("cell.png"),
    MARKED("flag.png"),
    EMPTY("empty.png"),
    NUM_1("1.png"),
    NUM_2("2.png"),
    NUM_3("3.png"),
    NUM_4("4.png"),
    NUM_5("5.png"),
    NUM_6("6.png"),
    NUM_7("7.png"),
    NUM_8("8.png"),
    BOMB("mine.png"),
    TIMER("timer.png"),
    BOMB_ICON("mineImage.png"),
    ;

    private final String fileName;
    private ImageIcon imageIcon;
    private static final Map<Integer, GameImage> numberImageMap = new HashMap<>();

    static {
        numberImageMap.put(0, EMPTY);
        numberImageMap.put(1, NUM_1);
        numberImageMap.put(2, NUM_2);
        numberImageMap.put(3, NUM_3);
        numberImageMap.put(4, NUM_4);
        numberImageMap.put(5, NUM_5);
        numberImageMap.put(6, NUM_6);
        numberImageMap.put(7, NUM_7);
        numberImageMap.put(8, NUM_8);
    }

    GameImage(String fileName) {
        this.fileName = fileName;
    }

    public static GameImage getNumberImage(int number) {
        return numberImageMap.get(number);
    }

    public synchronized ImageIcon getImageIcon() {
        if (imageIcon == null) {
            imageIcon = new ImageIcon(ClassLoader.getSystemResource(fileName));
        }

        return imageIcon;
    }
}
