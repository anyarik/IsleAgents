package projects.isleAgents;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jenetics.EnumGene;
import io.jenetics.engine.Engine;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.AMSService;
import jade.domain.FIPAAgentManagement.AMSAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.lang.acl.ACLMessage;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Random;


public class AgentSalesman extends Agent {
    private static final long serialVersionUID =
            8257866411543354395L;

    private Gson gson = new Gson();

    public void setup() {
        System.out.println("Hello World, my name is : " +
                getAID().getName());
        //поведение агента, исполняемое в цикле
        addBehaviour(new CyclicBehaviour(this) {
            private static final long serialVersionUID =
                    7774831398907094833L;
            public void action() {
                ACLMessage msg = receive();
                if (msg != null)
                {

                }
            }
        });
        AMSAgentDescription[] agents = null;
        try {
            SearchConstraints c = new SearchConstraints();
            c.setMaxResults(new Long(-1));
            agents= AMSService.search(this, new
                    AMSAgentDescription(), c); }
        catch (Exception e) {
            System.out.println("Problem searching AMS:" + e);
            e.printStackTrace(); }
        for (AMSAgentDescription agent : agents) {
            AID agentID = agent.getName();
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            //id агента, которому отправляем сообщение
            msg.addReceiver(agentID);
            msg.setLanguage("English");// язык сообщения

            Engine<EnumGene<Integer>, Double> enumGeneDoubleEngine = Utils.GetEngine(11,500);
            Type collectionType = new TypeToken< Engine<EnumGene<Integer>, Double>>(){}.getType();

            String result = gson.toJson(enumGeneDoubleEngine, collectionType);
            msg.setContent(result); // содержимое сообщения
            send(msg);// передача сообщения
        }
    }

}
