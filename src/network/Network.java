package network;

import java.util.ArrayList;

import general.Constant;

public class Network {
	private ArrayList<Layer> layerList = null;
	
	public Network() {
		this.layerList = new ArrayList<Layer>();
		
		Layer inputLayer = new Layer("inputLayer");
		inputLayer.generateNodeList(Constant.inputNodeNum);
		this.layerList.add(inputLayer);
		Layer hiddenLayer = new Layer("hiddenLayer");
		hiddenLayer.generateNodeList(Constant.hiddenNodeNum);
		this.layerList.add(hiddenLayer);
		Layer outputLayer = new Layer("outputLayer");
		outputLayer.generateNodeList(Constant.outputNodeNum);
		this.layerList.add(outputLayer);
	}

	public ArrayList<Layer> getLayerList() {
		return layerList;
	}

	public void setLayerList(ArrayList<Layer> layerList) {
		this.layerList = layerList;
	}
}