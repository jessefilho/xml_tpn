package SAX;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
import java.io.*;
public class CounterElement {

	int counterElement = 0;
	public static String getElement;
	public static void main(String args[])throws IOException {
		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		String xmlFile = "dvd.xml";
		File file = new File(xmlFile);
		if (file.exists()){
			System.out.print("Enter with an Element name to count it:");
			getElement = bf.readLine();
			CounterElement tagCount = new CounterElement(xmlFile);
			
		}
		else{
			System.out.println("Opss.. File not found!");
		}
	}
	public CounterElement(String str){
		try{
			SAXParserFactory parserFact = SAXParserFactory.newInstance();
			SAXParser parser = parserFact.newSAXParser();
			DefaultHandler dHandler = new DefaultHandler(){
				public void startElement(String uri, String name, String element,
						Attributes atri)throws SAXException{
					if (element.equals(getElement)){
						counterElement++;
					}
				}
				public void endDocument(){
					System.out.println("Total elements: " + counterElement);
				}
			};
			parser.parse(str,dHandler);
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}


