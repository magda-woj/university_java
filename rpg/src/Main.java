public class Main {
    public static void main(String[] args) {  
        Character Josh = new Character(0, 13);
        Character Amy = new Character(18, 12);
        Character Paul = new Character(15, 12);
        Josh.setWeapon(new Weapon(3, new Weaken()));

        System.out.println("Josh: " + Josh.toString());
        System.out.println("Amy: " + Amy.toString());
        System.out.println("Paul: " + Paul.toString());

        Weapon knife = new Weapon(5, new NoEffect());
        Weapon poison = new Weapon(10, new Weaken());
        Weapon superSword = new Weapon(100, new Kill());
        Weapon destroyer = new Weapon(17, new Unarm());
        Item shield = new Item(new StrenghtenDefense());
        Item medicine = new Item(new Heal());
        Item glove = new Item(new Rob());

        Josh.addItem(glove);
        Josh.setWeapon(knife);
        Amy.setWeapon(destroyer);
        Paul.setWeapon(poison);
        Paul.addItem(shield);
        Paul.addItem(medicine);
        Paul.useItem(0);


        Paul.dealDamage(Amy);
        Amy.dealDamage(Paul);
        Paul.dealDamage(Amy);
        Amy.dealDamage(Paul);

        System.out.println("Amy: "+ Amy.toString());
        System.out.println("Paul: " + Paul.toString());

        Josh.dealDamage(Amy);
        Paul.useItem(0);
        Josh.useReusableItemOnSomebody(0, Paul);
        Amy.dealDamage(Josh);
        Paul.dealDamage(Josh);

        System.out.println("Josh: " + Josh.toString());
        System.out.println("Amy: " + Amy.toString());
        System.out.println("Paul: " + Paul.toString());

        Paul.useItem(0);
        Paul.dealDamage(Josh);
        Paul.setWeapon(knife);
        Josh.setWeapon(new Weapon(0, new NoEffect()));
        Paul.dealDamage(Josh);
        Paul.dealDamage(Josh);
        Amy.setWeapon(superSword);
        Paul.dealDamage(Josh);
        Paul.dealDamage(Josh);
        Amy.dealDamage(Paul);
        Josh.checkOnCharacter();
        
        System.out.println("Josh: " + Josh.toString());
        System.out.println("Amy: " + Amy.toString());
        System.out.println("Paul: " + Paul.toString());
        

    }
}