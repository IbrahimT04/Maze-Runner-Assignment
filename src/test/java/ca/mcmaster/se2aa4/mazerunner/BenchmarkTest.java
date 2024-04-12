package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BenchmarkTest {
    Benchmark myBenchmark;
    Maze myMaze;
    @Test
    void timeCheck() {
        try {
            myMaze = new Maze("./examples/medium.maz.txt");
        }
        catch (Exception IOException) {
            assertTrue(false);
        } 
        myBenchmark = new Benchmark("bfs", "righthand", myMaze);
        float timing1 = myBenchmark.timePath(new RightHandSolver());
        float timing2 = myBenchmark.timePath(new RightHandSolver());
        float errorMargin1 = 0.4f * timing1;
        float errorMargin2 = 20.0f;
        float timingError = Math.abs(timing2-timing1);
        assertTrue(timingError <= Math.max(errorMargin1, errorMargin2));
    }
    @Test
    void speedupCheck() {
        Path path = new Path("FLFFRFFRFFLFFFFFFRFFFFLF");
        String pather = path.getCanonicalForm();
        try {
            assertEquals(1.00, Benchmark.getSpeedup(pather,pather));

        } catch (Exception e) {
            assertTrue(false);
        }
    }
}
