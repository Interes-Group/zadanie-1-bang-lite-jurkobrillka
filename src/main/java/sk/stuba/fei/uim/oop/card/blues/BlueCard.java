package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.*;

public abstract class BlueCard extends PlayingCard {
    public BlueCard(String title) {
        super(title);
    }

    public void puttingCardOnTable(BlueCard bc, Player toPlayer){
            System.out.println("Vkladas pred hraca "+ toPlayer.getName()+ " kartu:  "+bc.getTitle());
            toPlayer.getTableCards().add(bc);
            toPlayer.getTableCards().sort(Comparator.comparing(PlayingCard::getTitle));
    }


}
