package facility.graph.helpers;

public class NodeData<T> implements Comparable<NodeData<T>>{
	
	private final T nodeId;
	private int distance;
	
	public NodeData(T nodeIn) {
		this.nodeId = nodeIn;
		this.distance = 900000; //TODO confirm this works!
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

	@Override
	public int compareTo(NodeData<T> o) {
        if (this.getDistance() > o.getDistance()) return 1;
        if (o.getDistance() > this.getDistance()) return -1;
        return 0;
	}

}
