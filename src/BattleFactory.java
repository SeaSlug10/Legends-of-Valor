public class BattleFactory {
    private MonsterFactory factory;

    public BattleFactory(MonsterFactory factory) {
        this.factory = factory;
    }

    public BattleCoordinator createBattle(Hero[] party) {
        return new BattleCoordinator(party, factory);
    }
}
