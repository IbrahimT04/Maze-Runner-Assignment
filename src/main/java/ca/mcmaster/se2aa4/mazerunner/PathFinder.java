package ca.mcmaster.se2aa4.mazerunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathFinder implements MazeFunctions {

    private static final Logger logger = LogManager.getLogger();
    protected String method;
    protected Maze maze;
    
    public PathFinder(String method, Maze maze){
        this.method = method;
        this.maze = maze;
    }

    /**
     * Solve provided maze with specified method.
     * @throws Exception If provided method does not exist
     */
    @Override
    public void impliment() throws IllegalArgumentException {
        logger.info("Finding Path");
        MazeSolver solver = commandReader(method);
        Path path = solver.solve(maze);
        logger.info(path.getFactorizedForm());
    }
    
    protected static MazeSolver commandReader(String algorithm) throws IllegalArgumentException{
        switch (algorithm) {
            case "righthand" -> {
                logger.debug("RightHand method chosen.");
                return new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux method chosen.");
                return new TremauxSolver();
            }
            case "bfs" -> {
                logger.debug("Breadth First Search method chosen.");
                return new BFSSolver();
            }
            default -> {
                throw new IllegalArgumentException("Maze solving method '" + algorithm + "' not supported.");
            }
        }
    }
}
