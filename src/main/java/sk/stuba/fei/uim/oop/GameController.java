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
        //tu mame rozdelenych hracov, maju karty, specificky hrac si pozrel svoje karty...

        //ideme hrat
        gameHearth();
    }

    private String[] names = {"Emiliano", "Pablo", "Chuan", "Jozko"};

    private Stack<PlayingCard> playingCards = new Stack<>();

    private Stack<PlayingCard> removedPlayingCards = new Stack<>();

    private List<Player> players = new ArrayList<>();





    public void gameHearth(){
        while (players.size()>1){
            //one round
            gameRound();
        }
    }

    public void secondPhaseRoundChoice(Player playingPlayer){
        boolean continueBool = true;
        while (continueBool){
            checkDeathAndCommit();
            roundMenu();
            int menuChoice = ZKlavesnice.readInt("Zvol jednu z moznosti: ");
            if (menuChoice<1 || menuChoice>3){
                badInputText();
            }
            else {
                switch (menuChoice){
                    case 1:
                        //pozri karty
                        playingPlayer.printCurrentStatus();
                        break;
                    case 2:
                        //zahraj kartu
                        //TODO ZVOL CI CHES KARTY Z RUKY NA STOL DAT
                        playingSpecificHandCard(playingPlayer);
                        System.out.println("hrajem kartu");
                        break;
                    case 3:
                        //koniec
                        if (playingPlayer.tooManyCards()){
                            playingPlayer.removeRedundantCards(removedPlayingCards);
                            continueBool = false;
                        }
                        else {
                            continueBool = false;
                        }

                        break;
                }
            }
        }
    }
    public void gameRound(){

        //s;
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player playingPlayer = iterator.next();
            System.out.println("Na rade je hrac: "+playingPlayer.getName());

           //todo POZOR ako kukas, ci prve vazenie a tak dynamit abo naspak jak to ma byt...

            if (checkForBlueCards(playingPlayer) == 11 || checkForBlueCards(playingPlayer) == 12){
                System.out.println("Bohuzial, koncis svoj tah...");
            }
            else {
                System.out.println("Tvoj tah pokracuje");
                System.out.println("Tahas si 2 karty...");
                System.out.println("TURURU: " + playingPlayer.getTableCards().size());
                checkDeathAndCommit();

                firstPhasePickingCards(playingPlayer);

                secondPhaseRoundChoice(playingPlayer);

            }
        }

        //s
        for (Player playingPlayer: players){

        }

    }

    private void checkDeathAndCommit() {

        //TODO iterator
        Iterator<Player> iterator = players.iterator();
        while (iterator.hasNext()) {
            Player p = iterator.next();
            if (p.getLives()<=0){
                p.playerDie(players,removedPlayingCards);
                iterator.remove();
            }
        }

    }

    private int checkForBlueCards(Player playingPlayer) {

        //IDEM ROBIT normalne s hracmi vo funkcii jebal to pes SPRAV 2 verzie, ukaz na cviku...
        //TODO

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

    public void firstPhasePickingCards(Player player){

        for (int i = 0; i <2 ; i++) {
            //pozeram ci balik neni prazdny
            checkIfplayingCardsEmpty();
            //ak je aj prezdny, opravi a mozem tahat
            player.addCardToHand(playingCards.pop());
        }
        System.out.println("");
    }

    public void roundMenu(){
        //menu po potiahnuti dvoch kariet
        System.out.println("Zvol moznost z menu: ");
        System.out.println("1. pozri si svoje karty a pocet zivotov");
        System.out.println("2. Zahraj kartu");
        System.out.println("3. Koniec tahu");
    }

    public void checkIfplayingCardsEmpty(){
        if (playingCards.size()==0){
            System.out.println("-----------0 kariet v balicku, doplnime pomiesame-----------------------");
            //TODO SKACE ERROR lebo este nedavam nic do removed
            reffilPlayingCardPack();
        }
    }

    public void reffilPlayingCardPack(){
        playingCards.addAll(removedPlayingCards);
        removedPlayingCards.clear();
        Collections.shuffle(playingCards);
    }

    public boolean prisonChecking(Player playingPlayer){
        int prisonActions = playingPlayer.prisonAction();
        if (prisonActions<3){
            //hrac ma kartu vazenie
            removedPlayingCards.add(new Prigione("Vazenie"));

            if (prisonActions==1){
                //je vo vazeni taze musi sa v hlavnom breaknut jeho tah TODO
                return false;
            }
            else {
                //neni vo vazeni, pokracuje
                return true;
            }
        }
        else {
            System.out.println("Nemas pred sebou vazenie");
            return true;
            //hrac nema kartu vazenie
        }
    }

    public void dynamiteChecking(Player playingPlayer){
        //idem pozriet kto je predmonou nech mu mozem posunut dynamit
        Player pBefore = whoIsPlayerBefore(playingPlayer);


        int dynAct = playingPlayer.dynamiteAction(rn);
        if (dynAct==1){
            int livesBeforeBomb = playingPlayer.getLives();
            playingPlayer.setLives(livesBeforeBomb-3);
            if (playingPlayer.getLives()<1){
                playingPlayer.playerDie(players,removedPlayingCards);
                players.remove(playingPlayer);
            }
            //TODO NEMAS PORIESENE ZE POJDE DALSIEMU HRACOVI, SPRAVIT!!! - UZ HOTOVO?
        }else if (dynAct==2){
            pBefore.addCardToTable(new Dinamite("Dynamit"));
        }
    }





    public Player whoIsPlayerBefore(Player playingPlayer){

        for (int i = 1; i <players.size() ; i++) {
            if (players.get(i) == playingPlayer){
                return players.get(i-1);
            }
        }
        return players.get(players.size()-1);
    }

    public void lorePrint(){
        System.out.println("\n---------------------------------------------------------------------------------");
        System.out.println("Ahoj, vitaj v hre FEI-BANG!");
        System.out.println("Nastavenie hry prebiha nasledovne: ");
        System.out.println("1. Vyberies si pocet hracov");
        System.out.println("2. Nastavime ti tvoj pocet hracov");
        System.out.println("3. Vyberies si z pociatocneho menu :");
        System.out.println("4. Ak si nezelas vykonat nic z pociatocneho menu, ideme hrat!");
        System.out.println("---------------------------------------------------------------------------------\n");
    }

    public void initializeCards(){

        //creating Blue Cards
        playingCards.add(new Dinamite("Dynamit"));

        for (int i = 0; i <3 ; i++) {
            playingCards.add(new Prigione("Vazenie"));
        }

        for (int i = 0; i <2 ; i++) {
            playingCards.add(new Barile("Barel"));
        }



        //creating Brown cards
        for (int i = 0; i <30 ; i++) {
            playingCards.add(new Bang("Bang"));
        }

        for (int i = 0; i <8 ; i++) {
            playingCards.add(new Birra("Pivo"));
        }

        for (int i = 0; i <15 ; i++) {
            playingCards.add(new Mancato("Vedla"));
        }

        for (int i = 0; i <6 ; i++) {
            playingCards.add(new CatBalou("Cat balou"));
        }

        for (int i = 0; i <4 ; i++) {
            playingCards.add(new Diligenza("Dostavnik"));
        }

        for (int i = 0; i <2 ; i++) {
            playingCards.add(new Indiani("Indiani"));
        }

        Collections.shuffle(playingCards);

    }

    public void initializePlayers() {


        while (true){
            int playersCount = ZKlavesnice.readInt("Zadaj kolko chces mat hracov");
            if (playersCount<2 || playersCount>4){
                System.out.println("Zadal si zly vstup, prosim zopakuj svoju volbu: ");
            }
            else {
                System.out.println("\nIdem nastavovat "+playersCount+" hracov: ");
                for (int i = 0; i <playersCount ; i++) {
                    //vyberanie kariet hracovi
                    ArrayList<PlayingCard> playersCard = new ArrayList<>();
                    for (int j = 0; j <4 ; j++) {
                        playersCard.add(playingCards.pop());
                    }
                    System.out.println("Nastavujeme "+(i+1)+" hraca... ");
                    players.add(new Player(names[i],1,playersCard,new ArrayList<PlayingCard>()));
                    System.out.println("Hotovo, hrac c."+(i+1)+" s menom "+ names[i]+" uspesne nastaveny\n");

                }
                break;
            }
        }

    }

    public void playingSpecificHandCard(Player player){
        System.out.println("chces zahrat nejaku kartu, ukazem ti tvoje karty");
        player.printPlayersCard();
        if (player.getHandCards().size()==0){
            System.out.println("Sak mas 0 kariet ty dilinko co chces hrat XD"); //uprav text dilino
        }else {
            int cardChoice = cardChoice(player);
            PlayingCard bc = player.getHandCards().get(cardChoice);
            System.out.println("SKUSAM KARTU ZABAVIC");
            //TODO - vymazac sout chujovy
            player.getHandCards().remove(bc);
            bc.useCard(playingCards,player,players);

        }
    }

    public int cardChoice(Player player){
        boolean goodChoice = true;
        int indexCard = 0;
        while (goodChoice){
            indexCard = ZKlavesnice.readInt("Zadaj poradove cislo karty (1-"+player.getHandCards().size()+")");
            if (indexCard<1 || indexCard>player.getHandCards().size()){
                //vybral zle chujo pokracuje
                badInputText();
            }
            else {
                goodChoice = false;
            }
        }
        return indexCard-1;
    }


    public Player oponentChoice(Player player){
        boolean goodChoice = true;
        int indexCard = 0;
        while (goodChoice){
            indexCard = ZKlavesnice.readInt("Zadaj poradove cislo hraca (1-"+players.size()+")");
            if (indexCard<1 || indexCard>players.size()){
                //vybral zle chujo pokracuje
                badInputText();
            }
            else {
                goodChoice = false;
            }
        }
        return players.get(indexCard-1);
    }
    public void startMenu(){
        while (true){
            startMenuPrint();
            int choice = ZKlavesnice.readInt("Vyber si moznost: (1/2)");
            if (choice==1){
                int playerIndex = ZKlavesnice.readInt("Zadaj poradove cislo hraca ktoreho karty chces vidiet (1-"+players.size()+")");
                if (playerIndex<1 || playerIndex>players.size()){
                    badInputText();
                }
                else {
                    players.get(playerIndex-1).printPlayersCard();
                }
            }
            else if (choice==2){
                System.out.println("Ideme hrat");
                break;
            }
            else {
                badInputText();
            }
        }
    }



    public void startMenuPrint(){
        System.out.println("Start menu:");
        System.out.println("1. Pozri si karty vybraneho hraca.");
        System.out.println("2. Zacnime hrat");
    }

    public void badInputText(){
        System.out.println("Zadal si zly vstup, opakuj volbu prosim");
    }


}
