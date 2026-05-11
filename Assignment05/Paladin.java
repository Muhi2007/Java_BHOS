package Assignment05;

public class Paladin extends GameCharacter implements Healable, Upgradeable {

    private boolean defending = false;

    //Upgrade stats
    private int attackBonus = 0;

    public Paladin(String name) {
        super(name, 130, 150);
    }

    @Override
    public void attack(GameCharacter target) throws InsufficientEnergyException, InvalidActionException {
        int attDmg = 15 + attackBonus;
        int cost = 5;
        if (this.energy < cost)
            throw new InsufficientEnergyException(this.name + " has insufficient energy to attack! (needs " + cost + ", has " + energy + ")");

        if (target.isDeath())
            throw new InvalidActionException(name + " cannot attack! (" + target.getName() + " is already dead)");

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
        System.out.println(this.name + " attacked " + target.getName() + " and dealt " + attDmg + " damage!");
    }

    @Override
    public void defend() {
        this.defending = true;
        System.out.println(this.name + " raised a holy shield! Next hit will deal 50% less damage!");
    }

    @Override
    public void useAbility(GameCharacter target) throws InsufficientEnergyException, InvalidActionException {
        int attDmg = 25 + attackBonus;
        int cost = 12;
        if (this.energy < cost)
            throw new InsufficientEnergyException(this.name + " has insufficient energy for Holy Strike! (needs " + cost + ", has " + energy + ")");

        if (target.isDeath())
            throw new InvalidActionException(name + " cannot use Holy Strike! (" + target.getName() + " is already dead)");

        // Check if target is defending and apply their mitigation
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
        System.out.println(this.name + " used Holy Strike on " + target.getName() + " and dealt " + attDmg + " damage!");
    }

    // --- Healable interface ---
    @Override
    public void heal() {
        int healAmount = 30;
        int cost = 10;
        if (this.energy < cost) {
            System.out.println(this.name + " tried to heal but has insufficient energy!");
            return;
        }
        this.health += healAmount;
        this.energy -= cost;
        System.out.println(this.name + " healed for " + healAmount + " HP! [HP: " + this.health + "]");
    }

    // --- Upgradeable interface ---
    @Override
    public void upgrade(String stat) throws InvalidActionException {
        switch (stat.toLowerCase()) {
            case "strength":
                this.attackBonus += 5;
                System.out.println(this.name + " upgraded Strength! Attack bonus is now +" + attackBonus + ".");
                break;
            case "health":
                this.health += 20;
                System.out.println(this.name + " upgraded Health! HP is now " + this.health + ".");
                break;
            case "energy":
                this.energy += 20;
                System.out.println(this.name + " upgraded Energy! Energy is now " + this.energy + ".");
                break;
            default:
                throw new InvalidActionException("Invalid stat \"" + stat + "\"! Valid options: strength, health, energy.");
        }
    }

    public boolean isDefending() {
        return this.defending;
    }

    public void changeDefending() {
        this.defending = !this.defending;
    }
}