package EDUMVC;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Enum for map position including NORTHEAST, NORTHWEST, SOUTHEAST, SOUTHWEST
 * @author percypan
 *
 */
public enum MapPosition implements Serializable{
	
	//SOUTH,
	//EAST,
	//NORTH,
	//WEST,
	NORTHWEST,
	NORTHEAST,
	SOUTHWEST,
	SOUTHEAST;
	
	
	private static final List<MapPosition> VALUES = Collections.unmodifiableList(Arrays.asList(values()));
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();
	
	/**
	 * Get a random map position
	 * @return the map position
	 */
	public static MapPosition randomMapPosition()  {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
	
}
