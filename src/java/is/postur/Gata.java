package is.postur;

public class Gata {

	private int id;
	private String heiti;
	private String heitiThagufall;
	private String postnumerString;
	private Postnumer postnumer;
	
	public String getNafn() {
		return heiti;
	}
	public void setNafn(String heiti) {
		this.heiti = heiti;
	}
	public String getNafnThagufall() {
		return heitiThagufall;
	}
	public void setNafnThagufall(String heitiThagufall) {
		this.heitiThagufall = heitiThagufall;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getGotuId() {
		return new Integer(getId()).toString();
	}
	/*
	public void setGotuId(int id) {
		this.id = id;
	}
	*/
	
	public String getPostnumerString() {
		if(postnumerString==null&&postnumer!=null){
			return new Integer(postnumer.getNumer()).toString();
		}
		return postnumerString;
	}
	public void setPostnumerString(String postnumer) {
		if(postnumer!=null&&!postnumer.equals("")){
			Postnumer num = Postnumeraskra.getCached().getPostnumerByNumer(postnumer);
			setPostnumer(num);
		}
		this.postnumerString = postnumer;
	}
	public Postnumer getPostnumer() {
		return postnumer;
	}
	public void setPostnumer(Postnumer postnumer) {
		this.postnumer = postnumer;
	}
	
}
