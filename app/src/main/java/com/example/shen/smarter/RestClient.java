package com.example.shen.smarter;
import android.util.Log;

import com.example.shen.smarter.models.Credentials;
import com.example.shen.smarter.models.Eusage;
import com.example.shen.smarter.models.Resident;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class RestClient {
    private static final String BASE_URI = "http://192.168.1.100:8080/SmartER/webresources";

    public static String findAllCredentials(){
        final String methodPath = "/smarter.credentials";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByUsername(String username){
        String user = username;
        final String methodPath = "/smarter.credentials/findByUsername/" + user;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createCredentials(Credentials credential){
        final String methodPath = "/smarter.credentials/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        try{
            Gson gson = new Gson();
            String stringCredentialJson = gson.toJson(credential);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");

            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createResidents(Resident resident){
        final String methodPath = "/smarter.resident/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        try{
            Gson gson = new Gson();
            String stringCredentialJson = gson.toJson(resident);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");

            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createEusage(Eusage eusage){
        final String methodPath = "/smarter.eusage/";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        try{
            Gson gson = new Gson();
            String stringCredentialJson = gson.toJson(eusage);
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to POST
            conn.setRequestMethod("POST");
            //set the output to true
            conn.setDoOutput(true);
            //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCredentialJson.getBytes().length);
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");

            //send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCredentialJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }


    public static String getResid(){
        final String methodPath = "/smarter.resident/maxID";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getUsageid(){
        final String methodPath = "/smarter.eusage/maxID";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String validateUser(String username, String password){
        String user = username;
        final String methodPath = "/smarter.credentials/validateUser/" + user + "/" + password;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getFname(String username){
        String user = username;
        final String methodPath = "/smarter.credentials/getFname/" + user;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getAddress(String username){
        String user = username;
        final String methodPath = "/smarter.credentials/getAddress/" + user;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getPostCode(String username){
        String user = username;
        final String methodPath = "/smarter.credentials/getPostCode/" + user;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getAllAddress(){
        final String methodPath = "/smarter.resident/getAllAddress";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getHourlyUsage(){
        final String methodPath = "/smarter.eusage/hourlyPowerUsageForAllAppliance/2018-03-14/00";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getAllHourlyUsage(String resid, String date, String viewVariable){
            final String methodPath = "/smarter.eusage/hourlyOrDailyUsage/" + resid + "/" + date + "/" + viewVariable;
            //initialise
            URL url = null;
            HttpURLConnection conn = null;
            String textResult="";
            //Making HTTP request
            try{
                url = new URL(BASE_URI + methodPath);
                //open the connection
                conn = (HttpURLConnection)url.openConnection();
                //set the timeout
                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                //set the connection method to GET
                conn.setRequestMethod("GET");
                //add http headers to set your response type to json
                conn.setRequestProperty("Content-Type","application/json");
                conn.setRequestProperty("Accept","application/json");
                //read the response
                Scanner inStream = new Scanner(conn.getInputStream());
                //read the input stream and store it as string
                while (inStream.hasNextLine()){
                    textResult += inStream.nextLine();
                }
            } catch (Exception e){
                e.printStackTrace();
            } finally {
                conn.disconnect();
            }
            return textResult;


    }

    public static String getDailyUsage(){
        final String methodPath = "/smarter.eusage/dailyUsageOfAppliances/2018-03-14";
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult="";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String getDailyForUser(String username, String date){
        final String methodPath = "/smarter.eusage/DailyUsage/" + username + "/" + date;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByUserId(String userId){
        String user = userId;
        final String methodPath = "/smarter.resident/findByResid/" + user;
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        //Making HTTP request
        try{
            url = new URL(BASE_URI + methodPath);
            //open the connection
            conn = (HttpURLConnection)url.openConnection();
            //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            //set the connection method to GET
            conn.setRequestMethod("GET");
            //add http headers to set your response type to json
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("Accept","application/json");
            //read the response
            Scanner inStream = new Scanner(conn.getInputStream());
            //read the input stream and store it as string
            while (inStream.hasNextLine()){
                textResult += inStream.nextLine();
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

}
