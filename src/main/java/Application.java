import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kenzie.app.zipcode.data.dto.ZipCodeDTO;
import com.kenzie.app.zipcode.format.AddressFormatter;
import com.kenzie.app.zipcode.http.HTTPConnector;

import java.net.http.HttpClient;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        try {
            String BASE_URL = "https://api.zippopotam.us/us/";
            String urlPath;
            Scanner scanner = new Scanner(System.in);
            String recipientName;
            String streetAddress;
            String city;
            String state;
            String zipCode;

            System.out.println("Enter recipient name:");
            recipientName = scanner.nextLine();

            System.out.println("Enter street address:");
            streetAddress = scanner.nextLine();

            System.out.println("Enter city:");
            city = scanner.nextLine();

            System.out.println("Enter state: (two letter abbreviation)");
            state = scanner.nextLine();

            //System.out.println("City: " + city);

            //replace spaces with %20
            String cityURL = city.replaceAll(" ", "%20");
            //System.out.println("City: " + cityURL);

            //create URL
            urlPath = BASE_URL + state + "/" + cityURL;
            //System.out.println(urlPath);

            //call HTTP GET
            String httpResponse = HTTPConnector.makeGETRequest(urlPath);

            if (httpResponse.equals("\\{\\}")) {
                System.out.println("No valid zipcode found");
            } else {
                //use Objectmapper on the DTO
                ObjectMapper objectMapper = new ObjectMapper();

                ZipCodeDTO zipObj = objectMapper.readValue(httpResponse, ZipCodeDTO.class);
                //System.out.println(zipObj.getPlaces().get(0).getPostCode());

                //loop and ask user for zipcode if there are multiple
                if (zipObj.getPlaces().size() == 1) {
                    zipCode = zipObj.getPlaces().get(0).getPostCode();
                } else {
                    //print out menu
                    System.out.println("Choose a zipcode: ");
                    for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                        System.out.println(i + ") " + zipObj.getPlaces().get(i).getPostCode());
                    }

                    //read in choice
                    int choice = scanner.nextInt();
                    scanner.nextLine();

                    zipCode = zipObj.getPlaces().get(choice).getPostCode();
                }
                System.out.println("User choose zipcode: " + zipCode);


                //address info
                AddressFormatter.initializeAddressMap();
                System.out.println("Name: " + AddressFormatter.formatAddress(recipientName));
                System.out.println("Street Address: " + AddressFormatter.formatStreetAddress(streetAddress));
                System.out.println("City: " + AddressFormatter.formatAddress(city));
                System.out.println("State: " + AddressFormatter.formatAddress(state));
                System.out.println("Zipcode: " + zipCode);
            }


        } catch (Exception e) {
            System.out.println("Unexpected exception:" + e);
        }
    }


    public static void main_backup(String[] args) {
        try {

            final String TEST_URL = "https://api.zippopotam.us/us/ca/LOS%20ANGELES";
            //final String FAIL_TEST_URL = "https://api.zippopm.us/us/ca/LOS%20ANGELES";
            String httpResponseStr;

            //read in user input
            Scanner scanner = new Scanner(System.in);

            //format user input using AddressFormatter
            AddressFormatter.initializeAddressMap();
            AddressFormatter.replaceAbbreviation("123 Main St.");


            httpResponseStr = HTTPConnector.makeGETRequest(TEST_URL);

            System.out.println(httpResponseStr);

            //ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            //Declare DTO object and read data
            ZipCodeDTO zipObj = objectMapper.readValue(httpResponseStr, ZipCodeDTO.class);

            //Print out place name, zip code and state at first index
            System.out.println("City:" + zipObj.getPlaces().get(0).getPlaceName());
            System.out.println("ZipCode:" + zipObj.getPlaces().get(0).getPostCode());
            System.out.println("State:" + zipObj.getState());

            //Loop and print the list of zipcodes if more than 1
            for (int i = 0; i < zipObj.getPlaces().size(); i++) {
                if (zipObj.getPlaces().size() > 1) {
                    System.out.println("City:" + zipObj.getPlaces().get(i).getPlaceName());
                    System.out.println("ZipCode:" + zipObj.getPlaces().get(i).getPostCode());
                    System.out.println("State:" + zipObj.getState());
                }

            }

        } catch (Exception e) {
            System.out.println("Unexpected exception:" + e);

        }


        //failure case
        //System.out.println("failure case: ");
        //httpResponseStr = HTTPConnector.makeGETRequest(FAIL_TEST_URL);
        //System.out.println(httpResponseStr);

    }
}
