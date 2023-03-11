package sk.stuba.fei.uim.oop.card.blues;

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
    public int useCard(Player player) {
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
            if (bc instanceof Dinamite){
                System.out.println("Nemozes dat pred seba dynamit, uz jeden ma...");
                canIPutOnTheTable = false;
                break;
            }
        }

        if (canIPutOnTheTable){
            poorPlayer.getTableCards().add(new Prigione("Vazenie"));
            System.out.println("Pokladas pred hraca "+poorPlayer.getName()+" kartu vazenie");
            for (int i = 0; i < byPlayer.getHandCards().size(); i++){
                if (byPlayer.getHandCards().get(i) instanceof Prigione){
                    System.out.println("Berieme ti z ruky kartu "+byPlayer.getHandCards().get(i).getTitle()+" do odhadzovacieho balicka");
                    byPlayer.getHandCards().remove(i);
                    break;
                }
            }
        }

        return null;
    }
}
