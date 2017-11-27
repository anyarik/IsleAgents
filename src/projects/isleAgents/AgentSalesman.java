package projects.isleAgents;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.jenetics.*;
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
                    Type collectionMessageType = new TypeToken<Phenotype<EnumGene<Integer>, Double>>(){}.getType();
                    Phenotype<EnumGene<Integer>, Double> bestPhemotype = gson.fromJson(msg.getContent(), collectionMessageType);

                    Engine<EnumGene<Integer>, ?> newEngine =
                            Engine.builder(bestPhemotype.getFitnessFunction(), bestPhemotype.getGenotype())
                                    .optimize(Optimize.MINIMUM)
                                    .maximalPhenotypeAge(11)
                                    .populationSize(500)
                                    .alterers(
                                            new SwapMutator<>(0.2),
                                            new PartiallyMatchedCrossover<>(0.35))
                                    .build();

                    Type collectionResultType = new TypeToken< Engine<EnumGene<Integer>, Double>>(){}.getType();
                    String  newResult = gson.toJson(newEngine, collectionResultType);


                    //AID agentID = agent.getName();
                    ACLMessage resultMsg = new ACLMessage(ACLMessage.INFORM);
                    //id агента, которому отправляем сообщение

//                    SearchConstraints c = new SearchConstraints();
//                    AMSAgentDescription[] agents= AMSService.search(this, new
//                            AMSAgentDescription(), c);

                   // resultMsg.addReceiver( 8257866411543354395L);
                    resultMsg.setLanguage("English");// язык сообщения

                    resultMsg.setContent(newResult);
                    send(resultMsg);

                }
            }
        });

//        AMSAgentDescription[] agents = null;
//        try {
//            SearchConstraints c = new SearchConstraints();
//            c.setMaxResults(new Long(-1));
//            agents= AMSService.search(this, new
//                    AMSAgentDescription(), c); }
//        catch (Exception e) {
//            System.out.println("Problem searching AMS:" + e);
//            e.printStackTrace(); }
//        for (AMSAgentDescription agent : agents) {
//            AID agentID = agent.getName();
//            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
//            //id агента, которому отправляем сообщение
//            msg.addReceiver(agentID);
//            msg.setLanguage("English");// язык сообщения
//
//            Engine<EnumGene<Integer>, Double> enumGeneDoubleEngine = Utils.GetEngine(11,500);
//            Type collectionType = new TypeToken< Engine<EnumGene<Integer>, Double>>(){}.getType();
//
//            String result = gson.toJson(enumGeneDoubleEngine, collectionType);
//            msg.setContent(result); // содержимое сообщения
//            send(msg);// передача сообщения
//        }
    }

}
