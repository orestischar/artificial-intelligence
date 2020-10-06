public class taxi {

	
	public double X_t;
	public double Y_t;
	public int Id_t;
	String avail;
	String[] capacity;
	String[] languages;
	double rating;
	String long_distance;
	String type;
	double distance;
	
	
	
	public taxi (double xcord, double ycord, int id, String a, String[] c, String[] l, double r, String d, String t) {
		
		this.X_t = xcord;
		this.Y_t = ycord;
		this.Id_t = id;
		this.avail = a;
		this.capacity = c;
		this.languages = l;
		this.rating = r;
		this.long_distance = d;
		this.type = t;
		
	}

	public double getX_t() {
		return X_t;
	}

	public void setX_t(double x_t) {
		X_t = x_t;
	}

	public double getY_t() {
		return Y_t;
	}

	public void setY_t(double y_t) {
		Y_t = y_t;
	}

	public int getId_t() {
		return Id_t;
	}

	public void setId_t(int id_t) {
		Id_t = id_t;
	}

	public String isAvail() {
		return avail;
	}

	public void setAvail(String avail) {
		this.avail = avail;
	}

	

	public String[] getCapacity() {
		return capacity;
	}

	public void setCapacity(String[] capacity) {
		this.capacity = capacity;
	}

	public String[] getLanguages() {
		return languages;
	}

	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String isLong_distance() {
		return long_distance;
	}

	public void setLong_distance(String long_distance) {
		this.long_distance = long_distance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
