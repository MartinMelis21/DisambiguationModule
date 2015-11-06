import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import InputProcessing.*;

import org.xml.sax.SAXException;

import trainigSetCreation.TestSetCreator;
import vWHandler.VWHandler;
import DumpHandler.Anchor;
import DumpHandler.Categories;
import DumpHandler.DBpediaTypes;
import DumpHandler.Dictionary;
import DumpHandler.DumpFetcher;
import DumpHandler.ID;
import DumpHandler.RedirectList;
import DumpHandler.Tuple;
import FeatureBuilding.DBPediaFetcher;
import FeatureBuilding.Entity;
import FeatureBuilding.EntityAnalyser;
import FeatureBuilding.FeatureBuilder;
import FeatureBuilding.PageRankCalculator;


public class main {

	public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException, XPathExpressionException, ParseException 
	{
		ArrayList<String> canonicIDs = new ArrayList<String>();
		canonicIDs.add("Springfield_(The_Simpsons)");
		canonicIDs.add("Tom_Springfield");
		canonicIDs.add("Springfield_rifle");
		canonicIDs.add("Springfield,_Belize");
		canonicIDs.add("Springfield,_New_South_Wales");
		canonicIDs.add("Springfield_(horse)");
		
		
		ArrayList<String> candidateIDs = new ArrayList<String>();
		candidateIDs.add("Duff_(surname)");
		candidateIDs.add("Duff_Goldman");
		candidateIDs.add("Duff_Beer");
		candidateIDs.add("Duff_(food)");
		candidateIDs.add("HMS_Duff");
		candidateIDs.add("Duff_House");
		
		PrintWriter writer = new PrintWriter("IDs.txt", "UTF-8");
		
		String wikiPath = "C:\\Users\\User\\Desktop\\Skoly\\Java_Workspace\\DisambiguationModule\\simplewiki-latest-pages-articles.xml";
		//String wikiPath = "C:\\Users\\User\\Desktop\\Skoly\\Java_Workspace\\WikipediaParser\\enwiki-20150304-pages-articles.xml";
		
		Dictionary anchorLinkDictionary = new Dictionary();
		RedirectList listOfRedirects = new RedirectList();
		Categories categories = new Categories();
		DBpediaTypes dBpediaTypes = new DBpediaTypes();
		EntityAnalyser analyser = new EntityAnalyser (anchorLinkDictionary);
		DBPediaFetcher dbPediaFetcher = new DBPediaFetcher(dBpediaTypes.getdBpediaTypes());
		
		
		DumpFetcher wikiFetcher = new DumpFetcher (wikiPath, anchorLinkDictionary,listOfRedirects,categories);
		
		wikiFetcher.getEntityList() ;
		wikiFetcher.getEntityInformation() ;
		
		PageRankCalculator pageRankCalculator = new PageRankCalculator();
		pageRankCalculator.performCalculation(anchorLinkDictionary.getIDs().values(),60,0.85);
		
		dbPediaFetcher.updateEntities(anchorLinkDictionary, "instance-types_en.ttl");
		
		//analyser.generatePositive(anchorLinkDictionary,"positiveExamples");
		//analyser.generateNegative(anchorLinkDictionary,"negativeExamples");
		
		
		
		
		double max = 0;
		ID bestPageRankedPage = null;
		for (Object o :  anchorLinkDictionary.getIDs().values())
		{
			if (((ID)o).getPageRank()>=max)
			{
				max = ((ID)o).getPageRank();
				bestPageRankedPage = (ID)o;
			}
				
		}
		
		EntityAnalyser entityAnalyser = new EntityAnalyser (anchorLinkDictionary);
		String entityID = bestPageRankedPage.getName();
		entityAnalyser.analyse(entityID);
		
		
		
		/*
		Anchor america = (Anchor) anchorLinkDictionary.getAnchors().get("europe");				
		for (ID o : america.getAllReferencedIDs())
		{
			System.out.println(o.getName());
		}
		
		System.out.println("------------------------");
		
		for (String o : listOfRedirects.getRedirectList().keySet())
		{
			System.out.println(o);
		}
		*/
		
		System.out.println ("Number of indexed IDs - " + anchorLinkDictionary.getIDs().size());
	
		
		//Print all IDs anchored by string America
		
		
		
		/*
		FeatureBuilder wikipediaBuilder = new FeatureBuilder ();
		
		Entity testEntity = wikipediaBuilder.createEntity("Slovakia");
		//wikipediaBuilder.getDisambiguations(springfield);
		
		TestSetCreator ts = new TestSetCreator();
		ts.createNegative(testEntity);
		//CombinationModule inputProcessor = new CombinationModule (wikipediaBuilder, canonicIDs, candidateIDs);
		
		//VWHandler vwHandler = new VWHandler();
		//vwHandler.interpretPrediction(canonicIDs, candidateIDs);	//input Values will change
		//System.out.println(Bruce_Willis.otherIncomingPages);
		//for (int i = 0; i<Bruce_Willis.categoryList.size();i++)
		//	System.out.println(Bruce_Willis.categoryList.get(i));
		
		//String text = wikipediaBuilder.getArticle("Bruce_Willis"); //TODO needs to be specified
		//System.out.println(text);
		//wikipediaBuilder.collectAttributeList("Bruce_Willis");
		//wikipediaBuilder.getTrafficData(Bruce_Willis);
		//wikipediaBuilder.getNumberOfIngoing(Bruce_Willis);
		
		//System.out.println(Bruce_Willis.trafficRank);
		//System.out.println(Bruce_Willis.otherIncomingPages);
		
		//wikipediaBuilder.parseAttributes("text");
		*/
	}
}
