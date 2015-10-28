package model.matrix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import io.reader.interfaces.IMatrixReader;
import model.matrix.decorator.IMatrix;
import model.util.nuplet.PairF;
import util.SGLogger;


/**
 * Allows to design our proper CSR + CSC format.
 *	<br>This storage format basically represents the original data twice, but only for performance requirements.
 *	Indeed, it allows to iterate easily and efficiently over rows and over columns, taking into account the matrix sparseness.
 *	<br>Furthermore, because CSR and CSC are very compressed formats, the gain still are huge :
 *	<br>-See : article{neelima2012effective,
  title={Effective Sparse Matrix Representation for the GPU Architectures},
  author={Neelima, B and Raghavendra, Prakash S},
  journal={International Journal of Computer Science, Engineering and Applications},
  volume={2},
  number={2},
  pages={151},
  year={2012},
  publisher={Academy and Industry Research Collaboration Center (AIRCC)}
}
<br>
	
	-See article{stanimirovic2009performance,
  title={Performance comparison of storage formats for sparse matrices},
  author={Stanimirovic, Ivan P and Tasic, Milan B},
  journal={FACTA UNIVERSITATIS (NIˇS) Ser. Math. Inform},
  volume={24},
  pages={39--51},
  year={2009}
}<br>
 -See http://www.bu.edu/pasi/files/2011/01/NathanBell1-10-1000.pdf
 * 
 * @author dugue
 *
 *	
 *
 */
public class CsrMatrix implements IMatrix {

	protected ArrayList< PairF > rows;
	protected ArrayList<Integer> cumulative_rows;
	
	protected ArrayList<PairF> columns;
	protected ArrayList<Integer> cumulative_columns;
	
	//Avoid computing several times the sums
	protected float[] sum_line;
	protected float[] sum_col;
	
	private Logger log;
	
	public CsrMatrix(IMatrixReader mr) {
		cumulative_rows=new ArrayList<Integer>(mr.getNb_rows());
		rows = new ArrayList<PairF>(mr.getNb_elmt());
		Iterator<ArrayList<PairF>> it_rows = mr.getMatrix_rows().iterator();
		Iterator<PairF> it_row;
		int elmt_i=0;
		while (it_rows.hasNext()) {
			it_row=it_rows.next().iterator();
			while (it_row.hasNext()) {
				rows.add(it_row.next());
				elmt_i++;
			}
			cumulative_rows.add(elmt_i);
		}
		
		cumulative_columns=new ArrayList<Integer>(mr.getNb_columns());
		columns = new ArrayList<PairF>(mr.getNb_elmt());
		Iterator<ArrayList<PairF>> it_columns = mr.getMatrix_columns().iterator();
		Iterator<PairF> it_column;
		elmt_i=0;
		while (it_columns.hasNext()) {
			it_column=it_columns.next().iterator();
			while (it_column.hasNext()) {
				columns.add(it_column.next());
				elmt_i++;
			}
			cumulative_columns.add(elmt_i);
		}
		
		sum_line = new float[this.getNbRows()];
		sum_col = new float[this.getNbColumns()];
		
		log=SGLogger.getInstance();
		log.debug(this.getNbRows() + " rows");
		log.debug(this.getNbColumns() + " columns");
		log.debug(this.getNbElements() + " non-null elements");
		log.debug((100.0 - (this.getNbElements()*100.0 / ((float)this.getNbColumns() * this.getNbRows()))) + " % of sparseness");
		
	}
	

	public CsrMatrix() {
	}
	
	public int getCumulativeRows(int i) {
		return cumulative_rows.get(i);
	}
	public int getCumulativeColumns(int i) {
		return cumulative_columns.get(i);
	}
	
	public PairF getIinRows(int i) {
		return rows.get(i);
	}
	public PairF getIinColumns(int i) {
		return columns.get(i);
	}
	
	public int getNbElements() {
		return cumulative_rows.get(cumulative_rows.size() - 1);
	}
	public int getNbRows() {
		return cumulative_rows.size();
	}
	public int getNbColumns() {
		return cumulative_columns.size();
	}
	
	/**
	 * If the row sum has already been computed, it returns this sum. Otherwise, it runs the computation, store the result and return it.
	 * 
	 * @param i the row you need to get the sum
	 * @return sum over the i-th row of the matrix
	 */
	public float getSumRow(int i) {
		if (sum_line[i] == 0.0)
			sum_line[i] = getSum(i, true);
		return sum_line[i];
	}
	
	/**
	 * If the column sum has already been computed, it returns this sum. Otherwise, it runs the computation, store the result and return it.
	 * 
	 * @param i the column you need to get the sum
	 * @return sum over the i-th column of the matrix
	 */
	public float getSumCol(int i) {
		if (sum_col[i] == 0.0)
			sum_col[i] = getSum(i, false);
		return sum_col[i];
	}
	
	private float getSum(int i, boolean is_on_row) {
		ArrayList< PairF > rows_or_col;
		ArrayList<Integer> cumulative_rows_or_col;
		if (is_on_row) {
			rows_or_col=this.rows;
			cumulative_rows_or_col=this.cumulative_rows;
		}
		else {
			rows_or_col=this.columns;
			cumulative_rows_or_col=this.cumulative_columns;
		}
		int start;
		int end;
		if (i == 0) {
			start=0;
		}
		else {
			start=cumulative_rows_or_col.get(i-1);
		}
		end =cumulative_rows_or_col.get(i);
		float sum=0f;
		for (int j=start; j < end; j++) {
			sum +=rows_or_col.get(j).getRight();
		}
		return sum;
	}


	@Override
	public String getLabelOfCol(int j) {
		return ""+j;
	}


	@Override
	public String getLabelOfRow(int i) {
		return ""+i;
	}


	@Override
	public List<PairF> getRow(int i) {
		int start;
		int end;
		if (i == 0) {
			start=0;
		}
		else {
			start=cumulative_rows.get(i-1);
		}
		end =cumulative_rows.get(i);
		return rows.subList(start, end);
	}


	@Override
	public List<PairF> getColumn(int j) {
		int start;
		int end;
		if (j == 0) {
			start=0;
		}
		else {
			start=cumulative_columns.get(j-1);
		}
		end =cumulative_columns.get(j);
		return columns.subList(start, end);
	}


	@Override
	public int getIndexOfRowLabel(String label) {
		return Integer.parseInt(label);
	}


	@Override
	public int getIndexOfColLabel(String label) {
		return Integer.parseInt(label);
	}
	
}
