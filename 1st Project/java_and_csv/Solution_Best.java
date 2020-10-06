//package ask1;
import java.util.*;

public class Solution_Best {
	
	ArrayList<point> cs = new ArrayList<point>();
	
	public static void main(String[] args) {
		
		ArrayList<search_front> other_taxis = new ArrayList<search_front>();
		
		freader state = new freader();
		double d, min;
		state.checknodes();

		
		/* sort the taxi list according to the distance from the client */
		
		Collections.sort(state.listoftaxis, new Comparator<taxi>() {

			@Override
			public int compare(taxi arg0, taxi arg1) {
				// TODO Auto-generated method stub
				if (arg0.distance < arg1.distance) return -1;
				else return 1;
			} } );
			
		
		/* find the node which is closest to the client location*/
		
		point closest = state.roads.get(0);
		min = Math.sqrt(Math.pow(state.pelatis.X_c - closest.X_p, 2) + Math.pow(state.pelatis.Y_c - closest.Y_p, 2));
		for (point p: state.roads) {
			d = Math.sqrt(Math.pow(state.pelatis.X_c - p.X_p, 2) + Math.pow(state.pelatis.Y_c - p.Y_p, 2));
			if (d < min) { min = d; closest= p; }
		}
		state.pelatis.X_c = closest.X_p;
		state.pelatis.Y_c = closest.Y_p;
		//System.out.printf("%.7f,%.7f,%d\n",closest.X_p,closest.Y_p,closest.Id_s); 
		//state.print();
	

		double best = Double.MAX_VALUE;
		taxi c_t = null;
		search_front result = null;
		
		for (taxi t: state.listoftaxis) {
			best = Double.MAX_VALUE;

			/* find which node is closest to each taxi */
			point k = state.roads.get(0);
			k.dev = false;
			min = Math.sqrt(Math.pow(t.X_t - k.X_p, 2) + Math.pow(t.Y_t - k.Y_p, 2));
			int min_pos = 0;
			for (int i=0; i<state.roads.size(); i++) {
				point p = state.roads.get(i);
				p.dev= false;
				d = Math.sqrt(Math.pow(t.X_t - p.X_p, 2) + Math.pow(t.Y_t - p.Y_p, 2));
				if (d < min) { min = d; k= p; min_pos=i;}
			}

			
			double distance = Math.sqrt(Math.pow(k.X_p - state.pelatis.X_c, 2) + Math.pow(k.Y_p - state.pelatis.Y_c, 2));

			if (distance < best) {
				search_front init = new search_front(k,closest,state.roads,state.crossnodes,0,distance,null,min_pos);
				
				
				Astar solve = new Astar(best,init);
				
				

				if (solve.solution != null) {
					best = solve.solution.calculate_f();
					c_t = t;
					result = solve.solution;
				}
		 	  } break;
		}
		
		/* print result */
		
		if (result == null) { System.out.print("A* did not find a solution\n");  }
		else {
			System.out.format("The closest taxi has id: %d\n", c_t.Id_t);
			System.out.format("The distance is %f\n", best);
			System.out.print("Nodes:\n");
			result.printit(result);
		
		} 
		
	}
}
