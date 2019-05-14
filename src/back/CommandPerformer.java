package back;


import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static back.SqlConnector.DATE_FORMAT;

public class CommandPerformer {

    private static SqlConnector connector = SqlConnector.get();

    public static String purchase(
            Date date,
            double amount,
            String currency,
            String name
    ) throws SQLException, IOException {
        Model model = new Model(date, amount, currency, name);
        connector.insert(model);
        return all();
    }

    public static String all() throws SQLException, IOException {
        List<Model> models = connector.select();
        Map<Date, List<Model>> groups = models.stream().collect(Collectors.groupingBy(Model::getDate));
        StringBuilder result = new StringBuilder();

        for(Map.Entry<Date, List<Model>> item : groups.entrySet()){
            result.append(item.getKey()).append("\n");

            for(Model model : item.getValue()){
                result.append(model.getAll()).append("\n");
            }

            result.append("\n");
        }

        return String.valueOf(result);
    }

    public static String clear(Date date) throws SQLException, IOException {
        connector.delete("date = " + date );
        return all();
    }

    public static String report(int year, String currency) throws IOException, SQLException {
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

        double result = 0;

        Map<String, Double> currencyMap = Model.getCurrencyMap();
        List<Model> models = connector.select("`date` BETWEEN '"+ year + "-01-01' AND '"
                                                                             + year + "-12-31'");
        Number needCurrency = currencyMap.get(currency);

        if(needCurrency == null) {
            return "Currency not found. Supported currency:\n"
                    + currencyMap.keySet().toString().substring(1, currencyMap.keySet().toString().length() - 1);
        } else {
            for (Model model : models) {
                Number modelCurrency = currencyMap.get(model.getCurrency());
                result += model.getAmount() / modelCurrency.doubleValue() * needCurrency.doubleValue();
            }

            return String.format("%.2f", result) + " " + currency;
        }
    }
}