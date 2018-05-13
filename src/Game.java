import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private boolean running;
    private HashMap<String, Action> actionsNames;
    private HashMap<String, String> actionsCommands;
    private HashMap<String, Node> allNodesMap;
    private HashMap<String, Node> allNodesSerialMap;
    private TimerClass watch;

    public Game(HashMap<String, Action> actionsNamesVar, HashMap<String, String> actionsCommandsVar, HashMap<String, Node> allNodesMapVar, HashMap<String, Node> allNodesSerialMapVar, TimerClass watchVar){
        actionsNames = actionsNamesVar;
        actionsCommands = actionsCommandsVar;
        allNodesMap = allNodesMapVar;
        allNodesSerialMap = allNodesSerialMapVar;
        watch = watchVar;
    }

    public void run() {
                running = true;

        while (running) {
            Scanner keyboardInput = new Scanner(System.in);
            String nextCommand = keyboardInput.nextLine().trim();

            if (nextCommand.contains(" ")) {
                String[] splitted = nextCommand.split(" ");
                String thisNodeName = splitted[0].trim();
                String thisCommand = splitted[1].trim();
                if (allNodesSerialMap.containsKey(thisNodeName)) {
                    Node thisNode = allNodesSerialMap.get(thisNodeName);
                    processPlayersInput(actionsNames, actionsCommands, thisNode, thisCommand, watch);
                } else {
                    if (thisNodeName.contains("all") && thisCommand.contains("show-map")) {
                        new Action_ShowMap().map(allNodesMap);
                    } else {
                        System.out.println("Node not recognized");
                    }
                }
            } else {
                System.out.println("Input without a space");
            }
        }
    }

    public void processPlayersInput(HashMap<String, Action> actionsNames, HashMap<String, String> actionsCommands, Node thisNode, String thisCommandName, TimerClass watch) {
        if (actionsCommands.containsKey(thisCommandName)) {
            Action thisCommand = actionsNames.get(actionsCommands.get(thisCommandName));
            if (thisNode.getAvailableActions().contains(thisCommand)) {
                thisCommand.action(thisNode, watch);
            } else {
                watch.punish();
            }
        } else {
            System.out.println("Command not recognized");
        }
    }

}

