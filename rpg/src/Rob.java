import java.util.ArrayList;

public class Rob implements CharacterEffect {
    @Override
    public void call(Character c) {
        c.setItemList(new ArrayList<Item>());
        c.setWeapon(new Weapon(0, new NoEffect()));
    }
}
