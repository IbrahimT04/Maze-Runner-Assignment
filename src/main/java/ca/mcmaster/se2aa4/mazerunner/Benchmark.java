package ca.mcmaster.se2aa4.mazerunner;

import org.apache.commons.cli.CommandLine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Benchmark extends PathFinder {

    private static final Logger logger = LogManager.getLogger();
    
    public Benchmark(CommandLine cmd, Maze maze){
        super(cmd, maze);
        logger.info("Benchmarking");   
    }

    @Override
    public void impliment() throws Exception {

        float Speedup;

        String method = cmd.getOptionValue("method");
        String baseline = cmd.getOptionValue("baseline");

        MazeSolver solver1 = commandReader(method);
        MazeSolver solver2 = commandReader(baseline);

        String path1 = timePath(solver1, method);
        String path2 = timePath(solver2, baseline); 

        Speedup = (path2.length())*1f/(path1.length());
        logger.info("Runtime Speedup = %.2f\n", Speedup);
    }

    private String timePath(MazeSolver solver, String method){
        long mTimeStart = System.nanoTime();
        Path path = solver.solve(maze);
        long mTimeEnd = System.nanoTime();
        float methodTime = (mTimeEnd -mTimeStart)/1000000f;
        logger.info("Runtime %s = %.2f ms\n", method, methodTime);
        return path.getCanonicalForm();
    }

        
}
