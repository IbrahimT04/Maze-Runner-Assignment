package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Deque;
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
        BigInteger i = BigInteger.valueOf(1);
        
        BigInteger one = BigInteger.valueOf(1);
        BigInteger three = BigInteger.valueOf(3);
        

        Position exit = maze.getEnd();
        for (int a=0;a<maze.getSizeX();a++){
            for (int b=0;b<maze.getSizeY();b++){
                marked[b][a]= false;
            }
        }
        LocationQueue currentLocat = new LocationQueue(pos, dir, i);

        while (!pos.equals(exit) && !currentLocat.isEmpty()) {

            currentLocat.updateQueue();
            
            pos = currentLocat.getPosition();
            dir = currentLocat.getDirection();
            i = currentLocat.getIndex();
            
            if (!marked[pos.y()][pos.x()]){
                
                // Go to the position (Use ternary tree)
                marked[pos.y()][pos.x()] = true;
                
                Position leftPos = pos.move(dir.turnLeft());
                BigInteger leftIndex = i.multiply(three);

                Position rightPos = pos.move(dir.turnRight());
                BigInteger rightIndex = leftIndex.subtract(one);
                
                Position forwardPos = pos.move(dir);
                BigInteger forwardIndex = leftIndex.add(one);

                if (maze.isInBounds(leftPos) && !maze.isWall(leftPos) && !marked[leftPos.y()][leftPos.x()]){
                    // Ternary tree add left node with value "L"
                    currentLocat.add(leftPos, dir.turnLeft(), leftIndex);
                }

                if (maze.isInBounds(rightPos) && !maze.isWall(rightPos) && !marked[rightPos.y()][rightPos.x()]){
                    // Ternary tree add right node with value "R"
                    currentLocat.add(rightPos, dir.turnRight(), rightIndex);
                }
                
                if (maze.isInBounds(forwardPos) && !maze.isWall(forwardPos) && !marked[forwardPos.y()][forwardPos.x()]){
                    // Ternary tree add middle node with value "F"
                    currentLocat.add(forwardPos, dir, forwardIndex);
                }
            }
        }
        if (!pos.equals(exit))throw new IllegalStateException("Algorithm Error: Exit not found or doesn't exist");
        return i; 
    }

    private Path retraceSteps(BigInteger index){
        Deque<Character> tempStorage = new ArrayDeque<>();
        Path path = new Path();
        BigInteger zero = BigInteger.valueOf(0);
        BigInteger one = BigInteger.valueOf(1);
        BigInteger two = BigInteger.valueOf(2);
        BigInteger three = BigInteger.valueOf(3);
        BigInteger modCheck;

        while (!index.equals(one)){
            modCheck = index.mod(three);

            if (modCheck.equals(one)){
                tempStorage.push('F');

            } else if(modCheck.equals(two)){
                tempStorage.push('F');
                tempStorage.push('R');

            } else if(modCheck.equals(zero)){
                tempStorage.push('F');
                tempStorage.push('L');

            } else throw new IllegalStateException("Division Error"); 
            
            index = index.add(one);
            index = index.divide(three);
        }
        while (!tempStorage.isEmpty()) path.addStep(tempStorage.pop());
        return path;
    }
}
