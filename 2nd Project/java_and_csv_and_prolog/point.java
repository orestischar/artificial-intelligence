public class point {
	
	double X_p;
	double Y_p;
	int Id_s ;
	String Id_p;
	
	
	public point(double xcord, double ycord, int i, String j) {
		
		this.X_p = xcord;
		this.Y_p = ycord;
		this.Id_s = i;
		this.Id_p = j;
	}

	public double getX_p() {
		return X_p;
	}

	public void setX_p(double x_p) {
		X_p = x_p;
	}

	public double getY_p() {
		return Y_p;
	}

	public void setY_p(float y_p) {
		Y_p = y_p;
	}

	public int getId_s() {
		return Id_s;
	}

	public void setId_s(int id_s) {
		Id_s = id_s;
	}

	public String getId_p() {
		return Id_p;
	}

	public void setId_p(String id_p) {
		Id_p = id_p;
	}
	
	

}
