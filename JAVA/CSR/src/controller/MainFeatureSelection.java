package controller;

import java.io.FileNotFoundException;

import model.featureselection.FeaturesSelection;
import model.roles.FunctionalCartography;
import model.util.factory.GraphFactory;

public class MainFeatureSelection {

	public static void main(String[] args) throws FileNotFoundException {
		FeaturesSelection fs = (FeaturesSelection) new GraphFactory().getFeatureSelecter("Guimera_matrix1000",
				"Guimera_community1000");
		FunctionalCartography fc = new FunctionalCartography(fs.getMatrix());
		fc.doZScore();
		for (int i = 0; i < fs.getNbRows(); i++) {
			System.out.println(i + " " + fs.fp(i, fs.getClusterOfObjectI(i)) + " " + fs.fr(i, fs.getClusterOfObjectI(i))
					+ " " + fc.getZScore(i) + " "+fc.getParticipationCoefficient(i) + " " + fc.getDegree(i) + " "
					+ fc.getSizeCommunity(fs.getClusterOfObjectI(i)));
		}
	}
}
