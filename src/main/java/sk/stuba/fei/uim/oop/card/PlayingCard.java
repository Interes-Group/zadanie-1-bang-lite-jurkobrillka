package sk.stuba.fei.uim.oop.card;

import sk.stuba.fei.uim.oop.Player;

import java.util.ArrayList;
import java.util.Random;

public abstract class PlayingCard {
    String title;


    public PlayingCard(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
