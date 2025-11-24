package MonstersHeroesGame.Data.MainData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Armory {
    public String name;
    public String cost;
    public String rqLevel;
    public String damageReduction;

    public Armory(String name, String cost, String rqLevel, String damageReduction) {
        this.name = name;
        this.cost = cost;
        this.rqLevel = rqLevel;
        this.damageReduction = damageReduction;
    }
    public List<String> getArmoryDetails(){
        List<String> armoryDetails = new ArrayList<>();
        Collections.addAll(armoryDetails, name,cost,rqLevel,damageReduction);
        return armoryDetails;
    }
}
