import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SuppressWarnings({"unchecked"})
public class GamePlayer {
    private Scanner inp =  new Scanner(System.in);
    private HeroFactory heroFactory;
    private MonsterFactory monsterFactory;
    private ItemFactory itemFactory;
    private BattleFactory battleFactory;
    private MerchantFactory merchantFactory;
    private WorldMap worldMap;
    private Hero[] party;

    public GamePlayer(String file) {
        heroFactory = new HeroFactory(file);
        monsterFactory = new MonsterFactory(file);
        itemFactory = new ItemFactory(file);
        battleFactory = new BattleFactory(monsterFactory);
        merchantFactory = new MerchantFactory(itemFactory);
        worldMap = new WorldMap(merchantFactory);
        int partySize = 0;
        while(partySize < 1 || partySize > 3) {
            clearScreen();
            System.out.println("Welcome to Monsters and Heroes!");
            System.out.println("How many heroes would you like in your party? (enter a number 1-3)");
            String size = inp.nextLine();
            try{
                partySize = Integer.parseInt(size);
            } catch (Exception e){}
        }
        party = new Hero[partySize];
        for(int i = 0; i < partySize; i++) {
            party[i] = heroFactory.createHero();
        }
        mainLoop();
    }

    public Hero[] getParty() {
        return party;
    }

    //for testing only
    public Merchant getMerchant(){
        HashMap merchants = worldMap.getAllMerchants();
        Object[] merchs = merchants.values().toArray();
        return (Merchant)merchs[0];
    }

