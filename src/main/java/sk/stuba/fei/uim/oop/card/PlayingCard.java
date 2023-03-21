package sk.stuba.fei.uim.oop.card;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public abstract class PlayingCard {
    String title;


    public PlayingCard(String title) {
        this.title = title;
    }


    public abstract int useCard(Player player, List<Player> players); //budes davat prazdny list ak tak no ta sa neposer


    public String getTitle() {
        return title;
    }

    public abstract ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players); //budes davat prazdny list ak tak no ta sa neposer


    public void setTitle(String title) {
        this.title = title;
    }

    public Player choicePlayerToBeAttacked(Player byPlayer, List<Player> players){
        System.out.println("Zadaj poradove cislo hraca na ktoreho chces zautocit touto kartou");
        for (int i = 0; i <players.size() ; i++) {
            System.out.print("("+(i+1)+"). "+players.get(i).getName()+", ");
        }
        int indexPlayer = 0;
        boolean goodChoice = true;
        while (goodChoice){
            int plIndex = ZKlavesnice.readInt("Zadaj poradove cislo: ");
            if (plIndex<1 || plIndex>players.size()){
                System.out.println("Zadal si zly vstup, opakuj volbu prosim.");
            }
            else if (players.get(plIndex-1) == byPlayer){
                System.out.println("Zadal si sam seba, opakuj volbu prosim.");
            }
            else {
                indexPlayer = plIndex;
                goodChoice = false;
            }
        }
        System.out.println("Vybral si si hraca s menom: "+players.get(indexPlayer-1).getName());
        return players.get(indexPlayer-1);
    }
}
