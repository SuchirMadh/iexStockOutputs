import netscape.javascript.JSObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.json.JSONObject;


public class Company {
    String ticker;
    String companyName;
    String quantity;
    String costBasis;
    float price;

    float dayChange;
    float dayChangePercent;

    long marketCap;
    float peRatio;
    String allData;


    private static HttpURLConnection connection;

    /**
     * initializes a stock of given company
     * @param tick ticker symbol
     * @param cName name of company
     * @param quant quantity of stock bought
     * @param costBase cost basis
     */
    public Company(String tick, String cName, String quant, String costBase){
        ticker = tick;
        companyName = cName;
        quantity = quant;
        costBasis = costBase;


        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        //break
        try {
            //sk_a1279da1a0124ed4a70b366f7ef63c02
            URL url = new URL(
                    "https://cloud.iexapis.com/v1/stock/" + ticker + "/quote?token=pk_2651739bfa384218ae2b69959adb6c2a");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            allData = responseContent.toString();
            if (!responseContent.toString().contains("Unknown symbol")) {
                JSONObject obj = new JSONObject(responseContent.toString());
                this.peRatio = obj.getFloat("peRatio");
                this.marketCap = obj.getLong("marketCap");
                this.price = obj.getInt("latestPrice");
                this.dayChange = obj.getFloat("change");
                this.dayChangePercent = obj.getFloat("changePercent");

            }

        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
    }

    /**
     * gets all data of company from IEXCloud
     * @return string of all data
     */
    /*public String getData() {

        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();


        try {
            //sk_a1279da1a0124ed4a70b366f7ef63c02
            URL url = new URL(
                    "https://cloud.iexapis.com/v1/stock/" + ticker + "/quote?token=pk_2651739bfa384218ae2b69959adb6c2a");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }

            if (!responseContent.toString().contains("Unknown symbol")) {
                JSONObject obj = new JSONObject(responseContent.toString());
                this.peRatio = obj.getFloat("peRatio");
                this.marketCap = obj.getInt("marketCap");
                this.price = obj.getInt("latestPrice");
                this.dayChange = obj.getFloat("change");
                this.dayChangePercent = obj.getFloat("changePercent");
            }

            return(responseContent.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return "";
    }

    *//**
     * gets current price of stock
     * @return string of stock price
     *//*
    public String getCurrentPrice(){
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();

        try {
            //sk_a1279da1a0124ed4a70b366f7ef63c02
            URL url = new URL(
                    "https://cloud.iexapis.com/stable/stock/"+ ticker +"/quote/latestPrice?token=pk_2651739bfa384218ae2b69959adb6c2a");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int status = connection.getResponseCode();

            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                reader.close();
            }
            else {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while ((line = reader.readLine()) != null) {
                    responseContent.append(line);
                }
                reader.close();
            }
            return(responseContent.toString());
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            connection.disconnect();
        }
        return "";

    }*/
    public void printInfo(){
        System.out.println("Company Name:" + getCompanyName() + "\r\n" +
                "Symbol:" + getTicker() + "\r\n" +
                "Quantity:" + getQuantity() + "\r\n" +
                "Latest Price:" + getPrice() + "\r\n" +
                "Day Change:" + getDayChange() + "\r\n" +
                "Day Change %:" + getDayChangePercent() + "\r\n" +
                "Market Value:" + getMarketCap() + "\r\n" +
                "PE Ratio:" + getPeRatio());
    }

    public String commaInfo(){
        return ("Company Name: " + getCompanyName() + ", " +
                "Symbol: " + getTicker() + ", " +
                "Quantity: " + getQuantity() + ", " +
                "Latest Price: " + getPrice() + ", " +
                "Day Change: " + getDayChange() + ", " +
                "Day Change %: " + getDayChangePercent()  + ", " +
                "Market Value: " + getMarketCap()  +  ", " +
                "PE Ratio: " + getPeRatio() + "\r\n");
    }

    public File csvInfo() throws IOException {
        File csv = new File(System.getProperty("user.dir"), "file.csv");
        if (!csv.exists()) {
            csv.createNewFile();
        }
        BufferedWriter write = new BufferedWriter(new FileWriter(csv));
        write.append(" Company Name: " + getCompanyName() + ", " +
                "Symbol: " + getTicker() + ", " +
                "Quantity: " + getQuantity() + ", " +
                "Latest Price: " + getPrice() + ", " +
                "Day Change: " + getDayChange() + ", " +
                "Day Change %: " + getDayChangePercent()  + ", " +
                "Market Value: " + getMarketCap()  +  ", " +
                "PE Ratio: " + getPeRatio());
        write.close();
        return csv;
    }

    public String getTicker() {
        return ticker;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getCostBasis() {
        return costBasis;
    }

    public float getPrice() { return price; }

    public float getDayChange() { return dayChange; }

    public float getDayChangePercent() { return dayChangePercent; }

    public long getMarketCap(){ return marketCap; }
    public float getPeRatio() { return peRatio; }
    public String getAllData(){ return allData; }

    public static void main(String[] args) throws IOException {
        /*stockOutput csv = new stockOutput();
        csv.printCompanyInfo();
        System.out.println(csv.getCompanies().get(1).getCurrentPrice());
        System.out.println(csv.getTotalStockValue());
        System.out.println(csv.getTotalCost());
        System.out.println(csv.getPortfolioProfits());*/
        Company Cisco = new Company("CSCO","CISCO SYSTEMS INC","150","10000");
        Cisco.printInfo();
        Cisco.csvInfo();
        System.out.println(Cisco.getAllData());

    }
}