    public Map doBattle() {
        BattleCoordinator battle = battleFactory.createBattle(party);
        int turn = 0;
        String error = "";
        String prevTurn = "You were attacked by " + battle.numMonsters() + " monsters!";
        while (!(boolean) battle.battleStatus().get("over")) {
            Hero up = party[turn];
            if(!battle.getAliveMembers().contains(up)){
                if(turn < party.length - 1) {
                    turn++;
                } else {
                    turn = 0;
                }
                continue;
            }
            String inpReq = "1) Attack\n2) Use Item\n3) See stats";
            String addtlInfo = prevTurn + error + "\n" + up.name + "'s turn!";
            error = "";
            prevTurn = "";
            int action = selectNum(1, 3, battle.toString(), addtlInfo, inpReq);
            if (action == -1) {
                Map out = new HashMap();
                out.put("quit", true);
                return out;
            }
            addtlInfo = "";
            switch (action) {
                case 1:
                    inpReq = "Attack which monster? (enter number or b for back)";
                    action = selectNum(1, battle.numMonsters(), battle.toString(), addtlInfo, inpReq);
                    if (action == -1) {
                        Map out = new HashMap();
                        out.put("quit", true);
                        return out;
                    } else if (action == -2) {
                        continue;
                    }
                    Map playerAtk = party[turn].attack();
                    String outcome = battle.attackMonster(action, playerAtk);
                    prevTurn = playerAtk.get("string") + "\n" + outcome;
                    if (turn < party.length - 1) {
                        turn++;
                    } else {
                        turn = 0;
                    }
                    break;
                case 2:
                    inpReq = "Use which item? (enter number or b for back)";
                    String mainDisp = up.inventoryString();
                    addtlInfo = "";
                    if (up.getInventory().size() == 0) {
                        error = "Nothing in inventory!";
                        continue;
                    }
                    action = selectNum(1, up.getInventory().size(), mainDisp, addtlInfo, inpReq);
                    if (action == -1) {
                        Map out = new HashMap();
                        out.put("quit", true);
                        return out;
                    } else if (action == -2) {
                        continue;
                    }
                    Item toUse = up.getInventory().get(action - 1);
                    if (toUse instanceof Potion) {
                        prevTurn = up.usePotion(action - 1);
                    } else if (toUse instanceof Spell) {
                        if (up.getMp()[0] > ((Spell) toUse).getMpCost()) {
                            playerAtk = up.useSpell(action - 1);
                            action = selectNum(1, battle.numMonsters(), battle.toString(),
                                    "Casting Spell " + toUse.getName(),
                                    "Use on which monster? (enter number or b for back)");
                            if (action == -1) {
                                Map out = new HashMap();
                                out.put("quit", true);
                                return out;
                            } else if (action == -2) {
                                continue;
                            } else {
                                outcome = battle.attackMonster(action, playerAtk);
                                prevTurn = playerAtk.get("string") + "\n"+outcome;
                            }
                        } else {
                            error = "Not enough MP!";
                            continue;
                        }
                    } else if (toUse instanceof Armor) {
                        if(toUse.getUses()[0] == 0){
                            error = "Can't equip weapon with no uses!";
                            continue;
                        }
                        if(((Armor) toUse).isEquipped()){
                            up.unequipArmor(((Armor) toUse).getSlot());
                            prevTurn = up.getName() + " unequips " + toUse.getName();
                        } else {
                            up.equipArmor(action - 1);
                            prevTurn = up.getName() + " equips " + toUse.getName();
                        }
                    } else if (toUse instanceof Weapon) {
                        if(toUse.getUses()[0] == 0){
                            error = "Can't equip weapon with no uses!";
                            continue;
                        }
                        if(((Weapon) toUse).isEquipped()) {
                            if (((Weapon) toUse).isTwoHand()) {
                                up.equipWeapon(action - 1, true, true);
                                prevTurn = up.getName() + " equips " + toUse.getName();
                            } else {
                                String inpStr = "Equip to left hand (l) right hand (r) or use two-handed (t)?";
                                String input = "";
                                while (true) {
                                    input = display(up.equippedItemsString(), "Equipping " + toUse.getName(), inpStr);
                                    if (input.toLowerCase().equals("l")) {
                                        up.equipWeapon(action - 1, false, true);
                                        prevTurn = up.getName() + " equips " + toUse.getName();
                                        break;
                                    } else if (input.toLowerCase().equals("r")) {
                                        up.equipWeapon(action - 1, true, false);
                                        prevTurn = up.getName() + " equips " + toUse.getName();
                                        break;
                                    } else if (input.toLowerCase().equals("t")) {
                                        up.equipWeapon(action - 1, true, true);
                                        prevTurn = up.getName() + " equips " + toUse.getName();
                                        break;
                                    } else if (input.toLowerCase().equals("q")) {
                                        Map out = new HashMap();
                                        out.put("quit", true);
                                        return out;
                                    }
                                }
                            }
                        } else {
                            up.unequipWeapon(action - 1);
                            prevTurn = up.getName() + " unequips " + toUse.getName();
                        }
                    }
                    if (turn < party.length - 1) {
                        turn++;
                    } else {
                        turn = 0;
                    }
                    break;
                case 3:
                    display(battle.partyInfo() + "\n" + battle.monsterInfo(), "", "Enter to return");
                    continue;
            }
            if (!(boolean) battle.battleStatus().get("over")) {
                String monsterRes = battle.takeMonsterTurn();
                prevTurn += "\n" + monsterRes + "\n";
            } else {
                return battle.battleStatus();
            }
            error = "";
        }
        return battle.battleStatus();
    }

