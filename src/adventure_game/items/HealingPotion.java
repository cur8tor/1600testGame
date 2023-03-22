package adventure_game.items;
import adventure_game.Character;
import adventure_game.Game;
import adventure_game.items.HealingPotion;
/**
 * The HealingPotion class represents a consumable item in the game that
 * restores a certain amount of health to the character that consumes it.
 */
public class HealingPotion implements Consumable {
    /**
     * Consumes the item and applies its effects to the given owner.
     * This method calculates the healing provided by the consumable and adjusts the owner's health
     * accordingly. If the calculated healing exceeds the difference between owner's maximum health and
     * current health, then the healing is capped at that difference.
     *
     * @param owner The owner of the consumable item.
     */
    public void consume(Character owner){
        int hitPoints = calculateHealing();
        int hitPointsFromMax = owner.getMaxHealth() - owner.getHealth();
        if(hitPoints > hitPointsFromMax){
            hitPoints = hitPointsFromMax;
        }
        owner.modifyHealth(50);
        System.out.printf("You heal for %d points, back up to %d/%dhp.\n\n", 50, owner.getHealth(), owner.getMaxHealth());
    }
    /**
     * Calculate the amount of healing done by consuming this consumable.
     *
     * The healing amount is equivalent to rolling 4d4+4.
     * Four random values in the range [1,4] are summed up and 4 is added to that.
     *
     * @return the amount of healing points produced by consuming this consumable.
     */
    private int calculateHealing(){
        // Equivalent to rolling 4d4 + 4
        // sum up four random values in the range [1,4] and
        // add 4 to that.
        int points = Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        points += Game.rand.nextInt(4)+1;
        return points + 4;
    }
}