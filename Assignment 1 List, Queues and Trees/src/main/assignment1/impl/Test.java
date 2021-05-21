package main.assignment1.impl;


import main.assignment1.MyAVL4Strings;
import main.assignment1.MyList;

public class Test {

	public static void main(String args[])
	{
		MyListImpl<String> tree = new MyListImpl<String>();
		MyAVL4StringsImpl imp = new MyAVL4StringsImpl();

		imp.insert("Jonna");
		imp.insert("Jon");
		imp.insert("Jonny");
		imp.insert("Alice");
		imp.insert("Jonathan");
		imp.insert("Jones");
		imp.insert("Jonty");
		imp.insert("Bob");
		imp.insert("Xavier");

		System.out.println("This prints out avl4strings tree: ");
		imp.printTree();
		System.out.println();
		System.out.println("Size: " + imp.size());
		
		System.out.println("Partial search: " + imp.partialSearch("Jonn"));
		
		System.out.println("---------------------------------------------------------------------------------------------------------------");
		System.out.println("Tree size: " + tree.size());
		tree.add("A");
		tree.add("B");
		tree.add("C");
		tree.add("D");
		tree.add("E");
		tree.add("F");
		tree.add("G");
		tree.add("H");
		tree.add("I");
		tree.add("J");
		tree.add("K");

		System.out.println("Getting 10th element: " + tree.get(10));
		System.out.println("Size of tree after insertion: " + tree.size());
		System.out.println("Getting 2nd element: " + tree.get(2));
		System.out.println("Getting 9th element: " + tree.get(9));
		
		MyAVL4StringsImpl avltree = new MyAVL4StringsImpl();

		avltree.insert("Xavier");
		avltree.insert("Bob");
		avltree.insert("Jonty");
		avltree.insert("Jones");
		avltree.insert("Jonathan");
		avltree.insert("Alice");
		avltree.insert("Jonny");
		avltree.insert("Jon");
		avltree.insert("Jonna");


		MyList<MyList<String>> byLevels = avltree.LevelByLevelLists();

		System.out.println(byLevels.get(0));
	}
}
