package sk.stuba.fei.uim.oop;

import sk.stuba.fei.uim.oop.card.PlayingCard;
import sk.stuba.fei.uim.oop.card.blues.Barile;
import sk.stuba.fei.uim.oop.card.blues.BlueCard;
import sk.stuba.fei.uim.oop.card.blues.Dinamite;
import sk.stuba.fei.uim.oop.card.blues.Prigione;
import sk.stuba.fei.uim.oop.card.browns.*;
import sk.stuba.fei.uim.oop.utility.ZKlavesnice;

import java.util.*;

public class GameController {

    Random rn = new Random();

    public GameController() {
        lorePrint();
        initializeCards();
        initializePlayers();
        startMenu();

        gameHearth();
    }

    private final String[] names = {"Emiliano", "Pablo", "Chuan", "Jozko"};

    private final Stack<PlayingCard> playingCards = new Stack<>();

    private final Stack<PlayingCard> removedPlayingCards = new Stack<>();

    private final List<Player> players = new ArrayList<>();


    public void gameHearth() {
        boolean tr = true;
        int i = 0;
        int indexPlayer = 0;
        int beforeLen = 0;
        while (tr) {
            beforeLen = players.size();
            indexPlayer = i % players.size();
            System.out.println("Na rade je hrac " + players.get(indexPlayer).getName());

            Player playingPlayer = players.get(indexPlayer);
            System.out.println("Na rade je hrac: " + playingPlayer.getName());

            //todo POZOR ako kukas, ci prve vazenie a tak dynamit abo naspak jak to ma byt...

            int blueResults = checkForBlueCards(playingPlayer);
            if (blueResults == 11 || blueResults == 12) {
                System.out.println("Bohuzial, koncis svoj tah...");
            } else {
                System.out.println("Tvoj tah pokracuje");
                checkDeathAndCommit();
                //TODO PRUSER VYBUCHOL MAL ZOMRIET ALE NEZOMREL, lebo asi prve tahal na dynamite a tak ine....
                System.out.println("Tahas si 2 karty...");
                System.out.println("TURURU: " + playingPlayer.getTableCards().size());


                firstPhasePickingCards(playingPlayer);

                secondPhaseRoundChoice(playingPlayer, i);

            }
            i++;
            i = i - (beforeLen - players.size());
            for (int j = 0; j < players.size(); j++) {
                System.out.println("Meno: " + players.get(j).getName() + " - " + j);
            }

        }
    }

