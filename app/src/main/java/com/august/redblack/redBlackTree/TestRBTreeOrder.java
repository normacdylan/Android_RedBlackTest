package redBlackTree;

/**
 * Class for testing the invariants of RedBlackTrees.
 * 
 * @author Markus
 *
 */
public class TestRBTreeOrder {
	/**
	 * Method that checks whether the attached RedBlackTree is well ordered.
	 * 
	 * @param tree
	 *            the RedBlackTree to check.
	 * @return true if the tree is in order, otherwise false.
	 */
	public static boolean isOrdered(RBTree tree) {
		boolean redKidsOK = redKidsOK(tree);
		boolean blackRootOK = blackRootOK(tree);
		boolean numBlackNodesOK = numBlackNodesOK(tree);
		return redKidsOK && blackRootOK && numBlackNodesOK;
	}

	private static boolean numBlackNodesOK(RBTree tree) {
		// (The method called checks for null.)
		return numBlackNodesOK(tree.getRoot()) == -1 ? false : true;
	}

	private static int numBlackNodesOK(RBTNode node) {
		int numToNull = 0;
		// Checks if this node exists, else returns zero.
		if (node == null)
			return numToNull;

		// Adds the node to the sum if it is black.
		numToNull = (node.getColor() == RBTNode.Col.BLACK) ? 1 : 0;

		int numOnLeft = numBlackNodesOK(node.getLeftChild());
		// If the tree on the left is unbalanced, return -1.
		if (numOnLeft == -1)
			return -1;

		int numOnRight = numBlackNodesOK(node.getRightChild());
		// If the left and right side are unequal, return -1.
		// (Covers if numToRight returns -1 too, since numToLeft != -1.)
		if (numOnLeft != numOnRight)
			return -1;

		// Return the numToNull plus one of numOnRight and numOnLeft, since the
		// total numToNull is the same for both branches.
		numToNull = numToNull + numOnLeft;
		return numToNull;
	}

	private static boolean blackRootOK(RBTree tree) {
		if (tree.size() <= 0)
			return true;
		RBTNode root = tree.getRoot();
		return (root.getColor() == RBTNode.Col.BLACK) ? true : false;
	}

	private static boolean redKidsOK(RBTree tree) {
		RBTNode root = tree.getRoot();
		if (root == null)
			return true;
		// (The method called checks for null.)
		return redKidsOK(root, root.getColor());
	}

	private static boolean redKidsOK(RBTNode node, RBTNode.Col color) {
		// If the node doesn't exist, it doesn't violate the conditions.
		if (node == null)
			return true;

		RBTNode.Col myCol = node.getColor();
		// Check both the node and its parent are red.
		if (color == RBTNode.Col.RED && color == myCol)
			return false;

		// (The method called checks for null.)
		return redKidsOK(node.getLeftChild(), myCol) && redKidsOK(node.getRightChild(), myCol);
	}

}
