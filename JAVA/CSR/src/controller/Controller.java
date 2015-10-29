package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.diachronism.LabelDiachronism;
import model.util.factory.AFactory;
import view.View;

//TODO - ADD File Labels Diachronism
//TODO - Move to a responsability chain design pattern !
/**
 * Gère les liens entre le modèle et la vue (affichage)
 * 
 * @author nicolas
 *
 */
public class Controller {
	
	private LabelDiachronism ld;
	private View v;
	private AFactory adf;
	
	
	
	public Controller(View v, AFactory f) {
		super();
		this.v = v;
		this.adf=f;
	}


	/**
	 * Run diachronism using feature fmeasures for source and target labels
	 * 
	 * @param f1 source features values
	 * @param f2 target features values
	 * @throws IOException 
	 */
	public void doRunDiachronism(String f1, String f2) throws IOException {
		ld=adf.matchingFromLabel(f1, f2);
		v.print(this.getJson());
	}
	/**
	 * Run diachronism using matrices and clusterings
	 * 
	 * @param m1 source matrix
	 * @param m2 target matrix
	 * @param c1 source clustering
	 * @param c2 target clustering
	 * @throws IOException 
	 */
	public void doRunDiachronism(String m1, String m2, String c1, String c2) throws IOException {
		ld=adf.matchingFromMatrixCluster(m1,m2,c1,c2);
		v.print(this.getJson());
	}
	
	/**
	 * Run diachronism using matrices, clusterings and labels
	 * 
	 * @param m1 source matrix
	 * @param m2 target matrix
	 * @param c1 source clustering
	 * @param c2 target clustering
	 * @param l1 source labels
	 * @param l2 target labels
	 * @throws IOException 
	 */
	public void doRunDiachronism(String m1, String m2, String c1, String c2, String l1, String l2) throws IOException {
		if (l1 == null || l2 == null)
			this.doRunDiachronism(m1, m2, c1, c2);
		else {
			ld=adf.matchingFromMatrixClusterLabels(m1,m2,c1,c2,l1,l2);
			v.print(this.getJson());
		}
	}
	
	/**
	 * Generate JSON result of the diachronism
	 * 
	 * @return
	 */
	public String getJson() {
		Iterator<Integer> itClusters;
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    int target;
	    String json="[";
	    boolean[] targets=new boolean[ld.getNbClusterTarget()];
	    
		for (int s =0; s < ld.getNbClusterSource(); s++) {
			itClusters=ld.getTargetClusterMatching(s).iterator();
			if (!itClusters.hasNext()) {
				json+="{\"Cluster source\":\""+ld.getLabelOfClusterSource(s)+"\", \"state\" : \"vanished\"},";
			}
			else {
				while (itClusters.hasNext()) {
					target=itClusters.next();
					targets[target] = true;
					
					json+=gson.toJson(ld.getLabelCore(s, target));
					json+=",";
				}
			}
		}
		for (int t = 0; t < targets.length; t++) {
			if (!targets[t]) {
				json+="{\"Cluster target\":\"G-"+ld.getLabelOfClusterTarget(t)+"\", \"state\" : \"appeared\"},";
			}
		}
		if (json.length() > 0 && json.charAt(json.length()-1)==',') {
			json = json.substring(0, json.length()-1);
		}
		return json + "]";
	}

}
