package order.loader.impl;

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

import order.OrderFactory;
import order.interfaces.Order;
import order.loader.interfaces.OrderLoader;

public class OrderXmlLoader implements OrderLoader {
	
	public ArrayList<Order> loadOrders(String inputFile) {
		ArrayList<Order> orderList = new ArrayList<>(); 
		
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
			
			NodeList orderEntries = doc.getDocumentElement().getChildNodes();
			
			for (int i = 0; i < orderEntries.getLength(); i++) {
				if (orderEntries.item(i).getNodeType() == Node.TEXT_NODE){
					continue;
				}
				
				String entryName = orderEntries.item(i).getNodeName();
				if (!entryName.equals("Order")) {
					System.err.println("Unexpected node: " + entryName);
				}
				
				//Get named nodes
				Element elem = (Element) orderEntries.item(i);
				String orderId = elem.getElementsByTagName("Id").item(0).getTextContent();
				int startDay = Integer.parseInt(elem.getElementsByTagName("StartDay").item(0).getTextContent());
				String destCity = elem.getElementsByTagName("City").item(0).getTextContent();
				String destState = elem.getElementsByTagName("State").item(0).getTextContent();
				
				
				//Get all subnodes called Item
				HashMap<String, Integer> items = new HashMap<>();
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
					items.put(itemName, itemQuantity);
				
				// Create an Item object loaded from the XML
				orderList.add( OrderFactory.createOrder("Regular",orderId, startDay, destCity, destState, items));
				}
			}
		}
		catch (ParserConfigurationException | SAXException | IOException | DOMException e) {
			e.printStackTrace();
		}
		
		return orderList;
	}
}
