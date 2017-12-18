package data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteData {
	
	public void writeData(String write_name, String string) throws IOException {
	 File f=new File(write_name);
	 FileWriter out=new FileWriter(f,true);
	 out.write(string);
	 out.close();
	}
	
	public void clearData(String write_name) {
		 File f=new File(write_name);
		 f.delete();
	}
}