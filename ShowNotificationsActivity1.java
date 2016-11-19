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
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowNotificationsActivity1 extends ActionBarActivity{
	
	TextView tvHeader;
	String loggedId, requestedId, requestedField, status, success, message, root_url, id, type;
	String query;
	String lId,rId,rF,s;
	ListView lv;
	
	int i,j=0, quantity;
	public static ProgressDialog pd;
	boolean okToGo = false;
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		j=0;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_shownotifications1);
		
		Bundle bundle = getIntent().getExtras();
		id = bundle.getString("ID");
		root_url = bundle.getString("ROOT_URL");
		
		tvHeader = (TextView) findViewById(R.id.tvLKJHG1);
		lv = (ListView) findViewById(R.id.lvNotifs);
		
		tvHeader.setText("ID: "+id);

		pd = new ProgressDialog(ShowNotificationsActivity1.this);
		pd.setCancelable(false);
		pd.setMessage("Just a sec");
		pd.show();
		RestAdapter adapter = new RestAdapter.Builder()
								.setEndpoint(root_url)
								.build();
		final TheAPI api = adapter.create(TheAPI.class);
		api.checknotification(id, new Callback<NotificationsReplyFormat>() {

			@Override
			public void failure(RetrofitError e) {
				
				if(pd!=null)
					pd.dismiss();
				Toast.makeText(getApplicationContext(), "Failure", Toast.LENGTH_SHORT).show();
				Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
				
			}

			@Override
			public void success(NotificationsReplyFormat response, Response arg1) {
				//&& !(response.getLoggedId().equals(id) && response.getStatus().equals("RequestPending"))
				
				if(pd!=null)
					pd.dismiss();
				
				quantity = response.notifs.size();
				
				final String[] lIds = new String[quantity];
				final String[] rIds = new String[quantity];
				final String[] rFs = new String[quantity];
				final String[] Ss = new String[quantity];
				final String[] notifs = new String[quantity];
				
				for(i=0;i<quantity;i++)
				{
					lIds[i] = new String();
					rIds[i] = new String();
					rFs[i] = new String();
					Ss[i] = new String();
					notifs[i] = new String();
				}
				
			//	Toast.makeText(getApplicationContext(),"# of real notifs: "+quantity, Toast.LENGTH_SHORT).show();
				if(response.success.equals("1"))
				{
					
					for(i=0;i<quantity;i++)
					{
						lId = response.getNotifs().get(i).getLoggedId();
						rId=response.getNotifs().get(i).getRequestedId();
						rF=response.getNotifs().get(i).getRequestedField();
						s=response.getNotifs().get(i).getStatus();
						
						if(id.equals(lId))
							type = "requesting";
						else if(id.equals(rId))
							type = "requested";
						
						if(type.equals("requesting")){
							if(s.equals("Allowed")||s.equals("Denied")){
								
								lIds[j] = lId;
								rIds[j] = rId;
								rFs[j] = rF;
								Ss[j] = s;
								
								//Toast.makeText(getApplicationContext(),lIds[j], Toast.LENGTH_SHORT).show();
								
								if(s.equals("Denied"))
								{	
									notifs[j] = "Sorry! Your request for "+rFs[j]+" from "+rIds[j]+" has been denied.";
									//Toast.makeText(getApplicationContext(),notifs[j], Toast.LENGTH_LONG).show();
									j++;
								}
								else		//allowed
								{
									notifs[j] = "Congrats! Your request for "+rFs[j]+" from "+rIds[j]+" has been accepted.";
									//provide access and run query
					//				Toast.makeText(getApplicationContext(),notifs[j], Toast.LENGTH_LONG).show();
									j++;
								}
								
								
							}
						}
						else if(type.equals("requested")){
							if(s.equals("RequestPending")||s.equals("Done")){
								lIds[j] = lId;
								rIds[j] = rId;
								rFs[j] = rF;
								Ss[j] = s;
								
								if(s.equals("RequestPending"))
								{
									notifs[j] = "You've a request for "+rFs[j]+" from "+lIds[j]+".";
					//				Toast.makeText(getApplicationContext(),notifs[j], Toast.LENGTH_LONG).show();
								}
								else 		//done
								{
									notifs[j] = "ID: "+lIds[j]+" is done with data "+rFs[j]+" that you granted.";
						//			Toast.makeText(getApplicationContext(),notifs[j], Toast.LENGTH_LONG).show();
						//			Toast.makeText(getApplicationContext(),"Revoking access...", Toast.LENGTH_LONG).show();
								}
								j++;
							}
						}				//if else-if ends here
						
						
					}	//for loop ends here
					
					ArrayAdapter adapter = new ArrayAdapter<String>(ShowNotificationsActivity1.this,R.layout.listview_item,notifs);
					lv.setAdapter(adapter);
					
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> parent,
								View view, final int pos, long id) {
							
					//		Toast.makeText(getApplicationContext(), notifs[pos], Toast.LENGTH_LONG).show();
							if(Ss[pos].equals("Allowed"))
							{
								pd.show();
								RestAdapter adapter = new RestAdapter.Builder()
														.setEndpoint(root_url)
														.build();
								TheAPI api = adapter.create(TheAPI.class);
								api.provideaccess(lIds[pos], rIds[pos], rFs[pos], new Callback<ProvideAccessReplyFormat>() {

									@Override
									public void failure(RetrofitError arg0) {
										// TODO Auto-generated method stub
										if(pd!=null)
											pd.dismiss();
										Toast.makeText(getApplicationContext(), "Got Screwed up!", Toast.LENGTH_LONG).show();
									}

									@Override
									public void success(ProvideAccessReplyFormat resp,Response arg1) {

										if(pd!=null)
											pd.dismiss();
										
										if(resp.success.equals("1")){
											AlertDialog.Builder adb = new AlertDialog.Builder(ShowNotificationsActivity1.this);
											adb.setMessage("Your requested data:\n"+rFs[pos]+": "+resp.getData());
											adb.setPositiveButton("OK, got it!", new OnClickListener() {
												@Override
												public void onClick(DialogInterface dialog, int which) {
													Toast.makeText(getApplicationContext(), "Great", Toast.LENGTH_LONG).show();
													setDone(lIds[pos], rIds[pos], rFs[pos]);
												}
											});
											AlertDialog ad = adb.create();
											ad.show();
										}
										
									}
								} );
							}
							else if(Ss[pos].equals("Denied"))
							{
								AlertDialog.Builder adb = new AlertDialog.Builder(ShowNotificationsActivity1.this);
								adb.setMessage("Sorry, Your request for "+rFs[pos]+" has been denied! ");
								adb.setPositiveButton("I understand.", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Toast.makeText(getApplicationContext(), "Great", Toast.LENGTH_LONG).show();
										String query="delete from notifications where status='Denied' and loggedId='"+lIds[pos]+"' and requestedId='"+rIds[pos]+"' and requestedField = '"+ rFs[pos] +"'";
										deleteNotif(lIds[pos], rIds[pos], rFs[pos], query);
									}
								});
								AlertDialog ad = adb.create();
								ad.show();
							}
							else if(Ss[pos].equals("Done"))
							{
								String query="delete from notifications where status='Done' and loggedId='"+lIds[pos]+"' and requestedId='"+rIds[pos]+"' and requestedField = '"+ rFs[pos] +"'";
								deleteNotif(lIds[pos], rIds[pos], rFs[pos], query);
							}
							else if(Ss[pos].equals("RequestPending"))
							{
								AlertDialog.Builder adb = new AlertDialog.Builder(ShowNotificationsActivity1.this);
								adb.setMessage("Allow "+lIds[pos]+" to access "+rFs[pos]+"?");
								adb.setPositiveButton("Allow", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										allowAccess(lIds[pos], rIds[pos], rFs[pos]);
										
									}
								});
								adb.setNegativeButton("Deny", new OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										denyAccess(lIds[pos], rIds[pos], rFs[pos]);
									}
								});
								
								AlertDialog ad = adb.create();
								ad.show();
							}
						}
					});
					
				}
				else
					Toast.makeText(getApplicationContext(), "You don't have any notifications", Toast.LENGTH_LONG).show();
			}
		});
		
	}
	
	public void allowAccess(String loggedId, String requestedId, String requestedField){
		
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(root_url)
		.build();
		TheAPI api = adapter.create(TheAPI.class);
		String query="update notifications set status='Allowed' where loggedId='"+loggedId+"' and requestedId='"+requestedId+"' and requestedField = '"+ requestedField +"' and status = 'RequestPending'";
		api.runquery(query, new Callback<LoginReplyFormat>() {
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(getApplicationContext(), "Got Screwed up!", Toast.LENGTH_LONG).show();
				
			}
			@Override
			public void success(LoginReplyFormat arg0, Response arg1) {
				
					Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_LONG).show();	
			}
		});
	}
	
	public void denyAccess(String loggedId, String requestedId, String requestedField){
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(root_url)
		.build();
		TheAPI api = adapter.create(TheAPI.class);
		String query="update notifications set status='Denied' where loggedId='"+loggedId+"' and requestedId='"+requestedId+"' and requestedField = '"+ requestedField +"' and status = 'RequestPending'";
		api.runquery(query, new Callback<LoginReplyFormat>() {
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(getApplicationContext(), "Got Screwed up!", Toast.LENGTH_LONG).show();
			}
			@Override
			public void success(LoginReplyFormat arg0, Response arg1) {
				Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void deleteNotif(String loggedId, String requestedId, String requestedField, String query)
	{
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(root_url)
		.build();
		TheAPI api = adapter.create(TheAPI.class);
		
		//query="update notifications set status='Done' where loggedId='"+lId+"' and requestedId='"+rId+"' and status='Allowed'";
		api.runquery(query, new Callback<LoginReplyFormat>() {

			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(getApplicationContext(), "Got Screwed up!", Toast.LENGTH_LONG).show();
			}

			@Override
			public void success(LoginReplyFormat respo, Response arg1) {
				
				Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_LONG).show();
			}
		});
	}

	public void setDone(String loggedId, String requestedId, String requestedField){
		
		
		RestAdapter adapter = new RestAdapter.Builder()
		.setEndpoint(root_url)
		.build();
		TheAPI api = adapter.create(TheAPI.class);
		String query="update notifications set status='Done' where loggedId='"+loggedId+"' and requestedId='"+requestedId+"' and requestedField = '"+ requestedField +"' and status='Allowed'";
		//query="update notifications set status='Done' where loggedId='"+lId+"' and requestedId='"+rId+"' and status='Allowed'";
		api.runquery(query, new Callback<LoginReplyFormat>() {

			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(getApplicationContext(), "Got Screwed up!", Toast.LENGTH_LONG).show();
			}

			@Override
			public void success(LoginReplyFormat respo, Response arg1) {
				
				Toast.makeText(getApplicationContext(), "yo", Toast.LENGTH_LONG).show();
			}
		});
	}
}
