package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.Barile;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bang extends BrownCard{
    public Bang(String title) {
        super(title);
    }



    @Override
    public ArrayList<PlayingCard> useCard(Player byPlayer, List<Player> players) {
        //vyberam hraca na ktoreho budem strielat
        Player attackedPlayer = choicePlayerToBeAttacked(byPlayer,players);
        System.out.println("Ides zautocit na "+attackedPlayer.getName());


        //pozeram ci ma vedle
        //ale predtym pozeram ci ma doriti ten posraty bang
        Barile barel = new Barile("Barel");
        if (attackedPlayer.getTableCards().contains(barel)){
            //rob daco s barelom dopizdi
        }else {
            //nema barel, idem kukat ci nema vedle
            PlayingCard mancato = new Mancato("Vedla");
            boolean helpJesusBoolean = false;
            for (PlayingCard cardMan: attackedPlayer.getHandCards()){
                if (cardMan.getTitle().equals("Vedla")){
                    helpJesusBoolean = true;
                    break;
                }
            }

            if (helpJesusBoolean){
                //ma vedle, odstranim mu vedle
                for (int i = 0; i < attackedPlayer.getHandCards().size(); i++) {
                    if (attackedPlayer.getHandCards().get(i).getTitle().equals("Vedla")){
                        attackedPlayer.getHandCards().remove(i);
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
