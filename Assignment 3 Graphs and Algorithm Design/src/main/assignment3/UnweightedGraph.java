package main.assignment3;

import main.assignment1.MyList;

public interface UnweightedGraph<AnyType> 
{
    public void addVertex(AnyType vertex);    

    public void addEdge(AnyType sourceVertex, AnyType targetVertex);
    
    public boolean isConnected();

    public MyList<MyList<AnyType>> connectedComponents();

    public boolean hasEulerPath();

    public MyList<AnyType> eulerPath();

   

}
