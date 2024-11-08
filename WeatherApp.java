import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.JFrame;
import java.util.Arrays;
class WeatherApp{

    public static void main(String[] args) throws Exception {
        // this takes in an input for longitude and latitude
        Scanner scnr = new Scanner(System.in);
        System.out.println("What is the longitude and laditude of your location");
        System.out.println("Usage: <longitude> <laditude> please use doubles");
        Double longitude = scnr.nextDouble();
        Double laditude =  scnr.nextDouble();
        scnr.close();

        // All of the URL constructors are depreceated so a URI is needed
        URI temp = new URI("https://api.openweathermap.org/data/3.0/onecall?lat="+laditude.toString()+"&lon="+longitude.toString()+"&exclude=minutely,hourly,daily,alerts&appid=f7c51f779b08d241bafbdc3feb374ed5&units=imperial");

        URL weatherData = temp.toURL();

        // sets up connection to API and variables necessary for reading
        HttpURLConnection connection = (HttpURLConnection) weatherData.openConnection();
        
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuffer data = new StringBuffer();
        String info;

        //makes sure connection is good and processes data from API
        if(responseCode == HttpURLConnection.HTTP_OK){

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;

            while((input=reader.readLine())!= null){
                data.append(input);
            }

            System.out.println(data.toString());
            

        }else{
            System.out.println("Error with response code");
        }
        
        info = data.toString();
        //spilts API input into the rows and columns for table
        String [] split1 = info.split(",");
        String [] columns = new String [split1.length];
        String [] rows = new String [split1.length];

        for(int i=0;i<split1.length;i++){
            String [] temp1 = split1[i].split(":");
            columns[i] = temp1[0];
            rows[i]=temp1[1];
            
        }
        //removes any bracket notation from API
        for(int i=0;i<rows.length;i++){
            int index;
            char [] temp2;
            if(rows[i].contains("}")){
                index = rows[i].indexOf("}");
                temp2 = rows[i].toCharArray();
                temp2[index]=' ';
                rows[i] = new String(temp2);
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
        String [][] stats = {rows};

        JTable table = new JTable(stats,columns);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Weather data for"+longitude.toString()+" "+laditude.toString());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(300, 500);
        frame.setVisible(true);
    }
}