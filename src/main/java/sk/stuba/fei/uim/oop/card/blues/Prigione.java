package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.Random;

public class Prigione extends BlueCard{

    public Prigione(String title) {
        super(title);
    }


    @Override
    public int useCard(Random rn, Player player) {
        int prisonCHance = rn.nextInt(4)+1;
        if (prisonCHance==4){
            System.out.println("Si vo vazeni, pokracuje dalsi hrac, kartu ti berieme");
            return 1;
        }
        else {
            System.out.println("Vyhol si sa vazeniu, kartu ti berieme");
            return 2;
        }

    }
}
