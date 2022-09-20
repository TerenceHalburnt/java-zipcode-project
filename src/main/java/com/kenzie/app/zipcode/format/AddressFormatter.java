package com.kenzie.app.zipcode.format;

import java.util.HashMap;

public class AddressFormatter {

    //lookup table -HashMap
    private static HashMap<String, String> abbreviationTable;

    //initialize map - hardcode
    public static void initializeAddressMap(){
        abbreviationTable = new HashMap<>();

        abbreviationTable.put("ST", "STREET");
        abbreviationTable.put("ST.", "STREET");
        abbreviationTable.put("St", "STREET");
        abbreviationTable.put("St.", "STREET");
        abbreviationTable.put("st", "STREET");
        abbreviationTable.put("st.", "STREET");
        abbreviationTable.put("AVE", "AVENUE");
        abbreviationTable.put("AVE.", "AVENUE");
        abbreviationTable.put("Ave", "AVENUE");
        abbreviationTable.put("ave", "AVENUE");
        abbreviationTable.put("ave.", "AVENUE");
        abbreviationTable.put("RD", "ROAD");
        abbreviationTable.put("RD.", "ROAD");
        abbreviationTable.put("Rd", "ROAD");
        abbreviationTable.put("Rd.", "ROAD");
        abbreviationTable.put("rd", "ROAD");
        abbreviationTable.put("rd.", "ROAD");
        abbreviationTable.put("BLVD", "BOULEVARD");
        abbreviationTable.put("BLVD.", "BOULEVARD");
        abbreviationTable.put("Blvd", "BOULEVARD");
        abbreviationTable.put("Blvd.", "BOULEVARD");
        abbreviationTable.put("blvd", "BOULEVARD");
        abbreviationTable.put("blvd.", "BOULEVARD");

    }

    //replace address string - use the map
    public static String replaceAbbreviation(String input){

        String resultStr = input;

        //write replace logic
        for (String currentKey : abbreviationTable.keySet()) {
            resultStr = resultStr.replace(currentKey, abbreviationTable.get(currentKey));

        }


        return resultStr;
    }
    public static String formatAddress(String str){
        return str.toUpperCase().trim();
    }

    public static String formatStreetAddress(String str){
        return formatAddress((replaceAbbreviation(str)));
    }

    public static void main(String[] args) {
        AddressFormatter.initializeAddressMap();
        System.out.println(AddressFormatter.replaceAbbreviation("123 Main St."));
    }

}
