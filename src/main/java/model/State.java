package model;

/**
 * 
 * */
public class State implements Cloneable{
    private boolean init;
    private String name;
    private String [] label;
	
    /**
     * Is state an initial state
     * @return boolean init 
     * */
    public boolean isInit() {
	return init;
    }
	
    /**
     * Returns the name of the state
     * @return String name 
     * */
    public String getName() {
	return name;
    }
	
    /**
     * Returns the labels of the state
     * @return Array of string labels
     * */
    public String[] getLabel() {
	return label;
    }    
    
    @Override
    public Object clone()throws CloneNotSupportedException {
        return super.clone();
    }
    
    /** TODO: Setters created for testing only**/
    public void setInit(boolean init){
    	this.init = init;
    }
    
    public void setName(String name){
    	this.name = name;
    }
    
    public void setLabel(String[] label){
    	this.label = label;
    }
    
    @Override
    public boolean equals(Object obj){
    	//Two sets are equal if they share the same name
    	if (obj instanceof State){
    		return this.name.equals(((State) obj).name);
    	}
    	return false;
    }
	
    @Override
    public String toString(){
    	return name;
    }
}
