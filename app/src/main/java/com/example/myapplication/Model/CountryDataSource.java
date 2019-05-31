package com.example.myapplication.Model;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class CountryDataSource {

    public static final String COUNTRY_KEY = "country";
    public static final float MINIMUM_CONFIDENCE_LEVEL = 0.4F;
    public static final String DEFAULT_COUNTRY_NAME = "India";
    public static final double DEFAULT_COUNTRY_LATITUDE = 22.884004;
    public static final double DEFAULT_COUNTRY_LONGITUDE = 79.455990;
    public static final String DEFAULT_MESSAGE = "welcome";

    //Hashtable uses a key value pair
    private Hashtable<String, String> countriesAndMessages;

    public CountryDataSource(Hashtable<String, String> countriesAndMessages) {
        this.countriesAndMessages = countriesAndMessages;

    }

    public String MatchWithWords(ArrayList<String> userWords, float[] ConfidenceLevels) {
        if (userWords == null || ConfidenceLevels == null) {
            return DEFAULT_COUNTRY_NAME;

        }
        int numberCount = userWords.size();

        Enumeration<String> countries;


        for (int Index = 0; Index < numberCount && Index < ConfidenceLevels.length; Index++) {
            if (ConfidenceLevels[Index] < MINIMUM_CONFIDENCE_LEVEL)
                break;


            String Accept = userWords.get(Index);
            countries = countriesAndMessages.keys();

            while (countries.hasMoreElements()) {
                String selectedCountry = countries.nextElement();
                if (Accept.equalsIgnoreCase(selectedCountry)) {
                    return Accept;

                }

            }


        }
        return DEFAULT_COUNTRY_NAME;

    }

    public String getTheInfo(String country)
    {
        return countriesAndMessages.get(country);
    }
}
