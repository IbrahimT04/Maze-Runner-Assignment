package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BenchmarkTest {
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
}
