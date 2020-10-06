import java.io.IOException;
import java.util.*;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;


public class Solution {
	
	public static ArrayList<ArrayList<Double>> result_taxis = new ArrayList<ArrayList<Double>>(); //contains the 5 taxis closer to the client
	public static ArrayList<ArrayList<Double>> rate_taxis = new ArrayList<ArrayList<Double>>(); //contains these taxis in an order acccording to their rating

	public static JIPEngine jip = new JIPEngine();
	public static JIPTermParser parser = jip.getTermParser();
	public static JIPQuery jipQuery; 
	public static JIPTerm term;

	
	public static void main(String[] args) throws JIPSyntaxErrorException, IOException {
		
		int k=5;
		double min,di;
		
		jip.consultFile("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/prolog.pl");
		
		
		
		freader state = new freader();
		String[] t= state.pelatis.time.split(":");
		int tm= Integer.parseInt(t[0]); 
		
		/*****************************************************************************/
		for (traffic p :state.trafficinfo) {
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("looktraffic(" + p.traffic_info + "," + tm + ",C)."));
            term = jipQuery.nextSolution();
            if (!term.getVariablesTable().get("C").toString().equals("0")) {
				jip.asserta(parser.parseTerm("trafficcost(" + p.Id_st + "," + term.getVariablesTable().get("C").toString() + ")."));
			}
		}
		/******************************************************************************/
		for (road r: state.roadinfo) {
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("candrive(" + r.info + ")."));
			term = jipQuery.nextSolution();
			if (term != null) {
				String d[] = r.info.split(",");
				if (d[3].equals("yes")) 
					jip.asserta(parser.parseTerm("direction(" + d[0] + ",1)."));
				else if (d[3].equals("-1"))
					jip.asserta(parser.parseTerm("direction(" + d[0] + ",-1)."));
				else 
					jip.asserta(parser.parseTerm("direction(" + d[0] + ",0)."));
				
				jipQuery = jip.openSynchronousQuery(parser.parseTerm("trafficcost(" + d[0] +",C)."));
				term = jipQuery.nextSolution();
				if (term == null) 
					jip.asserta(parser.parseTerm("trafficcost(" + d[0] + ",1)."));
				
				// here we fill the fields that are missing
				if ( d[6].equals("0")) {
					if (d[2].equals("primary") || d[2].equals("primary_link") || d[2].equals("secondary") || d[2].equals("secondary_link") || d[2].equals("tertiary") || d[2].equals("tertiary_link"))
						d[6] = "3";
					else
						d[6] = "1"; 
				}
				
				if ( d[7].equals("0")) {
					if (d[2].equals("primary") || d[2].equals("primary_link") || d[2].equals("secondary") || d[2].equals("secondary_link") || d[2].equals("tertiary") || d[2].equals("tertiary_link"))
						d[7] = "100";
					
					else d[7] = "60";
				}
				StringBuilder cur = new StringBuilder();
				cur.append(Integer.toString(tm));
				cur.append(",");
				cur.append(d[0]);
				int w;
				for (w=1; w<d.length; w++) {
					cur.append(",");
					cur.append(d[w]);
				}
				
				jipQuery = jip.openSynchronousQuery(parser.parseTerm("trafficcost(" + d[0] +",C)."));
				term = jipQuery.nextSolution();
				if (term == null) 
					cur.append(",1");
				else {
					cur.append(",");
					cur.append(term.getVariablesTable().get("C").toString()); }
				jipQuery = jip.openSynchronousQuery(parser.parseTerm("roadcost(" + cur.toString() + ",Cost).")); 
				term = jipQuery.nextSolution();
				if (term != null)
					jip.asserta(parser.parseTerm("cost(" + d[0] + "," + term.getVariablesTable().get("Cost").toString() + ")."));
				
				
			}
		}
		
		/******************************************************************************/
		int i;
		int previous=0;
		String prev_x= " ";
		String prev_y= " ";
		for (point p : state.nodes) {
			String X = Double.toString(p.X_p);
			String Y = Double.toString(p.Y_p);
			String id = Integer.toString(p.Id_s);
			
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("direction(" + id + ",D)."));
			term = jipQuery.nextSolution();
			if (term != null) {
				if (Integer.parseInt(term.getVariablesTable().get("D").toString()) >= 0) {
					if ( p.Id_s == previous && previous != 0)
						jip.asserta(parser.parseTerm("next(" + prev_x + "," + prev_y + "," + X + "," + Y + "," + id + ")."));
				}
				if (Integer.parseInt(term.getVariablesTable().get("D").toString()) <= 0) {
					if (p.Id_s == previous) 
						jip.asserta(parser.parseTerm("next(" + X + "," + Y + "," + prev_x + "," + prev_y + "," + id + ")."));
					}
				}
			previous= p.Id_s;
			prev_x= X;
			prev_y = Y;
			}
		
		/*******************************************************************************/
		int q;
		for (taxi c: state.listoftaxis) {
			StringBuilder t_lang = new StringBuilder();
			t_lang.append("[");
			for (q=0; q < (c.languages.length - 1); q++) {
				t_lang.append(c.languages[q]);
				t_lang.append(",");

			}
			t_lang.append(c.languages[q]);
			t_lang.append("]");
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("suitable(" + c.avail + "," + c.capacity[0] + "," + c.capacity[1] + "," + Integer.toString(state.pelatis.persons) + "," + t_lang.toString() + "," + state.pelatis.language + "," + Integer.toString(state.pelatis.luggage) + "," + c.type + ")."));
			term = jipQuery.nextSolution();
			if (term != null) {
				if (c.long_distance.equals("yes")) 
					jip.asserta(parser.parseTerm("longdistance(" + Integer.toString(c.Id_t) + ")" ));
				
				jip.asserta(parser.parseTerm("rating(" + Integer.toString(c.Id_t) + "," + Double.toString(c.rating) + ")."));
				jip.asserta(parser.parseTerm("valid(" + Integer.toString(c.Id_t) + ")."));
			}
		}
		
		/******************************************************************************/
		
		/* find the node which is closest to the client location*/
		
		point closest = state.nodes.get(0);
		min = Math.sqrt(Math.pow(state.pelatis.X_c - closest.X_p, 2) + Math.pow(state.pelatis.Y_c - closest.Y_p, 2));
		for (point p: state.nodes) {
			di = Math.sqrt(Math.pow(state.pelatis.X_c - p.X_p, 2) + Math.pow(state.pelatis.Y_c - p.Y_p, 2));
			if (di < min) { min = di; closest= p; }
		}
		state.pelatis.X_c = closest.X_p;
		state.pelatis.Y_c = closest.Y_p;
		
		/* find the node which is closest to the client destination*/
		
		point dest_c = state.nodes.get(0);
		min = Math.sqrt(Math.pow(state.pelatis.X_dest - closest.X_p, 2) + Math.pow(state.pelatis.Y_dest - closest.Y_p, 2));
		for (point p: state.nodes) {
			di = Math.sqrt(Math.pow(state.pelatis.X_dest - p.X_p, 2) + Math.pow(state.pelatis.Y_dest - p.Y_p, 2));
			if (di < min) { min = di; dest_c= p; }
		}
		state.pelatis.X_dest = dest_c.X_p;
		state.pelatis.Y_dest = dest_c.Y_p;
		
		/* sort the taxi list according to the distance from the client */
		
		Collections.sort(state.listoftaxis, new Comparator<taxi>() {

			@Override
			public int compare(taxi arg0, taxi arg1) {
				// TODO Auto-generated method stub
				if (arg0.distance < arg1.distance) return -1;
				else return 1;
			} } );
		
		double dist;
		search_front result= null;
		
		
		for (taxi tx: state.listoftaxis) {
			int h;
			/* find which node is closest to each taxi */
			jipQuery = jip.openSynchronousQuery(parser.parseTerm("valid(" + Integer.toString(tx.Id_t) + ")."));
			term = jipQuery.nextSolution();
			if (term != null) { 
				point spot = state.nodes.get(0);
				min = Math.sqrt(Math.pow((tx.X_t - spot.X_p),2) + Math.pow((tx.Y_t - spot.Y_p),2));
				for (h=0; h<state.nodes.size(); h++) {
					point tmp = state.nodes.get(h);
					dist = Math.sqrt(Math.pow(tx.X_t - tmp.X_p, 2) +  Math.pow(tx.Y_t - tmp.Y_p, 2));
					if (dist < min) { 
						min= dist;  
						spot = tmp; }
				}
				double distance_p=  Math.sqrt(Math.pow(spot.X_p - state.pelatis.X_c, 2) + Math.pow(spot.Y_p - state.pelatis.Y_c, 2));
				search_front init = new search_front(spot.X_p,spot.Y_p,closest,0,distance_p,null) ;
				Astar solve = new Astar(init);
				
				result = solve.solution; 
				if (result != null) {
					ArrayList<Double> res = new ArrayList<Double>();
					res.add(Double.parseDouble(Integer.toString(tx.Id_t)));
					res.add(solve.solution.calculate_f());
					jipQuery = jip.openSynchronousQuery(parser.parseTerm("rating(" + Integer.toString(tx.Id_t) + ",R)."));
					term = jipQuery.nextSolution();
					if (term != null) {
						res.add(Double.parseDouble(term.getVariablesTable().get("R").toString()));
						result_taxis.add(res); 

					}
					System.out.format("\nTaxi with id= %d\n",tx.Id_t);
					//if we want to type the route from each taxi's position to the client's position:
					result.printit(result);
					}
				else System.out.format("%d\n",tx.Id_t);
				}
			
			}
		
		Collections.sort(result_taxis, new Comparator<ArrayList<Double>>() {

			@Override
			public int compare(ArrayList<Double> arg0, ArrayList<Double> arg1) {
				// TODO Auto-generated method stub
				if (arg0.get(1) < arg1.get(1)) return -1;
				else return 1;
			} } );
		
		if (result_taxis.size()==0) {
			System.out.println("No taxi for you, sorryyyyyyyyy byeeeeeeee");
			return;
		}
		
		if (result_taxis.size() < 5) k= result_taxis.size();  //if the taxis we have are less than 5

		System.out.println("Hey you.");
		System.out.format("These %d taxis are reaaaaady to ruuuumble !\n", k);
		int e;
		for (e=0; e<k; e++) {
			System.out.format("%d. Taxi with id = %d has distance from you = %f\n",e+1, result_taxis.get(e).get(0).intValue(), result_taxis.get(e).get(1));
			rate_taxis.add(result_taxis.get(e)); }
		
		Collections.sort(rate_taxis, new Comparator<ArrayList<Double>>() {

			@Override
			public int compare(ArrayList<Double> arg0, ArrayList<Double> arg1) {
				// TODO Auto-generated method stub
				if (arg0.get(2) < arg1.get(2)) return 1;
				else return -1;
			} } );
		
		System.out.println("\nYou wanna rating order? No problemo Bob !");
		for (e=0; e<k; e++) 
			System.out.format("%d. Taxi with id = %d and rating = %f\n",e+1, rate_taxis.get(e).get(0).intValue(), rate_taxis.get(e).get(2));

		System.out.println("Gimme the id of the one that you want, that you really really want.");
		int num_t;
		Scanner scan = new Scanner(System.in);
		num_t = scan.nextInt();
		
		System.out.format("Kind of a weird choice but whatevs... Taxi with id = %d is on it's way\n",num_t);
		
		double finald = Math.sqrt(Math.pow((dest_c.X_p - closest.X_p), (dest_c.Y_p - closest.Y_p)));
		search_front cl = new search_front(closest.X_p,closest.Y_p,dest_c,0,finald,null) ;
		Astar solve2 = new Astar(cl);
		search_front res2;
		res2 = solve2.solution; 
		//if we want to type the route from the client's position to the destination: 
		res2.printit(res2);
		
		System.out.println("Please mind your personal belongings. Thanks you");
		}
				
	} 

