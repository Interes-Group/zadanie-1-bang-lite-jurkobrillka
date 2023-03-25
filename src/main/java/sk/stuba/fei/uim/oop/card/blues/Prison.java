package sk.stuba.fei.uim.oop.card.blues;


import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Prison extends BlueCard{

    public Prison(String title) {
        super(title);
    }


    @Override
    public int useCard(Player player, List<Player> players) {
        int prisonCHance = (int) Math.floor(Math.random() *(4 - 1 + 1) + 1);

        if (prisonCHance==4){
            System.out.println("Si vo vazeni, pokracuje dalsi hrac, kartu ti berieme");
            return 11;
        }
        else {
            System.out.println("Vyhol si sa vazeniu, kartu ti berieme: "+prisonCHance);
            return 20;
        }

    }


    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        System.out.println("Zadaj pred koho chces zadat vazenie: ");
        Player poorPlayer = byPlayer.choicePlayerToBeAttacked(byPlayer,players);
        boolean canIPutOnTheTable = true;
        for (PlayingCard bc: poorPlayer.getTableCards()){
            if (bc instanceof Prison){
                System.out.println("Nemozes tam dat vazenie, uz jeden ma...\nVraciame ti kartu do ruky :) (nz...)");
                canIPutOnTheTable = false;
                byPlayer.getHandCards().add(new Prison("Vazenie"));
                break;
            }
        }

        if (canIPutOnTheTable){
            puttingCardOnTable(new Prison("Vazenie"),poorPlayer);
        }

        return null;
    }
}
