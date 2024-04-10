package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BenchmarkTest {
    Maze maze;
    Benchmark benchmark;
    /* 
    @BeforeAll
    void beforeAll(){
        try {        
            maze = new Maze("./examples/straight.maz.txt");
            CommandLine cmd = null;
            benchmark = new Benchmark(cmd);
        } 
        catch (Exception e) {
            assertEquals(1,2);
            }
        }
    */
    @Test
    void speedupCheck() {
        Path path = new Path("FLFFRFFRFFLFFFFFFRFFFFLF");
        String pather = path.getCanonicalForm();
        try {
            assertEquals(1.00, Benchmark.getSpeedup(pather,pather));

        } catch (Exception e) {
            assertEquals(1,2);
        }
    }
    /*
    @Test
    void runtimeCheck(){
        try {
            assertEquals(benchmark.timePath(new BFSSolver()), benchmark.timePath(new BFSSolver()));
        } catch (Exception e) {
            assertEquals(1,2);
        }
    }
    */
}
