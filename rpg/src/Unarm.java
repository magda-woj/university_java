public class Unarm implements CharacterEffect {
    @Override
    public void call(Character c) {
        c.setWeapon(new Weapon(0, new NoEffect()));
    }
}
