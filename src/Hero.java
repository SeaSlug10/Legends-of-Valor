import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@SuppressWarnings({"unchecked"})
public class Hero extends Entity{
    private final String type;


    public Hero(String name, int level, String type, int hp, int str, int dex, int agi, int mp) {
        super(name, level, hp, agi);
        this.name = Colors.GREEN + name + Colors.RESET;
        this.type = type;
        this.str = str;
        this.dex = dex;
        this.mp = new int[]{mp, mp};
    }

    public String getType(){
        return type;
    }

    public void levelUp(){
        xp[0] = Math.max(0,xp[0] - xp[1]);
        level += 1;
        xp[1] = level*10;
        int newHp = (int)(hp[1] + 100*(1.0 + randInt(0,3)/10.0));
        hp = new int[]{newHp, newHp};
        if(type.equals("paladin")){
            int newMp = (int)(mp[1] + 10*(1.0 + randInt(0,3)/10.0));
            mp = new int[]{newMp, newMp};
            dex = (int)(dex*1.1) + randInt(5, 11);
            str = (int)(str*1.1) + randInt(5, 11);
            agi = (int)(agi*1.05) + randInt(5, 11);
        } else if(type.equals("sorcerer")){
            int newMp = (int)(mp[1] + 10*(1.0 + randInt(0,3)/10.0));
            mp = new int[]{newMp, newMp};
            dex = (int)(dex*1.1) + randInt(5, 11);
            str = (int)(str*1.05) + randInt(5, 11);
            agi = (int)(agi*1.1) + randInt(5, 11);
        } else {
            int newMp = (int)(mp[1] + 5*(1.0 + randInt(0,3)/10.0));
            mp = new int[]{newMp, newMp};
            dex = (int)(dex*1.05) + randInt(5, 11);
            str = (int)(str*1.1) + randInt(5, 11);
            agi = (int)(agi*1.1) + randInt(5, 11);
        }
    }

    public String usePotion(int i){
        Potion toUse = (Potion)inventory.get(i);
        int amt = toUse.getModAmt();
        switch (toUse.getModType()){
            case "hp":
                setHp(getHp()[0] + amt);
                break;
            case "mp":
                setMp(getMp()[0] + amt);
                break;
            case "str":
                setStr(getStr() + amt);
                break;
            case "agi":
                setAgi(getAgi() + amt);
                break;
            case "dex":
                setDex(getDex() + amt);
                break;
        }
        inventory.remove(i);
        return String.format("%s used %s and increased %s by %d", name, toUse.getName(), toUse.getModType(), amt);
    }

    public Map useSpell(int i){
        Spell toUse = (Spell)inventory.get(i);
        mp[0] = Math.max(0, mp[0] - toUse.getMpCost());
        int dmg = toUse.getDamage() + (int)(dex/10.0)*(toUse.getDamage());
        toUse.setUses(toUse.getUses()[0] - 1);
        if(toUse.getUses()[0] == 0){
            inventory.remove(i);
        }
        String outStr = String.format(" used %s to attack for %d %s damage!",
                toUse.getName(), dmg, toUse.getDmgType());
        Map out = new HashMap();
        out.put("dmg", dmg);
        out.put("string", name + outStr);
        out.put("type", toUse.getDmgType());
        return out;
    }

    public Map attack(){
        int damage = str;
        boolean broke = false;
        String brokeStr = "";
        for(int i=0;i<2;i++){
            Weapon weapon = weapons[i];
            if(!(weapon instanceof EmptyWeapon)){
                if(weapon instanceof DoubleHanded){
                    damage += 0.5*weapons[0].getDamage();
                } else {
                    damage += weapon.getDamage();
                    weapon.setUses(weapon.getUses()[0] - 1);
                    if(weapon.getUses()[0] == 0){
                        broke = true;
                        brokeStr += " "+weapon.getName();
                        unequipWeapon(inventory.indexOf(weapon));
                    }
                }
            }
        }
        String outStr = String.format(" attacked for %d damage!", damage);
        if(broke){
            outStr += String.format(" Their %s broke...", brokeStr);
        }
        Map out = new HashMap();
        out.put("dmg", damage);
        out.put("string", name + outStr);
        out.put("type", "normal");
        return out;
    }

