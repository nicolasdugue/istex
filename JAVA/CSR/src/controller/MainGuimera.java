package controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import model.featureselection.FeaturesSelection;
import model.roles.FunctionalCartography;
import model.util.factory.FactoryFeatureSelection;

public class MainGuimera {

	public static void main(String[] args) throws IOException {
		System.out.println(args.length);
		FeaturesSelection fs = (FeaturesSelection) FactoryFeatureSelection.getFeatureSelecter("../Guimera/"+args[0],
				"../Guimera/"+args[1]);
		FunctionalCartography fc = new FunctionalCartography(fs.getMatrix());
		
		int index = args[0].lastIndexOf("/");
		String path=args[0].substring(0, index)+"/";
		FileWriter fw = new FileWriter(new File("../Guimera/"+path+"Guimera"));
		FileWriter fw_r = new FileWriter(new File("../Guimera/"+path+"README_Guimera"));
		fc.doZScore();
		for (int i = 0; i < fs.getNbRows(); i++) {
			fw.write(i + " " + fs.fp(i, fs.getClusterOfObjectI(i)) + " " + fs.fr(i, fs.getClusterOfObjectI(i))
					+ " " + fc.getZScore(i) + " "+fc.getParticipationCoefficient(i) + " " + fc.getDegree(i) + " "
					+ fc.getSizeCommunity(fs.getClusterOfObjectI(i))+"\n");
		}
		fw.close();
		fw_r.write("Node;FeaturePrecision;FeatureRecall;Zscore;P;Degree;CommunitySize");
		fw_r.close();
	}

}
