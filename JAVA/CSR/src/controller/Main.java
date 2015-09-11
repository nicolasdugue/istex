package controller;

import java.io.FileNotFoundException;

import view.View;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		View v = new View();
		Controller c = new Controller(v);
		/*
		 * Whole experiment with diachronism and labels on a word co-occurence
		 * network
		 
		String[] params = { "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double",
				"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double",
				"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_7_level2",
				"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_22_level1",
				"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels",
				"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels" };*/
		
		String[] params = { "00-0350gnge-tn-DD-ENL.fmgs",
				"96-99-F43gnge-tn-DD-ENL.fmgs"};

		// String[] params = {""};

		c.doRunStuff(params);
	}

}
