
import java.util.*;


public class Astar {
	
	double best_so_far;
	public search_front start;
	public ArrayList<search_front> to_do = new ArrayList<search_front>();
	public search_front solution;
    

	
	public Astar (double d, search_front s) {
		this.best_so_far = d;
		this.start = s;
		this.solution = null ;
		this.to_do.add(s);
	
		while (! to_do.isEmpty()) {

			search_front min_sf = to_do.get(0);
			int min_p = 0;
			double min_d = min_sf.calculate_f();			// min_d to f ths temp
			for (int i=1; i<to_do.size(); i++) {
				search_front temp = to_do.get(i);
				if (min_d > temp.calculate_f()) {
					min_d = temp.calculate_f();
					min_p = i;
					min_sf = temp; }						
				}

			to_do.remove(min_p);
			

			for (int i=0; i<to_do.size(); i++){

				search_front temp2 = to_do.get(i);

				if(temp2.cur_node==min_sf.cur_node && temp2.calculate_f()==min_d){
					//System.out.println("WWWWWWWWWWWWWWWWWWWW");
					to_do.remove(i);
					min_sf.susie();
					search_front temp3 = min_sf;
					search_front temp4;
					outerloop:
					while( (temp3.getPrevious())!=null){
						
						temp4 = temp2;

						while(temp4.getPrevious()!=null){
							if(temp4==temp3.getPrevious()) {
								search_front bob = new search_front(temp4.cur_node, temp4.goal_node, temp4.all_nodes, temp4.crosses, temp4.g_distance, temp4.h_distance, temp2, temp4.pos );
								//temp4.argo();
								//System.out.println("ANDIIIIIIIIIIIIIIIIIIIIIIIIIIIWILL");
								bob.argo();
								temp3.set_previous(bob);
								//temp3.set_previous(temp2);
								break outerloop;
							}
							temp4=temp4.getPrevious();
						}
						temp3=temp3.getPrevious();
					}

				}
			}


			
			if (min_sf.success()) { this.solution = min_sf;   break; }
			

			if (min_sf.cur_node.dev) continue;
			else { min_sf.cur_node.dev = true;   }
			
			 if (min_d < this.best_so_far) {
					

				for (search_front n : min_sf.check_neighbours()) {
					
					to_do.add(n);
					
				} }
			else { this.solution = null;  break; }
			
		}
		
	}

}