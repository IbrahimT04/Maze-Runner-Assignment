package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmTest {
    Maze myMaze;

    @Test
    void allAlgoTest(){
        try {
            myMaze = new Maze("./examples/small.maz.txt");
        }
        catch (Exception IOException) {
            assertTrue(false);
        }
        AlgoChecker(new BFSSolver());
        AlgoChecker(new TremauxSolver());
        AlgoChecker(new RightHandSolver());

    }

    private void AlgoChecker(MazeSolver solver){        
        Path myPath = solver.solve(myMaze);
        PathChecker myPathChecker = new PathChecker(myPath.getCanonicalForm(), myMaze);
        assertTrue(myPathChecker.validatePath());

    }
    
}
