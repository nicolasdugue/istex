package model.quality;

import model.featureselection.FeaturesSelection;

public class Quality {

	//========================ADDED_BEGIN========================
	
		public static float getContrast(FeaturesSelection fs, int column, int cluster) {
			//NOTES :
			//*IF Den == 0 , return 0. 
			//TODO MOVE TO CLUSTERED MATRIX QUAL
			
			return fs.getFeatureFMeanValue(column)==0 ? 0 : fs.getFeatureValue(column,cluster)/fs.getFeatureFMeanValue(column); 
		}
		
		public static float getPC(FeaturesSelection fs) {

			//NOTES :
			//-MOVE TO CLUSTERED MATRIX QUAL
			int nbClusters = fs.getMatrix().getNbCluster(); 
			float resultPC = 0f;
			float sumContrast = 0f;
			for (int i = 0 ;  i < nbClusters ; i++){
						
				//Sum of contrasts
				for (int f : fs.getFeaturesSelected(i))
				{
					sumContrast += getContrast(fs,f,i);
				};
				
				resultPC += 1/fs.getMatrix().getSizeCk(i)*sumContrast;
				sumContrast = 0f;
			}
			return(resultPC/nbClusters);
			
		}
		
		public static float getEC(FeaturesSelection fs) {
			
			
			//NOTES :
			//-MOVE TO CLUSTERED MATRIX QUAL
			int nbClusters = fs.getMatrix().getNbCluster(); 
			float resultEC = 0f;
			float sumContrast = 0f;
			float sumAntiContrast = 0f;
			float tempContrast =0f;
			int nbActiveFeatures=0;
			int nbPassiveFeatures=0;
			
			for (int i =0 ;  i < nbClusters ; i++){
						
				// Get all the information with one single thread
				for (int f : fs.getFeaturesSelected(i))
				{
					//One single call
					tempContrast += getContrast(fs,f,i);
					
					//Active feature
					if (tempContrast >= 1)
					{
						sumContrast += tempContrast;
						nbActiveFeatures++;
					}
					//Passive feature
					else
					{
						sumAntiContrast += 1/tempContrast; //TODO : decide what to do when 1/0
						nbPassiveFeatures++;
					}
								
				}
				int nbDataAssociatedCk = fs.getMatrix().getSizeCk(i);
				resultEC += 1/( nbDataAssociatedCk * (nbActiveFeatures+nbPassiveFeatures)) * ( nbActiveFeatures*sumContrast + nbPassiveFeatures*sumAntiContrast) ;
				
				//Reset sums
				sumContrast = 0f;
				sumAntiContrast = 0f;
				nbActiveFeatures=0;
				nbPassiveFeatures=0;
			}
			return(resultEC/nbClusters);
			
		}
		
		//========================ADDED_END========================
}
