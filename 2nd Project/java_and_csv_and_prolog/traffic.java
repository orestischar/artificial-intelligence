public class traffic {
	
	int Id_st;
	String traffic_info;
	
	public traffic (int i, String t) {
		
		this.Id_st = i;
		this.traffic_info = t;
	}

	public int getId_st() {
		return Id_st;
	}

	public void setId_st(int id_st) {
		Id_st = id_st;
	}

	public String getTraffic_info() {
		return traffic_info;
	}

	public void setTraffic_info(String traffic_info) {
		this.traffic_info = traffic_info;
	}
	
	

}
