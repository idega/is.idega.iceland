package is.thjodskra;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.idega.repository.data.Instantiator;
import com.idega.repository.data.Singleton;
import com.idega.repository.data.SingletonRepository;

public class SveitarfelagsUtil implements Singleton {
	
	private static final String MAPPING_PROPERTY_RESOURCE = "/resources/postnumerSveitarfelagsnumerMapping.properties";
	
	private static Instantiator instantiator = new Instantiator() { 
		
		public Object getInstance() { 
			return new SveitarfelagsUtil();
		}
		
	};
	
	public static SveitarfelagsUtil getInstance() {
		return (SveitarfelagsUtil) SingletonRepository.getRepository().getInstance(SveitarfelagsUtil.class, instantiator);
	}

	
	/* useful links -
	following sources were used for figuring the mapping of postal code to sveitarfelag number out
	
	main source:
	http://is.wikipedia.org/wiki/Íslensk_sveitarfélög_fyrr_og_síðar (mapping postalcode and sveitarfelagsnumer
	http://lmi.is/ (sveitarfelags map)
	note, those postal codes are just post office boxes:
	http://www.postur.is/english/Pricing/Post_Office_Box.html
	
	http://www.zignalzone.com/postnumer/
	http://www.fmr.is/?PageID=599  (list of sveitarfelagsnumer)
	http://is.wikipedia.org/wiki/Íslensk_sveitarfélög_fyrr_og_síðar (mapping postalcode and sveitarfelagsnumer)
	http://www.postur.is/cgi-bin/hsrun.exe/Distributed/vefur/vefur.htx;start=HS_Postnumeralisti (postalcode list)
	http://www.postur.is/english/Pricing/Post_Office_Box.html
	http://www.postur.is/vefur/Afgreidslustadir/landakort.htm
	
	some source html code found on fmr
    <option value="0">Reykjavík                    - (0)</option>
    <option value="1000">Kópavogur                    - (1000)</option>
    <option value="1100">Seltjarnarnes                - (1100)</option>
    <option value="1300">Garðabær                     - (1300)</option>
    <option value="1400">Hafnarfjörður                - (1400)</option>
    <option value="1603">Álftanes                     - (1603)</option>
    <option value="1604">Mosfellsbær                  - (1604)</option>
    <option value="1605">Reykjavík - Kjalarnes        - (1605)</option>
    <option value="1606">Kjósarhreppur                - (1606)</option>
    <option value="2000">Reykjanesbær                 - (2000)</option>
    <option value="2300">Grindavík                    - (2300)</option>
    <option value="2503">Sandgerði                    - (2503)</option>
    <option value="2504">Garður                       - (2504)</option>
    <option value="2506">Vogar                        - (2506)</option>
    <option value="2509">Varnarsvæðið Miðnesheiði     - (2509)</option>
    <option value="3000">Akranes                      - (3000)</option>
    <option value="3506">Skorradalshreppur            - (3506)</option>
    <option value="3511">Hvalfjarðarsveit             - (3511)</option>
    <option value="3609">Borgarbyggð                  - (3609)</option>
    <option value="3709">Grundarfjarðarbær            - (3709)</option>
    <option value="3710">Helgafellssveit              - (3710)</option>
    <option value="3711">Stykkishólmur                - (3711)</option>
    <option value="3713">Eyja- og Miklaholtshreppur   - (3713)</option>
    <option value="3714">Snæfellsbær                  - (3714)</option>
    <option value="3811">Dalabyggð                    - (3811)</option>
    <option value="4100">Bolungarvík                  - (4100)</option>
    <option value="4200">Ísafjarðarbær                - (4200)</option>
    <option value="4502">Reykhólahreppur              - (4502)</option>
    <option value="4604">Tálknafjarðarhreppur         - (4604)</option>
    <option value="4607">Vesturbyggð                  - (4607)</option>
    <option value="4803">Súðavíkurhreppur             - (4803)</option>
    <option value="4901">Árneshreppur                 - (4901)</option>
    <option value="4902">Kaldrananeshreppur           - (4902)</option>
    <option value="4908">Bæjarhreppur                 - (4908)</option>
    <option value="4911">Strandabyggð                 - (4911)</option>
    <option value="5200">Skagafjörður                 - (5200)</option>
    <option value="5508">Húnaþing vestra              - (5508)</option>
    <option value="5604">Blönduós                     - (5604)</option>
    <option value="5609">Skagaströnd                  - (5609)</option>
    <option value="5611">Skagabyggð                   - (5611)</option>
    <option value="5612">Húnavatnshreppur             - (5612)</option>
    <option value="5706">Akrahreppur                  - (5706)</option>
    <option value="6000">Akureyri                     - (6000)</option>
    <option value="6100">Norðurþing                   - (6100)</option>
    <option value="6250">Fjallabyggð                  - (6250)</option>
    <option value="6400">Dalvíkurbyggð                - (6400)</option>
    <option value="6501">Grímseyjarhreppur            - (6501)</option>
    <option value="6506">Arnarneshreppur              - (6506)</option>
    <option value="6513">Eyjafjarðarsveit             - (6513)</option>
    <option value="6514">Hörgárbyggð                  - (6514)</option>
    <option value="6601">Svalbarðsströnd              - (6601)</option>
    <option value="6602">Grýtubakkahreppur            - (6602)</option>
    <option value="6607">Kröfluvirkjun                - (6607)</option>
    <option value="6609">Aðaldælahreppur              - (6609)</option>
    <option value="6611">Tjörneshreppur               - (6611)</option>
    <option value="6612">Þingeyjarsveit               - (6612)</option>
    <option value="6706">Svalbarðshreppur             - (6706)</option>
    <option value="6709">Langanesbyggð                - (6709)</option>
    <option value="7000">Seyðisfjörður                - (7000)</option>
    <option value="7300">Fjarðabyggð                  - (7300)</option>
    <option value="7502">Vopnafjarðarhreppur          - (7502)</option>
    <option value="7505">Fljótsdalshreppur            - (7505)</option>
    <option value="7509">Borgarfjarðarhreppur         - (7509)</option>
    <option value="7613">Breiðdalshreppur             - (7613)</option>
    <option value="7617">Djúpavogshreppur             - (7617)</option>
    <option value="7620">Fljótsdalshérað              - (7620)</option>
    <option value="7708">Hornafjörður                 - (7708)</option>
    <option value="8000">Vestmannaeyjar               - (8000)</option>
    <option value="8200">Sveitarfélagið Árborg        - (8200)</option>
    <option value="8508">Mýrdalshreppur               - (8508)</option>
    <option value="8509">Skaftárhreppur               - (8509)</option>
    <option value="8610">Vatnsfellsvirkjun            - (8610)</option>
    <option value="8613">Rangárþing eystra            - (8613)</option>
    <option value="8614">Rangárþing ytra              - (8614)</option>
    <option value="8710">Hrunamannahreppur            - (8710)</option>
    <option value="8716">Hveragerði                   - (8716)</option>
    <option value="8717">Hellisheiðarvirkjun          - (8717)</option>
    <option value="8719">Grímsnes-og Grafningshreppur - (8719)</option>
    <option value="8720">Skeiða- og Gnúpverjahreppur  - (8720)</option>
    <option value="8721">Bláskógabyggð                - (8721)</option>
    <option value="8722">Flóahreppur                  - (8722)</option>
    */

