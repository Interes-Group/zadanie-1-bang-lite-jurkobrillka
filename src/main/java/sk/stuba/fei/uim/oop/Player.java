package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.BlueCard;
import sk.stuba.fei.uim.oop.card.blues.Dinamite;
import sk.stuba.fei.uim.oop.card.blues.Prigione;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.*;

public class Player {


    private String name;

    private int lives;

    private List<PlayingCard> handCards;

    private List<PlayingCard> tableCards;

    public Player(String name, int lives, List<PlayingCard> handCards, List<PlayingCard> tableCards) {
        this.name = name;
        this.lives = lives;
        this.handCards = handCards;
        this.tableCards = tableCards;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setHandCards(List<PlayingCard> handCards) {
        this.handCards = handCards;
    }

    public List<PlayingCard> getTableCards() {
        return tableCards;
    }

    public void setTableCards(List<PlayingCard> tableCards) {
        this.tableCards = tableCards;
    }

    public int cardChoice() {
        boolean goodChoice = true;
        int indexCard = 0;
        while (goodChoice) {
            indexCard = ZKlavesnice.readInt("Zadaj poradove cislo karty (1-" + getHandCards().size() + ")");
            if (indexCard < 1 || indexCard > getHandCards().size()) {
                //vybral zle chujo pokracuje
                badInputText();
            } else {
                goodChoice = false;
            }
        }
        return indexCard - 1;
    }

    public void badInputText() {
        System.out.println("Zadal si zly vstup, opakuj volbu prosim");
    }


    public void playingSpecificHandCard(Stack<PlayingCard> playingCards,List<Player> players) {
        System.out.println("chces zahrat nejaku kartu, ukazem ti tvoje karty");
        printPlayersCard();
        if (getHandCards().size() == 0) {
            System.out.println("Sak mas 0 kariet ty dilinko co chces hrat XD"); //uprav text dilino
        } else {
            int cardChoice = cardChoice();
            PlayingCard bc = getHandCards().get(cardChoice);
            System.out.println("SKUSAM KARTU ZABAVIC");
            //TODO - vymazac sout chujovy
            this.getHandCards().remove(bc);
            bc.useCard(playingCards, this, players);
        }
    }
    public void playerDie(Stack<PlayingCard> removedPlayingCards) {
        System.out.println("Hrac " + name + " stratil vsetky zivoty, umiera");
        for (PlayingCard bc : handCards) {
            removedPlayingCards.push(bc);
        }
        for (PlayingCard bc : tableCards) {
            removedPlayingCards.push(bc);
        }

    }

    public int prisonAction(){
        for (int i = 0; i <tableCards.size() ; i++) {
            if (tableCards.get(i) instanceof Prigione){
                BlueCard bc = (BlueCard) tableCards.get(i);
                removeTableCard(bc);
                return 10;//bc.useCard(this,);
            }
        }
        return 3;


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
        //TODO
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

        //odstranovanie kariet
        for (int i = 0; i <redundantCards ; i++) {
            printPlayersDeckOfCards(handCards);
            System.out.print("Chcem vymazat kartu s poradim: ");
            for (int j = 1; j <handCards.size()+1 ; j++) {
                System.out.print(j+"., ");
            }
            int unwantedCard = 0;
            //TODO OSETRI VSTUP TERAZ SA MI NECHCE!!!
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
            //DOCHUJA CELE ZLE - mozno ne uz asi opravene mozno -asik uz dobre
        }
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

    public void printPlayersDeckOfCards(List<PlayingCard> deck){
        if (deck.isEmpty()){
            System.out.println("Hrac nema ziadne karty");
        }else {
            int indexhelping = 1;
            for (PlayingCard pc:deck){
                System.out.print("("+indexhelping+".) "+pc.getTitle()+", ");
                indexhelping++;
            }
            System.out.println("");
        }
    }

    public int dynamiteAction(Random rn) {
        for (int i = 0; i <tableCards.size() ; i++) {
            if (tableCards.get(i) instanceof Dinamite){
                System.out.println("NO BOHA TU SOM");
                BlueCard dc = (BlueCard) tableCards.get(i);
                removeTableCard(tableCards.get(i));
                return 10;//dc.useCard(this);
            }
        }
        //WORKING ON ZE NEREGISTRUJE BARELL ANI VAZENIE TODO
        System.out.println("Nemas pred sebou dynamit");
        return 3;
        //TODO NEMAS PORIESENE ZE POJDE DALSIEMU HRACOVI, SPRAVIT!!! - fakt nemas? bo mne sa zda ze hej


    }

    public void removeTableCard(PlayingCard bc){
        tableCards.remove(bc);
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

}
