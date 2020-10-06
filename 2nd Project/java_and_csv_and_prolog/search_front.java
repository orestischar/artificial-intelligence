import java.util.ArrayList;

import com.ugos.jiprolog.engine.JIPQuery;


public class search_front {
	
	double curX;
	double curY;
	public point goal_node;
	public double g_distance;
	public double h_distance;
	public search_front previous;
	boolean jason;
	
	public search_front (double X, double Y, point gn, double g, double h, search_front p) {
		
		this.curX = X;
		this.curY = Y;
		this.goal_node = gn;
		this.g_distance = g;
		this.h_distance = h;
		this.previous = p;
		this.jason=false;
	}
	
	public ArrayList<search_front> check_neighbours () {
		
		JIPQuery  jipQ;
		ArrayList <search_front> neighbours = new ArrayList<search_front>();
		Solution.jipQuery = Solution.jip.openSynchronousQuery(Solution.parser.parseTerm("next(" + Double.toString(curX) + "," + Double.toString(curY) + ",Nx, Ny, R)."));
    	Solution.term = Solution.jipQuery.nextSolution();

        while (Solution.term != null) {

        	double new_X = Double.parseDouble(Solution.term.getVariablesTable().get("Nx").toString());
        	double new_Y = Double.parseDouble(Solution.term.getVariablesTable().get("Ny").toString());
        	jipQ = Solution.jip.openSynchronousQuery(Solution.parser.parseTerm("cost(" + Solution.term.getVariablesTable().get("R").toString() + ", C)."));
        	Solution.term = jipQ.nextSolution();
        	double new_g = g_distance + (Math.abs(curX - new_X) + Math.abs(curY - new_Y)) * Double.parseDouble(Solution.term.getVariablesTable().get("C").toString());
        	double new_h = Math.sqrt(Math.pow(goal_node.X_p - new_X, 2) + Math.pow(goal_node.Y_p - new_Y, 2));
        	search_front neg = new search_front(new_X, new_Y,goal_node,new_g,new_h,this);
        	
        	neighbours.add(neg);
            Solution.term = Solution.jipQuery.nextSolution();

        }
        return neighbours;
		
	}

	public void set_previous(search_front k){
		//if(this.previous==null)
		this.previous=k;
		//else System.out.println("WWWWWWWWWWWWWWWWWWWW");
	}
	
	public double calculate_f () {
		return h_distance + g_distance;
	}

	public boolean success () {
		if ((curX == goal_node.X_p) && (curY == goal_node.Y_p)) return true;
		else return false;
	}

	public void printit (search_front s) {
		if (s.getPrevious() == null)
			//if(s.jason==true) System.out.println("END OF WAY"); 
			System.out.format("%.7f,%.7f,0\n",s.curX,s.curY);
		
		else {
			printit(s.getPrevious());
			if(s.jason==true) System.out.println("END OF WAY"); 
			System.out.format("%.7f,%.7f,0\n",s.curX,s.curY); }
	}

	public search_front getPrevious() {
		return previous;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(curX);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(curY);
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
		search_front other = (search_front) obj;
		if (Double.doubleToLongBits(curX) != Double
				.doubleToLongBits(other.curX))
			return false;
		if (Double.doubleToLongBits(curY) != Double
				.doubleToLongBits(other.curY))
			return false;
		return true;
	}
	
	
}
