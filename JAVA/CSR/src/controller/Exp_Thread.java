package controller;

import java.awt.Window.Type;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Scanner;

import model.cluster.Clustering;
import model.featureselection.FeaturesSelection;
import model.matrix.CsrMatrix;
import model.matrix.CsrMatrixClustered;
import model.matrix.decorator.IMatrix;
import model.matrix.decorator.MatrixFeatureLabels;
import model.quality.Quality;
import model.util.MultiThread;
import model.util.nuplet.PairF;
import io.reader.ArcsReader;
import io.reader.ClusteringReader;
import io.reader.LabelReader;
import io.reader.MatrixReader;
import io.reader.interfaces.IMatrixReader;


/*
public class Exp_Thread {
	public static void p(Object O) {
		System.out.print(O);
	}

	public static void pl(Object O) {
		System.out.println(O);
	}
	
	 public Exp_Thread() {
		
	    // création d'une instance du thread
	    MultiThread thread = new MultiThread();
	    // Activation du thread
	    thread.start();
	    int i =0;
	    // tant que le thread est en vie...
	    while( thread.isAlive() ) {
	      // faire un traitement...
	    	i++;
	      System.out.println("Ligne affichée par le main : "+i);
	      try {
	        // et faire une pause
	        thread.sleep(500);
	      }
	      catch (InterruptedException ex) {}
	    }
	  }
	
	 public static void main(String[] args) {
		    new Exp_Thread();
		  }
		 
/*
	public static void main(String[] args) throws IOException {

		IMatrixReader amr = new ArcsReader("exemples/Guimera_matrix");
		ClusteringReader acr = new ClusteringReader(
				"exemples/Guimera_community");
		IMatrix aim = new CsrMatrix(amr);
		CsrMatrixClustered amc = new CsrMatrixClustered(aim, acr.getClusters());
		//pl(Quality.getConductance(0,amc));
		//pl(Quality.getCutRatio(1,amc));
		
		MatrixReader mr = new MatrixReader("exemples/matrix_lamirel_iskomaghreb"); 
		ClusteringReader cr = new ClusteringReader("exemples/clustering_lamirel_iskomaghreb");
		IMatrix im = new CsrMatrix(mr);
		
		CsrMatrixClustered mc = new CsrMatrixClustered(im, cr.getClusters());
	
		pl(Quality.getDiameter(0, mc));
	}
}

import java.util.Random;

import java.awt.*;
import java.awt.event.*;
 
import javax.swing.*;
 

public class Exp_Thread extends JFrame {
  JProgressBar jBar = new JProgressBar();
  GridLayout layout = new GridLayout(2,1);
  JButton btnAction = new JButton("Lancer le traitement");
 
  public Exp_Thread() {
    // Construction de la fenêtre de test
    Container contentPane = getContentPane();
    contentPane.setLayout(layout);
    contentPane.add(jBar);
    contentPane.add(btnAction);
    setTitle("Test de progressBar");
    // Ajout du listener du bouton
    btnAction.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          // Instanciation et lancement du traitement
          MultiThread action = new MultiThread();
          action.traitementLong(jBar);
        }
      }
    );
 
    // Affichage de la fenêtre
    pack();
    setVisible(true);
 
  }
 
  public static void main(String[] args) {
    new Exp_Thread();
  }
}*/
/*
public parallelCalcul(){
	ArrayList array = new ArrayList[launch]();
	for (int nThread = 0; nThread < matrix.length ; nThread++)
		array.put(new launch(nThread));
	}
	*/

