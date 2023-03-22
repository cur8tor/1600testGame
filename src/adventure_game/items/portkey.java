package adventure_game.items;
import adventure_game.Character;
import adventure_game.items.portkey;

/**
 * The portkey class adds the portkey to player inventory and has no effect.
 * However, once the player posesses the key they win the game.
 */
public class portkey implements Consumable {
    /**
     * Consumes the item.
     *
     * @param owner The owner of the consumable item.
     */
    public void consume(Character owner){
        System.out.print("winner winner!\n\n");
    }
}