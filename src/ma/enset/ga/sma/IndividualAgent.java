package ma.enset.ga.sma;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.wrapper.AgentContainer;

import java.util.Random;

public class IndividualAgent extends Agent {
    private int genes[]=new int[10];
    private int fitness;
    Random rnd=new Random();
    @Override
    protected void setup() {
        //calculate the fitness
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                fitness=0;
                for (int gene:genes) {
                    fitness+=gene;
                }
            }
        });
        AgentContainer containerController = getContainerController();
        //containerController.cre
        //mutation
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                int index=rnd.nextInt(6);
                if(genes[index]==1){
                    genes[index]=0;
                }else{
                    genes[index]=1;
                }

            }
        });
    }
}
