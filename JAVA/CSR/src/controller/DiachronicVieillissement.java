package controller;

import java.io.FileNotFoundException;

import model.util.factory.AFactory;
import model.util.factory.VieillissementFactory;
import view.View;

public class DiachronicVieillissement {

	public static void main(String[] args) throws FileNotFoundException{
		View v = new View();
		AFactory factory = new VieillissementFactory();

		Controller c = new Controller(v, factory);

		  
//		  String[] params = {
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.nrm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.nrm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.elm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.elm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.idd",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.idd" };
		
		
		String[] params = {
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.nrm",
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.nrm",
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.elm",
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.elm",
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.idd",
				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.idd" };
		  
//		  String[] params = {
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.nrm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.nrm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.elm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.elm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.idd",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.idd" };
		 
		
		c.doRunStuff(params[0], params[1], params[2], params[3], params[4], params[5]);
	}
}
