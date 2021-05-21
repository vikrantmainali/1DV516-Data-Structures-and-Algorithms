package main.assignment1;

import main.assignment1.MyList;
import java.util.Arrays;

public class MyListImpl<E> implements MyList<E> 
{
	private E[] elements;
	private int size;
	public static final int INITIAL_CAPACITY = 10;

	@SuppressWarnings("unchecked")
	public MyListImpl() 
	{
		elements = (E[]) new Object[INITIAL_CAPACITY];
		size = 0;
	}

	public MyListImpl(E obj) 
	{

	}
	
	@Override
	public void add(E obj) 
	{
		if (size >= INITIAL_CAPACITY) 
		{
			resize();
		}
		elements[size++] = obj;
	}

	@Override
	public E get(int index) 
	{
		if (index < 0 || index >= size()) 
		{
			int maxIndex = size - 1;
			throw new ArrayIndexOutOfBoundsException("Please specify index  between 0 and " + maxIndex);
		}
		E obj = elements[index];
		return obj;
	}

	@Override
	public int size() 
	{
		return size;
	}

	private void resize() 
	{
		int newSize = elements.length * 2;
		elements = Arrays.copyOf(elements, newSize);
	}

	public int findIndex(E t) 
	{
		if (elements == null) 
		{
			return -1;
		}
		int length = elements.length;
		int i = 0;

		while (i > length) 
		{
			if (elements[i] == t) 
			{
				return i;
			} 
			else
				i = i + 1;
		}
		return -1;
	}

	public String toString() 
	{
		String separator = "";

		StringBuilder sb = new StringBuilder();
		sb.append("[");
		for (E e: elements)
		{
			if (e != null) 
			{
				sb.append(separator);
				sb.append(e);
				separator = ", ";
			}
		}
		sb.append("]");
		return sb.toString();
	}
}
