package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.Heroes;
import MonstersHeroesGame.Data.MainData.Monsters;
import MonstersHeroesGame.Data.StoreData.HeroesSingleton;
import MonstersHeroesGame.Data.StoreData.MonstersSingleton;
import Parent.GamePrinter;
import Parent.Starter;

import java.awt.event.MouseMotionAdapter;
import java.sql.SQLOutput;
import java.util.*;

public class HMGameStarter extends Starter {
    private HMGameBoard board;
    private HMGamePrinter printer;
    private List<HMGamePlayer> players;
    private HeroesSingleton heroInstance;
    private MonstersSingleton monstersInstance;
    private MarketSpaceDealing marketSpaceDealing;
    private String winner;
    private boolean quit;


    public HMGameStarter() {
        this.players = new ArrayList<HMGamePlayer>();
        this.printer = new HMGamePrinter();
        heroInstance = HeroesSingleton.getInstance();
        monstersInstance = MonstersSingleton.getInstance();
        marketSpaceDealing = new MarketSpaceDealing();
        winner = "";
    }
    public void startGame() {

        String[] welcomeMsg = {
                "CAS CS 611, HW2 'Dots and Boxes' by Atharv & William \n",
                "Rules: ...\n",
                "\t1. The Goal of both players is to draw as many boxes as they can\n",
                "\t2. Row and column naming is based on lines (either horizontal or vertical)\n",
                "\t3. The player with the most number of boxes wins at the end\n",
                "\t4. If you make a box, you get an additional turn.'\n",
                "\t5. You can enter quit at any time to quit. Quitting by one player will result in other player winning. '\n"
        };
        String[] playerInput={"Enter the number of players who want to play this game (1-3)"};
        String[] invalidInput={"Please Enter a Valid Number"};
        int nPlayers=1;


        for(int i=0;i<nPlayers;i++){
            addUsers(i);
        }

        initBoard();

        int tot= board.getSizex()*board.getSizey();
        String[] heroInitials=new String[players.get(0).chosenHeroes.size()];
        for(int i=0;i<heroInitials.length;i++){
            heroInitials[i]=""+(players.get(0).chosenHeroes.get(i).name).charAt(0);
        }
        List<String> playernNames=new ArrayList<>();
        for(int i=0;i<nPlayers;i++){
            playernNames.add(players.get(i).getUsername());
        }
        board.setInitialPieces((int)(tot*0.3), (int)(tot*0.2),heroInitials,playernNames);
        marketSpaceDealing.mktInitialize();
    }

    public void playGame() {
        System.out.println("Playing game");
        String[] quitMsg = {"Quitting Game ...","Thanks for playing!"};
        String[] playerTurnMsg = {
                "Player",
                "playerName",
                "take your turn..."
        };
        int numMoves=0;
        do{
            for(HMGamePlayer player:players){
                printer.printBoard(board.getBoardArray());
                playerTurnMsg[1] = player.getUsername();
                printer.displayMsgNoSp(playerTurnMsg);
                boolean success=false;
                boolean marketArr=false;
                do {
                    HMGameMove move=(HMGameMove) player.takeTurn();
                    if(move.direction==null){
                        System.out.println("Please Enter a valid move");
                        continue;
                    }
                    if(move.direction.equalsIgnoreCase("i")){
                        player.displayStats();
                        System.out.println("Hope you have seen your Hero stats!");
                        System.out.println("Now Please Proceed to take your move");
                        continue;
                    }
                    success=board.makeMove(move);
                    System.out.println(success);
                    marketArr= move.mktArr;

                    System.out.println(move.mktArr);

//                    success=true;
                }while(!success);
                if(marketArr){
                    do {
                        String inp=printer.getInput(new String[]{"Do you Wish to enter the market","Enter M/m to enter the market","Enter N/n to skip"});
                        if(inp.equalsIgnoreCase("m")){
                            System.out.println("Entering the market");
                            boolean mv = player.mktMove(marketSpaceDealing);
                        }
                        if(inp.equalsIgnoreCase("n")){
                            break;
                        }
                    }while (true);
                }
                else{
                    int luck=spinDice();
                    if(luck%2!=0){
                        System.out.println("Heroes Get ready for a battle");
                        List<Monsters> spawnedMonsters=spawnMonsters();
                        List<MonsterSpawn>sp=new ArrayList<>();
                        Collections.shuffle(spawnedMonsters);
                        int max=spawnedMonsters.size();
                        if(max>players.get(0).chosenHeroes.size()){
                            max=players.get(0).chosenHeroes.size();
                        }
                        spawnedMonsters=spawnedMonsters.subList(0,max);
                        for(Monsters m:spawnedMonsters){
                            if(player.notDefeated(m)) {
                                sp.add(new MonsterSpawn(m.getName(), Integer.parseInt(m.getLevel()), Integer.parseInt(m.getDamage()), Integer.parseInt(m.getDefense()), Integer.parseInt(m.getDodgeChance())));
                            }
                        }

                        HMBattle battle=new HMBattle(player.chosenHeroes,sp);
                        battle.battleStart();
                        winner=battle.battleWinner;
                        if(winner.equals("Monsters")){
                            break;
                        }
                        player.wonBattleTurn(sp);
                    }
                    else{
                        System.out.println("Pheww! That was close. The monsters went by without noticing you");
                    }
                }


            }
        }while(true);
    }

    public void endGame() {
        if(quit){
            System.out.println("Since you have quit the game, the Monsters Win");
            System.out.println("Quitting Game ...");
        }
        if(winner.equals("Monsters")){
            System.out.println("All Your Heroes Have Been Defeated! Monsters Win!");
        }
        else{
            System.out.println("You Win!!");
            System.out.println("Your Heroes Have Defeated all the Monsters!");
        }
    }

