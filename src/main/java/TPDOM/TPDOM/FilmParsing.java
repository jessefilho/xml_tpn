package TPDOM.TPDOM;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
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

		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------- START ----------");
		System.out.println(root.getElementsByTagName("film").item(1).getChildNodes().getLength());
		userInteration(root);
		System.out.println("finish...");

	}
	private static void userInteration(Element root) {
		System.out.println(" ");
		System.out.println(" #*#*#*#*#*#*#*#*# Make a Choose #*#*#*#*#*#*#*#*#");
		System.out.println("Enter with your choose: ");
		System.out.println("1 - List of actors;");
		System.out.println("2 - Return dates;");
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
}
