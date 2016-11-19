package project.demo.pd1;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ProgressDialog;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class NextSearchActivity extends ActionBarActivity{
	
	String root_url,id,searchType,keyword;
	
	TextView tvHeader;
	Button b;
	EditText et;
	ListView lv;
	public static ProgressDialog pd;
	int quantity=0,i=0;
	SearchResultFormat response1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_nextsearch);
		
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		id = bundle.getString("ID");
		searchType = bundle.getString("SEARCH_TYPE");

		tvHeader = (TextView) findViewById(R.id.tvEnterDetails);
		
		b = (Button) findViewById(R.id.buttonSearchIt);
		et = (EditText) findViewById(R.id.etTypeKeyword);
		lv = (ListView) findViewById(R.id.theListView);
	
//		for(i=0;i<20;i++)
//			strArray[i] = new String();
		i=0;
		
		if(searchType.equals("name"))
			tvHeader.setText("Please Enter the Name:");
		if(searchType.equals("id"))
			tvHeader.setText("Please Enter the ID:");
		if(searchType.equals("city"))
			tvHeader.setText("Please Enter the City:");
		if(searchType.equals("company"))
			tvHeader.setText("Please Enter the Company:");
		if(searchType.equals("advancedSearch"))
			tvHeader.setText("Please Enter the Keyword:");
		if(!(searchType.equals("advancedSearch")))
			et.setHint(searchType+" please...");
		else
			et.setHint("Keyword please...");
		
		
		
		
		b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				pd = new ProgressDialog(NextSearchActivity.this);
				pd.setMessage("Searching...");
				pd.setCancelable(false);
				pd.show();
					
				
				keyword = et.getText().toString();
				
				RestAdapter adapter = new RestAdapter.Builder().setEndpoint(root_url).build();
				TheAPI api = adapter.create(TheAPI.class);
				api.search(searchType, keyword, new Callback<SearchResultFormat>() {
					
					@Override
					public void success(SearchResultFormat response, Response arg1) {
						if(pd!=null)
							pd.dismiss();
						response1=response;
						Toast.makeText(getApplicationContext(), response.message, Toast.LENGTH_SHORT).show();
						quantity = response.posts.size();
						i=0;
						String strArray[] = new String[quantity];
						for(i=0;i<quantity;i++)
							strArray[i] = new String();
						i=0;
						while(i<quantity){
							strArray[i] = "Name: "+response.getPost().get(i).getName() + "\nID: "+response.getPost().get(i).getId() + "\nAddress: "+response.getPost().get(i).getAddress();
							i++;
						}
						ArrayAdapter adapter = new ArrayAdapter<String>(NextSearchActivity.this, R.layout.listview_item,strArray);
						lv.setAdapter(adapter);
						
						lv.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long idl) {
								
								Intent intent = new Intent(NextSearchActivity.this,OpenProfile.class);
								intent.putExtra("ROOT_URL",root_url);
								intent.putExtra("LOGGED_ID",id);
								intent.putExtra("REQUESTED_ID",response1.getPost().get(position).getId());
								intent.putExtra("REQUESTED_NAME",response1.getPost().get(position).getName());
								intent.putExtra("REQUESTED_GENDER",response1.getPost().get(position).getGender());
								startActivity(intent);
							}
						});
						
					}
					
					@Override
					public void failure(RetrofitError e) {
						if(pd!=null)
							pd.dismiss();
						Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_LONG).show();
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		
			}
}
