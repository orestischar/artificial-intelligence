import java.util.*;
import java.io.*;

//------------------------------------------------------------------------
//Edw prepei na allaxthei to path sta new File("______")
//------------------------------------------------------------------------


public class freader {
	
	public client pelatis;
	public ArrayList<taxi> listoftaxis = new ArrayList<taxi>();
	public ArrayList<point> roads = new ArrayList<point>();
	public Map <point, point> crossnodes = new HashMap<>();
	
	public freader ()  {

        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> temp1 = new ArrayList<>();


		try {
			
			File file1 = new File("/home/orestis/Desktop/TN2018-T1/java_and_csv/client.csv");
			Scanner f1 = new Scanner(file1);
			//X,Y
			//23.74674,37.97852
			String line = f1.nextLine();
			line = f1.nextLine();
			String[] a = line.split(",");
			
			this.pelatis = new client(Double.parseDouble(a[0]),Double.parseDouble(a[1]));
			f1.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			String line2;
			Scanner file2 = new Scanner(new File("/home/orestis/Desktop/TN2018-T1/java_and_csv/taxis.csv"));
			

			line2 = file2.nextLine();
			line2 = file2.nextLine();
			
			while (file2.hasNextLine()) {
				temp.add(line2);
				//i++;
				line2 = file2.nextLine();
            }
			
			temp.add(line2);
			
            Iterator<String> taxi_iterator = temp.iterator();

			while (taxi_iterator.hasNext()) {
                String[] a = taxi_iterator.next().split(",");
                taxi t = new taxi(Double.parseDouble(a[0]), Double.parseDouble(a[1]), Integer.parseInt(a[2]));
                t.distance = Math.sqrt(Math.pow(this.pelatis.X_c - t.X_t, 2) + Math.pow(this.pelatis.Y_c - t.Y_t, 2));
                this.listoftaxis.add(t);
                
			}
			 
			 
			 file2.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
			String line3;
			Scanner file3 = new Scanner(new File("/home/orestis/Desktop/TN2018-T1/java_and_csv/nodes.csv"));
			
			//ArrayList<String> temp1 = new ArrayList<>();
			if((file3.hasNextLine()))
				line3 = file3.nextLine();
           	line3 = file3.nextLine();
			//int j=0;
            while (file3.hasNextLine()) {
                temp1.add(line3);
                line3 = file3.nextLine();
                //j++;
            }
            
            temp1.add(line3);
            //System.out.format("%d\n", j);
			Iterator<String> points_iterator = temp1.iterator();

			while (points_iterator.hasNext()) {
                String[] a = points_iterator.next().split(",");
                this.roads.add(new point(Double.parseDouble(a[0]), Double.parseDouble(a[1]), Integer.parseInt(a[2])));
			}
			 
			 
			 file3.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		
		
	}
	
		public void checknodes () {
				int i;
				//Map <point, point> crossnodes = new HashMap<>();
				for (i=0; i<roads.size(); i++) {
					point p = roads.get(i);
					point temp = crossnodes.get(p);
					
					if (temp == null) {
						 crossnodes.put(p, p);
						 temp = crossnodes.get(p);
						 temp.rd.add(p.Id_s);
						 temp.pos_list.add(i); }
						
					else {
					 temp.rd.add(p.Id_s);
					 temp.pos_list.add(i);
				} }
			
			 // i=0; Crossroads 
			  for (Iterator<Map.Entry<point, point>> it = crossnodes.entrySet().iterator(); it.hasNext(); ) {
				  Map.Entry<point,point> entry = it.next();
				  point temp = entry.getValue();
				  if (temp.rd.size() < 2) it.remove();
				  //else i++;
			  }
			 }
		
		
		
		public void print () {
			
			System.out.print("Client: ");
			System.out.format("%f, %f\n", this.pelatis.getX_c(), this.pelatis.getY_c());
		
			System.out.print("Taxis:\n");
			for (taxi t: this.listoftaxis) 
				System.out.format("%f,%f,%f,%d\n", t.X_t, t.Y_t, t.distance, t.Id_t);
			
			System.out.print("Nodes:\n");
			for (point p: this.roads)
				System.out.format("%f,%f,%d\n", p.X_p, p.Y_p, p.Id_s);
				
			}
		}

	