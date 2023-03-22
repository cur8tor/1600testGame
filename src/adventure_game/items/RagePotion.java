package adventure_game.items;
import adventure_game.Character;
import adventure_game.items.RagePotion;

/**
 * The RagePotion class represents a consumable item in the game that
 * increases damage by 5 to the character that consumes it.
 */
public class RagePotion implements Consumable {
    /**
     * Consumes the item and applies its effects to the given owner.
     * This method adjusts the owner's damage by the rage amount of 5.
     *
     * @param owner The owner of the consumable item.
     */
    public void consume(Character owner){
        int rageAmount = 5;
        owner.setDamage(rageAmount);
        System.out.print("FEEL THE RAGE!\n\n");
    }
}