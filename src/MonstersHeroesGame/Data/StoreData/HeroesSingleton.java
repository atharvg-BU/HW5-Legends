package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Heroes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class HeroesSingleton {
    public List<Heroes> heroes;
    private static HeroesSingleton instance;

    private HeroesSingleton() {
        heroes = new ArrayList<Heroes>();
    }

    public static HeroesSingleton getInstance() {
        if (instance == null) {
            instance = new HeroesSingleton();
            instance.addHeroes("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Warriors.txt","Warriors");
            instance.addHeroes("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Paladins.txt","Paladins");
            instance.addHeroes("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Sorcerers.txt","Sorcerers");
        }
        return instance;
    }
    private void addHeroes(String path, String type){
        try {
            Scanner heroReader = new Scanner(new File(path));
            int k=0;
            while (heroReader.hasNextLine()) {
                String data = heroReader.nextLine();
                if(k>=1) {
                    String[] hData = data.split("\\s+");
                    if(hData.length>1) {
                        heroes.add(new Heroes(hData[0], hData[1], hData[2], hData[3], hData[4], hData[5], hData[6], type));
                    }
                }
                k++;
            }
            heroReader.close();
//            return;
        }
        catch(Exception e){
            System.out.println("Error loading heroes from file");
        }

    }
}
