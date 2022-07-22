import java.io.*;
import java.util.ArrayList;

public class stockOutput {
        /*ArrayList<String> ticker = new ArrayList<String>();
        ArrayList<String> companyName = new ArrayList<String>();
        ArrayList<String> quantity = new ArrayList<String>();
        ArrayList<String> costBasis = new ArrayList<String>();*/

        ArrayList<Company> Companies = new ArrayList<Company>();

        /**
         * reads csv file for company info, inputs info into array list
         */
        public stockOutput(){
                StringBuilder sb = new StringBuilder();
                sb.append(System.getProperty("user.dir"));
                sb.append("/src/programming.csv");
                String path = sb.toString();
                String line;

                try{
                        BufferedReader buffRead = new BufferedReader(new FileReader(path));

                        while((line = buffRead.readLine()) != null){
                                String[] values = line.split(",");

                                Companies.add(new Company(values[0], values[1],
                                        values[2], values[3]));
                                /*ticker.add(values[0]);
                                companyName.add(values[1]);
                                quantity.add(values[2]);
                                costBasis.add(values[3]);*/

                        }
                }
                catch(FileNotFoundException e){
                        e.printStackTrace();
                }
                catch(IOException e){
                        e.printStackTrace();
                }

        }

        /**
         * prints all info of companies listed
         */
        public void printCompanyInfo(){
                for(int i = 0; i < Companies.size(); i++){
                        System.out.println(Companies.get(i).getAllData());
                }
        }

        /**
         * prints the combined value of the portfolio(quantity * current stock price)
         * Note: i is set to 1 in the for loop because the ticker symbol for the first company
         *      produces an unknown
         */
        public float getTotalStockValue(){
                float total = 0;
                for(int i = 1; i < Companies.size(); i++){
                        float price = Companies.get(i).getPrice();
                        float stockValue = price * Integer.parseInt(Companies.get(i).getQuantity());
                        total =  total + stockValue;
                }
                return (total);
        }

        /**
         * prints the combined value of the cost basis for each company
         * Note: i is set to 1 in the for loop because the ticker symbol for the first company
         *      produces an unknown
         */
        public float getTotalCost(){
                float cost = 0;
                for(int i = 1; i < Companies.size(); i++){
                        cost =  cost + Float.parseFloat(Companies.get(i).getCostBasis());
                }
                return (cost);
        }

        public File returnCsv() throws IOException {
                File file = new File(System.getProperty("user.dir"), "file.csv");
                if (!file.exists()) {
                        file.createNewFile();
                }
                BufferedWriter write = new BufferedWriter(new FileWriter(file));
                for (int i = 1; i < Companies.size(); i++) {
                        write.append(Companies.get(i).commaInfo());
                }
                write.append("Total Value:" +getTotalStockValue() + ", " +
                        "Total Cost:" + getTotalCost() +
                        "Total Profits: " + getPortfolioProfits() + ", " +
                        "\n");
                write.close();

                return file;
        }

        /**
         * gets the total profit of portfolio
         * @return stock valuation subtracted by the cost
         */
        public float getPortfolioProfits(){
                return getTotalStockValue()-getTotalCost();
        }
        public ArrayList<Company> getCompanies() {
                return Companies;
        }
}
