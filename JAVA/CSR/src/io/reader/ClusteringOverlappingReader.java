package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.reader.interfaces.IClusteringReader;
import model.cluster.OverlappingClustering;
import util.LineNumber;

/**
 * Allows to read Clustering file descriptor written as follows : each line i contains and integer k that indicates that object i from the matrix (line) belongs to clusters k
 * 
 * @author dugue
 *
 */
public class ClusteringOverlappingReader implements IClusteringReader {
	private OverlappingClustering clusters;
	private String fileName;
	public ClusteringOverlappingReader(String fileName) throws IOException {
		super();
		clusters=new OverlappingClustering(LineNumber.getNbLines(fileName));
		this.fileName = fileName;
		readAndFillModel();
	}
	private void readAndFillModel() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		String line;
		String[] tab;
		int numLine=0;

		while (sc.hasNextLine()) {
			//each line 
			line = sc.nextLine();
			tab=line.split(",");
			if (!lineCorrectlySplitted(tab)) {
				tab=line.split(";");
				if (!lineCorrectlySplitted(tab)) {
					tab=line.split("|");
				}
			}
			for (int i=0; i < tab.length; i++) {
				clusters.add(numLine,Integer.parseInt(tab[i]));
			}
			numLine++;
		}
		sc.close();
		clusters.clusteringLoaded();
	}
	private boolean lineCorrectlySplitted(String[] tab) {
		for (int i=0; i < tab.length; i++) {
			try {
				Integer.parseInt(tab[i]);
			}
			catch(NumberFormatException e){
		       return false;
		    }
		}
		return true;
		
	}
	public OverlappingClustering getClusters() {
		return clusters;
	}
	public void setClusters(OverlappingClustering clusters) {
		this.clusters = clusters;
	}
	
}
