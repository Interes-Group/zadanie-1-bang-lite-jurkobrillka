package sk.stuba.fei.uim.oop.controller;

import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.Barel;
import sk.stuba.fei.uim.oop.card.blues.Dynamite;
import sk.stuba.fei.uim.oop.card.blues.Prison;
import sk.stuba.fei.uim.oop.card.browns.*;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.*;

public class GameController {


    private static final String[] NAMES = {"Emiliano", "Pablo", "Chuan", "Jozko"};

    private final Stack<PlayingCard> playingCards = new Stack<>();

    private final Stack<PlayingCard> removedPlayingCards = new Stack<>();

    private final List<Player> deathPlayers = new ArrayList<>();

    private final List<Player> players = new ArrayList<>();

    public GameController() {
        lorePrint();
        initializeCards();
        initializePlayers();
        startMenu();
        gameHearth();
    }

    private void gameHearth() {
        System.out.println("Na rade je hrac: " + players.get(0).getName());
        int indexPlayer = 0;

        while (true) {

            Player playingPlayer = players.get(indexPlayer);

            int blueResults = playingPlayer.checkForBlueCards(players);
            if (blueResults == 11 || blueResults == 12) {
                System.out.println("Bohuzial, koncis svoj tah...");
            } else {
                checkDeathAndCommit();
                System.out.println("Tvoj tah pokracuje");

                firstPhasePickingCards(playingPlayer);

                secondPhaseRoundChoice(playingPlayer);

            }
            indexPlayer = playingPlayer.nextPlayer(players);
        }
    }

    private void secondPhaseRoundChoice(Player playingPlayer) {
        boolean continueBool = true;
        while (continueBool) {
            checkDeathAndCommit();
            roundMenu();
            int menuChoice = ZKlavesnice.readInt("Zvol jednu z moznosti: ");
            if (menuChoice < 1 || menuChoice > 3) {
                badInputText();
            } else {
                switch (menuChoice) {
                    case 1:
                        playingPlayer.printCurrentStatus();
                        break;
                    case 2:
                        playingPlayer.playingSpecificHandCard(playingCards, players);
                        break;
                    case 3:
                        continueBool = false;
                        if (playingPlayer.tooManyCards()) {
                            playingPlayer.removeRedundantCards(removedPlayingCards);
                        }

                        break;
                }
            }
        }
    }


    private void checkDeathAndCommit() {

        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getLives() <= 0) {
                p.playerDie(removedPlayingCards, deathPlayers);
                iterator.remove();
                if (players.size() == 1) {
                    System.out.println("KONIEC HRY\nVYHRAL HRAC: " + players.get(0).getName() + " -> SI UZASNY");
                    System.exit(0);
                }
            }
        }

    }


    private void firstPhasePickingCards(Player player) {

        System.out.println("Faza c.1: Tahas si 2 karty");
        for (int i = 0; i < 2; i++) {
            checkIfplayingCardsEmpty();
            player.addCardToHand(playingCards.pop());
        }
    }

    private void roundMenu() {
        System.out.println("Zvol moznost z menu: ");
        System.out.println("1. pozri si svoje karty a pocet zivotov");
        System.out.println("2. Zahraj kartu");
        System.out.println("3. Koniec tahu");
    }

    private void checkIfplayingCardsEmpty() {
        if (playingCards.size() == 0) {
            System.out.println("-----------0 kariet v balicku, doplnime pomiesame-----------------------");
            reffilPlayingCardPack();
        }
    }

    private void reffilPlayingCardPack() {
        playingCards.addAll(removedPlayingCards);
        removedPlayingCards.clear();
        Collections.shuffle(playingCards);
    }

    private void lorePrint() {
        System.out.println("\n---------------------------------------------------------------------------------");
        System.out.println("Ahoj, vitaj v hre FEI-BANG!");
        System.out.println("Nastavenie hry prebiha nasledovne: ");
        System.out.println("1. Vyberies si pocet hracov");
        System.out.println("2. Nastavime ti tvoj pocet hracov");
        System.out.println("3. Vyberies si z pociatocneho menu :");
        System.out.println("4. Ak si nezelas vykonat nic z pociatocneho menu, ideme hrat!");
        System.out.println("---------------------------------------------------------------------------------\n");
    }

    private void initializeCards() {

        playingCards.add(new Dynamite());

        for (int i = 0; i < 3; i++) {
            playingCards.add(new Prison());
        }

        for (int i = 0; i < 2; i++) {
            playingCards.add(new Barel());
        }

        for (int i = 0; i < 30; i++) {
            playingCards.add(new Bang());
        }

        for (int i = 0; i < 8; i++) {
            playingCards.add(new Beer());
        }

        for (int i = 0; i < 15; i++) {
            playingCards.add(new Missed());
        }

        for (int i = 0; i < 6; i++) {
            playingCards.add(new CatBalou());
        }

        for (int i = 0; i < 4; i++) {
            playingCards.add(new Diligence());
        }

        for (int i = 0; i < 2; i++) {
            playingCards.add(new Indians());
        }

        Collections.shuffle(playingCards);

    }

    private void initializePlayers() {


        while (true) {
            int playersCount = ZKlavesnice.readInt("Zadaj kolko chces mat hracov");
            if (playersCount < 2 || playersCount > 4) {
                System.out.println("Zadal si zly vstup, prosim zopakuj svoju volbu: ");
            } else {
                System.out.println("\nIdem nastavovat " + playersCount + " hracov: ");
                for (int i = 0; i < playersCount; i++) {
                    ArrayList<PlayingCard> playersCard = new ArrayList<>();
                    for (int j = 0; j < 4; j++) {
                        playersCard.add(playingCards.pop());
                    }
                    System.out.println("Nastavujeme " + (i + 1) + " hraca... ");
                    players.add(new Player(NAMES[i], 4, playersCard, new ArrayList<>()));
                    System.out.println("Hotovo, hrac c." + (i + 1) + " s menom " + NAMES[i] + " uspesne nastaveny\n");

                }
                break;
            }
        }

    }

    private void startMenu() {
        while (true) {
            startMenuPrint();
            int choice = ZKlavesnice.readInt("Vyber si moznost: (1/2)");
            if (choice == 1) {
                int playerIndex = ZKlavesnice.readInt("Zadaj poradove cislo hraca ktoreho karty chces vidiet (1-" + players.size() + ")");
                if (playerIndex < 1 || playerIndex > players.size()) {
                    badInputText();
                } else {
                    players.get(playerIndex - 1).printPlayersCard();
                }
            } else if (choice == 2) {
                System.out.println("Ideme hrat");
                break;
            } else {
                badInputText();
            }
        }
    }


    private void startMenuPrint() {
        System.out.println("Start menu:");
        System.out.println("1. Pozri si karty vybraneho hraca.");
        System.out.println("2. Zacnime hrat");
    }

    private void badInputText() {
        System.out.println("Zadal si zly vstup, opakuj volbu prosim");
    }


}
