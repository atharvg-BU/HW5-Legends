package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.Armory;
import MonstersHeroesGame.Data.MainData.Heroes;

import java.sql.SQLOutput;
import java.util.*;

public class HMBattle {
//    Class to handle the battle in a common space between heroes and monsters
    List<HMChosenHero> heroes;
    List<MonsterSpawn> monsters;
    HMGamePrinter printer;
    public String battleWinner;
    public boolean quit;
    HMBattle(List<HMChosenHero> heroes, List<MonsterSpawn> monsters){
        this.heroes=heroes;
        this.monsters=monsters;
        this.printer=new HMGamePrinter();
        this.battleWinner="";
        this.quit=false;
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Function to do the basic printing when a battle starts between heroes and monsters
     */
    public void startPrint(){
        System.out.print("Battle is between ");
        for(HMChosenHero h:heroes){
            System.out.print(h.name+" ");
        }
        System.out.print("And ");
        for(MonsterSpawn m:monsters){
            System.out.print(m.name+" ");
        }
        System.out.println();
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Function to start the battle between heroes and monsters
     */
    public void battleStart(){
        startPrint();
        String[] monsterAttack={"Please select which Monster you want to attack"};
        String[] invalid={"Please Enter a Valid Input"};
        do{
            System.out.println("HP of Heroes:");
            System.out.printf("%-22s %-8s%n",
                    "Hero Name","Hero Health");
            for(HMChosenHero h:heroes){
                System.out.printf("%-22s %-8s%n",h.name,h.health);
            }
            for(HMChosenHero h:heroes){
                if(h.fainted){
                    System.out.println(h.name+" is already fainted");
                    continue;
                }
                if(h.weapons.size()==0 && h.potions.size()==0 && h.spells.size()==0){
                    System.out.println("Hero "+h.name+" has nothing to attack the monster with");
                    continue;
                }
                System.out.println("Hero "+h.name+" its is your turn!");

                System.out.printf(
                        "%-6s %-22s %-8s %-10s %-10s%n",
                        "Index", "Monster Name", "Health", "Dodge Chance", "Defeated"
                );
                int mIndex=1;
                for(MonsterSpawn m:monsters){
                    System.out.printf(
                            "%-6s %-22s %-8s %-10s %-10s%n",
                            mIndex++,
                            m.name,
                            m.hp,
                            m.dodgeChance,
                            m.defeated
                    );
                }
                do {
                    String inp = printer.getInput(monsterAttack);
                    if (inp.equalsIgnoreCase("q")) {
                        quit = true;
                        return;
                    }

                    try {
                        int in = Integer.parseInt(inp);
                        if (in < 1 || in > monsters.size()) {
                            System.out.println("Please enter a Valid Input");
                            continue;
                        }
                        if (monsters.get(in - 1).defeated) {
                            System.out.println("Monster " + monsters.get(in - 1) + " is already defeated...");
                            continue;
                        }
                        HMBattleMove battleMv = new HMBattleMove();
                        battleMv.opponent = monsters.get(in - 1).name;
                        System.out.println(battleMv.opponent);
                        battleMv = h.takeBattleTurn(battleMv);
                        if (!battleMv.selfHeal && !dodge(monsters.get(in - 1).dodgeChance / 100.0)) {
                            monsters.get(in - 1).hp -= (battleMv.hpDamage - (monsters.get(in - 1).defense / 100.0));
                            System.out.println("Hero " + h.name + " attacked monster resulting in damage of " + (battleMv.hpDamage - (monsters.get(in - 1).defense / 100.0)));
                            if (monsters.get(in - 1).hp < 0) {
                                System.out.println("Hero " + h.name + " has defeated monster " + monsters.get(in - 1).name);
                                monsters.get(in - 1).hp = 0;
                                monsters.get(in - 1).defeated = true;
                            }
                        } else {
                            if (battleMv.selfHeal) {
                                System.out.println("You have self healed");
                            } else {
                                System.out.println("Monster " + battleMv.opponent + "  has dodged your attack...");
                            }
                        }
                        break;
                    } catch (Exception e) {
                        printer.displayMsgs(invalid);
                    }
                }while (true);
                boolean flag=false;
                for(MonsterSpawn m:monsters){
                    if(!m.defeated){
                        flag=true;
                    }
                }
                if(!flag){
                    System.out.println("All Monsters Have Been Defeated");
                    System.out.println("Heroes Win the battle");
                    battleWinner="Heroes";
                    return ;
                }
            }
            System.out.println("Now its monsters Turn to Attack");
            for(MonsterSpawn m:monsters){
                System.out.println(m.name+" is your turn!");
                HMBattleMove battleMv=new HMBattleMove();
                double minHp=heroes.get(0).health;
                int minInd=0;
                HMChosenHero chosenHero=heroes.get(0);
                for(int i=0;i<heroes.size();i++){
                    if(heroes.get(i).health>0 && heroes.get(i).health<minHp){
                        minHp=heroes.get(i).health;
                        minInd=i;
                        chosenHero=heroes.get(i);
                    }
                }
                battleMv.opponent=heroes.get(minInd).name;
                if(dodge(chosenHero.agility*0.0005)){
                    System.out.println("Hero "+chosenHero.name+ " dodged "+m.name+"'s attack");
                }
                else{
                    battleMv=m.takeBattleTurn(battleMv);
                    if(chosenHero.otherHand!=null && chosenHero.otherHand instanceof Armory) {
                        chosenHero.health -= ((battleMv.hpDamage / 10.0) - 10 - (Double.parseDouble(((Armory) chosenHero.otherHand).damageReduction) /100.0));
                        System.out.println("Monster "+m.name+" has attacked hero "+battleMv.opponent+" resulting in damage of "+((battleMv.hpDamage / 10.0) - 10 - (Double.parseDouble(((Armory) chosenHero.otherHand).damageReduction) /100.0)));
                    }
                    else{
                        chosenHero.health -= ((battleMv.hpDamage / 10.0) - 10);
                        System.out.println("Monster "+m.name+" has attacked hero "+battleMv.opponent+" resulting in damage of "+ (((battleMv.hpDamage / 10.0) - 10)));
                    }

                    if(chosenHero.health<=0){
                        System.out.println(chosenHero.name+" has fainted");
                        chosenHero.fainted=true;
                    }
                }

                boolean flag=false;
                for(HMChosenHero h:heroes){
                    if(!h.fainted){
                        flag=true;
                    }
                }
                if(!flag){
                    System.out.println("All Heroes Have Been Defeated");
                    System.out.println("Monsters Win the battle");
                    battleWinner="monsters";
                    return;
                }
            }
        }while (true);

    }

    /*
    Input: Dodge Chance of a Hero/Monster
    Outputs: Boolean value telling if the character has successfully dodged opponents attack or not
    Function: Function to return True/False based on whether the character has dodged its opponent's attack
     */
    public boolean dodge(double dodgeChance){
        double r=Math.random();
        if(r<=(dodgeChance)){
            return true;
        }
        else{
            return false;
        }
    }

}
