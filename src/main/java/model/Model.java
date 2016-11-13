package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import com.google.gson.Gson;

/**
 * A model is consist of states and transitions
 */
public class Model {
	
	private Set<State> initialStates;
    private State[] states;
    private Transition[] transitions;
    private Set<State> statesSet;

    public static Model parseModel(String filePath) throws IOException {
        Gson gson = new Gson();
        Model model = gson.fromJson(new FileReader(filePath), Model.class);
        for (Transition t : model.transitions) {
            System.out.println(t);
            ;
        }
        
        model.initialStates = new HashSet<State>();
        for (State s : model.states){
        	if (s.isInit()){
        		model.initialStates.add(s);
        	}
        }
        
        model.statesSet = new HashSet<State>();
        for (State s : model.states){
        	model.statesSet.add(s);
        }
        
        return model;
    }

    /**
     * Returns the list of the states
     * 
     * @return list of state for the given model
     */
    public State[] getStates() {
        return states;
    }

    /**
     * Returns the list of transitions
     * 
     * @return list of transition for the given model
     */
    public Transition[] getTransitions() {
        return transitions;
    }

    /**
     * Returns the set of initial states of the model
     * @return
     */
	public Set<State> getInitialStates() {
		return initialStates;
	}
	
	/** TODO: setters created for testing only*/
	public void setInitialStates(Set<State> initialStates){
		this.initialStates = initialStates;
	}
	
	public void setStates(State[] states){
		this.states = states;
		this.statesSet = new HashSet<State>();
        for (State s : states){
        	statesSet.add(s);
        }
	}
	
	public Set<State> getStatesSet(){
		return statesSet;
	}
	
	public void setTransitions(Transition[] transitions){
		this.transitions = transitions;
	}

}
