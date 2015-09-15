package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import model.featureselection.FeaturesSelection;
import model.featureselection.LabelSelection;
import model.util.factory.FactoryFeatureSelection;

public class MainGraphLabelSelection {

	public static void main(String[] args) throws IOException {
		FeaturesSelection fs = (FeaturesSelection) FactoryFeatureSelection.getGraphFeatureSelecter("/home/dugue/Dropbox/LORIA/DATA/Vieillissement/graph_int","/home/dugue/Dropbox/LORIA/DATA/Vieillissement/graph_int_oslo_files/tp.clst","/home/dugue/Dropbox/LORIA/DATA/Vieillissement/labels");
		LabelSelection ls = new LabelSelection(fs);
		int f;
		float ff;
		String label;
		FileWriter fw = new FileWriter("/home/dugue/Dropbox/LORIA/DATA/Vieillissement/tp.fs");
		for (int cluster =0; cluster < fs.getNbCluster(); cluster++) {
			fw.write("\n------------CLUSTER "+cluster+ " ------------------+\n");
			for (Iterator<Integer> it=ls.getPrevalentFeatureSet(cluster).iterator(); it.hasNext();) {
				f=it.next();
				ff=fs.getFeatureValue(f, cluster);
				if (fs.getClusterOfObjectI(f) == cluster) {
					label=fs.getLabelOfCol(f);
					fw.write(label+" "+ ff+" | ");
				}
			}
			
		}
		fw.close();
	}

}
