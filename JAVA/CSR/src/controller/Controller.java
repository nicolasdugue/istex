package controller;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.diachronism.LabelDiachronism;
import model.util.factory.FactoryDiachronism;
import model.util.nuplet.PairFWeighted;
import view.View;

public class Controller {
	
	private LabelDiachronism ld;
	private View v;
	
	
	
	public Controller(View v) {
		super();
		this.v = v;
	}


	public void doRunStuff(String[] params) throws FileNotFoundException {
		if (params.length == 2 ) {
			ld=FactoryDiachronism.matchingFromLabel(params[0], params[1]);
		}
		else if (params.length == 4 ) {
			ld=FactoryDiachronism.matchingFromMatrixCluster(params[0], params[1], params[2], params[3]);
		}
		else if (params.length == 6) {
			ld=FactoryDiachronism.matchingFromMatrixClusterLabels(params[0], params[1], params[2], params[3], params[4], params[5]);
		}
		else {
			//TODO -- DESIGNS EXCEPTIONS AND USER MANUAL FOR THE CLI
			ld=null;
		}
		v.print(this.getJson());
	}
	
	public String getJson() {
		Iterator<String> itLabels;
		Iterator<Integer> itClusters;
		final GsonBuilder builder = new GsonBuilder();
	    final Gson gson = builder.create();
	    String label;
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
