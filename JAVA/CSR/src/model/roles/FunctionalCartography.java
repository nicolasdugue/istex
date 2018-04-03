package model.roles;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import model.matrix.CsrMatrixClustered;
import util.SGLogger;

public class FunctionalCartography {

	private CsrMatrixClustered matrix;
	private float[][] z_score;
	private float[] participation;
	private Logger log;




	public FunctionalCartography(CsrMatrixClustered matrix) {
		super();
		this.matrix = matrix;

		// z-score doit devenir un tableau de tableau de float[getNbCommunities][getNbColumns]
		z_score = new float[matrix.getNbCluster()][matrix.getNbColumns()];
		// Il y a un seul score de participation pour toutes les communautés d apres la définition du coefficient de participation
		participation=new float[matrix.getNbColumns()];
		log=SGLogger.getInstance();
	}

	/**
	 * Returns the internal degree of node in its community
	 * @param node
	 * @return
	 */
	public int getInternalDegree(int node) {
		int com = this.getCommunity(node);
		return (int)getDegreeInCom(node, com);
	}

	/**
	 * Returns the degree of node in com
	 * @param node
	 * @return
	 */
	public int getDegreeInCom(int node, int com) {
		return getInDegreeInCom(node, com)+getOutDegreeInCom(node, com);
	}
	public int getInDegreeInCom(int node, int com) {
		return (int)matrix.getSumColInCluster(node, com);
	}
	public int getOutDegreeInCom(int node, int com) {
		return (int)matrix.getSumRowInCluster(node, com);
	}



	public int getInDegree(int node) {
		return (int)matrix.getSumCol(node);
	}
	public int getOutDegree(int node) {
		return (int)matrix.getSumRow(node);
	}

	/**
	 * Returns the degree of node
	 * @param node
	 * @return
	 */
	public int getDegree(int node) {
		return getInDegree(node)+getOutDegree(node);
	}

	/**
	 * Returns the community that node belongs to
	 * @param node
	 * @return
	 */
	public int getCommunity(int node) {
		return matrix.getClusterOfObjectI(node);
	}
	/**
	 * Returns all the nodes belonging to the community com
	 * @param com - an integer id of the community
	 * @return
	 */
	public ArrayList<Integer> getObjectsInCommunity(int com) {
		return matrix.getObjectsInCk(com);
	}

	/**
	 * Get the internal degrees of the whole community as a list of integer
	 *
	 * @param com
	 * @return
	 */
	public ArrayList<Integer> getAllComunityInternalDegree(int com) {
		ArrayList<Integer> community = this.getObjectsInCommunity(com);
		log.debug("Community : "+ community);
		ArrayList<Integer> degrees = new ArrayList<Integer>(community.size());
		for (Iterator<Integer> it=community.iterator(); it.hasNext();) {
			int i = it.next();
			log.debug("-> "+i);
			degrees.add(this.getInternalDegree(i));
		}
		return degrees;
	}

	/**
	 *
	 * Get the mean of a list of integer. Used to get the mean of the internal degree for example.
	 *
	 * @param l
	 * @return
	 */
	public float mean(List<Integer> l) {
		float mean=0;
		for (Iterator<Integer> it=l.iterator(); it.hasNext(); ) {
			mean+=it.next();
		}
		mean/=l.size();
		return mean;
	}
	/**
	 * Get the standard deviation of a list of integer with relation to its mean.
	 *
	 * @param l
	 * @param mean
	 * @return
	 */
	public float std(List<Integer> l, float mean) {
		if (mean == 0)
			return 0;
		float std=0;
		float tmp;
		for (Iterator<Integer> it=l.iterator(); it.hasNext(); ) {
			tmp=it.next();
			std+=(tmp - mean)*(tmp - mean);
		}
		std=(float) Math.sqrt(std);
		std/=l.size();
		return std;
	}

	/**
	 *
	 * Process the z_score of the internal degree of a community and store it in this, the current object.
	 *
	 * @param com
	 */
	public void doZScore(int com) {
		/*List<Integer> internalDegrees=this.getAllComunityInternalDegree(com);
		float mean = mean(internalDegrees);
		float std=std(internalDegrees, mean);
		List<Integer> nodes=getObjectsInCommunity(com);
		int node;
		// Remplacer par la liste de tous les noeuds
		for (Iterator<Integer> it=nodes.iterator(); it.hasNext();) {
			node=it.next();
			if (std == 0)
				z_score[node] = 0;
			else
				//Remplacer this.getInternalDegree(node) par this.getInternalDegree(node,com)
				z_score[node]=(this.getInternalDegree(node)-mean)/std;
		}*/
		List<Integer> internalDegrees=this.getAllComunityInternalDegree(com);
		float mean = mean(internalDegrees);
		float std=std(internalDegrees, mean);
		List<Integer> nodes=getObjectsInCommunity(com);
		int node;
		// Remplacer par la liste de tous les noeuds
		for (int i =0; i < matrix.getNbColumns(); i++) {
			node=i;
			if (std == 0)
				z_score[com][node] = 0;
			else
				//Remplacer this.getInternalDegree(node) par this.getInternalDegree(node,com)
				z_score[com][node]=(this.getDegreeInCom(node, com)-mean)/std;
		}
	}

	/**
	 *
	 * Process the z_score of the internal degree of all the graph nodes.
	 *
	 */
	public void doZScore() {
		for (int i=0; i< this.getNbCommunities(); i++) {
			doZScore(i);
			log.debug("Community " + i + " handled : " + this.getSizeCommunity(i) + " nodes in it");
		}
	}


	public float getParticipationCoefficient(int node) {
		//1 - Somme sur toutes les communautés c de (degré de node dans c - degré de node)²
		if (this.getDegree(node) == 0)
			return 0;
		float coef_p=1;
		float in_degree;
		for (int com=0; com < this.getNbCommunities();com++) {
			in_degree=this.getDegreeInCom(node, com);
			if (in_degree != 0)
				coef_p-=Math.pow(((float)in_degree / (float)this.getDegree(node)), 2);
		}
		return coef_p;
	}


	public int getNbCommunities() {
		return matrix.getNbCluster();
	}

	public int getNbNodes() {
		return matrix.getNbRows();
	}
	public float getZScore(int com, int node) {
		return z_score[com][node];
	}

	public float getZScore(int node) {
		return z_score[this.getCommunity(node)][node];
	}

	public int getSizeCommunity(int com) {
		return matrix.getSizeCk(com);
	}



}
