/*
 * Copyright (c) 2018 by Jordan Pickett and Nora White. All rights reserved.
 *
 * Apr 10, 2018
 * 
 */
package CPSC3620;

/**
 * The Class RedBlackTree.
 *
 * @author Jordan Pickett and Nora White
 * @param <T> the generic type
 */
public class RedBlackTree<T extends Comparable<T>> {

	private Node<T> nil = new Node<T>();
	private Node<T> root = nil;

	/**
	 * Instantiates a new red black tree.
	 */
	public RedBlackTree() {
		root.left = nil;
		root.right = nil;
		root.parent = nil;
	}

	/**
	 * Left rotate.
	 *
	 * @param x the x
	 */
	private void leftRotate(Node<T> x) {

		// Call leftRotateFixup() which updates the numLeft
		// and numRight values.
		// leftRotateFixup(x);

		// Perform the left rotate as described in the algorithm
		// in the course text.
		Node<T> y;
		y = x.right;
		x.right = y.left;

		// Check for existence of y.left and make pointer changes
		if (!isNil(y.left))
			y.left.parent = x;
		y.parent = x.parent;

		// x's parent is nul
		if (isNil(x.parent))
			root = y;

		// x is the left child of it's parent
		else if (x.parent.left == x)
			x.parent.left = y;

		// x is the right child of it's parent.
		else
			x.parent.right = y;

		// Finish of the leftRotate
		y.left = x;
		x.parent = y;
	}

	/**
	 * Right rotate.
	 *
	 * @param y the y
	 */
	private void rightRotate(Node<T> y) {

		// Call rightRotateFixup to adjust numRight and numLeft values
		// rightRotateFixup(y);

		// Perform the rotate as described in the course text.
		Node<T> x = y.left;
		y.left = x.right;

		// Check for existence of x.right
		if (!isNil(x.right))
			x.right.parent = y;
		x.parent = y.parent;

		// y.parent is nil
		if (isNil(y.parent))
			root = x;

		// y is a right child of it's parent.
		else if (y.parent.right == y)
			y.parent.right = x;

		// y is a left child of it's parent.
		else
			y.parent.left = x;
		x.right = y;

		y.parent = x;

	}// end rightRotate(Node y)

	/**
	 * Insert.
	 *
	 * @param key the key
	 */
	public void insert(T key) {
		insert(new Node<T>(key));
	}

	/**
	 * Insert.
	 *
	 * @param z the z
	 */
	private void insert(Node<T> z) {

		// Create a reference to root & initialize a node to nil
		Node<T> y = nil;
		Node<T> x = root;

		// While we haven't reached a the end of the tree keep
		// tryint to figure out where z should go
		while (!isNil(x)) {
			y = x;

			// if z.key is < than the current key, go left
			if (z.key.compareTo(x.key) < 0) {

				// Update x.numLeft as z is < than x
				x.numLeft++;
				x = x.left;
			}

			// else z.key >= x.key so go right.
			else {

				// Update x.numGreater as z is => x
				x.numRight++;
				x = x.right;
			}
		}
		// y will hold z's parent
		z.parent = y;

		// Depending on the value of y.key, put z as the left or
		// right child of y
		if (isNil(y))
			root = z;
		else if (z.key.compareTo(y.key) < 0)
			y.left = z;
		else
			y.right = z;

		// Initialize z's children to nil and z's color to red
		z.left = nil;
		z.right = nil;
		z.color = Node.RED;

		// Call insertFixup(z)
		insertFixup(z);

	}

