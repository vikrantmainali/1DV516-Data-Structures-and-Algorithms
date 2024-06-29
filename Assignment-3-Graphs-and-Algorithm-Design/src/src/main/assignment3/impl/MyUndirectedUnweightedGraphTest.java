package main.assignment3.impl;

import main.assignment3.UnweightedGraph;

public class MyUndirectedUnweightedGraphTest
{
    public static void main(String[] args)
    {
        // Exercise 1
        UnweightedGraph<String> graph = new MyUndirectedUnweightedGraphImpl<String>();

        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");

        graph.addEdge(new String("1"), new String("2"));
        graph.addEdge(new String("1"), new String("3"));
        graph.addEdge(new String("1"), new String("4"));
        graph.addEdge(new String("2"), new String("3"));
        graph.addEdge(new String("2"), new String("1"));
        // graph.addEdge(new String("4"), new String("5")); // To test if adding an edge between 4 and 5 will change the isConnected boolean to true or not

        System.out.println("Graph is connected?: " + graph.isConnected());
        System.out.println("Connected components: " + graph.connectedComponents());


        // Exercise 2
        UnweightedGraph<Integer> graph2 = new MyUndirectedUnweightedGraphImpl<Integer>();
        for (int i = 1; i <= 5; i++)
        {
            graph2.addVertex(i);
        }
        // Add edges for vertex 1
        graph2.addEdge(1, 2);
        graph2.addEdge(1, 3);
        graph2.addEdge(1, 4);
        graph2.addEdge(1, 5);

        // Add edges for vertex 2
        graph2.addEdge(2, 3);
        graph2.addEdge(2, 4);

        // Add edges for vertex 3
        graph2.addEdge(3, 4);
        graph2.addEdge(3, 5);

        System.out.println("Graph has Euler path?: " + graph2.hasEulerPath());
        System.out.println("Euler path: " + graph2.eulerPath());
    }
}
