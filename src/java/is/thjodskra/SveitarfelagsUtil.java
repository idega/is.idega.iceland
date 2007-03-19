package is.thjodskra;

public class SveitarfelagsUtil {

	
	/**
	 * <p>
	 * Gets the "Sveitarfelagsnumer" or Municipality number/code from a given postal code.
	 * Currently only handles the largest and most common municipalities in Iceland.
	 * </p>
	 * @param postnumer
	 * @return
	 */
	public static String getSveitarfelagsnrFromPostnumer(String postnumer) {

		String sveitarfelagsnr = null;
		if(postnumer!=null){
			if(postnumer.startsWith("10")||postnumer.startsWith("11")||postnumer.startsWith("12")||postnumer.startsWith("13")||postnumer.startsWith("15")){
				//Reykjavik
				return "0000";
			}
			else if(postnumer.startsWith("17")){
				//Seltjarnarnes
				return "1100";
			}
			else if(postnumer.startsWith("20")){
				//Kopavogur
				return "1000";
			}
			else if(postnumer.startsWith("21")){
				//Gardabaer
				return "1300";
			}
			else if(postnumer.startsWith("22")){
				//Hafnarfjordur
				return "1400";
			}
			else if(postnumer.startsWith("23")){
				//Reykjanesbaer
				return "2000";
			}
			else if(postnumer.equals("240")){
				//Grindavik
				return "2300";
			}
			else if(postnumer.startsWith("30")){
				//Akranes
				return "3000";
			}
			
			else if(postnumer.startsWith("60")){
				//Akureyri
				return "6000";
			}
		}
		
		return sveitarfelagsnr;
	}

	
}
