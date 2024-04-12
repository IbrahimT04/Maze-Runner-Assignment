package ca.mcmaster.se2aa4.mazerunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MovementTest {
   @Test
    void positionTestRight(){
        Position pos = new Position(1, 1);
        Position moveRight = pos.move(Direction.RIGHT);
        
        assertEquals(new Position(2, 1), moveRight);
    }
    @Test
    void positionTestLeft(){
        Position pos = new Position(1, 1);
        Position moveLeft = pos.move(Direction.LEFT);
      
        assertEquals(new Position(0, 1), moveLeft);
    }
    @Test
    void positionTestUp(){
        Position pos = new Position(1, 1);
        Position moveUp = pos.move(Direction.UP);
        
        assertEquals(new Position(1, 0), moveUp);
    }
    @Test
    void positionTestDown(){
        Position pos = new Position(1, 1);
        Position moveDown = pos.move(Direction.DOWN);
        
        assertEquals(new Position(1, 2), moveDown);
    }
    @Test
    void directionTestRight(){
        Direction dir = Direction.RIGHT;
        Direction rightTurn = dir.turnRight();

        assertEquals(Direction.DOWN, rightTurn);
    }
    @Test
    void directionTestLeft(){
        Direction dir = Direction.RIGHT;
        Direction leftTurn = dir.turnLeft();

        assertEquals(Direction.UP, leftTurn);
    }
    @Test
    void moveTest(){
        Position pos = new Position(1, 1);
        Position otherPos = new Position(9, 9);
        
        assertEquals(new Position(10, 10), pos.add(otherPos));
        
    }
}
