package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;

public class Mancato extends BrownCard{

    public Mancato(String title) {
        super(title);
    }

    @Override
    public ArrayList<PlayingCard> useCard(Player byPlayer, List<Player> players) {
        return null;
    }
}
