package front;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Scanner;

public class UserInterface {

    private static UserInterface instance = null;

    public final String HELP_MESSAGE = "Enter `help` to get the command list\n" +
                                       "Enter `help [command name]` to get the detail of command";

    public static UserInterface get(){
        if (instance == null){
            instance = new UserInterface();
        }
        return instance;
    }

    private UserInterface() {

        Scanner scanner = new Scanner(System.in);

        try {


            System.out.println(Command.HELP.execute());

            while (true) {
                String result = "";

                try {
                    String[] command = scanner.nextLine().split(" ");
                    Command commandType = Command.valueOf(command[0].toUpperCase());
                    String[] commandParams = Arrays.copyOfRange(command, 1, command.length);

                    result = commandType.execute(commandParams);
                } catch (ParseException | IOException e) {
                    System.out.println("The command parameters entered incorrectly.");
                    System.out.println(Command.HELP.execute());
                } catch (IllegalArgumentException e) {
                    System.out.println("The command entered incorrectly.");
                    System.out.println(Command.HELP.execute());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Invalid number of command parameters");
                    System.out.println(Command.HELP.execute());
                }
                System.out.println(result);
            }
        } catch (SQLException | ParseException | IOException e) {
            System.out.println("Can not connect to sql server.\nThe program will be closed");
        }
    }
}