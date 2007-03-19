package is.postur;

import com.idega.core.location.data.PostalCode;
import com.idega.core.location.data.PostalCodeHome;
import com.idega.data.IDOLookup;

public class StreetImporter {

	public StreetImporter(){
		try {
			executeImport(new Gotuskra());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void executeImport(Gotuskra gotuskra) throws Exception{
		/*
		StreetHome streetHome = (StreetHome) IDOLookup.getHome(StreetHome.class);
		
		List gotur = gotuskra.getGotur();
		for (Iterator iter = gotur.iterator(); iter.hasNext();) {
			Gata gata = (Gata) iter.next();
			int id = gata.getId();
			Integer iId = new Integer(id);
			Street street;
			try{
				street = streetHome.findByPrimaryKey(iId);
			}
			catch(FinderException fe){
				street = streetHome.create(iId);
				street.setStreetName(gata.getHeiti());
				street.setStreetNameDative(gata.getHeitiThagufall());
				street.setPostalCode(getPostalCode(gata.getPostnumer()));
				street.store();
			}
			
		}
		*/
		
	}

	private PostalCode getPostalCode(String postnumer) throws Exception {
		PostalCodeHome postalCodeHome = (PostalCodeHome) IDOLookup.getHome(PostalCode.class);
		return postalCodeHome.findByPostalCode(postnumer);
	}
	
}
