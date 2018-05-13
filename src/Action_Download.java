public class Action_Download extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        if (node.getDiscovered()) {
            if (node.getCaptured()){
                System.out.println("Downloaded " + node.getInventory());
                System.out.println("Tell NPCs that you have " + node.getInventory());
            }else {
                watch.punish();
            }
        } else {
            System.out.println("Node not recognized");
        }
    }
}