    public void addUsers(int n){
        String[] userAccept={("Enter the name of the Player "+(n+1)+" name")};
        String[] invalid={"Invalid Input!"};
        String userName;
        while(true) {
            try {
                userName = printer.getInput(userAccept);
                userName=userName.trim();
                if(userName.isEmpty()){
                    printer.displayMsgs(invalid);
                    continue;
                }
                break;
            }
            catch (Exception e){
                printer.displayMsgs(invalid);
            }
        }


        String[] characterAccept={"To Play the Game you will have to pick one of the Heroes",
                                    "Your chosen one will be your character for rest of the game",
                                    "The List of Available characters is Displayed Below",
                                    "While Selecting your Hero please enter the the number corresponding to your hero specified in the index column"};
        printer.displayMsgs(characterAccept);

        int heroParty;
        String[] numHeroes={"Enter the party of heroes(1-3)"};
        while(true) {
            try{
                String inp=printer.getInput(numHeroes);
                if(inp.length()>1){
                    printer.displayMsgs(invalid);
                    continue;
                }
                else{
                    heroParty=Integer.parseInt(inp);
                    if(heroParty>=1 && heroParty<=3){
                        break;
                    }
                    printer.displayMsgs(invalid);
                }
            }
            catch (Exception e){
                printer.displayMsgs(invalid);
            }
        }


//        System.out.println(heroParty);
        List<HMChosenHero> chosenHeroes=new ArrayList<>();

        for(int i=0;i<heroParty;i++){
            int k = 1;
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                    "Index", "Hero", "Mana", "Strength", "Agility", "Dexterity",
                    "Starting Money", "Starting Experience", "Type"
            );
            for (Heroes h : heroInstance.heroes) {
                List<String> oneHero = h.getHeroDetails();
                System.out.printf(
                        "%-6d %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                        k++,
                        oneHero.get(0),
                        oneHero.get(1),
                        oneHero.get(2),
                        oneHero.get(3),
                        oneHero.get(4),
                        oneHero.get(5),
                        oneHero.get(6),
                        oneHero.get(7)
                );
            }

            String[] characterInp = {"Please Enter the index of your hero"};

            int in;
            while (true) {
                try {
                    in = Integer.parseInt(printer.getInput(characterInp));
                    if (in < 1 || in >= k) {
                        printer.displayMsgs(invalid);
                        continue;
                    }
                    List<String> chHero=heroInstance.heroes.get(in-1).getHeroDetails();
                    boolean flag=false;
                    for(HMChosenHero h : chosenHeroes){
                        if(chHero.get(0).equals(h.name)){
                            System.out.println("You cannot select the same hero twice");
                            flag=true;
                            break;
                        }
                    }
                    if(flag){
                        continue;
                    }
                    HMChosenHero chosenHero = new HMChosenHero(
                            heroInstance.heroes.get(in-1),
                            chHero.get(0),
                            Integer.parseInt(chHero.get(1)),
                            Integer.parseInt(chHero.get(2)),
                            Integer.parseInt(chHero.get(3)),
                            Integer.parseInt(chHero.get(4)),
                            Integer.parseInt(chHero.get(5)),
                            Integer.parseInt(chHero.get(6)),
                            chHero.get(7)
                    );
                    chosenHeroes.add(chosenHero);
                    System.out.println("Your Hero "+(i+1)+" is: "+chosenHero.name);
                    break;
                } catch (Exception e) {
                    printer.displayMsgs(invalid);
                }
            }
        }

        HMGamePlayer newPlayer=new HMGamePlayer(userName,chosenHeroes,marketSpaceDealing.getInitialWeapon());
//        System.out.println("Name of Selected Hero:"+heroInstance.heroes.get(in).getHeroName());
        players.add(newPlayer);

    }

    private void initBoard() {
        Scanner sc = new Scanner(System.in);
        String[][] boardInitMsgs = {
                {"Please enter the Size of the world (Greater than or equal to 8):"}
        };
        String[] invalidInputMsg = {
                "Invalid Input! Please Try Again...\n"
        };
        int m=0;
        int k=0;

        while(k<1){
            try {
                System.out.println(boardInitMsgs[k][0]);


                m=Integer.parseInt(sc.nextLine());
                if(m<8){
                    System.out.println("Please enter a value greater than or equal to 8");
                    continue;
                }


            } catch (Exception e){
                printer.displayMsgs(invalidInputMsg);
                continue;
            }
            k++;
        }
        int sizex = m;
        int sizey = m;
        System.out.println("Initializing the board");
        this.board = new HMGameBoard(sizex, sizey);

    }

    public int spinDice(){
        Random random=new Random();
        int randomNumber = 1 + random.nextInt(6);
        return randomNumber;
    }

    public List<Monsters> spawnMonsters(){
        List<Monsters> spMonsters=new ArrayList<>();
        List<Monsters> superSetMonsters=new ArrayList<>();
        int allowedLevel=players.get(0).getMaxHeroLevel();
        while(true) {
            for (int i = 0; i < monstersInstance.monsters.size(); i++) {
                if (Integer.parseInt(monstersInstance.monsters.get(i).getLevel()) <= allowedLevel) {
                    spMonsters.add(monstersInstance.monsters.get(i));
                }
            }
            if (spMonsters.size() >= players.size() || allowedLevel>10) {
                return spMonsters;
            }
            else{
                allowedLevel++;
                spMonsters.clear();
            }
        }
    }
    public boolean checkWin(){
        int defMon=players.get(0).getNumDefeatedMonsters();
        if(defMon==monstersInstance.monsters.size()){
            return true;
        }
        else {
            return false;
        }

    }

}
