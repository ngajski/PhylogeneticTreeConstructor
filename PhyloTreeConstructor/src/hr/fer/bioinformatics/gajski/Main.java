package hr.fer.bioinformatics.gajski;

import java.io.IOException;

import javax.swing.SwingUtilities;

import hr.fer.bioinformatics.gajski.gui.TreeDrawerFrame;

public class Main {

	public static void main(String[] args) throws IOException {
		
		SwingUtilities.invokeLater(() -> {
			TreeDrawerFrame frame = new TreeDrawerFrame();
			frame.setVisible(true);
		});
//		
//		final File file = new File("examples/allignedClustal.fasta");
//
//		final FASTAFileReader reader = new FASTAFileReaderImpl(file);
//
//		final FASTAElementIterator it = reader.getIterator();
//
//		List<String> allignedSequences = new ArrayList<>();
//		List<String> taxons = new ArrayList<>();
//		// int i = 0;
//		while (it.hasNext()) {
//			final FASTAElement el = it.next();
//			taxons.add(el.getHeader().split(" ")[0]);
//			allignedSequences.add(el.getSequence());
//			// System.out.println(i + " : " + el.getHeader());
//			// System.out.println(el.getLineLength());
//			// System.out.println(el.getSequenceLength());
//			// System.out.println(el.getSequence());
//			// i++;
//		}
//
//		reader.close();
//
//		DistanceMatrix distanceMatrix = new DistanceMatrix(allignedSequences,taxons);
////		 Cell[][] distances = distanceMatrix.getMatrix();
////
////		 System.out.println(distances[2][0]);
////		 System.out.println(distances[0][1]);
//
//		UPGMA upgma = new UPGMA(distanceMatrix);
//
//		upgma.getRTreeRoot().iterateThroughChildren(0);
	}

}
