package opengl.shadows2;

import java.util.ArrayList;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import shapes.Cube;
import shapes.floor;

import loaders.RawResourceReader;
import loaders.ShaderHandles;
import loaders.ShaderHelper;
import android.content.Context;
import android.opengl.Matrix;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.os.SystemClock;

public class MyRenderer implements Renderer {

	int widthView,heightView;
	Context mActivityContext;
	float ratio;
	float[] projection = new float[16];
	float[] view = new float[16];
	float[] model = new float[16];
	float[] rotMatrix = new float[16];
	int[] shadow_tex,shadow_buffer;
	int bias;
	float shadowMat[] = {
			0.5f, 0.0f, 0.0f, 0.0f,
			0.0f, 0.5f, 0.0f, 0.0f,
			0.0f, 0.0f, 0.5f, 0.0f,
			0.5f, 0.5f, 0.5f, 1.0f
			};
	ArrayList<ShaderHandles> shaderPrograms = new ArrayList<ShaderHandles>();
	floor ground;
	Cube wall;
	
	public MyRenderer(final Context activityContext)
	{
		mActivityContext = activityContext;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {

		long time = SystemClock.uptimeMillis() % 4000L;
		float angle = 0.090f * ((int) time);
		
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
		GLES20.glUseProgram(shaderPrograms.get(0).programHandle);
		

		Matrix.rotateM(model, 0, -90.0f, 1.0f, 0.0f, 0.0f);
		Matrix.translateM(model, 0,  -5.0f, -5.0f, 0.0f);
		ground.draw(projection, view, model, shaderPrograms.get(0));
		Matrix.setIdentityM(model, 0);
		
		Matrix.translateM(model, 0,  0.0f, 1.0f, 0.0f);
		Matrix.rotateM(model, 0, angle, 0.0f, 1.0f, 0.0f);
		Matrix.translateM(model, 0, -0.5f, -0.5f, 0.5f);
		wall.draw(projection, view, model, shaderPrograms.get(0));
		Matrix.setIdentityM(model, 0);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		// TODO Auto-generated method stub
		GLES20.glViewport(0, 0, width, height);
		ratio = (float)width/height;
		Matrix.perspectiveM(projection, 0, 90, ratio, 0.1f, 100);
		Matrix.setLookAtM(view, 0, 1.0f, 2.0f, 4.0f, 0, 0.0f, 0, 0, 1, 0);
		Matrix.setIdentityM(model, 0);
		widthView = width;
		heightView = height;
		
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		// TODO Auto-generated method stub
		 GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		 GLES20.glClearDepthf(1.0f);  
		 GLES20.glEnable( GLES20.GL_DEPTH_TEST );
		 GLES20.glDepthFunc( GLES20.GL_LESS);
		 GLES20.glDepthMask( true );
		 
		//create shader program handles and program for display textures
		ShaderHandles shader = new ShaderHandles();
		shader.programHandle = createShader(R.raw.vertex,R.raw.fragment);
		initHandles(shader);
		shaderPrograms.add(shader);
		
		ground = new floor();
		wall = new Cube();
		
		shadow_tex = new int[1];
		shadow_buffer = new int[1];
		
		/*GLES20.glGenFramebuffers(1, shadow_buffer, 0);
		GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, shadow_buffer[0]);
		
		GLES20.glGenTextures(1, shadow_tex, 0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, shadow_tex[0]);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);*/
		//GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_DEPT, widthView, heightView, 0, GLES20.GL_RGB, GLES20.GL_UNSIGNED_BYTE, texBuffer);
		

	}
	
	public void initHandles(ShaderHandles shader)
	{
		//attributes
		shader.positionHandle = GLES20.glGetAttribLocation(shader.programHandle, "aPosition");
		shader.normalHandle = GLES20.glGetAttribLocation(shader.programHandle, "normal");
				
		//uniforms
		shader.modelHandle =  GLES20.glGetUniformLocation(shader.programHandle, "model");
		shader.viewHandle =  GLES20.glGetUniformLocation(shader.programHandle, "view");
		shader.projectionHandle =  GLES20.glGetUniformLocation(shader.programHandle, "projection");
	}
	

	
	public int createShader(int vertex, int fragment) {
		String vertexShaderCode = RawResourceReader
				.readTextFileFromRawResource(mActivityContext, vertex);
		String fragmentShaderCode = RawResourceReader.readTextFileFromRawResource(mActivityContext, fragment);

		int vertexShaderHandle = ShaderHelper.compileShader(
				GLES20.GL_VERTEX_SHADER, vertexShaderCode);
		int fragmentShaderHandle = ShaderHelper.compileShader(
				GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);
			
		int mProgram;
		
		mProgram = ShaderHelper.createAndLinkProgram(vertexShaderHandle,fragmentShaderHandle);

		return mProgram;
	}

	
}
