package MonstersHeroesGame.Data.MainData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Heroes {
    private String heroName;
    private String heroMana;
    private String heroStrength;
    private String heroAgility;
    private String heroDexterity;
    private String startingMoney;
    private String heroExperience;
    private String heroType;

    public Heroes(String heroName, String heroMana, String heroStrength, String heroAgility, String heroDexterity,String startingMoney, String heroExperience, String heroType ){
        this.heroName = heroName;
        this.heroMana = heroMana;
        this.heroStrength = heroStrength;
        this.heroAgility = heroAgility;
        this.heroDexterity = heroDexterity;
        this.startingMoney = startingMoney;
        this.heroExperience = heroExperience;
        this.heroType = heroType;
    }
    public List<String> getHeroDetails(){
        List<String> heroeDetails = new ArrayList<>();
        Collections.addAll(heroeDetails, heroName,heroMana,heroStrength,heroAgility,heroDexterity,startingMoney,heroExperience,heroType);
        return heroeDetails;
    }
    public String getHeroName() {
        return heroName;
    }

    public String getHeroExperience() {
        return heroExperience;
    }
}
