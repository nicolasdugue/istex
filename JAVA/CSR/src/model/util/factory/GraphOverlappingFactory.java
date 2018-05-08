package model.util.factory;

import io.reader.factory.FactoryGraphReader;
import io.reader.factory.FactoryOverlappingClusteringReader;

public class GraphOverlappingFactory extends AFactory {

	public GraphOverlappingFactory() {
		super(new FactoryGraphReader(), new FactoryOverlappingClusteringReader());
		// TODO Auto-generated constructor stub
	}

}