    public Map merchantExchange(Hero player, Merchant merchant){
        String info = player.getName() + " has " + Colors.YELLOW + player.getGold() +Colors.RESET+ " gold";
        while(true){
            String input = display("Welcome to the Merchant!", info
                    , "(p) to purchase, (s) to sell, (r) to repair, (b) to leave").toLowerCase();
            switch (input){
                case "p":
                    input = "What item to purchase? (enter number or b for back)";
                    String last = "";
                    while(true){
                        String mainDisp = merchant.toString();
                        info = player.getName() + " has " + Colors.YELLOW + player.getGold() +Colors.RESET+ " gold";
                        int val = selectNum(1, merchant.numItems(), mainDisp, last + "\n"+info, input);
                        last = "";
                        if(val == -1){
                            Map out = new HashMap();
                            out.put("quit", true);
                            return out;
                        } else if (val == -2) {
                            break;
                        } else {
                            Item purchase = merchant.getItem(val - 1);
                            if(player.getGold() >= purchase.getValue() && player.getLevel() >= purchase.getLevel()){
                                player.setGold(player.getGold() - purchase.getValue());
                                player.addToInventory(purchase);
                                merchant.removeItem(purchase);
                                last = String.format("Bought %s for %s%d%s gold!",
                                        purchase.getName(), Colors.YELLOW, purchase.getValue(), Colors.RESET);
                            } else {
                                last = "Can't buy that!";
                            }
                        }
                    }
                    break;
                case "s":
                    input = "What item to sell? (enter number or b for back)\n(items sell for 1/2 value)";
                    last = "";
                    while(true){
                        String mainDisp = player.inventoryString();
                        info = player.getName() + " has " + Colors.YELLOW + player.getGold() +Colors.RESET+ " gold";
                        int val = selectNum(1, player.getInventory().size(), mainDisp, last + "\n"+info, input);
                        last = "";
                        if(val == -1){
                            Map out = new HashMap();
                            out.put("quit", true);
                            return out;
                        } else if (val == -2) {
                            break;
                        } else {
                            Item sell = player.getInventory().get(val - 1);
                            player.setGold(player.getGold() + (int)Math.ceil(sell.getValue()/2.0));
                            player.removeFromInventory(sell);
                            merchant.addItem(sell);
                            last = String.format("Sold %s for %s%d%s gold!", sell.getName(), Colors.YELLOW, sell.getValue(), Colors.RESET);
                            if(sell instanceof Weapon && ((Weapon) sell).isEquipped()){
                                player.unequipWeapon(val - 1);
                            } else if(sell instanceof Armor && ((Armor) sell).isEquipped()){
                                player.unequipArmor(((Armor) sell).getSlot());
                            }
                        }
                    }
                    break;
                case "r":
                    input = "Repair which item? (enter number or b for back)\n(items cost 1/2 value to repair)";
                    last = "";
                    while(true){
                        String mainDisp = player.inventoryString();
                        info = player.getName() + " has " + Colors.YELLOW + player.getGold() +Colors.RESET+ " gold";
                        int val = selectNum(1, player.getInventory().size(), mainDisp, last + "\n"+info, input);
                        last = "";
                        if(val == -1){
                            Map out = new HashMap();
                            out.put("quit", true);
                            return out;
                        } else if (val == -2) {
                            break;
                        } else {
                            Item repairItem = player.getInventory().get(val - 1);
                            if(!(repairItem instanceof Weapon) && !(repairItem instanceof Armor)){
                                last = "Can only repair weapon or armor!";
                            } else if((int)Math.ceil(repairItem.getValue()/2.0) > player.getGold()){
                                last = "Not enough gold!";
                            } else if(repairItem.getUses()[0] == repairItem.getUses()[1]){
                                last = "Already fully repaired!";
                            } else {
                                player.setGold(player.getGold() - (int)Math.ceil(repairItem.getValue()/2.0));
                                repairItem.setUses(repairItem.getUses()[1]);
                                last = String.format("Repaired %s's %s", player.getName(), repairItem.getName());
                            }
                        }
                    }
                    break;
                case "b":
                    return new HashMap();
                case "q":
                    Map out = new HashMap();
                    out.put("quit", true);
                    return out;
            }

        }

    }

