package model.util;

import java.util.Random;

import javax.swing.JProgressBar;


/*
public class MultiThread extends Thread{
  public MultiThread() {
		
	}

public void run() {
	int i =0;
    long start = System.currentTimeMillis();
    // boucle tant que la durée de vie du thread est < à 5 secondes
    while( System.currentTimeMillis() < ( start + (1000 * 5))) {
      i = i+1;
      System.out.println("Ligne affichée par le thread : "+ i);
      try {
        // pause
        Thread.sleep(500);
      }
      catch (InterruptedException ex) {}
    }
  }    
}*/
/*
public class MultiThread {
	 
    public synchronized void traitementLong(JProgressBar pBar) {
        // initialisation de la progressBar au début du traitement
        pBar.setMinimum(0);
        pBar.setMaximum(99);
        pBar.setValue(0);
 
        // Traitement
        for( int i = 0; i < 100; i++ ) {
            System.out.print(".");
            pBar.setValue(i);
            try {
                // Pause pour simuler un traitement
                Thread.sleep(100);
            } catch (InterruptedException e) {}
        }
        System.out.println("") ;
    }
}*/

public class MultiThread {
    private static final int QUANTITE_INITIALE = 200;
    private static final int NB_thread_MAX = 2;
 
    private static int iteration = 0;
 
    private int[] vase = {QUANTITE_INITIALE / 2,QUANTITE_INITIALE / 2};
 
    public MultiThread() {
        for( int i = 0; i < NB_thread_MAX; i++)
            new threadTransfert().start();
    }
    public static void main(String[] args) {
        new MultiThread();
    }
 
    public int transfert(int qte) {
        // Ne pas enlever les System.out de ce test !
        System.out.print("-("+qte+") dans le vase 1 ");
        vase[0] -= qte;
        System.out.println("+("+qte+") dans le vase 2");
        vase[1] += qte;
        iteration++;
        if( iteration % 1000 == 0)
            System.out.println("" + iteration + " itérations.");
        return vase[0]+vase[1];
    }
 
    public class threadTransfert extends Thread {
        Random r = new Random();
        int quantite;
        public void run() {
            while( !isInterrupted()) {
                quantite = r.nextInt(11)-6;
                vase[0] -= quantite;
                vase[1] += quantite;
                if( transfert(quantite) != QUANTITE_INITIALE) {
                    System.err.println("Quantité totale invalide à l'itération " + iteration);
                    System.exit(-1);
                }
 
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
        }
 
    }
}