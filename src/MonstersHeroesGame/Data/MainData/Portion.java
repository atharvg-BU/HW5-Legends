package MonstersHeroesGame.Data.MainData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Portion {
    public String portionName;
    public String portionCost;
    public String portionLevel;
    public String attributeIncrease;
    public String attributeAffected;

     public Portion(String portionName, String portionCost, String portionLevel, String attributeIncrease, String attributeAffected){
        this.portionName = portionName;
        this.portionCost = portionCost;
        this.portionLevel = portionLevel;
        this.attributeIncrease = attributeIncrease;
        this.attributeAffected = attributeAffected;
    }

    public List<String> getPortionDetails(){
        List<String> portionDetails = new ArrayList<>();
        Collections.addAll(portionDetails, portionName,portionCost,portionLevel,attributeAffected,attributeIncrease);
        return portionDetails;
    }
}
