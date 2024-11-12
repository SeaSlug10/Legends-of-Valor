import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

public class WorldMap {
    private MapTile[][] map;
    private int[] partyLocation;
    private int size = 10;
    private MerchantFactory factory;
    private HashMap<String, Merchant> merchants;

    public WorldMap(MerchantFactory factory) {
        this.factory = factory;
        map = new MapTile[size][size];
        merchants = new HashMap<String, Merchant>();
        for(int i = 0; i < map.length; i++){
            for(int j = 0; j < map[i].length; j++){
                int rand = randInt(0, 10);
                if(rand < 2){
                    map[i][j] = new MapTile("inaccessible");
                } else if(rand < 4){
                    map[i][j] = new MapTile("market");
                    merchants.put(i+","+j, factory.createMerchant());
                } else {
                    map[i][j] = new MapTile("common");
                }
            }
        }
        MapTile startLoc = new MapTile("inaccessible");
        while(startLoc.getType().equals("inaccessible")){
            int x = randInt(0, size);
            int y = randInt(0, size);
            partyLocation = new int[]{x, y};
            startLoc = map[x][y];
        }
        map[partyLocation[0]][partyLocation[1]].setOccupied(true);
    }

    public int[] getPartyLocation() {
        return partyLocation;
    }

    //true if rolled for a battle
    public boolean setPartyLocation(int[] partyLocation) {
        map[this.partyLocation[0]][this.partyLocation[1]].setOccupied(false);
        map[partyLocation[0]][partyLocation[1]].setOccupied(true);
        this.partyLocation = partyLocation;
        if(map[partyLocation[0]][partyLocation[1]].getType().equals("common")){
            if(randInt(0, 3) == 2){
                return true;
            }
        }
        return false;
    }

    public Merchant getMerchant(int x, int y){
        return merchants.get(x + "," + y);
    }
    public HashMap getAllMerchants(){
        return merchants;
    }

    public MapTile getTile(int x, int y) {
        return map[x][y];
    }

    public int getSize(){
        return this.size;
    }

    public String toString(){
        String outStr = "";
        for(int i = 0; i < map.length; i++){
            String rowStr = "";
            for(int j = 0; j < map[i].length; j++){
                rowStr += map[i][j].toString() + " | ";
            }
            outStr += rowStr + "\n" +
            "----------------------------------------\n";
        }
        return outStr;
    }
    private int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
