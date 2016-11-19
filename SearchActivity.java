package project.demo.pd1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

public class SearchActivity extends ActionBarActivity{
	
	String root_url,id;
	Button bSBName, bSBId, bSBCity, bSBCompany, bAdvancedSearch;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_search);
		
		bSBName = (Button) findViewById(R.id.buttonSearchByName);
		bSBId = (Button) findViewById(R.id.buttonSearchById);
		bSBCity = (Button) findViewById(R.id.buttonSearchByCity);
		bSBCompany = (Button) findViewById(R.id.buttonSearchByCompany);
		bAdvancedSearch = (Button) findViewById(R.id.buttonAdvancedSearch);
		
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		id = bundle.getString("ID");
		
		final Intent intent = new Intent(SearchActivity.this, NextSearchActivity.class);
		intent.putExtra("ROOT_URL", root_url);
		intent.putExtra("ID",id);
		
		bSBName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("SEARCH_TYPE","name");
				startActivity(intent);
			}
		});
		
		bSBId.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("SEARCH_TYPE","id");
				startActivity(intent);
			}
		});
		
		bSBCity.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("SEARCH_TYPE","city");
				startActivity(intent);
			}
		});
		
		bSBCompany.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("SEARCH_TYPE","company");
				startActivity(intent);
			}
		});
		
		bAdvancedSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intent.putExtra("SEARCH_TYPE","advancedSearch");
				startActivity(intent);
			}
		});
		
		
		
	}

}
