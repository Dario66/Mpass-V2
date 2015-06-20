package it.project.main;
import java.util.ArrayList;
import java.util.Collection;


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
	
	@GET("/prova/passaggioparametri")
	public String gets(@Query("stringa")String prova);
	
	@GET("/Login")
	public String getLogin(/*@Query("para") String id*/);
	
}
