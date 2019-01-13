import java.util.ArrayList;
import java.util.Random;
public class Room implements Explorable{
  private boolean isExplored;
  private Block[][] blocksHere;
  private Block[] borderBlocks; //List of Blocks on the outer perimeter of the Room
  private int startXcor, startYcor, endXcor, endYcor;
  private int width;
  private int length;

  //public Room(Block start, Block end)
  /**Creates a Room given two blocks (start, end) with the start block being at the top left and the end block being at the bottom right
    *Precondition: start is to the top and left of end block
    *@param start is the Block representing the top left Block of the Room to be made
    *@param end is the Block representing the bottom right Block of the Room to be made
  */
  public Room(Block start, Block end){
    //Storing starting coordinates
    int xcor = start.getX();
    int ycor = start.getY();
    //Storing length and width
    width = xcor - end.getX();
    length = ycor - end.getY();
    startXcor = xcor;
    startYcor = ycor;
    endXcor = end.getX();
    endYcor = end.getY();
    blocksHere = new Block[width][length];
    borderBlocks = new Block[2*width + 2* length - 2];
    int indexBorderBlocks = 0;
    for (int x = 0; x < length; x++){
      for (int y = 0; y < width; y++){
        blocksHere[x][y] = new Block(xcor+x,ycor+y,"Room");
        //If the xcors equal startXcor or endXcor or the ycors equal startYcor or endYcor, add it to borderBlocks list
        if(blocksHere[x][y].getX()==startXcor || blocksHere[x][y].getY()==startYcor
        || blocksHere[x][y].getX()==endXcor || blocksHere[x][y].getY()==endYcor){
          borderBlocks[indexBorderBlocks] = blocksHere[x][y];
          indexBorderBlocks++;
        }
      }
    }
  }

  //public Room(int startXcor, int startYcor, int endXcor, int endYcor
  /**Creates a Room given the coordinates of two blocks (start, end) with the start block being at the top left and the end block being at the bottom right
    *Precondition: start is to the top and left of end block
    *@param startXcor is the xcor of the Block representing the top left Block of the Room to be made
    *@param endXcor is the xcor of the Block representing the bottom right Block of the Room to be made
    *@param startYcor is the xcor of the Block representing the top left Block of the Room to be made
    *@param endYcor is the xcor of the Block representing the bottom right Block of the Room to be made
  */
  public Room(int newStartXcor, int newStartYcor, int newEndXcor, int newEndYcor){
    startXcor = newStartXcor;
    startYcor = newStartYcor;
    endXcor = newEndXcor;
    endYcor = newEndYcor;
    width = endXcor - startXcor;
    length = endYcor - startYcor;
    blocksHere = new Block[width][length];
    borderBlocks = new Block[2*width + 2* length - 2];
    int indexBorderBlocks = 0;
    for (int x = 0; x < width; x++){
      for (int y = 0; y < length; y++){
        blocksHere[x][y] = new Block(startXcor+x,startYcor+y,"Room");
        //If the xcors equal startXcor or endXcor or the ycors equal startYcor or endYcor, add it to borderBlocks list
        if (x == 0 || y== 0 || x == width-1 || y==length-1){
          borderBlocks[indexBorderBlocks] = blocksHere[x][y];
          indexBorderBlocks++;
        }
      }
    }
  }

