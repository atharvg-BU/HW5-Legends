package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Armory;
import MonstersHeroesGame.Data.MainData.Weapon;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class WeaponsSingleton {
    public List<Weapon> weapons;
    private static WeaponsSingleton instance;

    private WeaponsSingleton() {
        weapons = new ArrayList<Weapon>();
    }

    public static WeaponsSingleton getInstance() {
        if (instance == null) {
            instance = new WeaponsSingleton();
            instance.addWeapon("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Weaponry.txt");
        }
        return instance;
    }
    private void addWeapon(String path){
        try {
            Scanner heroReader = new Scanner(new File(path));
            int k=0;
            while (heroReader.hasNextLine()) {
                String data = heroReader.nextLine();
                if(k>=1) {
                    String[] hData = data.split("\\s+");
                    if(hData.length>1) {
                        weapons.add(new Weapon(hData[0], hData[1], hData[2], hData[3],hData[4]));
                    }
                }
                k++;
            }
            heroReader.close();
//            return;
        }
        catch(Exception e){
            System.out.println("Error loading Weapons from file");
        }

    }
}
