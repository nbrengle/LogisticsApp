package facility.loader.impl;

import facility.FacilityFactory;
import facility.exceptions.NoSuchFacilityException;
import facility.interfaces.Facility;
import facility.loader.FacilityLoaderHelper;
import facility.loader.interfaces.FacilityLoader;
import item.ItemFactory;
import item.exceptions.NoSuchItemException;
import item.interfaces.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;




public class FacilityXmlLoader implements FacilityLoader {
	
	public ArrayList<Facility> loadFacilities (String inputFile){
		ArrayList<Facility> facilityList = new ArrayList<Facility>();
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			File xml = new File(inputFile);
			if (!xml.exists()) {
				System.err.println("XML File" + inputFile + "cannot be found");
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
				Element elem = (Element) facEntries.item(i).getAttributes();
				String facCity = elem.getElementsByTagName("City").item(0).getTextContent();
				String facState = elem.getElementsByTagName("State").item(0).getTextContent();
				int facItemsPerDay = Integer.parseInt(elem.getElementsByTagName("ItemsPerDay").item(0).getTextContent());
				double facCostPerDay = Double.parseDouble(elem.getElementsByTagName("CostPerDay").item(0).getTextContent());
				
				//Get all subnodes named Connecting Facility
				ArrayList<FacilityLoaderHelper> connections = new ArrayList<>();
				NodeList connectList = elem.getElementsByTagName("Connection");
				for (int j = 0; j < connectList.getLength(); j++) {
					if (connectList.item(j).getNodeType() == Node.TEXT_NODE) 
						continue;
					
					entryName = connectList.item(j).getNodeName();
					if  (!entryName.equals("Connection")) {
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
				NodeList itemList = elem.getElementsByTagName("Item");
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
				catch (NoSuchFacilityException e) {
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