    public String takeDamage(int damage){
        double dodge = Math.min(agi*.002, .95);
        //cap dodge chance at 95%
        if(dodge > (double)randInt(0,101)/100){
            return name+" dodged the attack!";
        }
        boolean broke = false;
        String brokeStr = "";
        for(Armor armorItem : armor.values()){
            if(!(armorItem instanceof EmptyArmor)){
                damage -= armorItem.getReduction();
                armorItem.setUses(armorItem.getUses()[0] - 1);
                if(armorItem.getUses()[0] == 0){
                    broke = true;
                    brokeStr += " " + armorItem.getName();
                    unequipArmor(armorItem.getSlot());
                }
            }
        }
        hp[0] = Math.max(hp[0] - damage, 0);
        String outStr = String.format(" took %d damage!", damage);
        if(hp[0] == 0){
            outStr += " They fainted!";
        }
        if(broke){
            outStr += " Their" + brokeStr + " broke...";
        }
        return name + outStr;
    }

    public String toString(){
        return String.format("%s %s, LVL:%d, XP:%d/%d, HP:%d/%d, MP:%d/%d, STR:%d, DEX:%d, AGI:%d, Gold: %s%d%s",
                type, name, level, xp[0], xp[1], hp[0], hp[1], mp[0], mp[1], str, dex, agi, Colors.YELLOW, gold, Colors.RESET);
    }

    public String prettyString(){
        int currentHp = hp[0];
        int currentMp = mp[0];
        String hpStr = "";
        String mpStr = "";
        for(int i=0;i<10;i++){
            if(currentHp > Math.floor(hp[1]/10.1)){
                hpStr += Colors.GREEN + "█";
                currentHp -= hp[1]/10;
            } else {
                hpStr += Colors.RED + "█";
            }
            if(currentMp > Math.floor(mp[1]/10.1)){
                mpStr += Colors.BLUE + "█";
                currentMp -= mp[1]/10;
            } else {
                mpStr += Colors.WHITE + "█";
            }
        }
        hpStr += Colors.RESET;
        mpStr += Colors.RESET;
        return String.format("%s\nLVL : %d\nHP:%d/%d %s\nMP:%d/%d %s",
                name, level, hp[0], hp[1], hpStr, mp[0], mp[1], mpStr);
    }

    public String equippedItemsString(){
        String headStr = "Head : ";
        String chestStr = "Chest : ";
        String legsStr = "Legs : ";
        String lHandStr = "Left Hand : ";
        String rHandStr = "Right Hand : ";
        if(armor.get("head") instanceof EmptyArmor){
            headStr += "none";
        } else {
            headStr += armor.get("head").getName() + " DEF: " + armor.get("head").getReduction();
        }
        if(armor.get("chest") instanceof EmptyArmor){
            chestStr += "none";
        } else {
            chestStr += armor.get("chest").getName() + " DEF: " + armor.get("chest").getReduction();
        }
        if(armor.get("legs") instanceof EmptyArmor){
            legsStr += "none";
        } else {
            legsStr += armor.get("legs").getName() + " DEF: " + armor.get("legs").getReduction();
        }
        if(weapons[0] instanceof EmptyWeapon){
            lHandStr += "none";
        } else {
            lHandStr += weapons[0].getName() + " DMG : " + weapons[0].getDamage();
        }
        if(weapons[1] instanceof EmptyWeapon){
            rHandStr += "none";
        } else if(weapons[1] instanceof DoubleHanded){
            rHandStr += weapons[0].getName() + " (two-handed)";
        } else {
            rHandStr += weapons[1].getName() + " DMG : " + weapons[1].getDamage();
        }
        return String.format("%s\n%s\n%s\n%s\n%s", headStr, chestStr, legsStr, lHandStr, rHandStr);
    }

    public String inventoryString(){
        String outStr = "";
        for(int i=0;i<inventory.size();i++){
            outStr += i+1 + ": " + inventory.get(i).toString() + "\n";
        }
        return outStr;
    }

    private int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
