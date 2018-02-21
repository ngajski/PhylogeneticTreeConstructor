package hr.fer.bioinformatics.gajski.tree;

import java.util.ArrayList;
import java.util.List;

import hr.fer.bioinformatics.gajski.model.Cell;

public class TreeNode {

	private Cell node;
	private double leftChildDst;
	private double rightChildDst;
	private double distanceFromLeaf;
	
	private Cell parentNode;
	private double height;
	
	private TreeNode leftChild;
	private TreeNode rigthChild;
	
	private String taxonName;

	
	
	public TreeNode() {
		node = null;
		leftChildDst = 0;
		parentNode = null;
		leftChild = null;
		rigthChild = null;
	}
	
	public TreeNode(String name) {
		super();
		this.taxonName = name;
	}
	
	public TreeNode(double height) {
		this.height = height;
	}
		
	public boolean hasChildren() {
		if (leftChild == null) {
			return false;
		} 
		return true;
	}
	
	/**
	 * Adds children and sets name to this node.
	 * @param leftChild
	 * @param rigthChild
	 */
	public void addChildren(TreeNode leftChild, TreeNode rigthChild) {
		this.leftChild = leftChild;
		this.rigthChild = rigthChild;
		
		this.taxonName = leftChild.getTaxonName() + "#" + rigthChild.getTaxonName();
	}
	
	public void iterateThroughChildren(int level) {
		System.out.println(level + " " + toString());
		level++;
		
		if (leftChild == null) return;
		leftChild.iterateThroughChildren(level);
		
		if (rigthChild == null) return;
		rigthChild.iterateThroughChildren(level);
	}
	
	public int maxDepth(TreeNode node) {
		if (node == null)
			return 0;
		else {
			int lDepth = maxDepth(node.leftChild);
			int rDepth = maxDepth(node.rigthChild);

			if (lDepth > rDepth)
				return (lDepth + 1);
			else
				return (rDepth + 1);
		}
	}
	

	public TreeNode getLeftChild() {
		return leftChild;
	}
	
	public TreeNode getRigthChild() {
		return rigthChild;
	}
	
	public void setHeight(double height) {
		this.height = height;
	}
	
	public double getHeigth() {
		return height;
	}
	
	public void setDistanceFromChildren(double lChildDst, double rChildDst) {
		this.leftChildDst = lChildDst;
		this.rightChildDst = rChildDst;
	}
	
	public void setTaxonName(String taxonName) {
		this.taxonName = taxonName;
	}
	
	public String getTaxonName() {
		return taxonName;
	}
	
	public double getDistanceFromLeaf() {
		return distanceFromLeaf;
	}
	
	public void setDistanceFromLeaf(double distanceFromLead) {
		this.distanceFromLeaf = distanceFromLead;
	}
	
	public double getRightChildDst() {
		return rightChildDst;
	}
	
	public double getLeftChildDst() {
		return leftChildDst;
	}
	
	@Override
	public String toString() {
		String s = taxonName;
		
		if (leftChild != null) {
			s+= " lCh: " + leftChild.getTaxonName();
		}
		
		if (leftChild != null) {
			s+= " rCh: " + rigthChild.getTaxonName();
		}
		
		return s ;
	}

}
