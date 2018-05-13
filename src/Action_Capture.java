public class Action_Capture extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        if (node.getDiscovered()) {
            System.out.println("Node captured");
            node.setCaptured(true);
        } else {
            System.out.println("Node not recognized");
        }
    }
}
