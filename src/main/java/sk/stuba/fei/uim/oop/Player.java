package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.BlueCard;
import sk.stuba.fei.uim.oop.card.blues.Dinamite;
import sk.stuba.fei.uim.oop.card.blues.Prigione;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

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

    public void playerDie(List<Player> players, Stack<PlayingCard> removedPlayingCards) {
        System.out.println("Hrac " + name + " stratil vsetky zivoty, umiera");
        players.remove(this);
        for (PlayingCard bc : handCards) {
            removedPlayingCards.push(bc);
        }
        for (PlayingCard bc : tableCards) {
            removedPlayingCards.push(bc);
        }
    }

    public int prisonAction(Random rn){
        Prigione prisonCard = new Prigione("Vazenie");
        if(tableCards.contains(prisonCard)){
            removeTableCard(prisonCard);
            return prisonCard.useCard(rn, this);
        }
        else {
            return 3;
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
            int unwantedCard = ZKlavesnice.readInt("Zadaj poradove cislo");
            //TODO OSETRI VSTUP TERAZ SA MI NECHCE!!!
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
        Dinamite dynamiteCard = new Dinamite("Dynamit");
        if (tableCards.contains(dynamiteCard)) {
            removeTableCard(dynamiteCard);
            return dynamiteCard.useCard(rn,this);
        } else {
            System.out.println("Nemas pred sebou dynamit");
            return 3;
            //TODO NEMAS PORIESENE ZE POJDE DALSIEMU HRACOVI, SPRAVIT!!!
        }
    }

    public void removeTableCard(PlayingCard bc){
        tableCards.remove(bc);
    }


}
