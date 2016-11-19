package project.demo.pd1;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class AccessActivity extends ActionBarActivity{

	String root_url, loggedId, requestedId, requestedField, targetId;
	TextView tv1, tv2;
	public static ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_access);
		
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		loggedId = bundle.getString("LOGGED_ID");
		requestedId = bundle.getString("REQUESTED_ID");
		requestedField = bundle.getString("REQUESTED_FIELD");
		
		tv1 = (TextView) findViewById(R.id.tvASDFGH);
		tv2 = (TextView) findViewById(R.id.tvZXCVB);
		tv2.setVisibility(View.INVISIBLE);
		tv1.setText("ID: "+loggedId);
		//Toast.makeText(getApplicationContext(), requestedId, Toast.LENGTH_LONG).show();
		pd = new ProgressDialog(AccessActivity.this);
		pd.setMessage("Checking your privilages...");
		pd.setCancelable(false);
		pd.show();
		
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		TheAPI api = adapter.create(TheAPI.class);
		api.checkAccess(loggedId, requestedId, requestedField, new Callback<AccessCheckReplyFormat>() {
			
			@Override
			public void success(AccessCheckReplyFormat response, Response r) {
				
				if(pd!=null)
					pd.dismiss();
				tv2.setVisibility(View.VISIBLE);
				if(response.getSuccess().equals("0")){		//dont have access
					tv2.setText(response.getMessage());
					Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
					
					Handler handler = new Handler();
					handler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							askForRequest();
						}
					}, 2000);
				}
				else{											//have access
					tv2.append("\n"+requestedField+" = "+response.getData());
					Toast.makeText(getApplicationContext(), response.getMessage(), Toast.LENGTH_LONG).show();
				}
				
			}
			
			@Override
			public void failure(RetrofitError e) {
				tv2.setVisibility(View.VISIBLE);
				if(pd!=null)
					pd.dismiss();
				tv2.setText("Sorry...some error occurred....try again!");
				Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_LONG).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
		
		
	}
	
	public void askForRequest(){
		
		AlertDialog.Builder adb = new AlertDialog.Builder(this);		//may get error
		adb.setMessage("Would you like to request access from someone with higher privilages?");
		adb.setPositiveButton("Yes, show me the list", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				Intent intent = new Intent(AccessActivity.this,SendRequest.class);
				intent.putExtra("ROOT_URL",root_url);
				intent.putExtra("LOGGED_ID",loggedId);
				intent.putExtra("REQUESTED_ID",requestedId);
				intent.putExtra("REQUESTED_FIELD", requestedField);
				startActivity(intent);
				
			}
		});
		adb.setNegativeButton("No, take me back.", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
				
			}
		});
		AlertDialog ad = adb.create();
		ad.show();
	}
	
}
