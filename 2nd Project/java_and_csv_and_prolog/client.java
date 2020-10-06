public class client {
	
		public double X_c;
		public double Y_c;
		public double X_dest;
		public double Y_dest;
		public String time;
		public int persons;
		public String language;
		public int luggage;

		public client (double x1, double y1, double x2, double y2, String t, int p, String l, int lg) {
			
			this.X_c = x1;
			this.Y_c = y1;
			this.X_dest = x2;
			this.Y_dest = y2;
			this.time = t;
			this.persons = p;
			this.language = l;
			this.luggage = lg;
		}

		public double getX_c() {
			return X_c;
		}

		public void setX_c(double x_c) {
			X_c = x_c;
		}

		public double getY_c() {
			return Y_c;
		}

		public void setY_c(double y_c) {
			Y_c = y_c;
		}

		public double getX_dest() {
			return X_dest;
		}

		public void setX_dest(double x_dest) {
			X_dest = x_dest;
		}

		public double getY_dest() {
			return Y_dest;
		}

		public void setY_dest(double y_dest) {
			Y_dest = y_dest;
		}

		public String getTime() {
			return time;
		}

		public void setTime(String time) {
			this.time = time;
		}

		public int getPersons() {
			return persons;
		}

		public void setPersons(int persons) {
			this.persons = persons;
		}

		public String getLanguage() {
			return language;
		}

		public void setLanguage(String language) {
			this.language = language;
		}

		public int getLuggage() {
			return luggage;
		}

		public void setLuggage(int luggage) {
			this.luggage = luggage;
		}
		
		


}


