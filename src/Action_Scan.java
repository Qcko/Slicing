public class Action_Scan extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        if (node.getDiscovered()) {
            if (node.getCaptured()){
                System.out.println("Showing neighbours:");
                for (Node neighbour:
                     node.getNeighbours()) {
                 System.out.println(neighbour.getSerialNumber());
                 neighbour.setDiscovered(true);
                }
            }else {
                watch.punish();
            }
        } else {
            System.out.println("Node not recognized");
        }
    }
}
