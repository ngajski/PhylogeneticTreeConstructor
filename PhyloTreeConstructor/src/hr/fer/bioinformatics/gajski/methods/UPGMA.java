package hr.fer.bioinformatics.gajski.methods;

import java.util.List;

import hr.fer.bioinformatics.gajski.model.Cell;
import hr.fer.bioinformatics.gajski.tree.TreeNode;
import hr.fer.bioinformatics.gajski.model.DistanceMatrix;

public class UPGMA {

	private DistanceMatrix distanceMatrix;
	private TreeNode root;
	
	public UPGMA(DistanceMatrix matrix) {
		this.distanceMatrix = matrix;
		this.root = new TreeNode();
		createTree();
	}
	
	private void createTree() {
		DistanceMatrix newMatrix = null;
		
		while (this.distanceMatrix.getMatrixSize() > 1) {
			Cell smallestDistanceCell = distanceMatrix.getTheSmallestDistanceCell();
			int smallestDistanceCellRow = smallestDistanceCell.getRow();				
			int smallestDistanceCellColumn = smallestDistanceCell.getColumn();
			int newMatrixSize = distanceMatrix.getMatrixSize() - 1;
			List<Integer> oldClusters = distanceMatrix.getClusteredPairs();
			Cell[][] oldTaxonMatrix = distanceMatrix.getMatrix();

			newMatrix = new DistanceMatrix(newMatrixSize);

			double clusterRowNum = oldClusters.get(smallestDistanceCellRow);
			double clusterColumnNum = oldClusters.get(smallestDistanceCellColumn);

			Cell[] newCells = new Cell[newMatrixSize];
			int index = 0;
			
			root = addChildrenToParentNode(root,distanceMatrix);
			
			for (int row = 0; row < newMatrixSize + 1; ++row) {
				double distance = 0;
				if (row != smallestDistanceCellRow) {
					if (row == smallestDistanceCellColumn) {
						continue;
					}
					
					for (int column = 0; column < newMatrixSize + 1; column++) {
						if (column == smallestDistanceCellRow) {
							distance += (clusterRowNum / (clusterRowNum + clusterColumnNum))
									* oldTaxonMatrix[row][column].getDistance();
						} else if (column == smallestDistanceCellColumn) {
							distance += (clusterColumnNum / (clusterRowNum + clusterColumnNum))
									* oldTaxonMatrix[row][column].getDistance();
						}
					}
				}

				newCells[index] = new Cell(distance, index, smallestDistanceCellRow);
				index++;
			}
					
			newMatrix.setClusteredPairs(distanceMatrix.calculateClusteredPairs(smallestDistanceCellRow,smallestDistanceCellColumn));
			newMatrix.setTaxons(distanceMatrix.calculateTaxonNames(smallestDistanceCell));
			newMatrix.insertRow(newCells,smallestDistanceCell,distanceMatrix);
			distanceMatrix = newMatrix;
			
		}
	}
	
	
	/**
	 * Adds left and right node to parent node.
	 * 
	 * @param parentNode
	 * @param smallestDistanceCell
	 */
//	private TreeNode addChildrenToParentNode(TreeNode parentNode, DistanceMatrix distanceMatrix) {
//		//TODO usavršiti
//		Cell smallestDistanceCell = distanceMatrix.getTheSmallestDistanceCell();
//		String taxonRow = distanceMatrix.getTaxonName(smallestDistanceCell.getRow());
//		String taxonColumn = distanceMatrix.getTaxonName(smallestDistanceCell.getColumn());
//		double height = smallestDistanceCell.getDistance() / 2;
//		
//		TreeNode leftChild = new TreeNode(height);
//		TreeNode rightChild  = new TreeNode(height);
//		
//		leftChild.setTaxonName(distanceMatrix.getTaxonName(smallestDistanceCell.getRow()));
//		rightChild.setTaxonName(distanceMatrix.getTaxonName(smallestDistanceCell.getColumn()));
//		
//		if (!parentNode.hasChildren()) {
//			leftChild.setDistanceFromLeaf(0);
//			rightChild.setDistanceFromLeaf(0);
//			
//			parentNode.addChildren(leftChild, rightChild);
//			parentNode.setDistanceFromChildren(height,height);
//			parentNode.setDistanceFromLeaf(height);
//			parentNode.setTaxonName(leftChild.getTaxonName()+ "|" + rightChild.getTaxonName());
//			
//		} else if (taxonRow.equals(parentNode.getTaxonName())){
//			leftChild.addChildren(parentNode.getLeftChild(), parentNode.getRigthChild());
//			leftChild.setHeight(height - parentNode.getLeftChildDst());
//			leftChild.setDistanceFromChildren(parentNode.getLeftChildDst(), parentNode.getRightChildDst());
//			leftChild.setDistanceFromLeaf(parentNode.getDistanceFromLeaf());
//			
//			parentNode.addChildren(leftChild, rightChild);
//			parentNode.setDistanceFromChildren(leftChild.getHeigth(),height);
//			parentNode.setDistanceFromLeaf(height);
//			parentNode.setTaxonName(leftChild.getTaxonName()+ "|" +rightChild.getTaxonName());
//		} else if (taxonRow.equals(parentNode.getLeftChild())){
//			
//		} else if (taxonRow.equals(parentNode.getRigthChild().getTaxonName())) {
//			
//		} else if (parentNode.getLeftChildDst() > 0.0) {
//			TreeNode middleNode = new TreeNode();
//			middleNode.addChildren(leftChild,rightChild);
//			middleNode.setDistanceFromChildren(height,height);
//			middleNode.setTaxonName(leftChild.getTaxonName()+ "|" +rightChild.getTaxonName());
//			middleNode.setDistanceFromLeaf(height);
//			
//			TreeNode newParentNode = new TreeNode();
//			newParentNode.addChildren(parentNode, middleNode);
//			newParentNode.setDistanceFromChildren(0, 0);
//			parentNode = newParentNode;
//			parentNode.setTaxonName(newParentNode.getLeftChild().getTaxonName()+ "|" +middleNode.getTaxonName());
//		} else if (taxonColumn.equals(parentNode.getLeftChild().getTaxonName())){
//			
//		} else if (taxonColumn.equals(parentNode.getRigthChild().getTaxonName())) {
//			TreeNode middleNode = new TreeNode();
//			TreeNode lChild = parentNode.getRigthChild();
//			lChild.setHeight(height - lChild.getDistanceFromLeaf());
//			
//			middleNode.addChildren(lChild,leftChild);
//			middleNode.setDistanceFromChildren(lChild.getHeigth(),leftChild.getHeigth());
//			middleNode.setTaxonName(lChild.getTaxonName() + "|" +leftChild.getTaxonName());
//			middleNode.setDistanceFromLeaf(height);
//			
//			parentNode.addChildren(parentNode.getLeftChild(),middleNode);
//			parentNode.setTaxonName(parentNode.getLeftChild().getTaxonName()+ "|" + middleNode.getTaxonName());
//			
//		} else {
//			double lChildHeight = height - parentNode.getLeftChild().getDistanceFromLeaf();
//			double rChildHeight = height - parentNode.getRigthChild().getDistanceFromLeaf();
//			
//			parentNode.getLeftChild().setHeight(lChildHeight);
//			parentNode.getRigthChild().setHeight(rChildHeight);
//			
//			parentNode.setDistanceFromChildren(lChildHeight,rChildHeight);
//			parentNode.setDistanceFromLeaf(height);
//		}
//		
//		
//		return parentNode;
//		
//	}
	
