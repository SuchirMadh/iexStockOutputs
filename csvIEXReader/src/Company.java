import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Company {
    String ticker;
    String companyName;
    String quantity;
    String costBasis;
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
    }

    /**
     * gets all data of company from IEXCloud
     * @return string of all data
     */
    public String getData() {

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

    /**
     * gets current price of stock
     * @return string of stock price
     */
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

    public static void main(String[] args){
        stockOutput csv = new stockOutput();
        csv.printCompanyInfo();
        System.out.println(csv.getCompanies().get(1).getCurrentPrice());
        System.out.println(csv.getTotalStockValue());
        System.out.println(csv.getTotalCost());
        System.out.println(csv.getPortfolioProfits());
    }
}
