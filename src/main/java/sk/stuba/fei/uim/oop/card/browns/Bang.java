package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.Barile;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

public class Bang extends BrownCard{
    public Bang(String title) {
        super(title);
    }

    @Override
    public int useCard(Player player, List<Player> players) {
        return 0;
    }


    @Override
    public ArrayList<PlayingCard> useCard(Stack<PlayingCard> deck, Player byPlayer, List<Player> players) {
        //vyberam hraca na ktoreho budem strielat
        Player attackedPlayer = choicePlayerToBeAttacked(byPlayer,players);
        System.out.println("Ides zautocit na "+attackedPlayer.getName());


        //pozeram ci ma vedle
        //ale predtym pozeram ci ma doriti ten posraty bang
        boolean canContinue = true;

            for (PlayingCard pc: attackedPlayer.getTableCards()){
                if (pc instanceof Barile){
                    if (pc.useCard(attackedPlayer, players) == 1){
                        System.out.println("Hrac to trafil do barelu, este ze ho mas.");
                        canContinue = false;
                        break;
                    }
                    else {
                        System.out.println("hrac PRESTRELIL BAREL!");
                    }
                }
            }

            if (canContinue){
                boolean mancatoContinue = false;
                for (PlayingCard cardMan: attackedPlayer.getHandCards()){
                    if (cardMan instanceof Mancato){
                        mancatoContinue = true;
                        break;
                    }
                }

                if (mancatoContinue){
                    for (int j = 0; j < attackedPlayer.getHandCards().size(); j++) {
                        if (attackedPlayer.getHandCards().get(j) instanceof Mancato){
                            attackedPlayer.getHandCards().remove(j);
                            break;
                        }
                    }
                    System.out.println("Hrac "+attackedPlayer.getName()+ " ma kartu vedle, pouziva ju...");
                }
                else {
                    System.out.println("Hrac "+attackedPlayer.getName()+ " nema kartu vedle, straca zivot");
                    int atpLives = attackedPlayer.getLives();
                    attackedPlayer.setLives(atpLives-1);
                }
            }
        return null;
    }
}
