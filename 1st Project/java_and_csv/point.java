import java.util.ArrayList;

public class point{
	
	double X_p;
	double Y_p;
	int Id_s ;
	public ArrayList<Integer> rd = new ArrayList<Integer>();
	public ArrayList<Integer> pos_list = new ArrayList<Integer>();
	boolean dev;
	
	public point(double xcord, double ycord, int id) {
		
		this.X_p = xcord;
		this.Y_p = ycord;
		this.Id_s = id;
		dev = false;
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

	public ArrayList<Integer> getRd() {
		return rd;
	}

	public void setRd(ArrayList<Integer> rd) {
		this.rd = rd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(X_p);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(Y_p);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		point other = (point) obj;
		if (Double.doubleToLongBits(X_p) != Double.doubleToLongBits(other.X_p))
			return false;
		if (Double.doubleToLongBits(Y_p) != Double.doubleToLongBits(other.Y_p))
			return false;
		return true;
	}
	
	

}
