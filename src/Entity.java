import java.util.ArrayList;
import java.util.HashMap;

public abstract class Entity {
    protected String name;
    protected int level;
    protected int[] xp;
    protected int[] hp;
    protected int[] mp;
    protected int str;
    protected int dex;
    protected int agi;
    protected ArrayList<Item> inventory;
    protected int gold;
    protected HashMap<String, Armor> armor;
    protected Weapon[] weapons; //left hand = Weapon[0], right hand = Weapon[1]. If one-handed being used as 2 handed, always set lHand to weapon.


    public Entity(String name, int level, int hp, int agi){
        this.name = name;
        this.level = level;
        this.xp = new int[]{0, 10*level};
        this.hp = new int[]{hp, hp};
        this.mp = new int[2];
        this.agi = agi;
        this.inventory = new ArrayList<Item>();
        this.gold = 0;
        this.armor = new HashMap<String, Armor>();
        this.armor.put("head", new EmptyArmor());
        this.armor.put("chest", new EmptyArmor());
        this.armor.put("legs", new EmptyArmor());
        this.weapons = new Weapon[]{new EmptyWeapon(), new EmptyWeapon()};
    }

    //Equip armor from index i into inventory
    public boolean equipArmor(int i){
        Armor toEquip;
        try{
            toEquip = (Armor) inventory.get(i);
        } catch(Exception e){
            return false;
        }
        toEquip.setEquipped(true);
        if(!(armor.get(toEquip.getSlot()) instanceof EmptyArmor)){
            armor.get(toEquip.getSlot()).setEquipped(false);
        }
        armor.put(toEquip.getSlot(), toEquip);
        return true;
    }
    public void unequipArmor(String s){
        Armor unequipped = armor.get(s);
        unequipped.setEquipped(false);
        armor.put(s, new EmptyArmor());
    }

    //equip weapon from index i in hand(s) defined by booleans
    public boolean equipWeapon(int i, boolean rHand, boolean lHand){
        Weapon toEquip;
        try{
            toEquip = (Weapon) inventory.get(i);
        } catch(Exception e){
            return false;
        }
        toEquip.setEquipped(true);
        if(rHand && lHand){
            if(!(weapons[1] instanceof EmptyWeapon)){
                weapons[1].setEquipped(false);
            }
            if(!(weapons[0] instanceof EmptyWeapon)){
                weapons[0].setEquipped(false);
            }
            weapons[0] = toEquip;
            weapons[1] = new DoubleHanded();
        } else if(rHand){
            if(!(weapons[1] instanceof EmptyWeapon)){
                weapons[1].setEquipped(false);
            }
            weapons[1] = toEquip;
        } else if(lHand){
            if(!(weapons[0] instanceof EmptyWeapon)){
                weapons[0].setEquipped(false);
            }
            if(weapons[1] instanceof DoubleHanded){ //have to make not double-handed if was before
                weapons[1] = new EmptyWeapon();
            }
            weapons[0] = toEquip;
        }
        return true;
    }

    public void unequipWeapon(int i){
        Item toRemove = inventory.get(i);
        if(weapons[0] == toRemove){
            weapons[0].setEquipped(false);
            weapons[0] = new EmptyWeapon();
            if(weapons[1] instanceof DoubleHanded){
                weapons[1] = new EmptyWeapon();
            }
        } else {
            weapons[1].setEquipped(false);
            weapons[1] = new EmptyWeapon();
        }

    }

    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public int[] getHp() {
        return hp;
    }
    public void setHp(int hp) {
        this.hp[0] = Math.min(this.hp[1], hp);
    }

    public int[] getMp() {
        return mp;
    }
    public void setMp(int mp) {
        this.mp[0] = Math.min(this.mp[1], mp);
    }

    public int getStr() {
        return str;
    }
    public void setStr(int str) {
        this.str = str;
    }

    public int getDex() {
        return dex;
    }
    public void setDex(int dex) {
        this.dex = dex;
    }

    public int getAgi() {
        return agi;
    }
    public void setAgi(int agi) {
        this.agi = agi;
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }
    public void setInventory(ArrayList<Item> inventory) {
        this.inventory = inventory;
    }
    public void addToInventory(Item item){
        this.inventory.add(item);
    }
    //Can remove from inv either by index or item
    public void removeFromInventory(Item item){
        this.inventory.remove(item);
    }
    public void removeFromInventory(int i){
        this.inventory.remove(i);
    }

    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }

    public int[] getXp() {
        return xp;
    }
    public void setXp(int[] xp) {
        this.xp = xp;
    }

    public String getName() {
        return name;
    }


}