    public Map doInventory(Hero player){
        String inpReq = "Choose an item to use/equip/unequip (b to leave)";
        String last = "";
        while(true){
            String info = player.equippedItemsString();
            int val = selectNum(1, player.getInventory().size(),
                    player.toString() + "\n"+ player.inventoryString(), last + "\n"+info, inpReq);
            if(val == -1){
                Map out = new HashMap();
                out.put("quit", true);
                return out;
            } else if (val == -2) {
                return new HashMap();
            }
            Item toEquip = player.getInventory().get(val - 1);
            if(toEquip instanceof Armor){
                if(((Armor) toEquip).getUses()[0] == 0){
                    last = "Can't equip armor with no uses!";
                    continue;
                }
                if(((Armor) toEquip).isEquipped()){
                    player.unequipArmor(((Armor) toEquip).getSlot());
                    last = "unequipped "+toEquip.getName();
                } else {
                    player.equipArmor(val - 1);
                    last = "equipped "+toEquip.getName();
                }
            } else if(toEquip instanceof Weapon){
                if(((Weapon) toEquip).getUses()[0] == 0){
                    last = "Can't equip weapon with no uses!";
                    continue;
                }
                if(((Weapon) toEquip).isEquipped()){
                    player.unequipWeapon(val - 1);
                    last = "unequipped "+toEquip.getName();
                } else {
                    if(((Weapon) toEquip).isTwoHand()){
                        player.equipWeapon(val - 1, true, true);
                    } else {
                        while(true) {
                            String input = display(player.equippedItemsString(),
                                    "Equipping " + toEquip.getName(),
                                    "Equip to left hand (l) right hand (r) or use two-handed (t)?");
                            if (input.toLowerCase().equals("l")) {
                                player.equipWeapon(val - 1, false, true);
                                break;
                            } else if (input.toLowerCase().equals("r")) {
                                player.equipWeapon(val - 1, true, false);
                                break;
                            } else if (input.toLowerCase().equals("t")) {
                                player.equipWeapon(val - 1, true, true);
                                break;
                            } else if (input.toLowerCase().equals("q")) {
                                Map out = new HashMap();
                                out.put("quit", true);
                                return out;
                            }
                        }
                    }
                    last = "equipped "+toEquip.getName();
                }
            } else if(toEquip instanceof Potion){
                player.usePotion(val - 1);
                last = "used "+toEquip.getName();
            } else {
                last = "Can't use that right now!";
            }
        }

    }

    public void showInfo(){
        String infoStr = "";
        for(Hero player : party){
            infoStr += player.toString() + "\n";
        }
        display(infoStr, "", "Enter to continue");
    }

