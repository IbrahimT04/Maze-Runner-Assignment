package ca.mcmaster.se2aa4.mazerunner;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathFinder implements MazeFunctions {

    private static final Logger logger = LogManager.getLogger();
    protected CommandLine cmd;
    protected Maze maze;
    
    public PathFinder(CommandLine cmd){
        this.cmd = cmd;
    }

    /**
     * Solve provided maze with specified method.
     * @throws Exception If provided method does not exist
     */
    @Override
    public void impliment(Maze maze) throws Exception {
        this.maze = maze;
        logger.info("Finding Path");
        String method = cmd.getOptionValue("method", "righthand");
        MazeSolver solver = commandReader(method);
        Path path = solver.solve(maze);
        logger.info(path.getFactorizedForm());
    }
    
    protected static MazeSolver commandReader(String algorithm) throws Exception{
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
                throw new Exception("Maze solving method '" + algorithm + "' not supported.");
            }
        }
    }
}
