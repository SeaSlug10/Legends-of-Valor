public class Item implements Comparable<Item>{
    protected String name;
    protected int value;
    protected int level;
    protected int[] uses;
    protected int uuid; //factory will assign a uuid to each item it creates

    public Item(String name, int value, int level, int uses, int uuid) {
        this.name = name;
        this.value = value;
        this.level = level;
        this.uses = new int[]{uses, uses};
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public int[] getUses() {
        return uses;
    }

    public void setUses(int uses) { this.uses[0] = uses;}

    public int getUuid() {
        return uuid;
    }

    @Override
    public int compareTo(Item i) {
        if(this.uuid == i.getUuid()){
            return 0;
        };
        return 1;
    }
}
