package adventure_game;

import adventure_game.items.Consumable;
import adventure_game.items.HealingPotion;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class RoomTest {

    @Test
    void testGetItems(){
        Room room = new Room("Room 1", "This is the first room.");
        ArrayList<Consumable> items = room.getItems();

        assertNotNull(items);
        assertTrue(items.isEmpty());
    }

    @Test
    void testAddItem(){
        Room room = new Room("Room 1", "This is the first room.");
        Consumable item = new HealingPotion();
        room.obtain(item);

        ArrayList<Consumable> items = room.getItems();
        assertEquals(1, items.size());
        assertTrue(items.contains(item));
    }

    @Test
    void testHasItems(){
        Room room = new Room("Room 1", "This is the first room.");
        assertFalse(room.hasItems());

        Consumable item = new HealingPotion();
        room.obtain(item);

        assertTrue(room.hasItems());
    }

    @Test
    void testRemoveItem(){
        Room room = new Room("Room 1", "This is the first room.");

        Consumable item1 = new HealingPotion();
        Consumable item2 = new HealingPotion();
        room.obtain(item1);
        room.obtain(item2);

        ArrayList<Consumable> items = room.getItems();
        assertEquals(2, items.size());
        assertTrue(items.contains(item1));
        assertTrue(items.contains(item2));

        room.removeItem(item1);

        items = room.getItems();
        assertEquals(1, items.size());
        assertFalse(items.contains(item1));
        assertTrue(items.contains(item2));
    }

    @Test
    void testSetAndGetDirections(){
        Room room1 = new Room("Room 1", "This is the first room.");
        Room room2 = new Room("Room 2", "This is the second room.");

        assertNull(room1.getNorth());
        assertNull(room1.getEast());
        assertNull(room1.getSouth());
        assertNull(room1.getWest());

        room1.setNorth(room2);
        room1.setEast(room2);
        room1.setSouth(room2);
        room1.setWest(room2);

        assertEquals(room2, room1.getNorth());
        assertEquals(room2, room1.getEast());
        assertEquals(room2, room1.getSouth());
        assertEquals(room2, room1.getWest());
    }

    @Test
    void testGetAndSetOpponent(){
        Room room = new Room("Room 1", "This is the first room.");
        NPC opponent = new NPC("Goblin", 50, 0, 5);

        assertNull(room.getOpponent());

        room.setOpponent(opponent);

        assertEquals(opponent, room.getOpponent());
    }

    @Test
    void testGetNameAndDescription(){
        Room room = new Room("Room 1", "This is the first room.");

        assertEquals("Room 1", room.getName());
        assertEquals("This is the first room.", room.getDescription());

        room.setName("New Name");
        room.setDescription("New Description");

        assertEquals("New Name", room.getName());
        assertEquals("New Description", room.getDescription());
    }
}
