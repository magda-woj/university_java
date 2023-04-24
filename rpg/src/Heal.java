public class Heal implements CharacterEffect {
    @Override
    public void call(Character c) {
        c.setHp(c.getHp() + 20);
    }
}
