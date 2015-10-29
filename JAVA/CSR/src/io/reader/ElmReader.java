package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.reader.interfaces.IClusteringReader;
import model.cluster.Clustering;
import util.LineNumber;

public class ElmReader  implements IClusteringReader{
	private Clustering clusters;
	private String fileName;
	public ElmReader(String fileName) throws IOException {
		clusters=new Clustering(LineNumber.getNbLines(fileName));
		this.fileName = fileName;
		readAndFillModel();
	}
	private void readAndFillModel() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		int cluster;
		boolean debut=true;
		while (sc.hasNextLine()) {
			//each line 
			cluster=Integer.parseInt(sc.nextLine().split(" ")[0]);
			if (debut) {
				debut=false;
			}
			else
				clusters.add(cluster);
		}
		sc.close();
	}
	public Clustering getClusters() {
		return clusters;
	}
	public void setClusters(Clustering clusters) {
		this.clusters = clusters;
	}
}
