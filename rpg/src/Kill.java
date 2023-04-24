public class Kill implements CharacterEffect {
    @Override
    public void call(Character c) {
        c.setAlive(false);
    }
}