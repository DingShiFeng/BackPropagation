package data;

import java.util.ArrayList;

public class Data {
	private ArrayList<Double> attributionList = null;
	
	public Data() {
		this.attributionList = new ArrayList<Double>();
	}
	
	public void addAttribution(Double attribution) {
		this.attributionList.add(attribution);
	}
	
	public ArrayList<Double> getAttributionList() {
		return attributionList;
	}
	public void setAttributionList(ArrayList<Double> attributionList) {
		this.attributionList = attributionList;
	}
}
