package main.assignment3.impl;

import java.util.ArrayList;
import main.assignment1.MyList;
import main.assignment1.MyListImpl;
import main.assignment2.MyHashTableImpl;
import main.assignment3.Neighborhood;

public class MyNeighborhoodImpl<AnyType> implements Neighborhood<AnyType> 
{

	int numberOfNeighbors = 0;

	ArrayList<Neighbor> neighbors = new ArrayList<Neighbor>(); // storing neighbours

	ArrayList<AllNeighbors> neighborCombinations = new ArrayList<AllNeighbors>(); // storing combination of all neighbors

	// class for Neighbor
	class Neighbor 
	{
		int chatterTime;
		int candyAmount;
		int number;
		AnyType value;
		int efficiency;

		MyHashTableImpl<Integer, Integer> neighborsDistances = new MyHashTableImpl<Integer, Integer>(0.75);

		public Neighbor(int number, AnyType value, int chatterTime, int candyAmount)
		{
			this.value = value;
			this.chatterTime = chatterTime;
			this.candyAmount = candyAmount;
			this.number = number;
			this.efficiency = this.candyAmount / this.chatterTime;
		}
	}

	// inner class for storing all possible combinations of neighbors
	class AllNeighbors 
	{
		ArrayList<Neighbor> members = new ArrayList<Neighbor>();
		int totalValue;
		int totalTime;

		public AllNeighbors(ArrayList<Neighbor> members) 
		{
			this.members = members;
			this.totalValue = totalValue(members);
			this.totalTime = totalTime(members);
		}

		private int totalValue(ArrayList<Neighbor> members)
		{
			int sum = 0;
			for (Neighbor n : members) 
			{
				sum = sum + n.efficiency;
			}
			return sum;
		}

		private int totalTime(ArrayList<Neighbor> members) 
		{
			int sum = 0;
			for (Neighbor n : members)
			{
				sum = sum + n.chatterTime;
			}
			return sum;
		}

		public String toString() 
		{
			String string = "";
			for (Neighbor n : members)
			{
				string = string + n.value + " ";
			}
			return string;
		}

	}

	private void createCombinations(int arr[], int data[], int start, int end, int index, int j) 
	{
		// Creating list of unique combinations
		if (index == j)
		{
			ArrayList<Neighbor> combinations = new ArrayList<Neighbor>();
			for (int i = 0; i < j; i++) 
			{
				combinations.add(neighbors.get(data[i]));
			}
			AllNeighbors allNeighborCombinations = new AllNeighbors(combinations);
			neighborCombinations.add(allNeighborCombinations);
		}
		for (int i = start; i <= end && end - i + 1 >= j - index; i++)
		{
			data[index] = arr[i];
			createCombinations(arr, data, i + 1, end, index + 1, j);
		}
	}

	@Override
	public void addVertex(AnyType neighbor, int chatterTime, int candyAmount) 
	{
		neighbors.add(new Neighbor(numberOfNeighbors, neighbor, chatterTime, candyAmount));
		numberOfNeighbors++;
		nullifyUnknownDistances();
	}

	private Neighbor findNeigbor(AnyType value) 
	{
		for (Neighbor n : neighbors) 
		{
			if (n.value.equals(value)) 
			{
				return n;
			}
		}
		return null;
	}

	@Override
	public void addEdge(AnyType fromNeighbor, AnyType toNeighbor, int distance)
	{
		Neighbor source = findNeigbor(fromNeighbor);
		Neighbor destination = findNeigbor(toNeighbor);

		if (source == null || destination == null) 
		{
			throw new NullPointerException();
		}

		source.neighborsDistances.insert(destination.number, distance);
		destination.neighborsDistances.insert(source.number, distance);

		nullifyUnknownDistances();

		for (Neighbor n : neighbors) 
		{
			ArrayList<Neighbor> neighborList = (ArrayList<Neighbor>) neighbors.clone();
			while (neighborList.size() != 0) 
			{
				Neighbor currentNeighbor = shortestDistance(n, neighborList);

				int distToCurrentNeighbor = n.neighborsDistances.contains(currentNeighbor.number);
				neighborList.remove(currentNeighbor);
				if (distToCurrentNeighbor == Integer.MAX_VALUE) 
				{
					continue;
				}
				for (int i = 0; i < numberOfNeighbors; i++)
				{ 
					int distFromCurrentNeighbor = currentNeighbor.neighborsDistances.contains(i);
					if (distFromCurrentNeighbor < Integer.MAX_VALUE
							&& distToCurrentNeighbor + distFromCurrentNeighbor < n.neighborsDistances.contains(i)) 
					{
						n.neighborsDistances.insert(i, distToCurrentNeighbor + distFromCurrentNeighbor);
					}
				}
			}
		}
	}

