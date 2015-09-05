package rt.main;

import java.awt.Dimension;
import java.util.ArrayList;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import rt.main.scenes.worlds.World;

/**
* <h1>Segmentengine</h1>
* <p>
* The Segmentengine is a game engine.
* Segmentengine's main purpose is to help people create block-based (voxel) games.
* </p>
*
* @author  Sebastian Robert Karlsson
* @version 1.0
* @since   2015-09-15 
*/
public class Game {
	
	/*
 	* Setting up the size & dimension for our display.
 	*/
	public static int WIDTH = 640;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 2;
	public static Dimension DISPLAY_SIZE = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	
	/*
	 * Setting up our scene-buffer and initializing a scene-index that will be used as a pointer.
	 */
	private static ArrayList<Scene> SCENES = new ArrayList<Scene>();
	private static int SCENEINDEX = 0;
	
	/*
	 * Initializing some variables for timing.
	 */
    long lastFrame;
    int fps;
    long lastFPS;
	
    /*
     * Our open point for the program, creating a new instance of itself. (A game object).
     */
	public static void main(String[] args) throws LWJGLException{
		new Game();
	}
	
	public Game() throws LWJGLException{
		
		/*
		 * Creating our display.
		 */
		Display.setTitle("RoofTop Game");
		setDisplayMode(DISPLAY_SIZE.width/2, DISPLAY_SIZE.height/2, true);
		Display.create();
		
		/*
		 * Adding scenes to our game.
		 */
		setScenes(new Scene[]{
				new World()
		});
		
		/*
		 * Getting the delta & last-FPS values.
		 */
		getDelta();
		lastFPS = getTime();
		
		/*
		 * The GameLoop, here we draw & update scenes, actors and others.
		 */
		while(!Display.isCloseRequested()){
			/*
			 * Calculating our delta-time.
			 */
			int delta = getDelta();
			
			/*
			 * Fetching some objects from the current scene and the scene.
			 */
			Scene scene = getCurrentScene();
			Camera camera = scene.getCamera();
	        
			/*
			 * Updates our graphics, initializing 3D rendering. (This should probably be in the Camera class later.
			 */
			make3D();
			
			/*
			 * Clearing our graphics-buffer and replacing it with white color.
			 */
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
			GL11.glClearColor((float)scene.backgroundColor.getRed()/255, (float)scene.backgroundColor.getGreen()/255, (float)scene.backgroundColor.getBlue()/255, 1f);
			GL11.glColor3f(1f, 1f, 1f);
			
			/*
			 * Initializing the current scene if it hasn't already been initialized.
			 */
			if(!scene.isInitialized()){
				scene.initialize();
			}
			
			/*
			 * Pushing our graphics matrix.
			 */
			GL11.glPushMatrix();
			
			/*
			 * Rotating and translating our matrix with the current scene's camera.
			 */
			GL11.glRotatef(camera.xrot, 1, 0, 0);
			GL11.glRotatef(camera.yrot, 0, 1, 0);
			GL11.glTranslatef(-camera.x, -camera.y, -camera.z);
			
			/*
			 * Updating the current scene along with it's camera.
			 */
			camera.update(delta);
			scene.update(delta);
			
			/*
			 * Finally, we're popping our matrix.
			 */
			GL11.glPopMatrix();
			
			
			/*
			 * Making our display sync to 60FPS and then we're updating our display.
			 */
			Display.sync(60);
			Display.update();
			
			/*
			 * Making it possible to terminate the program with the ESCAPE key.
			 */
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				System.exit(0);
			}
		}
		
		/*
		 * Terminating our program if it runs out of the GameLoop. (Could be caused by a close-request)
		 */
		System.exit(0);
	}
	
	/**
	 * Set our view to 2D.
	 */
	protected static void make2D() {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), 0, Display.getHeight(), -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }
 
	/**
	 * Set our view to 3D.
	 */
    protected static void make3D() {
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(92f, (float)Display.getWidth()/Display.getHeight(), 0.1f, 1000);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
    }
	
	/**
	 * Set the display mode to be used 
	 * 
	 * @param width The width of the display required
	 * @param height The height of the display required
	 * @param fullscreen True if we want fullscreen mode
	 */
	private void setDisplayMode(int width, int height, boolean fullscreen) {
		
	    if ((Display.getDisplayMode().getWidth() == width) && 
	        (Display.getDisplayMode().getHeight() == height) && 
	    (Display.isFullscreen() == fullscreen)) {
	        return;
	    }
	 
	    try {
	        DisplayMode targetDisplayMode = null;
	         
	    if (fullscreen) {
	        DisplayMode[] modes = Display.getAvailableDisplayModes();
	        int freq = 0;
	                 
	        for (int i=0;i<modes.length;i++) {
	            DisplayMode current = modes[i];
	                     
	        if ((current.getWidth() == width) && (current.getHeight() == height)) {
	            if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
	                if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
	                targetDisplayMode = current;
	                freq = targetDisplayMode.getFrequency();
	                        }
	                    }
	            if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
	                        (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
	                            targetDisplayMode = current;
	                            break;
	                    }
	                }
	            }
	        } else {
	            targetDisplayMode = new DisplayMode(width,height);
	        }
	 
	        if (targetDisplayMode == null) {
	            System.out.println("Failed to find value mode: "+width+"x"+height+" fs="+fullscreen);
	            return;
	        }
	 
	        Display.setDisplayMode(targetDisplayMode);
	        Display.setFullscreen(fullscreen);
	             
	    } catch (LWJGLException e) {
	        System.out.println("Unable to setup mode "+width+"x"+height+" fullscreen="+fullscreen + e);
	    }
	}
	
	/**
	 * Used to get all of the scenes in a list.
	 *
	 * @return an Arraylist of scenes.
	 */
	public ArrayList<Scene> getScenes(){
		return SCENES;
	}
	
	/**
	 * Used to set which scenes to use.
	 * 
	 * @param scenes an array of scenes.
	 */
	public void setScenes(Scene[] scenes){
		ArrayList<Scene> s = new ArrayList<Scene>();
		for(Scene scene : scenes){
			s.add(scene);
		}
		
		SCENES = s;
	}
	
	/**
	 * Used to get the current scene that our pointer is pointing at.
	 * 
	 * @return the current scene.
	 */
	public static Scene getCurrentScene(){
		return SCENES.get(SCENEINDEX);
	}
	
	/**
	 * Used to get the value of our scene-pointer.
	 * 
	 * @return an Integer of the current SCENEINDEX.
	 */
	public int getSceneIndex(){
		return SCENEINDEX;
	}
	
	/**
	 * Used to set our pointer to a certain index.
	 * 
	 * @param index an Integer.
	 */
	public void setSceneIndex(int index){
		SCENEINDEX = index;
	}
	
	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds.
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	/**
	 * Used to get the delta-time.
	 * 
	 * @return delta-time (Integer)
	 */
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
}
