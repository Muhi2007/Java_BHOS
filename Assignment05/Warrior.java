package Assignment05;

public class Warrior extends GameCharacter {
    private boolean defending = false;

    public Warrior(String name) {
        super(name, 150, 100);
    }

    @Override
    public void attack(GameCharacter target) throws InsufficientEnergyException, InvalidActionException {
        int attDmg = 20;
        int cost = 5;
        if (this.energy < cost) throw new InsufficientEnergyException(this.name + " has insufficient energy to attack! (needs " + cost + ", has " + energy + ")");
        
        if (target.isDeath()) throw new InvalidActionException(name + " cannot attack! ("+ target.getName() + " has already dead )");
        
        if (target instanceof Warrior && ((Warrior) target).isDefending()) {
            attDmg /= 2;
            ((Warrior) target).changeDefending();
        } else if (target instanceof Mage && ((Mage) target).isDefending()) {
            attDmg = Math.max(0, attDmg - 20);
            ((Mage) target).changeDefending();
        } else if (target instanceof Paladin && ((Paladin) target).isDefending()) {
            attDmg /= 2;
            ((Paladin) target).changeDefending();
        }

        target.takeDamage(attDmg);
        this.energy -= cost;
        System.out.println(this.name + " attacked " + target.getName() + " and dealt " + attDmg + " points of damage!");
    }

    @Override
    public void defend() {
        this.defending = true;
        System.out.println(this.name + " took defencive stance. Next hit will cause 50% less damage!");
        
    }

    @Override
    public void useAbility(GameCharacter target) throws InsufficientEnergyException, InvalidActionException{
        int attDmg = 30;
        int cost = 10;
        if (this.energy < cost) throw new InsufficientEnergyException(this.name + " has insufficient energy for Shield Slam! (needs " + cost + ", has " + energy + ")");
        
        if (target.isDeath()) throw new InvalidActionException(name + " cannot attack! ("+ target.getName() + " has already dead )");
        
        if (target instanceof Warrior && ((Warrior)target).isDefending() ) {
            attDmg /= 2;
            ((Warrior)target).changeDefending();
        } else if (target instanceof Mage && ((Mage)target).isDefending()) {
            attDmg = Math.max(0, attDmg-20);
            ((Mage)target).changeDefending();
        }

        target.takeDamage(attDmg);
        this.energy -= cost;
        System.out.println(this.name + " attacked " + target.getName() + " and dealt " + attDmg + " points of damage!");
    }

    public boolean isDefending() {
        return this.defending;
    }

    public void changeDefending() {
        this.defending = !this.defending;
    }
}
