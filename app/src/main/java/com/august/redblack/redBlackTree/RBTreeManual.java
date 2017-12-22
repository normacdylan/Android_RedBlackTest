package redBlackTree;

/**
 * A class which implements a RedBlackTree data structure, which is a binary
 * tree that has a maximum difference with a factor of two between two paths.
 * This ensures that the finding an element will take log(n) time.
 * 
 * The invariants that accomplishes this are threefold: a) the root must be
 * colored black, b) no red node can have a red parent node, and c) no path from
 * the root to a null leaf can have more black nodes than any other. The
 * constraints a) and b) ensures that the largest difference between any two
 * paths will occur when one consist only of black nodes and the other
 * interleave red and black node, making the latter twice as long.
 * 
 * @author Markus
 *
 */
public class RBTreeManual implements RBTree {
	private RBTNode root;
	private int size = 0;

	@Override
	public void addNode(int value) {
		if (root == null) {
			root = Auxiliary.RBTNodeFactory(value);
			root.changeCol();

		} else {
			// Search for the right position of the new value
			RBTNode match = findMatch(value);

			// If the value already was in the tree, return
			if (match.getValue() == value)
				return;

			// Create a new node and add the (best) match as its parent
			RBTNode x = Auxiliary.RBTNodeFactory(value);
			x.setParent(match);

			// If the closest match was too small, add the new node as its right
			// child
			if (match.getValue() < value)
				match.setRightChild(x);
			// If the closest match was too large, add the new node as its left
			// child
			else
				match.setLeftChild(x);
		} // End of large else statement
		size = size + 1;
	}

	public void zigzig(RBTNode x, RBTNode p, RBTNode gp) {
		boolean isLeftZig = isLeftZig(x, p);
		// Gets great grandparent for later updating
		RBTNode ggp = gp.getParent();
		// Initialises the sibling of the actual node
		RBTNode s;
		// Assignments and rotations if it's a left zigzig
		if (isLeftZig) {
			// Get the sibling of the x
			s = p.getRightChild();

			// Switch p with gp
			p.setRightChild(gp);
			p.setParent(ggp); // It's ok if ggp is null
			gp.setParent(p);
			gp.setLeftChild(s);
		}
		// Assignments and rotations if it's a right zigzig
		else {
			// Get the sibling of the x
			s = p.getLeftChild();

			// Switch p with gp (some overlap with above, but the two extra
			// lines are included for clarity)
			p.setLeftChild(gp);
			p.setParent(ggp);
			gp.setParent(p);
			gp.setRightChild(s);
		}
		// Only p and gp need to change color in the zigzig rotation
		p.changeCol();
		gp.changeCol();

		// Update the root, if needed
		if (gp == root)
			root = p;
		// Otherwise ggp is not null and p must be connected to ggp
		else {
			// If the parent is on the left zig from ggp
			if (ggp.getValue() > p.getValue())
				ggp.setLeftChild(p);
			else
				ggp.setRightChild(p);
		}
	}

	/*
	 * The zigzag sets the actual node, x, in the position that the grandparent,
	 * gp, had, and assigns the two children of x (can be null) to p and gp. It
	 * also switches the reference from an eventual great grandparent to x.
	 * 
	 * Then the color of p and its eventual children are inverted.
	 *
	 * Assumes that the grandparent, gs, the parents sibling, ps, and the
	 * sibling of x, s, are all black, while the parent, p, and the actual node,
	 * x, are red.
	 * 
	 * Also assumes that gs, p, and x are not null.
	 */
	public void zigzag(RBTNode x, RBTNode p, RBTNode gp) {
		boolean isLeftZig = isLeftZig(p, gp);
		// Gets great grandparent for later updating
		RBTNode ggp = gp.getParent();
		// Gets the left child of x
		RBTNode leftChild = x.getLeftChild();
		// Gets the right child of x
		RBTNode rightChild = x.getRightChild();

		// Assignments and rotations if it's a left zigzag
		if (isLeftZig) {
			// Switch x with gp
			x.setRightChild(gp);
			x.setLeftChild(p);
			x.setParent(ggp); // It's ok if ggp is null
			gp.setParent(x);
			p.setParent(x);
			// Reattach the previous children of x
			gp.setLeftChild(rightChild);
			p.setRightChild(leftChild);
		}
		// Assignments and rotations if it's a right zigzig
		else {
			// Switch x with gp
			x.setLeftChild(gp);
			x.setRightChild(p);
			x.setParent(ggp); // It's ok if ggp is null
			gp.setParent(x);
			p.setParent(x);
			// Reattach the previous children of x
			gp.setRightChild(rightChild);
			p.setLeftChild(leftChild);
		}
		// Only x and gp need to change color in the zigzag rotation
		x.changeCol();
		gp.changeCol();

		// Update the root, if needed
		if (gp == root)
			root = x;
		// Otherwise ggp is not null and x must be connected to ggp
		else {
			// If the parent is on the left zig from ggp
			if (ggp.getValue() > x.getValue())
				ggp.setLeftChild(x);
			else
				ggp.setRightChild(x);
		}
	}

	/**
	 * A method for finding the node that matches the value
	 * 
	 * @param value
	 *            the value to search the tree for
	 * @return either the node that matches the value or null, if such node can
	 *         not be found
	 */
	public RBTNode find(int value) {
		if (root == null)
			return null;
		return find(root, value);
	}

	public void recolorRoot() {
		if (root == null)
			return;
		root.changeCol();
	}

	public void recolorTriangle(RBTNode x, RBTNode leftChild, RBTNode rightChild) {
		rightChild.changeCol();
		leftChild.changeCol();
		x.changeCol();
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public RBTNode getRoot() {
		return root;
	}

	private boolean isLeftZig(RBTNode x, RBTNode p) {
		// If the first zig is right
		return x.getValue() < p.getValue() ? true : false;
	}

	private RBTNode find(RBTNode x, int value) {
		// Returning a match, null or calling the method recursively
		int xValue = x.getValue();
		// Return if the node matches the value
		if (xValue == value)
			return x;
		// If the found nodes value is too small, iterate downwards or return
		// null if
		// there are no nodes further down the branch
		else if (xValue > value)
			return x.hasLeftChild() ? find(x.getLeftChild(), value) : null;
		// If the found nodes value is too large, iterate downwards or return
		// null if
		// there are no nodes further down the branch
		else
			return x.hasRightChild() ? find(x.getRightChild(), value) : null;
	}

	private RBTNode findMatch(int value) {
		if (root == null)
			return null;
		RBTNode match = findMatch(root, value);
		return match;
	}

	/**
	 * 
	 * @param node
	 *            the node to be examined.
	 * @param value
	 *            the value to check for.
	 * @return the closest match to the value.
	 */
	private RBTNode findMatch(RBTNode x, int value) {
		// Returning a match or calling the method recursively
		int xValue = x.getValue();
		if (xValue == value)
			return x;
		else if (xValue > value)
			return x.hasLeftChild() ? findMatch(x.getLeftChild(), value) : x;
		else
			return x.hasRightChild() ? findMatch(x.getRightChild(), value) : x;
	}

}
