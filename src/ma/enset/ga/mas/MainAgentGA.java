package ma.enset.ga.mas;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.SequentialBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import ma.enset.ga.sequencial.AGUtils;
import ma.enset.ga.sequencial.Individual;

import java.util.*;

public class MainAgentGA extends Agent {
    List<AgentFitness> agentsFitness=new ArrayList<>();
    Random rnd=new Random();

    @Override
    protected void setup() {

        //Search for agent and add it to the list
        DFAgentDescription dfAgentDescription=new DFAgentDescription();
        ServiceDescription serviceDescription=new ServiceDescription();
        serviceDescription.setType("ga");
        dfAgentDescription.addServices(serviceDescription);
        try {
            DFAgentDescription[] agentsDescriptions = DFService.search(this, dfAgentDescription);
            System.out.println(agentsDescriptions.length);
            for (DFAgentDescription dfAD:agentsDescriptions) {
                agentsFitness.add(new AgentFitness(dfAD.getName(),0));
            }
        } catch (FIPAException e) {
            e.printStackTrace();
        }
        calculateFintness();

        SequentialBehaviour sequentialBehaviour=new SequentialBehaviour();

        sequentialBehaviour.addSubBehaviour(new Behaviour() {

            //Get the message
            int cpt=0;
            @Override
            public void action() {
                ACLMessage receivedMSG = receive();
                if (receivedMSG!=null){
                    cpt++;
                    System.out.println(cpt);
                    int fintess=Integer.parseInt(receivedMSG.getContent());
                    AID sender=receivedMSG.getSender();
                    setAgentFintess(sender,fintess);
                    if(cpt== AGUtils.POPULATION_SIZE){
                        Collections.sort(agentsFitness,Collections.reverseOrder());
                        showPopulation();
                    }
                }else {
                    block();
                }
            }

            @Override
            public boolean done() {
                return cpt==AGUtils.POPULATION_SIZE;
            }
        });

        sequentialBehaviour.addSubBehaviour(new Behaviour() {

            int it=0;
            AgentFitness agentFitness1;
            AgentFitness agentFitness2;

            @Override
            public void action() {
                selection();
                crossover();
                Collections.sort(agentsFitness,Collections.reverseOrder());
                sendMessage(agentsFitness.get(0).getAid(),"chromosome",ACLMessage.REQUEST);
                ACLMessage aclMessage=blockingReceive();
                System.out.println(aclMessage.getContent()+" | "+agentsFitness.get(0).getFitness());
                it++;
            }

            private void selection(){
                agentFitness1=agentsFitness.get(0);
                agentFitness2=agentsFitness.get(1);
                sendMessage(agentFitness1.getAid(),"chromosome",ACLMessage.REQUEST);
                sendMessage(agentFitness2.getAid(),"chromosome",ACLMessage.REQUEST);
            }

            private void crossover(){
                ACLMessage aclMessage1=blockingReceive();
                ACLMessage aclMessage2=blockingReceive();

                int pointCroisment=rnd.nextInt(AGUtils.CHROMOSOME_SIZE -2 );
                pointCroisment++;

                char  [] chromParent1=aclMessage1.getContent().toCharArray();
                char  [] chromParent2=aclMessage2.getContent().toCharArray();
                char  [] chromOffsring1=new char[AGUtils.CHROMOSOME_SIZE];
                char  [] chromOffsring2=new char[AGUtils.CHROMOSOME_SIZE];

                for (int i=0;i<chromParent1.length;i++) {
                    chromOffsring1[i]=chromParent1[i];
                    chromOffsring2[i]=chromParent2[i];
                }
                for (int i=0;i<pointCroisment;i++) {
                    chromOffsring1[i]=chromParent2[i];
                    chromOffsring2[i]=chromParent1[i];
                }

                int fitness=0;
                for (int i=0;i<AGUtils.CHROMOSOME_SIZE;i++) {
                    if(chromOffsring1[i]==AGUtils.SOLUTION.charAt(i))
                        fitness+=1;
                }

                agentsFitness.get(AGUtils.POPULATION_SIZE-2).setFitness(fitness);

                /*fitness=0;
                for (int i=0;i<AGUtils.CHROMOSOME_SIZE;i++) {
                    if(chromOffsring2[i]==AGUtils.SOLUTION.charAt(i))
                        fitness+=1;
                }

                agentsFitness.get(AGUtils.POPULATION_SIZE-1).setFitness(fitness);*/


                sendMessage(agentsFitness.get(AGUtils.POPULATION_SIZE-2).getAid(),new String(chromOffsring1),ACLMessage.REQUEST);

                sendMessage(agentsFitness.get(AGUtils.POPULATION_SIZE-1).getAid(),new String(chromOffsring2),ACLMessage.REQUEST);

                ACLMessage recievedMsg1=blockingReceive();
                ACLMessage recievedMsg2=blockingReceive();

                setAgentFintess(recievedMsg1.getSender(),Integer.parseInt(recievedMsg1.getContent()));
                setAgentFintess(recievedMsg2.getSender(),Integer.parseInt(recievedMsg2.getContent()));

            }

            @Override
            public boolean done() {
                return it==AGUtils.MAX_IT || agentsFitness.get(0).getFitness()==AGUtils.CHROMOSOME_SIZE;
                //return false;
            }
        });
        addBehaviour(sequentialBehaviour);

    }
    private void calculateFintness(){

        //Send "fitness" message to other agents
        ACLMessage message=new ACLMessage(ACLMessage.REQUEST);

        for (AgentFitness agf:agentsFitness) {
            message.addReceiver(agf.getAid());
        }
        message.setContent("fitness");
        send(message);

    }
    private void setAgentFintess(AID aid,int fitness){
            for (int i=0;i<AGUtils.POPULATION_SIZE;i++){
                if(agentsFitness.get(i).getAid().equals(aid)){
                    agentsFitness.get(i).setFitness(fitness);
                    break;
                }
            }
    }

    public void sendMessage(AID aid, String contenet, int performative){
        ACLMessage message=new ACLMessage(performative);
        message.setContent(contenet);
        message.addReceiver(aid);
        send(message);
    }

    private void showPopulation(){
        for (AgentFitness agentFitness:agentsFitness) {
            System.out.println(agentFitness.getAid().getName()+" "+agentFitness.getFitness());
        }
    }
}
