// Letter Recognition
// 16 input layers, 10 hidden layers, 26 output layers
/* 
 * we use 26 binary numbers to donate a letter
 */

package general;

import java.io.IOException;
import java.util.ArrayList;

import data.ReadData;
import data.WriteData;
import data.Data;
import network.Network;
import network.Node;

public class Main{
	static double inputHiddenWeight[][] = new double[Constant.inputNodeNum][Constant.hiddenNodeNum];
	static double hiddenOutputWeight[][] = new double[Constant.hiddenNodeNum][Constant.outputNodeNum];
	static double inputHiddenBias[] = new double[Constant.hiddenNodeNum];
	static double hiddenOutputBias[] = new double[Constant.outputNodeNum];
	
	public static void main(String[] args) throws IOException {
		WriteData dataWriter = new WriteData();
		dataWriter.clearData(Constant.resultFile);
		ReadData dataReader = new ReadData();
		ArrayList<Data> trainDataList = dataReader.readData(Constant.dataFile, 0, 4999);
		ArrayList<Data> testDataList = dataReader.readData(Constant.dataFile, 5000, 5999);
		
		/*for(Data trainData : trainDataList) {
			for(int n=0; n<Constant.inputNodeNum+Constant.outputNodeNum; n++) {
				System.out.print(trainData.getAttributionList().get(n)+" ");
			}
			System.out.println();
		}*/
		/*for(Data testData : trainDataList) {
			for(int n=0; n<Constant.inputNodeNum+Constant.outputNodeNum; n++) {
				System.out.print(testData.getAttributionList().get(n)+" ");
			}
			System.out.println();
		}*/
		
		generateWeight();
		
		double Error = 0;
		Network network = new Network();
		for(int count=0; count<Constant.COUNT; count++) {
			Error = 0;
			for(Data trainData : trainDataList) {
				forwardPropagation(trainData, network);
				backPropagation(trainData, network);
				updateWeightAndBias(network);
				for(int k=0; k<Constant.outputNodeNum; k++) {
					Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
					Error += 0.5*Math.pow((trainData.getAttributionList().get(k+Constant.inputNodeNum)-outputNode.getOutputValue()), 2);
				}
			}
			Error /= trainDataList.size();
			if(count%10 == 0) {
				dataWriter.writeData(Constant.resultFile, "Error="+Error+"\r\n");
			}
			System.out.println("Error="+Error);
		}
		
		double Rate = 0;
		for(Data testData : testDataList) {
			int index = 0;
			for(int k=0; k<Constant.outputNodeNum; k++) {
				if(testData.getAttributionList().get(Constant.inputNodeNum+k) == 1) {
					index = k;
				}
			}
			forwardPropagation(testData, network);
			double max = 0;
			int pos = 0;
			for(int k=0; k<Constant.outputNodeNum; k++) {
				Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
				if(max < outputNode.getOutputValue()) {
					max = outputNode.getOutputValue();
					pos = k;
				}
			}
			if(pos == index) {
				Rate++;
			}
		}
		Rate /= testDataList.size();
		dataWriter.writeData(Constant.resultFile, "\r\nRate="+Rate);
		System.out.println("Rate="+Rate);
	}
	
	public static void generateWeight() {
		for(int i=0; i<Constant.inputNodeNum; i++) {
			for(int j=0; j<Constant.hiddenNodeNum; j++) {
				inputHiddenWeight[i][j] = (1-2*Math.random());
			}
		}
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			inputHiddenBias[j] = Math.random();
		}
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			for(int k=0; k<Constant.outputNodeNum; k++) {
				hiddenOutputWeight[j][k] = (1-2*Math.random());
			}
		}
		for(int k=0; k<Constant.outputNodeNum; k++) {
			hiddenOutputBias[k] = Math.random();
		}
	}
	
	public static void forwardPropagation(Data data, Network network) {
		Function func = new Function();
		for(int i=0; i<Constant.inputNodeNum; i++) {
			Node inputNode = network.getLayerList().get(0).getNodeList().get(i);
			inputNode.setOutputValue(data.getAttributionList().get(i));
		}
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			double temp = 0;
			Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
			for(int i=0; i<Constant.inputNodeNum; i++) {
				Node inputNode = network.getLayerList().get(0).getNodeList().get(i);
				temp += inputHiddenWeight[i][j]*inputNode.getOutputValue();
			}
			temp = temp + inputHiddenBias[j];
			hiddenNode.setOutputValue(func.logS(temp));
		}
		for(int k=0; k<Constant.outputNodeNum; k++) {
			double temp = 0;
			Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
			for(int j=0; j<Constant.hiddenNodeNum; j++) {
				Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
				temp += hiddenOutputWeight[j][k]*hiddenNode.getOutputValue();
			}
			temp = temp + hiddenOutputBias[k];
			outputNode.setOutputValue(func.logS(temp));
		}
	}
	
	public static void backPropagation(Data data, Network network) {
		for(int k=0; k<Constant.outputNodeNum; k++) {
			Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
			double temp = outputNode.getOutputValue()*(1-outputNode.getOutputValue())
					*(outputNode.getOutputValue()-data.getAttributionList().get(k+Constant.inputNodeNum));
			//System.out.print(data.getAttributionList().get(k+Constant.inputNodeNum)+" ");
			outputNode.setOutputError(temp);
			//System.out.print(outputNode.getOutputError()+" ");
		}
		//System.out.println();
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
			double sum = 0;
			for(int k=0; k<Constant.outputNodeNum; k++) {
				Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
				sum += hiddenOutputWeight[j][k]*outputNode.getOutputError();
			}
			double temp = hiddenNode.getOutputValue()*(1-hiddenNode.getOutputValue())*sum;
			hiddenNode.setHiddenError(temp);
		}
	}
	
	public static void updateWeightAndBias(Network network) {
		for(int i=0; i<Constant.inputNodeNum; i++) {
			Node inputNode = network.getLayerList().get(0).getNodeList().get(i);
			for(int j=0; j<Constant.hiddenNodeNum; j++) {
				Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
				inputHiddenWeight[i][j] -= Constant.eta*inputNode.getOutputValue()*hiddenNode.getHiddenError();
			}
		}
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
			inputHiddenBias[j] -= Constant.eta*hiddenNode.getHiddenError();
		}
		for(int j=0; j<Constant.hiddenNodeNum; j++) {
			Node hiddenNode = network.getLayerList().get(1).getNodeList().get(j);
			for(int k=0; k<Constant.outputNodeNum; k++) {
				Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
				hiddenOutputWeight[j][k] -= Constant.eta*hiddenNode.getOutputValue()*outputNode.getOutputError();
			}
		}
		for(int k=0; k<Constant.outputNodeNum; k++) {
			Node outputNode = network.getLayerList().get(2).getNodeList().get(k);
			hiddenOutputBias[k] -= Constant.eta*outputNode.getOutputError();
		}
	}
}

