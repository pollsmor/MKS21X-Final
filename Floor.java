import java.util.Random;

public class Floor{
  private Block[][] blocksHere; //Array of Blocks (like patches)
  private int floorNumber;
  private int width;
  private int length;
  private Room[] roomsHere; //List of Rooms
  private int numRooms; //Number of successful Rooms
  //private Tunnels[] tunnelsHere; //List of total tunnels created
  //private Mission[] missions; to be added later if we get to it

  //public Floor(int num, int terminal Width, int terminalLength)
  /** Constructs a Floor based on the terminal width and length and assigns the floor a number
    *@param num an int which will become the floorNumber
    *@param terminalWidth an int which will become the floor's width (used for creating blocksHere)
    *@param terminalLength an int which will become the floor's length (used for creating blocksHere)
  */
  public Floor(int num, int terminalWidth, int terminalLength){
    floorNumber = num;
    width = terminalWidth;
    length = terminalLength;
    blocksHere = new Block[length][width];
    for (int y = 0; y < length; y++) {
      for (int x = 0; x < width; x++) {
        blocksHere[y][x] = new Block(x, y, "Wall");
      }
    }
  }

  public Block getBlock(int row, int col) {
    return blocksHere[row][col];
  }
  public void setBlock(int row, int col, Block b){
    //System.out.println("Row: "+row);
    //System.out.println("Col: "+col);
    //System.out.println("blockHere.length: "+blocksHere.length);
    //System.out.println("blocksHere[0].length: "+blocksHere[0].length);
    blocksHere[row][col] = new Block(row, col, "Tunnel");
  }

  //public int getFloorNumber()
  /**Returns the number of floors the Player has successfully passed
    *@return floorNumber: an int which represents the number of Floors played in the current Game
  */
  public int getFloorNumber(){
    return floorNumber;
  }
  //public int getWidth()
  /**Returns the width of the Floor
    *@return width: an int which represents the width of the current Floor
  */
  public int getWidth(){
    return width;
  }
  //public int getLength()
  /**Returns the length of the Floor
    *@return length: an int which represents the length of the current Floor
  */
  public int getLength(){
    return length;
  }

  //public void createRooms(int seed)
  /**Spawns rooms on the Floor given a seed
    *@param seed is an int given to randomly generate Rooms
  */
  public void createRooms(int seed){
    Random rnd = new Random(seed); //Takes seed generated by Game class
    //First decide number of Rooms to create
    int rooms = rnd.nextInt(3) + (width+length)/10;
    roomsHere = new Room[rooms];
    //System.out.println("Rooms: "+rooms);
    int attempts = 500; //In case it's impossible to create all the rooms, have a set number of failed attempts possible
    //Using random, generate the xcors and ycors of top right Blocks and bottom left Blocks of the Rooms
    //Minimum width of room: 5 | Max: 12
    //Minimum length of room: 5 | Max: 12
    int startXcor, startYcor, endXcor, endYcor;
    int successfulRooms = 0; //Keep track of how many Rooms were successfully made
    boolean wasOverlap; //Keep track of whether or Room to be created overlaps with any other Room
    Room r, chosenRoom;
    while (successfulRooms < rooms && attempts > 0){
      startXcor = Math.abs(rnd.nextInt(width - 13)) + 1;
      endXcor = startXcor + rnd.nextInt(7) + 5;
      startYcor = Math.abs(rnd.nextInt(length - 13)) + 1;
      endYcor = startYcor + rnd.nextInt(7) + 5;
      //System.out.println("startXcor: "+ startXcor +", startYcor: "+startYcor+", endXcor: "+endXcor+", endYcor: "+endYcor);
      wasOverlap = false;
      //Make sure that rooms don't overlap with each other
      //System.out.println("startXcor: "+ startXcor +", startYcor: "+startYcor+", endXcor: "+endXcor+", endYcor: "+endYcor);
      for (int i = 0; i < successfulRooms; i++){
        if (roomsHere[i].tooClose(startXcor, startYcor, endXcor, endYcor)){
          wasOverlap = true;
          i = roomsHere.length; //Stop loop once it has been discovered that a Room will overlap the new one
          attempts--; //Reduce attempts by 1
        }
      }
      if (!wasOverlap){ //If there were no overlapping Rooms, create the Room
        r = createRoom(startXcor, startYcor, endXcor, endYcor);
        if (successfulRooms != 0){
        chosenRoom = roomsHere[rnd.nextInt(successfulRooms)];
        //System.out.println(successfulRooms);
        //System.out.println(r.toString());
        //System.out.println(chosenRoom.toString());
        //System.out.println(this.toString());
        r.connectRooms(chosenRoom, seed, this);
        //System.out.println(seed);
      }
      roomsHere[successfulRooms]= r;
      successfulRooms++;
        //Connect to a room
        //Create a tunnel
        //Allow tunnels to pass through each other
      }
    }
    numRooms = successfulRooms;
  }

