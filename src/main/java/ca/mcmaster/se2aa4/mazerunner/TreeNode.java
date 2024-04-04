package ca.mcmaster.se2aa4.mazerunner;
// Possible helper class for bfs implimentation
public class TreeNode {
    public Character movement;
    public TreeNode root;
    private TreeNode right;
    private TreeNode left;
    private TreeNode middle;

    public TreeNode(Character move){
        this.movement = move;
    }
    public void addRightChild(TreeNode rChild){
        this.right = rChild;
        rChild.root = this;
    }
    public void addLeftChild(TreeNode lChild){
        this.right = lChild;
        lChild.root = this;   
    }
    public void addForwardChild(TreeNode fChild){
        this.middle = fChild;
        fChild.root = this;
    }
    
}
