import java.util.concurrent.ThreadLocalRandom;

public class MerchantFactory {
    private ItemFactory factory;

    public MerchantFactory(ItemFactory factory) {
        this.factory = factory;
    }

    public Merchant createMerchant() {
        Merchant merchant = new Merchant();
        for(int i=1; i<=10; i++) {
            if(randInt(0,2) == 1){
                merchant.addItem(factory.createArmor(i));
            }
            if(randInt(0,2) == 1){
                merchant.addItem(factory.createWeapon(i));
            }
            if(randInt(0,2) == 1){
                merchant.addItem(factory.createPotion(i));
            }
            if(randInt(0,2) == 1){
                merchant.addItem(factory.createSpell(i));
            }
        }
        return merchant;
    }


    private int randInt(int min, int max){
        return ThreadLocalRandom.current().nextInt(min, max);
    }
}
