

import java.io.*;
import java.lang.Math;
import java.util.Scanner;

import java.util.*;
import java.text.NumberFormat;


public class main {
	static label start=new label();
	static label temp=new label();
	static int count=0;
	static int flag=0;
	static int n1=0;										//no. of source clusters
	static int n2=0;										//no. of target clusters
	static PrintStream opSummary;
	static int lab;
	static int docs;
	static int countsrc=-1;
	static int counttrg=-1;
	static double globalAvg=0;
	static double globalStdDeviation;
	public static void main(String args[]) throws FileNotFoundException, IOException{
		
		int i=0;
		double threshold;
		start.next=temp;
		File f1 = new File("./"+args[1]);
		BufferedReader in = new BufferedReader(new FileReader(f1)); 
		String str; 
		while ((str = in.readLine()) != null) { 
			processLine(str);
			n1++;
		} 
		in.close(); 
		//printOP();
		flag=1;
		File f2=new File("./"+args[3]);
		BufferedReader in2 = new BufferedReader(new FileReader(f2)); 
		String str2; 
		while ((str2 = in2.readLine()) != null) { 
			processLine(str2);
			n2++;
		} 
		in2.close(); 
		//printOP();
                findGlobalAvg();
		findGlobalStdDeviation();
		threshold=globalAvg+0.5*globalStdDeviation;
		//System.out.println(Util.format(-1, 2)+"1");
		/*double[][] FeatureVector=new double[count][count];
		FeatureVector=createVector();									//feature vector of each label together in a matrix!!!
		//printVector(FeatureVector);
		//System.out.println(count);
		/*Global labels list created*/
		
		/*create cluster profiles--source map*/
		PrintStream profile1;
		profile1 = new PrintStream(new FileOutputStream("./p1"));
		PrintStream profile2;
		profile2 = new PrintStream(new FileOutputStream("./p2"));
		cluster[]source=new cluster[n1];
		for(i=0;i<n1;i++){
			source[i]=new cluster();
		}
		i=0;
		try { 
			 in = new BufferedReader(new FileReader(f1)); 
 
			while ((str = in.readLine()) != null) { 
				source[i].featureVector=processLineforCluster(str);
				source[i].nLabels=lab;
				source[i].nDocs=docs;
				i++;
			} 
			in.close(); 
		} catch (IOException e) { } 

		for(i=0;i<n1;i++){
			for(int j=0;j<count;j++){
				profile1.print(source[i].featureVector[j]+"\t");
			}
			profile1.println();
		}
		
		profile1.close();
		/*create cluster profile--target map*/
		
		cluster[]target=new cluster[n2];
		for(i=0;i<n2;i++){
			target[i]=new cluster();
		}
		i=0;
		try { 
			in2 = new BufferedReader(new FileReader(f2)); 
			
			while ((str2 = in2.readLine()) != null) { 
				target[i].featureVector=processLineforCluster(str2);
				target[i].nLabels=lab;
				target[i].nDocs=docs;
				i++;
			} 
			in2.close(); 
		}catch (IOException e) { }
		
		for(i=0;i<n2;i++){
			for(int j=0;j<count;j++){
				profile2.print(target[i].featureVector[j]+"\t");
			}
			profile2.println();
		}
		profile1.close();
		profile2.close();

		double num=0;
		double den=0;
		double prob=0;
		PrintStream output;
		double[][] prob_fwd=new double[n1][n2];
		double[][] prob_back=new double[n2][n1];
		output = new PrintStream(new FileOutputStream("./output"));
		//PrintStream n;
		//n = new PrintStream(new FileOutputStream("/home/navesh/Desktop/DataNavesh/num"));
		//PrintStream d;
		//d = new PrintStream(new FileOutputStream("/home/navesh/Desktop/DataNavesh/den"));
		for(i=0;i<n1;i++){
			for(int j=0;j<n2;j++){
				num=numerator(source[i].featureVector,target[j].featureVector);
				den=denominator(source[i].featureVector,target[j].featureVector);
				if(den==0)
					prob=0;
				else
					prob=num/den;
				
				prob_fwd[i][j]=prob;
				//n.print(num+"\t");
				//d.print(den+"\t");
				output.print(prob+"\t");
			}
				//n.println();
				//d.println();
				output.println();
		}
		
		output.close();
		//n.close();
		//d.close();
		PrintStream output_back;
		output_back = new PrintStream(new FileOutputStream("./output_back"));
		for(i=0;i<n2;i++){
			for(int j=0;j<n1;j++){
				num=numerator(target[i].featureVector,source[j].featureVector);
				den=denominator(target[i].featureVector,source[j].featureVector);
				if(den==0)
					prob=0;
				else
					prob=num/den;
				
				prob_back[i][j]=prob;
				output_back.print(prob+"\t");
			}
				output_back.println();
		}
		output_back.close();
		double avg;
		double[][] connMatrix=new double[n1][n2];
		double[][] connMatrix_back=new double[n2][n1];
		PrintStream conn_fwd;
		conn_fwd = new PrintStream(new FileOutputStream("./conn_fwd"));
		PrintStream conn_back;
		conn_back = new PrintStream(new FileOutputStream("./conn_back"));
		
		for(i=0;i<n1;i++){
			avg=average(prob_fwd[i]);
			//System.out.println(avg);
			for(int j=0;j<n2;j++){
				if((prob_fwd[i][j]>avg||prob_fwd[i][j]==avg)&&prob_fwd[i][j]>threshold)
					connMatrix[i][j]=1;
			conn_fwd.print(connMatrix[i][j]+"\t");
			}
			conn_fwd.println();
		}
		
		for(i=0;i<n2;i++){
			avg=average(prob_back[i]);
			for(int j=0;j<n1;j++){
				if((prob_back[i][j]>avg||prob_back[i][j]==avg)&&prob_back[i][j]>threshold)
					connMatrix_back[i][j]=1;
			conn_back.print(connMatrix_back[i][j]+"\t");
			}
			conn_back.println();
		}
		conn_back.close();
		conn_fwd.close();
		
		double[][]stabilityMatrix=new double[n1][n2];
		PrintStream stability;
		stability = new PrintStream(new FileOutputStream("./stability"));
		for(i=0;i<n1;i++){
			for(int j=0;j<n2;j++){
				if(connMatrix[i][j]==1&&connMatrix_back[j][i]==1)
					stabilityMatrix[i][j]=1;
			stability.print(stabilityMatrix[i][j]+"\t");
			}
			stability.println();
		}
		stability.close();
		
		opSummary = new PrintStream(new FileOutputStream("./opSummary"));
		int[]commonIndex;
		int[]nonCommonIndex1;
		int[]nonCommonIndex2;
		int n3;
		for(i=0;i<n1;i++){
			for(int j=0;j<n2;j++){
				if(stabilityMatrix[i][j]==1){
					opSummary.println("source cluster: "+i+"\t["+source[i].nLabels+"/"+source[i].nDocs+"]\t"+"target cluster: "+j+"\t["+target[j].nLabels+"/"+target[j].nDocs+"]");
					commonIndex=commonLabelsIndex(source[i].featureVector,target[j].featureVector);		//choice of clusters to compare
					n3=commonIndex.length;
					commLabel[] commonLabels=new commLabel[n3];
					for(int k=0;k<n3;k++){
						commonLabels[k]=new commLabel();
						commonLabels[k].name=searchIndexforName(commonIndex[k]);
						commonLabels[k].index=commonIndex[k];
						commonLabels[k].fMeasure_source=searchIndexforfMeasureSource(commonIndex[k]);
						commonLabels[k].fMeasure_target=searchIndexforfMeasureTarget(commonIndex[k]);
					}
					nonCommonIndex1=nonCommonLabelsIndex(source[i].featureVector,target[j].featureVector,1);		//nonCommon---only source not in target
					n3=nonCommonIndex1.length;
					commLabel[] nonCommonLabels1=new commLabel[n3];
					for(int k=0;k<n3;k++){
						nonCommonLabels1[k]=new commLabel();
						nonCommonLabels1[k].name=searchIndexforName(nonCommonIndex1[k]);
						nonCommonLabels1[k].index=nonCommonIndex1[k];
						nonCommonLabels1[k].cluster_src=i;
						nonCommonLabels1[k].cluster_trg=searchIndexForCluster(nonCommonIndex1[k],1);
						nonCommonLabels1[k].fMeasure_source=searchIndexforfMeasureSource(nonCommonIndex1[k]);
						nonCommonLabels1[k].fMeasure_target=searchIndexforfMeasureTarget(nonCommonIndex1[k]);
					}
					nonCommonIndex2=nonCommonLabelsIndex(source[i].featureVector,target[j].featureVector,2);		//nonCommon---only target not in source
					n3=nonCommonIndex2.length;
					commLabel[] nonCommonLabels2=new commLabel[n3];
					for(int k=0;k<n3;k++){
						nonCommonLabels2[k]=new commLabel();
						nonCommonLabels2[k].name=searchIndexforName(nonCommonIndex2[k]);
						nonCommonLabels2[k].index=nonCommonIndex2[k];
						nonCommonLabels2[k].cluster_trg=j;
						nonCommonLabels2[k].cluster_src=searchIndexForCluster(nonCommonIndex2[k],0);
						nonCommonLabels2[k].fMeasure_source=searchIndexforfMeasureSource(nonCommonIndex2[k]);
						nonCommonLabels2[k].fMeasure_target=searchIndexforfMeasureTarget(nonCommonIndex2[k]);
					}
					commonLabels=sort(commonLabels,2);							//sort on the basis of avg f measure
					nonCommonLabels1=sort(nonCommonLabels1,0);
					nonCommonLabels2=sort(nonCommonLabels2,1);
					printCommonLabels(commonLabels);
					opSummary.println("Non-Common labels in source cluster");
					printLabels(nonCommonLabels1);
					opSummary.println("Non-Common labels in target cluster");
					printLabels(nonCommonLabels2);
					
				}
			}
		}
		/*vanishing---if any row is only zeros; appearing---if any column is only zeros*/
		int index[];
		boolean flag=false;
		for(i=0;i<n1;i++){
			flag=false;
			for(int j=0;j<n2;j++){
				if(stabilityMatrix[i][j]!=0){
					flag=true;
					break;
				}
			}	
			if(flag==false){
				opSummary.println("source cluster "+i+" is vanishing");
				index=labelsIndex(source[i].featureVector);	
				n3=index.length;
				commLabel[] Labels=new commLabel[n3];
				for(int k=0;k<n3;k++){
					Labels[k]=new commLabel();
					Labels[k].name=searchIndexforName(index[k]);
					Labels[k].index=index[k];
					Labels[k].cluster_src=i;
					Labels[k].cluster_trg=searchIndexForCluster(index[k],1);
					Labels[k].fMeasure_source=searchIndexforfMeasureSource(index[k]);
					Labels[k].fMeasure_target=searchIndexforfMeasureTarget(index[k]);
				}
				Labels=sort(Labels,0);						//sort on the basis of source f
				printLabels(Labels);
			}
		}
		opSummary.println();
		for(i=0;i<n2;i++){
			flag=false;
			for(int j=0;j<n1;j++){
				if(stabilityMatrix[j][i]!=0){
					flag=true;
					break;
				}
			}	
			if(flag==false){
				opSummary.println("target cluster "+i+" is appearing");
				index=labelsIndex(target[i].featureVector);	
				n3=index.length;
				commLabel[] Labels=new commLabel[n3];
				for(int k=0;k<n3;k++){
					Labels[k]=new commLabel();
					Labels[k].name=searchIndexforName(index[k]);
					Labels[k].index=index[k];
					Labels[k].cluster_trg=i;
					Labels[k].cluster_src=searchIndexForCluster(index[k],0);
					Labels[k].fMeasure_source=searchIndexforfMeasureSource(index[k]);
					Labels[k].fMeasure_target=searchIndexforfMeasureTarget(index[k]);
				}
				Labels=sort(Labels,1);							//sort on the basis on target f
				printLabels(Labels);
			}
				
		}
		/*potential splits---------if any row/column has more than 1 stable connection*/
		opSummary.println();
		int[] trg=new int[n2];
		for(i=0;i<n1;i++){
			int m=0;
			trg=new int[n2];
			for(int j=0;j<n2;j++){
				if(stabilityMatrix[i][j]!=0){
					m++;
					trg[j]=1;
				}
			}	
			if(m>1){
				opSummary.print("source cluster "+i+" is a potential split into");
				for(int k=0;k<n2;k++){
					if(trg[k]==1)
						opSummary.print("     "+"target cluster"+k);
				}
				opSummary.println();
			}
		}
		opSummary.println();
		
		int[] src=new int[n1];
		for(i=0;i<n2;i++){
			int m=0;
			src=new int[n1];
			for(int j=0;j<n1;j++){
				if(stabilityMatrix[j][i]!=0){
					m++;
					src[j]=1;
				}
			}	
			if(m>1){
				opSummary.print("target cluster "+i+" is a potential merge from");
				for(int k=0;k<n1;k++){
					if(src[k]==1)
						opSummary.print("    "+"source cluster"+k);
				}
				opSummary.println();
			}
		}
		opSummary.close();
	}
	static void findGlobalStdDeviation(){
		double sum=0;
		int n=0;
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.val_source!=0&&l.val_target!=0){
				sum+=Math.pow((l.val_source-globalAvg), 2)+Math.pow((l.val_target-globalAvg), 2);
				n+=2;
			}
			else{
				if(l.val_source!=0)
					sum+=Math.pow((l.val_source-globalAvg), 2);
				else
					sum+=Math.pow((l.val_target-globalAvg), 2);
				n++;
			}
			l=l.next;
		}
		globalStdDeviation=Math.sqrt(sum/n);
		//System.out.print(globalStdDeviation);
	}
	
	static void findGlobalAvg(){
		double sum=0;
		int n=0;
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			sum+=l.val_source+l.val_target;
			if(l.val_source!=0&&l.val_target!=0)
				n+=2;
			else
				n++;

			l=l.next;
		}
		globalAvg=sum/n;
		//System.out.print(globalAvg);
	}
	
	static int searchIndexForCluster(int index,int flg){
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.index==index){
				if(flg==0)
					return(l.cluster_src);
				else
					return(l.cluster_trg);
			}
				
			l=l.next;
		}
		return(-1);
	}

	static commLabel[] sort(commLabel[]label,int flag){						//flag=0--source; flag=1--target;  flag=2--avg
		commLabel t=new commLabel();
		//commLabel[] sorted=new commLabel[label.length];
		for(int i=0;i<label.length;i++){
			for(int j=i;j<label.length;j++){
				double avg1=(label[i].fMeasure_source+label[i].fMeasure_target)/2;
				double avg2=(label[j].fMeasure_source+label[j].fMeasure_target)/2;
				if(flag==0){
					if(label[i].fMeasure_source<label[j].fMeasure_source){
						t=label[i];
						label[i]=label[j];
						label[j]=t;
					}
				}
				if(flag==1){
					if(label[i].fMeasure_target<label[j].fMeasure_target){
						t=label[i];
						label[i]=label[j];
						label[j]=t;
					}
				}
				if(flag==2){
					if(avg1<avg2){
						t=label[i];
						label[i]=label[j];
						label[j]=t;
					}
				}
			}
		}
		return(label);
	}
	
	static void printCommonLabels(commLabel[] a){
		int n=a.length;
		double avg=0;
		double var=0;
		double f1=0;
		double f2=0;
		

		NumberFormat num = NumberFormat.getNumberInstance(); 
		num.setMaximumFractionDigits(6);
		num.setMinimumFractionDigits(6);
		for(int i=0;i<n;i++){
			f1=a[i].fMeasure_source;
			f2=a[i].fMeasure_target;
			avg=(f1+f2)/2;
			var=Math.pow((f1-avg),2)+Math.pow(f2-avg, 2);
			opSummary.println("f_avg: "+num.format(avg)+"    "+"var: "+num.format(var)+"    "+a[i].name);
		}
		opSummary.println();
	}

	static void printLabels(commLabel[] a){
		int n=a.length;
		double f1=0;
		double f2=0;
		NumberFormat num = NumberFormat.getNumberInstance(); 
		num.setMaximumFractionDigits(6);
		num.setMinimumFractionDigits(6);
		for(int i=0;i<n&&i<4;i++){
			f1=a[i].fMeasure_source;
			f2=a[i].fMeasure_target;
			opSummary.println("f1: "+num.format(f1)+"["+Util.format(a[i].cluster_src,2)+"]"+"    "+"f2: "+num.format(f2)+"["+Util.format(a[i].cluster_trg,2)+"]"+"    "+a[i].name);
		}
		opSummary.println();
	}
	
	static String searchIndexforName(int index){
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.index==index){
				return(l.name);
			}
				
			l=l.next;
		}
		return("invalid index");
	}
	
	static double searchIndexforfMeasureSource(int index){
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.index==index){
				return(l.val_source);
			}
				
			l=l.next;
		}
		return(0);
	}
	
	static double searchIndexforfMeasureTarget(int index){
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.index==index){
				return(l.val_target);
			}
				
			l=l.next;
		}
		return(0);
	}
	
	static int[] commonLabelsIndex(double[]s,double[]t){
		double labels[][]=new double[count][count];
		labels=giveCommonLabels(s,t);
		int n;
		n=Size(labels);
		int [] commonLabels= new int[n];
		int j=0;
		for(int i=0;i<count&&j<n;i++){
			if(labels[i][i]!=0){
				commonLabels[j]=i;				//assigning the index values of non zero elements into the array
				j++;
			}
		}
		return(commonLabels);
	}
	
	static int[] nonCommonLabelsIndex(double[]s,double[]t,int flg){
		double labels[][]=new double[count][count];
		if(flg==1)
			labels=giveNonCommonLabels1(s,t);
		else
			labels=giveNonCommonLabels2(s,t);
		int n;
		n=Size(labels);
		int [] NonCommonLabels= new int[n];
		int j=0;
		for(int i=0;i<count&&j<n;i++){
			if(labels[i][i]!=0){
				NonCommonLabels[j]=i;				//assigning the index values of non zero elements into the array
				j++;
			}
		}
		return(NonCommonLabels);
	}
	
	static int[] labelsIndex(double[]c){
		double labels[][]=new double[count][count];
		labels=giveLabels(c);
		int n;
		n=Size(labels);
		int [] commonLabels= new int[n];
		int j=0;
		for(int i=0;i<count&&j<n;i++){
			if(labels[i][i]!=0){
				commonLabels[j]=i;				//assigning the index values of non zero elements into the array
				j++;
			}
		}
		return(commonLabels);
	}	
	
	static int Size(double[][]labels){
		int n=0;
		for(int i=0;i<count;i++){
			if(labels[i][i]!=0)
				n++;
		}
		return(n);
	}
	
	static double average(double[]in){
		double avg=0;
		int count=0;
		double sum=0;
		for(int i=0;i<in.length;i++){
			if(in[i]!=0)
				count++;
			sum+=in[i];
		}
		avg=sum/count;
		return(avg);
	}
	
	static double numerator(double[] s,double[] t){
		double sum=0;
		double[][] labels1=new double[count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels1[i][j]=0;
			}
		}
		labels1=giveCommonLabels(s,t);
		for(int i=0;i<count;i++){
			sum+=Sim(labels1[i],t);
		}
		return(sum);
	}

	static double denominator(double[] s,double[] t){
		double sum=0;
		double[][]labels=new double[count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels[i][j]=0;
			}
		}
		labels=giveLabels(t);
		for(int i=0;i<count;i++){
			sum+=Sim(labels[i],t);
		}
		return(sum);
	}
	
	static double[][] giveLabels(double[] c){
		double[][]labels2=new double [count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels2[i][j]=0;
			}
		}
		//labels=labelsList;
		int row=0;
		for(row=0;row<count;row++){
				if(c[row]!=0.0)
					labels2[row][row]= c[row];
		}
		return(labels2);
	}
	
	static double[][] giveCommonLabels(double[] s,double[] t){
		double[][]labels3=new double [count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels3[i][j]=0;
			}
		}
		//labels=labelsList;
		int row;
		for(row=0;row<count;row++){
				if(s[row]!=0.0&&t[row]!=0)
					labels3[row][row]= t[row];
		}
		return(labels3);
	}
	
	static double[][] giveNonCommonLabels1(double[] s,double[] t){							///labels in source & not in target
		double[][]labels3=new double [count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels3[i][j]=0;
			}
		}
		//labels=labelsList;
		int row;
		for(row=0;row<count;row++){
				if(s[row]!=0.0&&t[row]==0)
					labels3[row][row]= s[row];
		}
		return(labels3);
	}
	
	static double[][] giveNonCommonLabels2(double[] s,double[] t){							///labels in target & not in source
		double[][]labels3=new double [count][count];
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				labels3[i][j]=0;
			}
		}
		//labels=labelsList;
		int row;
		for(row=0;row<count;row++){
				if(s[row]==0.0&&t[row]!=0)
					labels3[row][row]= t[row];
		}
		return(labels3);
	}
	
	static double Sim(double[]l,double[]s){
		double res;
		double ab1=abs(l);
		double ab2=abs(s);
		if(ab1==0||ab2==0){
			return (0.0);
		}
		else
			res=dot(l,s)/(ab1);					//resived measure....
		
		return(res);
	}
	
	static double dot(double[]l,double[]s){
		double sum=0;
		for(int i=0;i<l.length;i++){
			sum+=l[i]*s[i];
		}
		return(sum);
	}
	
	static double abs(double[]l){
		double sum=0;
		for(int i=0;i<l.length;i++){
			sum+=l[i]*l[i];
		}
		return(Math.sqrt(sum));
	}
	
	static void processLine(String line){
		//System.out.println(line);
		Scanner sc=new Scanner(line).useDelimiter("\t");
		if(flag==0)
			countsrc++;
		else
			counttrg++;
		sc.next();
		sc.next();
		sc.next();
		double val=0;
		while(sc.hasNext()){
			temp.index=count;
			temp.name=sc.next();
			//System.out.print(temp.name+"\t");
			if(sc.hasNextDouble()){
				//System.out.println("val="+sc.next());
				val=sc.nextDouble();
				//System.out.print(val+"\t"+"\n");
			}
			if(flag==0){								//source map
				temp.next=new label();
				temp.val_source=val;
				//System.out.print(temp.val_source+"\t"+"\n");
				temp.val_target=0;
				temp=temp.next;
				temp.cluster_src=countsrc;
				temp.cluster_trg=-1;
				count++;
			}
			else{										//target map
				int b;
				b=search(temp.name);
				//System.out.println(b);
				if(b==-1){
					temp.next=new label();
					temp.val_target=val;
					temp.val_source=0;
					temp=temp.next;
					count++;
					temp.cluster_trg=counttrg;
					temp.cluster_src=-1;
				}
				else{
					label t=new label();
					t=start;
					for(int i=0;i<=b;i++){
						t=t.next;
					}
					t.val_target=val;
					t.cluster_trg=counttrg;
				}
			}
		
		}
	}
	
	void printVector(double[][]f) throws IOException{
		PrintStream out;
		out = new PrintStream(new FileOutputStream("./output"));
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				out.print(f[i][j]+"  ");
			}
			out.print("\n");
		}
		out.close();
	}
	
	static double[][] createVector(){
		double[][] Vector=new double[count][count];
		temp=start.next;
		for(int i=0;i<count;i++){
			for(int j=0;j<count;j++){
				Vector[i][j]=0;
			}
			Vector[i][i]=temp.val_source;
			temp=temp.next;
		}
		return(Vector);
	}
	
	static double[] processLineforCluster(String line){
		double[] res=new double[count];
		int ind;
		String str;
		Scanner sc=new Scanner(line).useDelimiter("\t");
		sc.next();
		lab=sc.nextInt();
		docs=sc.nextInt();
		while(sc.hasNext()){
			str=sc.next();
			ind=search(str);
			if(sc.hasNextDouble()&&ind>=0){
				res[ind]=sc.nextDouble();
			}
			else
				System.out.println("not in global list or wrong pattern of input!!!!");
		}
		
		/*for(int i=0;i<res.length;i++){
			System.out.print(res[i]+"\t");
		}
		System.out.print("\n");*/
		return(res);

	}
	
	static int search(String name){
		label l=new label();
		l=start.next;
		//System.out.println(name);
		while(l.next!=null){
			if(l.name.equals(name)){
				return(l.index);
			}
				
			l=l.next;
		}
		return(-1);
	}
	
 	static void printOP(){
		//System.out.println("bajar");
 		label temp=new label();
		temp=start;
		temp=temp.next;
		while(temp!=null){
			System.out.print(temp.index+" : "+"\t");
			System.out.print(temp.name+"\t");
			System.out.print(temp.val_source+"\t");
			System.out.println(temp.val_target+"\t");
			if(temp.next==null)
				break;
			else
				temp=temp.next;
		}
	}
}

