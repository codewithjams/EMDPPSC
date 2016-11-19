package project.demo.pd1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class OpenProfile extends ActionBarActivity{
	
	String loggedId, requestedId, requestedName, requestedGender, root_url;
	TextView tv1, tv2, tv3; 
	Button btnMore;
	ListView lv;
	String[] attribs = {"level","dob","mobile","email","address","pancard"
			,"aadhaarcard","voterid","dl","education","criminalrecords","employment","relatives","propertiesowned"
			,"passport","signature","biometricinfo","password"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_openprofile);
		
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		loggedId = bundle.getString("LOGGED_ID");
		requestedId = bundle.getString("REQUESTED_ID");
		requestedName = bundle.getString("REQUESTED_NAME");
		requestedGender = bundle.getString("REQUESTED_GENDER");
		
		tv1 = (TextView) findViewById(R.id.tvQWERTY);
		tv2 = (TextView) findViewById(R.id.tvShowNameAndId);
		tv3 = (TextView) findViewById(R.id.tvSelectWhatYouWant);
		btnMore = (Button) findViewById(R.id.buttonWantMoreDetails);
		lv = (ListView) findViewById(R.id.theListViewAttributes);
		
		tv1.setText("ID: "+loggedId);
		tv2.setText("Name: "+requestedName+"\nID: "+requestedId+"\nGender: "+requestedGender);
		
		final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.listview_item,attribs);
		
		
		btnMore.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				tv3.setVisibility(View.VISIBLE);
				lv.setAdapter(adapter);
			}
		});
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long idl) {
				
				Intent intent = new Intent(OpenProfile.this, AccessActivity.class);
				intent.putExtra("ROOT_URL",root_url);
				intent.putExtra("LOGGED_ID",loggedId);
				intent.putExtra("REQUESTED_ID",requestedId);
				intent.putExtra("REQUESTED_FIELD", attribs[position]);
				startActivity(intent);
				
			}
		});
		
	}

}
