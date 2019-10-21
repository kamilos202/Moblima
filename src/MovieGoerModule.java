import java.io.*;
public class MovieGoerModule {


	
	public void listMovies()
	{


        try {
        	File file = new File("movies.txt"); 
        	  
        	  BufferedReader br = new BufferedReader(new FileReader(file)); 
        	  
        	  String st; 
        	  while ((st = br.readLine()) != null) {
        	    System.out.println(st); 
        	  } 

        } catch (Exception e) {
        	e.printStackTrace();
        	
        }
	}
}
