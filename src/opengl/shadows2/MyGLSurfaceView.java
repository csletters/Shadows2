package opengl.shadows2;

import android.content.Context;
import android.opengl.GLSurfaceView;

public class MyGLSurfaceView extends GLSurfaceView {
	
	
	MyRenderer renderer;
    public MyGLSurfaceView(Context context){
        super(context);

        // Set the Renderer for drawing on the GLSurfaceView
        setEGLContextClientVersion(2);
        renderer = new MyRenderer(context);
        setRenderer(renderer);

    }
    
}
