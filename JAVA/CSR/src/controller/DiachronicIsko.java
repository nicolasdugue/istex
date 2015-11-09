package controller;

import java.io.IOException;

import model.util.factory.AFactory;
import model.util.factory.GraphFactory;
import view.View;

public class DiachronicIsko {
	public static void main(String[] args) throws IOException {
		View v = new View();
		AFactory factory = new GraphFactory();

		Controller c = new Controller(v, factory);

		  
//		  String[] params = {
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.nrm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.nrm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.elm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.elm",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P1.idd",
//		  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.idd" };
		
		
		String[] params = {
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/global_graph_before2005_int",
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/global_graph_after2005_int",
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/communautes/global_graph_before2005_int.cls",
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/communautes/global_graph_after2005_int.cls",
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/global_graph_before2005_labels",
				  "/home/dugue/Dropbox/PartageAli/Nicolas/isko_maghreb/graphe_auteur/global_graphs/global_graph_after2005_labels" };
		  
//		  String[] params = {
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.nrm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.nrm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.elm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.elm",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P2.idd",
//				  "/home/dugue/Dropbox/LORIA/DATA/Vieillissement/Clusterings/DataViel-Pascal/Vieux3P3.idd" };
		 
		
		c.doRunDiachronism(params[0], params[1], params[2], params[3], params[4], params[5]);
	}
}
