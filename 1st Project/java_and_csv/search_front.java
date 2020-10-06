import java.util.*;
import java.io.*;

public class search_front {

	public point cur_node;
	public point goal_node;
	ArrayList<point> all_nodes ;
	Map<point,point> crosses;
	public double g_distance;
	public double h_distance;
	public search_front previous;
	boolean exm;
	int pos;
	boolean susan;
	boolean jason;



public search_front (point cn, point gn,  ArrayList<point> nodes_list, Map<point,point> cross_list, double gi, double hi, search_front p,int i) {
	this.cur_node = cn;
	this.goal_node = gn;
	this.all_nodes = nodes_list;
	this.crosses = cross_list;
	this.g_distance = gi;
	this.h_distance = hi;
	this.previous = p;
	this.exm = false;
	this.pos = i;
	this.susan=false;
	this.jason=false;
}


public ArrayList<search_front> check_neighbours  () {
	ArrayList <search_front> neighbours = new ArrayList<search_front>();
	/* check for the previous point on the same road*/
	if (pos>0) {
		point temp = all_nodes.get(pos-1);
		if (temp.Id_s == cur_node.Id_s && !temp.dev) {
			double d_h = Math.sqrt(Math.pow(goal_node.X_p - temp.X_p, 2) + Math.pow(goal_node.Y_p - temp.Y_p, 2));
			//Manhattan distance
			double d_g = g_distance + Math.abs(cur_node.X_p - temp.X_p) + Math.abs(cur_node.Y_p - temp.Y_p);
			search_front prev = new search_front(temp, goal_node, all_nodes, crosses, d_g, d_h, this, pos-1 );
			//System.out.format("%f,%f,%d\n",temp.X_p,temp.Y_p,temp.Id_s); 
			neighbours.add(prev);
		} 
	}
	/* check for the next point on the same road */
	if (pos < (all_nodes.size() - 1)) {
		point temp = all_nodes.get(pos+1);
		if (temp.Id_s == cur_node.Id_s && !temp.dev) {
			double d_h = Math.sqrt(Math.pow(goal_node.X_p - temp.X_p, 2) + Math.pow(goal_node.Y_p - temp.Y_p, 2));
			double d_g = g_distance + Math.abs(cur_node.X_p - temp.X_p) + Math.abs(cur_node.Y_p - temp.Y_p);
			search_front next = new search_front(temp, goal_node, all_nodes, crosses, d_g, d_h, this, pos+1 );
			neighbours.add(next);
		}
	}

	point temp = crosses.get(cur_node);
	if (temp != null) {
		for (int i=0; i<temp.rd.size(); i++) {
			if (temp.rd.get(i) != cur_node.Id_s) {
				int ps = temp.pos_list.get(i);
				point temp2 = all_nodes.get(ps);
				if (!temp2.dev) {
					double d_h = Math.sqrt(Math.pow(goal_node.X_p - temp2.X_p, 2) + Math.pow(goal_node.Y_p - temp2.Y_p, 2));
					double d_g = g_distance + Math.abs(cur_node.X_p - temp2.X_p) + Math.abs(cur_node.Y_p - temp2.Y_p);
					search_front crs = new search_front(temp2, goal_node, all_nodes, crosses, d_g, d_h, this, ps );
					neighbours.add(crs); }
			}
			
		}
	}
	//cur_node.dev = true;
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
	if ((cur_node.X_p == goal_node.X_p) && (cur_node.Y_p == goal_node.Y_p)) return true;
	else return false;
}

public void printit (search_front s) {
	if (s.getPrevious() == null){
		//if(s.susan==true) System.out.println("DIFFERENT WAYS COMING UP");
		//if(s.jason==true) System.out.println("END OF WAY"); 
		System.out.format("%.7f,%.7f,0\n",s.cur_node.X_p,s.cur_node.Y_p);
	}
	
	
	else {
		printit(s.getPrevious());
		//if(s.susan==true) System.out.println("DIFFERENT WAYS COMING UP");
		if(s.jason==true) System.out.println("END OF WAY"); 
		System.out.format("%.7f,%.7f,0\n",s.cur_node.X_p,s.cur_node.Y_p);
		}
}

public search_front getPrevious() {
	return previous;
}

public boolean isExm() {
	return exm;
}

public void susie() {
	this.susan=true;
}
public void argo() {
	this.jason=true;
}

public void setExm(boolean exm) {
	this.exm = exm;
}


}

