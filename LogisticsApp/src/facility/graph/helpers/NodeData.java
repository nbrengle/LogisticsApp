package facility.graph.helpers;

public class NodeData<T> {
	
	private final T nodeId;
	private int distance;
	
	public NodeData(T nodeIn) {
		this.nodeId = nodeIn;
		this.distance = Integer.MAX_VALUE; //TODO confirm this works!
	}
	
	public T getNodeId() {
		return nodeId;
	}
	
	public int getDistance() {
		return distance;
	}
	
	public void setDistance(int distIn) {
		//TODO add validation!
		this.distance = distIn;
	}

}
