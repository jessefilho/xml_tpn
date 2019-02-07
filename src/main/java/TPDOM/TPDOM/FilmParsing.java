package TPDOM.TPDOM;

import java.io.File;
import java.io.StringWriter;
import java.lang.invoke.ConstantCallSite;
import java.util.Scanner;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class FilmParsing {
	
	private static String filmTitle =  "The Lord of the Rings: The Return of the King"; //le titre du film loué
	private static String lastName = "Nascimento Filho"; //nom du locataire
	private static String firstName = "Jesse"; //prénom du locataire
	private static String address = "Street 54"; //adresse du locataire
	private static String date = "07/02/2019";//date de retour  
	public static void main(String[] args) throws Exception {
		File f = new File("dvd.xml");

		//recuperer une instance de factory qui se chargera  de fournir un parseur
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


		//créer le parseur à travers factory
		DocumentBuilder builder = factory.newDocumentBuilder();

		//parser le fichier .xml via l'objet builder et recuperer un objet document representant
		//la hierarchie d'objet crée  pendant le parsing
		Document document = builder.parse(f);

		

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------- START ----------");
		userInteration(document);
		System.out.println("finish...");

	}
	private static void userInteration(Document document) throws TransformerException {
		
		
		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		DOMSource source = new DOMSource(document);

		//StreamResult result =  new StreamResult(System.out.);
		//transformer.transform(source, result);

		//Manipulation des noueds
		Element root = document.getDocumentElement();
		
		System.out.println(" ");
		System.out.println(" #*#*#*#*#*#*#*#*# Make a Choose #*#*#*#*#*#*#*#*#");
		System.out.println("Enter with your choose: ");
		System.out.println("1 - List of actors;");
		System.out.println("2 - Return dates;");
		System.out.println("3 - xPath;");
		System.out.println("4 - Modify DVD`s Rent;");
		System.out.println("99 - To exit;");
		Scanner scanner = new Scanner(System.in);

		switch (scanner.nextInt()) {
		case 1:
			;
			listActeurs(root);
			userInteration(document);
		case 2:
			datesRetour(root);
			userInteration(document);
		case 3:
			try {
				xPathFunction(root);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userInteration(document);
		case 4:
			try {
				modficationRent(root,
						document,
						filmTitle,
						lastName,
						firstName,
						address,
						date);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userInteration(document);

		case 99:
			break;
		}
	}

	private static void listActeurs(Element root) {

		System.out.println("Answer of question (a): ");
		for (int k = 0; k < root.getElementsByTagName("DVD").getLength(); k++) {
			System.out.println(" ");
			System.out.println("DVD: " + root.getElementsByTagName("title").item(k).getTextContent());
			for (int i = 0; i < root.getElementsByTagName("film").item(k).getChildNodes().getLength(); i++) {
				if (i%2 != 0) {
					try {
						String nodeName = root.getElementsByTagName("film").item(k).getChildNodes().item(i).getNodeName();
						if (nodeName.equals("actor")) {
							System.out.println(nodeName);
							System.out.print(" "+ root.getElementsByTagName("film").item(k).getChildNodes().item(i).getChildNodes().item(1).getFirstChild().getNodeValue());
							System.out.print(" "+ root.getElementsByTagName("film").item(k).getChildNodes().item(i).getChildNodes().item(3).getFirstChild().getNodeValue());
							System.out.println();
						}

					} catch (NullPointerException e) {
						e.getStackTrace();
					}
				}

			}
		}

	}
	private static void datesRetour(Element root) {
		NodeList children = root.getChildNodes();
		System.out.println("Answer of question (b): ");

		for (int i = 1; i <= children.getLength(); i++) {
			System.out.println("");

			try {
				if(root.getElementsByTagName("rent").item(i).getAttributes().getNamedItem("date") != null) {
					System.out.println("DVD: " + root.getElementsByTagName("title").item(i).getTextContent());
					System.out.println("return at: " +root.getElementsByTagName("rent").item(i).getAttributes().getNamedItem("date"));
				}
			} catch (NullPointerException e) {
				e.getStackTrace();
			}

		}

	}
	private static void xPathFunction(Element root) throws XPathExpressionException{
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath path = xpathFactory.newXPath();

		String expressionTitles = "//film/title";
		String expressionRent = "//DVD";
		NodeList nlistOfTitles = null;
		NodeList nlistOfRents = null;
		
		try {
			//List of titles
			System.out.println("List of titles with xPath:");
			nlistOfTitles = (NodeList)path.evaluate(expressionTitles, root,XPathConstants.NODESET);
			for (int i = 0; i < nlistOfTitles.getLength(); i++) {
				System.out.println(" -> "+nlistOfTitles.item(i).getTextContent()+";");
			}
			System.out.println("-------------------------------------");
			//List of titles
			System.out.println("List of films rented:");
			nlistOfRents = (NodeList)path.evaluate(expressionRent, root,XPathConstants.NODESET);
			nlistOfTitles = (NodeList)path.evaluate(expressionTitles, root,XPathConstants.NODESET);
			for (int i = 0; i < nlistOfTitles.getLength(); i++) {			
				if(nlistOfRents.item(i).getChildNodes().item(3) != null) {
					System.out.println(" -> "+nlistOfTitles.item(i).getTextContent()+";");				
				}
			}
			System.out.println("-------------------------------------");
			//List of qty actors by film
			System.out.println("List of qty actors by film:");

			for (int i = 1; i <= nlistOfTitles.getLength(); i++) {
				XPathExpression expr1 = path.compile("//DVD["+i+"]/film/title/text()");
				String n1_movie = (String)expr1.evaluate(root,XPathConstants.STRING);
				System.out.print(n1_movie+ " has ");
				int count = 0;
				Node no = (Node)path.evaluate("//DVD["+i+"]/film", root,XPathConstants.NODE);

				for (int j = 1; j < no.getChildNodes().getLength(); j++) {
					if (j%2 != 0) {
						if (no.getChildNodes().item(j).getNodeName().equals("actor")) {
							count++;
						}
					}
				}
				System.out.println(count+ " actors.");

			}


		} catch (XPathExpressionException e) {	
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println(str);


	}
	
	private static void modficationRent(Element root,
			Document doc, //le DOM
			String filmTitle, //le titre du film loué
			String lastName, //nom du locataire
			String firstName, //prénom du locataire
			String address, //adresse du locataire
			String date//date de retour
			) throws XPathExpressionException{
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath path = xpathFactory.newXPath();
		String expressionDVDs = "//DVD";
		NodeList nlistOfDVD = (NodeList)path.evaluate(expressionDVDs, root,XPathConstants.NODESET);
			
		for (int i = 0; i < nlistOfDVD.getLength(); i++) {
			
			String title = nlistOfDVD.item(i).getChildNodes().item(1).getChildNodes().item(1).getChildNodes().item(0).getNodeValue();
			
			if (filmTitle.equals(title)) {
				System.out.println(title);
				try {
					Element rent = doc.createElement("rent");
					 
					Element person = doc.createElement("person");
					
					Text lastNameValue = doc.createTextNode(lastName); 
					Element lastNameEl = doc.createElement("lastName");
					
					Text firstNameValue = doc.createTextNode(firstName); 
					Element firstNameEl = doc.createElement("firstName");
					
					Text addressValue = doc.createTextNode(address); 
					Element addressEl = doc.createElement("address");
					
					lastNameEl.appendChild(lastNameValue);
					firstNameEl.appendChild(firstNameValue);
					addressEl.appendChild(addressValue);
					
					person.appendChild(lastNameEl);
					person.appendChild(firstNameEl);
					person.appendChild(addressEl);
					
					rent.setAttribute("date", date);
					rent.appendChild(person);
					
					nlistOfDVD.item(i).getChildNodes().item(3).getParentNode().insertBefore(rent, nlistOfDVD.item(i).getChildNodes().item(3));
					
				} catch (NullPointerException e) {
					e.getStackTrace();
				}
				
				
			}
		}
		
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");

		StreamResult result = new StreamResult(new StringWriter());
		DOMSource source = new DOMSource(doc);
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String xmlOutput = result.getWriter().toString();
		System.out.println(xmlOutput);
		
	}
}
