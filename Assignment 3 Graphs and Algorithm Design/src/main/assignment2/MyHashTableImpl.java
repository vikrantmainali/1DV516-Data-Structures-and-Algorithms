package main.assignment2;

import java.lang.reflect.Array;

public class MyHashTableImpl<K, V> implements MyMap<K, V>, ArrayWithPublishedSize 
{
	private MapEntryImpl<K, V>[] array;

	private double MAXIMUM_ALLOWED_LOAD_FACTOR;

	private int DEFAULT_CAPACITY = 7;

	private int size = 0;

	public MyHashTableImpl(double MAX_LOAD_FACTOR) 
	{
		MAXIMUM_ALLOWED_LOAD_FACTOR = MAX_LOAD_FACTOR;

		this.array = (MapEntryImpl<K, V>[]) Array.newInstance(MapEntryImpl.class, DEFAULT_CAPACITY);
	}

	public int getLengthOfArray() 
	{
		return this.array.length;
	}

	public void insert(K key, V value) 
	{
		checkKey(key);
		checkValue(value);

		if (this.size / this.array.length >= this.MAXIMUM_ALLOWED_LOAD_FACTOR) 
		{
			rehash();
		}

		int hash = hash(key);
		while (array[hash] != null && array[hash].getKey() != key) 
		{
			hash = (hash + 1) % DEFAULT_CAPACITY;
		}
		array[hash] = new MapEntryImpl<K, V>(key, value);
		this.size++;

	}

	public void delete(K key)
	{
		checkKey(key);

		int hash = (hash(key) % DEFAULT_CAPACITY);

		while (array[hash] != null && array[hash].getKey() != (key))
		{
			hash = (hash + 1) % DEFAULT_CAPACITY;
		}
		if (array[hash] == null) 
		{
			throw new NullPointerException();
		}

		else 
		{
			array[hash].setKey(null);
			array[hash].setValue(null);
		}
	}

	public V contains(K key) 
	{
		if (key == null)
		{
			return null;
		}

		int hash = (hash(key) % DEFAULT_CAPACITY);

		while (array[hash] != null && array[hash].getKey() != (key))
		{
			hash = (hash + 1) % DEFAULT_CAPACITY;
		}
		if (array[hash] == null) 
		{
			return null;
		}

		else 
		{
			return array[hash].getValue();
		}
	}

	private int hash(K key) 
	{
		int hash = Math.abs(key.hashCode()) % this.array.length;
		if (hash < 0)
		{
			hash += this.array.length;
		}
		return hash;
	}

	private void rehash() 
	{
		MapEntryImpl<K, V>[] newArray = (MapEntryImpl<K, V>[]) Array.newInstance(MapEntryImpl.class,
				DEFAULT_CAPACITY * 2);

		size = 0;

		for (int i = 0; i < array.length; i++)
		{
			newArray[i] = this.array[i];
		}
		 this.array = newArray;

	}

	private void checkKey(K key) 
	{
		if (key == null) 
		{
			throw new NullPointerException();
		}
	}

	private void checkValue(V value) 
	{
		if (value == null) 
		{
			throw new NullPointerException();
		}
	}

	@Override
	public String toString() 
	{
		String hashtable = "";
		for (MapEntryImpl<K, V> bucket : this.array) 
		{
			if (bucket != null) 
			{
				hashtable = hashtable + bucket.getKey() + " " + bucket.getValue() + "\n";
			} 
			else 
			{
				hashtable = hashtable + "null " + "null" + "\n";
			}
		}
		return hashtable;
	}
}
