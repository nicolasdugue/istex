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

import model.featureselection.FeaturesSelection;
import model.roles.FunctionalCartography;
import model.util.factory.AFactory;
import model.util.factory.GraphFactory;
import model.util.factory.MatrixFactory;
import view.View;

public class MainFeatureSelection {

	private static HelpFormatter formatter = new HelpFormatter();
	private static CommandLineParser parser = new DefaultParser();
	private static Options options = new Options();
	public static void main(String[] args) throws FileNotFoundException {
		
		options.addOption("h", "help", false, "print this message");
		options.addOption("g", "graph", false, "read a list of arcs instead of a matrix");
		options.addOption("r", "roles", false, "Computes community roles");
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("matrix");
		OptionBuilder.withDescription(
				"Source matrix, One row per line. Element separated with spaces or tabs.");
		Option opt = OptionBuilder.create("m");
		options.addOption(opt);
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("clustering");
		OptionBuilder.withDescription(
				"Source clustering. One integer per line indicated the belonging cluster of each row.");
		opt = OptionBuilder.create("c");
		options.addOption(opt);
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("labels");
		OptionBuilder.withDescription("Source labels to use to run the diachronism algorithm");
		opt = OptionBuilder.create("l");
		options.addOption(opt);
		OptionBuilder.hasArgs(1);
		OptionBuilder.withArgName("output");
		OptionBuilder.withDescription("Output directory");
		opt = OptionBuilder.create("o");
		options.addOption(opt);

		//args = new String[] { "-c", "t", "test" };

		

		AFactory factory = new MatrixFactory();
		String m1=null;
		String c1=null;
		String l1=null;
		try {
			// parse the command line arguments
			CommandLine line = parser.parse(options, args);
			boolean process = check(line, options);
			if (process) {
				if (line.hasOption("h")) {
					printHelp();
				}
				if (line.hasOption("g")) {
					factory = new GraphFactory();
				}
				m1 = line.getOptionValues("m")[0];
				c1 = line.getOptionValues("c")[0];
				if (line.hasOption("l")) {
					l1 = line.getOptionValues("l")[0];
				}

				View v = new View();

				Controller c = new Controller(v, factory);
				FeaturesSelection fs;
				if (l1 == null)
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1);
				else
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1,l1);
				String output =line.getOptionValues("o")[0];
				
				//TODO - Make a proposer controller
				if (line.hasOption("r")) {
					FunctionalCartography fc = new FunctionalCartography(fs.getMatrix());
					fc.doZScore();
					for (int i = 0; i < fs.getNbRows(); i++) {
						System.out.println(i + " " + fc.getZScore(i) + " "+fc.getParticipationCoefficient(i));
					}
				}
				else {
					for (int i = 0; i < fs.getNbRows(); i++) {
						System.out.println(i + " " + fs.fp(i, fs.getClusterOfObjectI(i)) + " " + fs.fr(i, fs.getClusterOfObjectI(i)) + " " + fs.getFeatureValue(i, fs.getClusterOfObjectI(i)));
					}
				}
				
			}

		} catch (ParseException exp) {
			System.out.println("Unexpected exception:" + exp.getMessage());
		}
	}
	
	//TODO Responsability chain to make it cleaner and more modular
		public static boolean check(CommandLine line, Options options) {
			HelpFormatter formatter = new HelpFormatter();
			boolean okay=true;
			if (line.hasOption("g") || line.hasOption("m")) {
				if (!line.hasOption("m")) {
					System.out.println("You need to provide matrix files with -m option");
					okay=false;
				}
				else if (line.getOptionValues("m").length < 1) {
					System.out.println("You need to provide a matrix file with -m option");
					okay=false;
				}
				if (!line.hasOption("c")) {
					System.out.println("You need to provide clustering files with -c option");
					okay=false;
				}
				else if (line.getOptionValues("c").length < 1) {
					System.out.println("You need to provide a clustering file with -c option");
					okay=false;
				}
				if (line.hasOption("l") && (line.getOptionValues("l").length <1)) {
					System.out.println("You need to provide a label file with -l option. If you don't want to use labels, don't use the -l option.");
					okay=false;
				}
				if (!line.hasOption("o")) {
					okay=false;
					System.out.println("You need to provide an output file with -o option");
				}
			}
			else {
				okay=false;
			}
			if (!okay) {
				printHelp();
			}
			return okay;

		}
		
		public static void printHelp() {
			formatter.printHelp("Feature selection, node ", options);
		}
}
