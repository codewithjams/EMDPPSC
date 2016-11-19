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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LoggedInHomeActivity extends ActionBarActivity {

	public static ProgressDialog pd;
	String root_url,id;
	Button bEduc, bRel, bCrimes, bBasic, bSearch, bNotif;
	TextView tvLoggedInAs, tvLoggedInAsNameOnly, tvPresenting;
	int quantity,i;
	
	//////////////LATER STAGE VARIABLES///////////////
	String loggedId, requestedId, requestedField, status, success, message, type;
	//////////////////////////////////////////////////
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		checkNotifications();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_loggedinhome);
	
		Bundle bundle = getIntent().getExtras();
		root_url = bundle.getString("ROOT_URL");
		id = bundle.getString("ID");
		
		tvLoggedInAs = (TextView) findViewById(R.id.tvLoggedInAs);
		tvLoggedInAsNameOnly = (TextView) findViewById(R.id.tvLoggedInAsNameOnly);
		tvPresenting = (TextView) findViewById(R.id.tvPresenting);
		bEduc = (Button) findViewById(R.id.buttonShowEduc);
		bRel = (Button) findViewById(R.id.buttonShowRelatives);
		bCrimes = (Button) findViewById(R.id.buttonShowCrimes);
		bBasic = (Button) findViewById(R.id.buttonShowBasicInfo);
		bSearch = (Button) findViewById(R.id.buttonSearch);
		bNotif = (Button) findViewById(R.id.buttonNotifications);		
		
		tvLoggedInAsNameOnly.setText("ID: "+id);
		
		basicInfo();
		
		
		
		bEduc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				tvPresenting.setText("Educational Details");
				
				pd = new ProgressDialog(LoggedInHomeActivity.this);
				pd.setTitle("Please wait");
				pd.setMessage("while we fetch your data :)");
				pd.setCancelable(false);
				pd.show();
				
				bBasic.setVisibility(View.VISIBLE);
				bEduc.setText("Refresh");
				bRel.setText("Relatives");
				bCrimes.setText("Crimes");
				
				RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(root_url)
				.build();
				TheAPI api = adapter.create(TheAPI.class);
				api.getEducData(id, new Callback<EducationData>() {

					@Override
					public void failure(RetrofitError e) {
						
						if(pd!=null)
							pd.dismiss();
						
						Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void success(EducationData answer, Response arg1) {
						if(pd!=null)
							pd.dismiss();
						
						if(answer.success.equals("0")){
							Toast.makeText(getApplicationContext(), answer.message, Toast.LENGTH_SHORT).show();
						}else{
							tvLoggedInAs.setText("");
							tvLoggedInAs.append("CurrentLevel : " + answer.currentlevel+"\n");
							tvLoggedInAs.append("Middle School Name : " + answer.middleschoolname+"\n");
							tvLoggedInAs.append("Middle School Percentage : " + answer.middleschoolpercentage+"\n");
							tvLoggedInAs.append("10th School Name : " + answer.tenthschoolname+"\n");
							tvLoggedInAs.append("10th School Percentage : " + answer.tenthschoolpercentage+"\n");
							tvLoggedInAs.append("12th School Name : " + answer.twelfthschoolname+"\n");
							tvLoggedInAs.append("12th School Percentage : " + answer.twelfthschoolpercentage+"\n");
							tvLoggedInAs.append("Graduating Stream : " + answer.graduatingstream+"\n");
							tvLoggedInAs.append("Graduating College : " + answer.graduatingcollege+"\n");
							tvLoggedInAs.append("Graduating Board : " + answer.graduatingboard+"\n");
							tvLoggedInAs.append("Graduating Percentage : " + answer.graduatingpercentage+"\n");
							tvLoggedInAs.append("Post Graduating Stream : " + answer.postgraduatingstream+"\n");
							tvLoggedInAs.append("Post Graduating College : " + answer.postgraduatingcollege+"\n");
							tvLoggedInAs.append("Post Graduating Board : " + answer.postgraduatingboard+"\n");
							tvLoggedInAs.append("Post Graduating Percentage : " + answer.postgraduatingpercentage+"\n");
							
						}
						
					}
				});
			}
		});
		
		bRel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				tvPresenting.setText("Relatives Details");
				
				pd = new ProgressDialog(LoggedInHomeActivity.this);
				pd.setTitle("Please wait");
				pd.setMessage("while we fetch your data :)");
				pd.setCancelable(false);
				pd.show();
				
				bBasic.setVisibility(View.VISIBLE);
				bRel.setText("Refresh");
				bCrimes.setText("Crimes");
				bEduc.setText("Education");
				
				RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(root_url)
				.build();
				TheAPI api = adapter.create(TheAPI.class);
				
				api.getRelativesData(id, new Callback<RelativesData>() {

					@Override
					public void failure(RetrofitError e) {
						if(pd!=null)
							pd.dismiss();
						
						Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void success(RelativesData yoAns, Response arg1) {
						if(pd!=null)
							pd.dismiss();
						if(yoAns.success.equals("0")){
							Toast.makeText(getApplicationContext(), yoAns.message, Toast.LENGTH_SHORT).show();
						}else{
							
							tvLoggedInAs.setText("");
							if(!(yoAns.mother.equals("")))
								tvLoggedInAs.append("Mother: "+yoAns.mother+"\n");
							if(!(yoAns.father.equals("")))
								tvLoggedInAs.append("Father: "+yoAns.father+"\n");
							if(!(yoAns.brother.equals("")))
								tvLoggedInAs.append("Brother: "+yoAns.brother+"\n");
							if(!(yoAns.sister.equals("")))
								tvLoggedInAs.append("Sister: "+yoAns.sister+"\n");
						}
						
						
					}
				});
			}
		});
		
		bCrimes.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				tvPresenting.setText("Criminal Records");
				
				pd = new ProgressDialog(LoggedInHomeActivity.this);
				pd.setTitle("Please wait");
				pd.setMessage("while we fetch your data :)");
				pd.setCancelable(false);
				pd.show();
				
				bBasic.setVisibility(View.VISIBLE);
				bCrimes.setText("Refresh");
				bRel.setText("Relatives");
				bEduc.setText("Education");
				
				RestAdapter adapter = new RestAdapter.Builder()
				.setEndpoint(root_url)
				.build();
				TheAPI api = adapter.create(TheAPI.class);
				api.getCriminalRecords(id, new Callback<CriminalRecords>() {

					@Override
					public void failure(RetrofitError e) {
						if(pd!=null)
							pd.dismiss();
						
						Toast.makeText(getApplicationContext(), "Failure:", Toast.LENGTH_SHORT).show();
						Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void success(CriminalRecords yoyoAns, Response arg1) {
						if(pd!=null)
							pd.dismiss();
						if(yoyoAns.success.equals("0")){
						//	Toast.makeText(getApplicationContext(), yoyoAns.message, Toast.LENGTH_SHORT).show();
							tvLoggedInAs.setText(yoyoAns.message);
						}else{
							tvLoggedInAs.setText("");
							tvLoggedInAs.append("Case ID : "+yoyoAns.caseid+"\n");
							tvLoggedInAs.append("Case Year : "+yoyoAns.caseyear+"\n");
							tvLoggedInAs.append("Crime : "+yoyoAns.crime+"\n");
							
						}
					}
				});
				
			}
		});
		
		bBasic.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				basicInfo();
				bRel.setText("Relatives");
				bEduc.setText("Education");
				bCrimes.setText("Crimes");
				bBasic.setText("Refresh");
			}
		});
		
		bSearch.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Intent intent = new Intent(LoggedInHomeActivity.this,SearchActivity.class);
				intent.putExtra("ROOT_URL", root_url);
				intent.putExtra("ID",id);
				startActivity(intent);
			}
		});
		
		bNotif.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoggedInHomeActivity.this,ShowNotificationsActivity1.class);
				intent.putExtra("ID", id);
				intent.putExtra("ROOT_URL", root_url);
				startActivity(intent);
			}
		});
		
	}
	
	public void basicInfo(){

		pd = new ProgressDialog(LoggedInHomeActivity.this);
		pd.setTitle("Please wait");
		pd.setMessage("while we fetch your data :)");
		pd.setCancelable(false);
		pd.show();
		
		tvPresenting.setText("Personnel Information");
		
		RestAdapter adapter = new RestAdapter.Builder()
									.setEndpoint(root_url)
									.build();
		TheAPI api = adapter.create(TheAPI.class);
		api.getLoggedInDetails(id, new Callback<CitizenDetails>() {
			
			@Override
			public void success(CitizenDetails response, Response arg1) {
				
				if(pd!=null)
					pd.dismiss();
				
				if(response.success.equals("0")){
					Toast.makeText(getApplicationContext(), response.message, Toast.LENGTH_SHORT).show();
				}else{
					//Toast.makeText(getApplicationContext(), response.message, Toast.LENGTH_SHORT).show();
					tvLoggedInAs.setText("");
					tvLoggedInAs.append("Name : " + response.name+"\n");
					tvLoggedInAs.append("Gender : " + response.gender+"\n");
					tvLoggedInAs.append("Level : " + response.level+"\n");
					tvLoggedInAs.append("Dob : " + response.dob+"\n");
					tvLoggedInAs.append("Mobile : " + response.mobile+"\n");
					tvLoggedInAs.append("Email : " + response.email+"\n");
					tvLoggedInAs.append("Address : " + response.address+"\n");
					tvLoggedInAs.append("Pancard : " + response.pancard+"\n");
					tvLoggedInAs.append("AadhaarCard : " + response.aadhaarcard+"\n");
					tvLoggedInAs.append("VoterId : " + response.voterid+"\n");
					tvLoggedInAs.append("DL : " + response.dl+"\n");
					tvLoggedInAs.append("Education : " + response.education+"\n");
					tvLoggedInAs.append("CriminalRecords : " + response.criminalrecords+"\n");
					tvLoggedInAs.append("Employment : " + response.employment+"\n");
					tvLoggedInAs.append("Relatives : " + response.relatives+"\n");
					tvLoggedInAs.append("PropertiesOwned : " + response.propertiesowned+"\n");
					tvLoggedInAs.append("Passport : " + response.passport+"\n");
					tvLoggedInAs.append("Signature : " + response.signature+"\n");
					tvLoggedInAs.append("BiometricInfo : " + response.biometricinfo+"\n");
					tvLoggedInAs.append("Password : " + response.password+"\n");
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
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), "You've been logged out", Toast.LENGTH_SHORT).show();
		finish();
	}

	public void checkNotifications(){
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		TheAPI api = adapter.create(TheAPI.class);
		api.checknotification(id, new Callback<NotificationsReplyFormat>() {

			@Override
			public void failure(RetrofitError e) {
				Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void success(NotificationsReplyFormat response, Response arg1) {
				//&& !(response.getLoggedId().equals(id) && response.getStatus().equals("RequestPending"))
				quantity = response.getNotifs().size();
				if(response.success.equals("1"))
				{
					for(i=0;i<quantity;i++)
					{
						if(id.equals(response.getNotifs().get(i).getLoggedId()))
							type = "requesting";
						else if(id.equals(response.getNotifs().get(i).getRequestedId()))
							type = "requested";
						
						if(type.equals("requesting")&&(response.getNotifs().get(i).getStatus().equals("Allowed")||response.getNotifs().get(i).getStatus().equals("Denied")))
						{
							bNotif.setVisibility(View.VISIBLE);
							break;
						}
						else if(type.equals("requested")&&(response.getNotifs().get(i).getStatus().equals("RequestPending")||
								response.getNotifs().get(i).getStatus().equals("Working")||response.getNotifs().get(i).getStatus().equals("Done")))
						{	
							bNotif.setVisibility(View.VISIBLE);
							break;
						}
						
					}
				}
				else{
					bNotif.setVisibility(View.INVISIBLE);
					Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_LONG).show();
				}
			}
		});
	}
	
}