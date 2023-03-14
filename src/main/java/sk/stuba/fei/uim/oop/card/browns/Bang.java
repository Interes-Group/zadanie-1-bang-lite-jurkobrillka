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
        Barile barel = new Barile("Barel");
        for (int i = 0; i <1 ; i++) {

            if (attackedPlayer.getTableCards().contains(barel)){
                if (barel.useCard(attackedPlayer, players) == 1){
                    //trafil barel, vyskakujes z loopu, ides dalej v hre
                    break;
                }
            }

                //nema barel, alebo ma ale netrafil d neho takze nevyskocil z loopu idem kukat ci nema vedle
                PlayingCard mancato = new Mancato("Vedla");
                boolean helpJesusBoolean = false;
                for (PlayingCard cardMan: attackedPlayer.getHandCards()){
                    if (cardMan instanceof Mancato){
                        helpJesusBoolean = true;
                        break;
                    }
                }

                if (helpJesusBoolean){
                    //ma vedle, odstranim mu vedle
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