    public void secondPhaseRoundChoice(Player playingPlayer, int indexPlayer) {
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
                        //pozri karty
                        playingPlayer.printCurrentStatus();
                        break;
                    case 2:
                        //zahraj kartu
                        playingPlayer.playingSpecificHandCard(playingCards, players);
                        System.out.println("hrajem kartu");
                        break;
                    case 3:
                        //koniec
                        if (playingPlayer.tooManyCards()) {
                            playingPlayer.removeRedundantCards(removedPlayingCards);
                            continueBool = false;
                        } else {
                            continueBool = false;
                        }

                        break;
                }
            }
        }
    }

    public void gameRound() {

    }

    private void checkDeathAndCommit() {

        //TODO iterator
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getLives() <= 0) {
                p.playerDie(removedPlayingCards);
                iterator.remove();
                if (players.size() == 1) {
                    System.out.println("KONIEC HRY");
                    System.out.println("VYHRAL HRAC " + players.get(0).getName());
                    System.out.println("SI UZASNY");
                    System.exit(0);
                }


            }
        }

    }

    private int checkForBlueCards(Player playingPlayer) {

        int retAction = 0;
        //TODO CONTROL
        Iterator<PlayingCard> iterator = playingPlayer.getTableCards().iterator();
        while (iterator.hasNext()) {
            PlayingCard pc = iterator.next();
            retAction = pc.useCard(playingPlayer, players);
            if (retAction > 2) {
                iterator.remove(); // odstránenie prvku pomocou iterátora
                //TODO CONTROL
            }
        }

        return retAction;
    }

    public void firstPhasePickingCards(Player player) {

        for (int i = 0; i < 2; i++) {
            checkIfplayingCardsEmpty();
            player.addCardToHand(playingCards.pop());
        }
        System.out.println("");
    }

    public void roundMenu() {
        //menu po potiahnuti dvoch kariet
        System.out.println("Zvol moznost z menu: ");
        System.out.println("1. pozri si svoje karty a pocet zivotov");
        System.out.println("2. Zahraj kartu");
        System.out.println("3. Koniec tahu");
    }

    public void checkIfplayingCardsEmpty() {
        if (playingCards.size() == 0) {
            System.out.println("-----------0 kariet v balicku, doplnime pomiesame-----------------------");
            reffilPlayingCardPack();
        }
    }

    public void reffilPlayingCardPack() {
        playingCards.addAll(removedPlayingCards);
        removedPlayingCards.clear();
        Collections.shuffle(playingCards);
    }

    public void lorePrint() {
        System.out.println("\n---------------------------------------------------------------------------------");
        System.out.println("Ahoj, vitaj v hre FEI-BANG!");
        System.out.println("Nastavenie hry prebiha nasledovne: ");
        System.out.println("1. Vyberies si pocet hracov");
        System.out.println("2. Nastavime ti tvoj pocet hracov");
        System.out.println("3. Vyberies si z pociatocneho menu :");
        System.out.println("4. Ak si nezelas vykonat nic z pociatocneho menu, ideme hrat!");
        System.out.println("---------------------------------------------------------------------------------\n");
    }

    public void initializeCards() {

        //creating Blue Cards
        playingCards.add(new Dinamite("Dynamit"));

        for (int i = 0; i < 3; i++) {
            playingCards.add(new Prigione("Vazenie"));
        }

        for (int i = 0; i < 2; i++) {
            playingCards.add(new Barile("Barel"));
        }

        //creating Brown cards
        for (int i = 0; i < 30; i++) {
            playingCards.add(new Bang("Bang"));
        }

        for (int i = 0; i < 8; i++) {
            playingCards.add(new Birra("Pivo"));
        }

        for (int i = 0; i < 15; i++) {
            playingCards.add(new Mancato("Vedla"));
        }

        for (int i = 0; i < 6; i++) {
            playingCards.add(new CatBalou("Cat balou"));
        }

        for (int i = 0; i < 4; i++) {
            playingCards.add(new Diligenza("Dostavnik"));
        }

        for (int i = 0; i < 2; i++) {
            playingCards.add(new Indiani("Indiani"));
        }

        Collections.shuffle(playingCards);

    }

    public void initializePlayers() {


        while (true) {
            int playersCount = ZKlavesnice.readInt("Zadaj kolko chces mat hracov");
            if (playersCount < 2 || playersCount > 4) {
                System.out.println("Zadal si zly vstup, prosim zopakuj svoju volbu: ");
            } else {
                System.out.println("\nIdem nastavovat " + playersCount + " hracov: ");
                for (int i = 0; i < playersCount; i++) {
                    //vyberanie kariet hracovi
                    ArrayList<PlayingCard> playersCard = new ArrayList<>();
                    for (int j = 0; j < 4; j++) {
                        playersCard.add(playingCards.pop());
                    }
                    System.out.println("Nastavujeme " + (i + 1) + " hraca... ");
                    players.add(new Player(names[i], 1, playersCard, new ArrayList<PlayingCard>()));
                    System.out.println("Hotovo, hrac c." + (i + 1) + " s menom " + names[i] + " uspesne nastaveny\n");

                }
                break;
            }
        }

    }

    public void startMenu() {
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


    public void startMenuPrint() {
        System.out.println("Start menu:");
        System.out.println("1. Pozri si karty vybraneho hraca.");
        System.out.println("2. Zacnime hrat");
    }

    public void badInputText() {
        System.out.println("Zadal si zly vstup, opakuj volbu prosim");
    }


}
