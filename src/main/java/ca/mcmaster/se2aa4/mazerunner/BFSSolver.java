package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class BFSSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Queue <Position> vertices;
    private Queue <Direction> direction;
    private Queue <BigInteger> index;

    // private Queue <TreeNode> Pathfinder;

    private boolean[][] marked;
    private Maze maze;
    
    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        marked = new boolean[maze.getSizeY()][maze.getSizeX()];
        logger.debug("Searching through map");
        BigInteger exitIndex = bfsSearch();
        
        logger.debug("Tracing path...");
        return retraceSteps(exitIndex);
    }
    private BigInteger bfsSearch(){
        
        Direction dir = Direction.RIGHT;
        Position pos = maze.getStart();


        //TreeNode head = new TreeNode('H');


        BigInteger i = new BigInteger("1");
        
        BigInteger one = new BigInteger("1");
        BigInteger three = new BigInteger("3");
        

        Position exit = maze.getEnd();
        for (int a=0;a<maze.getSizeX();a++){for (int b=0;b<maze.getSizeY();b++){marked[b][a]= false;}}
        int x = maze.getSizeX();
        int y = maze.getSizeY();
        vertices = new ArrayDeque<Position>(){};
        direction = new ArrayDeque<Direction>(){};
        index = new ArrayDeque<BigInteger>(){};
        vertices.add(pos);
        direction.add(dir);
        index.add(i);
        LocationQueue currentLocat = new LocationQueue(pos, dir, i);

        // Pathfinder = new ArrayDeque<TreeNode>(){};
        // Pathfinder.add(head);


        while (!pos.equals(exit) && !vertices.isEmpty()) {

            currentLocat.updateQueue();
            
            pos = currentLocat.getPosition();
            dir = currentLocat.getDirection();

            i = currentLocat.getIndex();
            // head = Pathfinder.remove();

            
            if (marked[pos.y()][pos.x()]==false){
                
                // Go to the position (Use ternary tree)
                marked[pos.y()][pos.x()] = true;

                Position rightPos = pos.move(dir.turnRight());
                Position leftPos = pos.move(dir.turnLeft());
                Position forwardPos = pos.move(dir);

                // Make sure to store position, direction and index of each node.
                if (isInBounds(rightPos, x, y) && !marked[rightPos.y()][rightPos.x()] && !maze.isWall(rightPos)){
                    vertices.add(rightPos);
                    direction.add(dir.turnRight());
                    
                    index.add((i.multiply(three)).subtract(one));
                    currentLocat.add(rightPos, dir.turnRight(), i.multiply(three).subtract(one));
                    // Ternary tree add right node with value "R"
                }
                if (isInBounds(leftPos, x, y) && !marked[leftPos.y()][leftPos.x()] && !maze.isWall(leftPos)){
                    vertices.add(leftPos);
                    direction.add(dir.turnLeft());
                    index.add((i.multiply(three)));
                    currentLocat.add(leftPos, dir.turnLeft(), i.multiply(three));
                    // Ternary tree add left node with value "L"
                }
                if (isInBounds(forwardPos, x, y) && !marked[forwardPos.y()][forwardPos.x()] && !maze.isWall(forwardPos)){
                    vertices.add(forwardPos);
                    direction.add(dir);
                    index.add((i.multiply(three)).add(one));
                    currentLocat.add(forwardPos, dir, i.multiply(three).add(one));

                    // Ternary tree add middle node with value "F"
                }
            }
        }
        if (!pos.equals(exit))throw new RuntimeException("Algorithm Error");
         return i; 
    }
    private Path retraceSteps(BigInteger index){
        Stack<Character> tempStorage = new Stack<Character>(){};
        Path path = new Path();
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger three = new BigInteger("3");

        while (!index.equals(one)){
            if ((index.mod(three)).equals(one))tempStorage.add('F');
            else if((index.mod(three)).equals(two)){
                tempStorage.add('F');
                tempStorage.add('R');
            } else if((index.mod(three)).equals(zero)){
                tempStorage.add('F');
                tempStorage.add('L');
            } else throw new RuntimeException("Division Error"); 
            
            index = (index.add(one)).divide(three);
        }
        while (!tempStorage.isEmpty()) path.addStep(tempStorage.pop());

        return path;
    }
    
    private boolean isInBounds(Position position, int sizeX, int sizeY) {
        return position.x() >= 0 && position.x() < sizeX && position.y() >= 0 && position.y() < sizeY;
    }
}
