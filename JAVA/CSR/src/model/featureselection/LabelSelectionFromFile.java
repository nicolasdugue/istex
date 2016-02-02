package model.featureselection;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.log4j.Logger;

import model.util.LabelStore;
import util.LineNumber;
import util.SGLogger;

public class LabelSelectionFromFile implements ILabelSelection {

	private ArrayList<ArrayList<Integer>> labels = new ArrayList<ArrayList<Integer>>();
	private ArrayList<ArrayList<Float>> featuresWeights;
	private LabelStore ls;
	private String fileName;
	private LabelStore clusterLabels;
	private Logger logger = SGLogger.getInstance();
	
	
	
	public LabelSelectionFromFile(String fileName) throws IOException {
		super();
		this.fileName = fileName;
		this.clusterLabels = new LabelStore(LineNumber.getNbLines(fileName));
		featuresWeights = new ArrayList<ArrayList<Float>>();
		labels = new ArrayList<ArrayList<Integer>>();
		ls = new LabelStore(this.clusterLabels.getSize());
		readAndFill();
		/*int i =0;
		String log="";
		for (ArrayList<Integer> clusterLabels : labels) {
			logger.debug("Cluster " + i);
			log="";
			for (int label : clusterLabels) {
				log+="\nLabel " + ls.getLabel(label)+ " "+label + ", weight : "+this.getFeatureValue(label, i)+" \n";
			}
			logger.debug(log);
			i++;
		}
		logger.debug("!!!!!-----------Group "+this.getIndexOfColLabel("group")+"----------!!!");
		
		logger.debug("!!!!!-----------Group in cluster 6 "+this.getFeatureValue(this.getIndexOfColLabel("group"),6)+"----------!!!");
		*/
	}
	private void readAndFill() throws FileNotFoundException {
		
		//-------------------------FMGS
		if (fileName.contains(".fmgs")) {
			logger.debug("fmgs file");
			Scanner sc = new Scanner(new File(fileName));
			String line;
			boolean first = true;
			int i=0;
			ArrayList<Integer> labelRead = new ArrayList<Integer>();
			ArrayList<Float> weights = new ArrayList<Float>();
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
						featuresWeights.add(weights);
						weights =new ArrayList<Float>();
						labelRead = new ArrayList<Integer>();
					}
				}
				else  {
					tabLine=line.split("\t");
					if (tabLine.length > 1) {
						if (ls.containsKey(tabLine[1])) {
							labelRead.add(ls.getIndexOfLabel(tabLine[1]));
							weights.add(Float.parseFloat(tabLine[2]));
						}
						else {
							labelRead.add(i);
							ls.addLabel(tabLine[1]);
							weights.add(Float.parseFloat(tabLine[2]));
							i++;
						}
					}
				}
			}
			labels.add(labelRead);
			featuresWeights.add(weights);
			sc.close();
		}
		//-------------------------dcfs ou dfms
		else {
			logger.debug("dcfs or dfms file");
			Scanner sc = new Scanner(new File(fileName));
			String line;
			boolean first = true;
			int i=0;
			ArrayList<Integer> labelRead = new ArrayList<Integer>();
			ArrayList<Float> weights = new ArrayList<Float>();
			String[] tabLine;
			while (sc.hasNextLine()) {
				line = sc.nextLine();
				//If this, then the line describes a new cluster
				if (line.startsWith("Classe")) {
					clusterLabels.addLabel(line);
					if (first) {
						first=false;
					}
					else {
						labels.add(labelRead);
						featuresWeights.add(weights);
						weights =new ArrayList<Float>();
						labelRead = new ArrayList<Integer>();
					}
				}
				else  {
					tabLine=line.split(" ");
					if (tabLine.length > 1) {
						if (ls.containsKey(tabLine[1])) {
							labelRead.add(ls.getIndexOfLabel(tabLine[1]));
							weights.add(Float.parseFloat(tabLine[0]));
						}
						else {
							labelRead.add(i);
							ls.addLabel(tabLine[1]);
							weights.add(Float.parseFloat(tabLine[0]));
							i++;
						}
					}
				}
			}
			labels.add(labelRead);
			featuresWeights.add(weights);
			sc.close();
		}
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
		float mean=0.0f;
		int inCluster=0;
		float value;
		for (int c =0; c < labels.size(); c++) {
			try {
				value=this.getFeatureValue(f, c);
				mean+=value;
				inCluster++;
			}
			catch(Exception e) {
			}
		}
		return mean / inCluster;
	}
	@Override
	public String getLabelOfCluster(int cluster) {
		return clusterLabels.getLabel(cluster);
	}
	@Override
	public int getClusterOfLabel(String s) {
		return clusterLabels.getIndexOfLabel(s);
	}
	@Override
	public float getFeatureValue(int f, int cluster) {
		ArrayList<Integer> features = labels.get(cluster);
		int i=0;
		for (int feature : features) {
			if (f == feature)
				break;
			i++;
		}
		try {
			return featuresWeights.get(cluster).get(i);
		}
		catch (IndexOutOfBoundsException e){
			return 0.0f;
		}
	}
	@Override
	public String getAutomaticNameForCluster(int cluster) {
		ArrayList<Float> weights = this.featuresWeights.get(cluster);
		ArrayList<String> features = this.getLabelSet(cluster);
		float max1=-1, max2=-1;
		String f1="", f2="";
		float w;
		for (int i = 0; i < weights.size(); i++) {
			w=weights.get(i);
			if (w > max1) {
				max2=max1;
				f2=f1;
				f1=features.get(i);
				max1=w;
			}
			else if (w > max2) {
				max2=w;
				f2=features.get(i);
			}
		}
		return f1 + " " +f2;
	}

}
