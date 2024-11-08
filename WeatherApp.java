import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URI;
import java.io.FileWriter;
import javax.swing.JTable;
class WeatherApp{

    public static void main(String[] args) throws Exception {
        Scanner scnr = new Scanner(System.in);
        System.out.println("What is the longitude and laditude of your location");
        Integer longitude = scnr.nextInt();
        Integer laditude =  scnr.nextInt();
        scnr.close();

        URI temp = new URI("https://api.openweathermap.org/data/3.0/onecall?lat="+laditude.toString()+"&lon="+longitude.toString()+"&exclude=minutely,hourly,daily,alerts&appid=f7c51f779b08d241bafbdc3feb374ed5&mode=xml");

        URL weatherData = temp.toURL();
        
        HttpURLConnection connection = (HttpURLConnection) weatherData.openConnection();
        
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuffer data = new StringBuffer();

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

            
    }
}