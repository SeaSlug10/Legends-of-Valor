import java.util.ArrayList;

public class Merchant {
    private ArrayList<Item> forSale;

    public Merchant(){
        forSale = new ArrayList<Item>();
    }

    public void addItem(Item item){
        forSale.add(item);
    }
    public void removeItem(Item item){
        forSale.remove(item);
    }
    public void removeItem(int index){
        forSale.remove(index);
    }
    public Item getItem(int index){
        return forSale.get(index);
    }

    public int numItems(){
        return forSale.size();
    }

    public String toString(){
        String outStr = "";
        for(int i=0; i<forSale.size(); i++){
            outStr += i+1 + ": " +  forSale.get(i).toString() + "\n";
        }
        return outStr;
    }
}
