package test.assignment1;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;

import main.assignment1.Couple;
import main.assignment1.MyAVL4Strings;
import main.assignment1.MyList;
import main.assignment1.impl.MyAVL4StringsImpl;

public class TestMyAVL4Strings {

    @Test
    public void TestLevelsOfSingleElement() {

	MyAVL4Strings tree = new MyAVL4StringsImpl();

	tree.insert("Alice");
	MyList<MyList<String>> byLevels = tree.LevelByLevelLists();

	Assertions.assertTrue(byLevels.get(0).get(0).equals("Alice"));
    }
  
    @Test
    public void TestPartialSearch() {

	MyAVL4Strings tree = new MyAVL4StringsImpl();
	// Be careful, this tree contains the same element but it does not have the same
	// structure as the tree in the figure!
	tree.insert("Alice");
	tree.insert("Bob");
	tree.insert("Jon");
	tree.insert("Jonatan");
	tree.insert("Jonna");
	tree.insert("Jones");
	tree.insert("Jonny");
	tree.insert("Jonty");
	tree.insert("Xabier");

	Couple<String> duple = tree.partialSearch("Jonn");

	Assertions.assertTrue(duple.getFirst().equals("Jonna"));
	Assertions.assertTrue(duple.getLast().equals("Jonny"));
    }
    
}
