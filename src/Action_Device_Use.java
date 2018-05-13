public class Action_Device_Use extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        if (node.getDiscovered()) {
            if (node.getCaptured()){
                System.out.println("Using " + node.getAvailableMoves());
                System.out.println("Tell NPCs that you used " + node.getAvailableMoves());
            }else {
                watch.punish();
            }
        } else {
            System.out.println("Node not recognized");
        }
    }
}
