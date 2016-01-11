package controller;

import controller.EThread.Launch;

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

public class EThread {
	
	private static final int Size= 20000;
	private static final int NThreads= 6;
	private int[][] matrix = createMatrix(Size);
	private int[] result = new int[Size];
	private ArrayList<Launch> array;
	
	private int[][] createMatrix(int size){
		int[][] mat = new int[size][size];
		for(int i=0; i<size;i++){
			for(int j=0; j<size;j++){
				mat[i][j]=j;
			}
		}
		return mat;
	}
	
	
	public EThread() {
		this.array = new ArrayList<Launch>();
		for(int res_i = 0; res_i < Size; res_i++)
			result[res_i] = 0;
		
		long start = System.nanoTime();
		for (int nThread = 0; nThread < NThreads ; nThread++)
		{
			 array.add(new Launch(nThread));
			 array.get(nThread).start();
		}
		long current = System.nanoTime();
        System.out.println("Thread creation: "+(current-start)/1000.00);
	}
	
	/*public void parallelCalcul(){
		ArrayList array = new ArrayList[launch]();
		for (int nThread = 0; nThread < matrix.length ; nThread++)
			array.put(new launch(nThread));
		}*/
	
	public class Launch extends Thread {

		public int index;
		
		public Launch(int i){
			this.index = i;
			//System.out.println("Launching Thread n� : "+i);
		}
		
		public void run(){
			
			
			for(int elt = (int) Math.floor(this.index/((float)NThreads)*Size); elt < (int) Math.floor((1+this.index)/((float)NThreads)*Size); elt++)
			{
				for(int col = 0; col<Size; col++)
				{
					result[elt]+= matrix[elt][col];
				}
					
				//System.out.println("Processing Thread n� : "+index);
			}
			
		}
		
	}
		
	public static void main(String[] args) throws InterruptedException {
		long start = System.nanoTime();
		EThread et = new EThread();
		for (int nThread = 0; nThread < NThreads ; nThread++)
		{
			 et.array.get(nThread).join();
		}
		long current = System.nanoTime();
        System.out.println("Resultat : "+et.result[0]+" "+et.result[1]+" "+et.result[2]+" "+et.result[3]);
        System.out.println("Time of process of Threads: "+(current-start)/1000.00);
        
        start = System.nanoTime();
        int[][] matrix = et.matrix;
        int[] result = new int[et.Size];
        for(int i=0; i <et.Size;i++)
        {	
        	result[i]= 0;
	        for(int elt : matrix[i])
			{
				result[i]+= elt;
				
			}
        }
        current = System.nanoTime();
        System.out.println("Resultat sans thread: "+result[0]+" "+result[1]+" "+result[2]+" "+result[3]);
        System.out.println("Time of linear process : "+(current-start)/1000.00);
    }
}
