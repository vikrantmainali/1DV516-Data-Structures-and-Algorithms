package main.assignment2.impl;

import main.assignment2.ArrayMath;

public class MyArrayMathImpl implements ArrayMath 
{

	@Override
	public boolean isSameCollection(int[] array1, int[] array2) 
	{
		if (array1.length != array2.length) 
		{
			return false;
		} 
		else
		{
			array1 = mergeSort(array1);
			array2 = mergeSort(array2);

			for (int i = 0; i < array1.length; i++) 
			{
				if (array1[i] != array2[i])
				{
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public int minDifferences(int[] array1, int[] array2)
	{
		if (array1.length != array2.length)
		{
			throw new ArithmeticException("The size of the two arrays is different");
		}
		array1 = mergeSort(array1);
		array2 = mergeSort(array2);
		int minDiff = 0;

		for (int i = 0; i < array1.length; i++)
		{
			minDiff += Math.pow((array2[i] - array1[i]), 2);
		}
		return minDiff;
	}

	@Override
	public int[] getPercentileRange(int[] arr, int lower, int upper)
	{
		if (lower > upper) 
		{
			throw new ArithmeticException("Lower bound is greater than upper bound");
		}
		arr = mergeSort(arr);

		int percentileRangeArray[] = new int[arr.length * (upper - lower) / 100];
		int counter = 0;

		for (int i = arr.length * lower / 100; i < arr.length * upper / 100; i++) 
		{
			percentileRangeArray[counter] = arr[i];
			counter++;
		}

		return percentileRangeArray;
	}

	// ------------------------------------------Sorting
	// algorithm-------------------------------------------
	public static int[] mergeSort(int[] in)
	/*
	 * method for merge sort of integer array created with the help of
	 * https://s3.amazonaws.com/ka-cs-algorithms/merge_sort_recursion.png
	 * (Previously used this sorting algorithm for the 1DV507 course assignment
	 */

	{
		int[] sortedArr = in.clone(); // making a separate array with the same content so that original array isn't
										// changed
		int[] left; // to store after breaking down the array
		int[] right; // to store after breaking down the array
		int first = 0, second = 0;

		if (in.length > 1) // since arrays with only one integer doesn't need to be sorted at all
		{
			left = new int[in.length / 2]; // the left integer array is the original with half the length
			right = new int[in.length - left.length];
			// the right integer array is the original minus the already stored left array

			System.arraycopy(in, 0, left, 0, left.length); // copying half the initial array to left array
			System.arraycopy(in, left.length, right, 0, right.length); // copying the rest to the right array

			left = mergeSort(left); // recursion is performed on the left array
			right = mergeSort(right); // recursion is performed on the right array

			for (int i = 0; i < in.length; i++)
			// sorting the left and right arrays first and then merge the two sorted arrays
			// to get the sorted array

			{
				if (second >= right.length || (first < left.length && left[first] <= right[second])) {
					sortedArr[i] = left[first];
					first++;
				} else {
					sortedArr[i] = right[second];
					second++;
				}
			}
		}
		return sortedArr; // return the sorted array
	}
}
