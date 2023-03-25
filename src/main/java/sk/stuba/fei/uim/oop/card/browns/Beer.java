package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Beer extends BrownCard{


    public Beer(String title) {
        super(title);
    }

    @Override
    public int useCard(Player player, List<Player> players) {
        return 0;
    }

    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        int livesNow = byPlayer.getLives();
        byPlayer.setLives(livesNow+1);
        System.out.println("Hrac "+byPlayer.getName()+" si dal fajne pivo a doplnil si zivot, teraz ma "+byPlayer.getLives()+" zivotov.");
        return null;
    }
}