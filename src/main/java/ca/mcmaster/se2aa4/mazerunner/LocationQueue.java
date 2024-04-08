package ca.mcmaster.se2aa4.mazerunner;

import java.math.BigInteger;
import java.util.ArrayDeque;
import java.util.Queue;

public class LocationQueue {
    private Position position;
    private Direction direction;
    private BigInteger pathIndex;
    private Queue <Position> posQueue; 
    private Queue <Direction> dirQueue;
    private Queue <BigInteger> indexQueue;
    private Integer length;

    public LocationQueue(Position pos, Direction dir, BigInteger index){
        this.position = pos;
        this.direction = dir;
        this.pathIndex = index;
        this.length = 0;
        posQueue = new ArrayDeque<Position>();
        dirQueue = new ArrayDeque<Direction>();
        indexQueue = new ArrayDeque<BigInteger>();
        add(pos,dir,index);
    }
    public void add(Position pos, Direction dir, BigInteger index){
        this.posQueue.add(pos);
        this.dirQueue.add(dir);
        this.indexQueue.add(index);
        this.length++;
    }
    public void updateQueue(){
        this.position = posQueue.remove();
        this.direction = dirQueue.remove();
        this.pathIndex = indexQueue.remove();
    }
    public Position getPosition(){
        return this.position;
    }
    public Direction getDirection(){
        return this.direction;
    }
    public BigInteger getIndex(){
        return this.pathIndex;
    }
    public boolean isEmpty(){
        return this.length == 0;
    }

}
