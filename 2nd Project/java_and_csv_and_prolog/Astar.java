import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Astar {
	
	public search_front start;
	public ArrayList<search_front> to_do = new ArrayList<search_front>();
	public search_front solution;

	public Astar (search_front s) {
		
		this.start = s;
		this.solution = null ;
		this.to_do.add(s);
		
		Set<search_front> dev = new HashSet<>() ; 
		
		while (! to_do.isEmpty()) {
			search_front min_sf = to_do.get(0);
			int min_p = 0;
			double min_d = min_sf.calculate_f();
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

				if(temp2.curX==min_sf.curX && temp2.curY==min_sf.curY && temp2.calculate_f()==min_d){
					to_do.remove(i);
					search_front temp3 = min_sf;
					search_front temp4;
					outerloop:

					while( (temp3.getPrevious())!=null){
						temp4 = temp2;

						while(temp4.getPrevious()!=null){
							if(temp4==temp3.getPrevious()){
								search_front bob = new search_front(temp4.curX, temp4.curY, temp4.goal_node, temp4.g_distance, temp4.h_distance, temp2);
								bob.jason=true;
								temp3.set_previous(bob);
								break outerloop;
							}

							temp4=temp4.getPrevious();
					}
				temp3=temp3.getPrevious();

				}
			}
		}

			if (min_sf.success()) { 
				this.solution = min_sf;  
				break; }
			
			if (dev.contains(min_sf)) continue;
			else dev.add(min_sf);
			
			for (search_front n : min_sf.check_neighbours()) 
					to_do.add(n); }
			
			

} } 
