import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FlavorText {
    private static final String SCENARIOSTART_SELECTOR = "ScenarioStart";
    private static final String TIMERSETUP_SELECTOR = "TimerSetUp";
    private static final String WAITINGFORCOMMAND_SELECTOR = "WaitingForCommand";
    private String flavorScenarioStart;
    private String flavorTimerSetUp;
    private String flavorWaitingForCommand;

    public FlavorText(File textFile) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(textFile));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains("//")) {
                        if (line.contains(SCENARIOSTART_SELECTOR)) {
                            String[] splitted = line.split(":");
                            flavorScenarioStart = splitted[1].trim();
                        }
                        if (line.contains(TIMERSETUP_SELECTOR)) {
                            String[] splitted = line.split(":");
                            flavorTimerSetUp = splitted[1].trim();
                        }
                        if (line.contains(WAITINGFORCOMMAND_SELECTOR)) {
                            String[] splitted = line.split(":");
                            flavorWaitingForCommand = splitted[1].trim();
                        }
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

    public String getFlavorScenarioStart() {
        return flavorScenarioStart;
    }

    public String getFlavorTimerSetUp() {
        return flavorTimerSetUp;
    }

    public String getFlavorWaitingForCommand() {
        return flavorWaitingForCommand;
    }
}
