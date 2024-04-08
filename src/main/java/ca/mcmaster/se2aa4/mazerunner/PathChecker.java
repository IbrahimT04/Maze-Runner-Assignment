package ca.mcmaster.se2aa4.mazerunner;
import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PathChecker implements MazeFunctions {

    private static final Logger logger = LogManager.getLogger();
    private CommandLine cmd;
    private Maze maze;

    public PathChecker(CommandLine cmd, Maze maze){
        logger.info("Validating path");
        this.cmd = cmd;
        this.maze = maze;
    }
    @Override
    public void impliment(){
        Path path = new Path(cmd.getOptionValue("p"));
        if (maze.validatePath(path)) {
            System.out.println("correct path");
        } else {
            System.out.println("incorrect path");
        }
    }
}
