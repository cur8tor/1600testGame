package adventure_game.items;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import adventure_game.Character;
import adventure_game.Player;

import org.junit.jupiter.api.BeforeEach;

public class RagePotionTests {

    private Character c;
    @BeforeEach
    void setup() {
        c = new Player("Hero", 100, 9, 7);
    }
    /**
     * Test the consume() method of the RagePotion by checking 
     * if the character's damage has increased after consuming the potion 
     */
    @Test void testRagePotion() {
        int initialDamage = c.getBaseDamage();
        Consumable item = new RagePotion();
        item.consume(c);
        int expectedDamage = initialDamage+5; 
        assertEquals(expectedDamage, c.getBaseDamage());
        assertEquals(12, c.getBaseDamage());

        Player player = new Player("TestPlayer", 100, 10, 10);
        Consumable ragePotion = new RagePotion();
        ragePotion.consume(player);
        player.removeItem(ragePotion);
        assertEquals(15, player.getBaseDamage());
    }
}