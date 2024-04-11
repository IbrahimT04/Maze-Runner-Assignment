package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Benchmark extends PathFinder {
    private static final Logger logger = LogManager.getLogger();
    
    public Benchmark(CommandLine cmd){
        super(cmd);
        logger.info("Benchmarking");   
    }

    /**
     * Solve provided maze with specified method.
     * @throws Exception If provided method does not exist
     */
    @Override
    public void impliment(Maze maze) throws IllegalArgumentException {
        this.maze = maze;
        String method = cmd.getOptionValue("method");
        String baseline = cmd.getOptionValue("baseline");

        MazeSolver solver1 = commandReader(method);
        MazeSolver solver2 = commandReader(baseline);

        // Used both timePath methods to show ease of use
        float time1 = timePath(solver1);
        logger.info("Runtime %s = %.2f ms %n", method, time1);
        Path path = solver1.solve(maze);
        String path1 = path.getCanonicalForm();

        String path2 = timePath(solver2, baseline);

        logger.info("Runtime Speedup = %.2f %n", getSpeedup(path1, path2));
    }

    private String timePath(MazeSolver solver, String method){
        long mTimeStart = System.nanoTime();
        Path path = solver.solve(maze);
        long mTimeEnd = System.nanoTime();
        float methodTime = (mTimeEnd -mTimeStart)/1000000f;
        System.out.printf("Runtime %s = %.2f ms %n", method, methodTime);
        return path.getCanonicalForm();
    }

    // Extra function if person wants the time returned in a value instead of a print statement
    public float timePath(MazeSolver solver){
        long mTimeStart = System.nanoTime();
        solver.solve(maze);
        long mTimeEnd = System.nanoTime();
        return (mTimeEnd -mTimeStart)/1000000f;
    }

    public static float getSpeedup(String path1, String path2) throws ArithmeticException{
        if (path1.length() == 0) throw new ArithmeticException("The method path '" + path1 + "' has a length of 0.");
        return path2.length()*1f/path1.length();
 
    } 
}
