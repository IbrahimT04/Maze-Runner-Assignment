package ca.mcmaster.se2aa4.mazerunner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Maze {
    private static final Logger logger = LogManager.getLogger();

    private final List<List<Boolean>> maze = new ArrayList<>();

    private final Position start;
    private final Position end;

    /**
     * Initialize a Maze from a file path.
     *
     * @param filePath File path of the maze file
     * @throws Exception If maze cannot be read, or maze has no start or end
     */
    public Maze(String filePath) throws Exception {
        logger.debug("Reading the maze from file " + filePath);
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            List<Boolean> newLine = new ArrayList<>();
            for (int idx = 0; idx < line.length(); idx++) {
                if (line.charAt(idx) == '#') {
                    newLine.add(true);
                } else if (line.charAt(idx) == ' ') {
                    newLine.add(false);
                }
            }
            maze.add(newLine);
        }
        // Making sure to close reader
        reader.close();
        start = findStart();
        end = findEnd();
    }

    /**
     * Find start position of the maze.
     *
     * @return The start position
     * @throws Exception If no valid start position exists
     */
    private Position findStart() throws Exception {
        for (int i = 0; i < maze.size(); i++) {
            Position pos = new Position(0, i);
            if (!isWall(pos)) {
                return pos;
            }
        }
        throw new Exception("Invalid maze (no start position available)");
    }

    /**
     * Find start end of the maze.
     *
     * @return The end position
     * @throws Exception If no valid end position exists
     */
    private Position findEnd() throws Exception {
        for (int i = 0; i < maze.size(); i++) {
            Position pos = new Position(maze.getFirst().size() - 1, i);
            if (!isWall(pos)) {
                return pos;
            }
        }
        throw new Exception("Invalid maze (no end position available)");
    }

    /**
     * Check if position of Maze is a wall.
     *
     * @param pos The position to check
     * @return If position is a wall
     */
    public Boolean isWall(Position pos) {
        return maze.get(pos.y()).get(pos.x());
    }

    /**
     * Get start position.
     *
     * @return Start position
     */
    public Position getStart() {
        return start;
    }

    /**
     * Get end position.
     *
     * @return End position
     */
    public Position getEnd() {
        return end;
    }

    /**
     * Get horizontal (X) size of Maze.
     *
     * @return Horizontal size
     */
    public int getSizeX() {
        return this.maze.getFirst().size();
    }

    /**
     * Get vertical (Y) size of Maze.
     *
     * @return Vertical size
     */
    public int getSizeY() {
        return this.maze.size();
    }

    
     /**
     * Check if position is in the maze bounds.
     *
     * @param position Position to validate
     * @param sizeX    Maze horizontal (X) size
     * @param sizeY    Maze vertical (Y) size
     * @return If position is in bounds
     */
    // I moved this function from the tremaux and bfs classes into this one so it would not be repeated twice
    public boolean isInBounds(Position position) {
        return position.x() >= 0 && position.x() < getSizeX() && position.y() >= 0 && position.y() < getSizeY();
    }
}