  //public boolean tooClose(int startXcor, int startYcor, int endXcor, int endYcor)
  /**Tests if rooms overlap with each other or border each other
    *@param startXcor is the xcor of the Block representing the top left Block of the Room to be made
    *@param endXcor is the xcor of the Block representing the bottom right Block of the Room to be made
    *@param startYcor is the xcor of the Block representing the top left Block of the Room to be made
    *@param endYcor is the xcor of the Block representing the bottom right Block of the Room to be made
    *@return whether or not the rooms will overlap or border
  */
  public boolean tooClose(int startXcor, int startYcor, int endXcor, int endYcor){
    //Make sure that rooms don't overlap with each other
    //Rooms overlap when...
    //Case 1: OVERLAP LEFT ORIGINAL
    //    endtXcor of Room to be created >= startXcor - 1 of a Room that already exists
    //AND startXcor of Room to be created <= endXcor of a Room that already exists
    //AND endYcor of Room to be created >= startYcor of a Room that already exists
    //AND startYcor of Room to be created <= endYcor of a Room that already exists

    /*
      0 1 2 3 4 5 6 7 8 9 10
    0 + - - - - - - - - - - +
    1 |                     |
    2 |       New          + - - - - - +
    3 |                    |  Original |
    4 + - - - - - - - - - + - - - - - +
    */

    //Case 2: OVERLAP TOP ORIGINAL
    //    startXcor of Room to be created <= endXcor of a Room that already exists
    //AND endXcor of Room to be created >= startXcor of a Room that already exists
    //AND startYcor of Room to be created <= endYcor of a Room that already exists
    //AND endYcor of Room to be created >= startYcor - 1 of a Room that already exists
    /*
      0 1 2 3 4 5 6 7 8 9 10
    0 + - - - - - - - - - - +
    1 |          New        |
    2 + - - - - - - + - - - + - +
    3               | Original  |
    4               + - - - - - +
    */

    //Case 3: OVERLAP RIGHT ORIGINAL
    //    endtXcor of Room to be created >= startXcor of a Room that already exists
    //AND startXcor of Room to be created <= endXcor + 1 of a Room that already exists
    //AND endYcor of Room to be created >= startYcor of a Room that already exists
    //AND startYcor of Room to be created <= endYcor of a Room that already exists
    /*
      0 1 2 3 4 5 6 7 8 9 10
    0 + - - - - - - - - - - +
    1 |                     |
    2 |       Original      + - - - - - +
    3 |                     |  New      |
    4 + - - - - - - - - - - + - - - - - +
    */
    //Case 4: OVERLAP BOTTOM ORIGINAL
    //    startXcor of Room to be created <= endXcor of a Room that already exists
    //AND endXcor of Room to be created >= startXcor of a Room that already exists
    //AND startYcor of Room to be created <= endYcor + 1 of a Room that already exists
    //AND endYcor of Room to be created >= startYcor of a Room that already exists
    /*
      0 1 2 3 4 5 6 7 8 9 10
    0 + - - - - - - - - - - +
    1 |      Original       |
    2 + - - - - - - + - - - +
    3               |  New  |
    4               + - - - +
    */

    boolean case1 = (endXcor >= this.startXcor - 1) && (startXcor<= this.endXcor) && (startYcor<=this.endYcor) && (endYcor >= this.startYcor);
    //tests to see if it crosses the left of original
    boolean case2 = (endYcor >= this.startYcor - 1) && (startYcor<= this.endYcor) && (startXcor<=this.endXcor) && (endXcor >= this.startXcor);
    //tests to see if it crosses the top of original
    boolean case3 = (startXcor <= this.endXcor + 1) && (endXcor>= this.startXcor) && (startYcor<=this.endYcor) && (endYcor >= this.startYcor);
    //tests to see if it crosses the right of original
    boolean case4 = (startYcor <= this.endYcor + 1) && (endYcor>= this.startYcor) && (startXcor<=this.endXcor) && (endXcor >= this.startXcor);
    //tests to see if it crosses the bottom of original
    /*System.out.println("Case 1| Overlap left original: "+case1);
    System.out.println("Case 2| Overlap top original: "+case2);
    System.out.println("Case 3| Overlap right original: "+case3);
    System.out.println("Case 4| Overlap bottom original: "+case4);*/

    return (case1||case2||case3||case4);
  }

