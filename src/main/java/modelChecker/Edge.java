package modelChecker;

/**
 * Represents an Edge on a graph used to find counter examples
 */
public class Edge {
	
	private static int edgeCount = 0;
	
	private Node from;
	private Node to;
	private String[] actions;
	private int id;
	
	public Edge(Node from, Node to){
		this.id = edgeCount++;
		this.from = from;
		this.to = to;
	}
	
	public int getId(){
		return id;
	}
	
	public Node getFrom() {
		return from;
	}
	
	public void setFrom(Node from) {
		this.from = from;
	}
	
	public Node getTo() {
		return to;
	}
	
	public void setTo(Node to) {
		this.to = to;
	}
	
	public String[] getActions() {
		return actions;
	}
	
	public String getActionsString(){
		String res = "[";
		for(int i = 0; i < actions.length; i++){
			res+=actions[i];
			if (i != actions.length - 1){
				res+=",";
			}
		}
		res+="]";
		return res;
	}
	
	public void setActions(String[] actions) {
		this.actions = actions;
	}
	
	public String toString(){
		return from.getState().toString() + " to " + to.getState().toString();
	}
}
