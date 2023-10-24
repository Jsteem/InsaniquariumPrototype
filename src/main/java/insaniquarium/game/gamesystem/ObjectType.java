package insaniquarium.game.gamesystem;

public enum ObjectType {
    GUPPY_SMALL(1),
    GUPPY_MEDIUM(1 << 1),
    GUPPY_LARGE(1 << 2),
    GUPPY_KING(1 << 3),
    DEAD(1 << 4),
    CARNIVORE(1 << 5),
    ULTRAVORE(1 << 6),
    STARCATCHER(1 << 7),
    GUPPYCRUNSHER(1 << 8),
    BEETLEMUNCHER(1 << 9),
    BREEDER(1 << 10),
    PET(1 << 11),
    ALIEN(1 << 12),
    COIN_SILVER(1 << 13),
    COIN_GOLD(1 << 14),
    DIAMOND(1 << 15),
    STAR(1 << 16),
    PERL(1 << 17),
    CHEST(1 << 18),
    PELLET_FOOD(1 << 19),
    CANNED_FOOD(1 << 20),
    PILL_FOOD(1 << 21),
    POTION_FOOD(1 << 22),
    BEETLE(1 << 23);

    private final int value;

    ObjectType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}