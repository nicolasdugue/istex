package model.featureselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import model.util.LabelStore;
import util.LineNumber;

public class LabelSelectionFromFile implements ILabelSelection {

	private ArrayList<ArrayList<Integer>> labels = new ArrayList<ArrayList<Integer>>();
	private ArrayList<Float> featuresWeights;
	private LabelStore ls;
	private String fileName;
	private LabelStore clusterLabels;
	
	
	
	public LabelSelectionFromFile(String fileName) throws IOException {
		super();
		this.fileName = fileName;
		this.clusterLabels = new LabelStore(LineNumber.getNbLines(fileName));
		featuresWeights = new ArrayList<Float>();
		labels = new ArrayList<ArrayList<Integer>>();
		ls = new LabelStore(this.clusterLabels.getSize());
		readAndFill();
	}
	private void readAndFill() throws FileNotFoundException {
		Scanner sc = new Scanner(new File(fileName));
		String line;
		boolean first = true;
		int i=0;
		ArrayList<Integer> labelRead = new ArrayList<Integer>();
		String[] tabLine;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			//If this, then the line describes a new cluster
			if (line.startsWith("G")) {
				clusterLabels.addLabel(line);
				if (first) {
					first=false;
				}
				else {
					labels.add(labelRead);
					labelRead = new ArrayList<Integer>();
				}
			}
			else  {
				tabLine=line.split("\t");
				if (tabLine.length > 1) {
					labelRead.add(i);
					ls.addLabel(tabLine[1]);
					featuresWeights.add(Float.parseFloat(tabLine[2]));
					i++;
				}
			}
		}
		labels.add(labelRead);
		sc.close();
	}

	@Override
	public ArrayList<Integer> getPrevalentFeatureSet(int cluster) {
		return labels.get(cluster);
	}

	@Override
	public ArrayList<String> getLabelSet(int cluster) {
		ArrayList<Integer> features = getPrevalentFeatureSet(cluster);
		ArrayList<String> l = new ArrayList<String>(features.size());
		for (int f : features) {
			l.add(this.getLabelOfCol(f));
		}
		return l;
	}

	@Override
	public int getNbCluster() {
		return labels.size();
	}

	@Override
	public String getLabelOfCol(int j) {
		return ls.getLabel(j);
	}

	@Override
	public int getIndexOfColLabel(String label) {
		return ls.getIndexOfLabel(label);
	}

	@Override
	public float getFeatureFMeanValue(int f) {
		return featuresWeights.get(f);
	}
	@Override
	public String getLabelOfCluster(int cluster) {
		return clusterLabels.getLabel(cluster);
	}
	@Override
	public int getClusterOfLabel(String s) {
		return clusterLabels.getIndexOfLabel(s);
	}

}
