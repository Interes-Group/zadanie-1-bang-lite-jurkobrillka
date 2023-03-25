package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Barile extends BlueCard {

    public Barile(String title) {
        super(title);
    }

    @Override
    public int useCard(Player player, List<Player> players) {
        int boomChance = (int) Math.floor(Math.random() * (4 - 1 + 1) + 1);
        if (boomChance == 4) {
            return 1;
        } else {
            return 2;
        }
    }


    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {


        boolean canIPutOnTheTable = true;
        for (PlayingCard bc : byPlayer.getTableCards()) {
            if (bc instanceof Barile) {
                System.out.println("Nemozes dat pred seba barel, uz jeden mas...\nVraciame ti kartu do ruky :) (nz...)");
                byPlayer.getHandCards().add(new Barile("Barel"));
                canIPutOnTheTable = false;
                break;
            }
        }
        if (canIPutOnTheTable) {
            puttingCardOnTable(new Barile("Barel"), byPlayer);
        }

        return null;
    }


}
