package ca.mcmaster.se2aa4.mazerunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Benchmark extends PathFinder {
    private static final Logger logger = LogManager.getLogger();
    private String baseline;

    public Benchmark(String method, String baseline, Maze maze){
        super(method,maze);
        this.baseline = baseline;
        logger.info("Benchmarking");   
    }

    /**
     * Solve provided maze with specified method.
     * @throws IllegalArgumentException If provided method does not exist
     */
    @Override
    public void impliment() throws IllegalArgumentException {

        MazeSolver solver1 = commandReader(method);
        MazeSolver solver2 = commandReader(baseline);

        // Used both timePath methods to show ease of use
        float time1 = timePath(solver1);
        time1 = Math.round(time1*100)/100f;
        logger.info("Runtime {} = {} ms \n", method, time1);

        Path path = solver1.solve(maze);
        String path1 = path.getCanonicalForm();

        String path2 = timePath(solver2, baseline);
        float speedup = getSpeedup(path1, path2);
        
        speedup = Math.round(speedup*100)/100f;
        logger.info("Runtime Speedup = {} \n", speedup);
    }

    private String timePath(MazeSolver solver, String method){
        long mTimeStart = System.nanoTime();
        Path path = solver.solve(maze);
        long mTimeEnd = System.nanoTime();

        float methodTime = (mTimeEnd -mTimeStart)/1000000f;
        methodTime = Math.round(methodTime*100)/100f;

        logger.info("Runtime {} = {} ms \n", method, methodTime);
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