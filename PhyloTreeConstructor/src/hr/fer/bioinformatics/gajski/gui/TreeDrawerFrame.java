package hr.fer.bioinformatics.gajski.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import hr.fer.bioinformatics.gajski.methods.Method;
import hr.fer.bioinformatics.gajski.methods.UPGMA;
import hr.fer.bioinformatics.gajski.model.DistanceMatrix;
import net.sf.jfasta.FASTAElement;
import net.sf.jfasta.FASTAFileReader;
import net.sf.jfasta.impl.FASTAElementIterator;
import net.sf.jfasta.impl.FASTAFileReaderImpl;

public class TreeDrawerFrame extends JFrame {

	private File fastaFile;
	private TreeCanvas treeCanvas;
	private DistanceMatrix distanceMatrix;
	
	public TreeDrawerFrame() {
		this.setTitle("PhyloTree Constructor ");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocation(800, 300);
		this.setSize(900, 600);
		this.setLayout(new BorderLayout());
		
		this.fastaFile = new File("examples/allignedClustal.fasta");
		try {
			this.distanceMatrix = initDistanceMatrix(fastaFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		initTreeCanvas();
		initMenu();
		
		
	}
	
	/**
	 * Initialize menu bar.
	 */
	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		this.setJMenuBar(menuBar);
		
		JMenuItem openItem = new JMenuItem("Open");
		openItem.addActionListener(event -> {

			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(TreeDrawerFrame.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				fastaFile = fc.getSelectedFile();
				try {
					TreeDrawerFrame.this.distanceMatrix = initDistanceMatrix(fastaFile);
				} catch (IOException e1) {
				}
			}

		});
		menuBar.add(openItem);
		

		JRadioButton rBtnUpgma = new JRadioButton("UPGMA");
		rBtnUpgma.addActionListener(event -> {
			treeCanvas.setMethod(Method.UPGMA);
		});
		
		JRadioButton rBtnNeighbour = new JRadioButton("Neighbour");
		rBtnNeighbour.addActionListener(event -> {
			treeCanvas.setMethod(Method.NEIGHBOR_JOINING);
		});

		ButtonGroup btnGroup = new ButtonGroup();
		btnGroup.add(rBtnNeighbour);
		btnGroup.add(rBtnUpgma);
		btnGroup.setSelected(rBtnUpgma.getModel(), true);

		JMenu methodMenu = new JMenu("Tree method");
		methodMenu.add(rBtnUpgma);
		methodMenu.add(rBtnNeighbour);
		menuBar.add(methodMenu);

		JButton btnDraw = new JButton("Draw");
		btnDraw.addActionListener(event -> {
				TreeDrawerFrame.this.treeCanvas.drawTree(distanceMatrix);
		});
		menuBar.add(btnDraw);

	}	
	
	/**
	 * Initialize tree canvas 
	 */
	private void initTreeCanvas() {
		this.treeCanvas = new TreeCanvas();
		JScrollPane scrollPane = new JScrollPane(treeCanvas);
		add(scrollPane,BorderLayout.CENTER);
		
	}
	
	/**
	 * Returns initialized distance matrix from file given as argument
	 * 
	 * @param fastaFile
	 * @return
	 * @throws IOException
	 */
	private DistanceMatrix initDistanceMatrix(File fastaFile) throws IOException {	
		final FASTAFileReader reader = new FASTAFileReaderImpl(fastaFile);
		final FASTAElementIterator it = reader.getIterator();

		List<String> allignedSequences = new ArrayList<>();
		List<String> taxons = new ArrayList<>();
		
		while (it.hasNext()) {
			final FASTAElement el = it.next();
			taxons.add(el.getHeader().split(" ")[0]);
			allignedSequences.add(el.getSequence());
		}

		reader.close();

		return new DistanceMatrix(allignedSequences,taxons);
	}
}
