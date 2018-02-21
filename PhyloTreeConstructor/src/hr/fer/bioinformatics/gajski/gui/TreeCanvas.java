package hr.fer.bioinformatics.gajski.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

import hr.fer.bioinformatics.gajski.methods.Method;
import hr.fer.bioinformatics.gajski.methods.UPGMA;
import hr.fer.bioinformatics.gajski.model.DistanceMatrix;
import hr.fer.bioinformatics.gajski.tree.TreeNode;

public class TreeCanvas extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2648155393694281813L;
	private Method method;
	
	
	private static int HORIZONTAL_LENGTH = 50;
	private static int VERTICAL_LENGTH = 45;
	
	public TreeCanvas() {
		this.method = Method.UPGMA;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0,this.getWidth(),this.getHeight());
		System.out.println(Thread.currentThread() + "canvas");
	}
	
	public void drawTree(DistanceMatrix distanceMatrix) {
		if (distanceMatrix == null) {
			throw new IllegalArgumentException("Matrica je NULL!");
		}
		
		if (method == Method.UPGMA) {
			drawUpgmaTree(new UPGMA(distanceMatrix));
		} else {
			
		}
		
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	private void drawUpgmaTree(UPGMA upgma) {
		Graphics2D g2d = (Graphics2D) getGraphics();
		g2d.setColor(Color.black);
		TreeNode root= upgma.getTreeRoot();
		root.iterateThroughChildren(0);
		
		iterateTreeNode(root,g2d,0,getHeight()/2,root.maxDepth(root));
	}

	private void iterateTreeNode(TreeNode treeRoot, Graphics2D g2d, int xAxis,int yAxis, int treeDepth) {
		System.out.println(Thread.currentThread());
		if (!treeRoot.hasChildren()) {
			g2d.drawLine(xAxis, yAxis, treeDepth*HORIZONTAL_LENGTH+HORIZONTAL_LENGTH, yAxis);
			g2d.drawString(treeRoot.getTaxonName(), treeDepth*HORIZONTAL_LENGTH+HORIZONTAL_LENGTH + 10, yAxis);
			return;
		} 
		
		if (treeRoot.getLeftChild() != null) {
			iterateTreeNode(treeRoot.getLeftChild(), g2d, xAxis+HORIZONTAL_LENGTH, yAxis+VERTICAL_LENGTH,treeDepth);
		}
		
		g2d.drawLine(xAxis+HORIZONTAL_LENGTH, yAxis, xAxis+HORIZONTAL_LENGTH, yAxis + VERTICAL_LENGTH);
		g2d.drawLine(xAxis + HORIZONTAL_LENGTH, yAxis, xAxis + HORIZONTAL_LENGTH, yAxis - VERTICAL_LENGTH);
		g2d.drawLine(xAxis, yAxis, xAxis + HORIZONTAL_LENGTH, yAxis);
		
		if (treeRoot.getRigthChild() != null && treeRoot.getRigthChild().hasChildren()) {
			g2d.drawLine(xAxis + HORIZONTAL_LENGTH, yAxis, xAxis + HORIZONTAL_LENGTH, yAxis - VERTICAL_LENGTH - VERTICAL_LENGTH);
			yAxis-=VERTICAL_LENGTH;
//			iterateTreeNode(treeRoot.getLeftChild(), g2d, xAxis, yAxis, treeDepth);
		} 
		
		if (treeRoot.getRigthChild() != null){
			iterateTreeNode(treeRoot.getRigthChild(), g2d, xAxis+HORIZONTAL_LENGTH, yAxis-VERTICAL_LENGTH,treeDepth);
		}
	}
	
	

}
