package controller;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import model.featureselection.LabelSelection;
import model.featureselection.labellingstategies.FeatureSelectionStrategy;
import model.featureselection.labellingstategies.FeatureThresholdStrategy;
import model.featureselection.labellingstategies.FixedNumberOfLabelStrategy;
import model.featureselection.labellingstategies.ILabelSelectionStrategy;
import model.util.factory.AFactory;
import model.util.factory.GraphElmFactory;
import model.util.factory.GraphFactory;
import model.util.factory.MatrixElmFactory;
import model.util.factory.MatrixFactory;
import model.util.factory.NrmClusteringFactory;
import model.util.factory.NrmElmFactory;
import util.SGLogger;
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
	private static HelpFormatter formatter = new HelpFormatter();
	private static CommandLineParser parser = new DefaultParser();
	private static Options options = new Options();
	public static void main(String[] args) throws FileNotFoundException {
		
		// create the Options
		options.addOption("h", "help", false, "print this message");
		options.addOption("q", "quiet", false, "be extra quiet");
		options.addOption("v", "verbose", false, "be extra verbose");
		//options.addOption("e", "example", false, "run exemple .");
		options.addOption("g", "graph", false, "read a list of arcs instead of a matrix");
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("matrixSource> <matrixTarget");
		OptionBuilder.withDescription(
				"Source and target matrices. Fomat : One row per line, element separated with spaces or tabs. NRM files also allowed.");
		Option opt = OptionBuilder.create("m");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("clusteringSource> <clusteringTarget");
		OptionBuilder.withDescription(
				"Source and target clustering. One integer per line indicated the belonging cluster of each row. ELM files also allowed.");
		opt = OptionBuilder.create("c");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("featuresLabelSource> <featuresLabelTarget");
		OptionBuilder.withDescription(
				"Source and target features labels. One String per line indicated the corresponding label of each feature.");
		opt = OptionBuilder.create("fl");
		options.addOption(opt);
		OptionBuilder.hasArgs(2);
		OptionBuilder.withArgName("labelsSource> <labelsTarget");
		OptionBuilder.withDescription("Labels source and target to use to run the diachronism algorithm");
		opt = OptionBuilder.create("l");
		options.addOption(opt);
		
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("threshold");
		OptionBuilder.withDescription("Select label with a threshold on feature selection.");
		opt = OptionBuilder.create("lst");
		options.addOption(opt);
		OptionBuilder.hasArgs(0);
		OptionBuilder.withArgName("feature");
		OptionBuilder.withDescription("Select label with feature selection. Automatic.");
		opt = OptionBuilder.create("lsf");
		options.addOption(opt);
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("number");
		OptionBuilder.withDescription("Select a fixed number of labels per cluster.");
		opt = OptionBuilder.create("lsn");
		options.addOption(opt);

		//args = new String[] { "-c", "t", "test" };

		

		AFactory factory = new MatrixFactory();
		String m1=null, m2=null;
		String c1=null, c2=null;
		String fl1, fl2;
		String l1=null, l2=null;
		Logger log = SGLogger.getInstance();
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			boolean process = check(line, options);
			int nbParam=0;
			if (process) {

				if (line.hasOption("h")) {
					printHelp();
				}
				if (line.hasOption("v")) {
					log.setLevel(Level.DEBUG);
				}
				if (line.hasOption("q")) {
					log.setLevel(Level.FATAL);
				}
	
				if (line.hasOption("e")) {
					//TODO Diachronic Exemple
				}
				else if (line.hasOption("fl")) {
					View v = new View();
					Controller c;
					c = new Controller(v);
					c.doRunDiachronism(line.getOptionValues("fl")[0],line.getOptionValues("fl")[1]);
					return;
				}
				if (line.hasOption("m")) {
					m1 = line.getOptionValues("m")[0];
					m2 = line.getOptionValues("m")[1];
					c1 = line.getOptionValues("c")[0];
					c2 = line.getOptionValues("c")[1];
					nbParam+=4;
				}
				if (line.hasOption("l")) {
					l1 = line.getOptionValues("l")[0];
					l2 = line.getOptionValues("l")[1];
					nbParam+=2;
				}
				if (line.hasOption("g")) {
					if (c1.contains(".elm"))
						factory = new GraphElmFactory();
					else
						factory = new GraphFactory();
				}
				if (line.hasOption("m") && m1.contains(".nrm")) {
					if (c1.contains(".elm"))
						factory = new NrmElmFactory();
					else
						factory = new NrmClusteringFactory();
				}
				else {
					if (line.hasOption("m") && c1.contains(".elm"))
						factory = new MatrixElmFactory();
				}
				View v = new View();
				Controller c;
				
				if (line.hasOption("lsn")) {
					FixedNumberOfLabelStrategy lss = new FixedNumberOfLabelStrategy();
					lss.setNumberOfLabels(Integer.parseInt(line.getOptionValues("lsn")[0]));
					c = new Controller(v, factory, lss);
				}
				else if (line.hasOption("lst")){
					FeatureThresholdStrategy lss = new FeatureThresholdStrategy();
					lss.setThreshold(Float.parseFloat(line.getOptionValues("lst")[0]));
					c = new Controller(v, factory,lss);
				}
				else {
					c = new Controller(v, factory);
				}
				

				c.doRunDiachronism(m1,m2,c1,c2,l1,l2);
			}

		} catch (ParseException exp) {
			log.fatal("Unexpected exception:" + exp.getMessage());
		} catch (IOException e) {
			log.fatal(e);
		}

		
	}
	//TODO Responsability chain to make it cleaner and more modular
	public static boolean check(CommandLine line, Options options) {
		HelpFormatter formatter = new HelpFormatter();
		Logger log=SGLogger.getInstance();
		boolean okay=true;
		if (line.hasOption("g") || line.hasOption("m")) {
			if (!line.hasOption("m")) {
				log.warn("You need to provide matrix files with -m option");
				okay=false;
			}
			else if (line.getOptionValues("m").length <2) {
				log.warn("You need to provide TWO matrix files with -m option");
				okay=false;
			}
			if (!line.hasOption("c")) {
				log.warn("You need to provide clustering files with -c option");
				okay=false;
			}
			else if (line.getOptionValues("c").length <2) {
				log.warn("You need to provide TWO clustering files with -c option");
				okay=false;
			}
			if (line.hasOption("l") && (line.getOptionValues("l").length <2)) {
				log.warn("You need to provide TWO label files with -c option. If you don't want to use labels, don't use the -l option.");
				okay=false;
			}
			
		}
		else if (line.hasOption("fl")) {
			if (line.getOptionValues("fl").length <2) {
				log.warn("You need to provide TWO feature label files with -fl option");
				okay=false;
			}
		}
		else {
			printHelp();
			return false;
		}
		if (!okay)
			printHelp();
		return okay;

	}
	
	public static void printHelp() {
		formatter.printHelp("MainDiachronic, Diachronism toolbox", options);
	}

}
