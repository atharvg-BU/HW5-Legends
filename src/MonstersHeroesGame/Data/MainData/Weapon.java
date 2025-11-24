package MonstersHeroesGame.Data.MainData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Weapon {
    public String name;
    public String cost;
    public String level;
    public String damage;
    public String hands;
    public Weapon(String name, String cost, String level, String damage, String hands){
        this.name = name;
        this.cost = cost;
        this.level = level;
        this.damage = damage;
        this.hands = hands;
    }
    public List<String> getWeaponDetails(){
        List<String> weaponDetails = new ArrayList<>();
        Collections.addAll(weaponDetails, name,cost,level,damage,hands);
        return weaponDetails;
    }
    public String getCost(){
        return cost;
    }
}
