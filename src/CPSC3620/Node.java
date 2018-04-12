/*
 * Copyright (c) 2018 by Jordan Pickett and Nora White. All rights reserved.
 *
 * Apr 10, 2018
 * 
 */
package CPSC3620;

/**
 * The Class Node.
 *
 * @author Jordan Pickett and Nora White
 * @param <T> the generic type
 */
public class Node<T extends Comparable<T>> {

	public static final int BLACK = 0;
	public static final int RED = 1;
	public T key;

	Node<T> parent;
	Node<T> left;
	Node<T> right;
	public int numLeft = 0;
	public int numRight = 0;
	public int color;

	/**
	 * Instantiates a new node with default values.
	 */
	Node() {
		color = BLACK;
		numLeft = 0;
		numRight = 0;
		parent = null;
		left = null;
		right = null;
	}

	/**
	 * Instantiates a new node with a key.
	 *
	 * @param key the key
	 */
	// Constructor which sets key to the argument.
	Node(T key) {
		this();
		this.key = key;
	}
}
