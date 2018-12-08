package SUPERMVC;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
/**
 * The super view for each view extends, currently have the scale method and gridconstraints methods
 * @author percypan
 *
 */
@SuppressWarnings("serial")
public class View extends JFrame{
	
	/**
	 * The method to scale the images according to the rates of width and height
	 * @param before the original image
	 * @param scaleRateW the scale rate for the width
	 * @param scaleRateH the scale rate for the height 
	 * @return the images after scaled
	 */
	public BufferedImage scaleImage(BufferedImage before, double scaleRateW, double scaleRateH) {
		int w = before.getWidth();
		int h = before.getHeight();
		BufferedImage after = new BufferedImage((int)(w*scaleRateW), (int)(h*scaleRateH), BufferedImage.TYPE_INT_ARGB);
		AffineTransform at = new AffineTransform();
		at.scale(scaleRateW,scaleRateH);
		AffineTransformOp scaleOp =  new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		after = scaleOp.filter(before, after);
		return after;
	}
	
	/**
	 * Create the bag constraint
	 * @param top the distance between element and the top
	 * @param left the distance between element and the left 
	 * @param bottom the distance between element and the bottom
	 * @param right the distance between element and the right
	 * @param L the anchor of the bag constraint, including CENTER, WEST, EAST, NORTH, SOUTH, SOUTHEAST...
	 * @return the bag constraint for the location of the elmeent
	 */
	public GridBagConstraints createGridConstraints(int top, int left, int bottom, int right, int L) {
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(top,left, bottom,right);
		gbc.anchor = L;
		return gbc;
	}
}
