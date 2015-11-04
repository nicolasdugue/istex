package io.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import io.reader.interfaces.IClusteringReader;
import model.cluster.Clustering;
import util.LineNumber;

/**
 * Allows to read Clustering file descriptor written as follows : each line i contains and integer k that indicates that object i from the matrix (line) belongs to clusters k
 * 
 * @author dugue
 *
 */
public class ClusteringReader implements IClusteringReader {
	private Clustering clusters;
	private String fileName;
	public ClusteringReader(String fileName) throws IOException {
		super();
		clusters=new Clustering(LineNumber.getNbLines(fileName));
		this.fileName = fileName;
		readAndFillModel();
	}
	private void readAndFillModel() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		int cluster;
		while (sc.hasNextLine()) {
			//each line 
			cluster=Integer.parseInt(sc.nextLine());
			clusters.add(cluster);
		}
		sc.close();
		clusters.clusteringLoaded();
	}
	public Clustering getClusters() {
		return clusters;
	}
	public void setClusters(Clustering clusters) {
		this.clusters = clusters;
	}
	
}
