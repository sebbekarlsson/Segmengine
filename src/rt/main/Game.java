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

public class Game {
	
	public static int WIDTH = 640;
	public static int HEIGHT = WIDTH / 16 * 9;
	public static int SCALE = 2;
	public static Dimension DISPLAY_SIZE = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
	private static ArrayList<Scene> SCENES = new ArrayList<Scene>();
	private static int SCENEINDEX = 0;
	/** time at last frame */
    long lastFrame;
     
    /** frames per second */
    int fps;
    /** last fps time */
    long lastFPS;
	
	public static void main(String[] args) throws LWJGLException{
		new Game();
	}
	
	public Game() throws LWJGLException{
		Display.setTitle("RoofTop Game");
		setDisplayMode(DISPLAY_SIZE.width/2, DISPLAY_SIZE.height/2, true);
		Display.create();
		
		setScenes(new Scene[]{
				new World()
		});
		
		getDelta();
		lastFPS = getTime();
		while(!Display.isCloseRequested()){
			int delta = getDelta();
			Scene scene = getCurrentScene();
			Camera camera = scene.getCamera();
	        
			make3D();
			
			GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_COLOR_BUFFER_BIT);
			GL11.glClearColor((float)scene.backgroundColor.getRed()/255, (float)scene.backgroundColor.getGreen()/255, (float)scene.backgroundColor.getBlue()/255, 1f);
			GL11.glColor3f(1f, 1f, 1f);
			
			
			if(!scene.isInitialized()){
				scene.initialize();
			}
			
			
			GL11.glPushMatrix();
			
			GL11.glRotatef(camera.xrot, 1, 0, 0);
			GL11.glRotatef(camera.yrot, 0, 1, 0);
			GL11.glTranslatef(-camera.x, -camera.y, -camera.z);
			
			camera.update(delta);
			scene.update(delta);
			
			GL11.glPopMatrix();
			
			
			Display.sync(60);
			Display.update();
			
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				System.exit(0);
			}
		}
		
		System.exit(0);
	}
	
	protected static void make2D() {
        //Remove the Z axis
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
 
    protected static void make3D() {
        //Restore the Z axis
    	GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GLU.gluPerspective(92f, (float)Display.getWidth()/Display.getHeight(), 1f, 1000);
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
	 
	    // return if requested DisplayMode is already set
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
	 
	            // if we've found a match for bpp and frequence against the 
	            // original display mode then it's probably best to go for this one
	            // since it's most likely compatible with the monitor
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
	
	public ArrayList<Scene> getScenes(){
		return SCENES;
	}
	
	public void setScenes(Scene[] scenes){
		ArrayList<Scene> s = new ArrayList<Scene>();
		for(Scene scene : scenes){
			s.add(scene);
		}
		
		SCENES = s;
	}
	
	public static Scene getCurrentScene(){
		return SCENES.get(SCENEINDEX);
	}
	
	public int getSceneIndex(){
		return SCENEINDEX;
	}
	
	public void setSceneIndex(int index){
		SCENEINDEX = index;
	}
	
	/**
	 * Get the time in milliseconds
	 * 
	 * @return The system time in milliseconds
	 */
	public static long getTime() {
		return (Sys.getTime() * 1000) / Sys.getTimerResolution();
	}
	
	public int getDelta() {
	    long time = getTime();
	    int delta = (int) (time - lastFrame);
	    lastFrame = time;
	         
	    return delta;
	}
}
