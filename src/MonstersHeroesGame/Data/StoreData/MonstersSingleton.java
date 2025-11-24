package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Heroes;
import MonstersHeroesGame.Data.MainData.Monsters;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MonstersSingleton {
    public List<Monsters> monsters;
    private static MonstersSingleton instance;

    private MonstersSingleton() {
        monsters = new ArrayList<Monsters>();
    }

    public static MonstersSingleton getInstance() {
        if (instance == null) {
            instance = new MonstersSingleton();
            instance.addMonsters("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Dragons.txt","Dragons");
            instance.addMonsters("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Exoskeletons.txt","Exoskeletons");
            instance.addMonsters("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Spirits.txt","Spirits");
        }
        return instance;
    }
    private void addMonsters(String path, String type){
        try {
            Scanner monsterReader = new Scanner(new File(path));
            int k=0;
            while (monsterReader.hasNextLine()) {
                String data = monsterReader.nextLine();
                if(k>=1) {
                    String[] mData = data.split("\\s+");
                    if(mData.length>1) {
                        monsters.add(new Monsters(mData[0], mData[1], mData[2], mData[3], mData[4], type));
                    }
                }
                k++;
            }
            monsterReader.close();
//            return;
        }
        catch(Exception e){
            System.out.println("Error loading heroes from file");
        }

    }
}
