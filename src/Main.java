import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) {


//todo SetUp getScenarioFile bugs with paths now it is hardcoded
//todo switch from txt files to json files

try {
        SetUp starting = new SetUp();
        FlavorText flavorText = new FlavorText(starting.getWorldFlavorText());
        SystemClass running = new SystemClass();
        running.setDuration(starting.getDuration());
        System.out.println(flavorText.getFlavorScenarioStart());
        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
        System.out.println(flavorText.getFlavorTimerSetUp() + " " + timeFormat.format(starting.getDuration()*1000));
        running.setActualTimer();
        TimerClass watch = new TimerClass(running);
        System.out.println(flavorText.getFlavorWaitingForCommand() + ": ");
        Game game = new Game(starting.getActionsNamesMap(), starting.getActionsCommandsMap(), starting.getAllNodesMap(), starting.getAllNodesSerialMap(), watch);
        game.run();
}catch (Exception e) {System.out.println(e.getMessage());}
    }
}





