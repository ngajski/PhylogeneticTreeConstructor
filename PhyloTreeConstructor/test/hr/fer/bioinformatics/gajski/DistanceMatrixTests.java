package hr.fer.bioinformatics.gajski;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;

import org.junit.Test;

import hr.fer.bioinformatics.gajski.model.Cell;
import hr.fer.bioinformatics.gajski.methods.UPGMA;
import hr.fer.bioinformatics.gajski.model.DistanceMatrix;

public class DistanceMatrixTests {

	
	@Test
	public void testGetTheSmallestDistance() {
		
		String[] taxons = {"A","B","C","D","E"};
		DistanceMatrix matrix = new DistanceMatrix(DistanceMatrix.matrixOne,taxons);

		System.out.println(matrix.getTheSmallestDistanceCell());
		
		Cell expected = new Cell(2.0,0,1);
		Cell obtained = matrix.getTheSmallestDistanceCell();
		assertEquals(expected,obtained );
	}
	
	
	@Test
	public void testMatrixOne() {
		String[] taxons = { "A", "B", "C", "D"};
		DistanceMatrix matrix = new DistanceMatrix(DistanceMatrix.matrixOne, taxons);
		UPGMA treeCreator = new UPGMA(matrix);
		DistanceMatrix shrinkedMatrix = treeCreator.getDistanceMatrix();

		double[][] expectedDistances = { { 0, 9.33 }, { 9.33, 0 } };
		String[] expectedTaxons = { "ABC", "D" };
		DistanceMatrix expectedMatrix = new DistanceMatrix(expectedDistances, expectedTaxons);

		Cell[][] excCells = expectedMatrix.getMatrix();
		Cell[][] obtCells = shrinkedMatrix.getMatrix();
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (int i = 0; i < obtCells.length; i++) {
			for (int j = 0; j < obtCells.length; j++) {
				assertEquals(formatter.format(excCells[i][j].getDistance()),
						formatter.format(obtCells[i][j].getDistance()));
			}
		}

		assertEquals(Arrays.asList(expectedTaxons), shrinkedMatrix.getTaxons());
	}
	
	@Test
	public void testMatrixTWO() {
		String[] taxons = {"A","B","C","D","E"};
		DistanceMatrix matrix = new DistanceMatrix(DistanceMatrix.matrixTwo,taxons);
        UPGMA treeCreator = new UPGMA(matrix);
        DistanceMatrix shrinkedMatrix = treeCreator.getDistanceMatrix();

        double[][] expectedDistances = {{0,12.00}, {12.00,0}};
        String[] expectedTaxons = {"AE","BCD"};
        DistanceMatrix expectedMatrix = new DistanceMatrix(expectedDistances,expectedTaxons);
		
		Cell[][] excCells = expectedMatrix.getMatrix();
		Cell[][] obtCells = shrinkedMatrix.getMatrix();
		NumberFormat formatter = new DecimalFormat("#0.00");
		for (int i = 0; i < obtCells.length; i++) {
			for (int j = 0; j < obtCells.length; j++) {
				assertEquals(formatter.format(excCells[i][j].getDistance()), formatter.format(obtCells[i][j].getDistance()));
			}
		}
		
		assertEquals(Arrays.asList(expectedTaxons), shrinkedMatrix.getTaxons());
	}
	
	@Test
	public void testMatrixThree() {
		String[] taxons = {"A","B","C","D","E"};
		DistanceMatrix matrix = new DistanceMatrix(DistanceMatrix.matrixThree,taxons);
        UPGMA treeCreator = new UPGMA(matrix);
        DistanceMatrix shrinkedMatrix = treeCreator.getDistanceMatrix();

        double[][] expectedDistances = {{0,78.33}, {78.33,0}};
        String[] expectedTaxons = {"AB","CDE"};
        DistanceMatrix expectedMatrix = new DistanceMatrix(expectedDistances,expectedTaxons);
		
		Cell[][] excCells = expectedMatrix.getMatrix();
		Cell[][] obtCells = shrinkedMatrix.getMatrix();
		NumberFormat formatter = new DecimalFormat("#0.00");
		
		for (int i = 0; i < obtCells.length; i++) {
			for (int j = 0; j < obtCells.length; j++) {
				assertEquals(formatter.format(excCells[i][j].getDistance()), formatter.format(obtCells[i][j].getDistance()));
			}
		}
		
		assertEquals(Arrays.asList(expectedTaxons), shrinkedMatrix.getTaxons());		
	}
	

}
