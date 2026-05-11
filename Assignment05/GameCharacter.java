package Assignment05;

import java.util.Objects;

public abstract class GameCharacter {
    protected String name;
    protected int health;
    protected int energy;

    public GameCharacter(String name, int health, int energy){
        this.energy = energy;
        this.health = health;
        this.name = name;
    }

    // Abstract methods
    public abstract void attack(GameCharacter target) throws InsufficientEnergyException, InvalidActionException;
    public abstract void defend();
    public abstract void useAbility(GameCharacter target) throws InsufficientEnergyException, InvalidActionException;

    public void takeDamage(int damage) {
        if (damage >= this.health) this.health = 0;
        else this.health-=damage;
    }

    public boolean isDeath() {return this.health <= 0;}

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public int getEnergy() {
        return energy;
    }

    @Override
    public int hashCode() {
        int hash = Objects.hash(this.energy, this.name, this.health);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GameCharacter other = (GameCharacter) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (health != other.health)
            return false;
        if (energy != other.energy)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name + " - [EN: " + this.energy + ", HP: " + this.health + "]";
    }

    
}
