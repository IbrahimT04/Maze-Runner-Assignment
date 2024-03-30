package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Comparator;
import java.util.Queue;

public class BSTSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private Queue vertices;
    private boolean[][] marked;
    private Maze maze;
    
    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        marked = new boolean[maze.getSizeY()][maze.getSizeX()];
        logger.debug("Marking entrances...");
        bstSearch();
        logger.debug("Tracing path...");
        return tracePath();
    }
    private void bstSearch(){}
    private Path tracePath(){
        return new Path("");
    };
}
