package vaxapp_package;
//Simul
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Recorder {
	 

	//method to write data into the txt file
    public static ArrayList<String> submitRecord(String d, String cname, String p, String m, String a ) throws IOException {
    	
    	//converting string doses to Integer
    	
    	
//    	Integer md = Integer.parseInt(m);
//    	Integer az = Integer.parseInt(a);
    	
        boolean validate = true;
        //relative path of the text file
        String filePath = "src/vaxapp_package/vaxdata.txt";
        //defining an arraylist to store error and success messages and return them back to the calling class
        ArrayList<String> msg = new ArrayList<String>();
        //concatenating the input values (and separating by comma) to a variable
        String myString = d + "," + cname + "," + p + "," + m + "," + a;
        
        
        // validate input
        
        //validate date (can't be null plus need to follow format of mm/dd/yyyy
        if (d.matches("\\d{2}\\/\\d{2}\\/\\d{4}")){
        	String[] dateUnits = d.split("/");
         	int[] dateUnitsInt = new int[dateUnits.length];
         	for(int i=0;i<dateUnits.length;i++) {
         		dateUnitsInt[i] = Integer.parseInt(dateUnits[i]);
         	}
         	int mm = dateUnitsInt[0];
         	int dd = dateUnitsInt[1];
         	int yyyy = dateUnitsInt[2];
         	if(mm<=0 || mm>12 || dd<=0 || dd>31 || yyyy<2020 || yyyy>Calendar.getInstance().get(Calendar.YEAR)) {
         		msg.add("Invalid date: (mm/dd/yyyy) and year must > 2019.");
                 validate=false;
         	} 
        }else{
        	msg.add("Invalid date: (mm/dd/yyyy) and year must > 2019.");
            validate=false;
        }
            
    	//end of date validation
    	//validate city name
        if(cname.isBlank()){msg.add("The City Name field cannot be empty!");validate=false;}
        
        //validate vaccine doses
        if(!p.isEmpty()) {
        	try {Integer pz = Integer.parseInt(p);
    		if(pz<0) {msg.add("The Pfizer Dose cannot be less than 0!");validate=false;}}
        	catch(Exception e) {
        		msg.add("Pfizer dose must be an integer");
        	}
    		
    	}else {
    		msg.add("The Pfizer Dose cannot be empty!");validate=false;
    	}
        if(!m.isEmpty()) {
        	try {Integer md = Integer.parseInt(m);
    		if(md<0) {msg.add("The Moderna Dose cannot be less than 0!");validate=false;}}
        	catch(Exception e) {
        		msg.add("Moderna dose must be an integer");
        	}
    	}else {
    		msg.add("The Moderna Dose cannot be empty!");validate=false;
    	}
        if(!a.isEmpty()) {
        	try {Integer az = Integer.parseInt(a);
    		if(az<0) {msg.add("The Astrazeneca Dose cannot be less than 0!");validate=false;}}
        	catch(Exception e) {
        		msg.add("Astrazeneca dose must be an integer");
        	}
    		
    	}else {
    		msg.add("The Astrazeneca Dose cannot be empty!");validate=false;
    	}
        
        

        // validation is successful, write data to txt file (3 scenarios)
        if(validate){
            //create the txt file if it doesn't exits
        	File myFile = new File(filePath);
//        	Scenario 1
//        	if file doesn't exist, create it
//        	write the first data in the txt file 
        	if(!myFile.isFile()) {
        		PrintWriter output = new PrintWriter(myFile);
        		output.println(myString);
        		msg.add("Record has been added!");
        		output.close();        		
        	}
//        	Scenario 2
//        	appending data to the file (2nd input and onwards)
        	else 
        	{
        		Scanner input = new Scanner(myFile);
        		ArrayList<String> data = new ArrayList<String>();
        		String matchedLine;
        		//replace flag for the condition where value needs to be replaced (with matching date and city name)
        		boolean replace = false;
        		//doNothing flag is for the case when the data being entered matches with existing data in the txt file, so do nothing
        		boolean doNothing = false;
        		//storing each line from the txt file into an arraylist data
        		while(input.hasNext()) {
        			data.add(input.next());
        		}
        		//check if the input date and city matches with the date and city from the line read from the file
        		for(int i=0;i<data.size();i++) {
//        			for a single line, storing all the contents separated by comma into an array
        			String[] values = data.get(i).split(",");
        			if(values[0].equals(d) && values[1].equals(cname)) {
//        				replacing the line with the new data in the arraylist data (not the txt file)
        				data.set(i,myString);
        				//set flag duplicate as true which means that there exits a record(line) in the txt file with the same date
        				replace = true;
        			}
//        			this is when all the fields match i.e. record already exists so don't do anything :)
        			if(values[0].equals(d) && values[1].equals(cname) && values[2].equals(p) && values[3].equals(m)&& values[4].equals(a)) {
        				replace = false;
        				doNothing = true;
        				
        			}
        		}
//        		replacing/updating record(line) logic - store all lines into an arraylist except for the line where the date matches,
//        		then replace the txt file with this data from the arraylist
        		if(replace && !doNothing) {          		
    				//replace the updated content from the arraylist data to the txt file
        			PrintWriter myReplaceFile = new PrintWriter(filePath);
            		try (PrintWriter outputAfterReplace = new PrintWriter(myReplaceFile)) {
    					for(int i=0;i<data.size();i++) {
    						outputAfterReplace.println(data.get(i));
    					}
    					outputAfterReplace.close();				}
            		msg.add("Record has been replaced!");
            		
        		}
//        		Scenario 3
//        		append the record if the input date data doesn't exist in the file
        		if(!replace && !doNothing) {    		
        				FileWriter myAppendFile = new FileWriter("src/vaxapp_package/vaxdata.txt", true);
                		try (PrintWriter outputAppend = new PrintWriter(myAppendFile)) {
        					outputAppend.println(myString);
        					outputAppend.close();				}
                		msg.add("Record has been appended!");
        			}     
        		input.close();
        	}
        }
        if(msg.isEmpty()) {msg.add("Record already exists!");}
        
        return msg;

    }

    //method to search data based on city and date
    public static ArrayList<String> searchData(String c, String d) throws FileNotFoundException {
    	boolean found = false;
    	ArrayList<String> searchResult = new ArrayList<String>();
    	//validation
    	if(c.isBlank()) {
    		searchResult.add("Please select a city!");
    	}else if(d.isBlank()){
    		searchResult.add("Please select a date!"); 
    	}else {
    		try {
    			//search for the doses for each vaccine type
        		File myFile = new File("src/vaxapp_package/vaxdata.txt");
        		Scanner input = new Scanner(myFile);
        		ArrayList<String> data = new ArrayList<String>();
        		String matchedLine;
        		
        		while(input.hasNext()) {
        			data.add(input.next());
        		}
        		
        		for(int i=0;i<data.size();i++) {
        			matchedLine = data.get(i);
        			String[] values = matchedLine.split(",");
        			if(values[0].equals(d) && values[1].equals(c)) {
        				for(String val : values) {
        					searchResult.add(val);
        					found = true;
        				}
        			}
        		}
        		input.close();
    			
    		}
    		catch(Exception e) {
    			searchResult.add("There is not a single record available!");
    			found = true;
    		}
    	}
    	if(!found) {
    		searchResult.add("Record not found!");
    	}
//    	calling the function in the vaxapp method that displays the search result back to the frame
        return searchResult;
    }
}
