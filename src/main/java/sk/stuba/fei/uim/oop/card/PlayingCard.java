package sk.stuba.fei.uim.oop.card;

import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public abstract class PlayingCard {
    private String title;


    public PlayingCard(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public abstract int useCard(Player player, List<Player> players);


    public abstract ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players);


}
