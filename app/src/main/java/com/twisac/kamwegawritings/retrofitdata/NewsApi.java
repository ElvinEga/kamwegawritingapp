package com.twisac.kamwegawritings.retrofitdata;

import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.twisac.kamwegawritings.jsonpojo.comments.Comment;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Elvin Ega on 10/23/2015.
 */
public interface NewsApi {

    @GET("/wp-json/wp/v2/posts")
    public void getNews(@Query("per_page") int per_page,@Query("page") int page,Callback<List<Posts>> response);
    @GET("/wp-json/wp/v2/posts?filter[cat]=110")
    public void getHeader(Callback<List<Posts>> response);
    @GET("/wp-json/wp/v2/posts")
    public void getPost(@Query("filter[name]") String postUrl,Callback<List<Posts>> response);

    @GET("/wp-json/wp/v2/comments")
    public void getComments(@Query("post") int post_id,Callback<List<Comment>> response);

}
