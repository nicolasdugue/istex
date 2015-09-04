package controller;

import java.io.FileNotFoundException;

import view.View;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		View v = new View();
		Controller c = new Controller(v);
		String[] params = {"/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double", "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double","/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_7_level2","/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_22_level1","/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels","/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels"};
		c.doRunStuff(params);
	}

}
