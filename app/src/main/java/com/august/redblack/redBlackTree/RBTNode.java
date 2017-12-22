package redBlackTree;

/**
 * Node class for filling a RedBlackTree.
 * 
 * @author Markus
 *
 */
public class RBTNode {
	public enum Col {
		RED, BLACK;
		public static Col opposite(Col t) {
			return t == BLACK ? RED : BLACK;
		}
	}

	private Col color;
	private int value;
	private RBTNode parent;
	private RBTNode leftChild;
	private RBTNode rightChild;

	public RBTNode(int value) {
		this(value, null);
	}
	
	public RBTNode(int value, RBTNode parent) {
		// Always initialize as red.
		this.color = Col.RED;
		this.value = value;
	}
	
	public void setParent(RBTNode node){
		parent = node;
	}

	public void setLeftChild(RBTNode node){
		leftChild = node;
	}

	public void setRightChild(RBTNode node){
		rightChild = node;
	}
	public RBTNode getParent(){
		return parent;
	}

	public RBTNode getLeftChild(){
		return leftChild;
	}

	public RBTNode getRightChild(){
		return rightChild;
	}
	
	public int getValue(){
		return value;
	}
	public Col getColor(){
		return color;
	}
	public void changeCol() {
		color = Col.opposite(color);
	}
	
	public boolean isRoot(){
		return parent == null? true : false;
	}
	
	public boolean hasLeftChild(){
		return leftChild == null? false:true;
	}
	
	public boolean hasRightChild(){
		return rightChild == null? false:true;
	}
}
