package MonstersHeroesGame;

import MonstersHeroesGame.Data.MainData.*;
import MonstersHeroesGame.Data.StoreData.ArmorySingleton;
import MonstersHeroesGame.Data.StoreData.PortionSingleton;
import MonstersHeroesGame.Data.StoreData.SpellsSingleton;
import MonstersHeroesGame.Data.StoreData.WeaponsSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MarketSpaceDealing {
    public ArmorySingleton armory;
    public PortionSingleton portion;
    public WeaponsSingleton weapons;
    public SpellsSingleton spells;
    public HMGamePrinter printer;

    public List<Weapon> weaponsAll;
    public List<Spell> spellsAll;
    public List<Armory> armoryAll;
    public List<Portion> portionAll;
    public MarketSpaceDealing(){
        armory = ArmorySingleton.getInstance();
        portion = PortionSingleton.getInstance();
        weapons = WeaponsSingleton.getInstance();
        spells = SpellsSingleton.getInstance();
        printer = new HMGamePrinter();
        this.weaponsAll = new ArrayList<>();
        this.spellsAll = new ArrayList<>();
        this.armoryAll = new ArrayList<>();
        this.portionAll = new ArrayList<>();

    }

    public void mktInitialize(){
        for(Armory a : armory.armoryAll){
            this.armoryAll.add(a);
        }
        for(Weapon w : weapons.weapons){
            this.weaponsAll.add(w);
        }
        weaponsAll.remove(0);

        for(Spell s : spells.spells){
            this.spellsAll.add(s);
        }

        for(Portion p : portion.portionData){
            this.portionAll.add(p);
        }

    }

    public Weapon getInitialWeapon(){
        return weapons.weapons.get(0);
    }

    public void mktEnter(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] buySellMsgs={"Welcome to the Market!","Here you can Buy/Sell Items","Enter B/b to Buy an Item","Enter S/s to Sell an Item","Enter N/n to Exit MarketSpace"};
        do {
            String buySell = printer.getInput(buySellMsgs);
            if(buySell.equalsIgnoreCase("q")){
                mktMove.quit=true;
                return;
            }
            if(mktMove.leaveMkt){
                System.out.println("Leaving the Market! Thank you for Shopping");
            }
            if (buySell.length()==0){
                System.out.println("Enter a valid Input ...");
                continue;
            }
            if(buySell.equalsIgnoreCase("B")){
                mktBuy(mktMove, hero);
            }
            if(buySell.equalsIgnoreCase("S")){
                mktSell(mktMove, hero);
            }
            if(buySell.equalsIgnoreCase("N")){
                mktMove.leaveMkt=true;
                break;
            }
        }while (true);
        if(mktMove.quit){
            return;
        }
    }

    public void mktBuy(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] buyMsg={"What can we Get you today?","Enter 1 to buy Weapons","Enter 2 to buy Potions","Enter 3 to buy Spells","Enter 4 to buy Armor", "Enter 0 to go back","Enter N/n to go exit the market"};
        do {
            if(mktMove.leaveMkt){
                return;
            }
            String inp = printer.getInput(buyMsg);
            if(inp.equalsIgnoreCase("q")){
                mktMove.quit=true;
                return;
            }
           if(inp.equalsIgnoreCase("n")){
               mktMove.leaveMkt=true;
               return;
           }
            int in;
            try {
                in = Integer.parseInt(inp);
                switch(in){
                    case 1:
                        buyWeapon(mktMove,hero);
                        break;
                    case 2:
                        buyArmor(mktMove,hero);
                        break;
                    case 3:
                        buySpell(mktMove,hero);
                        break;
                    case 4:
                        buyPortion(mktMove,hero);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid Input");
                        continue;
                }
            } catch (Exception e) {
                System.out.println("Please enter a valid Input ...");
            }
        }while (true);

    }

    public void buyWeapon(HMMarketGameMove mktMove, HMChosenHero hero){
        if(weaponsAll.size()==0){
            System.out.println("There are no Weapons in this MarketSpace! Your Heroes Own all the Weapons");
            return;
        }
        System.out.printf(
                "%-6s %-22s %-8s %-10s %-10s %-10s%n",
                "Index","Name", "Cost", "Level", "Damage", "Required Hands"
        );
        String[] weaponIn={"Please Enter the index of the Weapon you want to purchase","Enter 0 to go back"};

        int k = 1;
        for (Weapon h : weaponsAll) {
            List<String> oneWeapon = h.getWeaponDetails();
            System.out.printf(
                    "%-6d %-22s %-8s %-10s %-10s %-10s%n",
                    k++,
                    oneWeapon.get(0),
                    oneWeapon.get(1),
                    oneWeapon.get(2),
                    oneWeapon.get(3),
                    oneWeapon.get(4)
            );
        }
        do {
            String in=printer.getInput(weaponIn);
            if(in.equalsIgnoreCase("q")){
                mktMove.quit=true;
                return;
            }
            try{
                int p=Integer.parseInt(in);
                if(p==0){
                    return;
                }
                if(p<1 || p>weaponsAll.size()){
                    System.out.println("Please enter a valid Index ...");
                    continue;
                }
                Weapon selected=weaponsAll.get(p-1);
                if(Integer.parseInt(selected.getCost())>hero.money){
                    System.out.println("You don't have enough money to do that");
                    break;
                }
                else {
                    hero.weapons.add(selected);
                    hero.money-=Integer.parseInt(selected.cost);
                    weaponsAll.remove(selected);
                    break;
                }

            }
            catch (Exception e){
                System.out.println("Please enter a valid Index ...");
            }
        }while (true);
    }

    public void buyArmor(HMMarketGameMove mktMove, HMChosenHero hero){
        if(armoryAll.size()==0){
            System.out.println("There are no Armors in this MarketSpace! Your Heroes Own all the Armors");
            return;
        }
        System.out.printf(
                "%-6s %-22s %-8s %-10s%n",
                "Name", "Cost", "Required Level", "Damage Reduction"
        );
        String[] armoryIn={"Please Enter the index of the Armor you want to purchase"};

        int k = 1;
        for (Armory a : armory.armoryAll) {
            List<String> oneArmor = a.getArmoryDetails();
            System.out.printf(
                    "%-6d %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                    k++,
                    oneArmor.get(0),
                    oneArmor.get(1),
                    oneArmor.get(2),
                    oneArmor.get(3)
            );
        }
        do {
            String in=printer.getInput(armoryIn);
            if(in.equalsIgnoreCase("q")){
                mktMove.quit=true;
                return;
            }
            try{
                int p=Integer.parseInt(in);
                if(p==0){
                    return;
                }
                if(p<1 || p>armoryAll.size()){
                    System.out.println("Please enter a valid Index ...");
                    continue;
                }
                Armory selected=armoryAll.get(p);
                if(Integer.parseInt(selected.cost)>hero.money){
                    System.out.println("You don't have enough money to do that");
                    continue;
                }
                else{
                    hero.armor.add(selected);
                    armoryAll.remove(selected);
                    hero.money-=Integer.parseInt(selected.cost);
                    break;
                }
            }
            catch (Exception e){
                System.out.println("Please enter a valid Index ...");
            }
        }while (true);
    }

    public void buyPortion(HMMarketGameMove mktMove, HMChosenHero hero){
        if(portionAll.size()==0){
            System.out.println("There are no Portions in the marketSpace! Your Heroes Own all the Portions");
        }
        System.out.printf(
                "%-6d %-22s %-8s %-10s %-10s %-12s%n",
                "Index", "Name", "Cost", "Required Level", "Attribute Increase", "Atrributes Affected"
        );
        String[] portionIn={"Please Enter the index of the Portion you want to purchase"};

        int k = 1;
        for (Portion p : portionAll) {
            List<String> onePortion = p.getPortionDetails();
            System.out.printf(
                    "%-6d %-22s %-8s %-10s %-10s %-12s %-18s %-22s %-10s%n",
                    k++,
                    onePortion.get(0),
                    onePortion.get(1),
                    onePortion.get(2),
                    onePortion.get(3),
                    onePortion.get(4)
            );
        }
        do {
            String in=printer.getInput(portionIn);
            if(in.equalsIgnoreCase("quit")){
                mktMove.quit=true;
                return;
            }
            try{
                int p=Integer.parseInt(in);
                if(p<1 || p>portionAll.size()){
                    System.out.println("Please enter a valid Index ...");
                    continue;
                }
                Portion selected=portionAll.get(p-1);

                if(Integer.parseInt(selected.portionCost)>hero.money){
                        System.out.println("You don't have enough money to do that");
                        break;
                    }
                else {
                    hero.potions.add(selected);
                    portionAll.remove(selected);
                    hero.money-=Integer.parseInt(selected.portionCost);
                    break;
                }
            }
            catch (Exception e){
                System.out.println("Please enter a valid Index ...");
            }
        }while (true);
    }

    public void buySpell(HMMarketGameMove mktMove, HMChosenHero hero){
        if(spellsAll.size()==0){
            System.out.println("There are no Spells in the marketSpace! Your Heroes Own all the Spell");
        }
        System.out.printf(
                "%-6s %-22s %-8s %-10s %-10s %-10s %-10s%n",
                "Index", "Name", "Cost", "Required Level", "Damage", "Mana", "Type"
        );
        String[] spellIn={"Please Enter the index of the Spell you want to purchase"};

        int k = 1;
        for (Spell s : spellsAll) {
            List<String> oneSpell = s.getSpellDetails();
            System.out.printf(
                    "%-6d %-22s %-8s %-10s %-10s %-10s %-10s%n",
                    k++,
                    oneSpell.get(0),
                    oneSpell.get(1),
                    oneSpell.get(2),
                    oneSpell.get(3),
                    oneSpell.get(4),
                    oneSpell.get(5)
            );
        }
        do {
            String in=printer.getInput(spellIn);
            if(in.equalsIgnoreCase("quit")){
                mktMove.quit=true;
                return;
            }
            try{
                int p=Integer.parseInt(in);
                if(p<1 || p>spells.spells.size()){
                    System.out.println("Please enter a valid Index ...");
                    continue;
                }
                Spell selected=spells.spells.get(p-1);
                if(Integer.parseInt(selected.cost)>hero.money){
                    System.out.println("You don't have enough money to do that");
                    break;
                }
                else {
                    hero.spells.add(selected);
                    spellsAll.remove(selected);
                    hero.money-=Integer.parseInt(selected.cost);
                    break;
                }

            }
            catch (Exception e){
                System.out.println("Please enter a valid Index ...");
            }
        }while (true);
    }

    public void mktSell(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] sell={"Enter 1 to sell Weapons","Enter 2 to sell Armors","Enter 3 to sell Spells","Enter 4 to sell Portions","Enter N/n to exit the market"};
        do{
            String inp=printer.getInput(sell);
            if(inp.equalsIgnoreCase("quit")){
                mktMove.quit=true;
                return;
            }
            if(inp.equalsIgnoreCase("n")){
                mktMove.leaveMkt=true;
                return;
            }
            int in=Integer.parseInt(inp);
            switch (in){
                case 1:
                    weaponSell(mktMove,  hero);
                    break;
                case 2:
                    armorSell(mktMove, hero);
                    break;
                case 3:
                    spellSell(mktMove, hero);
                    break;
                case 4:
                    portionSell(mktMove,hero);
                    break;
                default:
                        System.out.println("Please enter a valid Input ...");
            }
        }while(true);
    }

    public void weaponSell(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] weaponInd={"Enter the index of the Weapons you want to sell"};
        if(hero.weapons.size()==0){
            System.out.println("You do not have any weapons!");
            return;
        }
        System.out.println("List of your Weapons:");
        System.out.printf(
                "%-6s %-22s %-8s %-10s %-10s %-10s %-10s%n",
                "Index", "Name", "Selling Price", "Level", "Damage","Required Hands"
        );
        int k=0;
        for(Weapon w: hero.weapons){
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s %-10s %-10s%n",
                    k++,
                    w.name,
                    (Integer.parseInt(w.cost)/2),
                    w.level,
                    w.damage,
                    w.hands
            );
        }
        do{
            if(weaponsAll.size()==1){
                System.out.println("You only have one weapon and you cannot sell that");
                break;
            }
            String inp=printer.getInput(weaponInd);
            if(inp.equalsIgnoreCase("q")){
                mktMove.quit=true;
                break;
            }
            if(inp.equalsIgnoreCase("n")){
                mktMove.leaveMkt=true;
                break;
            }
            try{
                int in=Integer.parseInt(inp);
                if(in<1 || in>hero.weapons.size()){
                    System.out.println("Please enter a valid Index ...");
                    continue;
                }
                Weapon selected=hero.weapons.get(in-1);
                weaponsAll.add(selected);
                hero.weapons.remove(selected);
                hero.money+=Integer.parseInt(selected.cost)/2;
            }
            catch (Exception e){
                System.out.println("Please enter a valid Input ...");
                continue;
            }

        }while(true);

    }

    public void spellSell(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] spellId={"Enter the ID of the Spell you want to sell"};
        if(hero.spells.size()==0){
            System.out.println("You do not have any spells!");
        }
        else{
            System.out.println("List of your Spells:");
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s%n",
                    "Index", "Name", "Selling Price", "Level", "Damage"
            );
            int k=0;
            for(Spell s: hero.spells){
                System.out.printf(
                        "%-6s %-22s %-8s %-10s %-10s%n",
                        k++,
                        s.name,
                        (Integer.parseInt(s.cost)/2),
                        s.rqLevel,
                        s.damage
                );
            }

            do{
                String inp=printer.getInput(spellId);
                if(inp.equalsIgnoreCase("q")){
                    mktMove.quit=true;
                    break;
                }
                if(inp.equalsIgnoreCase("n")){
                    mktMove.leaveMkt=true;
                    break;
                }
                try{
                    int in=Integer.parseInt(inp);
                    if(in<1 || in>hero.spells.size()){
                        System.out.println("Please enter a valid Index ...");
                        continue;
                    }
                    Spell s=hero.spells.get(in-1);
                    hero.spells.remove(s);
                    hero.money+=Integer.parseInt(s.cost)/2;
                    spellsAll.add(s);
                    break;
                }
                catch (Exception e){
                    System.out.println("Please enter a valid Input ...");
                    continue;
                }
            }while(true);
        }

    }
    public void armorSell(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] armorId={"Enter the ID of the Spell you want to sell"};
        if(hero.spells.size()==0){
            System.out.println("You do not have any Armors!");
        }
        else{
            System.out.println("List of your Armors:");
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s%n",
                    "Index", "Name", "Selling Price", "Level", "Damage Reduction"
            );
            int k=0;
            for(Armory a:hero.armor){
                System.out.printf(
                        "%-6s %-22s %-8s %-10s %-10s%n",
                        k++,
                        a.name,
                        (Integer.parseInt(a.cost)/2),
                        a.rqLevel,
                        a.damageReduction
                );
            }

            do{
                String inp=printer.getInput(armorId);
                if(inp.equalsIgnoreCase("quit")){
                    mktMove.quit=true;
                    break;
                }
                if(inp.equalsIgnoreCase("n")){
                    mktMove.leaveMkt=true;
                    break;
                }
                try{
                    int in=Integer.parseInt(inp);
                    if(in<1 || in>hero.armor.size()){
                        System.out.println("Please enter a valid Index ...");
                        continue;
                    }
                    Armory ar=hero.armor.get(in-1);
                    hero.money+=Integer.parseInt(ar.cost)/2;
                    armoryAll.add(ar);
                    hero.armor.remove(ar);
                    break;
                }
                catch (Exception e){
                    System.out.println("Please enter a valid Input ...");
                    continue;
                }
            }while(true);
        }
    }
    public void portionSell(HMMarketGameMove mktMove, HMChosenHero hero){
        String[] portionId={"Enter the ID of the Portion you want to sell"};
        if(hero.potions.size()==0){
            System.out.println("You do not have any Portions!");
        }
        else{
            System.out.println("List of your Portions:");
            System.out.printf(
                    "%-6s %-22s %-8s %-10s %-10s%n",
                    "Index", "Name", "Selling Price", "Level", "Attributes Affected"
            );
            int k=0;
            for(Portion p:hero.potions){
                System.out.printf(
                        "%-6s %-22s %-8s %-10s %-10s%n",
                        k++,
                        p.portionName,
                        (Integer.parseInt(p.portionCost)/2),
                        p.portionLevel,
                        p.attributeAffected
                );
            }

            do{
                String inp=printer.getInput(portionId);
                if(inp.equalsIgnoreCase("q")){
                    mktMove.quit=true;
                    break;
                }
                if(inp.equalsIgnoreCase("n")){
                    mktMove.leaveMkt=true;
                    break;
                }
                try{
                    int in=Integer.parseInt(inp);
                    if(in<1 || in>hero.potions.size()){
                        System.out.println("Please enter a valid Index ...");
                        continue;
                    }
                    Portion p=hero.potions.get(in-1);
                    hero.money+=Integer.parseInt(p.portionCost)/2;
                    hero.potions.remove(p);
                    portionAll.add(p);
                    break;
                }
                catch (Exception e){
                    System.out.println("Please enter a valid Input ...");
                    continue;
                }
            }while(true);
        }
    }

}
