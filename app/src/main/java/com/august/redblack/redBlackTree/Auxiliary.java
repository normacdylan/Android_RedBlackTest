package redBlackTree;

/**
 * Class that provides some auxiliary methods to trees implemented in an arrays.
 * 
 * @author Markus
 *
 */
public class Auxiliary {
	public static RBTNode RBTNodeFactory(int value) {
		return new RBTNode(value, null);
	}

	/**
	 * A method which prints a linked tree using a folder structure with the
	 * root most to the left.
	 * 
	 * @param tree
	 *            the tree to print.
	 */
	public static void printLinkedTree(RBTree tree) {
		RBTNode root = tree.getRoot();
		if (root == null)
			System.out.println("Empty tree");
		printLinkedTree(root, "");
	}

	private static void printLinkedTree(RBTNode node, String indent) {
		// Can be changed to print a string containing '-' at each leaf.
		if (node == null)
			return;
		printLinkedTree(node.getRightChild(), indent + "\t");
		System.out.println(indent + node.getValue() + " (" + node.getColor().toString() + ")");
		printLinkedTree(node.getLeftChild(), indent + "\t");
	}

	// /**
	// * A method which prints the tree using a folder structure with the root
	// * most to the left.
	// *
	// * @param tree
	// * the tree to print.
	// */
	// public static void printArrayTree(RBTree tree) {
	// if (tree.size() == 0)
	// System.out.println("Empty tree");
	// printArrayTree(tree, 0, "");
	// }
	//
	// private static void printArrayTree(RBTree tree, int index, String indent)
	// {
	// // Can be changed to print a string containing '-' at each leaf.
	// if (tree.size() <= index) {
	// // System.out.println(indent + "-");
	// return;
	// }
	// System.out.println(indent + tree.getNode(index).getColor().toString());
	// printArrayTree(tree, Auxiliary.getLeftChild(index), indent + "\t");
	// printArrayTree(tree, Auxiliary.getRightChild(index), indent + "\t");
	// }

	/**
	 * Computes the index of the left child of a parent node.
	 * 
	 * @param index
	 *            the position of the parent in the tree
	 * @return the index of the left child
	 */
	public static int getLeftChild(int index) {
		// Add one to index in order to start at one instead of zero.
		// Times two gives the left child.
		// Remove one in order the shift first element to position zero.
		return (index + 1) * 2 - 1;
	}

	/**
	 * Computes the index of the right child of a parent node.
	 * 
	 * @param index
	 *            the position of the parent in the tree
	 * @return the index of the right child
	 */
	public static int getRightChild(int index) {
		// Add one to index in order to start at one instead of zero.
		// Times two plus one gives the right child.
		// Remove one in order the shift first element to position zero.
		return (index + 1) * 2;
	}
}
