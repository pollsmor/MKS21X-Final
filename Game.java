import java.util.Random;
import java.util.ArrayList;

public class Game {
  private String name;
  private Player player;
  private ArrayList<Enemy> enemies;
  private int amtEnemies;
  private int level;
  private Floor floor;
  private Mission[] missions;
  private int seed;
  private boolean isRandomSeed;
  private int termRows;
  private int termCols;

  //Doesn't take a seed, so a random one is generated
  public Game(String inputName, int rows, int cols, int newLevel) {
    termRows = rows;
    termCols = cols;
    level = newLevel;
    floor = new Floor(level, cols, rows * 3/4); //Floor constructor takes left to right before top to bottom
    //missions = new Mission(); //ArrayList to allow easy adding/removing
    Random randgen = new Random();
    seed = randgen.nextInt();
    isRandomSeed = true;
    floor.createRooms(seed);

    name = inputName;
    spawnPlayer();
    amtEnemies = rows * cols / 450; //decent algorithm for calculating the amount of enemies on screen, based on Terminal size
    enemies = new ArrayList<Enemy>(amtEnemies);
    spawnEnemies();
  }

  //Takes a seed
  public Game(String inputName, int inputSeed, int rows, int cols, int newLevel) {
    termRows = rows;
    termCols = cols;
    level = newLevel;
    floor = new Floor(level, cols, rows * 3/4);
    seed = inputSeed;
    isRandomSeed = false;
    floor.createRooms(seed);

    name = inputName;
    player = spawnPlayer();
    amtEnemies = rows * cols / 450;
    enemies = new ArrayList<Enemy>(amtEnemies);
    spawnEnemies();
  }

  //Takes a Player: useful if the player has leveled up or has specific stats that aren't to be lost
  public Game(String inputName, int inputSeed, int rows, int cols, int newLevel, Player oldPlayer) {
    termRows = rows;
    termCols = cols;
    level = newLevel;
    floor = new Floor(level, cols, rows * 3/4);
    seed = inputSeed;
    isRandomSeed = false;
    floor.createRooms(seed);

    name = inputName;
    player = spawnPlayer(oldPlayer);
    amtEnemies = rows * cols / 450;
    enemies = new ArrayList<Enemy>(amtEnemies);
    spawnEnemies();
  }

  //-------------------------"Get" with the getters of the program below------------------------//
  public Player getPlayer() {
    return player;
  }

  public ArrayList<Enemy> getEnemies() {
    return enemies;
  }

  public Enemy getEnemy(int num) {
    return enemies.get(num);
  }

  public int getLevel() {
    return level;
  }

  public Floor getFloor() {
    return floor;
  }

  //Allows getting a single mission from the array
  public Mission getMission(int number) {
    return missions[number]; //Didn't really get implemented but oh wells
  }

  //Why a getSeed? Because the one provided for main isn't the final seed - it gets run through the constructor.
  public int getSeed() {
    return seed;
  }

  public boolean isWall(int row, int col) {
    return floor.getBlock(row, col).getType() == "Wall";
  }

  public boolean isTunnel(int row, int col){
    return floor.getBlock(row, col).getType() == "Tunnel";
  }

  public boolean isObjective(int row, int col){
    return floor.getBlock(row, col).getType() == "Objective";
  }

  public Block getBlock(int row, int col){
    return floor.getBlock(row, col);
  }

  //Gets a spawning location for a Player
  public int[] createSpawnPlayer() {
    Random randgenRow = new Random();
    Random randgenCol = new Random();

    if (!isRandomSeed) {
      randgenRow = new Random(seed + 2);
      randgenCol = new Random(seed + 3);
    }

    int row = 0;
    int col = 0;

    boolean spawnFound = false;
    while (!spawnFound) {
      row = Math.abs(randgenRow.nextInt() % (termRows * 3/4));
      col = Math.abs(randgenCol.nextInt() % termCols);
      if (!isWall(row, col) && getFloor().getBlocksHere()[row][col].getPokemonHere() == null){
        spawnFound = true;
      }
    }

    int[] output = new int[2];
    output[0] = row;
    output[1] = col;

    return output;
  }

  //Gets a spawning location for an Enemy
  public int[] createSpawnEnemy() {
    Random randgenRow = new Random();
    Random randgenCol = new Random();

    if (!isRandomSeed) {
      randgenRow = new Random(seed + 3);
      randgenCol = new Random();
    }

    int row = 0;
    int col = 0;

    boolean spawnFound = false;
    while (!spawnFound) {
      row = Math.abs(randgenRow.nextInt() % (termRows * 3/4));
      col = Math.abs(randgenCol.nextInt() % termCols);
      if (!isWall(row, col))
        spawnFound = true;
    }

    int[] output = new int[2];
    output[0] = row;
    output[1] = col;

    return output;
  }

  //Starting off fresh :)
  private Player spawnPlayer() {
      int[] playerSpawn = createSpawnPlayer();
      int row = playerSpawn[0];
      int col = playerSpawn[1];
      player = new Player(name, row, col);
      return player;
  }

  //Already have a Player with stats
  private Player spawnPlayer(Player player) {
    int[] playerSpawn = createSpawnPlayer();
    int row = playerSpawn[0];
    int col = playerSpawn[1];
    getFloor().getBlocksHere()[player.getRow()][player.getCol()].setPokemonHere(null);
    player.setRow(row);
    player.setCol(col);
    getFloor().getBlocksHere()[player.getRow()][player.getCol()].setPokemonHere(player);
    return player;
  }

  //Spawns enemies
  private void spawnEnemies() {
    for (int i = 0; i < amtEnemies; ++i) {
      int[] enemySpawn = createSpawnEnemy();
      int row = enemySpawn[0];
      int col = enemySpawn[1];
      Enemy e = new Enemy(row, col);
      enemies.add(e);
      floor.getBlock(row, col).spawnEnemyHere(enemies.get(i)); //Place the Enemy
    }
  }

  //Returns whether or not there is an Enemy nearby (can go over Tunnels too)
  public boolean enemyNearby() {
    return
    (floor.getBlock(player.getRow() -1, player.getCol()).getPokemonHere() != null) ||      //top
    (floor.getBlock(player.getRow(), player.getCol() + 1).getPokemonHere() != null) ||     //right
    (floor.getBlock(player.getRow() + 1, player.getCol()).getPokemonHere() != null) ||     //bottom
    (floor.getBlock(player.getRow(), player.getCol() - 1).getPokemonHere() != null);        //left
  }

  //Kill off the enemies that have nonpositive HP
  public void killTheDead() {
    for (int i = 0; i < enemies.size(); ++i) {
      if (enemies.get(i).getHP() <= 0){
        floor.getBlock(enemies.get(i).getRow(), enemies.get(i).getCol()).setPokemonHere(null);
        enemies.remove(i); //Remove the dead enemy from ArrayList
        i--;//Reduce the index b/c we lost an enemy
      }
    }
  }
}
