package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Birra extends BrownCard{


    public Birra(String title) {
        super(title);
    }

    @Override
    public ArrayList<PlayingCard> useCard(Player byPlayer, List<Player> players) {
        int livesNow = byPlayer.getLives();
        byPlayer.setLives(livesNow+1);
        System.out.println("Hrac "+byPlayer.getName()+" si dal fajne pivo a doplnil si zivot, teraz ma "+byPlayer.getLives()+" zivotov.");
        return null;
    }
}
