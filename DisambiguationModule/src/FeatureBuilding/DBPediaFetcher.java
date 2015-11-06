package FeatureBuilding;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import DumpHandler.DBpediaType;
import DumpHandler.Dictionary;
import DumpHandler.ID;
import DumpHandler.Printer;
import DumpHandler.Tuple;

public class DBPediaFetcher {
	
	public DBPediaFetcher(HashMap<String, DBpediaType> dBpediaTypes) {
		super();
		DBpediaTypes = dBpediaTypes;
	}

	private HashMap <String,DBpediaType> DBpediaTypes;
	
	public void updateEntities (Dictionary anchorLinks,String DBPediaDumpPath) throws IOException
		{
			BufferedReader br = new BufferedReader(new FileReader(DBPediaDumpPath));
			String currentLine = null;
			DBpediaType dBpediaTypeObject = null;
			
			HashMap <String, ID> IDs = anchorLinks.getIDs();
			
			while ((currentLine = br.readLine()) != null) 
			{
				Tuple <String,String > tuple = fetchData (currentLine);
				String wikiID = tuple.getFirst();
				String fetchedType = tuple.getSecond();
				ID wikiEntity = null;
				
				if ((wikiEntity = IDs.get(wikiID)) != null)
				{
						//Printer.print("Updateing entity " + wikiID, "adding DBpedia type" + fetchedType);
					
					 	if (DBpediaTypes.containsKey(fetchedType))
				    	  dBpediaTypeObject = DBpediaTypes.get(fetchedType);	      
					 	else
				    	  dBpediaTypeObject = new DBpediaType(fetchedType);
					 
					 
						if (!wikiEntity.getDBpediaTypes().contains(dBpediaTypeObject))
						{
							wikiEntity.addDBpediaType(dBpediaTypeObject);
						}
					
					
				}
				
			}
		}
	
	private Tuple<String , String> fetchData (String currentLine)
	{
		// Create a Pattern object
		  String  entityID = null; 
		  String  dBpediaType = null;
		  int patternFound = 0;
		  String pattern = "([^\\/ #]+)>";
	      Pattern r = Pattern.compile(pattern);

	      // Now create matcher object.
	      Matcher m = r.matcher(currentLine);
	      
	      while (m.find( )) {
	    	  
	    	  patternFound++;
	    	  
	    	  if (patternFound == 1)
	    	  {
		    	 entityID = m.group(1);
		    	 
		    	 if (entityID.startsWith(" "))
		    		 entityID = entityID.substring(1);
		    	 entityID = entityID.replace(' ', '_');
		    	 entityID = entityID.toLowerCase();
	    	  }	 
	    	 
	    	  if (patternFound == 3)
	    	  {
		    	 dBpediaType =  m.group(1);
		    	 if (dBpediaType.startsWith(" "))
		    		 dBpediaType = entityID.substring(1);
		    	 dBpediaType = dBpediaType.replace(' ', '_');
		    	 dBpediaType = dBpediaType.toLowerCase();
	    	  }
	      } 
	      
	      
		return new Tuple <String,String> (entityID,dBpediaType);
	}
}
