package view;

import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static javax.media.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLException;
import javax.media.opengl.glu.GLU;
import javax.sound.sampled.Clip;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureIO;



public class Core {
	
	private Texture texture;
	private Texture texture1;
	private Texture texture2;
	private Texture texture3;
	private Texture texture4;

	
	public void loadResources() {
		if (texture == null)
			try {
				texture = TextureIO.newTexture(new File("particlesmoke.tga"), false);
				texture1 = TextureIO.newTexture(new File("ball.png"), false);
				texture2 = TextureIO.newTexture(new File("explosion.png"), false);
				texture3 = TextureIO.newTexture(new File("spark.png"), false);
				texture4 = TextureIO.newTexture(new File("deadball.png"), false);
			} catch (GLException | IOException e) {
				e.printStackTrace();
				System.exit(-1);
			}
	}

	
	public void clearScreen(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear(GL.GL_COLOR_BUFFER_BIT);
	}

	
	public void reshape(GLAutoDrawable drawable, int x, int y, int w, int h) {
   	 	GL2 gl = drawable.getGL().getGL2();
        
   	 	GLU glu = GLU.createGLU(gl);

        gl.glMatrixMode(GL_PROJECTION);
        gl.glLoadIdentity();      
        glu.gluOrtho2D (0.0, w, h, 0);

        gl.glMatrixMode(GL_MODELVIEW);
        gl.glLoadIdentity();
        		
	}

	
	public void drawBox(GLAutoDrawable drawable, float x, float y, float w, float h, float r, float g, float b) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(r, g, b);
			gl.glTexCoord2f(0, 1);	gl.glVertex2f(x, y);
			gl.glTexCoord2f(1, 1);	gl.glVertex2f(x + w, y);
			gl.glTexCoord2f(1, 0);	gl.glVertex2f(x + w, y + h);
			gl.glTexCoord2f(0, 0);	gl.glVertex2f(x, y + h);
		gl.glEnd();	
	}

	public void drawSpark(GLAutoDrawable drawable, float x, float y, float w, float h) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		texture3.enable(gl);
		texture3.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); 
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(1, 1, 1);
				gl.glTexCoord2f(0, 1);	gl.glVertex2f(x, y);
				gl.glTexCoord2f(1, 1);	gl.glVertex2f(x + w, y);
				gl.glTexCoord2f(1, 0);	gl.glVertex2f(x + w, y + h);
				gl.glTexCoord2f(0, 0);	gl.glVertex2f(x, y + h);
			gl.glEnd();
		texture3.disable(gl);	
	}
	
	public void drawSmoke(GLAutoDrawable drawable, float x, float y, float w, float h, float color[]) {
		GL2 gl = drawable.getGL().getGL2();
		

		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		texture.enable(gl);
		texture.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); 
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor4f(color[0], color[1], color[2], color[3]);
				gl.glTexCoord2f(0, 1);	gl.glVertex2f(x, y);
				gl.glTexCoord2f(1, 1);	gl.glVertex2f(x + w, y);
				gl.glTexCoord2f(1, 0);	gl.glVertex2f(x + w, y + h);
				gl.glTexCoord2f(0, 0);	gl.glVertex2f(x, y + h);
			gl.glEnd();
			gl.glPopMatrix(); // Pop the old matrix without the transformations.
		texture.disable(gl);	

	}
	
	public void drawBall(GLAutoDrawable drawable, float x, float y, float w, float h) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		texture1.enable(gl);
		texture1.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); 
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(1, 1, 1);
				gl.glTexCoord2f(0, 1);	gl.glVertex2f(x, y);
				gl.glTexCoord2f(1, 1);	gl.glVertex2f(x + w, y);
				gl.glTexCoord2f(1, 0);	gl.glVertex2f(x + w, y + h);
				gl.glTexCoord2f(0, 0);	gl.glVertex2f(x, y + h);
			gl.glEnd();
			texture1.disable(gl);	
	}
	
	
	public void drawDeadBall(GLAutoDrawable drawable, float x, float y, float w, float h) {
		GL2 gl = drawable.getGL().getGL2();
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		texture4.enable(gl);
		texture4.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); 
			gl.glBegin(GL2.GL_QUADS);
			gl.glColor3f(1, 1, 1);
				gl.glTexCoord2f(0, 1);	gl.glVertex2f(x, y);
				gl.glTexCoord2f(1, 1);	gl.glVertex2f(x + w, y);
				gl.glTexCoord2f(1, 0);	gl.glVertex2f(x + w, y + h);
				gl.glTexCoord2f(0, 0);	gl.glVertex2f(x, y + h);
			gl.glEnd();
			texture4.disable(gl);	
	}
	
	public void drawExplosion(GLAutoDrawable drawable, float x, float y, float w, float h, int frame) {
		GL2 gl = drawable.getGL().getGL2();
		
		float col = frame % 4;
		float row = frame / 4;
		
		row = row / 8;
		row = 1 - row;
		
		gl.glEnable(GL.GL_BLEND);
		gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
		texture2.enable(gl);
		texture2.bind(gl);
        gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE); 
			gl.glBegin(GL2.GL_QUADS);
			gl.glTexCoord2f(col/4, row);						gl.glVertex3f(x, y, 0.0f);
			gl.glTexCoord2f((1+col)/4, row);					gl.glVertex3f(x + w, y, 0.0f);
			gl.glTexCoord2f((1+col)/4, 0.125f+row);				gl.glVertex3f(x + w, y + h, 0.0f);
			gl.glTexCoord2f(col/4, 0.125f+row);					gl.glVertex3f(x, y + h, 0.0f);
			gl.glEnd();
		texture2.disable(gl);	
	}
	
	public void playGunShot(){
		try {
		    String gunSound = "fire.wav";
		    InputStream in = new FileInputStream(gunSound);
		 
		    AudioStream audioStream = new AudioStream(in);
		 
		    AudioPlayer.player.start(audioStream);
		} catch (GLException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}