package data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import general.Constant;

public class ReadData {
	
	double[] tempData = new double[Constant.inputNodeNum+Constant.outputNodeNum];
	
	public ArrayList<Data> readData(String readFilename, int startIndex, int endIndex) {
		ArrayList<Data> dataList = new ArrayList<Data>();
		File file = new File(readFilename);
		BufferedReader bufRdr = null;
		try {
			bufRdr = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = null;
		int index = 0;
		try {
			while((line = bufRdr.readLine()) != null){
				if(index>=startIndex && index <= endIndex) {
					int col = -1;
					double max = 0;
					double min = 0;
					Data data = new Data();
					StringTokenizer st = new StringTokenizer(line,",");
					while (st.hasMoreTokens()){
						if(col == -1) {
							transfer(st.nextToken());
						}
						else {
							tempData[col] = Double.valueOf(st.nextToken());
							if(tempData[col] > max) {
								max = tempData[col];
							}
							if(tempData[col] < min) {
								min = tempData[col];
							}
						}
						col++;
					}
					
					double temp = 0;
					for(int i=0; i<Constant.inputNodeNum; i++) {
						temp = (tempData[i]-min)/(max-min); // normalize
						data.getAttributionList().add(temp);
					}
					for(int k=Constant.inputNodeNum; k<Constant.inputNodeNum+Constant.outputNodeNum; k++) {
						data.getAttributionList().add(tempData[k]);
					}
					dataList.add(data);
				}
				if(index > endIndex) {
					break;
				}
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}	
		try {
			bufRdr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return dataList;
	}
	
	public void calculate(int pos) {
		for(int k=Constant.inputNodeNum; k<Constant.inputNodeNum+Constant.outputNodeNum; k++) {
			tempData[k] = 0;
		}
		tempData[Constant.inputNodeNum+pos] = 1;
	}
	
	public void transfer(String str) {
		if(str.equals("A")) {
			calculate(25);
		}
		if(str.equals("B")) {
			calculate(24);
		}
		if(str.equals("C")) {
			calculate(23);
		}
		if(str.equals("D")) {
			calculate(22);
		}
		if(str.equals("E")) {
			calculate(21);
		}
		if(str.equals("F")) {
			calculate(20);
		}
		if(str.equals("G")) {
			calculate(19);
		}
		if(str.equals("H")) {
			calculate(18);
		}
		if(str.equals("I")) {
			calculate(17);
		}
		if(str.equals("J")) {
			calculate(16);
		}
		if(str.equals("K")) {
			calculate(15);
		}
		if(str.equals("L")) {
			calculate(14);
		}
		if(str.equals("M")) {
			calculate(13);
		}
		if(str.equals("N")) {
			calculate(12);
		}
		if(str.equals("O")) {
			calculate(11);
		}
		if(str.equals("P")) {
			calculate(10);
		}
		if(str.equals("Q")) {
			calculate(9);
		}
		if(str.equals("R")) {
			calculate(8);
		}
		if(str.equals("S")) {
			calculate(7);
		}
		if(str.equals("T")) {
			calculate(6);
		}
		if(str.equals("U")) {
			calculate(5);
		}
		if(str.equals("V")) {
			calculate(4);
		}
		if(str.equals("W")) {
			calculate(3);
		}
		if(str.equals("X")) {
			calculate(2);
		}
		if(str.equals("Y")) {
			calculate(1);
		}
		if(str.equals("Z")) {
			calculate(0);
		}
	}
}