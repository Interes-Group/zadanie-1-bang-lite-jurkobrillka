package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Diligence extends BrownCard{

    private static final String TITLE = "Dostavnik";

    public Diligence() {
        super(TITLE);
    }

    @Override
    public int useCard(Player player, List<Player> players) {
        return 0;
    }

    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {

        System.out.print("Hrac "+byPlayer.getName()+" si taha 2 karty: ");
        for (int i = 0; i < 2; i++) {

            PlayingCard pc = deck.pop();
            byPlayer.getHandCards().add(pc);
            System.out.print(pc.getTitle()+", ");
        }
        System.out.println();
        return null;
    }
}
