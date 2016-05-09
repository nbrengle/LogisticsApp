package facility.graph;

public class NodePair {
	
	private String node;
	private String connection;

	
	public NodePair (String nodeIn, String connectIn) {
		setNode(nodeIn);
		setConnection(connectIn);
	}

	public String getNode() {
		return node;
	}

	private void setNode(String nodeIn) {
		this.node = nodeIn;
	}

	public String getConnection() {
		return connection;
	}

	private void setConnection(String connectionIn) {
		this.connection = connectionIn;
	}
}
