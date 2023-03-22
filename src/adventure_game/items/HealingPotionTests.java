package adventure_game.items;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import adventure_game.Character;
import adventure_game.Player;

import org.junit.jupiter.api.BeforeEach;

public class HealingPotionTests {

    private Character c;
    @BeforeEach
    void setup() {
        c = new Player("Hero", 100, 9, 7);
    }
    /*
        Test the consume() method of the HealingPotion by checking 
        if the character's health has increased after consuming the potion 
    */
    @Test void testHealingPotion() {
        int initialHealth = c.getHealth();
        Consumable item = new HealingPotion();
        item.consume(c);
        int expectedHealth = initialHealth + Math.min(12, c.getMaxHealth() - initialHealth);
        assertEquals(expectedHealth, c.getHealth());
    }
}