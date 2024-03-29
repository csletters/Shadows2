package shapes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import loaders.ShaderHandles;

import android.opengl.GLES20;

public class floor {

	float vertices[] = {
			0.0f, 10.0f, 0.0f,
			0.0f, 0.0f, 0.0f,
			10.0f, 0.0f, 0.0f,
			
			0.0f, 10.0f, 0.0f,
			10.0f, 0.0f, 0.0f,
			10.0f, 10.0f, 0.0f,

			};
	
	float normals[] = {
			/*-0.33f,0.33f,0.33f,
			-0.33f,-0.33f,0.33f,
			0.33f,-0.33f,0.33f,
			
			-0.33f,0.33f,0.33f,
			0.33f,-0.33f,0.33f,
			0.33f,0.33f,0.33f
			0.0f,0.0f,1.0f,
			0.0f,0.0f,1.0f,
			0.0f,0.0f,1.0f,
			
			0.0f,0.0f,1.0f,
			0.0f,0.0f,1.0f,
			0.0f,0.0f,1.0f*/
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			0.0f, 0.0f, 1.0f,
			
	};
	

	float uvCords[] = {
			0.0f,1.0f,
			0.0f,0.0f,
			1.0f,0.0f,
			
			0.0f,1.0f,
			1.0f,0.0f,
			1.0f,1.0f
	};
	
	public float color[] = new float[4];
	int buffers[] = new int[3];
	int positions,normalValues,texcords;
	
	FloatBuffer vertexBuffer,normalBuffer,texBuffer;
	
	public floor()
	{
		// buffer for cube vertices
		ByteBuffer buffer = ByteBuffer.allocateDirect(4 * vertices.length);
		buffer.order(ByteOrder.nativeOrder());

		vertexBuffer = buffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);
		
		// buffer for cube normals
		ByteBuffer nBuffer = ByteBuffer.allocateDirect(4 * normals.length);
		nBuffer.order(ByteOrder.nativeOrder());

		normalBuffer = nBuffer.asFloatBuffer();
		normalBuffer.put(normals);
		normalBuffer.position(0);
				
		//buffer for texture cords
		ByteBuffer tBuffer = ByteBuffer.allocateDirect(4 * uvCords.length);
		tBuffer.order(ByteOrder.nativeOrder());

		texBuffer = tBuffer.asFloatBuffer();
		texBuffer.put(uvCords);
		texBuffer.position(0);
		
		//create VBO map
		GLES20.glGenBuffers(3, buffers, 0);
						
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, vertexBuffer.capacity() * 4, vertexBuffer, GLES20.GL_STATIC_DRAW);
				
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[1]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, texBuffer.capacity() * 4, texBuffer, GLES20.GL_STATIC_DRAW);
		
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[2]);
		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, normalBuffer.capacity() * 4, normalBuffer, GLES20.GL_STATIC_DRAW);
				
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
				
		positions = buffers[0];
		texcords = buffers[1];
		normalValues = buffers[2];
		vertexBuffer = null;
		texBuffer = null;
		normalBuffer = null;
	}
	
	//texture draw
	public void draw( float[] projection, float[] view, float[] model,ShaderHandles Shader)
	{

		//position
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, positions);
		GLES20.glEnableVertexAttribArray(Shader.positionHandle);
		GLES20.glVertexAttribPointer(Shader.positionHandle, 3, GLES20.GL_FLOAT, false,	0, 0);

		//normals
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, normalValues);
		GLES20.glEnableVertexAttribArray(Shader.normalHandle);
		GLES20.glVertexAttribPointer(Shader.normalHandle, 3, GLES20.GL_FLOAT, false,0, 0);
	
		//send uniforms
		GLES20.glUniformMatrix4fv(Shader.projectionHandle, 1, false, projection, 0);
		GLES20.glUniformMatrix4fv(Shader.viewHandle, 1, false, view, 0);
		GLES20.glUniformMatrix4fv(Shader.modelHandle, 1, false, model, 0);
		
		
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, vertices.length/3);
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
	}
	


}
