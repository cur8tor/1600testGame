package adventure_game;
import java.util.ArrayList;
import adventure_game.items.Consumable;
import java.util.Scanner;
/**
 * The abstract class Character represents a generic character in the game. 
 * It defines properties such as health, mana, base damage, and other attributes
 * such as being vulnerable or invincible for certain periods of time. 
 * The class also includes methods for attacking, defending, using items, and 
 * modifying health. Any subclass of Character must implement the abstract method 
 * takeTurn() which determines how the character behaves in combat.
 */
abstract public class Character{
    /**
     * The max health of the character.
     */
    private int maxHealth;
    /**
     * The current health of the character.
     */
    private int health;
    /**
     * The max mana of the character.
     */
    private int maxMana;
    /**
     * The current mana of the character.
     */
    private int mana;
    /**
     * The base damage of the character.
     */
    private int baseDamage;
    /**
     * The name of the character.
     */
    private String name;
    /**
     * The items field represents the list of consumable items that the character currently possesses.
     */
    protected ArrayList<Consumable> items;
    /**
     * Character Conditions:
     */
    /**
     * number of turns Character is vulnerable
     */
    private int turnsVulnerable;
    /**
     * number of turns Character takes no damage
     */
    private int turnsInvincible;
    /**
     * number of turns Character gets no actions
     */
    private int turnsStunned;
    /**
     * buffer factor for next attack. If 2.0, the next attack will do double damage
     */
    private double tempDamageBuff;
    /**
     * Creates a new character with the given parameters.
     * @param name        The name of the character.
     * @param health      The initial and maximum health of the character.
     * @param mana        The initial and maximum mana of the character.
     * @param damage  The base damage of the character.
     */
    public Character(String name, int health, int mana, int damage){
        this.name = name;
        this.maxHealth = health;
        this.health = health;
        this.maxMana = mana;
        this.mana = mana;
        this.baseDamage = damage;
        this.tempDamageBuff = 1.0;
        items = new ArrayList<Consumable>();
    }
    /**
     * Returns a String representation of the Character object.
     * @return A String containing the name, health, mana, and base damage of the Character object.
     */
    @Override
    public String toString(){
        String output;
        output = "";
        output += "Name " + getName() + "\n";
        output += "hp " + getHealth() + "\n";
        output += "mana " + getMana() + "\n";
        output += "damage " + getBaseDamage() + "\n";
        return output;
    }
    /**
     * Get the name of this Character
     * @return the name of this Character
     */
    public String getName(){
        return this.name;
    }
    /**
     * Get the current health of this Character
     * @return the current health of this Character
     */
    public int getHealth(){
        return this.health;
    }
    /**
     * Get the maximum health of this Character
     * @return the maximum health of this Character
     */
    public int getMaxHealth(){
        return this.maxHealth;
    }
    /**
     * Get the maximum mana of this Character
     * @return the maximum mana of this Character
     */
    public int getMaxMana(){
        return this.maxMana;
    }
    /**
     * Get the base damage of this Character
     * @return the base damage of this Character
     */
    public int getBaseDamage(){
        return this.baseDamage;
    }
    /**
     * Check if this Character is alive
     * @return true if this Character's health is greater than 0, false otherwise
     */
    public boolean isAlive(){
        return this.health > 0;
    }
    /**
     * Get items in inventory array list
     * @return items in inventory
     */
    public ArrayList<Consumable> getItems() {
        return items;
    }
    /**
     * Abstract method that determines how the character behaves during combat. 
     * It takes another Character object as a parameter and allows the character 
     * to select their actions during combat based on the state of the game.
     * @param owner is the player
     * @param other is the enemy
     * @param action is the chosen action
     */
    abstract void takeTurn(Character owner, Character other, int action);
    /**
     * Method that allows the character to attack another character.
     * It takes another Character object as a parameter and deals damage 
     * to the target based on the character's base damage and a random modifier. 
     * The method also takes into account whether the target is invincible 
     * or vulnerable at the time of the attack.
     * @param other is enemy the player is attacking.
     */
    public void attack(Character other){
        if(other.isInvincible()){
            System.out.printf("%S is unable to attack %S!\n", 
                                this.getName(), 
                                other.getName());
            other.decreaseTurnsInvincible();
            return;
        }
        double modifier = Game.rand.nextDouble();
        modifier = (modifier*0.4) + 0.8;
        int damage = (int)(this.baseDamage * modifier);
        // apply temporary damage buff, then reset it back to 1.0
        damage *= this.tempDamageBuff;
        this.tempDamageBuff = 1.0;
        if(other.isVulnerable()){
            damage *= 1.5;
            other.decreaseTurnsVulnerable();
        }
        other.modifyHealth(-damage);
        System.out.printf("%s dealt %d damage to %s\n", this.getName(), damage, other.getName());
        System.out.printf("%s now has...\n%s\n", other.getName(), other);
    }
    /**
     * Method that allows the character to defend against an attack. 
     * It gives the character a chance to become invincible for the 
     * next turn or become vulnerable for the next turn.
     * @param other is enemy attack the player is defending against.
     */
    public void defend(Character other){
        double chance = Game.rand.nextDouble();
        if(chance <=0.75){
            System.out.printf("%s enters a defensive posture and charges up their next attack!\n", this.getName());
            this.setAsInvincible(1);
            this.setTempDamageBuff(2.0);
        } else {
            System.out.printf("%s stumbles. They are vulnerable for the next turn!\n", this.getName());
            this.setAsVulnerable(1);
        }
    }
    /**
     * Method that modifies the health of the character. It takes an integer 
     * modifier as a parameter and adjusts the character's health by that amount. 
     * If the resulting health is less than 0, it is set to 0. If the resulting 
     * health is greater than the character's max health, it is set to the max health.
     * @param modifier is the amount to modify health by.
     */
    public void modifyHealth(int modifier) {
        this.health += modifier;
        if(this.health < 0){
            this.health = 0;
        }
        if(this.health > this.getMaxHealth()){
            this.health = this.getMaxHealth();
        }
    }
    /* CONDITIONS */
    /**
     * Set the number of turns that the character is vulnerable.
     * @param numTurns the number of turns that the character is vulnerable.
     */
    public void setAsVulnerable(int numTurns){
        this.turnsVulnerable = numTurns;
    }
    /**
     * Check whether the character is currently vulnerable.
     * @return true if the character is vulnerable; false otherwise.
     */
    public boolean isVulnerable(){
        return this.turnsVulnerable > 0;
    }
    /**
     * Decrease the number of turns that the character is vulnerable by one.
     */
    public void decreaseTurnsVulnerable(){
        this.turnsVulnerable--;
    }
    /**
     * Set the number of turns that the character is invincible.
     * @param numTurns the number of turns that the character is invincible.
     */
    public void setAsInvincible(int numTurns){
        this.turnsInvincible = numTurns;
    }
    /**
     * Check whether the character is currently invincible.
     * @return true if the character is invincible; false otherwise.
     */
    public boolean isInvincible(){
        return this.turnsInvincible > 0;
    }
    /**
     * Decrease the number of turns that the character is invincible by one.
     */
    public void decreaseTurnsInvincible(){
        this.turnsInvincible--;
    }
    /**
     * Set the number of turns that the character is stunned.
     * @param numTurns the number of turns that the character is stunned.
     */
    public void setAsStunned(int numTurns){
        this.turnsStunned = numTurns;
    }
    /**
     * Check whether the character is currently stunned.
     * @return true if the character is stunned; false otherwise. 
     */
    public boolean isStunned(){
        return this.turnsStunned > 0;
    }
    /**
     * Decrease the number of turns that the character is stunned by one.
     */
    public void decreaseTurnsStunned(){
        this.turnsStunned--;
    }
    /**
     * Set the temporary damage buffer. 
     * 
     * This is a multiplicative factor which will modify the damage 
     * for the next attack made by this Character. After the next 
     * attack, it will get reset back to 1.0
     * 
     * @param buff the multiplicative factor for the next attack's
     * damage.
     */
    public void setTempDamageBuff(double buff){
        this.tempDamageBuff = buff;
    }
    /**
     * Set the temporary damage buffer. 
     * 
     * This is a multiplicative factor which will modify the damage 
     * for the next attack made by this Character. After the next 
     * attack, it will get reset back to 1.0
     * 
     * @param modDamage the multiplicative factor for the next attack's
     * damage.
     */
    public void setDamage(double modDamage){
        this.baseDamage += modDamage;
    }
    /**
     * Adds the specified Consumable item to this character's inventory.
     *
     * @param item the item to be added to the character's inventory.
     */
    public void obtain(Consumable item){
        items.add(item);
    }
    /**
     * Allow the character to use an item from their inventory.
     * 
     * The method prompts the user to select an item from the character's inventory 
     * and uses the item on either the owner or another target character. The used 
     * item is removed from the inventory after use.
     * 
     * @param owner The character using the item.
     * @param other The target character that the item will affect.
     * @param scanner A scanner to collect user input.
     */
    public void useItem(Character owner, Character other, int action) {
        // Do nothing in the base class.
    }
    /**
     * Checks if the character has any consumable items.
     *
     * @return true if the character has any items, false otherwise.
     */
    public boolean hasItems(){
        return !items.isEmpty();
    }
    /**
     * Allow the player to cast a spell on the opponent.
     *
     * The method reduces the player's mana by 3 and halves the 
     * health of the opponent. If the opponent's health is already 
     * at 1, the spell does nothing.
     *
     * @param other the character to cast the spell on.
     */
    public void castSpell(Character other){
        if(other.getHealth() <= 1){
            System.out.printf("%S's spell did nothing!\n", this.getName());
            return;
        }
        System.out.printf("\n%S casts a spell on %S!\n", this.getName(), other.getName());
        this.setMana(-3);
        other.modifyHealth(-(other.getHealth() / 2));
        System.out.printf("%s now has...\n%s\n", other.getName(), other);
    }
    /**
     * Get the current mana of this Character
     * @return the current mana of this Character
     */
    public int getMana(){
        return this.mana;
    }
    /**
     * Modify and set the player's mana by the given modifier.
     * Check if mana is less than 0 set to 0.
     * Check if mana is more than max and set to max.
     * @param Tmana the amount to modify the player's mana by.
     */
    public void setMana(int Tmana){
        this.mana += Tmana;
        if(this.getMana() < 0){
            this.mana = 0;
        }
    }
    /**
     * Increase the player's mana by 1.
     */
    public void chargeUpMana() {
        this.setMana(+1);
        System.out.printf("\n%S charges up their mana to %d.\n\n", getName(), getMana());
    }
}