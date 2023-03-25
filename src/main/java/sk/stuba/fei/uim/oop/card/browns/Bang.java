package sk.stuba.fei.uim.oop.card.browns;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.Barel;

import java.util.ArrayList;
import java.util.List;
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
        //vyberam hraca na ktoreho budem strielat, spravny sposob potvrdeny cviciacou Ing. Vanesou Andicsovou 14.3.2023
        Player attackedPlayer = byPlayer.choicePlayerToBeAttacked(byPlayer,players);
        System.out.println("Ides zautocit na "+attackedPlayer.getName());

        boolean canContinue = true;

            for (PlayingCard pc: attackedPlayer.getTableCards()){
                if (pc instanceof Barel){
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
                    if (cardMan instanceof Missed){
                        mancatoContinue = true;
                        break;
                    }
                }

                if (mancatoContinue){
                    for (int j = 0; j < attackedPlayer.getHandCards().size(); j++) {
                        if (attackedPlayer.getHandCards().get(j) instanceof Missed){
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
