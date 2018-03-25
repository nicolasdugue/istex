package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

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

import model.matrix.CsrMatrixClustered;
import model.roles.FunctionalCartography;
import model.featureselection.FeaturesSelection;
import model.featureselection.LabelSelection;
import model.featureselection.labellingstategies.FeatureSelectionStrategy;
import model.featureselection.labellingstategies.FeatureThresholdStrategy;
import model.featureselection.labellingstategies.FixedNumberOfLabelStrategy;
import model.util.factory.AFactory;
import model.util.factory.GraphElmFactory;
import model.util.factory.GraphFactory;
import model.util.factory.MatrixElmFactory;
import model.util.factory.MatrixFactory;
import model.util.factory.NrmClusteringFactory;
import model.util.factory.NrmElmFactory;
import model.util.nuplet.PairSFWeighted;
import model.util.nuplet.collection.SortedLabelSet;
import util.SGLogger;

public class MainGraphMeasures {

	private static HelpFormatter formatter = new HelpFormatter();
	private static CommandLineParser parser = new DefaultParser();
	private static Options options = new Options();
	private static Logger log=SGLogger.getInstance();

	public static void main(String[] args) {

		options.addOption("h", "help", false, "print this message");
		options.addOption("g", "graph", false, "read a list of arcs instead of a matrix");
		//options.addOption("r", "roles", false, "Computes community roles using Guimerà & Amaral measures");
		options.addOption("log", "log", false, "log events");
		options.addOption("v", "verbose", false, "debug or verbose mode");
		options.addOption("e", "exemple", false, "Run exemple");
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
		OptionBuilder.withDescription("Output file without ext");
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
				if (line.hasOption("e")) {
					log.setLevel(Level.INFO);
					MainDiachronic m = new MainDiachronic();
					URL url = m.getClass().getClassLoader().getResource("matrix_lamirel_iskomaghreb");
					if (url.toString().startsWith("jar:")) {
						m1=createTempExemple("matrix_lamirel_iskomaghreb");
					}
					else
						m1=url.getFile();
					url = m.getClass().getClassLoader().getResource("clustering_lamirel_iskomaghreb");
					if (url.toString().startsWith("jar:")) {
						c1=createTempExemple("clustering_lamirel_iskomaghreb");
					}
					else
						c1=url.getFile();
					url = m.getClass().getClassLoader().getResource("label_lamirel_iskomaghreb");
					if (url.toString().startsWith("jar:")) {
						l1=createTempExemple("label_lamirel_iskomaghreb");
					}
					else
						l1=url.getFile();
				}
				else {
					if (line.hasOption("h")) {
						printHelp();
					}
					else {
						if (line.hasOption("log")) {
							log.setLevel(Level.INFO);
						}
						if (line.hasOption("v")) {
							log.setLevel(Level.DEBUG);
						}
						m1 = line.getOptionValues("m")[0];
						c1 = line.getOptionValues("c")[0];
						if (line.hasOption("l")) {
							l1 = line.getOptionValues("l")[0];
						}
						
						if (line.hasOption("g")) {
							if (c1.contains(".elm"))
								factory = new GraphElmFactory();
							else
								factory = new GraphFactory();
						}
						if (m1.contains(".nrm")) {
							log.debug("Matrice nrm");
							if (c1.contains(".elm"))  {
								log.debug("Clustering elm");
								factory = new NrmElmFactory();
							}
							else
								factory = new NrmClusteringFactory();
						}
						else {
							if (c1.contains(".elm"))
								factory = new MatrixElmFactory();
						}
						
					}
					
				}
				//View v = new View();

				//Controller c = new Controller(v, factory);
				CsrMatrixClustered matrix;
				if (!line.hasOption("l"))
					matrix = factory.getMatrixClustered(m1,c1);
				else
					matrix = factory.getMatrixClustered(m1,c1,l1);
				String output =line.getOptionValues("o")[0];
				
				
				//TODO - Make a proper controller
				//if (line.hasOption("r")) {
				FileWriter fwr = new FileWriter(new File(output+".rl"));
				FunctionalCartography fc = new FunctionalCartography(matrix);
				fc.doZScore();
				fwr.write("#node zscore coefp sizecom degree\n");
				for (int i = 0; i < matrix.getNbRows(); i++) {
					fwr.write(i + " " + fc.getZScore(i) + " "+fc.getParticipationCoefficient(i)+ " "+fc.getSizeCommunity(fc.getCommunity(i))+ " "+fc.getDegree(i)+"\n");
				}
				fwr.close();
				//}
				
			}

		} catch (ParseException exp) {
			log.fatal("Unexpected exception:" + exp.getMessage());
		} catch (FileNotFoundException e) {
			log.fatal(e);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.fatal(e);
		}
	}
	
	public static String createTempExemple(String s) {
		File file=null;
		 try {
			 	
			 	MainDiachronic m = new MainDiachronic();
	            InputStream input = m.getClass().getClassLoader().getResourceAsStream(s);
	            file= new File(s+".exemple");
	            OutputStream out = new FileOutputStream(file);
	            int read;
	            byte[] bytes = new byte[1024];

	            while ((read = input.read(bytes)) != -1) {
	                out.write(bytes, 0, read);
	            }
	            out.close();
	            
	            log.info(s+ ".exemple created");
	        } catch (IOException ex) {
	            log.fatal(ex);
	        }
		 return file.getAbsolutePath();
	}
	
	//TODO Responsability chain to make it cleaner and more modular
		public static boolean check(CommandLine line, Options options) {
			boolean okay=true;
			if (line.hasOption("e"))
				return true;
			if (line.hasOption("g") || line.hasOption("m")) {
				if (!line.hasOption("o")) {
					log.warn("You need to provide output path with -o option");
					okay=false;
				}
				if (okay && !line.hasOption("m")) {
					log.warn("You need to provide matrix files with -m option");
					okay=false;
				}
				else if (line.getOptionValues("m").length < 1) {
					log.warn("You need to provide a matrix file with -m option");
					okay=false;
				}
				if (!line.hasOption("c")) {
					log.warn("You need to provide clustering files with -c option");
					okay=false;
				}
				else if (line.getOptionValues("c").length < 1) {
					log.warn("You need to provide a clustering file with -c option");
					okay=false;
				}
				if (line.hasOption("l") && (line.getOptionValues("l").length <1)) {
					log.warn("You need to provide a label file with -l option. If you don't want to use labels, don't use the -l option.");
					okay=false;
				}
				/*if (!line.hasOption("o")) {
					okay=false;
					System.out.println("You need to provide an output file with -o option");
				}*/
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
			formatter.printHelp("Compute community roles ", options);
		}

}
