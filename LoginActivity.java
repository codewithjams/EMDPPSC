package project.demo.pd1;


import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends ActionBarActivity{
	
	EditText etUname,etPword;
	TextView tv1,tv2;
	Button loginButton,shortcutButton;
	ImageView manMobileConnected,imageSignal;
	String id,password;
	String root_url = "",ip="";
	public static ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		//changing the actionbar's color
		getActionBar().setHomeButtonEnabled(true);
		ActionBar ab = getActionBar();
		ab.setBackgroundDrawable(new ColorDrawable(Color.rgb(30, 110, 200)));
        
		etUname = (EditText) findViewById(R.id.username);
        etPword = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.loginButton);
        shortcutButton = (Button) findViewById(R.id.buttonShortcut);
        tv2 = (TextView) findViewById(R.id.wrongPassword);
        tv1 = (TextView) findViewById(R.id.correctPassword);
        manMobileConnected = (ImageView) findViewById(R.id.imageManMobileConnected);
        imageSignal = (ImageView) findViewById(R.id.imageSignal);

        Bundle bundle = getIntent().getExtras();
        ip = bundle.getString("IP");
        
    	manMobileConnected.setVisibility(View.VISIBLE);
	 	imageSignal.setVisibility(View.VISIBLE);
        
        root_url = "http://"+ip+":8080";

        loginButton.setOnClickListener(new OnClickListener() 
        {
			
			@Override
			public void onClick(View v) 
			{
				id = etUname.getText().toString();
				password = etPword.getText().toString();
				
				if(id.equals("")||password.equals("")){
					Toast.makeText(getApplicationContext(), "Don't you think that's just too much??", Toast.LENGTH_LONG).show();
				}
				else
				{
					pd = new ProgressDialog(LoginActivity.this);
					pd.setTitle("Authenticating");
					pd.setMessage("Sit back and relax, it may take upto a minute or two...who knows...");
					pd.setCancelable(false);
					pd.show();
						
					authorize();
				}
			}
        });
 
        shortcutButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "That button is meant only for developers!", Toast.LENGTH_LONG).show();
				
				Intent logged = new Intent("project.demo.LOGGEDINHOMEACTIVITY");
				logged.putExtra("ID", "KARBANWTF000001");
				logged.putExtra("ROOT_URL", root_url);
				startActivity(logged);
			}
		});
	}
	
	public void authorize()
	{
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(root_url).build();
		TheAPI api = adapter.create(TheAPI.class);
		api.login(id, password, new Callback<LoginReplyFormat>() {
			@Override
			public void success(LoginReplyFormat response, Response arg1) {
				if(pd!=null)
					pd.dismiss();
				if(response.success.equals("1")){
					if(tv2.getVisibility()==View.VISIBLE)
						tv2.setVisibility(View.INVISIBLE);
					tv1.setVisibility(View.VISIBLE);
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						@Override
						public void run() {
							Intent logged = new Intent("project.demo.LOGGEDINHOMEACTIVITY");
							logged.putExtra("ID", id);
							logged.putExtra("ROOT_URL", root_url);
							startActivity(logged);
						}
					}, 1000);
				}
				else{
					tv2.setVisibility(View.VISIBLE);
				}
			}
			@Override
			public void failure(RetrofitError e) {
				if(pd!=null)
					pd.dismiss();
				Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		tv1.setVisibility(View.INVISIBLE);
		tv2.setVisibility(View.INVISIBLE);
		etPword.setText("");
		etUname.setText("");
		id="";
		password="";
	}
}   
	 
	
	  
  

    	
    

