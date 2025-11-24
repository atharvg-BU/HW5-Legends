package MonstersHeroesGame.Data.MainData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Spell {
    public String name;
    public String cost;
    public String rqLevel;
    public String damage;
    public String mana;
    public String type;

    public Spell(String name, String cost, String rqLevel, String damage, String mana, String type) {
        this.name = name;
        this.cost = cost;
        this.rqLevel = rqLevel;
        this.damage = damage;
        this.mana = mana;
        this.type = type;
    }

    public List<String> getSpellDetails() {
        List<String> spellDetails = new ArrayList<>();
        Collections.addAll(spellDetails, name,cost,rqLevel,damage,mana,type);
        return spellDetails;
    }
}
