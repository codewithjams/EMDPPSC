package project.demo.pd1;

import java.util.concurrent.ScheduledExecutorService;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {

	private static ScheduledExecutorService worker;
	ImageView iv;
	Button b;
	EditText ed;
	String ip;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//changing the color of actionbar
		getActionBar().setHomeButtonEnabled(true);
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(70, 30, 0)));
		
		iv = (ImageView) findViewById(R.id.iv_loading);
		b = (Button) findViewById(R.id.buttonSubmitIP);
		ed = (EditText) findViewById(R.id.etIP);
		
		//Setting up animation
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_icon);
		iv.startAnimation(animation);
		
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
					
				ip = ed.getText().toString();
				Intent myIntent = new Intent("project.demo.LOGINACTIVITY");
				myIntent.putExtra("IP", ip);
				startActivity(myIntent);
			}
		});
		
		
		
		
	}
}
