package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.Stack;

public class BFSSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
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
        BigInteger i = new BigInteger("1");
        
        BigInteger one = new BigInteger("1");
        BigInteger three = new BigInteger("3");
        

        Position exit = maze.getEnd();
        for (int a=0;a<maze.getSizeX();a++){
            for (int b=0;b<maze.getSizeY();b++){
                marked[b][a]= false;
            }
        }
        int x = maze.getSizeX();
        int y = maze.getSizeY();
        
        LocationQueue currentLocat = new LocationQueue(pos, dir, i);

        while (!pos.equals(exit) && !currentLocat.isEmpty()) {

            currentLocat.updateQueue();
            
            pos = currentLocat.getPosition();
            dir = currentLocat.getDirection();
            i = currentLocat.getIndex();

            
            if (marked[pos.y()][pos.x()]==false){
                
                // Go to the position (Use ternary tree)
                marked[pos.y()][pos.x()] = true;
                
                Position leftPos = pos.move(dir.turnLeft());
                BigInteger leftIndex = i.multiply(three);

                Position rightPos = pos.move(dir.turnRight());
                BigInteger rightIndex = leftIndex.subtract(one);
                
                Position forwardPos = pos.move(dir);
                BigInteger forwardIndex = leftIndex.add(one);

                if (isInBounds(leftPos, x, y) && !maze.isWall(leftPos) && !marked[leftPos.y()][leftPos.x()]){
                    
                    currentLocat.add(leftPos, dir.turnLeft(), leftIndex);
                    // Ternary tree add left node with value "L"
                }

                if (isInBounds(rightPos, x, y) && !maze.isWall(rightPos) && !marked[rightPos.y()][rightPos.x()]){
                    
                    currentLocat.add(rightPos, dir.turnRight(), rightIndex);
                    // Ternary tree add right node with value "R"
                }
                
                if (isInBounds(forwardPos, x, y) && !maze.isWall(forwardPos) && !marked[forwardPos.y()][forwardPos.x()] ){
                    
                    currentLocat.add(forwardPos, dir, forwardIndex);
                    // Ternary tree add middle node with value "F"
                }
            }
        }
        if (!pos.equals(exit))throw new RuntimeException("Algorithm Error");
        return i; 
    }
    private Path retraceSteps(BigInteger index){
        Stack<Character> tempStorage = new Stack<Character>();
        Path path = new Path();
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");
        BigInteger two = new BigInteger("2");
        BigInteger three = new BigInteger("3");
        BigInteger modCheck;

        while (!index.equals(one)){

            modCheck = index.mod(three);

            if (modCheck.equals(one)){
                tempStorage.add('F');

            } else if(modCheck.equals(two)){
                tempStorage.add('F');
                tempStorage.add('R');

            } else if(modCheck.equals(zero)){
                tempStorage.add('F');
                tempStorage.add('L');

            } else throw new RuntimeException("Division Error"); 
            
            index = index.add(one);
            index = index.divide(three);
        }

        while (!tempStorage.isEmpty()) path.addStep(tempStorage.pop());

        return path;
    }
    
    private boolean isInBounds(Position position, int sizeX, int sizeY) {
        return position.x() >= 0 && position.x() < sizeX && position.y() >= 0 && position.y() < sizeY;
    }
}
