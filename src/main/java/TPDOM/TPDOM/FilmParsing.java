package TPDOM.TPDOM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class FilmParsing {
	public static void main(String[] args) throws Exception {
		File f = new File("dvd.xml");
		
		//recuperer une instance de factory qui se chargera  de fournir un parseur
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		
		
		//créer le parseur à travers factory
		DocumentBuilder builder = factory.newDocumentBuilder();
		
		//parser le fichier .xml via l'objet builder et recuperer un objet document representant
		//la hierarchie d'objet crée  pendant le parsing
		Document document = builder.parse(f);
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);
		
		StreamResult result =  new StreamResult(System.out);
		transformer.transform(source, result);
		
		//Manipulation des noueds
		Element root = document.getDocumentElement();
		
		System.out.println(root.getElementsByTagName("actor"));
		
		NodeList children = root.getChildNodes();
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------- QUESTION 2 ----------");
		
		
		 //listActeurs(children);
		datesRetour(children);
	
	}

	private static void listActeurs(NodeList children) {
		System.out.println("a)");
		for (int i = 1; i <= children.getLength(); i++) {
			//System.out.println(children.item(1).getChildNodes().item(1).getChildNodes().getLength());
			
			try {
				
			
			if (i%2 != 0) {
				System.out.println(children.item(i).getNodeName()+ " #"+i+" DVD");
				
				for (int j = 1; j <= children.item(i).getChildNodes().item(1).getChildNodes().getLength(); j++) {
					
					if (j%2 != 0) {
						
						try {
							String nodeName = children.item(i).getChildNodes().item(1).getChildNodes().item(j).getNodeName();
							if (nodeName.equals("actor")) {
								System.out.println(nodeName);
								System.out.print(" "+ children.item(i).getChildNodes().item(1).getChildNodes().item(j).getChildNodes().item(1).getFirstChild().getNodeValue());
								System.out.println(" "+ children.item(i).getChildNodes().item(1).getChildNodes().item(j).getChildNodes().item(3).getFirstChild().getNodeValue());
							}
							//DVD/film/actor
							//System.out.println(children.item(1).getChildNodes().item(1).getChildNodes().item(9));
						} catch (NullPointerException e) {
							// TODO: handle exception
							e.getStackTrace();
						}
					}
					//DVD/film/actor
					//System.out.println(children.item(1).getChildNodes().item(1).getChildNodes().item(9).getChildNodes().item(j).getFirstChild().getNodeValue());
				}
				
			}
			} catch (NullPointerException e) {
				e.getStackTrace();
			}
			
			
			
			
		}
	}
	private static void datesRetour(NodeList children) {
		System.out.println("b)");
		
		for (int i = 1; i <= children.getLength(); i++) {
			
			try {
				System.out.println(children.item(i).getChildNodes().item(3).getAttributes().getNamedItem("date"));
				
			} catch (NullPointerException e) {
				e.getStackTrace();
			}


			
		}
		
		
		
	}
}
