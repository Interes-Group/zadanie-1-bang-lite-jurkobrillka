package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.GameController;
import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Dinamite extends BlueCard {

    public Dinamite(String title) {
        super(title);
    }

    public Player whoIsPlayerBefore(Player byPlayer, List<Player> players){
        for (int i = 1; i <players.size() ; i++) {
            if (players.get(i) == byPlayer){
                return players.get(i-1);
            }
        }
        return players.get(players.size()-1);
    }

    @Override
    public int useCard(Player playingPlayer, List<Player> players) {

        //remove from table asi okkocina iterator tA MAZEM... playingPlayer.removeTableCard(this);
        int boomChance = (int) Math.floor(Math.random() *(8 - 1 + 1) + 1);//rn.nextInt(8) + 1; //TODO TU VYSKUSAT CI BUCHNE A CI SA POSUNIE KARTE DRUHEMU HRACOVI
        if (boomChance == 8) {
            //buchla bomba, zoberie mu zivoty, ale NEZDECHNE...
            //TODO CONTROL
            System.out.println("Buchol si :(");
            int livesBeforeBomb = playingPlayer.getLives();
            playingPlayer.setLives(livesBeforeBomb-3);
            if (playingPlayer.getLives()<=0){
                return 12;
            }
            else return 10;

        } else {
            System.out.println("Nebuchol si (jes!): "+boomChance);
            Player pBefore = whoIsPlayerBefore(playingPlayer,players);
            System.out.println("Posuvas hracovi "+pBefore.getName()+" kartu dynamit :P ");
            pBefore.addCardToTable(new Dinamite("Dynamit"));
            //posuvas inemu
            return 20;
        }

    }

    @Override
    public int useCard(GameController gameController) {
        return 0;
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
            System.out.println("Vkladas pred seba dynamit");
            byPlayer.getTableCards().add(new Dinamite("Dynamit"));

            for (int i = 0; i < byPlayer.getHandCards().size(); i++){
                if (byPlayer.getHandCards().get(i) instanceof Dinamite){
                    break;
                }
            }
        }

        return null;
    }
}
