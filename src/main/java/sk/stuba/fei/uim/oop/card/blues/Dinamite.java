package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.Random;

public class Dinamite extends BlueCard {

    public Dinamite(String title) {
        super(title);
    }

    @Override
    public int useCard(Random rn, Player player) {

        int boomChance = rn.nextInt(8) + 1; //TODO TU VYSKUSAT CI BUCHNE A CI SA POSUNIE KARTE DRUHEMU HRACOVI
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
}
