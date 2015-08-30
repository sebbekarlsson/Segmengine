package rt.main.scenes.worlds;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;


public class WorldGenerator{
	public World world;
	Random random = new Random();

	public WorldGenerator(World world){
		this.world = world;
	}


	public boolean generate() {
		BufferedImage map_height = new BufferedImage(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x < map_height.getWidth(); x++){
			for(int z = 0; z < map_height.getHeight(); z++){
				int r = 0;
				int g = 0;
				int b = 0;
				int normal_height = 16;
				
				Color color = new Color(normal_height, g ,b);
				map_height.setRGB(x, z, color.getRGB());
				
				// - MOUNTAINS - //
				if(random.nextInt(60) == 0){
					int mountainSize = random.nextInt(10);
					for(int xx = -mountainSize; xx < mountainSize; xx++){
						for(int yy = -mountainSize; yy < mountainSize; yy++){
							if(x+xx > 0 && x+xx < map_height.getWidth() && z+yy > 0 && z+yy < map_height.getHeight()){
								
								float distance = (float) Math.sqrt((x-x+xx)*(x-x+xx) + (z-z+yy)*(z-z+yy));
								if(distance < mountainSize && distance > 0){
									int height = normal_height + (int)((mountainSize) - distance);
									
									color = new Color(height, g ,b);
									map_height.setRGB(x+xx, z+yy, color.getRGB());
								}
							}
						}
					}
				}
				
				// - SLOPES - //
				if(random.nextInt(60) == 0){
					int slopeSize = random.nextInt(10);
					for(int xx = -slopeSize; xx < slopeSize; xx++){
						for(int yy = -slopeSize; yy < slopeSize; yy++){
							if(x+xx > 0 && x+xx < map_height.getWidth() && z+yy > 0 && z+yy < map_height.getHeight()){
								
								float distance = (float) Math.sqrt((x-x+xx)*(x-x+xx) + (z-z+yy)*(z-z+yy));
								if(distance < slopeSize && distance > 0){
									int height = normal_height - (int)((slopeSize) - distance);
									
									color = new Color(height, g ,b);
									map_height.setRGB(x+xx, z+yy, color.getRGB());
								}
							}
						}
					}
				}
			}
		}

		try {
			File outputfile = new File("world/map/map_height.png");
			ImageIO.write(map_height, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}


		BufferedImage map = new BufferedImage(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, BufferedImage.TYPE_INT_RGB);

		for(int x = 0; x < map.getWidth(); x++){
			for(int z = 0; z < map.getHeight(); z++){
				int r = 0;
				int g = 255;
				int b = 0;


				Color color = new Color(r, g ,b);
				map.setRGB(x, z, color.getRGB());
			}
		}

		try {
			File outputfile = new File("world/map/map.png");
			ImageIO.write(map, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return true;
	}
}
