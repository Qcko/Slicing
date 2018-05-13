import java.util.HashMap;

public class Action_ShowMap extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        System.out.println("Showing all discovered nodes");
    }

    public void map(HashMap<String, Node> allNodesMap){
        System.out.println("Showing all discovered nodes");
        for (String node :
             allNodesMap.keySet()) {
          if (allNodesMap.get(node).getDiscovered()) {
              System.out.println(allNodesMap.get(node).getSerialNumber());
          }
        }
    }
}
