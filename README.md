# Diachronic'Explorer - Java module for Diachrony and feature selection

## Presentation
Diachronic'Explorer is a project which aims to deal with documents corpora such as the [ISTEX](http://www.istex.fr/) one. This module is a Java driven library used for diachronic analysis, feature selection and cluster labeling. Another module of Diachronic'Explorer is used for [Visualization of diachronic analysis](https://github.com/nicolasdugue/istex-demonstrateur).

This library allows to handle large sparse matrices by using CSR and CSC formats.
It allows to analyse diachronically two different clustering of a same corpora (**[2]**) but at different time steps using several methods (**[1]**) : Clustering, label extraction (**[6]**) and diachronic analysis.
Furthermore, this tool is also adapted to deal with large weighted graphs to study the collaboration networks of authors of the corpus (**[3,5]**).
Finally, the software can also be used for Feature selection or cluster labeling.

## Installation
* The Java project may be imported into all IDE (Eclipse, IntelliJ or Netbeans) ;
* It need JRE7 at least ;
* The projet should be built using Maven and running the command *mvn install* at the root of the projet ;
* Releases can be downloaded **if one does not want to install maven**.

#### Using the jar as linux commands

To be able to use the jar easily as linux commands, you can use the following procedure.

In a shell, run the following commands for each jar you want to use as a Linux command (except the last line, only once).

	mkdir ~/bin
	echo  "java –jar /le chemin/jarname.jar \$@" | tee  ~/bin/cmdname
	chmod +x ~/bin/cmdname
	echo "export PATH=$PATH:~/bin" >> ~/.bashrc
	

Unlog then log to a terminal, you sould be able to run *cmdname* as a command.

## Usage

#### Diachronism

**Exemple :**
	diachronism -m m1.nrm m2.nrm -c c1.elm c2.elm -l l1.idd l2.idd -lst 0.015 > lst0_015.json


This command runs the diachronic analysis on matrices m1 et m2 respectively associated to clusterings and labels c1,l1 et c2,l2. Furthermore, clusters labeling is made using a thresdholf of 0.015 on Feature F-Mesure.

If you want an automatic threshold, use the "-lsf" option.

Finally, the output lst0_015.json is in json. To visualize it like an HTML report, open "JAVA/Visu/json2html-master/index.htm" with your browser, copy the json into the textarea and click on "json 2  html".


###### Help : 
	
	java -jar csr-0.0.1-Diachronism.jar 
	usage: MainDiachronic, Diachronism toolbox
	 -c <clusteringSource> <clusteringTarget>          Source and target
		                                           clustering. One integer
		                                           per line indicated the
		                                           belonging cluster of
		                                           each row. ELM files
		                                           also allowed.
	 -fl <featuresLabelSource> <featuresLabelTarget>   Source and target
		                                           features labels. One
		                                           String per line
		                                           indicated the
		                                           corresponding label of
		                                           each feature.
	 -g,--graph                                        read a list of arcs
		                                           instead of a matrix
	 -h,--help                                         print this message
	 -l <labelsSource> <labelsTarget>                  Labels source and
		                                           target to use to run
		                                           the diachronism
		                                           algorithm
	 -lsf                                              Select label with
		                                           feature selection.
		                                           Automatic.
	 -lsn <number>                                     Select a fixed number
		                                           of labels per cluster.
	 -lst <threshold>                                  Select label with a
		                                           threshold on feature
		                                           selection.
	 -m <matrixSource> <matrixTarget>                  Source and target
		                                           matrices. Fomat : One
		                                           row per line, element
		                                           separated with spaces
		                                           or tabs. NRM files also
		                                           allowed.
	 -q,--quiet                                        be extra quiet
	 -v,--verbose                                      be extra verbose

#### Feature Selection

	java -jar csr-0.0.1-FeatureSelection.jar 
	usage: Feature selection, node
	 -c <clustering>    Source clustering. One integer per line indicated the
		            belonging cluster of each row.
	 -e,--exemple       Run exemple
	 -g,--graph         read a list of arcs instead of a matrix
	 -h,--help          print this message
	 -l <labels>        Source labels to use to run the diachronism algorithm
	 -log,--log         log events
	 -lsf               Select label with feature selection. Automatic.
	 -lsn <number>      Select a fixed number of labels per cluster.
	 -lst <threshold>   Select label with a threshold on feature selection.
	 -m <matrix>        Source matrix, One row per line. Element separated
		            with spaces or tabs.
	 -o <output>        Output file without ext
	 -v,--verbose       debug or verbose mode


## Organization
The project is composed of the following folders:
* `CSR`: contains the main JAVA project
  * `src`: Java main class
  * `test`: Junit test of the program
  * `doc` : Html doc of the project
* `Expe_scientometrics` : contains data about the results from the JC paper, the report generated by JC and the report generated by the Java App.
* `Visu` : is about the javascript scripts used to visualise the diachronic report

## References
* **[1]** Jean-Charles Lamirel. A new approach for automatizing the analysis of research topics dynamics :
application to optoelectronics research. Scientometrics, 93(1) :151–166, 2012.
* **[2]** Jean-Charles Lamirel, Raghvendra Mall, Pascal Cuxac, and Ghada Safi. Variations to incremental growing neural gas algorithm based on label maximization. In Neural Networks (IJCNN), The 2011 Inter-
national Joint Conference on, pages 956–965. IEEE, 2011.
* **[3]** Mark EJ Newman. The structure of scientific collaboration networks. Proceedings of the National
Academy of Sciences, 98(2) :404–409, 2001.
* **[4]** Ivan P Stanimirovic and Milan B Tasic. Performance comparison of storage formats for sparse matrices.
FACTA UNIVERSITATIS (NIS) Ser. Math. Inform, 24 :39–51, 2009.
* **[5]** Nicolas Dugué, Ali Tebbakh, Pascal Cuxac, Jean-Charles Lamirel. Feature selection and complex networks methods for an analysis of collaboration evolution in science: an application to the ISTEX digital library. ISKO-MAGHREB 2015, Nov 2015, Hammamet, Tunisia
* **[6]** Jean-Charles Lamirel, Ingrid Falk, Claire Gardent: Federating clustering and cluster labelling capabilities with a single approach based on feature maximization: French verb classes identification with IGNGF neural clustering.
