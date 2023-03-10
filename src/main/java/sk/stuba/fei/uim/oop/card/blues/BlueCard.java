package sk.stuba.fei.uim.oop.card.blues;

import sk.stuba.fei.uim.oop.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;

import java.util.ArrayList;
import java.util.Random;

public abstract class BlueCard extends PlayingCard {
    public BlueCard(String title) {
        super(title);
    }

    public abstract int useCard(Random rn, Player player); //budes davat prazdny list ak tak no ta sa neposer


}
