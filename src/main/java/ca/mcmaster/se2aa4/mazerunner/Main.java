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
        try {
            cmd = parser.parse(getParserOptions(), args);
            String filePath = cmd.getOptionValue('i');
            Maze maze = new Maze(filePath);

            // New conditional option for benchmark option
            if (cmd.getOptionValue("baseline") != null){
                logger.info("Benchmarking");
                benchmark(cmd.getOptionValue("method"), cmd.getOptionValue("baseline"), maze);
            }
            else if (cmd.getOptionValue("p") != null) {
                logger.info("Validating path");
                Path path = new Path(cmd.getOptionValue("p"));
                if (maze.validatePath(path)) {
                    System.out.println("correct path");
                } else {
                    System.out.println("incorrect path");
                }
            } else {
                String method = cmd.getOptionValue("method", "righthand");
                Path path = solveMaze(method, maze);
                System.out.println(path.getFactorizedForm());
            }
        } catch (Exception e) {
            System.err.println("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("MazeSolver failed.  Reason: " + e.getMessage());
            logger.error("PATH NOT COMPUTED");
        }

        logger.info("End of MazeRunner");
    }

    /**
     * Solve provided maze with specified method.
     *
     * @param method Method to solve maze with
     * @param maze Maze to solve
     * @return Maze solution path
     * @throws Exception If provided method does not exist
     */
    private static Path solveMaze(String method, Maze maze) throws Exception {
        MazeSolver solver = null;
        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand algorithm chosen.");
                solver = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux algorithm chosen.");
                solver = new TremauxSolver();
            }
            // Added now algorithm (Currently in testing phase)
            case "bfs" -> {
                logger.debug("Breadth First Search algorithm chosen.");
                solver = new BFSSolver();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        logger.info("Computing path");
        return solver.solve(maze);
    }

    
    private static void benchmark(String method, String baseline, Maze maze) throws Exception {
        MazeSolver solver1 = null;
        MazeSolver solver2 = null;
        Path path1 = null;
        Path path2 = null;

        long mTimeStart = 0;
        long mTimeEnd = 0;
        float methodTime = 0f;

        long bTimeStart = 0;
        long bTimeEnd = 0;
        float baselineTime = 0f;

        float Speedup = 0f;

        switch (method) {
            case "righthand" -> {
                logger.debug("RightHand method chosen.");
                solver1 = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux method chosen.");
                solver1 = new TremauxSolver();
            }
            // Added now algorithm (Currently in testing phase)
            case "bfs" -> {
                logger.debug("Breadth First Search method chosen.");
                solver1 = new BFSSolver();
            }
            default -> {
                throw new Exception("Maze solving method '" + method + "' not supported.");
            }
        }

        switch (baseline) {
            case "righthand" -> {
                logger.debug("RightHand method chosen.");
                solver2 = new RightHandSolver();
            }
            case "tremaux" -> {
                logger.debug("Tremaux method chosen.");
                solver2 = new TremauxSolver();
            }
            // Added now algorithm (Currently in testing phase)
            case "bfs" -> {
                logger.debug("Breadth First Search method chosen.");
                solver2 = new BFSSolver();
            }
            default -> {
                throw new Exception("Maze solving baseline '" + baseline + "' not supported.");
            }
        }

        logger.info("Benchmarking");

        // Testing JVM warmup to see effects on runtime
        solver1.solve(maze);
        solver2.solve(maze);
        solver1.solve(maze);
        solver2.solve(maze);
        solver1.solve(maze);
        solver2.solve(maze);
        
        mTimeStart = System.nanoTime();
        path1 = solver1.solve(maze);
        mTimeEnd = System.nanoTime();
        methodTime = (mTimeEnd -mTimeStart)/1000000f;
        System.out.printf("Runtime %s = %.2f ms\n", method, methodTime);

        bTimeStart = System.nanoTime();
        path2 = solver2.solve(maze);
        bTimeEnd = System.nanoTime();
        baselineTime = (bTimeEnd-bTimeStart)/1000000f;
        System.out.printf("Runtime %s = %.2f ms\n", baseline, baselineTime);

        Speedup = (path2.getCanonicalForm().length())*1f/(path1.getCanonicalForm().length());
        System.out.printf("Runtime Speedup = %.2f\n", Speedup);
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
        // New command line benchmark
        options.addOption(new Option("baseline",true, "Compares how fast a given algorithm is"));

        return options;
    }
}
