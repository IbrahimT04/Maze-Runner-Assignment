package ca.mcmaster.se2aa4.mazerunner;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathChecker implements MazeFunctions {

    private static final Logger logger = LogManager.getLogger();
    private CommandLine cmd;
    private Maze maze;

    public PathChecker(CommandLine cmd){
        logger.info("Validating path");
        this.cmd = cmd;
    }

    /**
     * Solve provided maze with specified method.
     * @throws Exception If provided method does not exist
     */
    @Override
    public void impliment(Maze maze){
        this.maze = maze;
        Path path = new Path(cmd.getOptionValue("p"));
        if (validatePath(path)) {
            System.out.println("correct path");
        } else {
            System.out.println("incorrect path");
        }
    }

    // Moved these two subroutines to this class because maze class had this before which does not follow single responsibility 
    /**
     * Check if path is valid for Maze.
     *
     * @param path The path to valid
     * @return If path is valid
     */
    public Boolean validatePath(Path path) {
        return validatePathDir(path, maze.getStart(), Direction.RIGHT, maze.getEnd()) || validatePathDir(path, maze.getEnd(), Direction.LEFT, maze.getStart());
    }

    /**
     * Check if path is valid from starting to end position.
     *
     * @param path Path
     * @param startPos Starting position
     * @param startDir Starting direction
     * @param endPos Ending position
     * @return If path is valid
     */
    private Boolean validatePathDir(Path path, Position startPos, Direction startDir, Position endPos) {
        Position pos = startPos;
        Direction dir = startDir;
        for (char c : path.getPathSteps()) {
            switch (c) {
                case 'F' -> {
                    pos = pos.move(dir);

                    if (pos.x() >= maze.getSizeX() || pos.y() >= maze.getSizeY() || pos.x() < 0 || pos.y() < 0) {
                        return false;
                    }
                    if (maze.isWall(pos)) {
                        return false;
                    }
                }
                case 'R' -> dir = dir.turnRight();
                case 'L' -> dir = dir.turnLeft();
            }
            logger.debug("Current Position: " + pos);
        }

        return pos.equals(endPos);
    }
}
