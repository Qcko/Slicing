import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class Node {

    private String iD;                                      //id for me to work with
    private String serialNumber;                            //identification of a node to players
    private Boolean captured;
    private Boolean discovered;
    private String neighboursString;
    private List<Node> neighbours;
    private List<String> inventory;
    private List<String> availableActionsNames;
    private List<Action> availableActions;                  //all actions available on this node
    private List<String> availableMoves;                    //all actions name visible to players

    public Node() {
    }

    public Node(String iDVar, String serialNumberVar, List<String> inventoryVar, List<String> availableMovesVar, List<String> availableActionsNamesVar, String neighboursStringVar) {
        /** set up a node **/
        iD = iDVar;
        serialNumber = serialNumberVar;
        captured = false;
        discovered = false;
        inventory = inventoryVar;
        availableMoves = availableMovesVar;
        availableActionsNames = availableActionsNamesVar;
        neighboursString = neighboursStringVar;
    }

    public String getID() {
        return iD;
    }

    public String getNeighboursString() {
        return neighboursString;
    }

    public List getAvailableMoves() {
        return availableMoves;
    }

    public List<Node> getNeighbours() {
        return neighbours;
    }

    public Boolean getCaptured() {
        return captured;
    }

    public Boolean getDiscovered() {
        return discovered;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setCaptured(Boolean captured) {
        this.captured = captured;
    }

    public void setDiscovered(Boolean discovered) {
        this.discovered = discovered;
    }

    public void setNeighbours(List<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public List<Action> getAvailableActions() {
        return availableActions;
    }

    public void setAvailableActions(HashMap<String, Action> actionsNames) {
        List<Action> thisAvailableActions = new ArrayList<>();
        availableActionsNames.forEach(name -> {

            thisAvailableActions.add(actionsNames.get(name));
        });
        availableActions = thisAvailableActions;
    }


    @Override
    public String toString() {
        return serialNumber;
    }
}
