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
      data = 'R';
    }
    if (newType.equals("Tunnel")){
      data = 'T';
    }
    type = newType;
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

  //----------Type of Block----------//
  public String getType(){
    return null;
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
/* Spawns
  public void spawnObjective() {
    objectiveHere = true;
    data = '%';
  }
  public void spawnItem(){
    Item newItem = new Item();
    data = 'I';
    itemHere = newItem;
  }

  public void spawnEnemy() {
    Enemy newEnemy = new Enemy();
    data = 'E';
    pokemonHere = newEnemy;
  }

  public void spawnPlayer() {
    Player newPlayer = new Player();
    data = 'P';
    pokemonHere = newPlayer;
  }*/
}
