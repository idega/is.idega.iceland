package is.postur;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.idega.util.datastructures.HashMatrix;

public class Gotuskra {

	Document gotuskra;
	HashMatrix gotur;
	
	public static Gotuskra cached;
	
	public static Gotuskra getCached(){
		if(cached==null){
			try {
				cached=new Gotuskra();
				cached.parseData();
			} catch (SAXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return cached;
	}
	
	static String DEFAULT_URL="http://www.postur.is/gogn/Gotuskra/gotuskra.xml";
	//static String DEFAULT_URL="file:/tmp/gotuskra.xml";
	
	public Gotuskra() throws SAXException, IOException, ParserConfigurationException{
		this(createDocument(DEFAULT_URL));
	}
	
	public Gotuskra(Document document) {
		this.gotuskra=document;

	}
	
	protected void parseData(){
		NodeList gotulisti = gotuskra.getElementsByTagName("Gata");
		for (int i = 0; i < gotulisti.getLength(); i++) {
			Node nGata = gotulisti.item(i);
			NodeList gataNodes = nGata.getChildNodes();
			Gata gata = new Gata();
			for (int j = 0; j < gataNodes.getLength(); j++) {
				Node gataNode = gataNodes.item(j);
				if(gataNode.getNodeName().equals("Id")){
					String id = getNodeChildValueAsString(gataNode);
					try{
						gata.setId(Integer.parseInt(id));
					}
					catch(Exception e){
						e.printStackTrace();
					}
				}
				else if(gataNode.getNodeName().equals("Pnr")){
					String pnr = getNodeChildValueAsString(gataNode);
					gata.setPostnumerString(pnr);
				}
				else if(gataNode.getNodeName().equals("Heiti_nf")){
					String Heiti_nf = getNodeChildValueAsString(gataNode);
					gata.setNafn(Heiti_nf);
				}
				else if(gataNode.getNodeName().equals("Heiti_thgf")){
					String Heiti_thgf = getNodeChildValueAsString(gataNode);
					gata.setNafnThagufall(Heiti_thgf);
				}
			}
			getGotur().put(gata.getPostnumerString(), gata.getNafn(), gata);
		}
	}


	private String getNodeChildValueAsString(Node gataNode) {
		Node firstChild = gataNode.getFirstChild();
		String str = firstChild.getNodeValue();
		return str;
	}

	static Document createDocument(String url) throws SAXException, IOException, ParserConfigurationException{
		Document document = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			   DocumentBuilder builder = factory.newDocumentBuilder();
			   document = builder.parse(url);

			} catch (SAXParseException spe) {
			
			}
		return document;
	}
	
	
//	public static void main(String args[]) throws Exception{
//		
//		Gotuskra skra = new Gotuskra();
//		skra.parseData();
//		
//		List gotur = skra.getGotur();
//		for (Iterator iter = gotur.iterator(); iter.hasNext();) {
//			Gata gata = (Gata) iter.next();
//			System.out.println("Gata: "+gata.getNafn());
//			
//		}
//	}

	public HashMatrix getGotur() {
		if(gotur==null){
			// use insertion order 
			gotur=new HashMatrix() {
				  protected Map getYDimension(Object xKey)  {
					    if (this.xDimension == null) {
					      this.xDimension = new LinkedHashMap();
					    }
					    Map yDimension = (Map) this.xDimension.get(xKey);
					    if (yDimension == null) {
					      yDimension = new LinkedHashMap();
					      this.xDimension.put(xKey, yDimension);
					    }
					    return yDimension;
					  }
			};
		}
		return gotur;
	}

//	public void setGotur(List gotur) {
//		this.gotur = gotur;
//	}
	
	public List getGoturByPostnumer(String postnumer){
		Map gotur = getGotur().get(postnumer);
		Collection streets = gotur.values();
		return new ArrayList(streets);
	}
	
	public Gata getGataByNafnAndPostnumer(String nafn, String postnumer){

		if (nafn == null) { 
			return null;
		}
		nafn = nafn.trim();
		return (Gata) getGotur().get(postnumer, nafn);
	}
	
	public Gata getGataByNafnOrNafnThagufallAndPostnumer(String nafnNefnifallOrThagufall, String postnumer){

		if (nafnNefnifallOrThagufall == null) { 
			return null;
		}
		String nafn = nafnNefnifallOrThagufall.trim().toLowerCase();
		Collection gotur = getGoturByPostnumer(postnumer);
		for (Iterator iterator = gotur.iterator(); iterator.hasNext();) {
			Gata gata = (Gata) iterator.next();
			String gataNafn = gata.getNafn();
			String gataNafnThagufall = gata.getNafnThagufall();
			if(gataNafn.equalsIgnoreCase(nafn)||gataNafnThagufall.equalsIgnoreCase(nafn)){
				return gata;
			}
		}
		return null;
	}
		
//		List allargotur = getGoturByPostnumer(postnumer);
//		for (Iterator iter = allargotur.iterator(); iter.hasNext();) {
//			Gata gata = (Gata) iter.next();
//			if(gata.getNafn().equals(nafn)){
//				return gata;
//			}
//		}
//		return null;
//	}
}
