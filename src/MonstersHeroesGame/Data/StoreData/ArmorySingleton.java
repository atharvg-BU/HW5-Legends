package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Armory;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ArmorySingleton {
    public List<Armory> armoryAll;
    private static ArmorySingleton instance;

    private ArmorySingleton() {
        armoryAll = new ArrayList<Armory>();
    }

    public static ArmorySingleton getInstance() {
        if (instance == null) {
            instance = new ArmorySingleton();
            instance.addArmory("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Armory.txt");
            }
        return instance;
    }
    private void addArmory(String path){
        try {
            Scanner heroReader = new Scanner(new File(path));
            int k=0;
            while (heroReader.hasNextLine()) {
                String data = heroReader.nextLine();
                if(k>=1) {
                    String[] hData = data.split("\\s+");
                    if(hData.length>1) {
                        armoryAll.add(new Armory(hData[0], hData[1], hData[2], hData[3]));
                    }
                }
                k++;
            }
            heroReader.close();
//            return;
        }
        catch(Exception e){
            System.out.println("Error loading Armory from file");
        }

    }
}
