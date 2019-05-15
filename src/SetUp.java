import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.System;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class SetUp {

    private static final String FILENAME = "runThis.bat";                                //name of the bat file
    private static final String INPUT_FILE_NAME = "whichScenario.txt";                   //name of txt/json file which is used to control which scenario is executed
    private static final String SCENARIO_DEPOSIT = "scenarios";                             //name of the scenario deposit folder
    private static final String WORLD_DEPOSIT = "world";                                  //name of the world deposit folder
    private static final String SCENARIO_SELECTOR = "This";                             //text inside input file to
    private static final String SCENARIO_NODE_DATABASE_SELECTOR = "Database";            //text that initiated node information line for database type
    private static final String SCENARIO_NODE_DRIVER_SELECTOR = "Driver";                //text that initiated node information line for driver type
    private static final String SCENARIO_NODE_HONEYPOT_SELECTOR = "Honeypot";            //text that initiated node information line for honeypot type
    private static final String SCENARIO_NODE_NORMAL_SELECTOR = "Normal";                //text that initiated node information line for normal type
    private static final String SCENARIO_NODE_TERMINAL_SELECTOR = "Terminal";            //text that initiated node information line for terminal type
    private HashMap<String, Node> allNodesMap;
    private HashMap<String, Node> allNodesSerialMap;
    private HashMap<String, Action> actionsNamesMap;
    private HashMap<String, String> actionsCommandsMap;
    private File worldFlavorText;
    private long duration;

    public SetUp() {
        CreateActionMap allActions = new CreateActionMap();
        actionsNamesMap = allActions.getActionsNames();
        actionsCommandsMap = allActions.getActionsCommands();
        allNodesMap = createNodes(allActions.getActionsNames());
        connectNodes(allNodesMap);
        //todo create own class that takes two Strings and Node
    }

    public HashMap<String, String> getActionsCommandsMap() {
        return actionsCommandsMap;
    }

    public HashMap<String, Action> getActionsNamesMap() {
        return actionsNamesMap;
    }

    public HashMap<String, Node> getAllNodesMap() {
        return allNodesMap;
    }

    public HashMap<String, Node> getAllNodesSerialMap() {
        return allNodesSerialMap;
    }

    private File getScenarioFile() {
        /** gets the right scenario file **/
//todo split into separate methods
        Path programPath = Paths.get(FILENAME);
        File program = new File(String.valueOf(programPath));
        String programFullPath = program.getAbsolutePath();
        File programFull = new File(programFullPath);
        String scenarioFileName = null;
        String worldFileName = null;

        String inputFilePath = programFull.getParent() + "/" + INPUT_FILE_NAME;

        if (Files.notExists(Paths.get(inputFilePath))) {
            System.out.println(inputFilePath + " does not exist");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.contains("//")) {
                        if (line.contains(SCENARIO_SELECTOR)) {
                            String[] splitted = line.split(":");
                            worldFileName = "/" + splitted[3].trim();
                            scenarioFileName = "/" + splitted[2].trim();
                            duration = Long.parseLong(splitted[1].trim());
                            break;
                        }
                    }
                }
                reader.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        String worldDirPath = programFull.getParent() + "/" + WORLD_DEPOSIT;
        String worldPath = worldDirPath + worldFileName;

        if (Files.notExists(Paths.get(worldDirPath))) {
            System.out.println(worldDirPath + " does not exist");
            return null;
        } else {
            if (Files.notExists(Paths.get(worldPath))) {
                System.out.println(worldPath + " does not exist");
                return null;
            } else {
                File world = new File(worldPath);
                worldFlavorText = world;
            }
        }

        String scenarioDirPath = programFull.getParent() + "/" + SCENARIO_DEPOSIT;
        String scenarioPath = scenarioDirPath + scenarioFileName;

        if (Files.notExists(Paths.get(scenarioDirPath))) {
            System.out.println(scenarioDirPath + " does not exist");
            return null;
        } else {
            if (Files.notExists(Paths.get(scenarioPath))) {
                System.out.println(scenarioPath + " does not exist");
                return null;
            } else {
                File scenario = new File(scenarioPath);
                return scenario;
            }
        }
    }

    private HashMap<String, Node> createNodes(HashMap<String, Action> actionsNames) {
        File scenario = getScenarioFile();
        HashMap<String, Node> allNodesMap = new HashMap<>();
        HashMap<String, Node> allNodesSerialsMap = new HashMap<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(scenario));
            String line;
            while ((line = reader.readLine()) != null) {
                //todo create CreateNode method to unify similar actions
                /** database **/
                if (line.contains(SCENARIO_NODE_DATABASE_SELECTOR)) {
                    Node_Database thisNodeDatabase = Node_Database.createNode(line);
                    thisNodeDatabase.setAvailableActions(actionsNames);
                    allNodesMap.put(thisNodeDatabase.getID(), thisNodeDatabase);
                    allNodesSerialsMap.put(thisNodeDatabase.getSerialNumber(), thisNodeDatabase);
                }
                /** driver **/
                if (line.contains(SCENARIO_NODE_DRIVER_SELECTOR)) {
                Node_Driver thisNodeDriver = Node_Driver.createNode(line);
                    thisNodeDriver.setAvailableActions(actionsNames);
                    allNodesMap.put(thisNodeDriver.getID(), thisNodeDriver);
                    allNodesSerialsMap.put(thisNodeDriver.getSerialNumber(), thisNodeDriver);
                }
                /** honeypot **/
                if (line.contains(SCENARIO_NODE_HONEYPOT_SELECTOR)) {
                Node_Honeypot thisNodeHoneypot = Node_Honeypot.createNode(line);
                    thisNodeHoneypot.setAvailableActions(actionsNames);
                    allNodesMap.put(thisNodeHoneypot.getID(), thisNodeHoneypot);
                    allNodesSerialsMap.put(thisNodeHoneypot.getSerialNumber(), thisNodeHoneypot);
                }
                /** normal **/
                if (line.contains(SCENARIO_NODE_NORMAL_SELECTOR)) {
                Node_Normal thisNodeNormal = Node_Normal.createNode(line);
                    thisNodeNormal.setAvailableActions(actionsNames);
                    allNodesMap.put(thisNodeNormal.getID(), thisNodeNormal);
                    allNodesSerialsMap.put(thisNodeNormal.getSerialNumber(), thisNodeNormal);
                }
                /** terminal **/
                if (line.contains(SCENARIO_NODE_TERMINAL_SELECTOR)) {
                Node_Terminal thisNodeTerminal = Node_Terminal.createNode(line);
                    thisNodeTerminal.setAvailableActions(actionsNames);
                    thisNodeTerminal.setCaptured(true);
                    thisNodeTerminal.setDiscovered(true);
                    allNodesMap.put(thisNodeTerminal.getID(), thisNodeTerminal);
                    allNodesSerialsMap.put(thisNodeTerminal.getSerialNumber(), thisNodeTerminal);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        allNodesSerialMap = allNodesSerialsMap;
        return allNodesMap;
    }

    public long getDuration() {
        return duration;
    }

    private void connectNodes(HashMap<String, Node> allNodesMap ){
        ArrayList<Node> allNodes = new ArrayList<>();
               allNodes.addAll(allNodesMap.values());
        for (Node node:
             allNodes) {
            String thisNodeNeighboursString = node.getNeighboursString();
            String[] thisNodeNeighbours = thisNodeNeighboursString.split("%");
            ArrayList<Node> thisNeighbours = new ArrayList<>();
            for (String neighbour:
                 thisNodeNeighbours) {
                thisNeighbours.add(allNodesMap.get(neighbour));
            }
            node.setNeighbours(thisNeighbours);
        }
    }

    public File getWorldFlavorText() {
        return worldFlavorText;
    }
}