  //public Room createRoom(int startXcor, int startYcor, int endXcor, int endYcor)
  /**Creates a single room given the coordinates of the start and end Blocks
    *@param startXcor is the int representing the x-coordinate of the starting Block
    *@param startYcor is the int representing the y-coordinate of the starting Block
    *@param endXcor is the int representing the x-coordinate of the ending Block
    *@param endYcor is the int representing the y-coordinate of the ending Block
    *@return the new successfully created Room
  */
  public Room createRoom(int startXcor, int startYcor, int endXcor, int endYcor){
    Room a = new Room(startXcor, startYcor, endXcor, endYcor);
    //System.out.println("startXcor: "+startXcor+", startYcor: "+startYcor+", endXcor: "+endXcor+", endYcor: "+endYcor);
    //Must also update blocksHere
    for (int y = startYcor; y < endYcor + 1; y++){ // - 1 to avoid index exceptions
      for (int x = startXcor; x < endXcor + 1; x++){
        blocksHere[y][x] = new Block(x,y,"Room");
        //System.out.println("FloorBlock here"+this.blocksHere[y][x].printPoint());
        //System.out.println("RoomBlock here: "+ a.getBlocksHere()[y-startYcor][x-startXcor].printPoint());
        //System.out.println("Type: "+this.blocksHere[y][x].getType());
      }
    }
    return a;
  }

  //public Tunnel createTunnel(Room r1, Room r2){
  /*
  *Each tunnel is only in one direction and as such is assigned one direction: 0 is left-right, 1 is up-down
  *Tunnels may intersect with other Tunnels
  */
    //Some function to get a border block from room 1 and room 2
    //Tunnel a = new Tunnel(Block, Block);
    //Construct the Tunnel
    //return a;
    //return Tunnel made
  //}

  public String toString(){
    String output = " |";
    int n = 0;
    for (int y = 0; y < width; y++){
      output += n%10;
      n++;
    }
    //output += "\n";
    /*for (int y = 0; y < width; y++){
      output += "-";
      //if (y != length - 1){
        //output += " ";
      //}
    }*/
    output += "|\n";
    n = 0;
    for (int y = 0; y < length; y++){
      output+=n%10;
      output+="|";
      n++;
      for (int x = 0; x < width; x++){
        output+= blocksHere[y][x].getData();
        //if (y != length - 1){
        //  output += " ";
        //}
      }
      output+="|\n";
    }
    output +=" |";
    for (int y = 0; y < width; y++){
      output += "-";
      //if (y != length - 1){
      //  output += " ";
      //}
    }

    return output+"|";
  }

  public Block[][] getBlocksHere(){
    return blocksHere;
  }

  public static String toStringBlocks(Block[][] b){
    String output = "";
    for (int i = 0; i < b.length; i++){
      for (int j = 0; j < b[i].length; j++){
        output += b[i][j].getData();
        if (i!=b[i].length - 1){
          output += ", ";
        }
      }
      if (i!=b.length - 1){
        output += "\n";
      }
    }
    return output;
  }

  //public String toStringClean()
  /**

  */
  //toString makes a coordinate system that is offset from the one the blocksHere array uses.
  public String toStringClean(){
    String output = "";

    for (int i = 0; i < length; ++i) {
      for (int j = 0; j < width; ++j) {
        if (j == width - 1)
          output += "|";

        else
          output += blocksHere[i][j].getData();
      }

      output += '\n';
    }

    for (int x = 0; x < width; ++x)
      output += '-';

    return output;
  }

  //public void addTunnel(Tunnel t)
  /**Adds a Tunnel to blocksHere
    *@param t is a Tunnel which is to be added to the Floor
    *@param direction is an int representing the direction the Tunnel is: 0 for left right, 1 for up down
  */
  public void addTunnel(Tunnel t, int direction){
    Block b;
    for (int i = 0; i < t.getBlocksHere().length; i++){ //Horizontal
      for(int j = 0; j < t.getBlocksHere()[i].length;j++){
        b = t.getBlocksHere()[i][j];
        //System.out.println("b.getX(),b.getY(): "+ b.getX()+", "+b.getY());
        if (blocksHere[b.getY()][b.getX()].getType() == "Tunnel" && direction != blocksHere[b.getY()][b.getX()].getDirection()){
            blocksHere[b.getY()][b.getX()] = new Block(b.getX(), b.getY(), 2);
        }
        else{
        //if (blocksHere[b.getY()][b.getX()].getType() != "Room"){ //Don't make it a Tunnel Block if it was a Room
          //if ((i == 0 && j == 0)
          //||(direction == 0 && j==t.getBlocksHere()[i].length - 1) //horizontal last Block
          //||(direction == 1 && i == t.getBlocksHere().length - 1) //vertical last Block
          //||(b.getType() == "Tunnel"&&b.getDirection()!=direction)
          //)
          //{ //theres a Tunnel Block already here with a different direction than the one to be made
          //  blocksHere[b.getY()][b.getX()] = new Block(b.getX(), b.getY(), 2);   //Ends of tunnels get "#"
          //}
          //else{
          blocksHere[b.getY()][b.getX()] = new Block(b.getX(), b.getY(), direction);
        }
      }
    }
  }
}
