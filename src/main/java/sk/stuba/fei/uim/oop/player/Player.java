package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.*;

public class Player {


    private final String name;

    private int lives;

    private final List<PlayingCard> handCards;

    private final List<PlayingCard> tableCards;

    public Player(String name, int lives, List<PlayingCard> handCards, List<PlayingCard> tableCards) {
        this.name = name;
        this.lives = lives;
        this.handCards = handCards;
        this.tableCards = tableCards;
    }

    public String getName() {
        return name;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public List<PlayingCard> getHandCards() {
        return handCards;
    }


    public List<PlayingCard> getTableCards() {
        return tableCards;
    }


    private int cardChoice() {
        boolean goodChoice = true;
        int indexCard = 0;
        while (goodChoice) {
            indexCard = ZKlavesnice.readInt("Zadaj prosim poradove cislo karty (1-" + getHandCards().size() + ")");
            if (indexCard < 1 || indexCard > getHandCards().size()) {
                badInputText();
            } else {
                goodChoice = false;
            }
        }
        return indexCard - 1;
    }

    private void badInputText() {
        System.out.println("Zadal si zly vstup, opakuj volbu prosim");
    }


    public void playingSpecificHandCard(Stack<PlayingCard> playingCards,List<Player> players) {
        System.out.println("chces zahrat nejaku kartu, ukazem ti tvoje karty");
        printPlayersCard();
        if (getHandCards().size() == 0) {
            System.out.println("Mas 0 kariet, nemas co hrat...");
        } else {
            int cardChoice = cardChoice();
            PlayingCard bc = getHandCards().get(cardChoice);
            this.getHandCards().remove(bc);
            bc.useCard(playingCards, this, players);
        }
    }
    public void playerDie(Stack<PlayingCard> removedPlayingCards, List<Player> deathPlayers) {
        System.out.println("Hrac " + name + " stratil vsetky zivoty, umiera");
        deathPlayers.add(this);

        for (PlayingCard bc : handCards) {
            removedPlayingCards.push(bc);
        }
        for (PlayingCard bc : tableCards) {
            removedPlayingCards.push(bc);
        }

    }


    public void addCardToTable(PlayingCard pc){
        System.out.println("Hrac "+this.name+" ziskava kartu: "+pc.getTitle());
        tableCards.add(pc);
    }

    public void addCardToHand(PlayingCard card){
        handCards.add(card);
        System.out.println("Potiahol si kartu "+card.getTitle());
    }

    public void printCurrentStatus() {
        printPlayersCard();
        System.out.println("Hrac ma " + lives + " zivoty");
    }

    public boolean tooManyCards(){
        if (handCards.size()>lives){
            System.out.println("Mas privela kariet, (musis mat max tolko kolko ma zivotov)");
            System.out.println("Potrebujes vyhodit "+(handCards.size()-lives)+" karty");
            return true;
        }
        else {
            System.out.println("Tvoj pociet kariet je regularny k poctu zivotov, koncis svoj tah.");
            return false;
        }
    }

    public void removeRedundantCards(Stack<PlayingCard>removedPlayingCards){
        int redundantCards = handCards.size()-lives;
        System.out.println("Musis vyhodit "+redundantCards+" karty. Ukazem ti ich...");
        for (int i = 0; i <redundantCards ; i++) {
            printPlayersDeckOfCards(handCards);
            System.out.print("Chcem vymazat kartu s poradim: ");
            for (int j = 1; j <handCards.size()+1 ; j++) {
                System.out.print(j+"., ");
            }
            int unwantedCard = 0;
            boolean badInput = true;
            while (badInput){
                unwantedCard = ZKlavesnice.readInt("Zadaj poradove cislo");
                if (unwantedCard<=0 || unwantedCard>handCards.size()){
                    badInputText();
                }
                else {
                    badInput = false;
                }
            }
            PlayingCard remExample = handCards.get(unwantedCard-1);
            removedPlayingCards.add(remExample);
            handCards.remove(remExample);
        }
    }

    public int checkForBlueCards(List<Player> players) {

        int retAction = 0;
        Iterator<PlayingCard> iterator = this.getTableCards().iterator();
        while (iterator.hasNext()) {
            PlayingCard pc = iterator.next();
            retAction = pc.useCard(this, players);
            if (retAction > 2) {
                iterator.remove();
            }
        }

        return retAction;
    }


    public void printPlayersCard() {
        System.out.println("\nHrac s menom " + name + " ma karty:");
        System.out.println("\n----------------------------");
        System.out.println("Karty na ruke:");
        printPlayersDeckOfCards(handCards);
        System.out.println("----------------------------");
        System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxxxx");

        System.out.println("----------------------------");
        System.out.println("Karty na stole:");
        printPlayersDeckOfCards(tableCards);
        System.out.println("----------------------------");
    }

    private void printPlayersDeckOfCards(List<PlayingCard> deck){
        if (deck.isEmpty()){
            System.out.println("Hrac nema ziadne karty");
        }else {
            int indexhelping = 1;
            for (PlayingCard pc:deck){
                System.out.print("("+indexhelping+".) "+pc.getTitle()+", ");
                indexhelping++;
            }
            System.out.println();
        }
    }

    public int nextPlayer(List<Player> players){
        for (int j = 0; j < players.size(); j++) {
            if (players.get(j).getName().equals(this.getName())){
                System.out.println("Na rade je hrac: " + players.get(((j+1)%players.size())).getName());
                return ((j+1)%players.size());
            }
        }
        return 0;
    }

    public Player choicePlayerToBeAttacked(Player byPlayer, List<Player> players){
        System.out.println("Zadaj poradove cislo hraca na ktoreho chces zautocit touto kartou");
        for (int i = 0; i <players.size() ; i++) {
            System.out.print("("+(i+1)+"). "+players.get(i).getName()+", ");
        }
        int indexPlayer = 0;
        boolean goodChoice = true;
        while (goodChoice){
            int plIndex = ZKlavesnice.readInt("Zadaj poradove cislo: ");
            if (plIndex<1 || plIndex>players.size()){
                System.out.println("Zadal si zly vstup, opakuj volbu prosim.");
            }
            else if (players.get(plIndex-1) == byPlayer){
                System.out.println("Zadal si sam seba, opakuj volbu prosim.");
            }
            else {
                indexPlayer = plIndex;
                goodChoice = false;
            }
        }
        System.out.println("Vybral si si hraca s menom: "+players.get(indexPlayer-1).getName());
        return players.get(indexPlayer-1);
    }


    public Player whoIsPlayerBefore(List<Player> players){
        for (int i = 1; i <players.size() ; i++) {
            if (players.get(i) == this){
                return players.get(i-1);
            }
        }
        return players.get(players.size()-1);
    }

}
