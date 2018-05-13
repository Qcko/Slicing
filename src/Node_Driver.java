import java.util.Arrays;
import java.util.List;

public class Node_Driver extends Node {


    public Node_Driver() {
    }

    public Node_Driver(String iDVar, String serialNumberVar, List<String> inventoryVar, List<String> availableMovesVar, List<String> availableActionsNamesVar, String neighboursStringVar) {
        super(iDVar, serialNumberVar, inventoryVar, availableMovesVar, availableActionsNamesVar, neighboursStringVar);
    }

    public static Node_Driver createNode(String line) {
        /**creation of node for database type **/

        String[] splitted = line.split(":");
        String nodeInformation = splitted[1].trim();
        String[] nodeInformationArray = nodeInformation.split(",");
        String thisNodeID = nodeInformationArray[0];
        String thisNodeSerialNumber = nodeInformationArray[1];
        List<String> thisNodeInventory = Arrays.asList(nodeInformationArray[2].split("%"));
        List<String> thisNodeAvailableMoves = Arrays.asList(nodeInformationArray[3].split("%"));
        List<String> thisNodeAvailableActionsNames = Arrays.asList(nodeInformationArray[4].split("%"));
        String thisNodeNeighboursString = nodeInformationArray[5];
        Node_Driver thisNode = new Node_Driver(thisNodeID, thisNodeSerialNumber, thisNodeInventory, thisNodeAvailableMoves, thisNodeAvailableActionsNames, thisNodeNeighboursString);
        return thisNode;
    }
}
