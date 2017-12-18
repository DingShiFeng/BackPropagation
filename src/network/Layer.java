package network;

import java.util.ArrayList;

public class Layer {
	String name;
	private ArrayList<Node> nodeList = null;
	
	public Layer(String name) {
		this.name = name;
		this.nodeList = new ArrayList<Node>();
	}
	
	public void addNode(Node node) {
		this.nodeList.add(node);
		node.setAssociatedLayer(this);
	}

	public void generateNodeList(int nodeNum) {
		for(int i=0; i<nodeNum; i++) {
			Node node = new Node(this);
			this.addNode(node);
		}
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Node> getNodeList() {
		return nodeList;
	}
	public void setNodeList(ArrayList<Node> nodeList) {
		this.nodeList = nodeList;
	}
}