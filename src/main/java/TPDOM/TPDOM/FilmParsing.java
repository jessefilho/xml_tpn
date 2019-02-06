package TPDOM.TPDOM;

import java.io.File;
import java.util.Scanner;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
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

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------- START ----------");
		userInteration(root);
		System.out.println("finish...");

	}
	private static void userInteration(Element root) {
		System.out.println(" ");
		System.out.println(" #*#*#*#*#*#*#*#*# Make a Choose #*#*#*#*#*#*#*#*#");
		System.out.println("Enter with your choose: ");
		System.out.println("1 - List of actors;");
		System.out.println("2 - Return dates;");
		System.out.println("3 - xPath;");
		System.out.println("99 - To exit;");

		Scanner scanner = new Scanner(System.in);

		switch (scanner.nextInt()) {
		case 1:
			;
			listActeurs(root);
			userInteration(root);
		case 2:
			datesRetour(root);
			userInteration(root);
		case 3:
			try {
				xPathFunction(root);
			} catch (XPathExpressionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			userInteration(root);

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
}
