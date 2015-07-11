package it.project.main;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;

import retrofit.mime.TypedFile;
public interface ClientSvcApi {

	public static final String DATA_PARAMETER = "data";
	public static final String ID_PARAMETER = "id";
	public static final String VIDEO_SVC_PATH = "/video";
	public static final String VIDEO_DATA_PATH = VIDEO_SVC_PATH + "/{id}/data";

	/**
	 * This endpoint in the API returns a list of the videos that have
	 * been added to the server. The Video objects should be returned as
	 * JSON. 
	 * 
	 * To manually test this endpoint, run your server and open this URL in a browser:
	 * http://localhost:8080/video
	 * 
	 * @return
	 */
	
	
	
	@GET("/pag1")
	public String getVideoList();

	
	@GET("/locazione/getposti")
	public ArrayList<String> getAllLocation(/*@Query("stringa")String prova*/);
	
	@POST("/prova/passaggioparametri")
	public String reportPost(@Query("var1")String var1, @Query("var2")String var2,@Query("var3")String var3);
	
	@GET("/Login")
	public String getLogin(@Query("par1") String var, @Query("par2")String var2);
	
	@POST("/addp")
	public String addPlace(@Query("arr") ArrayList<String> var);//nuovo report
	
	
	@POST("/Registration")
	public String addRegistration(@Query("param")String nome, @Query ("param2")String pass );
	
	
	
	
	
	
}
