package item.loader.impl;

import item.ItemFactory;
import item.exceptions.NoSuchItemException;
import item.interfaces.Item;
import item.loader.interfaces.ItemLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ItemXmlLoader implements ItemLoader{

	public ArrayList<Item> loadItems(String inputFile) {
		ArrayList<Item> itemList = new ArrayList<Item>(); 
		
		try {
			String fileName = inputFile;
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			
			File xml = new File(fileName);
			if (!xml.exists()) {
				System.err.println("XML file " + fileName + " cannot be found");
				System.exit(-1);
			}
			
			Document doc = db.parse(xml);
			doc.getDocumentElement().normalize();
			
			NodeList itemEntries = doc.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < itemEntries.getLength(); i++) {
				if (itemEntries.item(i).getNodeType() == Node.TEXT_NODE){
					continue;
				}
				
				String entryName = itemEntries.item(i).getNodeName();
				if (!entryName.equals("Item")) {
					System.err.println("Unexpected node: " + entryName);
				}
				
				//Get named nodes
				Element elem = (Element) itemEntries.item(i);
				String itemName = elem.getElementsByTagName("Name").item(0).getTextContent();
				Double itemPrice = Double.parseDouble(elem.getElementsByTagName("Price").item(0).getTextContent());
				
				// Create an Item object loaded from the XML
				try {
					itemList.add( ItemFactory.createItem("Regular", itemName, itemPrice) );
				}
				catch (NoSuchItemException e) {
					e.printStackTrace();
				}
			}
		}
		catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
		
		return itemList;
	}
}
