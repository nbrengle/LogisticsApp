package facility.graph.helpers;

import facility.exceptions.InvalidParameterException;

public class NeighborNode<T> {
	
	private NodeData<T> nodeData;
	private int weight;
	
	public NeighborNode(T nodeIn, int weightIn) {
		try {
			setNodeData(nodeIn);
			setWeight(weightIn);
		} 
		catch (InvalidParameterException e) {
			e.printStackTrace();
		}
	}
	
	public NodeData<T> getNodeData() {
		return nodeData;
	}
	
	private void setNodeData(T nodeIn) {
		//TODO consider additional validation
		this.nodeData = new NodeData<T>(nodeIn);
	}
	
	public int getWeight() {
		return weight;
	}
	
	private void setWeight(int weight) throws InvalidParameterException {
		if (weight < 0) throw new InvalidParameterException("weight must be > 0");
		this.weight = weight;
	}
	
}
