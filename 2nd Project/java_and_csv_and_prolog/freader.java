import java.util.*;
import java.io.*;

public class freader {
	
	public client pelatis;
	public  ArrayList<point> nodes = new ArrayList<point>();
	public  ArrayList<taxi> listoftaxis= new ArrayList<taxi>();
	public ArrayList<traffic> trafficinfo = new ArrayList<traffic>();
	public ArrayList<road>  roadinfo = new ArrayList<road>();

	public freader ()  {
		

		try {
			
			File file1 = new File("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/client2.csv");
			Scanner f1 = new Scanner(file1);
			
			String line = f1.nextLine();
			line = f1.nextLine();
			String[] a = line.split(",");
			
			this.pelatis = new client(Double.parseDouble(a[0]),Double.parseDouble(a[1]),Double.parseDouble(a[2]),Double.parseDouble(a[3]), a[4], Integer.parseInt(a[5]), a[6], Integer.parseInt(a[7]));
			f1.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			
	        ArrayList<String> temp = new ArrayList<>();

			String line2;
			Scanner file2 = new Scanner(new File("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/taxis2.csv"));
		    
			line2= file2.nextLine();
			line2= file2.nextLine();

			while (file2.hasNextLine()) {
				temp.add(line2);
				line2 = file2.nextLine();
            }
			
			temp.add(line2);
			
            Iterator<String> taxi_iterator = temp.iterator();

			while (taxi_iterator.hasNext()) {
                String[] a = taxi_iterator.next().split(",");
                String[] lang;
                lang = a[5].split("\\|");
                String[] cap;
                cap = a[4].split("-");

                taxi t = new taxi(Double.parseDouble(a[0]), Double.parseDouble(a[1]), Integer.parseInt(a[2]), a[3], cap, lang, Double.parseDouble(a[6]), a[7], a[8]);
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
			Scanner file3 = new Scanner(new File("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/nodes.csv"));
			
			ArrayList<String> temp1 = new ArrayList<>();
			line3 = file3.nextLine();
            line3 = file3.nextLine();
            while (file3.hasNextLine()) {
                temp1.add(line3);
                line3 = file3.nextLine();
                //j++;
            }
            
            temp1.add(line3);
			Iterator<String> points_iterator = temp1.iterator();

			while (points_iterator.hasNext()) {
                String[] a = points_iterator.next().split(",");
                this.nodes.add(new point(Double.parseDouble(a[0]), Double.parseDouble(a[1]),Integer.parseInt(a[2]), a[3]));
			}
			 
			 
			 file3.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	

	
	try {
		
		String line4;
		Scanner file4 = new Scanner(new File("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/traffic.csv"));
		
		ArrayList<String> temp2 = new ArrayList<>();
		line4 = file4.nextLine();
        line4 = file4.nextLine();
        while (file4.hasNextLine()) {
            temp2.add(line4);
            line4 = file4.nextLine();
        }
        
        temp2.add(line4);
		Iterator<String> traffic_iterator = temp2.iterator();

		while (traffic_iterator.hasNext()) {
            String[] a = traffic_iterator.next().split(",");
            if (a.length == 3) {        	
        	   	   String ti= a[2].replaceAll("\\|", ",");
        		   this.trafficinfo.add(new traffic(Integer.parseInt(a[0]),ti));
        		   }
		}
 
		 
		 file4.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace(); }
	


try {
		
		String line5;
		Scanner file5 = new Scanner(new File("/home/orestis/Desktop/TN2018-T2/java_and_csv_and_prolog/lines.csv"));
		
		ArrayList<String> temp3 = new ArrayList<>();
		line5 = file5.nextLine();
        line5 = file5.nextLine();
		//int j=0;
        while (file5.hasNextLine()) {
            temp3.add(line5);
            line5 = file5.nextLine();
            //j++;
        }
        
        temp3.add(line5);
		Iterator<String> road_iterator = temp3.iterator();
		while (road_iterator.hasNext()) {
			String b = road_iterator.next();
			String c = b.replace("%", "");
			String[] a = c.split(",");
			if (a.length > 1 && a[1].length() == 0) continue;
			StringBuilder info = new StringBuilder();
			info.append(a[0]);
			int j=1;
			while (j < a.length) {
				info.append(",");
				if (a[j].length() == 0 || j== 2 )
					info.append("0");
				else 
					info.append(a[j]);
				j++;
			}
			while (j < 18) {
				info.append(",");
				info.append("0");
				j++;
			}
			this.roadinfo.add(new road(Integer.parseInt(a[0]),info.toString() ));
		}		 
		 file5.close();
		
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace(); }
	}
	
	public void print () {
		
		System.out.print("Client: ");
		System.out.format("%f, %f, %f, %f, %s, %d, %s, %d\n", this.pelatis.getX_c(), this.pelatis.getY_c(), this.pelatis.getX_dest(), this.pelatis.getY_dest(), this.pelatis.getTime(), this.pelatis.getPersons(), this.pelatis.getLanguage(), this.pelatis.getLuggage());
	
}
}


