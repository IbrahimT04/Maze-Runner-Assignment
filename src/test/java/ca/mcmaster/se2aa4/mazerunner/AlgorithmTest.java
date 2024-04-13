package ca.mcmaster.se2aa4.mazerunner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AlgorithmTest {
    Maze myMaze;
    @Test
    void BreadthFirstSearchTest(){
        assertTrue(AlgoChecker(new BFSSolver()));
    }

    @Test
    void TremauxTest(){
        assertTrue(AlgoChecker(new TremauxSolver()));
    }

     @Test
    void RightHandTest(){
        assertTrue(AlgoChecker(new RightHandSolver()));
    }

    private boolean AlgoChecker(MazeSolver solver){       
        try {
            myMaze = new Maze("./examples/small.maz.txt");
        }
        catch (Exception IOException) {
            assertTrue(false);
        } 
        Path myPath = solver.solve(myMaze);
        PathChecker myPathChecker = new PathChecker(myPath.getCanonicalForm(), myMaze);
        return myPathChecker.validatePath();
    }
}