	private Map postnumerSveitarfelagsMap;

	
	private SveitarfelagsUtil() {
		initialize();
	}
	
	public Set getSveitarfelagsnrFromPostnumer(String postnumer) {
		return (Set) postnumerSveitarfelagsMap.get(postnumer);
	}
	
	private void initialize() {
		InputStream inputStream = getClass().getResourceAsStream(MAPPING_PROPERTY_RESOURCE);
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		postnumerSveitarfelagsMap = new HashMap(properties.size() + 55 + 21);
		// adding ranges
		// 101 till 155 belongs to 0000
		addRange(postnumerSveitarfelagsMap, "0000", 101, 155);
		// 550 till 570 belongs to 5200
		addRange(postnumerSveitarfelagsMap, "5200", 550, 570);
		
		Iterator iterator = properties.keySet().iterator();
		while (iterator.hasNext()) {
			String sveitarfelagsnumer = (String) iterator.next();
			String postalcodesString = (String) properties.get(sveitarfelagsnumer);
			String[] postalcodes = postalcodesString.split("\\s");
			for (int i = 0; i < postalcodes.length; i++) {
				add(postnumerSveitarfelagsMap, postalcodes[i], sveitarfelagsnumer);
			}
		}
	}
	
	private void add(Map targetMap, String key, String value) {
		Set valueSet;
		if (! targetMap.containsKey(key)) {
			valueSet = new HashSet(1);
			targetMap.put(key, valueSet);
		}
		else {
			valueSet = (Set) targetMap.get(key);
		}
		valueSet.add(value);
	}
	
	
	private void addRange(Map targetMap, String value, int start, int end) {
		for (int i = start; i <= end; i++) {
			String postalCode = Integer.toString(i);
			add(targetMap, postalCode, value);
		}
	}
	/*
	 * for testing
	 */
	public static void main(String[] args) {
		SveitarfelagsUtil sveitarfelagsUtil = new SveitarfelagsUtil();
		sveitarfelagsUtil.initialize();
		
	}
	
	
}
