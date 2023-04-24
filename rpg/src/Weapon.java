public class Weapon {
    private int damage;
    private CharacterEffect weaponEffect;

    public Weapon(int damage, CharacterEffect weaponEffect)
    {
        this.damage = damage;
        this.weaponEffect = weaponEffect;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setWeaponEffect(CharacterEffect weaponEffect) {
        this.weaponEffect = weaponEffect;
    }

    public CharacterEffect getWeaponEffect() {
        return weaponEffect;
    }

}
