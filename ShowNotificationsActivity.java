package project.demo.pd1;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNotificationsActivity extends ActionBarActivity{
	
	TextView tvHeader, tvMiddle;
	String loggedId, requestedId, requestedField, status, success, message, root_url, id, type;
	String query;
	String lId,rId,rF,s;
	int i, quantity;
	public static ProgressDialog pd;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shownotifications);
		
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		root_url = bundle.getString("ROOT_URL");
		
		tvHeader = (TextView) findViewById(R.id.tvLKJHG);
		tvMiddle = (TextView) findViewById(R.id.tvYoYo);
		
		tvHeader.setText("ID: "+id);
		tvMiddle.setText("");
		
		pd = new ProgressDialog(ShowNotificationsActivity.this);
		pd.setCancelable(false);
		pd.setMessage("Just a sec");
		
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		final TheAPI api = adapter.create(TheAPI.class);
		api.checknotification(id, new Callback<NotificationsReplyFormat>() {

			@Override
			public void failure(RetrofitError e) {
				Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void success(NotificationsReplyFormat response, Response arg1) {
				//&& !(response.getLoggedId().equals(id) && response.getStatus().equals("RequestPending"))
				quantity = response.notifs.size();
				if(response.success.equals("1"))
				{
					for(i=0;i<quantity;i++)
					{
						lId=response.getNotifs().get(i).getLoggedId();
						rId=response.getNotifs().get(i).getRequestedId();
						rF=response.getNotifs().get(i).getRequestedField();
						s=response.getNotifs().get(i).getStatus();
						
						if(id.equals(lId))
							type = "requesting";
						else if(id.equals(rId))
							type = "requested";
						
						if(type.equals("requested"))
						{	
							if(s.equals("RequestPending"))
							{
								tvMiddle.setText("You have an access request from "+lId+ " to access "+rF);
								final AlertDialog.Builder adb = new AlertDialog.Builder(ShowNotificationsActivity.this);
								adb.setMessage("Grant "+rF+" to "+lId+" ?");
								adb.setPositiveButton("Allow", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										query="update notifications set status='Allowed' where loggedId='"+lId+"' and requestedId='"+rId+"' and requestedField='"+rF+"'" ;
										RestAdapter adapter = new RestAdapter.Builder()
																	.setEndpoint(root_url)
																	.build();
										TheAPI api = adapter.create(TheAPI.class);
										api.runquery(query, new Callback<LoginReplyFormat>() {

											@Override
											public void failure(RetrofitError e) {
												Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
												Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
											}
											@Override
											public void success(LoginReplyFormat arg0,Response arg1) {
												Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
											}
										});
									}
								});
								adb.setNegativeButton("Deny", new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										
										query="update notifications set status='Denied' where loggedId='"+lId+"' and requestedId='"+rId+"' and requestedField='"+rF+"'" ;
										RestAdapter adapter = new RestAdapter.Builder()
																	.setEndpoint(root_url)
																	.build();
										TheAPI api = adapter.create(TheAPI.class);
										api.runquery(query, new Callback<LoginReplyFormat>() {

											@Override
											public void failure(RetrofitError e) {
												Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
												Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
											}
											@Override
											public void success(LoginReplyFormat arg0,Response arg1) {
												Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
											}
										});
									}
								});
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										AlertDialog ad = adb.create();
										ad.show();
									}
								}, 5000);
								
							}
							else if(s.equals("Working"))
							{
								
							}
							else if(s.equals("Done"))
							{
								tvMiddle.setText("Its time to revoke "+rF+" from "+lId);
								query="delete from notifications where status='Done'";
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										
										api.runquery(query, new Callback<LoginReplyFormat>() {

											@Override
											public void failure(RetrofitError e) {
												Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
												Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
											}

											@Override
											public void success(LoginReplyFormat arg0,
													Response arg1) {
												Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();										
											}
										});
		
										
									}
								}, 3000);
							
							}
						}
						else if(type.equals("requesting"))
						{
							if(s.equals("Allowed"))
							{
								tvMiddle.setText("You have been given access to "+rF+" from "+rId);
								RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
								final TheAPI api = adapter.create(TheAPI.class);
								api.provideaccess(lId, rId, rF, new Callback<ProvideAccessReplyFormat>() {
									@Override
									public void failure(RetrofitError e) {
										Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
										Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
									}
									@Override
									public void success(ProvideAccessReplyFormat response,Response arg1) {
										tvMiddle.append("\nRequested Field: "+rF+" \nValue: "+response.getData());
									}
								});
								query="update notifications set status='Done' where loggedId='"+lId+"' and requestedId='"+rId+"' and status='Allowed'";
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										
										api.runquery(query, new Callback<LoginReplyFormat>() {

											@Override
											public void failure(RetrofitError e) {
												Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
												Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
											}

											@Override
											public void success(LoginReplyFormat arg0,
													Response arg1) {
												Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();										
											}
										});
		
										
									}
								}, 5000);
																
							}
							else if(s.equals("Denied"))
							{
								tvMiddle.setText("You have been denied from getting access to "+rF+" from "+rId);
								query="update notifications set status='Done' where loggedId='"+lId+"' and requestedId='"+rId+"' and status='Denied'";
								
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										
										api.runquery(query, new Callback<LoginReplyFormat>() {

											@Override
											public void failure(RetrofitError e) {
												Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
												Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
											}

											@Override
											public void success(LoginReplyFormat arg0,
													Response arg1) {
												Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();										
											}
										});
		
										
									}
								}, 5000);
							}

						}
												
					}
				}
				else
					Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_LONG).show();
			}
		});
		
	}

}
