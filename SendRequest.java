package project.demo.pd1;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SendRequest extends ActionBarActivity{
	
	String root_url, loggedId, requestedId, requestedField, targetId;
	TextView tv1,tv2;
	int i=0, quantity=0;
	public static ProgressDialog pd;
	HigherPeopleSearchResultFormat response;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_sendrequest);
		
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		loggedId = bundle.getString("LOGGED_ID");
		targetId = bundle.getString("REQUESTED_ID");
		requestedField = bundle.getString("REQUESTED_FIELD");
		
		tv1 = (TextView) findViewById(R.id.tvPOIUY);
		tv2 = (TextView) findViewById(R.id.tvListOfHigherPeople);
		
		tv1.setText("ID: "+loggedId);
		tv2.setVisibility(View.INVISIBLE);
		
		pd = new ProgressDialog(SendRequest.this);
		pd.setMessage("Generating the List...");
		pd.setCancelable(false);
		pd.show();
		
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		TheAPI api = adapter.create(TheAPI.class);
		api.presentHigherLevelPeople(loggedId, new Callback<HigherPeopleSearchResultFormat>() {
			
			@Override
			public void success(HigherPeopleSearchResultFormat response1, Response arg1) {
				
				if(pd!=null)
					pd.dismiss();
				tv2.setVisibility(View.VISIBLE);
				quantity = response1.posts.size();
				String[] peopleList= new String[quantity];
				for(i=0;i<quantity;i++)
					peopleList[i] = new String();
				for(i=0;i<quantity;i++)
				{
					peopleList[i] = response1.getPosts().get(i).getName()+"\nLevel: "+response1.getPosts().get(i).getLevel()+"\n"+response1.getPosts().get(i).getOthers();
				}
				response = response1;
				ListView lv = (ListView) findViewById(R.id.lvListOfHigherPeople);
				ArrayAdapter adapter = new ArrayAdapter<String>(SendRequest.this,R.layout.listview_item,peopleList);
				lv.setAdapter(adapter);
				lv.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long idl) {
						pd.setMessage("Just a sec...");
						pd.show();
						addNotification(response.getPosts().get(position).getId());
						
					}
				});
			}
			
			@Override
			public void failure(RetrofitError e) {
				if(pd!=null)
					pd.dismiss();
				Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
		
		
	}

	public void addNotification(String higherPersonId){
		
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		TheAPI api = adapter.create(TheAPI.class);
		api.addnotification(loggedId, higherPersonId, requestedField, targetId,"RequestPending", new Callback<LoginReplyFormat>() {

			@Override
			public void failure(RetrofitError e) {
				
				if(pd!=null)
					pd.dismiss();
				Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}

			@Override
			public void success(LoginReplyFormat arg0, Response arg1) {
				if(pd!=null)
					pd.dismiss();
				showDialogBox();
			}
		});
		
	}
	public void showDialogBox()
	{
		AlertDialog.Builder adb = new AlertDialog.Builder(this);		//may get error
		adb.setMessage("A notification has been sent. You'll be notified when he allows or denies.");
		adb.setPositiveButton("OK, I got it.", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {

				finish();
			}
		});
		
		
		AlertDialog ad = adb.create();
		ad.show();
	}
}
