package project.demo.pd1;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface TheAPI {

	@FormUrlEncoded
	@POST("/smartcity/checklogin.php")
	public void login(
			@Field("id") String id, 
			@Field("password") String password, 
			Callback<LoginReplyFormat> response);
	
	
	@FormUrlEncoded
	@POST("/smartcity/showloggedindata.php")
	public void getLoggedInDetails(
			@Field("id") String id, 
			Callback<CitizenDetails> response);
	
	@FormUrlEncoded
	@POST("/smartcity/showeducdata.php")
	public void getEducData(
			@Field("id") String id, 
			Callback<EducationData> response);
	
	@FormUrlEncoded
	@POST("/smartcity/showrelativesdata.php")
	public void getRelativesData(
			@Field("id") String id, 
			Callback<RelativesData> response);
	
	@FormUrlEncoded
	@POST("/smartcity/showcriminalrecords.php")
	public void getCriminalRecords(
			@Field("id") String id, 
			Callback<CriminalRecords> response);
	
	@FormUrlEncoded
	@POST("/smartcity/search.php")
	public void search(
			@Field("type") String type,
			@Field("keyword") String keyword,
			Callback<SearchResultFormat> response);
	
	@FormUrlEncoded
	@POST("/smartcity/checkAccess.php")
	public void checkAccess(
			@Field("loggedId") String loggedId,
			@Field("requestedId") String requestedId,
			@Field("requestedField") String requestedField,
			Callback<AccessCheckReplyFormat> response);

	@FormUrlEncoded
	@POST("/smartcity/presentHigherLevelPeople.php")
	public void presentHigherLevelPeople(
			@Field("loggedId") String loggedId,
			Callback<HigherPeopleSearchResultFormat> response);

	@FormUrlEncoded
	@POST("/smartcity/addnotifications.php")
	public void addnotification(
			@Field("loggedId") String loggedId,
			@Field("requestedId") String requestedId,
			@Field("requestedField") String requestedField,
			@Field("targetId") String targetId,
			@Field("status") String status,
			Callback<LoginReplyFormat> response);
	
	@FormUrlEncoded
	@POST("/smartcity/checkNotifications.php")
	public void checknotification(
			@Field("loggedId") String loggedId,
			Callback<NotificationsReplyFormat> response);

	@FormUrlEncoded
	@POST("/smartcity/runqueries.php")
	public void runquery(
			@Field("query") String query,
			Callback<LoginReplyFormat> response);
	@FormUrlEncoded
	@POST("/smartcity/provideaccess.php")
	public void provideaccess(
			@Field("loggedId") String loggedId,
			@Field("requestedId") String requestedId,
			@Field("requestedField") String requestedField,
			Callback<ProvideAccessReplyFormat> response);

}
