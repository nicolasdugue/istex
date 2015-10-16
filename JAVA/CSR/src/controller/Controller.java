package controller;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.diachronism.LabelDiachronism;
import model.util.factory.AFactory;
import view.View;

public class Controller {
	
	private LabelDiachronism ld;
	private View v;
	AFactory adf;
	
	
	
	public Controller(View v, AFactory f) {
		super();
		this.v = v;
		this.adf=f;
	}


	public void doRunStuff(String f1, String f2) throws FileNotFoundException {
		ld=adf.matchingFromLabel(f1, f2);
		v.print(this.getJson());
	}
	public void doRunStuff(String m1, String m2, String c1, String c2) throws FileNotFoundException {
		ld=adf.matchingFromMatrixCluster(m1,m2,c1,c2);
		v.print(this.getJson());
	}
	
	public void doRunStuff(String m1, String m2, String c1, String c2, String l1, String l2) throws FileNotFoundException {
		if (l1 == null || l2 == null)
			this.doRunStuff(m1, m2, c1, c2);
		else {
			ld=adf.matchingFromMatrixClusterLabels(m1,m2,c1,c2,l1,l2);
			v.print(this.getJson());
		}
	}
	
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
		return json + "]";
	}

}
