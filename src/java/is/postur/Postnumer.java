package is.postur;

public class Postnumer {
	
	String id;
	int numer;
	String heiti;
	String sveitarfelag;
	String heimili;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return heiti;
	}
	public void setName(String name) {
		this.heiti = name;
	}
	public String getNumer() {
		return getNumerString();
	}
	public void setNumer(String numer) {
		try {
			Integer num = new Integer(numer);
			setNumerInt(num.intValue());
		}
		catch (Exception ex) {
			setNumerInt(0);
		}
	}
	public int getNumerInt() {
		return numer;
	}
	public void setNumerInt(int numer) {
		this.numer = numer;
	}
	public String getNumerString() {
		return new Integer(getNumerInt()).toString();
	}
	public String getSveitarfelag() {
		return sveitarfelag;
	}
	public void setSveitarfelag(String sveitarfelag) {
		this.sveitarfelag = sveitarfelag;
	}

	public String getHeimili() {
		return heimili;
	}
	public void setHeimili(String heimili) {
		this.heimili = heimili;
	}
	
	public String getFulltNafn(){
		return getNumer()+" "+getName();
	}

}
