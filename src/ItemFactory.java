import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class ItemFactory {
    private String[] fireSpells;
    private String[] iceSpells;
    private String[] lightningSpells;
    private String[] onehanded;
    private String[] twohanded;
    private String[] headArmor;
    private String[] chestArmor;
    private String[] legArmor;
    private Map[] dmgMods;
    private Map[] defMods;
    private Map[] potMods;
    private int itemsMade;

    public ItemFactory(String file){
        itemsMade = 0;
        JSONReader reader = new JSONReader(file);
        this.fireSpells = reader.getStringArray("fireSpells");
        this.iceSpells = reader.getStringArray("iceSpells");
        this.lightningSpells = reader.getStringArray("lightningSpells");
        this.onehanded = reader.getStringArray("one-handedWeapons");
        this.twohanded = reader.getStringArray("two-handedWeapons");
        this.headArmor = reader.getStringArray("headArmor");
        this.chestArmor = reader.getStringArray("chestArmor");
        this.legArmor = reader.getStringArray("legArmor");
        this.dmgMods = reader.getMaps("dmgModifiers");
        this.defMods = reader.getMaps("defModifiers");
        this.potMods = reader.getMaps("potionModifiers");
    }

    public Weapon createWeapon(int level){
        String name = "";
        int dmg = 0;
        boolean twoHanded = false;
        if(randInt(0, 2) == 1){
            name = twohanded[randInt(0, twohanded.length)];
            dmg = level*(9 + randInt(0, 2));
            twoHanded = true;
        } else {
            name = onehanded[randInt(0, onehanded.length)];
            dmg = level*(5 + randInt(0, 2));
        }
        if(randInt(0, 2) == 1){
            Map mod = dmgMods[randInt(0, dmgMods.length)];
            name = mod.get("desc") + " " + name;
            long change = (long)mod.get("mod");
            dmg =  Math.max(dmg + Math.toIntExact(change)*(3 + randInt(0,2)), 1);
        }
        int uses = Math.max(randInt(level, level*2), 1);
        int value = Math.max(1,dmg/2);
        itemsMade++;
        return new Weapon(name, value, level, uses, itemsMade, twoHanded, dmg);
    }

    public Armor createArmor(int level){
        String name = "";
        int def = 0;
        String slot = "";
        switch(randInt(0, 3)){
            case 0:
                slot = "head";
                name = headArmor[randInt(0, headArmor.length)];
                def = level*(2 + randInt(0, 2));
                break;
            case 1:
                slot = "chest";
                name = chestArmor[randInt(0, chestArmor.length)];
                def = level*(5 + randInt(0, 2));
                break;
            case 2:
                slot = "legs";
                name = legArmor[randInt(0, legArmor.length)];
                def = level*(3 + randInt(0, 2));
                break;
        }
        if(randInt(0, 2) == 1){
            Map mod = defMods[randInt(0, defMods.length)];
            name = mod.get("desc") + " " + name;
            long change = (long)mod.get("mod");
            def = Math.max(def + Math.toIntExact(change)*(3 + randInt(0,2)), 1);
        }
        int uses = Math.max(1, randInt(level, level*2));
        int value = Math.max(1, def/2);
        itemsMade++;
        return new Armor(name, value, level, uses, itemsMade, slot, def);
    }

    public Potion createPotion(int level){
        String name = "";
        String modType = "";
        int modAmt = 0;
        int value = 0;
        switch(randInt(0, 5)){
            case 0:
                name = "Health Potion";
                modType = "hp";
                modAmt = (level*100)/(1 + randInt(1, 3));
                value = Math.max(1, modAmt/20);
                break;
            case 1:
                name = "Mana Potion";
                modType = "mp";
                modAmt = (level*10)/(1 + randInt(1, 3));
                value = Math.max(1, modAmt/2);
                break;
            case 2:
                name = "Strength Potion";
                modType = "str";
                modAmt = (level*10)/(1 + randInt(1, 3));
                value = Math.max(1, modAmt/2);
                break;
            case 3:
                name = "Agility Potion";
                modType = "agi";
                modAmt = (level*10)/(1 + randInt(1, 3));
                value = Math.max(1, modAmt/2);
                break;
            case 4:
                name = "Dexterity Potion";
                modType = "dex";
                modAmt = (level*10)/(1 + randInt(1, 3));
                value = Math.max(1, modAmt/2);
                break;
        }
        if(randInt(0, 2) == 1){
            Map mod = potMods[randInt(0, potMods.length)];
            name = mod.get("desc") + " " + name;
            long change = (long)mod.get("mod");
            modAmt = Math.max(modAmt + Math.toIntExact(change)*(3 + randInt(0,2)), 1);
        }
        int uses = 1;
        itemsMade++;
        return new Potion(name, value, level, uses, itemsMade, modType, modAmt);
    }

    public Spell createSpell(int level){
        String name = "";
        int dmg = level*(12 + randInt(0, 2));
        int mpCost = level*(10 / (2 + randInt(0, 2)));
        String dmgType = "";
        switch(randInt(0, 3)){
            case 0:
                name = fireSpells[randInt(0, fireSpells.length)];
                dmgType = "fire";
                break;
            case 1:
                name = lightningSpells[randInt(0, lightningSpells.length)];
                dmgType = "lightning";
                break;
            case 2:
                name = iceSpells[randInt(0, iceSpells.length)];
                dmgType = "ice";
                break;
        }
        if(randInt(0, 2) == 1){
            Map mod = dmgMods[randInt(0, dmgMods.length)];
            name = mod.get("desc") + " " + name;
            long change = (long)mod.get("mod");
            dmg =  Math.max(dmg + Math.toIntExact(change)*(3 + randInt(0,2)), 1);
        }
        int value = Math.max(1, dmg/2);
        int uses = Math.max(randInt(level/2, level), 1);
        itemsMade++;
        return new Spell(name, value, level, uses, itemsMade, dmg, mpCost, dmgType);
    }

    private int randInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
