package redBlackTree;

import java.util.Scanner;

/**
 * A class for testing the automatic tree class.
 * 
 * @author Markus
 *
 */
public class TestingAuto{
	public static void main(String[] args) {
		RBTreeAuto tree = new RBTreeAuto();
		Scanner sc = new Scanner(System.in);
		int col;
		System.out.println("Testing the automatic version. Type some numbers. \n");
		while (sc.hasNextInt()) {
			col = sc.nextInt();
			tree.addNode(col);
			System.out.println("The tree currently looks like: \n");
			Auxiliary.printLinkedTree(tree);
		}
		sc.close();
	}
}
