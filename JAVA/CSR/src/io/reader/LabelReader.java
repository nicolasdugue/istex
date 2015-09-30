package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import io.reader.interfaces.ILabelReader;
import model.util.LabelStore;

public class LabelReader implements ILabelReader {
	private LabelStore ls;
	private String fileName;
	public LabelReader(String fileName) throws FileNotFoundException {
		super();
		this.fileName = fileName;
		ls = new LabelStore();
		readAndFillModel();
	}
	private void readAndFillModel() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		String s;
		while (sc.hasNextLine()) {
			//each line 
			s=sc.nextLine();
			ls.addLabel(s);
		}
		sc.close();
	}
	public LabelStore getLs() {
		return ls;
	}
	
	
}
