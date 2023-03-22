package adventure_game;

import adventure_game.items.Consumable;
import adventure_game.items.HealingPotion;
import adventure_game.items.RagePotion;
import adventure_game.items.portkey;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

/**
 * This class contains JUnit tests for the Game class.
 */
class GameTests {

    private Game game;

    /**
     * Sets up the testing environment before each test.
     */
    @BeforeEach
    void setUp() {
        game = new Game();
    }

    /**
     * Tests the creation of a player.
     */
    @Test
    void testPlayerCreation() {
        // Set up input for testing
        String input = "TestPlayer\n5\n5\n10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        
        // Create player with input stream
        game.createPlayer(in);

        // Test that the player was created with correct stats
        assertEquals("TestPlayer", game.getPlayer().getName());
        assertEquals(250, game.getPlayer().getHealth());
        assertEquals(5, game.getPlayer().getBaseDamage());
        assertEquals(30, game.getPlayer().getMana());
    }

    /**
    * This class tests the enterCombat method of the Game class.
    */
    @Test
    void testEnterCombat() {
        // create a player with given stats
        String input = "TestPlayer\n5\n5\n10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.createPlayer(in);
        
        // create an NPC opponent
        NPC opponent = new NPC("Opponent", 50, 5, 5);
        
        // call the takeTurn method with action 1 (attack) and verify expected behavior after combat
        game.getPlayer().takeTurn(game.getPlayer(), opponent, 1); 
        assertTrue(game.getPlayer().isAlive()); // assert that the player is still alive
        assertTrue(opponent.isAlive()); // assert that the opponent is still alive
    }
    
    /**
     * Tests the ability of a player to navigate between rooms based on input.
     * The method loads the rooms and creates a player using an input stream. 
     * It then simulates user input to navigate through different rooms  
     * and asserts if the player's current room is equal to the expected room. 
     *
     * @param none
     * @return void
     */
    @Test
    void testNavigateRooms() {
        // Initialize game object
        Game game = new Game();

        // Load the rooms into the game
        game.loadRooms();

        // Create a player using an input stream
        String input = "TestPlayer\n5\n5\n10\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        game.createPlayer(in);

        // Simulate user input to navigate through different rooms
        String simulatedInput = "North\nSouth\nEast\nWest\nquit\n"; // Add "quit" to the end
        InputStream navigationInput = new ByteArrayInputStream(simulatedInput.getBytes());
        game.navigateRooms(navigationInput);

        // Assert if the player is in the expected room
        assertEquals(game.currentRoom.getName(), " Foyer");
    }

    /**
     * Tests the loadRooms() method in the Game class.
     */
    @Test
    void testLoadRooms() {
        game.loadRooms();
        assertNotNull(game.rooms);
        assertTrue(game.rooms.size() > 0);
        assertNotNull(game.currentRoom);
    }

    /**
     * Tests the testPlayerObtainItem() method in the Game class.
     */
    @Test
    void testPlayerObtainItem() {
        Player player = new Player("TestPlayer", 100, 10, 10);
        Consumable healingPotion = new HealingPotion();

        player.obtain(healingPotion);
        assertTrue(player.getItems().contains(healingPotion));
    }

    /**
     * This class tests the usage of the Player's items.
     */
    @Test
    void testPlayerUseItem() {
        /**
         * Creates a new Player object with given parameters
         * @param name      The name of the player.
         * @param health    The health value of the player.
         * @param attack    The attack value of the player.
         * @param defense   The defense value of the player.
         */
        Player player = new Player("TestPlayer", 100, 10, 10);
        
        /**
         * Creates a new Consumable healingPotion object to be obtained by the player
         */
        Consumable healingPotion = new HealingPotion();

        /**
         * Obtain the created item and check if the item is in the player's inventory
         */
        player.obtain(healingPotion);
        assertTrue(player.getItems().contains(healingPotion));

        /**
         * Remove the item and check if it is not in the inventory
         */
        player.removeItem(healingPotion);
        assertFalse(player.getItems().contains(healingPotion));
    }

    /**
     * This class tests the usage of the Player's items.
     */
    @Test
    void testPlayerUseHealingPotion() {
        Player player = new Player("TestPlayer", 50, 10, 10);
        int initialHealth = player.getHealth();
        Consumable item = new HealingPotion();
        item.consume(player);
        int expectedHealth = initialHealth + Math.min(12, player.getMaxHealth() - initialHealth);
        assertEquals(expectedHealth, player.getHealth());
    }

    /**
     * This class tests the usage of the Player's items.
     */
    @Test
    void testPlayerUseRagePotion() {
        Player player = new Player("TestPlayer", 100, 10, 10);
        Consumable ragePotion = new RagePotion();

        ragePotion.consume(player);
        player.removeItem(ragePotion);

        assertEquals(15, player.getBaseDamage());
    }

    /**
     * This class tests the usage of the Player's items.
     */
    @Test
    void testPlayerObtainPortkey() {
        Player player = new Player("TestPlayer", 100, 10, 10);
        Consumable portkeyItem = new portkey();

        player.obtain(portkeyItem);
        assertTrue(player.getItems().contains(portkeyItem));
        portkeyItem.consume(player);
        assertTrue(player.getItems().contains(portkeyItem));
    }
}
