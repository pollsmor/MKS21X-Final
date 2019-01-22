public class Block implements Explorable{
  private boolean isVisible;
  private Item itemHere;
  private Pokemon pokemonHere;
  private boolean objectiveHere;
  private String type;
  private int xcor;
  private int ycor;
  private String color;
  private char data; //what's printed in the block
  private boolean canMoveLeft, canMoveRight, canMoveUp, canMoveDown; //To handle adjacent Tunnels
  private int direction; //For intersection Tunnels

  public Block(int x, int y, String newType) { //Constructor given coordinates
    isVisible = false;
    objectiveHere = false;
    xcor = x;
    ycor = y;
    color = "black";
    //Default data ' ' is for Wall
    data = ' ';
    //For testing purposes
    if (newType.equals("Room")){
      data = ' ';
    }
    if (newType.equals("Tunnel")){
      data = ' ';
    }
    if (newType.equals("Objective")){
<<<<<<< HEAD
      data = ' ';
=======
      data = 'O';
>>>>>>> ab0677c8673053ed1865ac15abfec6b3c934ecc6
    }
    type = newType;
  }

  public Block (int x, int y, int dir){ //Separate constructor for Tunnels
    isVisible = false;
    objectiveHere = false;
    xcor = x;
    ycor = y;
    color = "gray";
    if (dir == 0){ //LR
      data = ' ';
    }
    if (dir == 1){ //UD
      data = ' ';
    }
    if (dir == 2){ //EndBlock of tunnel or intersection of Tunnels
      data = ' ';
    }
    direction = dir;
    type = "Tunnel";
  }

  public String toString(){
    return data + "";
  }
//----------Getters and Setters----------//

  //----------Visibility----------//
  public boolean isExplored(){
    return isVisible;
  }

  public void setVisibility(){
    if (!isVisible){
      isVisible = true;
    }
    else{
      isVisible = false;
    }
  }

  //----------Objectives---------//
  public boolean isObjective() {
    return objectiveHere;
  }

  public boolean setObjectiveHere(boolean b){
    boolean x = objectiveHere;
    objectiveHere = b;
    return x;
  }

  //----------Type of Block----------//
  public String getType(){
    return type;
  }

  //----------Coodinate stuffs----------//
  public int getX(){
    return xcor;
  }

  public int getY(){
    return ycor;
  }

  public char getData(){
    return data;
  }

  public String setType(String newType){
    String oldType = type;
    type = newType;
    if (type == "Objective"){
      data = '0';
    }
    return oldType;
  }

  public String printPoint(){
    return "("+xcor+", "+ ycor +")";
  }

  public int getDirection(){
    return direction;
  }

  public void setCanMove(char d, boolean t){
    if (d == 'u'){
      canMoveUp = t;
    }
    if (d == 'd'){
      canMoveDown = t;
    }
    if (d == 'r'){
      canMoveRight = t;
    }
    if (d == 'l'){
      canMoveLeft = t;
    }
  }

  public boolean canMove(char d){
    if (d == 'u'){
      return canMoveUp;
    }
    if (d == 'd'){
      return canMoveDown;
    }
    if (d == 'r'){
      return canMoveRight;
    }
    if (d == 'l'){
      return canMoveLeft;
    }
    return false; //Should never happen but needs to compile ://///
  }

  //Spawns
  public void spawnObjective() {
    objectiveHere = true;
    data = 'O';
    type = "Objective";
  }

  /*
  public void spawnItem(){
    Item newItem = new Item();
    data = 'I';
    itemHere = newItem;
  }
  */
  public void spawnPlayerHere(Player player){
    data = '\u04dd';
    pokemonHere = player;
    setPokemonHere(player);
  }

  public void spawnEnemyHere(Enemy enemy) {
    data = '\u03e1';
    pokemonHere = enemy;
  }

  public Pokemon getPokemonHere(){ //Can return an Player, Enemy, or null
    return pokemonHere;
  }

  public void setPokemonHere(Pokemon pokemon) {
    if (pokemon == null) {
      data = ' ';
      pokemonHere = null;
    }

    else if (pokemon instanceof Player) {
      data = '\u04dd';
      pokemonHere = pokemon;
    }

    else {
      data = '\u03e1';
      pokemonHere = pokemon;
    }
  }
}
