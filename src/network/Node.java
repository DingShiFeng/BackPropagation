package network;

public class Node {
	private Layer associatedLayer = null;
	
	private double outputValue;
	
	private double outputError;
	private double hiddenError;
	
	public Node(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}
	
	public Layer getAssociatedLayer() {
		return associatedLayer;
	}
	public void setAssociatedLayer(Layer associatedLayer) {
		this.associatedLayer = associatedLayer;
	}
	public double getOutputValue() {
		return outputValue;
	}
	public void setOutputValue(double outputValue) {
		this.outputValue = outputValue;
	}
	public double getOutputError() {
		return outputError;
	}
	public void setOutputError(double outputError) {
		this.outputError = outputError;
	}
	public double getHiddenError() {
		return hiddenError;
	}
	public void setHiddenError(double hiddenError) {
		this.hiddenError = hiddenError;
	}
}
