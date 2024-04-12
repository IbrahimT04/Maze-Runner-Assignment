package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PathTest {
    
    @Test
    void getCanonicalForm() {
        Path path = new Path("FLFFFFFRFFRFFLFFFFFFRFFFFLF");

        assertEquals("F L FFFFF R FF R FF L FFFFFF R FFFF L F", path.getCanonicalForm());
    }

    @Test
    void getFactorizedForm() {
        Path path = new Path("FLFFFFFRFFRFFLFFFFFFRFFFFLF");

        assertEquals("F L 5F R 2F R 2F L 6F R 4F L F", path.getFactorizedForm());
    }

    @Test
    void expandedPath() {
        Path path = new Path("4F 3R L");

        assertEquals("FFFF RRR L", path.getCanonicalForm());
    }


    @Test
    void expandedPath2() {
        Path path = new Path("10F 11R");

        assertEquals("FFFFFFFFFF RRRRRRRRRRR", path.getCanonicalForm());
    }
    @Test
    void addStep(){
        Path path1 = new Path("2F 3R F L");
        Path path2 = new Path();

        path2.addStep('F');
        path2.addStep('F');

        path2.addStep('R');
        path2.addStep('R');
        path2.addStep('R');

        path2.addStep('F');
        path2.addStep('L');

        assertEquals(path1.getCanonicalForm(), path2.getCanonicalForm());
    }    
}