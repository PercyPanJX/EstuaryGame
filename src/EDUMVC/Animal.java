package EDUMVC;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Animals including crab, turtle, rabbit and pig
 * @author percypan
 *
 */
public enum Animal implements Serializable{
	CRAB, //SOUTHEAST
	TURTLE, //SOUTHWEST
	RABBIT, //NORTHWEST
	PIG; //NORTHEAST

  private static final List<Animal> VALUES =
		  Collections.unmodifiableList(Arrays.asList(values()));
  private static final int SIZE = VALUES.size();
  private static final Random RANDOM = new Random();

  /**
   * The method for returning a random animal
   * @return random animal
   */
  public static Animal randomAnimal()  {
	  return VALUES.get(RANDOM.nextInt(SIZE));
  }
}
