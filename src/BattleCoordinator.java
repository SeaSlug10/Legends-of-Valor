import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

@SuppressWarnings({"unchecked"})
public class BattleCoordinator {
    private int goldGained = 0;
    private int xpGained = 0;
    private Hero[] party;
    private ArrayList<Hero> aliveMembers;
    private ArrayList<Monster> monsters;
    private int monsterTurn;
    private MonsterFactory factory;

    public BattleCoordinator(Hero[] party, MonsterFactory factory) {
        this.party = party;
        this.aliveMembers = new ArrayList<Hero>(Arrays.asList(party));
        this.factory = factory;
        this.monsters = new ArrayList<Monster>();
        for(int i = 0; i < party.length; i++){
            monsters.add(factory.createMonster(party));
        }
    }

    public int numMonsters(){
        return monsters.size();
    }

    public ArrayList<Hero> getAliveMembers() {
        return aliveMembers;
    }

    public String takeMonsterTurn(){
        Map attack = monsters.get(monsterTurn).attack();
        Hero target = aliveMembers.get(randInt(0, aliveMembers.size()));
        String result = target.takeDamage((int)attack.get("dmg"));
        if(target.getHp()[0] == 0){
            aliveMembers.remove(target);
        }
        if(monsterTurn + 1 < monsters.size()){
            monsterTurn++;
        } else {
            monsterTurn = 0;
        }
        return attack.get("string") + "\n" + result;
    }

    public String attackMonster(int i, Map dmgInfo){
        Monster target = monsters.get(i-1);
        Map result = target.takeDamage(dmgInfo);
        if((boolean)result.get("fainted")){
            xpGained += target.getXp()[0];
            goldGained += target.getGold();
            monsters.remove(target);
            monsterTurn = 0;
        }
        return (String)result.get("string");
    }

    public Map battleStatus(){
        Map outMap = new HashMap();
        if(monsters.size() == 0){
            outMap.put("over", true);
            outMap.put("playerWin", true);
            outMap.put("xp", xpGained);
            outMap.put("gold", goldGained);
        } else if(aliveMembers.size() == 0){
            outMap.put("over", true);
            outMap.put("playerWin", false);
        } else {
            outMap.put("over", false);
        }
        return outMap;
    }

    private String addSpacing(String str, int i){
        String outStr = str;
        for(int j = 0; j < i; j++){
            outStr += " ";
        }
        return outStr;
    }

    private String arrangeMonsters(){
        String[] outStr =  monsters.get(0).prettyString().split("\n");
        outStr = Stream.concat(Arrays.stream(new String[]{"(1)"}), Arrays.stream(outStr))
                .toArray(String[]::new);
        for(int i = 1; i < monsters.size(); i++){
            String[] next = monsters.get(i).prettyString().split("\n");
            outStr[0] = addSpacing(outStr[0], 19) + "| ("+(i+1)+")";
            outStr[1] = addSpacing(outStr[1], 3) + "| "+next[0];
            outStr[2] = addSpacing(outStr[2], 15) + "| "+next[1];
            outStr[3] = addSpacing(outStr[3], 1) + "| "+next[2];
        }
        return String.join("\n", outStr);
    }

    private String arrangeHeroes(){
        String[] outStr = party[0].prettyString().split("\n");
        for(int i = 1; i < party.length; i++){
            String[] next = party[i].prettyString().split("\n");
            outStr[0] = addSpacing(outStr[0], 32 - outStr[0].length()/i) + "| "+next[0];
            outStr[1] = addSpacing(outStr[1], 15) + "| "+next[1];
            outStr[2] = addSpacing(outStr[2], 1) + "| "+next[2];
            outStr[3] = addSpacing(outStr[3], 3) + "| "+next[3];
        }
        return String.join("\n", outStr);
    }

    public String toString(){
        return arrangeMonsters() + "\n\n" + arrangeHeroes();
    }

    public String monsterInfo(){
        String outStr = "";
        for(int i = 0; i < monsters.size(); i++){
            outStr += (i+1) + ") " + monsters.get(i).toString() + "\n";
        }
        return outStr;
    }

    public String partyInfo(){
        String outStr = "";
        for(int i = 0; i < party.length; i++){
            outStr += (i+1) + ") " + party[i].toString() + "\n";
        }
        return outStr;
    }


    private int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }

}
