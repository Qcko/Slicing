public class Action_Device_Info extends Action {

    @Override
    public void action(Node node, TimerClass watch) {
        if (node.getDiscovered()) {
            if (node.getCaptured()){
                System.out.println("Device can use " + node.getAvailableMoves());
        }else {
            watch.punish();
        }
        } else {
            System.out.println("Node not recognized");
        }
    }
}
