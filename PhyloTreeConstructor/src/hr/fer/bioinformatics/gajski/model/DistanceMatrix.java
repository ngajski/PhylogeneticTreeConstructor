package hr.fer.bioinformatics.gajski.model;

import java.util.ArrayList;
import java.util.List;

public class DistanceMatrix {

	private Cell [][] matrix;
	private int matrixSize;
	
	/**
	 * Number of clusters in row/column. Initially each row/column has got one cluster
	 */
	private List<Integer> clusteredPairs;
	private List<String> taxons;
	
	public static double [][] matrixOne ={{0,2,4,6},{2,0,8,10},{4,8,0,12},{6,10,12,0}}; 
	public static double [][] matrixTwo ={{0,10,12,8,7},{10,0,4,4,14},{12,4,0,6,16},{8,4,6,0,12},{7,14,16,12,0}};
	public static double [][] matrixThree = {{0,20,60,100,90},{20,0,50,90,80},{60,50,0,40,50},{100,90,40,0,30},{90,80,50,30,0}};
	
	public DistanceMatrix() {
		int size = 5;
		this.matrix = new Cell[size][size];
		this.matrixSize = size;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = new Cell(matrixThree[i][j], i, j);
			}
		}
	}
	
	/**
	 * Constructor for testing
	 * 
	 * @param matrix
	 */
	public DistanceMatrix(double[][] matrixForTest, String[] taxons) {
		int size = matrixForTest.length;
		this.matrix = new Cell[size][size];
		this.matrixSize = size;
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				matrix[i][j] = new Cell(matrixForTest[i][j], i, j);
			}
		}
		
		this.clusteredPairs = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			clusteredPairs.add(1);
		}
		
		this.taxons = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			this.taxons.add(taxons[i]);
		}
	}
	
	public DistanceMatrix(int newMatrixSize) {
		this.matrixSize = newMatrixSize;
		this.matrix = new Cell[newMatrixSize][newMatrixSize];
		this.clusteredPairs = new ArrayList<>();
		
		for (int i = 0; i < newMatrixSize; ++i) {
			clusteredPairs.add(1);
		}
	}
	
	public DistanceMatrix(List<String> allignedSequences,List<String> taxons) {
		this(allignedSequences.size());
		this.taxons = taxons;
			
		fillDistanceMatrix(allignedSequences);
	}
	
	private void fillDistanceMatrix(List<String> allignedSequences) {
		int numberOfSequences = allignedSequences.size();
		
		for (int i = 0; i < numberOfSequences; i++) {
			char[] seqOneCharArray = allignedSequences.get(i).toCharArray();
			int sequenceLength = seqOneCharArray.length;
			
			this.matrix[i][i] = new Cell(0.0,i,i);
			
			for (int j = 0; j < numberOfSequences; j++) {
				char[] seqTwoCharArray = allignedSequences.get(j).toCharArray();
				int numberOfMutations = 0;
				
				for (int k = 0; k < sequenceLength; k++) {
					if (seqOneCharArray[k] != '-' && seqTwoCharArray[k] != '-' && seqOneCharArray[k] != seqTwoCharArray[k]) {
						numberOfMutations++;
					}
				}
				
				double distance = calculateDistanceFrom(numberOfMutations,sequenceLength);
				this.matrix[i][j] = new Cell(distance,i,j);
				this.matrix[j][i] = new Cell(distance,j,i);
				
			}
		}		
	}

	private double calculateDistanceFrom(int numberOfMutations, int sequenceLength) {
		double p = (1.0*numberOfMutations / sequenceLength );
		return -1 * Math.log(1 - 4/3*p);
	}
	
	/**
	 * Method returns cell whose distance is the smallest.
	 * Row and column can be obtained by calling {@link Cell}
	 * method <code>getRow()</code> and <code>getColumn()</code>.
	 * 
	 * @return {@link Cell} whose distance is the smallest
	 */
	public Cell getTheSmallestDistanceCell() {
		double smallestDistance = matrix[0][1].getDistance();
		int row = 0;
		int column = 1;
		for (int i = 0; i < matrixSize; i++) {
			for (int j = i+1; j < matrixSize; j++) {
				if (smallestDistance > matrix[i][j].getDistance()) {
					smallestDistance = matrix[i][j].getDistance();
					row = i;
					column =j;
				}
			}
		}
		
		return matrix[row][column];
	}
	
	/**
	 * Return number of clusters in <code>clusteredPairs</code> array
	 * at position given with <code>position</code>
	 * 
	 * @param position in clusteredPairs array
	 * @return number of clusters
	 */
	public int getNumberOfClusters(int position) {
		return clusteredPairs.get(position);
	}
	
	/**
	 * Returns name of taxon in <code>taxons</code> array
	 * at position given with <code>forRoe</code>
	 * 
	 * @param forRow in clusteredPairs array
	 * @return number of clusters
	 */
	public String getTaxonName(int forRow) {
		if (forRow >= taxons.size()) {
			throw new ArrayIndexOutOfBoundsException("Not that many taxons!");
		}
		return taxons.get(forRow);
	}
	/**
	 * Sets new distance to matrix cell given with row and column coordinates,
	 * 
	 * @param row x
	 * @param column y
	 * @param distance new distance between taxons
	 */
	public void updateMatrixCell(int row, int column, double distance) {
		if (row < 0 || row > matrixSize -1) {
			throw new IndexOutOfBoundsException("Row is bigger than matrix size!");
		} else if (column < 0 || column > matrixSize - 1) {
			throw new IndexOutOfBoundsException("Column is bigger than matrix size!");
		}
		
		matrix[row][column] = new Cell(distance, row, column);
	}
	
	/**
	 * Decreases size of clustered pairs array by 1 and increases value of
	 * (number of joined clusters) clustered pairs array at position determined 
	 * by given <code>position</code> by 1. 
	 * 
	 * 
	 * @param distanceMatrix {@link DistanceMatrix}
	 * @param row row/column which represents taxon
	 * @return new clusteredPairs array
	 */
	public List<Integer> calculateClusteredPairs(int row, int column ) {
		
		int n = clusteredPairs.get(row) + clusteredPairs.get(column);
		clusteredPairs.set(row, n);
		clusteredPairs.remove(column);
		
		return clusteredPairs;
	}
	
	/**
	 * Creates new taxon names when when matrix is shrinking
	 * 
	 * @param smallestDistanceCell
	 * @return
	 */
	public List<String> calculateTaxonNames(Cell smallestDistanceCell) {
		int row = smallestDistanceCell.getRow();
		int column = smallestDistanceCell.getColumn();
		String name = "";
		
		String[] colArray = taxons.get(column).split("#");
		String[] rowArray = taxons.get(row).split("#");
		
		if (colArray.length > rowArray.length) {
			name = taxons.get(column) + "#" + taxons.get(row);
		} else {
			name = taxons.get(row) + "#" + taxons.get(column);
		}
				
		taxons.set(row, name);
		taxons.remove(column);
		
		return taxons;
	}
	
	

	public Cell[][] getMatrix() {
		return matrix;
	}
	
	public void setMatrix(Cell[][] matrix) {
		this.matrix = matrix;
	}
	
	public int getMatrixSize() {
		return matrixSize;
	}
	
	public void setMatrixSize(int matrixSize) {
		this.matrixSize = matrixSize;
	}
	
	public List<Integer> getClusteredPairs() {
		return clusteredPairs;
	}
	
	public void setClusteredPairs(List<Integer> clusteredPairs) {
		this.clusteredPairs = clusteredPairs;
	}

	public void setTaxons(List<String> taxons) {
		this.taxons = taxons;
	}
	
	public List<String> getTaxons() {
		return taxons;
	}

	/**
	 * Inserts merged row into this matrix and removes 2 rows and 2 columns from old matrix.
	 * 
	 * @param insertionCells
	 * @param smallestDistanceCell
	 * @param oldMatrix
	 */
	public void insertRow(Cell[] insertionCells, Cell smallestDistanceCell,DistanceMatrix oldMatrix) {
		for (int row = 0; row < matrixSize; ++row) {
			matrix[row][row] = new Cell(0, row, row);
		
			for (int column = row + 1; column < matrixSize; column++) {
				if (row == smallestDistanceCell.getRow()) {
					matrix[row][column] = new Cell(insertionCells[column].getDistance(), row, column);
					matrix[column][row] = new Cell(insertionCells[column].getDistance(), column, row);
				} else if (column == smallestDistanceCell.getRow()) {
					matrix[row][column] = new Cell(insertionCells[row].getDistance(), row, column);
					matrix[column][row] = new Cell(insertionCells[row].getDistance(), column, row);
				} else {
					if (row < smallestDistanceCell.getRow() && column < smallestDistanceCell.getColumn()) {
						matrix[row][column] = new Cell(oldMatrix.getMatrix()[row][column].getDistance(), row, column);
						matrix[column][row] = new Cell(oldMatrix.getMatrix()[row][column].getDistance(), column, row);
					} else if (row >smallestDistanceCell.getRow() && column >  smallestDistanceCell.getColumn()) {
						matrix[row][column] = new Cell(oldMatrix.getMatrix()[row+1][column+1].getDistance(), row, column);
						matrix[column][row] = new Cell(oldMatrix.getMatrix()[row+1][column+1].getDistance(), column, row);
					} else if (row > smallestDistanceCell.getRow() && column < smallestDistanceCell.getColumn()) {
						matrix[row][column] = new Cell(oldMatrix.getMatrix()[row+1][column].getDistance(), row, column);
						matrix[column][row] = new Cell(oldMatrix.getMatrix()[row+1][column].getDistance(), column, row);
					} else if (row < smallestDistanceCell.getRow() && column >= smallestDistanceCell.getColumn()) {
						matrix[row][column] = new Cell(oldMatrix.getMatrix()[row][column+1].getDistance(), row, column);
						matrix[column][row] = new Cell(oldMatrix.getMatrix()[row][column+1].getDistance(), column, row);
					}
				}
			}
		}
	}

	
		
	
}
