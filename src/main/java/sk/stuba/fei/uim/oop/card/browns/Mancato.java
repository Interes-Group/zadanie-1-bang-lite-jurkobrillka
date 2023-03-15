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
    public int useCard(Player player, List<Player> players) {
        return 0;
    }


    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        System.out.println("Karta vedle sa v tvojom tahu zahrat neda :) vraciame ti ju, najdes ju na konci balicka svojich kariet. (nz...)");
        byPlayer.getHandCards().add(new Mancato("Vedle"));
        return null;
    }
}
