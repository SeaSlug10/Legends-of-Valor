import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({"unchecked"})
public class Monster extends Entity{

    private int defense;
    private int damage;
    private final String type;

    public Monster(int defense, int damage, String name, int level, int hp, int agi, String type){
        super(name, level, hp, agi);
        this.name = Colors.RED + name + Colors.RESET;
        this.defense = defense;
        this.damage = damage;
        this.type = type;
    }

    public int getDefense() {
        return defense;
    }
    public int getDamage() {
        return damage;
    }

    public Map attack(){
        int damage = getDamage();
        Map out = new HashMap();
        out.put("string", String.format("%s attacked for %d damage!",name, damage));
        out.put("dmg", damage);
        return out;
    }

    public Map takeDamage(Map dmgInfo){
        int dmg = (int)dmgInfo.get("dmg");
        double dodge = Math.min(agi*.002, .95);
        String outStr = "";
        Map out = new HashMap();
        if(dodge > (double)randInt(0,101)/100){
            out.put("string", name+" dodged the attack!");
            out.put("fainted", false);
            return out;
        }
        if(randInt(1,21) == 20){
            dmg *= 2;
            outStr += " was hit critically and ";
        }
        dmg = Math.max(0, dmg - defense);
        outStr += " took " +dmg+" damage! ";
        if(dmgInfo.get("type").equals("fire")){
            int change = (int)(defense*0.1);
            defense -= change;
            outStr += " Defense was reduced by " + change+"! ";
        } else if(dmgInfo.get("type").equals("lightning")){
            int change = (int)(agi*0.1);
            agi -= change;
            outStr += " Dodge was reduced by " + change+"! ";
        } else if(dmgInfo.get("type").equals("ice")){
            int change = (int)(damage*0.1);
            damage -= change;
            outStr += " Damage was reduced by " + change+"! ";
        }
        setHp(Math.max(0, getHp()[0] - dmg));
        if(getHp()[0] == 0){
            outStr += name + " was defeated!";
            out.put("fainted", true);
        } else {
            out.put("fainted", false);
        }

        out.put("string", name + outStr);
        return out;
    }

    public String prettyString(){
        int currentHp = hp[0];
        String hpStr = "";
        for(int i=0;i<10;i++){
            if(currentHp > Math.floor(hp[1]/10.1)){
                hpStr += Colors.GREEN + "█";
                currentHp -= hp[1]/10;
            } else {
                hpStr += Colors.RED + "█";
            }
        }
        hpStr += Colors.RESET;
        return String.format("%s\nLVL : %d\nHP:%d/%d %s",
                name, level, hp[0], hp[1], hpStr, mp[0], mp[1]);
    }

    public String toString(){
        return String.format("Level %d %s, HP:%d/%d, DMG:%d, DEF:%d, Dodge:%d", level, name, hp[0],hp[1], damage, defense, agi);
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
