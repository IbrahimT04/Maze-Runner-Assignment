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
            // Necessory Parameters
            String filePath = cmd.getOptionValue('i');
            Maze maze = new Maze(filePath);
            // Optional Parameters
            String method = cmd.getOptionValue("method", "righthand");
            String baseline = cmd.getOptionValue("baseline");
            String path = cmd.getOptionValue("p");

            if (baseline != null){
                function = new Benchmark(method, baseline, maze);

            } else if (path != null) {
                function = new PathChecker(path,maze);
                              
            } else {
                function = new PathFinder(method,maze);
            }

            function.impliment();

        } catch (Exception e) {
            logger.error("MazeSolver failed.  Reason: {}", e.getMessage());
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
