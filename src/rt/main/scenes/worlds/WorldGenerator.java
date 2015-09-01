package rt.main.scenes.worlds;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import rt.main.utils.Smart;


public class WorldGenerator{
	public World world;
	Random random = new Random();

	public WorldGenerator(World world){
		this.world = world;
	}


	public boolean generate() {
		BufferedImage map_height = new BufferedImage(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, BufferedImage.TYPE_INT_RGB);
		BufferedImage map = new BufferedImage(Chunk.WIDTH * world.chunk_number, Chunk.WIDTH * world.chunk_number, BufferedImage.TYPE_INT_RGB);

		// map biomes //
		for(int x = 0; x < map.getWidth(); x++){
			for(int z = 0; z < map.getHeight(); z++){
				int r = 0;
				int g = 255;
				int b = 0;

				if(random.nextInt(1000) == 0){

					int mountainSize = random.nextInt(24);
					for(int xx = -mountainSize; xx < mountainSize; xx++){
						for(int yy = -mountainSize; yy < mountainSize; yy++){

							float distance = (float) Math.sqrt(((x+xx) - x)*((x+xx) - x) + ((z+yy) - z)*((z+yy) - z));
							if(distance <= mountainSize && distance >= 0){
								Color color = new Color(255, 0, 0);
								map.setRGB(Smart.mod(x+xx, map.getWidth()), Smart.mod(z+yy, map.getHeight()), color.getRGB());
							}

						}
					}
				}


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
		
		
		// - SET BASE HEIGHT - //
		int normal_height = 16;
		for(int x = 0; x < map_height.getWidth(); x++){
			for(int z = 0; z < map_height.getHeight(); z++){
				int r = 0;
				int g = 0;
				int b = 0;

				Color color = new Color(normal_height, g ,b);
				map_height.setRGB(x, z, color.getRGB());

			}
		}


		for(int x = 0; x < map_height.getWidth(); x++){
			for(int z = 0; z < map_height.getHeight(); z++){
				int r = 0;
				int g = 0;
				int b = 0;

				Color color = new Color(normal_height, g ,b);

				// - MOUNTAINS - //
				if(random.nextInt(60) == 0 && new Color(map.getRGB(x, z)).getRed() != 255){
					int mountainSize = random.nextInt(10);
					for(int xx = -mountainSize; xx < mountainSize; xx++){
						for(int yy = -mountainSize; yy < mountainSize; yy++){
							if(x+xx > 0 && x+xx < map_height.getWidth() && z+yy > 0 && z+yy < map_height.getHeight()){

								float distance = (float) Math.sqrt(((x+xx) - x)*((x+xx) - x) + ((z+yy) - z)*((z+yy) - z));
								if(distance <= mountainSize && distance >= 0){
									int height = normal_height + (int)((mountainSize) - distance);

									color = new Color(height, g ,b);
									map_height.setRGB(x+xx, z+yy, color.getRGB());
								}
							}
						}
					}
				}

				// - SLOPES - //
				if(random.nextInt(60) == 0 && new Color(map.getRGB(x, z)).getRed() != 255){
					int rough = 1;
					if(random.nextInt(3) == 0){
						rough = random.nextInt(4)+1;
					}

					int mountainSize = random.nextInt(10);
					for(int xx = -mountainSize; xx < mountainSize; xx++){
						for(int yy = -mountainSize; yy < mountainSize; yy++){
							if(x+xx > 0 && x+xx < map_height.getWidth() && z+yy > 0 && z+yy < map_height.getHeight()){

								float distance = (float) Math.sqrt(((x+xx) - x)*((x+xx) - x) + ((z+yy) - z)*((z+yy) - z));
								if(distance <= mountainSize && distance >= 0){
									int height = (normal_height / rough) - (int)((mountainSize) - distance);
									color = new Color(Math.max(0, height), g ,b);
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


		return true;
	}
}
