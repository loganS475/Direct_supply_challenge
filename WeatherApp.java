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
        Scanner scnr = new Scanner(System.in);
        System.out.println("What is the longitude and laditude of your location");
        Integer longitude = scnr.nextInt();
        Integer laditude =  scnr.nextInt();
        scnr.close();

        URI temp = new URI("https://api.openweathermap.org/data/3.0/onecall?lat="+laditude.toString()+"&lon="+longitude.toString()+"&exclude=minutely,hourly,daily,alerts&appid=f7c51f779b08d241bafbdc3feb374ed5&units=imperial");

        URL weatherData = temp.toURL();
        
        HttpURLConnection connection = (HttpURLConnection) weatherData.openConnection();
        
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        StringBuffer data = new StringBuffer();
        String info;

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
        info.replace('{',' ');

        String [] split1 = info.split(",");
        String [] catagories = new String [split1.length];
        String [] numbers = new String [split1.length];

        for(int i=0;i<split1.length;i++){
            String [] temp1 = split1[i].split(":");
            catagories[i] = temp1[0];
            numbers[i]=temp1[1];
            
        }

        for(int i=0;i<numbers.length;i++){
            int index;
            char [] temp2;
            if(numbers[i].contains("}")){
                index = numbers[i].indexOf("}");
                temp2 = numbers[i].toCharArray();
                temp2[index]=' ';
                numbers[i] = temp2.toString();
            }
            if(numbers[i].contains("{")){
                index = numbers[i].indexOf("{");
                temp2 = numbers[i].toCharArray();
                temp2[index]=' ';
                numbers[i] = temp2.toString();
            }
            if(numbers[i].contains("[")){
                index = numbers[i].indexOf("[");
                temp2 = numbers[i].toCharArray();
                temp2[index]=' ';
                numbers[i] = temp2.toString();
            }
            if(numbers[i].contains("]")){
                index = numbers[i].indexOf("]");
                temp2 = numbers[i].toCharArray();
                temp2[index]=' ';
                numbers[i] = temp2.toString();
            }
            
        }

        String [][] stats = {numbers};

        JTable table = new JTable(stats,catagories);

        JScrollPane scrollPane = new JScrollPane(table);

        JFrame frame = new JFrame("Simple JTable Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(scrollPane);
        frame.setSize(300, 500);
        frame.setVisible(true);
    }
}