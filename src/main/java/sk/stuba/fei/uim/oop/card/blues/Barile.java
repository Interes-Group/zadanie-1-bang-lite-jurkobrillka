package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.GameController;
import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Barile extends BlueCard{

    public Barile(String title) {
        super(title);
    }

    @Override
    public int useCard(Player player, List<Player> players) {
        int boomChance = (int) Math.floor(Math.random() *(4 - 1 + 1) + 1);//rn.nextInt(8) + 1; //TODO TU VYSKUSAT CI BUCHNE A CI SA POSUNIE KARTE DRUHEMU HRACOVI
        if (boomChance == 4) {
            //bomb has exploded
            System.out.println("Hrac to trafil do barelu, este ze ho mas.");
            // TODO removeTableCard(dynamiteCard);
            return 1;
        } else {
            // TODO removeTableCard(dynamiteCard);
            System.out.println("Nestihol si sa skryt za barel...");
            return 2;
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
            if (bc instanceof Barile){
                System.out.println("Nemozes dat pred seba barel, uz jeden mas...");
                canIPutOnTheTable = false;
                break;
            }
        }

        if (canIPutOnTheTable){
            System.out.println("Predkladas pred seba Barel.");
            byPlayer.getTableCards().add(new Barile("Barel"));
            //TODO REMOVE FROM HAND
            for (int i = 0; i < byPlayer.getHandCards().size(); i++){
                if (byPlayer.getHandCards().get(i) instanceof Barile){
                    break;
                }
            }
        }

        return null;
    }


}
