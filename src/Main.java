import front.UserInterface;


public class Main {

    public static void main(String[] args) {
        try {
            UserInterface ui = UserInterface.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