  //public boolean connectRooms(Room theChosenOne)
  /**Given two Rooms, will connect them with a Tunnel
    *@param theChosenOne is a Room from Floor's roomsHere to connect to
    *@param seed is an int from Floor used to generate Tunnel corner points
    *@param floor is the Floor
    *@return whether or not the connection was successful
  */
  public boolean connectRooms(Room theChosenOne, int seed, Floor floor){
    boolean above = false;
    boolean left = false;
    boolean below = false;
    boolean right = false;
    //Note it's possible for a Room to be above, left, and right at the same time compared to another Room
    //Ex:
    /* 0 1 2 3 4 5 6 7 8 9
    0
    1    theChosenOne
    2    R R R R R R R R R R     <-- NOT BELOW b/c theChosenOneendYcor is NOT > thisEndYcor
    3    R R R R R R R R R R     <-- above, b/c theChosenOneStartYcor < thisStartYcor
    4    R R R R R R R R R R     <-- left, b/c theChosenOneStartXcor < thisStartXcor
    5    R R R R R R R R R R     <-- right, b/c theChosenOneEndXcor > thisEndXcor
    6        this
    7        R R R R
    8        R R R R
    9        R R R R
    10       R R R R
    */
    //Note: NO CHANCE OF OVERLAP: AT LEAST ONE AND AT MOST 3 OF THESE BOOLEANS WILL BE TRUE AT ALL TIMES

    //If the theChosenOne is above this, then its startYcor is less than this's startYcor
    if (theChosenOne.getStartYcor()<this.getStartYcor()){
      above = true;
    }
    //If the theChosenOne is left of this, then its startXcor is less than this's startXcor
    if (theChosenOne.getStartXcor()<this.getStartXcor()){
      left = true;
    }
    //If the theChosenOne is below this, then its endXcor is greater than this's endYcor
    if (theChosenOne.getEndXcor()>this.getEndYcor()){
      below = true;
    }
    //If the theChosenOne is right of this, then its xcor is greater than the Room
    if (theChosenOne.getEndXcor()>this.getEndXcor()){
      right = true;
    }

    //Randomly selecting a borderBlock based on location of Rooms relative to each other
    Random rnd = new Random(seed);
    ArrayList<Block> pThis = new ArrayList<Block>(this.borderBlocks.length); //ArrayList of possible borderBlocks to choose one end of the Tunnel
    ArrayList<Block> pTCO = new ArrayList<Block>(theChosenOne.borderBlocks.length); //ArrayList of possible borderBlocks to choose the other end of the Tunnel
    //Getting a random borderBlock of this
    for (Block b: this.borderBlocks){
      //If is below, all border blocks on the bottom have a chance of being picked
      if (below && (b.getY() == theChosenOne.getEndYcor())){
        pThis.add(b);
      }
      //If the block is left, all border blocks on the left have a chance of being picked
      if (left && (b.getX() == theChosenOne.getStartXcor())){
        pThis.add(b);
      }
      //if the block is above, all border blocks on top have a chance to be picked
      if (above && (b.getY() == theChosenOne.getStartYcor())){
        pThis.add(b);
      }
      //If the block is on the right, all border blocks have a chance to be picked
      if (right && (b.getX() == theChosenOne.getEndXcor())){
        pThis.add(b);
      }
    }
    Block thisBlock = pThis.get(rnd.nextInt(pThis.size()));
    //Getting a random borderBlock of theChosenOne
    //Note: sometimes its impossible to choose a certain Block
    for (Block b: this.borderBlocks){
      //If the block is below, all border blocks on the bottom have a chance of being picked
      if (below && (b.getY() == theChosenOne.getStartYcor())){
        pTCO.add(b);
      }
      //If the block is left, all border blocks on the left have a chance of being picked
      if (left && (b.getX() == theChosenOne.getEndXcor())){
        pTCO.add(b);
      }
      //if the block is above, all border blocks on top have a chance to be picked
      if (above && (b.getY() == theChosenOne.getEndYcor())){
        pTCO.add(b);
      }
      //If the block is on the right, all border blocks have a chance to be picked
      if (right && (b.getX() == theChosenOne.getStartXcor())){
        pTCO.add(b);
      }
    }
    Block TCO = pThis.get(rnd.nextInt(pThis.size()));
    //Now create a Tunnel from Block thisBlock to Block TCO
    //Finding distances between thisBlock and TCO
    int deltaX = thisBlock.getX()-TCO.getX();
    //if deltaX is negative, thisBlock is to the left of TCO
    //if deltaX is positive, thisBlock is to the right of TCO
    //if deltaX is 0, simply create a Tunnel directly between them
    int deltaY = thisBlock.getY()-TCO.getY();
    //if deltaY is negative, thisBlock is above TCO
    //if deltaY is positive, thisBlock is below TCO
    //if deltaY is 0, simply create a Tunnel directly between them

    Tunnel connector;
    if (deltaX == 0){
      if (deltaY < 0){
        connector = new Tunnel(thisBlock, TCO, floor);
      }
      else{
        connector = new Tunnel(TCO, thisBlock, floor);
      }
    }
    else if (deltaY == 0){
      if (deltaX < 0){
        connector = new Tunnel(thisBlock, TCO, floor);
      }
      else{
        connector = new Tunnel(TCO, thisBlock, floor);
      }
    }

    //If thisBlock and TCO don't have the same xcor nor ycor...
    else{
      Block corner1, corner2, corner3;
      int dir=rnd.nextInt(2); //decide which direction to go First
      //Getting Xcors and Ycors of first 2 corners
      int randomXcor = rnd.nextInt(Math.abs(deltaX+1));
      int randomYcor = rnd.nextInt(Math.abs(deltaY+1));


      //Starting with horizontal Tunnel first
      Tunnel section1, section2, section3, section4;
      if (dir == 0){//start with horizontal
        if(deltaX < 0){ //if deltaX is negative, thisBlock is to the left of TCO
          if(deltaY > 0){//if deltaY is positive, thisBlock is below TCO
            //Case 1
            /*
                                      corner3=====TCO
                                         |        |
                corner1===============corner2====corner3     ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
    vertical first |                     |
                thisBlock=============corner1
                        path horizontal first
            */
            corner1 = new Block(randomXcor+thisBlock.getX(),thisBlock.getY(),"Tunnel");
            section1 = new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomXcor+thisBlock.getX(), randomYcor+TCO.getY(), "Tunnel");
            section2 = new Tunnel(corner1, corner2, floor);
            corner3 = new Block(TCO.getX(),randomYcor+TCO.getY(), "Tunnel");
            section3 = new Tunnel(corner2, corner3, floor);
            section4 = new Tunnel(corner3, TCO, floor);
          }
          //Case 2
          /*
              thisBlock=============corner1
                                       |
                                    corner2====corner3     ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                                                |
                                              TCO
          */
          else{//if deltaY is negative, thisBlock is above TCO
            corner1 = new Block(randomXcor+thisBlock.getX(),thisBlock.getY(),"Tunnel");
            section1= new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomXcor+thisBlock.getX(), randomYcor+thisBlock.getY(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(TCO.getX(),randomYcor+thisBlock.getY(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(corner3, TCO, floor);
          }
        }


        else{//if deltaX is positive, thisBlock is to the right of TCO
          if(deltaY > 0){//if deltaY is positive, thisBlock is below TCO
            //Case 3
            /*
                TCO===========corner1
                                |
                            corner2====corner3     ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                                         |
                                    thisBlock
                        path horizontal first
            */
            corner1 = new Block(randomXcor+TCO.getX(),TCO.getY(),"Tunnel");
            section1= new Tunnel(TCO, corner1, floor);
            corner2 = new Block(randomXcor+TCO.getX(), randomYcor+TCO.getY(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(thisBlock.getX(),randomYcor+TCO.getY(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(corner3, thisBlock, floor);
          }
          /*
                                        thisBlock
                                           |
                          corner2=======corner3    ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                             |
            TCO===========corner1
          */
          else{//if deltaY is negative, thisBlock is above TCO
            //Case 4
            corner1 = new Block(randomXcor+TCO.getX(),TCO.getY(),"Tunnel");
            section1= new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomXcor+TCO.getX(), randomYcor+thisBlock.getY(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(thisBlock.getX(),randomYcor+thisBlock.getY(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(corner3, TCO, floor);
          }
        }
      }
    }

    //Now select Blocks in between them, only changing one coordinate at a time

    //Run createTunnel multiple times
    return true;
  }

  //public static ArrayList<Block> removeDupes(ArrayList ary)
  /**Removes duplicates from a given ArrayList
    *@param ary is an ArrayList of Blocks to have its duplicates removed
    *@return ary after duplicates have been removed
  */
  public static ArrayList<Block> removeDupes(ArrayList<Block> ary){
    ArrayList<Block> alreadyThere = new ArrayList<Block>(ary.size());
    for (Block e: ary){
      if (alreadyThere.contains(e)){
        ary.remove(e);
      }
      else{
        alreadyThere.add(e);
      }
    }
    return ary;
  }

  //public String isExplored()
  /**Creates a String of the data of the Blocks in the Room
    *@return a String of the data of the Blocks in the Room
  */
  public String toString(){
    String output = "";
    for (int x = 0; x < width; x++){
      for (int y = 0; y < length; y++){
        output+= blocksHere[x][y].getData();
      }
      output+= "\n";
    }
    return output;
  }
  //public boolean isExplored()
  /**Returns whether or not the Room has been explored
    *@return true when: the Player has entered the Room before
    *        false when: the Player has not entered the Room before
  */
  public boolean isExplored(){
    return isExplored;
  }

  //public void setVisibility()
  /**Sets isExplored of all Blocks in the Room to true
  */
  public void setVisibility(){
    for (int x = 0; x < width; x++){
      for (int y = 0; y < length; y++){
        blocksHere[x][y].setVisibility();
      }
    }
    if (!isExplored){
      isExplored = true;
    }
    else{
      isExplored = false;
    }
  }

  //Getters
  public int getStartXcor(){
    return startXcor;
  }
  public int getStartYcor(){
    return startYcor;
  }
  public int getEndXcor(){
    return endXcor;
  }
  public int getEndYcor(){
    return endYcor;
  }
  public Block[] getBorderBlocks(){
    return borderBlocks;
  }
}
