import java.util.ArrayList;
import java.util.Random;
public class Room implements Explorable{
  private boolean isExplored;
  private Block[][] blocksHere;
  private ArrayList<Block> borderBlocks; //List of Blocks on the outer perimeter of the Room
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
    width ++;
    length ++;
    startXcor = xcor;
    startYcor = ycor;
    endXcor = end.getX();
    endYcor = end.getY();
    blocksHere = new Block[length][width];
    borderBlocks = new ArrayList<Block>(2*width + 2* length - 2);
    //int indexBorderBlocks = 0;
    for (int x = 0; x < width; x++){
      for (int y = 0; y < length; y++){
        blocksHere[x][y] = new Block(xcor+x,ycor+y,"Room");
        //If the xcors equal startXcor or endXcor or the ycors equal startYcor or endYcor, add it to borderBlocks list
        if(blocksHere[x][y].getX()==startXcor || blocksHere[x][y].getY()==startYcor
        || blocksHere[x][y].getX()==endXcor || blocksHere[x][y].getY()==endYcor){
          borderBlocks.add(blocksHere[x][y]);
          //indexBorderBlocks++;
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
    width++;
    length++;
    blocksHere = new Block[length][width];
    borderBlocks = new ArrayList<Block>(2*width + 2* length);
    //int indexBorderBlocks = 0;
    for (int y = 0; y < length; y++){
      for (int x = 0; x < width; x++){
        blocksHere[y][x] = new Block(startXcor+x,startYcor+y,"Room");
        //If the xcors equal startXcor or endXcor or the ycors equal startYcor or endYcor, add it to borderBlocks list
        if (x == 0 || y== 0 || x == width - 1 || y==length - 1){
          borderBlocks.add(blocksHere[y][x]);
          //System.out.println("Block here: "+ blocksHere[x][y].toString());
        }
      }
    }
    //System.out.println(toString(borderBlocks));
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
    //Ex: width y
    /* 0 1 2 3 4 5 6 7 8 9
l   0
e   1    theChosenOne
n   2    R R R R R R R R R R     <-- NOT BELOW b/c theChosenOneendYcor is NOT > thisEndYcor
g   3    R R R R R R R R R R     <-- above, b/c theChosenOneStartYcor < thisStartYcor
t   4    R R R R R R R R R R     <-- left, b/c theChosenOneStartXcor < thisStartXcor
h   5    R R R R R R R R R R     <-- right, b/c theChosenOneEndXcor > thisEndXcor
    6        this
    7        R R R R
x   8        R R R R
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
    ArrayList<Block> pThis = new ArrayList<Block>(this.getBlocksHere().length); //ArrayList of possible borderBlocks to choose one end of the Tunnel
    ArrayList<Block> pTCO = new ArrayList<Block>(theChosenOne.getBlocksHere().length); //ArrayList of possible borderBlocks to choose the other end of the Tunnel
    //Getting a random borderBlock of this
    /*System.out.println("Above, left, below, right"+above+left+below+right);
    System.out.println("This.startYcor: "+this.getStartYcor());
    System.out.println("This.startXcor: "+this.getStartXcor());
    System.out.println("This.endYcor: "+this.getEndYcor());
    System.out.println("This.endXcor: "+this.getEndXcor());
    System.out.println("This.blocksHere.length: "+this.blocksHere.length);
    System.out.println("This.blocksHere[0].length: "+this.blocksHere[0].length);*/
    for (int y = 0; y < this.blocksHere.length; y++){
      for (int x = 0; x < this.blocksHere[0].length; x++){
        //If is below, all border blocks on the bottom have a chance of being picked
        //System.out.println("X,Y: "+x+", "+y);
        //System.out.println("blocksHere[x][y]: "+blocksHere[x][y].getX()+", "+blocksHere[x][y].getY());
        if (below && (this.blocksHere[y][x].getY() == this.getEndYcor())){
          pThis.add(this.blocksHere[y][x]);
        }
        //If the block is left, all border blocks on the left have a chance of being picked
        if (left && (this.blocksHere[y][x].getX() == this.getStartXcor())){
          pThis.add(this.blocksHere[y][x]);
        }
        //if the block is above, all border blocks on top have a chance to be picked
        if (above && (this.blocksHere[y][x].getY() == this.getStartYcor())){
          pThis.add(this.blocksHere[y][x]);
        }
        //If the block is on the right, all border blocks have a chance to be picked
        if (right && (this.blocksHere[y][x].getX() == this.getEndXcor())){
          pThis.add(this.blocksHere[y][x]);
        }
        //System.out.println("Size:"+pThis.size());
      }
    }
    //System.out.println("Size:"+pThis.size());
    //Block thisBlock = pThis.get(Math.abs(rnd.nextInt(pThis.size())));
    Block thisBlock = this.blocksHere[rnd.nextInt(this.blocksHere.length)][rnd.nextInt(this.blocksHere[0].length)];
    //thisBlock.setType("Opening");
    //Changing the Block in floor to Opening

    floor.getBlocksHere()[thisBlock.getY()][thisBlock.getX()] = thisBlock;
    //Getting a random borderBlock of theChosenOne
    /*System.out.println("theChosenOne.startYcor: "+theChosenOne.getStartYcor());
    System.out.println("theChosenOne.startXcor: "+theChosenOne.getStartXcor());
    System.out.println("theChosenOne.endYcor: "+theChosenOne.getEndYcor());
    System.out.println("theChosenOne.endXcor: "+theChosenOne.getEndXcor());
    System.out.println("theChosenOne.blocksHere.length: "+theChosenOne.blocksHere.length);
    System.out.println("theChosenOne.blocksHere[0].length: "+theChosenOne.blocksHere[0].length);
    */
    for (int y = 0; y < theChosenOne.blocksHere.length; y++){
      for (int x = 0; x < theChosenOne.blocksHere[0].length; x++){
        //If is below, all border blocks on the bottom have a chance of being picked
        //System.out.println("X,Y: "+x+", "+y);
        //System.out.println("blocksHere[y][x]: "+theChosenOne.blocksHere[x][y].getX()+", "+theChosenOne.blocksHere[x][y].getY());
        if (below && (theChosenOne.blocksHere[y][x].getY() == theChosenOne.getStartYcor())){
          pTCO.add(theChosenOne.blocksHere[y][x]);
        }
        //If the block is left, all border blocks on the left have a chance of being picked
        if (left && (theChosenOne.blocksHere[y][x].getX() == theChosenOne.getEndXcor())){
          pTCO.add(theChosenOne.blocksHere[y][x]);
        }
        //if the block is above, all border blocks on top have a chance to be picked
        if (above && (theChosenOne.blocksHere[y][x].getY() == theChosenOne.getEndYcor())){
          pTCO.add(theChosenOne.blocksHere[y][x]);
        }
        //If the block is on the right, all border blocks have a chance to be picked
        if (right && (theChosenOne.blocksHere[y][x].getX() == theChosenOne.getStartXcor())){
          pTCO.add(theChosenOne.blocksHere[y][x]);
        }
        //System.out.println("Size:"+pTCO.size());
      }
    }
    //System.out.println("Size:"+pTCO.size());
    //Block TCO = pTCO.get(rnd.nextInt(pTCO.size()));

    Block TCO = theChosenOne.blocksHere[rnd.nextInt(theChosenOne.blocksHere.length)][rnd.nextInt(theChosenOne.blocksHere[0].length)];
    //TCO.setType("Opening");
    //Changing the Block in floor to Opening

    floor.getBlocksHere()[TCO.getY()][TCO.getX()] = TCO;
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
      //means this is a vertical Tunnel
      if (deltaY < 0){
        connector = new Tunnel(thisBlock, TCO, floor);
      }
      else{
        connector = new Tunnel(TCO, thisBlock, floor);
      }
      floor.addTunnel(connector);
    }
    else if (deltaY == 0){
      //mean this is a horizontal Tunnel
      if (deltaX < 0){
        connector = new Tunnel(thisBlock, TCO, floor);
      }
      else{
        connector = new Tunnel(TCO, thisBlock, floor);
      }
      floor.addTunnel(connector);
    }

    //If thisBlock and TCO don't have the same xcor nor ycor...
    else{
      Block corner1, corner2, corner3;
      int dir=rnd.nextInt(2); //decide which direction to go First
      //Getting Xcors and Ycors of first 2 corners
      int randomXcor = rnd.nextInt(Math.abs(deltaX)); //the first difference in xcor
      int randomYcor = rnd.nextInt(Math.abs(deltaY)); //the first difference in ycor
      Tunnel section1, section2, section3, section4;

      //-----------------------------------Starting with horizontal Tunnel first---------------------------------//
      if (dir == 0){//start with horizontal
        if(deltaX < 0){ //if deltaX is negative, thisBlock is to the left of TCO
          if(deltaY > 0){//if deltaY is positive, thisBlock is below TCO
            //Case 1
            /*
                                                 TCO
                                                  |
                                      corner2====corner3     ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                                         |
                thisBlock=============corner1
                        path horizontal first
            */
            corner1 = new Block(randomYcor+thisBlock.getY(),thisBlock.getX(),"Tunnel");
            section1 = new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomYcor+thisBlock.getY(), randomXcor+TCO.getX(), "Tunnel");
            section2 = new Tunnel(corner2, corner1, floor);
            corner3 = new Block(TCO.getY(),randomXcor+TCO.getX(), "Tunnel");
            section3 = new Tunnel(corner2, corner3, floor);
            section4 = new Tunnel(TCO, corner3, floor);
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
            corner1 = new Block(randomYcor+thisBlock.getY(),thisBlock.getX(),"Tunnel");
            section1= new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomYcor+thisBlock.getY(), randomXcor+thisBlock.getX(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(TCO.getY(),randomXcor+thisBlock.getX(), "Tunnel");
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
            corner1 = new Block(randomYcor+TCO.getY(),TCO.getX(),"Tunnel");
            section1= new Tunnel(TCO, corner1, floor);
            corner2 = new Block(randomYcor+TCO.getY(), randomXcor+TCO.getX(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(thisBlock.getY(),randomXcor+TCO.getX(), "Tunnel");
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
            corner1 = new Block(randomYcor+TCO.getY(),TCO.getX(),"Tunnel");
            section1= new Tunnel(TCO, corner1, floor);
            corner2 = new Block(randomYcor+TCO.getY(), randomXcor+thisBlock.getX(), "Tunnel");
            section2= new Tunnel(corner2, corner1, floor);
            corner3 = new Block(thisBlock.getY(),randomXcor+thisBlock.getX(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(thisBlock,corner3, floor);
          }
        }
        System.out.println("thisBlock: "+thisBlock.getX()+", "+thisBlock.getY());
        System.out.println("TCO: "+TCO.getX()+", "+TCO.getY());
        System.out.println("DeltaX: " +deltaX);
        System.out.println("DeltaY: " +deltaY);
        System.out.println("RandomXcor: "+randomXcor);
        System.out.println("RandomYcor: "+randomYcor);
        System.out.println("Section1 start: "+ section1.getStartBlock().getX()+", "+ section1.getStartBlock().getY());
        System.out.println("Section1 end: "+ section1.getEndBlock().getX()+", "+ section1.getEndBlock().getY());
        floor.addTunnel(section1);
        System.out.println("Section2 start: "+ section2.getStartBlock().getX()+", "+ section2.getStartBlock().getY());
        System.out.println("Section2 end: "+ section2.getEndBlock().getX()+", "+ section2.getEndBlock().getY());
        floor.addTunnel(section2);
        System.out.println("Section3 start: "+ section3.getStartBlock().getX()+", "+ section3.getStartBlock().getY());
        System.out.println("Section3 end: "+ section3.getEndBlock().getX()+", "+ section3.getEndBlock().getY());
        floor.addTunnel(section3);
        System.out.println("Section4 start: "+ section4.getStartBlock().getX()+", "+ section4.getStartBlock().getY());
        System.out.println("Section4 end: "+ section4.getEndBlock().getX()+", "+ section4.getEndBlock().getY());
        floor.addTunnel(section4);
      }
      //--------------------Start with Vertical------------------//
      else{
        if(deltaX < 0){ //if deltaX is negative, thisBlock is to the left of TCO
          if(deltaY > 0){//if deltaY is positive, thisBlock is below TCO
            //Case 1
            /*
                                      corner3=====TCO
                                         |
                corner1===============corner2      ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
    vertical first |
                thisBlock

            */
            corner1 = new Block(thisBlock.getY(),randomXcor+TCO.getX(),"Tunnel");
            section1 = new Tunnel(corner1, thisBlock, floor);
            corner2 = new Block(randomYcor+thisBlock.getY(), randomXcor+TCO.getX(), "Tunnel");
            section2 = new Tunnel(corner1, corner2, floor);
            corner3 = new Block(randomYcor+thisBlock.getY(),TCO.getX(), "Tunnel");
            section3 = new Tunnel(corner3, corner2, floor);
            section4 = new Tunnel(corner3, TCO, floor);
          }
          //Case 2
          /*
              thisBlock
                  |
              corner1========corner2     ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                              |
                            corner3====TCO
          */
          else{//if deltaY is negative, thisBlock is above TCO
            corner1 = new Block(thisBlock.getY(),randomXcor+thisBlock.getX(),"Tunnel");
            section1= new Tunnel(thisBlock, corner1, floor);
            corner2 = new Block(randomYcor+thisBlock.getY(), randomXcor+thisBlock.getX(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(randomYcor+thisBlock.getY(),TCO.getX(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(corner3, TCO, floor);
          }
        }


        else{//if deltaX is positive, thisBlock is to the right of TCO
          if(deltaY > 0){//if deltaY is positive, thisBlock is below TCO
            //Case 3
            /*
                TCO
                |
              corner1========corner2    ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
                                |
                            corner3====thisBlock
                        path horizontal first
            */
            corner1 = new Block(TCO.getY(),randomXcor+TCO.getX(),"Tunnel");
            section1= new Tunnel(TCO, corner1, floor);
            corner2 = new Block(randomYcor+TCO.getY(), randomYcor+TCO.getX(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(randomYcor+TCO.getY(),thisBlock.getX(), "Tunnel");
            section3= new Tunnel(corner2, corner3, floor);
            section4= new Tunnel(corner3, thisBlock, floor);
          }
          /*
                              corner3=====thisBlock
                                |
              corner1========corner2    ^Note to self, perhaps we should make a "Door" Block to show where the Tunnels are
              |
            TCO
          */
          else{//if deltaY is negative, thisBlock is above TCO
            //Case 4
            corner1 = new Block(TCO.getY(),randomXcor+thisBlock.getX(),"Tunnel");
            section1= new Tunnel(corner1, TCO, floor);
            corner2 = new Block(randomYcor+TCO.getY(), randomXcor+thisBlock.getX(), "Tunnel");
            section2= new Tunnel(corner1, corner2, floor);
            corner3 = new Block(randomYcor+TCO.getY(),thisBlock.getX(), "Tunnel");
            section3= new Tunnel(corner3, corner2, floor);
            section4= new Tunnel(corner3, thisBlock, floor);
          }
        }
        System.out.println("thisBlock: "+thisBlock.getX()+", "+thisBlock.getY());
        System.out.println("TCO: "+TCO.getX()+", "+TCO.getY());
        System.out.println("DeltaX: " +deltaX);
        System.out.println("DeltaY: " +deltaY);
        System.out.println("RandomXcor: "+randomXcor);
        System.out.println("RandomYcor: "+randomYcor);
        System.out.println("Section1 start: "+ section1.getStartBlock().getX()+", "+ section1.getStartBlock().getY());
        System.out.println("Section1 end: "+ section1.getEndBlock().getX()+", "+ section1.getEndBlock().getY());
        floor.addTunnel(section1);
        System.out.println("Section2 start: "+ section2.getStartBlock().getX()+", "+ section2.getStartBlock().getY());
        System.out.println("Section2 end: "+ section2.getEndBlock().getX()+", "+ section2.getEndBlock().getY());
        floor.addTunnel(section2);
        System.out.println("Section3 start: "+ section3.getStartBlock().getX()+", "+ section3.getStartBlock().getY());
        System.out.println("Section3 end: "+ section3.getEndBlock().getX()+", "+ section3.getEndBlock().getY());
        floor.addTunnel(section3);
        System.out.println("Section4 start: "+ section4.getStartBlock().getX()+", "+ section4.getStartBlock().getY());
        System.out.println("Section4 end: "+ section4.getEndBlock().getX()+", "+ section4.getEndBlock().getY());
        floor.addTunnel(section4);
      }
    }
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
    for (int x = 0; x < length; x++){
      for (int y = 0; y < width; y++){
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
  public Block[][] getBlocksHere(){
    return blocksHere;
  }
  public ArrayList<Block> getBorderBlocks(){
    return borderBlocks;
  }
  public static String toString(ArrayList<Block> b){
    String output = "";
    for (int i = 0; i < b.size(); i++){
      //System.out.println(b.get(i).toString());
      output+=b.get(i).toString();
    }
    return output;
  }
}
