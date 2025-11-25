package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.Heroes;

import java.util.*;

public class HMBattle {
    List<HMChosenHero> heroes;
    List<MonsterSpawn> monsters;
    HMGamePrinter printer;
    public String battleWinner;
    HMBattle(List<HMChosenHero> heroes, List<MonsterSpawn> monsters){
        this.heroes=heroes;
        this.monsters=monsters;
        this.printer=new HMGamePrinter();
    }

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
    public boolean battleStart(){
        startPrint();
        String[] monsterAttack={"Please select which Monster you want to attack"};
        String[] invalid={"Please Enter a Valid Input"};
        do{
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
                String inp=printer.getInput(monsterAttack);
                try {
                    int in=Integer.parseInt(inp);
                    if(in<1 || in>monsters.size()){
                        System.out.println("Please enter a Valid Input");
                        continue;
                    }
                    if(monsters.get(in-1).defeated){
                        System.out.println("Monster "+monsters.get(in-1)+" is already defeated...");
                        continue;
                    }
                    HMBattleMove battleMv=new HMBattleMove();
                    battleMv.opponent=monsters.get(in-1).name;
                    System.out.println(battleMv.opponent);
                    battleMv=h.takeBattleTurn(battleMv);
                    if(!battleMv.selfHeal && !dodge(monsters.get(in-1).dodgeChance/100.0)){
                        monsters.get(in-1).hp-= battleMv.hpDamage;
                    }
                    else{
                        if(battleMv.selfHeal){
                            System.out.println("You have self healed");
                        }
                        else{
                            System.out.println("Monster "+battleMv.opponent+"  has dodged your attack...");
                        }
                    }
                }
                catch (Exception e){
                    printer.displayMsgs(invalid);
                }
            }

            for(MonsterSpawn m:monsters){
                HMBattleMove battleMv=new HMBattleMove();
                double minHp=heroes.get(0).health;
                int minInd=0;
                HMChosenHero chosenHero=heroes.get(0);
                for(int i=0;i<heroes.size();i++){
                    if(heroes.get(i).health<minHp){
                        minHp=heroes.get(i).health;
                        minInd=i;
                        chosenHero=heroes.get(i);
                    }
                }
                battleMv.opponent=heroes.get(minInd).name;
                battleMv.hpDamage=m.damage;
                if(dodge(chosenHero.agility*0.002)){
                    System.out.println("Hero"+chosenHero.name+ " dodged your attack");
                }
                else{
                    chosenHero.health-= (battleMv.hpDamage/100.0)+10;
                    if(chosenHero.health<=0){
                        System.out.println(chosenHero.name+" has fainted");
                        chosenHero.fainted=true;
                    }
                }


            }
        }while (true);
    }
    public boolean dodge(double dodgeChance){
        double r=Math.random();
        if(r>=(dodgeChance)){
            return true;
        }
        else{
            return false;
        }
    }

}