	private void nullifyUnknownDistances() 
	{
		int maxValue = Integer.MAX_VALUE;

		for (Neighbor n : neighbors) 
		{
			for (int i = 0; i < numberOfNeighbors; i++) 
			{
				if (n.neighborsDistances.contains(i) == null && n.number != i) 
				{
					n.neighborsDistances.insert(i, maxValue);
				} 
				else if (n.neighborsDistances.contains(i) == null && n.number == i) 
				{
					n.neighborsDistances.insert(i, 0);
				}
			}
		}
	}

	public String toString() 
	{
		String string = "     ";
		for (Neighbor n : neighbors)
		{
			string = string + n.value + " ";
		}
		for (Neighbor n : neighbors) 
		{
			string = string + "\n " + n.value + " : ";
			for (int i = 0; i < numberOfNeighbors; i++)
			{
				string = string + n.neighborsDistances.contains(i) + " ";
			}
		}
		string = string + "\n";
		return string;
	}

	private Neighbor shortestDistance(Neighbor root, ArrayList<Neighbor> list) 
	// neighbor with shortest distance from root
	{

		Integer maxValue = Integer.MAX_VALUE;
		Neighbor shortestDistanceNeighbor = null;
		for (Neighbor n : list)
		{
			int distance = root.neighborsDistances.contains(n.number);
			if (distance <= maxValue) 
			{
				shortestDistanceNeighbor = n;
				maxValue = distance;
			}
		}
		return shortestDistanceNeighbor;
	}

	@Override
	public int approximateMinimumDistance() 
	{	
		int totalDistance = 0;

		ArrayList<Neighbor> neighborToVisit = (ArrayList<Neighbor>) neighbors.clone();

		Neighbor currentNeighbor = neighbors.get(0);
		while (neighborToVisit.size() > 0) 
		{
			neighborToVisit.remove(currentNeighbor);
			Neighbor nextNeighbor = shortestDistance(currentNeighbor, neighborToVisit);

			if (nextNeighbor != null)
			{
				totalDistance = totalDistance + currentNeighbor.neighborsDistances.contains(nextNeighbor.number);
				currentNeighbor = nextNeighbor;
			} 
			else 
			{
				int distanceBackToSource = currentNeighbor.neighborsDistances.contains(0);
				totalDistance = totalDistance + distanceBackToSource;
			}
		}
		return totalDistance;
	}

	@Override
	public MyList<AnyType> neighborsToVisit(int maximum_Time) 
	{
		createNeighborCombination(); 
		MyListImpl<AnyType> list = new MyListImpl<AnyType>();
		AllNeighbors bestCombination = null;
		int maxValue = 0;
		for (AllNeighbors n : neighborCombinations) 
		{
			if (n.totalValue > maxValue && n.totalTime <= maximum_Time)
			{
				bestCombination = n;
				maxValue = n.totalValue;
			}
		}
		System.out.println("best time: " + bestCombination.totalTime + " min");
		System.out.println("best value: " + bestCombination.totalValue + " candy per min");

		for (Neighbor n : bestCombination.members)
		{
			list.add(n.value);
		}

		return list;
	}

	private void createNeighborCombination() 
	{
		// Creating all possible neighbor combinations
		
		int[] indexOfNeighbor = new int[numberOfNeighbors];
		for (int i = 0; i < numberOfNeighbors; i++) 
		{
			indexOfNeighbor[i] = i;
		}

		int length = indexOfNeighbor.length;
		int[] data = new int[length];

		neighborCombinations.clear();

		for (int i = 1; i <= neighbors.size(); i++) 
		{
			createCombinations(indexOfNeighbor, data, 0, length - 1, 0, i);
		}
	}

}
