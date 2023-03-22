package adventure_game;
import java.util.Scanner;
import adventure_game.items.Consumable;
import adventure_game.items.HealingPotion;
import adventure_game.items.RagePotion;
import java.util.ArrayList;

/**
 * Constructs a new Player object with the given name, health, mana, and base damage.
 * 
 * The class Player represents a specific type of character in the game. 
 * It extends the abstract class Character and implements the takeTurn() 
 * method to allow the player to select their actions during combat. 
 * The Player class also includes a constructor for creating a new player, 
 * as well as methods for using items and checking if the player has any items.
 */
public class Player extends Character{
    /**
     * @param name The name of the player.
     * @param health The initial health of the player.
     * @param mana The initial mana of the player.
     * @param baseDamage The base damage of the player's attacks.
     */
    public Player(String name, int health, int mana, int baseDamage){
        super(name, health, mana, baseDamage);
        items = new ArrayList<>();

        this.obtain(new HealingPotion());
        this.obtain(new RagePotion());
    }

    /**
     * Get the current items of this Character
     * @return the current items of this Character
     */
    public ArrayList<Consumable> getItems() {
        return items;
    }

    /**
     * Remove the specified item from the player's inventory.
     * 
     * @param item The item to be removed.
     */
    public void removeItem(Consumable item) {
        items.remove(item);
    }
    
    /**
     * Determines the player's action during their turn in combat. The player may attack, defend,
     * or use an item if they have any available. If the player is stunned, they will be unable
     * to take any action and will lose their turn.
     * @param other The opposing character in the combat.
     */
    @Override
    public void takeTurn(Character owner, Character other, int action) {
        switch (action) {
            case 1:
                System.out.println("\nYou chose to ATTACK!");
                owner.attack(other);
                break;
            case 2:
                System.out.println("\nYou chose to defend?!");
                owner.defend(other);
                break;
            case 3:
                System.out.println("\nYou chose to use an item.");
                if (owner.hasItems()) {
                    int i = 1;
                    System.out.printf("Choose the number of an item from your inventory:\n");
                    for (Consumable item : owner.getItems()) {
                        System.out.printf("  %d: %s\n", i, item.getClass().getSimpleName());
                        i++;
                    }
                    System.out.print("Enter your choice: ");
                    Scanner scanner4 = new Scanner(System.in);
                    int action4 = scanner4.nextInt();
                    owner.useItem(owner, other, action4); // You may need to modify the useItem method to not use a Scanner
                    scanner4.close();
                } else {
                    System.out.printf("%s has no items to use.\n\n", owner.getName());
                }
                break;
            case 4:
                if (owner.getMana() >= 3) {
                    owner.castSpell(other);
                } else {
                    System.out.print("Nope, your getmana() count: ");
                    System.out.println(owner.getMana());
                    System.out.println("Not enough mana to cast a spell. Choose another action.");
                }
                break;
            case 5:
                owner.chargeUpMana();
                break;
            default:
                System.out.println("\nInvalid choice. Please choose a valid option.");
                break;
        }
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
     */
    @Override
    public void useItem(Character owner, Character other, int action) {
        if (owner instanceof Player) {
            Player player = (Player) owner;
            int choice = action;
            System.out.println("");
            if (choice < 1 || choice > player.getItems().size()) {
                System.out.println("Invalid choice!");
                return;
            }
            Consumable item = player.getItems().get(choice - 1);
            System.out.println("Character to use item on: ");
            System.out.println(owner);
            item.consume(owner);
            player.removeItem(item);
            System.out.println("Used item and removed from inventory.\nConsumer now is:");
            System.out.println(owner);
        }
    }
}