package TPDOM.TPDOM;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class FilmParsingTest {
	private static String filmTitle =  "The Lord of the Rings: The Return of the King"; //le titre du film loué
	private static String lastName = "Nascimento Filho"; //nom du locataire
	private static String firstName = "Jesse"; //prénom du locataire
	private static String address = "Street 54"; //adresse du locataire
	private static String date = "07/02/2019";//date de retour 
	
	public static void main(String[] args) throws Exception {
		File f = new File("dvd.xml");
		FilmParsing fpg = new FilmParsing();
		//recuperer une instance de factory qui se chargera  de fournir un parseur
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();


		//créer le parseur à travers factory
		DocumentBuilder builder = factory.newDocumentBuilder();

		//parser le fichier .xml via l'objet builder et recuperer un objet document representant
		//la hierarchie d'objet crée  pendant le parsing
		Document document = builder.parse(f);
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("---------- START TEST ----------");
		fpg.userInterationTest(document);
		System.out.println("finish...");
	}

}
