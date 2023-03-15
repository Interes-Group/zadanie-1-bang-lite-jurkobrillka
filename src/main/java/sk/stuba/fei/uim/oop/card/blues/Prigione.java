package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.GameController;
import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.browns.CatBalou;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Prigione extends BlueCard{

    public Prigione(String title) {
        super(title);
    }


    @Override
    public int useCard(Player player, List<Player> players) {
        int prisonCHance = (int) Math.floor(Math.random() *(4 - 1 + 1) + 1);

        //chujovina pre iterator asi... player.removeTableCard(this);
        if (prisonCHance==4){
            System.out.println("Si vo vazeni, pokracuje dalsi hrac, kartu ti berieme");
            return 11;
        }
        else {
            System.out.println("Vyhol si sa vazeniu, kartu ti berieme");
            return 20;
        }

    }

    @Override
    public int useCard(GameController gameController) {
        int prisonCHance = (int) Math.floor(Math.random() *(4 - 1 + 1) + 1);

        if (prisonCHance==4){
            System.out.println("Si vo vazeni, pokracuje dalsi hrac, kartu ti berieme");
            return 1;
        }
        else {
            System.out.println("Vyhol si sa vazeniu, kartu ti berieme");
            return 2;
        }
    }

    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        System.out.println("Zadaj pred koho chces zadat vazenie: ");
        Player poorPlayer = choicePlayerToBeAttacked(byPlayer,players);
        boolean canIPutOnTheTable = true;
        for (PlayingCard bc: poorPlayer.getTableCards()){
            if (bc instanceof Prigione){
                System.out.println("Nemozes tam dat vazenie, uz jeden ma...\nVraciame ti kartu do ruky :) (nz...)");
                canIPutOnTheTable = false;
                byPlayer.getHandCards().add(new Prigione("Vazenie"));
                break;
            }
        }

        if (canIPutOnTheTable){
            poorPlayer.getTableCards().add(new Prigione("Vazenie"));
            System.out.println("Pokladas pred hraca "+poorPlayer.getName()+" kartu vazenie");
            for (int i = 0; i < byPlayer.getHandCards().size(); i++){
                if (byPlayer.getHandCards().get(i) instanceof Prigione){
                    break;
                }
            }
        }

        return null;
    }
}
