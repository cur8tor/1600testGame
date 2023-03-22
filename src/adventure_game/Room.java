package adventure_game;
import adventure_game.items.Consumable;
import java.util.ArrayList;

public class Room {
    private Room east;
    private Room north;
    private Room west;
    private Room south;
    private NPC opponent;
    private ArrayList<Consumable> items;
    private String name;
    private String description;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        items = new ArrayList<Consumable>();
    }

    /**
     * Get items in inventory array list
     * @return items in inventory
     */
    public ArrayList<Consumable> getItems() {
        return items;
    }
    /**
     * Adds the specified Consumable item to this character's inventory.
     *
     * @param item the item to be added to the character's inventory.
     */
    public void obtain(Consumable item){
        items.add(item);
    }
    /**
     * Checks if the character has any consumable items.
     *
     * @return true if the character has any items, false otherwise.
     */
    public boolean hasItems(){
        return !items.isEmpty();
    }

    /**
     * Remove the specified item from the player's inventory.
     * 
     * @param item The item to be removed.
     */
    public void removeItem(Consumable item) {
        items.remove(item);
    }

    public Room getEast() {
        return east;
    }

    public void setEast(Room east) {
        this.east = east;
    }

    public Room getNorth() {
        return north;
    }

    public void setNorth(Room north) {
        this.north = north;
    }

    public Room getWest() {
        return west;
    }

    public void setWest(Room west) {
        this.west = west;
    }

    public Room getSouth() {
        return south;
    }

    public void setSouth(Room south) {
        this.south = south;
    }

    public NPC getOpponent() {
        return opponent;
    }

    public void setOpponent(NPC opponent) {
        this.opponent = opponent;
    }

    public void setItems(ArrayList<Consumable> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}