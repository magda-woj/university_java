import java.util.ArrayList;

public class Character {
    private int hp = 50;
    private int damage;
    private int defense;
    private boolean alive = true;
    private Weapon weapon = new Weapon(0, new NoEffect());
    private ArrayList<Item> itemList = new ArrayList<Item>();

    public Character(int damage, int defense){
        this.damage = damage;
        this.defense = defense;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getDefense() {
        return defense;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean getAlive() {
        return alive;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setItemList(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public void addItem(Item item) {
        this.itemList.add(item);
    }

    public void useItem(int i){
        if(i >= itemList.size()){
            System.out.println("oops, don't have that item \n");
            return;
        }
        itemList.get(i).itemEffect.call(this);
        itemList.remove(i);
    }

    public void useReusableItem(int i){
        if(i >= itemList.size()){
            System.out.println("oops, don't have that item \n");
            return;
        }
        itemList.get(i).itemEffect.call(this);
    }

    public void useItemOnSomebody(int i, Character c){
        if(i >= itemList.size()){
            System.out.println("oops, don't have that item \n");
            return;
        }
        itemList.get(i).itemEffect.call(c);
        itemList.remove(i);
    }

    public void useReusableItemOnSomebody(int i, Character c){
        if(i >= itemList.size()){
            System.out.println("oops, don't have that item \n");
            return;
        }
        itemList.get(i).itemEffect.call(c);
    }

    public void checkOnCharacter(){
        if(this.hp <= 0) this.alive = false;
    }

    @Override
    public String toString() {
        String s = "";
        if(!alive){
            s += "is dead \n";
           return s;
       }
       s += "is alive \n";
        s += "hp " + hp + "\n" + "damage: " + damage + "\n" + "defense: " + defense + "\n";
       
        return s;
    }

    public void dealDamage(Character c){
        c.setHp(c.getHp()+c.getDefense()-this.damage-this.weapon.getDamage());
        this.weapon.getWeaponEffect().call(c);
    }


}