	/**
	 * Insert fixup.
	 *
	 * @param z the z
	 */
	private void insertFixup(Node<T> z) {

		Node<T> y = nil;
		// While there is a violation of the RedBlackTree properties..
		while (z.parent.color == Node.RED) {

			// If z's parent is the the left child of it's parent.
			if (z.parent == z.parent.parent.left) {

				// Initialize y to z 's cousin
				y = z.parent.parent.right;

				// Case 1: if y is red...recolor
				if (y.color == Node.RED) {
					z.parent.color = Node.BLACK;
					y.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					z = z.parent.parent;
				}
				// Case 2: if y is black & z is a right child
				else if (z == z.parent.right) {

					// leftRotaet around z's parent
					z = z.parent;
					leftRotate(z);
				}

				// Case 3: else y is black & z is a left child
				else {
					// recolor and rotate round z's grandpa
					z.parent.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					rightRotate(z.parent.parent);
				}
			}

			// If z's parent is the right child of it's parent.
			else {

				// Initialize y to z's cousin
				y = z.parent.parent.left;

				// Case 1: if y is red...recolor
				if (y.color == Node.RED) {
					z.parent.color = Node.BLACK;
					y.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					z = z.parent.parent;
				}

				// Case 2: if y is black and z is a left child
				else if (z == z.parent.left) {
					// rightRotate around z's parent
					z = z.parent;
					rightRotate(z);
				}
				// Case 3: if y is black and z is a right child
				else {
					// recolor and rotate around z's grandpa
					z.parent.color = Node.BLACK;
					z.parent.parent.color = Node.RED;
					leftRotate(z.parent.parent);
				}
			}
		}
		// Color root black at all times
		root.color = Node.BLACK;

	}

	/**
	 * Tree minimum.
	 *
	 * @param node the node
	 * @return the node
	 */
	private Node<T> treeMinimum(Node<T> node) {

		// while there is a smaller key, keep going left
		while (!isNil(node.left))
			node = node.left;
		return node;
	}

	/**
	 * Tree successor.
	 *
	 * @param x the x
	 * @return the node
	 */
	private Node<T> treeSuccessor(Node<T> x) {

		// if x.left is not nil, call treeMinimum(x.right) and
		// return it's value
		if (!isNil(x.left))
			return treeMinimum(x.right);

		Node<T> y = x.parent;

		// while x is it's parent's right child...
		while (!isNil(y) && x == y.right) {
			// Keep moving up in the tree
			x = y;
			y = y.parent;
		}
		// Return successor
		return y;
	}

	/**
	 * Removes the.
	 *
	 * @param v the v
	 */
	public void remove(Node<T> v) {

		Node<T> z = search(v.key);

		// Declare variables
		Node<T> x = nil;
		Node<T> y = nil;

		// if either one of z's children is nil, then we must remove z
		if (isNil(z.left) || isNil(z.right))
			y = z;

		// else we must remove the successor of z
		else
			y = treeSuccessor(z);

		// Let x be the left or right child of y (y can only have one child)
		if (!isNil(y.left))
			x = y.left;
		else
			x = y.right;

		// link x's parent to y's parent
		x.parent = y.parent;

		// If y's parent is nil, then x is the root
		if (isNil(y.parent))
			root = x;

		// else if y is a left child, set x to be y's left sibling
		else if (!isNil(y.parent.left) && y.parent.left == y)
			y.parent.left = x;

		// else if y is a right child, set x to be y's right sibling
		else if (!isNil(y.parent.right) && y.parent.right == y)
			y.parent.right = x;

		// if y != z, trasfer y's satellite data into z.
		if (y != z) {
			z.key = y.key;
		}

		// Update the numLeft and numRight numbers which might need
		// updating due to the deletion of z.key.
		fixNodeData(x, y);

		// If y's color is black, it is a violation of the
		// RedBlackTree properties so call removeFixup()
		if (y.color == Node.BLACK)
			removeFixup(x);
	}

