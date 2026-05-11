package Assignment05;

public class Mage extends GameCharacter{
    private boolean defending = false;

    public Mage(String name) {
        super(name, 100, 200);
    }

    @Override
    public void attack(GameCharacter target) throws InsufficientEnergyException, InvalidActionException {
        int attDmg = 15;
        int cost = 8;
        if (this.energy < cost) throw new InsufficientEnergyException(this.name + " has insufficient energy to attack! (needs " + cost + ", has " + energy + ")");
        
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

    @Override
    public void defend() {
        this.defending = true;
        System.out.println(this.name + " took defencive stance. Next hit will cause 50% less damage!");
        
    }

    @Override
    public void useAbility(GameCharacter target) throws InsufficientEnergyException, InvalidActionException{
        int attDmg = 35;
        int cost = 15;
        if (this.energy < cost) throw new InsufficientEnergyException(this.name + " has insufficient energy for Fireball! (needs " + cost + ", has " + energy + ")");
        
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

    public boolean isDefending() {
        return this.defending;
    }

    public void changeDefending() {
        this.defending = !this.defending;
    }
}
