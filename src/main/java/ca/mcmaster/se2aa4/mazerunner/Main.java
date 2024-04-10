package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    private static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        logger.info("** Starting Maze Runner");
        CommandLineParser parser = new DefaultParser();

        CommandLine cmd = null;
        MazeFunctions function;

        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            Maze maze = new Maze(filePath);

            if (cmd.getOptionValue("baseline") != null){
                function = new Benchmark(cmd);

            } else if (cmd.getOptionValue("p") != null) {
                function = new PathChecker(cmd);  
                              
            } else {
                function = new PathFinder(cmd);
            }

            function.impliment(maze);

        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }


    /**
     * Get options for CLI parser.
     *
     * @return CLI parser options
     */
    private static Options getParserOptions() {
        Options options = new Options();

        Option fileOption = new Option("i", true, "File that contains maze");
        fileOption.setRequired(true);
        options.addOption(fileOption);

        options.addOption(new Option("p", true, "Path to be verified in maze"));
        options.addOption(new Option("method", true, "Specify which path computation algorithm will be used"));
        options.addOption(new Option("baseline",true, "Compares how fast a given algorithm is"));

        return options;
    }
}
