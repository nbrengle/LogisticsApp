package facility.loader.impl;

import facility.FacilityFactory;
import facility.exceptions.NoSuchFacilityException;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderHelper;
import facility.loader.interfaces.FacilityLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class FacilityXmlLoader implements FacilityLoader {
	
	public ArrayList<Facility> loadFacilities (String inputFile){
		ArrayList<Facility> facilityList = new ArrayList<Facility>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			File xml = new File(inputFile);
			if (!xml.exists()) {
				System.err.println("XML File " + inputFile + " cannot be found");
				System.exit(-1);
			}
			
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();
			
			NodeList facEntries = doc.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < facEntries.getLength(); i++) {
				if (facEntries.item(i).getNodeType() == Node.TEXT_NODE)
					continue;
				
				String entryName = facEntries.item(i).getNodeName();
				if (!entryName.equals("Facility")) {
                    System.err.println("Unexpected node found: " + entryName);
				}
				
				//City, State, ItemsPerDay, CostPerDay
				//Get a named node
				Element elem = (Element) facEntries.item(i);
				String facCity = elem.getElementsByTagName("City").item(0).getTextContent();
				String facState = elem.getElementsByTagName("State").item(0).getTextContent();
				int facItemsPerDay = Integer.parseInt(elem.getElementsByTagName("ItemsPerDay").item(0).getTextContent());
				double facCostPerDay = Double.parseDouble(elem.getElementsByTagName("CostPerDay").item(0).getTextContent());
				
				//Clone for Inventory Use down the line
				Element invElem = (Element) elem.cloneNode(true);
				
				//Get all subnodes named Connecting Facility
				ArrayList<FacilityLoaderHelper> connections = new ArrayList<>();
				NodeList connectList = elem.getElementsByTagName("ConnectingFacility");
				for (int j = 0; j < connectList.getLength(); j++) {
					if (connectList.item(j).getNodeType() == Node.TEXT_NODE) 
						continue;
					
					entryName = connectList.item(j).getNodeName();
					if  (!entryName.equals("ConnectingFacility")) {
						System.err.println("Unexpected node found: " + entryName);
					}
					
					//City, State, Distance
					//Get Named nodes
					elem = (Element) connectList.item(j);
					String connectCity  = elem.getElementsByTagName("City").item(0).getTextContent();
					String connectState = elem.getElementsByTagName("State").item(0).getTextContent();
					int connectDistance = Integer.parseInt( elem.getElementsByTagName("Distance").item(0).getTextContent());
					FacilityLoaderHelper helper = new FacilityLoaderHelper(connectCity, connectState, connectDistance);
					connections.add(helper);
				}
				
				//Get all subnodes called Item
				HashMap<String, Integer> inventory = new HashMap<>();
				NodeList itemList = invElem.getElementsByTagName("Item");
				for (int k = 0; k < itemList.getLength(); k++) {
					if (itemList.item(k).getNodeType() == Node.TEXT_NODE)
						continue;
					
					entryName = itemList.item(k).getNodeName();
					if (!entryName.equals("Item")) {
						System.err.println("Unexpected node found: " + entryName);
					}
					
					//Name, Quantity
					elem = (Element) itemList.item(k);
					String itemName = elem.getElementsByTagName("Name").item(0).getTextContent();
					int itemQuantity = Integer.parseInt( elem.getElementsByTagName("Quantity").item(0).getTextContent() );
					inventory.put(itemName, itemQuantity);
			}
				
				// Create an Item object loaded from the XML
				try {
					facilityList.add( FacilityFactory.createFacility("Regular", facCity, facState, 
										facItemsPerDay, facCostPerDay, connections, inventory) );
				}
				catch (NoSuchFacilityException | NullPointerException e) {
					e.printStackTrace();
				}
			
		} 
		
		} 
		catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
		
		return facilityList;
	}

}
