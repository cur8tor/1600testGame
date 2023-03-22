// package adventure_game;

// import java.util.Scanner;
// import java.util.Random;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.util.HashMap;
// import java.io.IOException;

// public class Game {
//     static Scanner in = new Scanner(System.in);

//     public static Random rand = new Random();

//     private Player player;

//     private Room currentRoom;

//     private HashMap<Integer, Room> rooms;

//     public static void main(String[] args){
//         Game game = new Game();
//         game.loadRooms();
//         System.out.println("");
//         System.out.println("--------------------");
//         System.out.println("Let the game begin!");
//         System.out.println("--------------------\n");
//         game.createPlayer();
//         game.navigateRooms();
//     }

//     public void createPlayer(){
//         String name = null;
//         int healthPoints = 0;
//         int damagePoints = 0;
//         int manaPoints = 0;
//         int pointsLeft = 20;

//         System.out.println("\nFirst, create a character with custom stats.");
//         System.out.println("You are given 20 stat points so choose wisely.");
//         System.out.println("(1 point gives +10 health)");
//         System.out.println("(1 point gives +1 damage)");
//         System.out.println("(1 point gives +3 mana)");

//         while (pointsLeft > 0) {
//             System.out.printf("Your current stat points: %d\n", pointsLeft);
//             while (name == null) {
//                 System.out.print("Enter a name for your character: ");
//                 String input = in.nextLine();
//                 if (!input.isEmpty()) {
//                     name = input;
//                 } else {
//                     System.out.println("Please enter a valid name for your character.");
//                 }
//             }
//             System.out.printf("Enter the number of points you want to allocate to health (1 point gives +10 health): ");
//             if (in.hasNextInt()) {
//                 int hp = in.nextInt();
//                 if (hp <= pointsLeft) {
//                     healthPoints += hp * 10;
//                     pointsLeft -= hp;
//                 } else {
//                     System.out.println("You don't have enough points. Please try again.");
//                 }
//             } else {
//                 System.out.println("Invalid input. Please enter an integer.");
//                 in.next(); // consume invalid input
//             }
//             System.out.printf("Enter the number of points you want to allocate to damage (1 point gives +1 damage): ");
//             if (in.hasNextInt()) {
//                 int dp = in.nextInt();
//                 if (dp <= pointsLeft) {
//                     damagePoints += dp;
//                     pointsLeft -= dp;
//                 } else {
//                     System.out.println("You don't have enough points. Please try again.");
//                 }
//             } else {
//                 System.out.println("Invalid input. Please enter an integer.");
//                 in.next(); // consume invalid input
//             }
//             System.out.printf("Enter the number of points you want to allocate to mana (1 point gives +3 mana): ");
//             if (in.hasNextInt()) {
//                 int mp = in.nextInt();
//                 if (mp <= pointsLeft) {
//                     manaPoints += mp * 3;
//                     pointsLeft -= mp;
//                 } else {
//                     System.out.println("You don't have enough points. Please try again.");
//                 }
//             } else {
//                 System.out.println("Invalid input. Please enter an integer.");
//                 in.next(); // consume invalid input
//             }
//         }
//         player =     new Player(name, healthPoints, manaPoints, damagePoints);
//         System.out.printf("\nYour character %s has been created with the following initial stats:\n%s\n", player.getName(), player);
//     }
    
//     public void enterCombat(NPC opponent){
//         System.out.printf("%s and %s are in a battle. A battle to the death.\n", this.player.getName(), opponent.getName());
//         System.out.printf("You are able to think fast and now have the first move.\n\n");
//         Scanner scanner = new Scanner(System.in);
//         while(true){
//             System.out.printf("[PLAYER TURN:]\n");
//             this.player.takeTurn(this.player,opponent,scanner);
//             if(!opponent.isAlive()){
//                 System.out.printf("%S is SLAIN!!\n",opponent.getName());
//                 break;
//             }
//             System.out.printf("[ENEMY TURN:]\n");
//             opponent.takeTurn(opponent, this.player, scanner);
//             if(!this.player.isAlive()){
//                 System.out.printf("%S is SLAIN!!\n",this.player.getName());
//                 break;
//             }
//         }
//         scanner.close();
//     }
    
//     public Player getPlayer() {
//         return this.player;
//     }
    
//     private static final String LEVEL_FILE = "data/levels/the-stilts.txt"; // Path to your level file
    
//     private void loadRooms() {
//         rooms = new HashMap<>();
//         try (BufferedReader br = new BufferedReader(new FileReader(LEVEL_FILE))) {
//             String line;
//             int roomCount = -1;
//             while ((line = br.readLine()) != null) {
//                 if (line.startsWith("#")) {
//                     continue; // Ignore comments
//                 }
//                 if (roomCount == -1) {
//                     roomCount = Integer.parseInt(line);
//                 } else if (rooms.size() < roomCount) {
//                     String[] parts = line.strip().split(":");
//                     int id = Integer.parseInt(parts[0]);
//                     String name = parts[1];
//                     String description = parts[2];
//                     rooms.put(id, new Room(name, description));
//                 } else {
//                     String[] parts = line.strip().split(":");
//                     int id = Integer.parseInt(parts[0]);
//                     Room currentRoom = rooms.get(id);
//                     currentRoom.setEast(parts[1].equals("-1") ? null : rooms.get(Integer.parseInt(parts[1])));
//                     currentRoom.setNorth(parts[2].equals("-1") ? null : rooms.get(Integer.parseInt(parts[2])));
//                     currentRoom.setWest(parts[3].equals("-1") ? null : rooms.get(Integer.parseInt(parts[3])));
//                     currentRoom.setSouth(parts[4].equals("-1") ? null : rooms.get(Integer.parseInt(parts[4])));
//                 }
//             } 
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//         currentRoom = rooms.get(0);
//     }
    
//     private void printRoomInfo() {
//         System.out.println("\n-------------------");
//         System.out.println(currentRoom.getName());
//         System.out.println("-------------------");
//         System.out.println(currentRoom.getDescription());
//     }
    
//     private void navigateRooms() {
//         printRoomInfo();
//         while (true) {
//             System.out.println("\nChoose a direction to move (East, North, West, South) or type 'quit' to end the game:");
//             String input = in.nextLine().toLowerCase();
//             Room nextRoom = null;
    
//             if (input.equals("east") && currentRoom.getEast() != null) {
//                 nextRoom = currentRoom.getEast();
//             } else if (input.equals("north") && currentRoom.getNorth() != null) {
//                 nextRoom = currentRoom.getNorth();
//             } else if (input.equals("west") && currentRoom.getWest() != null) {
//                 nextRoom = currentRoom;
//             } else if (input.equals("south") && currentRoom.getSouth() != null) {
//                 nextRoom = currentRoom.getSouth();
//             } else if (input.equals("quit")) {
//                 System.out.println("Thanks for playing!");
//                 break;
//             } else {
//                 System.out.println("Invalid direction or there is no exit in that direction. Please try again.");
//                 continue;
//             }
//             currentRoom = nextRoom;
//             if (currentRoom.getOpponent() != null) {
//                 System.out.println("An enemy has arrived!");
//                 System.out.println(currentRoom.getOpponent().toString());
//                 enterCombat(currentRoom.getOpponent());
//                 if (!player.isAlive()) {
//                     break;
//                 }
//             }
//             printRoomInfo();
//         }
//     }
// }