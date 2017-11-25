package projects.isleAgents;
import jade.core.Agent;

public class Main  extends Agent  {
    private static  final long serialVersionUID = 1L;

    public void setup(){
        System.out.println("Hello, my name is" + getAID().getName());
    }
}
