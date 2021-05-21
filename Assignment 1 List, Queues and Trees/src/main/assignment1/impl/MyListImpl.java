package main.assignment1.impl;

import main.assignment1.MyList;
import java.util.Arrays;

public class MyListImpl<E> implements MyList<E>
{
	private E[] elements;
	private int size;
	public static final int INITIAL_CAPACITY = 10;
	
	public MyListImpl()
	{
		elements = (E[])new Object[INITIAL_CAPACITY];
		size = 0;
	}
	
	
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
		if(index < 0 || index >= size()) {
			int maxIndex = size-1;
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
	 
	 @Override
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
