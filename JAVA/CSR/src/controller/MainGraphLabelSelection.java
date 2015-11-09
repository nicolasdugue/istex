package controller;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import model.featureselection.FeaturesSelection;
import model.featureselection.LabelSelection;
import model.featureselection.labellingstategies.FeatureSelectionStrategy;
import model.util.factory.GraphFactory;
import model.util.nuplet.PairSFWeighted;
import model.util.nuplet.collection.SortedLabelSet;

public class MainGraphLabelSelection {

	public static void main(String[] args) throws IOException {
		String graph_file="/home/dugue/Dropbox/LORIA/DATA/Vieillissement/graph_6_int";
		String clustering_file="/home/dugue/Dropbox/LORIA/DATA/Vieillissement/graph_6_int_oslo_files/tp.clst";
		String label_file="/home/dugue/Dropbox/LORIA/DATA/Vieillissement/labels";
		FeaturesSelection fs = (FeaturesSelection) new GraphFactory().getFeatureSelecter(graph_file,clustering_file,label_file);
		LabelSelection ls = new LabelSelection(fs , new FeatureSelectionStrategy(fs));
		int f;
		float ff;
		String label;
		FileWriter fw = new FileWriter(graph_file+".fs");
		SortedLabelSet s = new SortedLabelSet();
		for (int cluster =0; cluster < fs.getNbCluster(); cluster++) {
			fw.write("\n------------CLUSTER "+cluster+ " ------------------+\n");
			s = new SortedLabelSet();
			for (Iterator<Integer> it=ls.getPrevalentFeatureSet(cluster).iterator(); it.hasNext();) {
				f=it.next();
				ff=fs.getFeatureValue(f, cluster);
				if (fs.getClusterOfObjectI(f) == cluster) {
					label=fs.getLabelOfCol(f);
					s.add(new PairSFWeighted(label, ff));
				}
			}
			for (PairSFWeighted pair : s) {
				fw.write(pair.getLeft()+" "+ pair.getRight()+" | ");
			}
			
		}
		fw.close();
	}

}
