package main.assignment1;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

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

	@Override
	public Iterator<E> iterator() {
		return new MyListIterator<E>();
	}

	private void resize() 
	{
		int newSize = elements.length * 2;
		elements = Arrays.copyOf(elements, newSize);
	}
	public void remove(E obj)
	{
		for (int i = 0; i < this.size; i++) {
			if (this.elements[i].equals(obj)) {
				remove(i);
				return;
			}
		}
	}
	public void remove(int index)
	{
		if (index >= this.size() || index < 0)
		{
			throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
		}
		if (this.size() - 1 - index >= 0) {
			System.arraycopy(this.elements, index + 1, this.elements, index, this.size - 1 - index);
			size--;
		}
	}

	public int indexOf(E obj)
	{
		for (int i = 0; i < size; i++)
		{
			if (elements[i].equals(obj))
			{
				return i;
			}
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

	private class MyListIterator<E> implements Iterator<E> {
		private int currentIndex = 0;

		@Override
		public boolean hasNext() {
			return currentIndex < size;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return (E) elements[currentIndex++];
		}
	}
}