	private TreeNode addChildrenToParentNode(TreeNode parentNode, DistanceMatrix distanceMatrix) {
		
		Cell smallestDistanceCell = distanceMatrix.getTheSmallestDistanceCell();

		TreeNode leftChild = new TreeNode(distanceMatrix.getTaxonName(smallestDistanceCell.getRow()));
		TreeNode rightChild  = new TreeNode(distanceMatrix.getTaxonName(smallestDistanceCell.getColumn()));
		
		
		if (!parentNode.hasChildren()) {
			leftChild.setDistanceFromLeaf(0);
			rightChild.setDistanceFromLeaf(0);
			
			parentNode.addChildren(leftChild, rightChild);
			
		} else if (!parentNode.getTaxonName().contains(leftChild.getTaxonName()) && !parentNode.getTaxonName().contains(rightChild.getTaxonName())) {
			TreeNode middleNode = new TreeNode();
			TreeNode newNode = new TreeNode();
			
			newNode.addChildren(leftChild, rightChild);
			
			middleNode.addChildren(parentNode, newNode);
			parentNode = middleNode;
		} else if (parentNode.getTaxonName().equals(leftChild.getTaxonName())) {
			TreeNode middleNode = new TreeNode();
			middleNode.addChildren(parentNode, rightChild);
			parentNode = middleNode;
		} else if (parentNode.getTaxonName().equals(rightChild.getTaxonName())) {
			TreeNode middleNode = new TreeNode();
			middleNode.addChildren(parentNode, leftChild);
			parentNode = middleNode;
		}
		
		return parentNode;
	}

//	/**
//	 * Method calculates distance between joined clusters and other cluster.
//	 * 
//	 * d(C k , C l ) = (|C i | · d(C i , C l ) + |C j | · d(C j , C r )) / (|C i | + |C j |)
//	 * 
//	 * @param smallestDistanceTaxonRow -> |Ci|
//	 * @param smallestDistanceTaxonColumn ->|Cj|
//	 * @param oldClusters 
//	 * @param oldTaxonMatrix
//	 * @param a -> Ci
//	 * @param b -> Cl
//	 * @param c -> Cj
//	 * @param d -> Cr
//	 * @return distance between clusters
//	 */
//	private double calculateDistanceBetweenClusters(int smallestDistanceTaxonRow, int smallestDistanceTaxonColumn, List<Integer> oldClusters,
//			Cell[][] oldTaxonMatrix, int a, int b,int c,int d) {
//		return (oldClusters.get(smallestDistanceTaxonRow)
//				* oldTaxonMatrix[a][b].getDistance()
//				+ oldClusters.get(smallestDistanceTaxonColumn)
//						* oldTaxonMatrix[c][d].getDistance())
//				/ (oldClusters.get(smallestDistanceTaxonRow) + oldClusters.get(smallestDistanceTaxonColumn));
//	}
	
	public DistanceMatrix getDistanceMatrix() {
		return distanceMatrix;
	}
	
	public TreeNode getTreeRoot() {
		return root;
	}
}
