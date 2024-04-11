package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TremauxSolver implements MazeSolver {
    private static final Logger logger = LogManager.getLogger();
    private int[][] marks;
    private Maze maze;

    @Override
    public Path solve(Maze maze) {
        this.maze = maze;
        marks = new int[maze.getSizeY()][maze.getSizeX()];
        logger.debug("Marking entrances...");
        markEntrances();
        logger.debug("Tracing path...");
        return tracePath();
    }

    /**
     * Mark entrances in marks 2D array.
     */
    private void markEntrances() {
        Position currentPos = maze.getStart();
        Position previousPos = null;
        marks[currentPos.y()][currentPos.x()] = 1;

        while (!currentPos.equals(maze.getEnd())) {
            List<Position> neighbors = getMazeNeighbors(currentPos);
            neighbors.remove(previousPos);

            Position newPos;
            if (neighbors.isEmpty()) {
                // Go back
                newPos = previousPos;
            } else if (neighbors.size() == 1) {
                // Move forward
                newPos = neighbors.getFirst();
            } else {
                // At junction
                Position fewestMarks = pickNeighbor(neighbors);
                if (marks[previousPos.y()][previousPos.x()] != 0 && marks[fewestMarks.y()][fewestMarks.x()] == 0) {
                    newPos = fewestMarks;
                } else if (marks[previousPos.y()][previousPos.x()] != 2) {
                    newPos = previousPos;
                } else {
                    newPos = fewestMarks;
                }
            }

            if ((previousPos != null && getMazeNeighbors(previousPos).size() > 2)||(getMazeNeighbors(newPos).size() > 2)) {
                marks[currentPos.y()][currentPos.x()] += 1;
            }
            previousPos = currentPos;
            currentPos = newPos;
        }
        marks[maze.getEnd().y()][maze.getEnd().x()] = 1;
    }

    /**
     * Chose a viable neighbor by sorting the neighbors by the number of
     * marks and picking the first.
     *
     * @param neighbors Neighbors to chose from
     * @return Chosen neighbor
     */
    private Position pickNeighbor(List<Position> neighbors) {
        neighbors.sort(Comparator.comparingInt(pos -> marks[pos.y()][pos.x()]));
        return neighbors.getFirst();
    }

    /**
     * Get list of possible valid neighbors of position.
     *
     * @param pos Position to get neighbors of
     * @return List of neighbors
     */
    private List<Position> getMazeNeighbors(Position pos) {
        List<Position> neighbors = new ArrayList<>();

        if (pos == null) throw new NullPointerException("Current position doesn't exist");

        Position left = pos.add(new Position(-1, 0));
        if (left.x() >= 0 && Boolean.FALSE.equals(maze.isWall(left))) neighbors.add(left);

        Position right = pos.add(new Position(1, 0));
        if (right.x() < maze.getSizeX() && Boolean.FALSE.equals(maze.isWall(right))) neighbors.add(right);

        Position up = pos.add(new Position(0, -1));
        if (up.y() >= 0 && Boolean.FALSE.equals(maze.isWall(up))) neighbors.add(up);

        Position down = pos.add(new Position(0, 1));
        if (down.y() < maze.getSizeY() && Boolean.FALSE.equals(maze.isWall(down))) neighbors.add(down);

        return neighbors;
    }

    /**
     * Create path from start to end using marks.
     *
     * @return Path from start to end
     */
    private Path tracePath() {
        Path path = new Path();

        Direction dir = Direction.RIGHT;
        Position pos = maze.getStart();

        while (!pos.equals(maze.getEnd())) {
            Position rightPos = pos.move(dir.turnRight());
            Position leftPos = pos.move(dir.turnLeft());
            Position forwardPos = pos.move(dir);
            if (validPosCheck(rightPos, 1)) {
                path.addStep('R');
                path.addStep('F');
                dir = dir.turnRight();
                pos = rightPos;
            } else if (validPosCheck(leftPos, 1)) {
                path.addStep('L');
                path.addStep('F');
                dir = dir.turnLeft();
                pos = leftPos;
            } else if (validPosCheck(forwardPos, 1)) {
                path.addStep('F');
                pos = forwardPos;
            } else {
                if (validPosCheck(rightPos, 0)) {
                    path.addStep('R');
                    path.addStep('F');
                    dir = dir.turnRight();
                    pos = rightPos;
                } else if (validPosCheck(leftPos, 0)) {
                    path.addStep('L');
                    path.addStep('F');
                    dir = dir.turnLeft();
                    pos = leftPos;
                } else if (validPosCheck(forwardPos, 0)) {
                    path.addStep('F');
                    pos = forwardPos;
                } else {
                    throw new IllegalStateException("Invalid maze.");
                }
            }
        }
        return path;
    }

    private boolean validPosCheck(Position pos, int visited){
        return maze.isInBounds(pos)
         && !maze.isWall(pos)
         && marks[pos.y()][pos.x()] == visited;
    }
}
