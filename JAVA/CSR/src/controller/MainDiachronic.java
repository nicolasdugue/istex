package controller;

import java.io.FileNotFoundException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import model.util.factory.AFactory;
import model.util.factory.GraphFactory;
import model.util.factory.MatrixFactory;

import view.View;

@SuppressWarnings("deprecation")
public class MainDiachronic {

	/*
	 * Whole experiment with diachronism and labels on a word co-occurence
	 * network
	 * 
	 * String[] params = {
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double",
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_arcs_double",
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_7_level2",
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/com_22_level1",
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels",
	 * "/home/dugue/Dropbox/LORIA/JAVA/dataset/adjnoun/adjnoun.net_labels" };
	 */

	public static void main(String[] args) throws FileNotFoundException {
		CommandLineParser parser = new DefaultParser();

		// create the Options
		Options options = new Options();
		options.addOption("h", "help", false, "print this message");
		options.addOption("q", "quiet", false, "be extra quiet");
		options.addOption("v", "verbose", false, "be extra verbose");
		options.addOption("e", "example", false, "run exemple .");
		options.addOption("g", "graph", false, "read a list of arcs instead of a matrix");
		options.addOption("log", "logfile", true, "used to log in a specific file");
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("matrixSource> <matrixTarget");
		OptionBuilder.withDescription(
				"Matrices source and target. One row per line. Element separated with spaces or tabs.");
		Option opt = OptionBuilder.create("m");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("clusteringSource> <clusteringTarget");
		OptionBuilder.withDescription(
				"Clustering source and target. One integer per line indicated the belonging cluster of each row.");
		opt = OptionBuilder.create("c");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("featuresLabelSource> <featuresLabelTarget");
		OptionBuilder.withDescription(
				"Features labels source and target. One String per line indicated the corresponding label of each feature.");
		opt = OptionBuilder.create("fl");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("labelsSource> <labelsTarget");
		OptionBuilder.withDescription("Labels source and target to use to run the diachronism algorithm");
		opt = OptionBuilder.create("l");
		options.addOption(opt);

		args = new String[] { "-c", "caca", "pue" };

		HelpFormatter formatter = new HelpFormatter();

		AFactory factory = new MatrixFactory();
		String m1, m2;
		String c1, c2;
		String fl1, fl2;
		String l1, l2;
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			boolean process = check(line, options);
			if (process) {
				if (line.hasOption("log")) {
					// TODO logger
				}
				if (line.hasOption("h")) {
					formatter.printHelp("MainDiachronic", options);
				}
				if (line.hasOption("v")) {
					// TODO verbose
				}
				if (line.hasOption("v")) {
					// TODO quiet
				}
	
				if (line.hasOption("e")) {
					// TODO exemple, create exemple factory and automatic run in
					// verbose mode
				}
				if (line.hasOption("g")) {
					factory = new GraphFactory();
				}
				if (line.hasOption("m")) {
					m1 = line.getOptionValues("m")[0];
					m2 = line.getOptionValues("m")[1];
					c1 = line.getOptionValues("c")[0];
					c2 = line.getOptionValues("c")[1];
					
				}
				if (line.hasOption("l")) {
					l1 = line.getOptionValues("l")[0];
					l2 = line.getOptionValues("l")[1];
				}
				// TODO use commons cli to get properly the params
				String[] params = { "00-0350gnge-tn-DD-ENL.fmgs", "96-99-F43gnge-tn-DD-ENL.fmgs" };

				View v = new View();

				Controller c = new Controller(v, factory);

				c.doRunStuff(params);
			}

		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}

		
	}
	//TODO Responsability chain to make it cleaner and more modular
	public static boolean check(CommandLine line, Options options) {
		HelpFormatter formatter = new HelpFormatter();
		boolean okay=true;
		if (line.hasOption("g") || line.hasOption("v") || line.hasOption("q") || line.hasOption("log") ) {
			if (!line.hasOption("m")) {
				System.out.println("You need to provide matrix files with -m option");
				okay=false;
			}
			else if (line.getOptionValues("m").length <2) {
				System.out.println("You need to provide TWO matrix files with -m option");
				okay=false;
			}
			if (!line.hasOption("c")) {
				System.out.println("You need to provide clustering files with -c option");
				okay=false;
			}
			else if (line.getOptionValues("c").length <2) {
				System.out.println("You need to provide TWO clustering files with -c option");
				okay=false;
			}
			
		}
		else if (line.hasOption("m")) {
			if (line.getOptionValues("m").length <2) {
				System.out.println("You need to provide TWO matrix files with -m option");
			}
			if (!line.hasOption("c")) {
				System.out.println("You need to provide clustering files with -c option");
				okay=false;
			}
			else if (line.getOptionValues("c").length <2) {
				System.out.println("You need to provide TWO clustering files with -c option");
				okay=false;
			}
		}
		if (!okay)
			formatter.printHelp("MainDiachronic", options);
		return okay;

	}

}