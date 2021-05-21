package main.assignment1.impl;

import main.assignment1.MyList;

// This class has been copied from an assignment done for the 1DV507 Java course
public class MyArrayList implements MyList 
{	
	Object[] array;
	int size;

	public MyArrayList() 
	{
		size = 0;
		array = new Object[8];
	}

	@Override
	public Object get(int index)
	{
		if (index < size)
		{
			return array[index];
		}
		else
		{
			throw new IndexOutOfBoundsException();
		}
	}

	@Override
	public int size() 
	{
		return size;
	}

	public void add(Object o) 
	{
		if (size >= array.length)
		{
			resise();
		}
		array[size] = o;
		size ++;
	}


	private void resise()
	{
		Object[] newArray = new Object[array.length*2];
		for (int i = 0; i<array.length; i++)
		{
			newArray[i] = array[i];
		}
		array = newArray;
	}
}
