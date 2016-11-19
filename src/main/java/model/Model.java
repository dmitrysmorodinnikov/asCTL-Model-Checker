package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.google.gson.Gson;

/**
 * A model is consist of states and transitions
 */
public class Model {
	
	private Set<State> initialStates = new HashSet<State>();
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
        	if (s.isInit() && !initialStates.contains(s)){
        		initialStates.add(s);
        	}
        }
	}
	
	public Set<State> getStatesSet(){
		return statesSet;
	}
	
	public void setTransitions(Transition[] transitions){
		this.transitions = transitions;
	}
	
	public Set<State> getPostCollection(State state){
		Set<String>postStrings = Arrays.asList(getTransitions())
				.stream()
				.filter(x->x.getSource().equals(state.getName()))
				.map(x->x.getTarget())
				.collect(Collectors.toSet());
		Set<State>postStates = statesSet.stream()
				.filter(x->postStrings.contains(x.getName()))
				.collect(Collectors.toSet());
		return postStates;
	}
	
	/**
	 * Validates that there are not final states. If one is found, 
	 * then a new transition to itself is added
	 */
	public void validate(){
		ArrayList<Transition> tr = new ArrayList<Transition>();
		tr.addAll(Arrays.asList(transitions));
		for (State s : states){
			if (getPostCollection(s).isEmpty()){
				Transition newTransition = new Transition();
				newTransition.setActions(new String[]{});
				newTransition.setSource(s.getName());
				newTransition.setTarget(s.getName());
				System.out.println("Created Transition: " + newTransition);
				tr.add(newTransition);
			}
		}
		this.transitions = tr.toArray(new Transition[tr.size()]);
	}

}
