package test.assignment2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import main.assignment2.ArrayWithPublishedSize;
import main.assignment2.MyMap;
import main.assignment2.impl.MyHashTableImpl;

class TestMyHashTableImpl 
{

    private static final double MAX_LOAD_FACTOR = 0.75;

    private final String ADT = "ADT";
    private final String ADT_MEANING = "Abstract Data Type";

    @Test
    void TestInsertDictionary() 
    {
    	MyMap<String, String> dictionary = new MyHashTableImpl<String, String>(MAX_LOAD_FACTOR);

    	dictionary.insert(ADT, ADT_MEANING);
    	Assertions.assertNotNull(dictionary.contains(ADT));
    	Assertions.assertEquals(ADT_MEANING, dictionary.contains(ADT));

    }

    @Test
    void TestRemoveDictionary() 
    {
    	MyMap<String, String> dictionary = new MyHashTableImpl<String, String>(MAX_LOAD_FACTOR);

    	dictionary.insert(ADT, ADT_MEANING);
    	Assertions.assertNotNull(dictionary.contains(ADT));
    	dictionary.delete(ADT);
    	Assertions.assertNull(dictionary.contains(ADT));
    }

    @Test
    void TestSizeTable() 
    {
    	MyMap<String, String> dictionary = new MyHashTableImpl<String, String>(MAX_LOAD_FACTOR);

    	Assertions.assertTrue(((ArrayWithPublishedSize) dictionary).getLengthOfArray() > 0);	

    }
}
