package rt.main.scenes.worlds;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

import javax.imageio.ImageIO;


public class WorldGenerator{
	public World world;
	Random random = new Random();
	public NoiseGenerator noisegenerator = new NoiseGenerator(7);

	public WorldGenerator(World world){
		this.world = world;
	}


	public boolean generate() {
		
		BufferedImage map_height = new BufferedImage(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, BufferedImage.TYPE_INT_RGB);
		float[][] colors = noisegenerator.generateOctavedSimplexNoise(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, 20, 0.5f, 0.001f);
		
		for(int x = 0; x < map_height.getWidth(); x++){
			for(int y = 0; y < map_height.getHeight(); y++){
				float nn = colors[x][y];
				if(nn < 0){ nn = nn * -1; }
				nn = nn * 25;
				if(nn >= 16){
					nn = 15;
				}
				map_height.setRGB(x, y, new Color((int)nn, (int)nn,(int)nn).getRGB());
			}
		}

		try {
			File outputfile = new File("world/map/map_height.png");
			ImageIO.write(map_height, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}


		return true;
	}
	
	private static boolean isDirEmpty(final Path directory) throws IOException {
	    try(DirectoryStream<Path> dirStream = Files.newDirectoryStream(directory)) {
	        return !dirStream.iterator().hasNext();
	    }
	}
}
