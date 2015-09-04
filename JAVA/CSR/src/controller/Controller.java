package controller;

import java.io.FileNotFoundException;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.diachronism.LabelDiachronism;
import model.diachronism.clustermatching.ClusterLabelled;
import model.diachronism.clustermatching.ClusterMatching;
import model.factory.Factory;
import model.util.PairFWeighted;
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
			ld=Factory.matchingFromLabel(params[0], params[1]);
		}
		else if (params.length == 4 ) {
			ld=Factory.matchingFromMatrixCluster(params[0], params[1], params[2], params[3]);
		}
		else if (params.length == 6) {
			ld=Factory.matchingFromMatrixClusterLabels(params[0], params[1], params[2], params[3], params[4], params[5]);
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
	    ClusterLabelled cs;
	    ClusterMatching cm;
	    ClusterLabelled ct ;
	    String label;
	    int target;
	    String json="[";
		for (int s =0; s < ld.getNbClusterSource(); s++) {
			cs = new ClusterLabelled();
			cs.setName("G-"+s);
			cm = new ClusterMatching(cs);
			itClusters=ld.getTargetClusterMatching(s).iterator();
			while (itClusters.hasNext()) {
				target=itClusters.next();
				ct = new ClusterLabelled();
				ct.setName("G-"+target);
				
				itLabels=ld.getLabelsClusterTarget(target).iterator();
				while (itLabels.hasNext()) {
					label=itLabels.next();
					ct.add(new PairFWeighted(label,ld.getFeatureValueTarget(label)));
				}	
				cm.add(ct);
			}
			itLabels=ld.getLabelsClusterSource(s).iterator();
			while (itLabels.hasNext()) {
				label=itLabels.next();
				cs.add(new PairFWeighted(label,ld.getFeatureValueSource(label)));
			}
			json+=gson.toJson(cm).replace("left", "title").replace("right", "weight");
			if (s < (ld.getNbClusterSource() - 1)) {
				json+=",";
			}
		}
		return json + "]";
	}

}
