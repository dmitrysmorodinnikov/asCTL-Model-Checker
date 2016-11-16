package modelChecker;

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
	
	public void setActions(String[] actions) {
		this.actions = actions;
	}
	
	public String toString(){
		return from.getState().toString() + " to " + to.getState().toString();
	}
}
