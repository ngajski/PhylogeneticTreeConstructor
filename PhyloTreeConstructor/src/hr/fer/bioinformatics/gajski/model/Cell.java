package hr.fer.bioinformatics.gajski.model;

public class Cell {

	private double distance;
	private int numberOfTaxa;
	
	/**
	 * X coordinate in {@link DistanceMatrix}
	 */
	private int row;
	
	/**
	 * Y coordinate in {@link DistanceMatrix}
	 */
	private int column;


	public Cell(double distance) {
		this.distance = distance;
		numberOfTaxa = 1;
	}
	
	public Cell(double distance,int row, int column) {
		this(distance);
		this.row = row;
		this.column = column;
	}
	
	
	
	public double getDistance() {
		return distance;
	}
	
	public int getColumn() {
		return column;
	}

	public int getRow() {
		return row;
	}
	
	public int getNumberOfTaxa() {
		return numberOfTaxa;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		long temp;
		temp = Double.doubleToLongBits(distance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (column != other.column)
			return false;
		if (Double.doubleToLongBits(distance) != Double.doubleToLongBits(other.distance))
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Taxon " + Double.toString(distance) + ",x:" +row + "y:"+ column;
	}
	
	
	
	
}
