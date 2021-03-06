package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

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

import model.featureselection.FeaturesSelection;
import model.featureselection.LabelSelection;
import model.featureselection.labellingstategies.FeatureSelectionStrategy;
import model.featureselection.labellingstategies.FeatureThresholdStrategy;
import model.featureselection.labellingstategies.FixedNumberOfLabelStrategy;
import model.util.factory.AFactory;
import model.util.factory.GraphElmFactory;
import model.util.factory.GraphFactory;
import model.util.factory.GraphOverlappingFactory;
import model.util.factory.MatrixElmFactory;
import model.util.factory.MatrixFactory;
import model.util.factory.NrmClusteringFactory;
import model.util.factory.NrmElmFactory;
import model.util.nuplet.PairSFWeighted;
import model.util.nuplet.collection.SortedLabelSet;
import util.SGLogger;

@SuppressWarnings("deprecation")
public class MainFeatureSelection {

	private static HelpFormatter formatter = new HelpFormatter();
	private static CommandLineParser parser = new DefaultParser();
	private static Options options = new Options();
	private static Logger log=SGLogger.getInstance();

	public static void main(String[] args) {

		options.addOption("h", "help", false, "print this message");
		options.addOption("g", "graph", false, "read a list of arcs instead of a matrix");
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
		OptionBuilder.hasArgs(0);
		OptionBuilder.withArgName("overlapping");
		OptionBuilder.withDescription(
				"Tells if clustering is overlapping.");
		opt = OptionBuilder.create("ov");
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
				String output;
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
					log.info("Results stored in exemple_resultat file");
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
						log.debug("Matrice : " + m1);
						log.debug("Clustering : " +c1);
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
						if (line.hasOption("ov")) {
							factory = new GraphOverlappingFactory();
						}
						
						
					}
					
				}
				if (line.hasOption("e"))
					output="exemple_resultat";
				else
					output=line.getOptionValues("o")[0];
				//View v = new View();

				//Controller c = new Controller(v, factory);
				FeaturesSelection fs;
				if (!line.hasOption("l"))
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1);
				else
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1,l1);
				
				
				
				
				FileWriter fw = new FileWriter(new File(output+".fs"));
				fw.write("#cluster object ff fr fp\n");
				SortedLabelSet s = new SortedLabelSet();
				int feature;
				log.info("Writing Feature selection results");
				for (int cls=0; cls < fs.getNbCluster(); cls++) {
					 for (int j=0; j < fs.getNbColumns(); j++) {
						 s.add(new PairSFWeighted(fs.getLabelOfCol(j), fs.getFeatureValue(j, cls)));
					 }
					 for (PairSFWeighted pair : s) {
						 fw.write(cls +" "+pair.getLeft()+" "+ pair.getRight()+ " " + fs.fr(fs.getIndexOfColLabel(pair.getLeft()),cls)+ " "+ fs.fp(fs.getIndexOfColLabel(pair.getLeft()),cls) +"\n");
					 }
					 s.clear();
				}
				fw.close();
				if (line.hasOption("lsn") || line.hasOption("lsf") || line.hasOption("lst") || line.hasOption("e")) {
					log.info("Running label selection");
					FileWriter fwl = new FileWriter(new File(output+".ls"));
					LabelSelection ls;
					if (line.hasOption("lsn")) {
						ls = new LabelSelection(fs, new FixedNumberOfLabelStrategy(fs, Integer.parseInt(line.getOptionValues("lsn")[0])));
					}
					else if (line.hasOption("lst")){
						ls = new LabelSelection(fs, new FeatureThresholdStrategy(fs, Float.parseFloat(line.getOptionValues("lst")[0])));
					}
					else {
						ls = new LabelSelection(fs, new FeatureSelectionStrategy(fs));
					}
					log.info("Writing label selection results");
					for (int cluster =0; cluster < fs.getNbCluster(); cluster++) {
						if (cluster != 0)
							fwl.write("\n");
						fwl.write("#------------CLUSTER "+cluster+ " ------------------+\n");
						s.clear();
						for (Iterator<Integer> it=ls.getPrevalentFeatureSet(cluster).iterator(); it.hasNext();) {
							feature=it.next();
							s.add(new PairSFWeighted(fs.getLabelOfCol(feature), fs.getFeatureValue(feature, cluster)));
						}
						for (PairSFWeighted pair : s) {
							fwl.write(pair.getLeft()+" "+ pair.getRight()+" | ");
						}
					}
					fwl.close();
				}
				/*for (int i = 0; i < fs.getNbColumns(); i++) {
					System.out.println(fs.getLabelOfCol(i) + " " + fs.fp(i, fs.getClusterOfObjectI(i)) + " " + fs.fr(i, fs.getClusterOfObjectI(i)) + " " + fs.getFeatureValue(i, fs.getClusterOfObjectI(i)));
				}*/
				
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
			formatter.printHelp("Feature selection, node ", options);
		}
}
