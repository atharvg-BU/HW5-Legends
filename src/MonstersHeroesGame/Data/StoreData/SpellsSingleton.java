package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Armory;
import MonstersHeroesGame.Data.MainData.Spell;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class SpellsSingleton {
    public List<Spell> spells;
    private static SpellsSingleton instance;

    private SpellsSingleton() {
        spells = new ArrayList<Spell>();
    }

    public static SpellsSingleton getInstance() {
        if (instance == null) {
            instance = new SpellsSingleton();
            instance.addSpell("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/FireSpells.txt","Fire");
            instance.addSpell("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/IceSpells.txt","Ice");
            instance.addSpell("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/LightningSpells.txt","Lightning");
        }
        return instance;
    }
    private void addSpell(String path, String type){
        try {
            Scanner spellReader = new Scanner(new File(path));
            int k=0;
            while (spellReader.hasNextLine()) {
                String data = spellReader.nextLine();
                if(k>=1) {
                    String[] hData = data.split("\\s+");
                    if(hData.length>1) {
                        spells.add(new Spell(hData[0], hData[1], hData[2], hData[3],hData[4],type));
                    }
                }
                k++;
            }
            spellReader.close();
//            return;
        }
        catch(Exception e){
            System.out.println("Error loading Spell from file");
        }

    }
}
