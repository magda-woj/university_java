public class StrenghtenDefense implements CharacterEffect {
    @Override
    public void call(Character c) {
        c.setDefense(c.getDefense() + 5);
    }
}