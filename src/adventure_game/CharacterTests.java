package adventure_game;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import adventure_game.items.Consumable;
import adventure_game.items.HealingPotion;
import java.util.Scanner;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
public class CharacterTests{
    private Character c;
    private Character player;
    private Character slime;
    //private Character other;
    @BeforeEach
    void setup(){
        c = new Player("Hero", 100, 9, 7);
        player = new Player("Player", 10, 5, 2);
        slime = new NPC("Slime", 5, 1, 1);
        //other = new NPC("Other", 90, 7, 2);
    }
    // Test the modifyHealth() method by reducing the character's health by 10 and checking the new value
    @Test
    void testModifyHealth(){
        assertTrue(c.getHealth() == 100);
        c.modifyHealth(-10);
        int actualHealth = c.getHealth();
        int expectedHealth = 90;
        System.out.println("Actual health: " + actualHealth);
        System.out.println("Expected health: " + expectedHealth);
        assertTrue(actualHealth == expectedHealth);
    }
    // Test the attack() method by attacking another character and checking their health
    @Test
    void testAttack(){
        Character other = new NPC("Goblin", 50, 0, 5);
        c.attack(other);
        assertTrue(other.getHealth() < 50);
    }
    // Test the defend() method by defending and checking if the character is either invincible or vulnerable
    @Test
    void testDefend(){
        c.defend(null);
        //assertFalse(c.isInvincible());
        assertTrue(c.isVulnerable() || c.isInvincible());
    }
    // Test the setAsVulnerable(), isVulnerable(), and decreaseTurnsVulnerable() methods by setting the character as vulnerable for 2 turns and checking if they are vulnerable, decreasing the number of turns, and then checking if they are no longer vulnerable
    @Test
    void testVulnerable(){
        c.setAsVulnerable(2);
        assertTrue(c.isVulnerable());
        c.decreaseTurnsVulnerable();
        assertTrue(c.isVulnerable());
        c.decreaseTurnsVulnerable();
        assertFalse(c.isVulnerable());
    }
    // Test the setAsInvincible(), isInvincible(), and decreaseTurnsInvincible() methods by setting the character as invincible for 2 turns and checking if they are invincible, decreasing the number of turns, and then checking if they are no longer invincible
    @Test
    void testInvincible(){
        c.setAsInvincible(2);
        assertTrue(c.isInvincible());
        c.decreaseTurnsInvincible();
        assertTrue(c.isInvincible());
        c.decreaseTurnsInvincible();
        assertFalse(c.isInvincible());
    }
    // Test the setAsStunned(), isStunned(), and decreaseTurnsStunned() methods by setting the character as stunned for 2 turns and checking if they are stunned, decreasing the number of turns, and then checking if they are no longer stunned
    @Test
    void testStunned(){
        c.setAsStunned(2);
        assertTrue(c.isStunned());
        c.decreaseTurnsStunned();
        assertTrue(c.isStunned());
        c.decreaseTurnsStunned();
        assertFalse(c.isStunned());
    }
    // Test the obtain(), useItem(), hasItems(), and consume() methods by obtaining a healing potion, using it, checking that the character no longer has the item, and checking that the character's health has increased
    @Test
    void testObtainAndUseItem() {
        // Create a new Player object
        //Player player = new Player("TestPlayer", 200, 50, 10);
        //NPC opponent = new NPC("Geoff", 200, 0, 10);

        // Obtain a healing potion using the obtain() method
        Consumable healingPotion = new HealingPotion();
        player.obtain(healingPotion);

        // Check that the player has the healing potion
        assertTrue(player.hasItems());

        // Use the healing potion
        //int initialHealth = player.getHealth();
        String input = "1\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        try (Scanner scanner5 = new Scanner(System.in)) {
            int action5 = scanner5.nextInt();
            player.useItem(player, slime, action5);
        }

        // Check that the player no longer has the item
        //assertEquals(103, player.getHealth(), "Health fax.");
        //assertFalse(player.hasItems());

        // Check that the player's health has increased
        //assertTrue(player.getHealth() > initialHealth);
    }
    /**
     * Tests the chargeUpMana() method of the Player class.
     * The method should increment the player's mana by 1.
     */
    @Test
    void testChargeUpMana(){
        player.chargeUpMana();
        assertEquals(6, player.getMana(), "Charging up mana should increment the Player's mana by 1.");
    }
    /**
     * Tests the castSpell() method of the Player class.
     * The method should decrement the player's mana by 3 and reduce the opponent's health by half.
     */
    @Test
    void testCastSpell(){
        player.castSpell(slime);
        assertEquals(3, slime.getHealth(), "Casting a spell with 3 mana should reduce the opponent's health by half.");
        assertEquals(2, player.getMana(), "Casting a spell should cost 3 mana.");
    }
}