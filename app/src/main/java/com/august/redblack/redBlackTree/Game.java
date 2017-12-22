package redBlackTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Game implements IMovable, IModel{
	private List<IViewListener> listeners;
	
	// Game related
	private int score;
	private RBTreeManual tree;
	private Stack<Integer> valuesToAdd;
	
	public Game(){
		// Initialising the list of listeners
		listeners = new ArrayList<>();
		
		// Initialising the game
		score = 0;
		tree = new RBTreeManual();
		
		// Initialises the values that the player will face.
		valuesToAdd = new Stack<Integer>();
		valuesToAdd.push(6);
		valuesToAdd.push(5);
		valuesToAdd.push(15);
		valuesToAdd.push(4);
		valuesToAdd.push(8);
		valuesToAdd.push(9);
		valuesToAdd.push(11);
		valuesToAdd.push(10);
		valuesToAdd.push(12);
	}
	//getTime

	private void nextRound(){
		updateScore();
		tree.addNode(valuesToAdd.pop());
	}
	private void updateScore(){
		if (TestRBTreeOrder.isOrdered(tree)) {
			score++;
		}
	}
	
	////////////////////// Getters
	public int getScore(){
		return score;
	}
	public RBTNode getTree(){
		return tree.getRoot();
	}
	
	////////////////////// Interface IMovable
	@Override
	public void subscribe(IViewListener view){
		listeners.add(view);
	}
	@Override
	public void unSubscribe(IViewListener view){
		listeners.remove(view);
		
	}
	private void notifyViews(){
		
	}
	
	////////////////////// Interface IMovable
	@Override
	public void makeMove(){
		
	}
}
