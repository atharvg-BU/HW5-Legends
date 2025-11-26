package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.*;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HMChosenHero implements Cloneable {
//    Class to create and store instance of chosen heroes
    public String name;
    public double health;
    public int mana;
    public int agility;
    public int strength;
    public int money;
    public int experience;
    public int dexterity;
    public String heroType;
    public int level;
    public int defense;
    public boolean fainted;
    public List<Weapon> weapons;
    public List<Armory> armor;
    public List<Spell> spells;
    public List<Portion> potions;
    public Weapon oneHand;
    public Object otherHand;
    public HMGamePrinter printer;
    public Heroes refHero;

    public HMChosenHero(Heroes ref,String name, int mana, int strength, int agility, int dexterity, int money, int experience, String heroType ) {
        this.heroType = heroType;
        this.name = name;
        this.strength = strength;
        this.level=experience/3;
        this.health = level*100;
        this.mana = mana;
        this.agility = agility;
        this.dexterity = dexterity;
        this.money = money;
        this.experience = experience;
        this.fainted = false;
        this.defense=0;
        weapons = new ArrayList<>();
        armor = new ArrayList<>();
        spells = new ArrayList<>();
        potions = new ArrayList<>();
        printer = new HMGamePrinter();
        refHero = ref;
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: This function enables heroes to equip the weapon they want to fight monsters
     */
    public void equipWeapon(){
        String[] weaponEqp={"Enter the weapon you want to equip"};
        System.out.printf(
                "%-6s %-22s %-8s %-10s%n",
                "Index", "Name","Damage", "Required Hands"
        );
        int k=1;
        for(Weapon w:weapons){
            System.out.printf(
                    "%-6d %-22s %-8s %-10s%n",
                    k++,
                    w.name,
                    w.damage,
                    w.hands
            );
        }
        do{
            String inp=printer.getInput(weaponEqp);
            try{
                int index=Integer.parseInt(inp);
                if(index<0 && index>weapons.size()){
                    System.out.println("Invalid index");
                    continue;
                }
                oneHand=weapons.get(index-1);
                if(Integer.parseInt(weapons.get(index-1).hands)==2){
                    otherHand=weapons.get(index-1);
                }
                else if(Integer.parseInt(weapons.get(index-1).hands)==1){
                    otherHand=null;
                }
                break;

            }
            catch (Exception e){
                System.out.println(e.getMessage());
                System.out.println("Please Enter a valid Input!");
            }
        }while(true);
    }

    public void equipArmor(){
        if(armor.isEmpty()){
            System.out.println("No armor");
            return;
        }
        if(otherHand!=null && (otherHand instanceof Weapon)){
            System.out.println("You cannot equip an armor since you have a weapon that requires 2 hands");
            return;
        }
        String[] weaponEqp={"Enter the Armor you want to equip"};
        System.out.printf(
                "%-6s %-22s %-12s%n",
                "Index", "Name","Damage Reduction"
        );
        int k=1;
        for(Armory w:armor){
            System.out.printf(
                    "%-6d %-22s %-12s%n",
                    k++,
                    w.name,
                    w.damageReduction
            );
        }
        do{
            String inp=printer.getInput(weaponEqp);
            try{
                int index=Integer.parseInt(inp);
                if(index<0 && index>weapons.size()+1){
                    System.out.println("Invalid index");
                    continue;
                }
                otherHand=armor.get(index-1);
                break;

            }
            catch (Exception e){
                System.out.println("Please Enter a valid Input!");
            }
        }while(true);
    }

    /*
    Input: HMBattleMover variable to store information about heroes turn in battle
    Outputs: HMBattleMove type giving information on heroes turn in battle
    Function: Function to enable hero to take their turn by either in a battle
     */
    public HMBattleMove takeBattleTurn(HMBattleMove battleMove){
        String[] input={"Enter 1 to Attack using Weapon","Enter 2 to use a Spell","Enter 3 to use a Portion","Enter 4 to equip another weapon","Enter 5 to see Hero Stats"};
        do{

            try {
                String inp = printer.getInput(input);
                if (inp.length() == 0) {
                    System.out.println("Please enter a valid input");
                    continue;
                }
                System.out.println(inp);
                int in = Integer.parseInt(inp);
                System.out.println(in);
                if (in < 1 || in > 5) {
                    System.out.println("Please Enter a Valid Input");
                    continue;
                }

                if (in == 1) {
                    if (oneHand == null) {
                        System.out.println("Please Equip an weapon to attack");
                        continue;
                    }
                    battleMove.hpDamage = (strength+Integer.parseInt(oneHand.damage))*0.05;

                    break;
                }
                if(in==2){
                    if(spells.size()==0){
                        System.out.println("You have no Spells");
                        continue;
                    }
                    System.out.println("Enter the Spell you want to use");
                    System.out.println("Spells:");
                    System.out.printf(
                            "%-6s %-22s %-12s %-12s%n",
                            "Index", "Name", "Damage", "Mana Cost"
                    );
                    int k=0;
                    for(Spell s:spells){
                        System.out.printf("%-6s %-22s %-12s %-12s%n",
                                k++,
                                s.name,
                                s.damage,
                                s.mana);
                    }
                    String spellIn=printer.getInput(new String[]{"Enter the index of Spell you want to use"});
                    int sIn=Integer.parseInt(spellIn);

                    if(sIn<1 || sIn>spells.size()){
                        System.out.println("Please Enter a Valid Index");
                        continue;
                    }
                    Spell chosenSpell=spells.get(sIn-1);
                    if(Integer.parseInt(chosenSpell.mana)>mana){
                        System.out.println("You do not have enough mana to cast that spell");
                        continue;
                    }
                    battleMove.hpDamage=Integer.parseInt(chosenSpell.damage);
                    mana-=Integer.parseInt(chosenSpell.mana);
                    break;
                }

                if (in == 3) {
                    if (potions.size() == 0) {
                        System.out.println("You have no Potions");
                        continue;
                    }
                    System.out.println("Enter the portion you want to use");
                    System.out.println("Portions:");
                    System.out.printf(
                            "%-6s %-22s %-12s %-30s%n",
                            "Index", "Name", "Attribute Increase", "Attribute Affected"
                    );
                    int k=1;
                    for(Portion p:potions){
                        System.out.printf("%-6s %-22s %-12s %-12s%n",
                                k++,
                                p.portionName,
                                p.attributeIncrease,
                                p.attributeAffected);
                    }
                    String portionIn = printer.getInput(new String[]{"Enter the index of portion you want to use"});
                    int pIn = Integer.parseInt(portionIn);
                    Portion pUsed = potions.get(pIn - 1);
                    String attributesAff = pUsed.attributeAffected.trim();
                    String[] attAff = attributesAff.split("/");
                    for (int i = 0; i < attAff.length; i++) {
                        attAff[i] = attAff[i].trim();
                        if (attAff[i].equals("Health")) {
                            health += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("Mana")) {
                            mana += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("Strength")) {
                            strength += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("Dexterity")) {
                            dexterity += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("Agility")) {
                            agility += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("defense")) {
                            defense += Integer.parseInt(pUsed.attributeIncrease);
                        }
                        if (attAff[i].equals("All")) {
                            health += Integer.parseInt(pUsed.attributeIncrease);
                            mana += Integer.parseInt(pUsed.attributeIncrease);
                            strength += Integer.parseInt(pUsed.attributeIncrease);
                            agility += Integer.parseInt(pUsed.attributeIncrease);
                            defense += Integer.parseInt(pUsed.attributeIncrease);
                            dexterity += Integer.parseInt(pUsed.attributeIncrease);
                            break;
                        }
                    }
                    System.out.println("Hero "+name+" just used portion "+potions.get(pIn-1).portionName+"increasing attributes "+potions.get(pIn-1).attributeAffected+" by "+potions.get(pIn-1).attributeIncrease);
                    potions.remove(pIn - 1);
                    battleMove.selfHeal = true;
                    break;
                }
                if(in == 4){
                    equipWeapon();
                    equipArmor();
                    System.out.println("Hope you have chosen your weapon and armor! Now proceed to take your turn");
                    continue;
                }
                if(in == 5){
                    displayHeroStats();
                    continue;
                }
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Exception! Invalid Input!");
            }
        }while(true);
        return battleMove;
    }

    /*
    Input: N/A
    Outputs: N/A
    Function: Function to enable hero to take their turn by either moving their player/consuming portion or seeing their stats
     */
    public void displayHeroStats(){
        System.out.printf(
                "%-15s %-10s %-10s %-10s %-10s %-10s %-12s %-12s %-10s %-12s%n",
                "Hero","Health", "Mana", "Strength", "Agility", "Dexterity",
                "Money", "Experience","Level", "Type"
        );

        System.out.printf(
                "%-15s %-10s %-10s %-10s %-10s %-10s %-12s %-12s %-10s %-12s%n",
                name,
                health,
                mana,
                strength,
                agility,
                dexterity,
                money,
                experience,
                level,
                heroType
        );
        System.out.println("Weapons with Hero:");
        System.out.printf(
                "%-6s %-22s %-8s %-10s%n",
                "Index", "Name","Damage", "Required Hands"
        );
        int k=1;
        for(Weapon w:weapons){
            System.out.printf(
                    "%-6d %-22s %-8s %-10s%n",
                    k++,
                    w.name,
                    w.damage,
                    w.hands
            );
        }
        if(armor.size()==0){
            System.out.println("You Have No Armor");
        }
        else {
            System.out.println("Armors with Hero:");
            System.out.printf(
                    "%-6s %-22s %-12s%n",
                    "Index", "Name", "Damage Reduction"
            );
            k = 1;
            for (Armory w : armor) {
                System.out.printf(
                        "%-6d %-22s %-12s%n",
                        k++,
                        w.name,
                        w.damageReduction
                );
            }
        }

        if(spells.size()==0){
            System.out.println("You Have no Spells currently");
        }
        else {
            System.out.println("Spells with Hero:");
            System.out.printf(
                    "%-6s %-22s %-12s %-12s %-12s%n",
                    "Index", "Name", "Type","Damage", "Mana Cost"
            );
            k = 1;
            for (Spell w : spells) {
                System.out.printf(
                        "%-6s %-22s %-12s %-12s%n",
                        k++,
                        w.name,
                        w.type,
                        w.damage,
                        w.mana
                );
            }
            System.out.println("Potions with Hero:");
        }

        if(potions.size()==0){
            System.out.println("You Have no Portions currently");
        }
        else {
            System.out.printf(
                    "%-6s %-22s %-12s %-12s%n",
                    "Index", "Name", "Attribute Increase", "Attribute Affected"
            );

            k = 1;
            for (Portion w : potions) {
                System.out.printf(
                        "%-6s %-22s %-12s %-12s%n",
                        k++,
                        w.portionName,
                        w.attributeIncrease,
                        w.attributeAffected
                );
            }
        }


    }

    /*
    Input: GameMove storing information on the move when hero consumes a portion outside the battle
    Outputs: N/A
    Function: Function to enable hero to take portion outside the battle
     */
    public void consumePortion(HMGameMove move){
        if (potions.size() == 0) {
            System.out.println("You have no Potions");
            return;
        }
        System.out.println("Portions:");
        System.out.printf(
                "%-6s %-22s %-30s %-12s %-12s%n",
                "Index", "Name", "Attribute Increase", "Attribute Affected","Units"
        );
        int k=1;
        for(int i=0;i<potions.size();i++){
            int units=0;
            boolean rep=false;
            for(int j=0;j<potions.size();j++){
                if(potions.get(i).portionName.equals(potions.get(j).portionName)){
                    if(j<i){
                        rep=true;
                        break;
                    }
                    units++;
                }

            }
            if(rep){
                continue;
            }
            System.out.printf("%-6s %-22s %-30s %-12s %-12s%n",
                    k++,
                    potions.get(i).portionName,
                    potions.get(i).attributeIncrease,
                    potions.get(i).attributeAffected,
                    units
                    );
        }
        do {
            String portionIn = printer.getInput(new String[]{"Enter the index of portion you want to use"});
            try {
                int pIn = Integer.parseInt(portionIn);

                if (pIn < 0 || pIn > k) {
                    System.out.println("Invalid index");
                    continue;
                }
                Portion pUsed = potions.get(pIn - 1);
                String attributesAff = pUsed.attributeAffected.trim();
                String[] attAff = attributesAff.split("/");
                for (int i = 0; i < attAff.length; i++) {
                    attAff[i] = attAff[i].trim();
                    if (attAff[i].equals("Health")) {
                        if (health + Integer.parseInt(pUsed.attributeIncrease) > level * 100) {
                            health = level * 100;
                        } else {
                            health += Integer.parseInt(pUsed.attributeIncrease);
                        }
                    }
                    if (attAff[i].equals("Mana")) {
                        mana += Integer.parseInt(pUsed.attributeIncrease);
                    }
                    if (attAff[i].equals("Strength")) {
                        strength += Integer.parseInt(pUsed.attributeIncrease);
                    }
                    if (attAff[i].equals("Dexterity")) {
                        dexterity += Integer.parseInt(pUsed.attributeIncrease);
                    }
                    if (attAff[i].equals("Agility")) {
                        agility += Integer.parseInt(pUsed.attributeIncrease);
                    }
                    if (attAff[i].equals("defense")) {
                        defense += Integer.parseInt(pUsed.attributeIncrease);
                    }
                    if (attAff[i].equals("All")) {
                        health += Integer.parseInt(pUsed.attributeIncrease);
                        mana += Integer.parseInt(pUsed.attributeIncrease);
                        strength += Integer.parseInt(pUsed.attributeIncrease);
                        agility += Integer.parseInt(pUsed.attributeIncrease);
                        defense += Integer.parseInt(pUsed.attributeIncrease);
                        dexterity += Integer.parseInt(pUsed.attributeIncrease);
                        break;
                    }
                }
                potions.remove(pIn - 1);
                break;
            }
            catch (Exception e) {
                System.out.println("Invalid input!");
            }
        }while (true);
    }

    /*
    Input: N/A
    Outputs: Cloned object of HMChosenHero class
    Function: Function to clone a hero to avoid lengthy process of new object creation
     */
    @Override
    public HMChosenHero clone() {
        try {
            return (HMChosenHero) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    Input: Initial values to be set to global variables of cloned objects
    Outputs: N/A
    Function: Function to set the initial values of global variables when object is cloned
     */
    public void setValues(Heroes ref,String name, int mana, int strength, int agility, int dexterity, int money, int experience, String heroType){
        this.heroType = heroType;
        this.name = name;
        this.strength = strength;
        this.level=experience/3;
        this.health = level*100;
        this.mana = mana;
        this.agility = agility;
        this.dexterity = dexterity;
        this.money = money;
        this.experience = experience;
        this.fainted = false;
        this.defense=0;
        weapons = new ArrayList<>();
        armor = new ArrayList<>();
        spells = new ArrayList<>();
        potions = new ArrayList<>();
        printer = new HMGamePrinter();
        refHero = ref;
    }
}
