package MonstersHeroesGame.Data.StoreData;

import MonstersHeroesGame.Data.MainData.Portion;

import java.io.File;
import java.util.*;

public class PortionSingleton {
    public List<Portion> portionData;
    private static PortionSingleton instance;
    private PortionSingleton() {
        portionData = new ArrayList<Portion>();
    }

    public static PortionSingleton getInstance() {
        if(instance == null) {
            instance = new PortionSingleton();
            try {
                Scanner portionReader = new Scanner(new File("/Users/atharvgulati/Desktop/BUAssignments/CS611/HeroesAndMonsters/src/Legends_Monsters_and_Heroes 2/Potions.txt"));
                int k=0;
                while(portionReader.hasNextLine()) {
                    String data = portionReader.nextLine();
                    if(k>=1){
                        String[] pData=data.split("\\s+");
                        Portion portion = new Portion(pData[0],pData[1],pData[2],pData[3],pData[4]);
                        instance.portionData.add(portion);
                    }
                    k++;
                }
            }catch(Exception e) {
                System.out.println("Error in reading Portion file");
            }
        }
        return instance;
    }


}
