package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Dinamite extends BlueCard {

    public Dinamite(String title) {
        super(title);
    }

    @Override
    public int useCard(Player playingPlayer, List<Player> players) {

        int boomChance = (int) Math.floor(Math.random() *(8 - 1 + 1) + 1);
        if (boomChance ==8) {
            System.out.println("Buchol si :(");
            int livesBeforeBomb = playingPlayer.getLives();
            playingPlayer.setLives(livesBeforeBomb-3);
            if (playingPlayer.getLives()<=0){
                return 12;
            }
            else return 10;

        } else {
            System.out.println("Nebuchol si (jes!): "+boomChance);
            Player pBefore = playingPlayer.whoIsPlayerBefore(players);
            System.out.println("Posuvas hracovi "+pBefore.getName()+" kartu dynamit :P ");
            pBefore.addCardToTable(new Dinamite("Dynamit"));
            return 20;
        }

    }


    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {


        boolean canIPutOnTheTable = true;
        for (PlayingCard bc: byPlayer.getTableCards()){
            if (bc instanceof Dinamite){
                System.out.println("Nemozes dat pred seba dynamit, uz jeden mas...\nVraciame ti kartu do ruky :) (nz...)");
                byPlayer.getHandCards().add(new Dinamite("Dynamit"));
                canIPutOnTheTable = false;
                break;
            }
        }
        if (canIPutOnTheTable){
            puttingCardOnTable(new Dinamite("Dynamit"),byPlayer);
        }

        return null;
    }
}
