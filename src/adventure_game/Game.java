package adventure_game;
import java.util.Scanner;
import adventure_game.items.Consumable;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.io.IOException;
import adventure_game.items.HealingPotion;
import adventure_game.items.RagePotion;
import adventure_game.items.portkey;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.io.InputStream;

public class Game {
    static Scanner in = new Scanner(System.in);

    public static Random rand = new Random();

    public Player player;

    public Room currentRoom;

    public HashMap<Integer, Room> rooms = new HashMap<>();

    public static void main(String[] args){
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);
        game.loadRooms();
        System.out.println("--------------------");
        System.out.println("Let the game begin!");
        System.out.println("--------------------");
        game.createPlayer();
        game.navigateRooms();
        scanner.close();
        in.close();
    }

    public void createPlayer() {
        createPlayer(System.in);
    }

    public void createPlayer(InputStream inStream) {
        try (Scanner in = new Scanner(inStream)) {
            String name = null;
            int healthPoints = 200;
            int damagePoints = 0;
            int manaPoints = 0;
            int pointsLeft = 20;

            System.out.println("\nFirst, create a character with custom stats.");
            System.out.println("You are given 20 stat points so choose wisely.");
            System.out.println("(1 point gives +10 health)");
            System.out.println("(1 point gives +1 damage)");
            System.out.println("(1 point gives +3 mana)");

            boolean pointsAllocated = false;
            while (pointsLeft > 0) {
                System.out.printf("Your current stat points: %d\n", pointsLeft);
                while (name == null) {
                    System.out.print("Enter a name for your character: ");
                    String input = in.nextLine();
                    if (!input.isEmpty()) {
                        name = input;
                    } else {
                        System.out.println("Please enter a valid name for your character.");
                    }
                }
                System.out.printf("Enter the number of points you want to allocate to health (1 point gives +10 health): ");
                if (in.hasNextInt()) {
                    int hp = in.nextInt();
                    in.nextLine(); // consume the newline character
                    if (hp <= pointsLeft) {
                        healthPoints += hp * 10;
                        pointsLeft -= hp;
                    } else {
                        System.out.println("You don't have enough points. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    in.next(); // consume invalid input
                }
                System.out.printf("Enter the number of points you want to allocate to damage (1 point gives +1 damage): ");
                if (in.hasNextInt()) {
                    int dp = in.nextInt();
                    in.nextLine(); // consume the newline character
                    if (dp <= pointsLeft) {
                        damagePoints += dp;
                        pointsLeft -= dp;
                    } else {
                        System.out.println("You don't have enough points. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    in.next(); // consume invalid input
                }
                System.out.printf("Enter the number of points you want to allocate to mana (1 point gives +3 mana): ");
                if (in.hasNextInt()) {
                    int mp = in.nextInt();
                    in.nextLine(); // consume the newline character
                    if (mp <= pointsLeft) {
                        manaPoints += mp * 3;
                        pointsLeft -= mp;
                        //pointsAllocated = true;
                    } else {
                        System.out.println("You don't have enough points. Please try again.");
                    }
                } else {
                    System.out.println("Invalid input. Please enter an integer.");
                    in.next(); // consume invalid input
                }
                if (!pointsAllocated) {
                    System.out.println("Points not allocated correctly. Please try again.");
                } else {
                    break; // exit the loop
                }
            }
            player = new Player(name, healthPoints, manaPoints, damagePoints);
        }
        System.out.printf("\nYour character %s has been created with the following initial stats:\n%s\n", player.getName(), player);
    }
    
    public void enterCombat(NPC opponent){
        System.out.printf("%s and %s are in a battle. A battle to the death.\n", this.player.getName(), opponent.getName());
        System.out.printf("You are able to think fast and now have the first move.\n\n");
        while(true){
            System.out.printf("[PLAYER TURN:]\n");
            System.out.println("Do you want to...");
            System.out.println("  1: Attack");
            System.out.println("  2: Defend");
            System.out.println("  3: Use Item");
            System.out.println("  4: Cast Spell");
            System.out.println("  5: Charge up Mana");
            System.out.print("Enter your choice: ");
            int action = in.nextInt();
            player.takeTurn(player, opponent, action);
            
            if(!opponent.isAlive()){
                System.out.printf("%S is SLAIN!!\n",opponent.getName());
                // give item if slain enemy has item
                System.out.printf("enemy had a %s!\n", currentRoom.getItems().get(0).getClass().getSimpleName());
                if (currentRoom.getItems().size() > 0) {
                    Consumable item = currentRoom.getItems().get(0);
                    player.obtain(item);
                    // currentRoom.removeItem(item);
                    System.out.printf("You found a %s!\n", item.getClass().getSimpleName());
                    // check if the item the player obtained was a portkey and if so win the game
                    if (item.getClass().getSimpleName()=="portkey") {
                        System.out.println("Congratulations! You found the portkey and won the game!");
                        break;
                    }
                }
                break;
            }
            System.out.printf("[ENEMY TURN:]\n");
            opponent.takeTurn(this.player, opponent, action);
            if(!this.player.isAlive()){
                System.out.printf("%S is SLAIN!!\n",this.player.getName());
                break;
            }
        }
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    protected static final String LEVEL_FILE = "/Users/tannermartz/Desktop/tulane2023/1600/Project01/Nproject-01-adventure-game-cur8tor-main/levels/the-stilts.txt"; // Path to your level file
    
    public void loadRooms() {
        try {
            try (InputStream is = new FileInputStream(LEVEL_FILE)) {
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                // catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Level file not found: " + LEVEL_FILE);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(LEVEL_FILE))) {
            String line;
            int roomCount = -1;
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#")) {
                    continue; // Ignore comments
                }
                if (roomCount == -1) {
                    roomCount = Integer.parseInt(line.trim());
                } else if (rooms.size() < roomCount) {
                    String[] parts = line.strip().split(":");
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String description = parts[2];
                    rooms.put(id, new Room(name, description));
                    if (id == 0) {
                        currentRoom = rooms.get(0); // Set currentRoom to the first room created
                    }
                } else {
                    String[] parts = line.strip().split(":");
                    int id = Integer.parseInt(parts[0].trim());
                    Room currentRoom = rooms.get(id);
                    currentRoom.setEast(parts[1].equals("-1") ? null : rooms.get(Integer.parseInt(parts[1].trim())));
                    currentRoom.setNorth(parts[2].equals("-1") ? null : rooms.get(Integer.parseInt(parts[2].trim())));
                    currentRoom.setWest(parts[3].equals("-1") ? null : rooms.get(Integer.parseInt(parts[3].trim())));
                    currentRoom.setSouth(parts[4].equals("-1") ? null : rooms.get(Integer.parseInt(parts[4].trim())));
                }
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }

        NPC[] npcs = {
                new NPC("Goblin", 50, 0, 5),
                new NPC("Skeleton", 75, 0, 10),
                new NPC("Zombie", 100, 0, 15),
                new NPC("Werewolf", 150, 0, 20),
                new NPC("Vampire", 200, 0, 25)
        };
        
        Consumable[] items = {
                new HealingPotion(),
                new HealingPotion(),
                new HealingPotion(),
                new RagePotion(),
                new portkey()
        };
        
        Collections.shuffle(Arrays.asList(npcs));
        Collections.shuffle(Arrays.asList(items));
        
        List<Consumable> itemList = new ArrayList<>(Arrays.asList(items));
        List<Integer> roomIds = new ArrayList<>();

        for (int i = 0; i < 15; i++) {
            roomIds.add(i);
        }
        Collections.shuffle(roomIds);

        for (NPC npc : npcs) {
            if (roomIds.isEmpty()) {
                break;
            }
            int roomId = roomIds.get(0);
            roomIds.remove(0);
            Room room = rooms.get(roomId);
            room.setOpponent(npc);
            // Assign a random item to the room
            if (!itemList.isEmpty()) {
                int itemIndex = rand.nextInt(itemList.size());
                Consumable item = itemList.get(itemIndex);
                room.obtain(item);
                // Remove the assigned item from the list
                itemList.remove(itemIndex);
            }
        }

        // Print all rooms and their details for debugging purposes
        System.out.println("\n------------------");
        System.out.println("--Load room info--");
        System.out.println("------------------");
        for (Room room : rooms.values()) {
            System.out.println(room.getName());
            //System.out.println(room.getDescription());
            System.out.println("East: " + (room.getEast() == null ? "null" : room.getEast().getName()));
            System.out.println("North: " + (room.getNorth() == null ? "null" : room.getNorth().getName()));
            System.out.println("West: " + (room.getWest() == null ? "null" : room.getWest().getName()));
            System.out.println("South: " + (room.getSouth() == null ? "null" : room.getSouth().getName()));
            if (room.getOpponent() != null){
                System.out.println(room.getOpponent().getName());
            } else {
                System.out.println("No NPC in the room.");
            }
            for (Consumable item : room.getItems()) {
                System.out.printf("Room Items: %s\n",item.getClass().getSimpleName());
            }
            System.out.println();
        }
    }
    
    public void printRoomInfo() {
        System.out.println("\n-----------------------");
        System.out.print("You are in the:");
        System.out.println(currentRoom.getName());
        System.out.println("-----------------------");
        System.out.println(currentRoom.getDescription());
        System.out.println("");
    }
    
    public void navigateRooms() {
        navigateRooms(System.in);
    }

    public void navigateRooms(InputStream inStream) {
        try (Scanner in = new Scanner(inStream)) {
            printRoomInfo();
            boolean gameWon = false;
            boolean quit = false;
            while (!gameWon && !quit) {
                System.out.println("\nChoose a direction to move (East, North, West, South) or type 'quit' to end the game:");
                String input = in.nextLine().toLowerCase();
                Room nextRoom = null;
   
                if (input.equals("east") && currentRoom.getEast() != null) {
                    nextRoom = currentRoom.getEast();
                } else if (input.equals("north") && currentRoom.getNorth() != null) {
                    nextRoom = currentRoom.getNorth();
                } else if (input.equals("west") && currentRoom.getWest() != null) {
                    nextRoom = currentRoom.getWest();
                } else if (input.equals("south") && currentRoom.getSouth() != null) {
                    nextRoom = currentRoom.getSouth();
                } else if (input.equals("quit")) {
                    System.out.println("Thanks for playing!");
                    quit = true; // Set the quit variable to true
                    break;
                } else {
                    System.out.println("Invalid direction or there is no exit in that direction. Please try again.");
                    continue;
                }
                currentRoom = nextRoom;
                if (currentRoom.getOpponent() != null) {
                    System.out.println("An enemy has arrived!");
                    System.out.println(currentRoom.getOpponent().toString());
                    enterCombat(currentRoom.getOpponent());
                    if (!player.isAlive()) {
                        break;
                    }
                }

                // Check if the player has a portkey and if so, set gameWon to true
                if (player.hasItems()) {
                    for (Consumable item : player.getItems()) {
                        if (item.getClass().getSimpleName().equals("portkey")) {
                            gameWon = true;
                            break;
                        }
                    }
                }
                printRoomInfo();
            }
            
            if (gameWon) {
                System.out.println("Congratulations! You found the portkey and won the game!");
            }
        }
    }
}