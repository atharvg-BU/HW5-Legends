package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.*;
import Parent.GameMove;
import Parent.GameUser;

import java.util.*;

public class HMGamePlayer extends GameUser {
    public List<HMChosenHero> chosenHeroes;
    public Weapon oneHand;
    public Armory otherHand;
    public boolean quit;
    private HMGamePrinter printer;
    public List<String> defeatedMonsters;
    public MarketSpaceDealing market;
    public HMGamePlayer(String username, List<HMChosenHero> chosenHeroes, Weapon initialWeapon) {
        super(username);
        this.chosenHeroes = chosenHeroes;
        this.quit = false;
        this.defeatedMonsters = new ArrayList<>();
        printer=new HMGamePrinter();
        chosenHeroes.get(0).weapons.add(initialWeapon);
    }

    public GameMove takeTurn() {
        HMGameMove move=new HMGameMove();
        move.playerN=getUsername();
//        String[] turnMsgs={"Enter 1 to Move your Hero","Enter 2 to see your Stats"};
        String[] moveTurnMsgs = {"Enter W/w to move Up","Enter A/a to move Left","Enter S/s to move Down","Enter D/d to move Right","Enter I/i to see your hero stats","Enter P/p to consume Portion"};
        String[] mktMsg={"You have landed on a Market Place Block?","Do you want to enter the market place to Buy/Sell items","Enter M/m to Enter the Market Place","Enter N/n to Pass"};
        String[] invalidInputMsgs = {"Please enter a valid input..."};
        do{
            try {
                String inp=printer.getInput(moveTurnMsgs);
                if(inp.length()<1){
                    printer.displayMsgs(invalidInputMsgs);
                }
                if(inp.equalsIgnoreCase("q")){
                    move.quit=true;
                    break;
                }
                if(inp.equalsIgnoreCase("w")){
                    move.direction="Up";
                    break;
                }
                if(inp.equalsIgnoreCase("a")){
                    move.direction="Left";
                    break;
                }
                if(inp.equalsIgnoreCase("d")){
                    move.direction="Right";
                    break;
                }
                if(inp.equalsIgnoreCase("s")){
                    move.direction="Down";
                    break;
                }
                if(inp.equalsIgnoreCase("i")){
                    move.direction="Stats";
                    displayStats();
                    break;
                }
                if(inp.equalsIgnoreCase("p")){
                    move.direction="PortionConsume";
                    portionConsume(move);
                }
                printer.displayMsgs(invalidInputMsgs);
            }
            catch(Exception e) {
               printer.displayMsgs(invalidInputMsgs);
            }

        }while(true);
        return move;
    }

    public void portionConsume(HMGameMove move){
        String[] heroCh={"Enter the hero you to consume Portion","Enter 0 to go back"};
        displayStats();
        do{
            try {
                String inp=printer.getInput(heroCh);
                int in=Integer.parseInt(inp);
                if(in==0){
                    System.out.println("Hope you have consumed the portion");
                    return;
                }
                if(in<1 && in>chosenHeroes.size()){
                    System.out.println("Please enter a number between 1 and "+chosenHeroes.size());
                    continue;
                }
                HMChosenHero chosenHero=chosenHeroes.get(in-1);
                chosenHero.consumePortion(move);

            }
            catch (Exception e) {}
        }while (true);
    }

    public void displayStats(){
        System.out.println("Stats of All your Heroes");
        System.out.printf(
                "%-6s %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                "Index", "Hero", "Mana", "Strength", "Agility", "Dexterity",
                "Starting Money", "Starting Experience", "Type"
        );
        int k=1;
        for(HMChosenHero h:chosenHeroes){
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                    k++,
                    h.name,
                    h.mana,
                    h.strength,
                    h.agility,
                    h.dexterity,
                    h.money,
                    h.experience,
                    h.heroType

            );
        }
    }

    public int getMaxHeroLevel(){
        int maxHeroLevel=0;
        for(int i=0;i<chosenHeroes.size();i++){
            if(chosenHeroes.get(i).level>maxHeroLevel){
                maxHeroLevel=chosenHeroes.get(i).level;
            }
        }
        return maxHeroLevel;
    }

    public boolean mktMove(MarketSpaceDealing marketSpaceDealing){
        String[] st=new  String[chosenHeroes.size()];
        for (int i = 0; i < chosenHeroes.size(); i++) {
            st[i]=("Enter " + i + " if you wish to enter the market with Hero " + chosenHeroes.get(i).name);
        }
        do {
            String inp=printer.getInput(st);
            try{
                int in =Integer.parseInt(inp);
                if(in<1 || in>chosenHeroes.size()){
                    System.out.println("Please enter a number between 1 and "+chosenHeroes.size()+".");
                    continue;
                }
                HMMarketGameMove move=new HMMarketGameMove();
                marketSpaceDealing.mktEnter(move,chosenHeroes.get(in));
                break;
            }
            catch(Exception e){
                System.out.println("Please enter a valid input...");
                continue;
            }
        }while (true);

        return true;
    }

//    public HMBattleMove takeBattleTurn(List<MonsterSpawn> monsters){
//        return  new HMBattleMove();
//    }
    public boolean wonBattleTurn(List<MonsterSpawn> monsterSpawns){
        for(HMChosenHero h:chosenHeroes){
            if(h.fainted){
                System.out.println("Reviving Hero "+h.name);
                h.money=h.money/2;
                h.mana=h.mana/2;
                h.level=h.experience/2;
            }
            else{
                for(MonsterSpawn m:monsterSpawns){
                    h.money+=m.level*100;
                }
                h.experience+=monsterSpawns.size()*2;
                int newLvl=h.experience/3;
                if(newLvl>h.level){
                    System.out.println("Hero "+h.name+" has leveled Up!!!");
                    h.level=newLvl;
                    h.money+=h.level*50;
                    h.mana+=h.level*5;
                    h.health=h.level*100;
                }
                else{
                    h.health=h.level*100;
                }
            }

        }
        return  false;
    }

    public int getNumDefeatedMonsters(){
        return defeatedMonsters.size();
    }

}
