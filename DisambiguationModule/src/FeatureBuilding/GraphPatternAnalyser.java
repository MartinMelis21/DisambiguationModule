package FeatureBuilding;

import java.util.ArrayList;
import java.util.HashSet;

import DumpHandler.Anchor;
import DumpHandler.Dictionary;
import DumpHandler.ID;
import DumpHandler.Tuple;

public class GraphPatternAnalyser {

	
	public int getNumberOfOneHopTransferers (ID entity1, ID entity2)
	{
		int counter = 0;
	
		
		//get number of outgoingLinks from entity1
		HashSet <ID> list1 = entity1.getOutgoingIDs();
				
		//get number of ingoingLinks to entity 2
		HashSet <ID> list2 = entity2.getOutgoingIDs();
				
		//count overlappings between lists
				
		for (Object o : list1)
			{
			if (list2.contains(o))
				counter++;
			}
					
		return counter;
	}
	
	public int getNumberOfAdditionalDirectInterconnections (ID entity1, ID entity2)
	{
		int counter = 0;
	
		
		//get number of outgoingLinks from entity1
		HashSet<Tuple<Anchor, ID>> listTuples1 = entity1.getOutgoingTuples();
				
		//get number of ingoingLinks to entity 2
		HashSet<Tuple<Anchor, ID>> listTuples2 = entity2.getOutgoingTuples();
		
		
		for (Tuple<Anchor, ID> tuple : listTuples1)
		{
			if (tuple.getSecond() == entity2)			
				counter++;
		}
			
		for (Tuple<Anchor, ID> tuple : listTuples2)
		{
			if (tuple.getSecond() == entity1)
				counter++;
		}
		
		if (counter>1)
			counter--;
					
		return counter;
	}
		
	
	public int getNumberOfListeners (ID entity1, ID entity2)
	{
		int counter = 0;
		//get number of outgoing links from entity1
		HashSet <ID> list1 = entity1.getOutgoingIDs();
		
		//get number of outgoing links from entity2
		HashSet <ID> list2 = entity2.getOutgoingIDs();
		
		//count overlappings between lists
		
		for (Object o : list1)
		{
			if (list2.contains(o))
				counter++;
		}
		
		return counter;
	}
	
	public int getNumberOfSpokesmen (ID entity1, ID entity2)
	{

		int counter = 0;
		//get number of ingoing links to entity1
		HashSet <ID> list1 = entity1.getIngoingIDs();
		
		//get number of in links to entity2
		HashSet <ID> list2 = entity2.getIngoingIDs();
		
		//count overlappings between lists
		
		for (Object o : list1)
		{
			if (list2.contains(o))
				counter++;
		}
		
		return counter;
	}
}
