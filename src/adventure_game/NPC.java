package adventure_game;
import java.util.Scanner;
/**
 * The class NPC represents a specific type of character in the game 
 * that is controlled by the computer. It extends the abstract class Character 
 * and implements the takeTurn() method to allow the NPC to attack the opponent during combat. 
 * The NPC class includes a constructor for creating a new NPC.
 */
public class NPC extends Character{
    /**
     * Creates a new NPC with the given parameters.
     * @param name The name of the NPC.
     * @param health The initial and maximum health of the NPC.
     * @param mana The initial and maximum mana of the NPC.
     * @param baseDamage The base damage of the NPC.
     */
    public NPC(String name, int health, int mana, int baseDamage){
        super(name, health, mana, baseDamage);
    }
    /**
     * Determines how this NPC behaves in combat. If this NPC is stunned, they will be unable to take any actions. Otherwise, the NPC will attack the target Character.
     * @param owner the NPC
     * @param other the target Character that this NPC attacks
     * @param scanner the scanner for input
     */
    @Override
    public void takeTurn(Character owner, Character other, int action){
        if(this.isStunned()){
            this.decreaseTurnsStunned();
            System.out.printf("%S is unable to take any actions this turn!", this.getName());
            return;
        }
        this.attack(other);
    }
}