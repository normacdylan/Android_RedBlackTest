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
public class RBTreeAuto implements RBTree{
	private RBTNode root;
	private int size = 0;
	@Override
	public void addNode(int value) {
		if (root == null) {
			root = Auxiliary.RBTNodeFactory(value);
			root.changeCol();
			
		} else {
			// Search for the right position of the new value, and if two red
			// siblings are encountered while doing so, the tree is reorganized.
			RBTNode match = findAndReorganize(value);
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
			// If both the match and its new child are red, run the rotate
			// method
			if (match.getColor() == RBTNode.Col.RED)
				rotate(x, match, match.getParent());
		}// End of large else statement
		size = size + 1;
	}

	/*
	 * Call when two siblings are both red with a red grandparent, after having
	 * made the grandparent and one of the siblings black, and having made the
	 * parent red. The parameter x should be the (still) red sibling.
	 */
	private void rotate(RBTNode x, RBTNode p, RBTNode gp) {
		// If the first zig is right
		if (gp.getValue() < p.getValue()) {
			// The right zigzig case
			if (p.getValue() < x.getValue()) {
				boolean isLeftZig = false;
				zigzig(x, p, gp, isLeftZig);
			}
			// The right zigzag case.
			else {
				boolean isLeftZig = false;
				zigzag(x, p, gp, isLeftZig);
			}
		}
		// If the first zig is left
		else {
			// The left zigzig case
			if (p.getValue() > x.getValue()) {
				boolean isLeftZig = true;
				zigzig(x, p, gp, isLeftZig);
			}
			// The left zigzag case
			else {
				boolean isLeftZig = true;
				zigzag(x, p, gp, isLeftZig);
			}
		}
	}

	/*
	 * The zigzig sets the parent, p, in the position that the grandparent, gp,
	 * had, with the sibling, s, as a child of gp. It also switches the
	 * reference from an eventual great grandparent to p.
	 * 
	 * Then the color of p and gp are inverted.
	 * 
	 * Assumes that the grandparent, gs, the parents sibling, ps, and the
	 * sibling of x, s, are all black, while the parent, p, and the actual node,
	 * x, are red.
	 * 
	 * Also assumes that gs, p, and x are not null.
	 */
	private void zigzig(RBTNode x, RBTNode p, RBTNode gp, boolean isLeftZig) {
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
	private void zigzag(RBTNode x, RBTNode p, RBTNode gp, boolean isLeftZig) {
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

	private RBTNode findAndReorganize(int value) {
		if (root == null)
			return null;
		RBTNode match = findAndReorganize(root, value);

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
	private RBTNode findAndReorganize(RBTNode x, int value) {
		/* The recoloring-on-the-way-down bit */
		// Check if it has two red children
		if (x.hasLeftChild() && x.hasRightChild()) {
			RBTNode leftChild = x.getLeftChild();
			RBTNode rightChild = x.getRightChild();
			if (leftChild.getColor() == RBTNode.Col.RED && rightChild.getColor() == RBTNode.Col.RED) {
				twoRedChildren(x, leftChild, rightChild);
			}
		} // End of double if-statement

		// Returning a match or calling the method recursively
		int xValue = x.getValue();
		if (xValue == value)
			return x;
		else if (xValue > value)
			return x.hasLeftChild() ? findAndReorganize(x.getLeftChild(), value) : x;
		else
			return x.hasRightChild() ? findAndReorganize(x.getRightChild(), value) : x;
	}

	private void twoRedChildren(RBTNode x, RBTNode leftChild, RBTNode rightChild) {
		// If the parent of x is red, recolor one of the children to x.
		// Then call rotate wrt to the other child
		RBTNode p = x.getParent();
		if (p != null) {
			if (p.getColor() == RBTNode.Col.RED) {
				// Color both children black
				rightChild.changeCol();
				leftChild.changeCol();

				// Color their parent red
				x.changeCol();

				// Call rotate wrt x, p, p.parent now that the tree fulfills the
				// prerequisite for the rotate method
				rotate(x, p, p.getParent());
				return;
			}
		}
		// If the parent is black or null, it's enough to recolor x and the
		// children
		x.changeCol();
		leftChild.changeCol();
		rightChild.changeCol();

		// Recolor x black if x was the root
		if (x == root) {
			x.changeCol();
		}
	}
	@Override
	public int size() {
		return size;
	}
	@Override
	public RBTNode getRoot() {
		return root;
	}

}
