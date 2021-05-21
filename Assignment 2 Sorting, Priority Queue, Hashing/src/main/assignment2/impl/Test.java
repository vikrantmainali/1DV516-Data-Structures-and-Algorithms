package main.assignment2.impl;


import java.util.Arrays;

import org.junit.jupiter.api.Assertions;

import main.assignment2.ArrayMath;
import main.assignment2.MyMap;

public class Test {

	public static void main(String[] args) {

		int[] array1 = { 10, 1, 7, 10 };
		int[] array2 = { 1, 10, 7, 10 };
		ArrayMath mymath = new MyArrayMathImpl();
		System.out.println(mymath.isSameCollection(array1, array2));
		
		
		int[] array3 = { 2, 5, 3, 9 };
		int[] array4 = { 15, 12, 1, 3 };
		System.out.println(mymath.minDifferences(array3, array4));
		
		int[] array5 = {10,1,7,9};
		int[] array6 = {1,10,7,10};
		System.out.println(mymath.isSameCollection(array5,array6));
		
		
		int[] array = { 20000, 160, -2, 4, 100, 6, 120, 8, 140, 1800 };
		// int[] solution = [-2];
		System.out.println(Arrays.toString(mymath.getPercentileRange(array, 0, 10)));
		// int[] solution = [4];
		System.out.println(Arrays.toString(mymath.getPercentileRange(array, 10, 20)));
		// int[] solution = [ 4, 6, 8, 100 ];
		System.out.println(Arrays.toString(mymath.getPercentileRange(array, 10, 50)));
		// int[] solution = [140];
		System.out.println(Arrays.toString(mymath.getPercentileRange(array, 60, 70)));
		// int[] solution = [-2, 4, 6, 8, 100, 120, 140, 160, 1800, 20000];
		System.out.println(Arrays.toString(mymath.getPercentileRange(array, 0, 100)));
		
		/* wrong values
		 * 
		 * System.out.println(Arrays.toString(mymath.getPercentileRange(array, 40, 10)));
		 * 
		*/
		
// ---------------------------------------------HASHTABLE-------------------------------------------------------------

	    final double MAX_LOAD_FACTOR = 0.75;
	    final String ADT = "ADT";
	    final String ADT_MEANING = "Abstract Data Type";
		
		
		MyHashTableImpl<String,String> test = new MyHashTableImpl<String,String>(MAX_LOAD_FACTOR);
		System.out.println("Length of Array: " + test.getLengthOfArray());
		test.insert(ADT, ADT_MEANING);
		test.insert("Cat", "Animal");
		System.out.println(test);
		System.out.println(("test before delete: " + test.contains(ADT)));
		test.delete(ADT);
		System.out.println(test);
		System.out.println(("test after delete: " + test.contains(ADT)));
		test.insert("Dog", "Animal");
		test.insert("Apple", "Fruit");
		test.insert("Banana", "Fruit");
		test.insert("BMW", "Car");
		test.insert("Volvo", "Car");
		test.insert("Audi", "Car");
		test.insert("Volvo", "Swedish Car");
		System.out.println(test.getLengthOfArray());
		System.out.println(test);

		
	}

}
