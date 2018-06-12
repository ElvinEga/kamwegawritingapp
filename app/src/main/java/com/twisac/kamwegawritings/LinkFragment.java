package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 10/22/2015.
 */

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.twisac.kamwegawritings.adapter.NewsRealAdapter;
import com.twisac.kamwegawritings.egaview.RecyclerItemClickListener;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.twisac.kamwegawritings.retrofitdata.NewsApi;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;

//import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;


public class LinkFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";
    private static final String POST_FEATURED = "featured";
    private static final String POST_CONTENT = "content";
    private static final String POST_TITLE = "title";
    private static final String POST_DATE = "date";
    private static final String POST_EXCERPT = "excerpt";
    private static final String POST_LINK = "link";
    private static long SIZE_OF_CACHE =10 * 1024 * 1024;// 10MB
    String pointUrl;

    TextView textView;
    ImageView imageLoading;

    OkHttpClient okHttpClient;
    RestAdapter restadapter;

    private ProgressDialog pDialog;
    NewsRealAdapter adapter;

    RecyclerView rv;

    FloatingActionButton fab;
    Cache cache;

    NewsApi newsApi;

    private SwipeRefreshLayout swipeRefreshLayout;
    List<Posts> postsList;

      ConnectivityManager cn;
    NetworkInfo nf;
    //Retrofit retrofit;
   // com.squareup.okhttp.Response response2;


    public LinkFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intentUrl = getActivity().getIntent();

//intent.get
        Uri uri    =intentUrl.getData();
        pointUrl = uri.toString();
        pointUrl= pointUrl.replaceAll("http://kamwegawritings.com/", "");
        pointUrl=  pointUrl.replace("http", "");
        pointUrl= pointUrl.replace(":","");
        pointUrl= pointUrl.replace("www.", "");
      //  pointUrl= pointUrl.replaceAll(".", "");
        pointUrl = pointUrl.replace("kamwegawritings.", "");
        pointUrl= pointUrl.replace("com","");
      //  pointUrl= pointUrl.replaceAll(".","");
    //    pointUrl= pointUrl.replaceAll(":","");
        pointUrl=  pointUrl.replaceAll("/", "");

       // pointUrl = getArguments().getString("postUrl");

        try{
            cache = new Cache(new File(getActivity().getCacheDir(),"http"),SIZE_OF_CACHE);
        }catch (IOException e){

        }

         okHttpClient= new OkHttpClient();
        okHttpClient.setCache(cache);
        okHttpClient.setConnectTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(30, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);


       okHttpClient.networkInterceptors().add(mCacheInterceptor);
      //  executor = Executors.newCachedThreadPool();

         restadapter = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
               // .setExecutors(executor, executor)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();



        View rootView = inflater.inflate(R.layout.fragment_link, container, false);
         imageLoading =(ImageView)rootView.findViewById(R.id.imageLoading);
         textView = (TextView)rootView.findViewById(R.id.textLoading);

       fab =(FloatingActionButton)getActivity().findViewById(R.id.fabup);

       rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        //   AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        //   alphaAdapter.setDuration(1000);

        rv.setAdapter(adapter);



       cn = (ConnectivityManager)getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
      //  nf = cn.getActiveNetworkInfo();
        swipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.red, R.color.green, R.color.blue);

fab.hide();

swipeRefreshLayout.setOnRefreshListener(this);


        //Add Cache-Control Intercepter
       // okHttpClient.

     swipeRefreshLayout.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     swipeRefreshLayout.setRefreshing(true);

                                     listUpdate();

                                 }
                             }
     );

        return rootView;
    }

    @Override
    public void onRefresh() {

        listUpdate();

    }

    public void listUpdate() {
        Snackbar.make(getView(),
                "The url " + pointUrl,
                Snackbar.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(true);
        if(adapter==null) {
            textView.setVisibility(View.VISIBLE);
            imageLoading.setVisibility(View.VISIBLE);
            imageLoading.setImageResource(R.drawable.kwback);
            textView.setText("Loading...");
        }
        if(adapter!=null){
           // adapter.clear();
           // adapter.notifyDataSetChanged();
            textView.setVisibility(View.GONE);
            imageLoading.setVisibility(View.GONE);
        }


        newsApi = restadapter.create(NewsApi.class);
        newsApi.getPost(pointUrl, new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> posts, Response response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                postsList = posts;

                adapter = new NewsRealAdapter(getActivity(), postsList, rv);


                rv.setAdapter(adapter);
                if (adapter != null) {
                    // adapter.clear();
                    // adapter.notifyDataSetChanged();
                    textView.setVisibility(View.GONE);
                    imageLoading.setVisibility(View.GONE);
                }
                ;

                rv.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String postContent = postsList.get(position).getContent().getRendered();
                                String postTitle = postsList.get(position).getTitle().getRendered();
                                String featuredImage = postsList.get(position).getBetterFeaturedImage().getSourceUrl();
                                String postDate = postsList.get(position).getDate();
                                String postExcerpt = postsList.get(position).getExcerpt().getRendered();
                                String postLink = postsList.get(position).getLink();

                                Intent intent = new Intent(getActivity(), StoryActivity.class);

                                intent.putExtra(POST_TITLE, postTitle);

                                intent.putExtra(POST_FEATURED, featuredImage);

                                intent.putExtra(POST_DATE, postDate);

                                intent.putExtra(POST_CONTENT, postContent);

                                intent.putExtra(POST_EXCERPT, postExcerpt);

                                intent.putExtra(POST_LINK, postLink);
                                startActivity(intent);

                            }
                        })
                );

            }

            @Override
            public void failure(RetrofitError error) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                //  Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
                if (adapter == null) {
                    textView.setVisibility(View.VISIBLE);
                    textView.setText("Opps!Please Check Your Internet Connection " );
                    imageLoading.setVisibility(View.VISIBLE);
                    imageLoading.setImageResource(R.drawable.ic_error_red);
                }
                Snackbar.make(getView(),
                        "Failed.Check Your internet Connection"+ pointUrl,
                        Snackbar.LENGTH_SHORT).show();

            }
        });
    }


private  Interceptor mCacheInterceptor = new Interceptor() {
    @Override
    public com.squareup.okhttp.Response intercept(Chain chain) throws IOException {

         nf = cn.getActiveNetworkInfo();
        Request request = chain.request();

        //Add Cache control Only for GET methods
        if(request.method().equals("GET")){
            if(nf != null && nf.isConnected() == true){
                //1 day
                request.newBuilder()
                        .header("Cache-Control","only-if-cached")
                .build();
            }else {
                //4 week stale
                request.newBuilder()
                        .header("Cache-Control", "public, max-stale=2419200")
                        .build();
            }
        }
        com.squareup.okhttp.Response response = chain.proceed(request);

        return response.newBuilder()
                .header("Cache-Control", "public, max-stale=86400")//1 day
                .build();
    }
};
}
