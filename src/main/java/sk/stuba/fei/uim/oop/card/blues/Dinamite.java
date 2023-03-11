package sk.stuba.fei.uim.oop.card.blues;

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

    @Override
    public int useCard(Player player) {

        int boomChance = (int) Math.floor(Math.random() *(8 - 1 + 1) + 1);//rn.nextInt(8) + 1; //TODO TU VYSKUSAT CI BUCHNE A CI SA POSUNIE KARTE DRUHEMU HRACOVI
        if (boomChance == 8) {
            //bomb has exploded
            System.out.println("Buchol si :(");
            // TODO removeTableCard(dynamiteCard);
            return 1;
        } else {
            // TODO removeTableCard(dynamiteCard);
            System.out.println("Nebuchol si (jes!)");
            return 2;
        }

    }

    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {


        boolean canIPutOnTheTable = true;
        for (PlayingCard bc: byPlayer.getTableCards()){
            if (bc instanceof Dinamite){
                System.out.println("Nemozes dat pred seba dynamit, uz jeden mas...");
                canIPutOnTheTable = false;
                break;
            }
        }

        if (canIPutOnTheTable){
            System.out.println("Vkladas pred seba dynamit");
            byPlayer.getTableCards().add(new Dinamite("Dynamit"));

            for (int i = 0; i < byPlayer.getHandCards().size(); i++){
                if (byPlayer.getHandCards().get(i) instanceof Dinamite){
                    System.out.println("Berieme ti z ruky kartu "+byPlayer.getHandCards().get(i).getTitle()+" do odhadzovacieho balicka");
                    byPlayer.getHandCards().remove(i);
                    break;
                }
            }
        }

        return null;
    }
}
