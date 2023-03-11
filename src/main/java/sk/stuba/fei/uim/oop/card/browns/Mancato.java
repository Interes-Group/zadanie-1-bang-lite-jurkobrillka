package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Mancato extends BrownCard{

    public Mancato(String title) {
        super(title);
    }

    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        System.out.println("Karta vedle sa v tvojom tahu zahrat neda :) ");
        return null;
    }
}
