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
import model.roles.FunctionalCartography;
import model.util.factory.AFactory;
import model.util.factory.GraphFactory;
import model.util.factory.MatrixFactory;
import model.util.nuplet.PairFWeighted;
import model.util.nuplet.collection.SortedLabelSet;
import util.SGLogger;
import view.View;

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
		options.addOption("ls", "labelselection", false, "Extract labels");
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
						if (line.hasOption("g")) {
							factory = new GraphFactory();
						}
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
						
					}
					
				}
				//View v = new View();

				//Controller c = new Controller(v, factory);
				FeaturesSelection fs;
				if (!line.hasOption("l"))
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1);
				else
					fs = (FeaturesSelection) factory.getFeatureSelecter(m1,c1,l1);
				String output =line.getOptionValues("o")[0];
				
				
				
				FileWriter fw = new FileWriter(new File(output+".fs"));
				fw.write("#com node ff\n");
				ArrayList<Integer> l;
				SortedLabelSet s = new SortedLabelSet();
				int node;
				log.info("Writing Feature selection results");
				for (int cls=0; cls < fs.getNbCluster(); cls++) {
					 l= fs.getObjectsInCk(cls);
					 for (Iterator<Integer> it=l.iterator(); it.hasNext();) {
						 node=it.next();
						 s.add(new PairFWeighted(fs.getLabelOfCol(node), fs.getFeatureValue(node, cls)));
					 }
					 for (PairFWeighted pair : s) {
						 fw.write(cls +" "+pair.getLeft()+" "+ pair.getRight()+"\n");
					 }
					 s.clear();
				}
				fw.close();
				if (line.hasOption("ls")) {
					log.info("Running label selection");
					FileWriter fwl = new FileWriter(new File(output+".ls"));
					LabelSelection ls = new LabelSelection(fs);
					log.info("Writing label selection results");
					for (int cluster =0; cluster < fs.getNbCluster(); cluster++) {
						fwl.write("#\n------------CLUSTER "+cluster+ " ------------------+\n");
						s.clear();
						for (Iterator<Integer> it=ls.getPrevalentFeatureSet(cluster).iterator(); it.hasNext();) {
							node=it.next();
							if (fs.getClusterOfObjectI(node) == cluster) {
								s.add(new PairFWeighted(fs.getLabelOfCol(node), fs.getFeatureValue(node, cluster)));
							}
						}
						for (PairFWeighted pair : s) {
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
