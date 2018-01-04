package com.framework.ux.utils.chapter10;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Carl Cocchiaro
 *
 * TestNG JSON DataProvider Utility Class
 *
 */
public class JSONDataProvider {
    public static String dataFile = "";
    public static String testCaseName = "NA";

    public JSONDataProvider() throws Exception {
    }

    /**
     * fetchData method to retrieve test data for specified method
     *
     * @param method
     * @return Object[][]
     * @throws Exception
     */
    @DataProvider(name = "fetchData_JSON")
    public static Object[][] fetchData(Method method) throws Exception {
        Object rowID, description;
        Object[][] result;
        testCaseName = method.getName();
        List<JSONObject> testDataList = new ArrayList<JSONObject>();
        JSONArray testData = (JSONArray) extractData_JSON(dataFile).get(method.getName());

        for ( int i = 0; i < testData.size(); i++ ) {
            testDataList.add((JSONObject) testData.get(i));
        }

        // include Filter
        if ( System.getProperty("includePattern") != null ) {
            String include = System.getProperty("includePattern");
            List<JSONObject> newList = new ArrayList<JSONObject>();
            List<String> tests = Arrays.asList(include.split(",", -1));

            for ( String getTest : tests ) {
                for ( int i = 0; i < testDataList.size(); i++ ) {
                    if ( testDataList.get(i).toString().contains(getTest) ) {
                        newList.add(testDataList.get(i));
                    }
                }
            }

            // reassign testRows after filtering tests
            testDataList = newList;
        }

        // exclude Filter
        if ( System.getProperty("excludePattern") != null ) {
            String exclude =System.getProperty("excludePattern");
            List<String> tests = Arrays.asList(exclude.split(",", -1));

            for ( String getTest : tests ) {
                // start at end of list and work backwards so index stays in sync
                for ( int i = testDataList.size() - 1 ; i >= 0; i-- ) {
                    if ( testDataList.get(i).toString().contains(getTest) ) {
                        testDataList.remove(testDataList.get(i));
                    }
                }
            }
        }

        // create object for dataprovider to return
        try {
            result = new Object[testDataList.size()][testDataList.get(0).size()];

            for ( int i = 0; i < testDataList.size(); i++ ) {
                rowID = testDataList.get(i).get("rowID");
                description = testDataList.get(i).get("description");
                result[i] = new Object[] { rowID, description, testDataList.get(i) };
            }
        }

        catch(IndexOutOfBoundsException ie) {
            result = new Object[0][0];
        }

        return result;
    }

    /**
     * extractData_JSON method to get JSON data from file
     *
     * @param file
     * @return JSONObject
     * @throws Exception
     */
    public static JSONObject extractData_JSON(String file) throws Exception {
        FileReader reader = new FileReader(file);
        JSONParser jsonParser = new JSONParser();

        return (JSONObject) jsonParser.parse(reader);
    }

}
