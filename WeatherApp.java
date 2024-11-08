import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
class WeatherApp{

    public static void main(String[] args) throws Exception {
        // this takes in an input for longitude and latitude
        Scanner scnr = new Scanner(System.in);
        System.out.println("What is the longitude and laditude of your location");
        System.out.println("Usage: <longitude> <laditude> please use Doubles");
        Double longitude = scnr.nextDouble();
        Double laditude =  scnr.nextDouble();
        scnr.close();
        //checks to make sure user input is valid
        if((longitude > 180.00) || (longitude < -180.00)){
            throw new IllegalArgumentException("Longitude must be between -180.00 and 180.00");
        }
        if((laditude > 180.00) || (laditude < -180.00)){
            throw new IllegalArgumentException("Laditude must be between -180.00 and 180.00");
        }

        // All of the URL constructors are depreceated so a URI is needed
        URI temp = new URI("https://api.openweathermap.org/data/3.0/onecall?lat="+laditude.toString()+"&lon="+longitude.toString()+"&exclude=minutely,hourly,daily,alerts&appid=f7c51f779b08d241bafbdc3feb374ed5&units=imperial");

        URL weatherData = temp.toURL();
        // sets up connection to API and variables necessary for reading
        HttpURLConnection connection = (HttpURLConnection) weatherData.openConnection();
        
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuffer data = new StringBuffer();
        String info;

        //makes sure connection is good and requests data from API
        if(responseCode == HttpURLConnection.HTTP_OK){

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;

            while((input=reader.readLine())!= null){
                data.append(input);
            }
            

        }else{
            System.out.println("Error with retrevial of data"+"/n" +"Error number:"+ responseCode);
        }
        // I know that using JSON would have been cleaner and easier to use
        // However I was having trouble with some of the necessary packages
        // So instead I'm reformatting the string
        info = data.toString();
        //creates arrays to store and reformat data
        String [] split1 = info.split(",");
        String [] columns = new String [split1.length];
        String [] rows = new String [split1.length];
        //splits data into columns and rows
        for(int i=0;i<split1.length;i++){
            String [] temp1 = split1[i].split(":");
            columns[i] = temp1[0];
            rows[i]=temp1[1];
            
        }
        //removes any bracket notation from row data
        for(int i=0;i<rows.length;i++){
            int index;
            char [] temp2;
            if(rows[i].contains("}")){
                index = rows[i].indexOf("}");
                temp2 = rows[i].toCharArray();
                temp2[index]=' '; // since brackets are usually at beginning or end space character won't make an obvious visible difference
                rows[i] = new String(temp2); //allows string conversion without including format of array
            }
            if(rows[i].contains("{")){
                index = rows[i].indexOf("{");
                temp2 = rows[i].toCharArray();
                temp2[index]=' ';
                rows[i] = new String(temp2);
            }
            if(rows[i].contains("[")){
                index = rows[i].indexOf("[");
                temp2 = rows[i].toCharArray();
                temp2[index]=' ';
                rows[i] = new String(temp2);
            }
            if(rows[i].contains("]")){
                index = rows[i].indexOf("]");
                temp2 = rows[i].toCharArray();
                temp2[index]=' ';
                rows[i] = new String(temp2);
            }
            
        }
        //removes any bracket notation from column data
        for(int i=0;i<columns.length;i++){
            int index;
            char [] temp2;
            if(columns[i].contains("}")){
                index = columns[i].indexOf("}");
                temp2 = columns[i].toCharArray();
                temp2[index]=' '; 
                columns[i] = new String(temp2);
            }
            if(columns[i].contains("{")){
                index = columns[i].indexOf("{");
                temp2 = columns[i].toCharArray();
                temp2[index]=' ';
                columns[i] = new String(temp2);
            }
            if(columns[i].contains("[")){
                index = columns[i].indexOf("[");
                temp2 = columns[i].toCharArray();
                temp2[index]=' ';
                columns[i] = new String(temp2);
            }
            if(columns[i].contains("]")){
                index = columns[i].indexOf("]");
                temp2 = columns[i].toCharArray();
                temp2[index]=' ';
                columns[i] = new String(temp2);
            }
        }

        //table set up
        String [][] stats = {rows}; // JTable only works with a 2D array and a 1D array 

        JTable table = new JTable(stats,columns);

        JScrollPane scrollPane = new JScrollPane(table);// allows ability to scroll through table

        JFrame frame = new JFrame("Weather data for"+"longitude:"+longitude.toString()+" latitude: "+laditude.toString());//sets table name
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(300, 500);
        frame.setVisible(true);
    }
}