    public void mainLoop(){
        String err = "";
        boolean quit = false;
        while(!quit){
            String displayMap = worldMap.toString();
            int[] partyLoc = worldMap.getPartyLocation();
            String input = display(displayMap, err,
                    "Please input a command:\nw,a,s,d to move in a direction" +
                            "\ni to open inventory\np to see party stats\nm if on a market space to enter the market").toLowerCase();
            err = "";
            if(input.equals("w") || input.equals("a") || input.equals("s") || input.equals("d")){
                int[] newLoc = new int[2];
                switch(input) {
                    case "w":
                        newLoc = new int[]{partyLoc[0]-1, partyLoc[1]};
                        break;
                    case "a":
                        newLoc = new int[]{partyLoc[0], partyLoc[1]-1};
                        break;
                    case "s":
                        newLoc = new int[]{partyLoc[0]+1, partyLoc[1]};
                        break;
                    case "d":
                        newLoc = new int[]{partyLoc[0], partyLoc[1]+1};
                }
                if(newLoc[0] >= 0 && newLoc[0] <= worldMap.getSize()-1 && newLoc[1] >= 0 && newLoc[1] <= worldMap.getSize()-1){
                    if(worldMap.getTile(newLoc[0],newLoc[1]).getType().equals("inaccessible")){
                        err = "Inaccessible tile!";
                    } else {
                        boolean triggerBattle = worldMap.setPartyLocation(newLoc);
                        if(triggerBattle){
                            Map battleRes = doBattle();
                            if(battleRes.containsKey("quit")){
                                break;
                            } else if((boolean)battleRes.get("playerWin")){
                                int goldWon = (int)battleRes.get("gold")/party.length;
                                int xpWon = (int)battleRes.get("xp")/party.length;
                                err = String.format("Your party won! each player gained %s%d%s gold and %d xp",
                                        Colors.YELLOW,goldWon,Colors.RESET, xpWon);
                                for(Hero player : party){
                                    player.setHp(player.getHp()[0] + (int)(player.getHp()[1]*0.1));
                                    player.setMp(player.getMp()[0] + (int)(player.getMp()[1]*0.1));
                                    player.setGold(player.getGold() + goldWon);
                                    int[] curXp = player.getXp();
                                    curXp[0] += xpWon;
                                    player.setXp(curXp);
                                    while(player.getXp()[0] > player.getXp()[1]){
                                        player.levelUp();
                                        err += "\n" + player.getName() + " leveled up!";
                                    }
                                }
                            } else {
                                clearScreen();
                                System.out.printf("Game Over! Your final Party:\n");
                                for(Hero player: party){
                                    System.out.println(player.toString());
                                }
                                break;
                            }
                        }
                    }
                } else {
                    err = "Out of bounds!";
                }
            } else {
                switch(input){
                    case "p":
                        showInfo();
                        break;
                    case "m":
                        if(worldMap.getTile(partyLoc[0], partyLoc[1]).getType().equals("market")){
                            Map entering = selectHero("Which hero should enter?");
                            if(entering.containsKey("quit")){
                                break;
                            } else if(entering.containsKey("hero")){
                                Map res = merchantExchange((Hero)entering.get("hero"), worldMap.getMerchant(partyLoc[0], partyLoc[1]));
                                if(res.containsKey("quit")){
                                    break;
                                }
                            }
                        }
                        break;
                    case "i":
                        Map inv = selectHero("Which hero's inventory to modify?");
                        if(inv.containsKey("quit")){
                            break;
                        } else if(inv.containsKey("hero")){
                            doInventory((Hero)inv.get("hero"));
                        }
                        break;
                    case "q":
                        quit = true;
                        break;
                }
            }
        }
    }

    private Map selectHero(String inpReq){
        String dispStr = "";
        for(int i=0;i< party.length;i++){
            dispStr += (i+1) + ": " + party[i].toString() + "\n";
        }
        int ind = selectNum(1, party.length, dispStr, "", inpReq);
        Map out = new HashMap();
        if(ind == -1){
            out.put("quit", true);
            return out;
        } else if(ind == -2) {
            return out;
        } else {
           out.put("hero", party[ind-1]);
        }
        return out;
    }

    private int selectNum(int min, int max, String mainDisplay, String addtl, String inpReq){
        int number = -1;
        String err = "";
        while(number < min || number > max){
            String input = display(mainDisplay, err + "\n" + addtl, inpReq);
            err = "";
            if(input.toLowerCase().equals("q")){
                return -1;
            } else if(input.toLowerCase().equals("b")){
                return -2;
            }
            try{
                number = Integer.parseInt(input);
            } catch (Exception e){
                err = "Could not understand " + input;
            }
            err = String.format("please enter a number between %d and %d", min, max);
        }
        return number;
    }

    //displays info and returns player response
    public String display(String mainDisplay, String addtlInfo, String inputReq){
        clearScreen();
        int lineLen = mainDisplay.length()/mainDisplay.split("\n").length;
        System.out.println(mainDisplay + "\n"
        + repeatChar("=", lineLen) + "\n"
        + addtlInfo + "\n"
        + repeatChar("=", lineLen) + "\n"
        + inputReq);
        return inp.nextLine();
    }

    public static String repeatChar(String s, int i){
        String outStr = "";
        for(int j=0;j<i;j++){
            outStr += s;
        }
        return outStr;
    }
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
