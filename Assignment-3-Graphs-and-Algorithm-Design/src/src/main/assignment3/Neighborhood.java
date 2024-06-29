package main.assignment3;

import main.assignment1.MyList;

public interface Neighborhood<AnyType> 
{
    public void addVertex(AnyType neighbor, int chatterTime, int candyAmount);
    
    public void addEdge(AnyType fromNeighbor, AnyType toNeighbor, int distance);

    public int approximateMinimumDistance();

    public MyList<AnyType> neighborsToVisit(int maximum_Time);
}
