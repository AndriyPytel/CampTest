package front;

import back.CommandPerformer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static back.SqlConnector.DATE_FORMAT;

public enum Command {

    PURCHASE {
        @Override
        public String execute(String... param) throws SQLException, ParseException, IOException {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

            Date   date     = format.parse (param[0]);
            double amount   = Double.parseDouble(param[1]);
            String currency = param[2];
            String name     = String.join(" ", Arrays.copyOfRange(param,3, param.length)) ;

            return CommandPerformer.purchase(date, amount, currency, name);

        }
    },

    ALL {
        @Override
        public String execute(String... param) throws SQLException, IOException {
            return CommandPerformer.all();
        }
    },

    CLEAR {
        @Override
        public String execute(String... param) throws SQLException, ParseException, IOException {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);

            Date   date     = format.parse (param[0]);
            return CommandPerformer.clear(date);
        }
    },

    REPORT {
        @Override
        public String execute(String... param) throws SQLException, IOException {

            int year = Integer.parseInt(param[0]);
            String currency = param[1];
            return CommandPerformer.report(year, currency);
        }
    },
    HELP {
        @Override
        public String execute(String... params){

            StringBuilder result = new StringBuilder();

            if (params.length == 0) {
                result.append(COMMAND_LIST + "\n" + HELP_DETAIL);
            } else {
                switch (params[0].toLowerCase()) {
                    case "full": {
                        result.append(COMMAND_LIST
                                + HELP_DETAIL       + "\n"
                                + PURCHASE_DETAIL   + "\n"
                                + ALL_DETAIL        + "\n"
                                + REPORT_DETAIL     + "\n"
                                + CLEAR_DETAIL      + "\n"
                                + EXIT_DETAIL
                        );
                    } break;
                    case "purchase": {
                        result.append(PURCHASE_DETAIL);
                    } break;
                    case "all": {
                        result.append(ALL_DETAIL);
                    } break;
                    case "clear": {
                        result.append(CLEAR_DETAIL);
                    } break;
                    case "report": {
                        result.append(REPORT_DETAIL);
                    } break;
                    case "exit": {
                        result.append(EXIT_DETAIL);
                    } break;
                    default: {
                        result.append(COMMAND_LIST + "\n" + HELP_DETAIL);
                    }
                }
            }
            return String.valueOf(result);
        }
    },
    EXIT {
        @Override
        public String execute(String... param) {
            System.exit(0);
            return null;
        }
    };

    private static final String HELP_DETAIL     = "Enter `help [command name]` to get the detail of command.\n" +
            "Command parameter `full` to get all detail or no parameters to get command list\n";
    private static final String PURCHASE_DETAIL = "Enter `purchase [date] [amount] [currency] [name]` " +
            "to add purchases made by customers\n " +
            "in any supported currency (e.g. USD, EUR, PLN, etc.) to the list of purchases.\n " +
            "Purchases for various dates could be added in any order. \n" +
            "Command accepts the following parameters:\n" +
            "\n" +
            "[date] — the date when purchase has occurred\n" +
            "[amount] — an amount of money spent by customer\n" +
            "[currency] — the currency in which purchase has occurred\n" +
            "[name] — the name of the product purchased\n" +
            "For example: purchase 2019-04-25 12 USD “Photo Frame”\n";
    private static final String ALL_DETAIL      = "Enter `all` to show all purchases sorted by date \n";
    private static final String CLEAR_DETAIL    = "Enter `clear [date]` " +
            "to removes all purchases for specified date, where:\n" +
            "[date] — the date for which all purchases should be removed"+
            "For example: clear 2019-04-25\n";
    private static final String REPORT_DETAIL   = "Enter `report [year] [currency]` " +
            "to calculate the total income for specified year,\n" +
            "convert and present it in specified currency, where:\n" +
            "[year] — year for which total income should be calculated\n" +
            "[currency] — currency in which total income is presented\n" +
            "For example report 2011 UAH\n";
    private static final String EXIT_DETAIL     = "Enter `exit` to close program\n";
    private static final String COMMAND_LIST    = "Program commands:\n" +
            "purchase [date] [amount] [currency] [name] to add purchases made by customers\n" +
            "help [command name] to get detail of command \n" +
            "all to show all purchases\n" +
            "clear [date] to removes all purchases for specified date\n" +
            "report [year] [currency] to calculate the total income for specified year\n" +
            "exit to close program\n";

    public abstract String execute(String... param) throws SQLException, ParseException, IOException;

}