	/**
	 * Fix node data.
	 *
	 * @param x the x
	 * @param y the y
	 */
	private void fixNodeData(Node<T> x, Node<T> y) {

		// Initialize two variables which will help us traverse the tree
		Node<T> current = nil;
		Node<T> track = nil;

		// if x is nil, then we will start updating at y.parent
		// Set track to y, y.parent's child
		if (isNil(x)) {
			current = y.parent;
			track = y;
		}

		// if x is not nil, then we start updating at x.parent
		// Set track to x, x.parent's child
		else {
			current = x.parent;
			track = x;
		}

		// while we haven't reached the root
		while (!isNil(current)) {
			// if the node we deleted has a different key than
			// the current node
			if (y.key != current.key) {

				// if the node we deleted is greater than
				// current.key then decrement current.numRight
				if (y.key.compareTo(current.key) > 0)
					current.numRight--;

				// if the node we deleted is less than
				// current.key thendecrement current.numLeft
				if (y.key.compareTo(current.key) < 0)
					current.numLeft--;
			}

			// if the node we deleted has the same key as the
			// current node we are checking
			else {
				// the cases where the current node has any nil
				// children and update appropriately
				if (isNil(current.left))
					current.numLeft--;
				else if (isNil(current.right))
					current.numRight--;

				// the cases where current has two children and
				// we must determine whether track is it's left
				// or right child and update appropriately
				else if (track == current.right)
					current.numRight--;
				else if (track == current.left)
					current.numLeft--;
			}

			// update track and current
			track = current;
			current = current.parent;

		}

	}

	/**
	 * Removes the fixup.
	 *
	 * @param x the x
	 */
	private void removeFixup(Node<T> x) {

		Node<T> w;

		// While we haven't fixed the tree completely...
		while (x != root && x.color == Node.BLACK) {

			// if x is it's parent's left child
			if (x == x.parent.left) {

				// set w = x's sibling
				w = x.parent.right;

				// Case 1, w's color is red.
				if (w.color == Node.RED) {
					w.color = Node.BLACK;
					x.parent.color = Node.RED;
					leftRotate(x.parent);
					w = x.parent.right;
				}

				// Case 2, both of w's children are black
				if (w.left.color == Node.BLACK && w.right.color == Node.BLACK) {
					w.color = Node.RED;
					x = x.parent;
				}
				// Case 3 / Case 4
				else {
					// Case 3, w's right child is black
					if (w.right.color == Node.BLACK) {
						w.left.color = Node.BLACK;
						w.color = Node.RED;
						rightRotate(w);
						w = x.parent.right;
					}
					// Case 4, w = black, w.right = red
					w.color = x.parent.color;
					x.parent.color = Node.BLACK;
					w.right.color = Node.BLACK;
					leftRotate(x.parent);
					x = root;
				}
			}
			// if x is it's parent's right child
			else {

				// set w to x's sibling
				w = x.parent.left;

				// Case 1, w's color is red
				if (w.color == Node.RED) {
					w.color = Node.BLACK;
					x.parent.color = Node.RED;
					rightRotate(x.parent);
					w = x.parent.left;
				}

				// Case 2, both of w's children are black
				if (w.right.color == Node.BLACK && w.left.color == Node.BLACK) {
					w.color = Node.RED;
					x = x.parent;
				}

				// Case 3 / Case 4
				else {
					// Case 3, w's left child is black
					if (w.left.color == Node.BLACK) {
						w.right.color = Node.BLACK;
						w.color = Node.RED;
						leftRotate(w);
						w = x.parent.left;
					}

					// Case 4, w = black, and w.left = red
					w.color = x.parent.color;
					x.parent.color = Node.BLACK;
					w.left.color = Node.BLACK;
					rightRotate(x.parent);
					x = root;
				}
			}
		} // end while

		// set x to black to ensure there is no violation of
		// RedBlack tree Properties
		x.color = Node.BLACK;
	}

	/**
	 * Search.
	 *
	 * @param key the key
	 * @return the node
	 */
	public Node<T> search(T key) {

		// Initialize a pointer to the root to traverse the tree
		Node<T> current = root;

		// While we haven't reached the end of the tree
		while (!isNil(current)) {

			// If we have found a node with a key equal to key
			if (current.key.equals(key))

				// return that node and exit search(int)
				return current;

			// go left or right based on value of current and key
			else if (current.key.compareTo(key) < 0)
				current = current.right;

			// go left or right based on value of current and key
			else
				current = current.left;
		}

		// we have not found a node whose key is "key"
		return null;

	}

	/**
	 * Checks if is nil.
	 *
	 * @param node the node
	 * @return true, if is nil
	 */
	private boolean isNil(Node node) {
		return node == nil;
	}

}