package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.Random;

public class Barile extends BlueCard{

    public Barile(String title) {
        super(title);
    }

    @Override
    public int useCard(Random rn, Player player) {
        return 0;
    }
}
