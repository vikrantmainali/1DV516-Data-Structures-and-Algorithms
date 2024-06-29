package main.assignment1;

import java.util.Iterator;

public interface MyList<E>  extends Iterable<E>
{
	public void add(E obj);
    public E get(int index); //returns the element in index position. The first element is in position 0.
    public int size(); //returns the number of elements in the list
    Iterator<E> iterator();
    void remove(E obj);
    void remove(int i);
}
