package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 10/22/2015.
 */
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.twisac.kamwegawritings.adapter.NewsRealAdapter;
import com.twisac.kamwegawritings.components.AlertPopup;
import com.twisac.kamwegawritings.egaview.RecyclerItemClickListener;
import com.twisac.kamwegawritings.interfaces.RecycConnector;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.twisac.kamwegawritings.jsonpojo.Posts2;
import com.twisac.kamwegawritings.retrofitdata.NewsApi;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

//import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;
import retrofit.Callback;

import retrofit.RestAdapter;

import retrofit.RetrofitError;
import retrofit.client.OkClient;
import retrofit.client.Response;


public class OneFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/";

    private static final String POST_FEATURED = "featured";
    private static final String POST_CONTENT = "content";
    private   String POST_CATEGORY = "category";
    private static final String POST_TITLE = "title";
    private static final String POST_DATE = "date";
    private static final String POST_EXCERPT = "excerpt";
    private static final String POST_LINK = "link";
    private static final String POST_AUTHOR = "author";
    private String POST_ID = "id";
    private static long SIZE_OF_CACHE =10 * 1024 * 1024;// 10MB
    int page=1;
    final int per_page=5;

    TextView textView;
    ImageView imageLoading;
    Executor executor;
    OkHttpClient okHttpClient;
    RestAdapter restadapter;
    //   RestAdapter restadapter2;
    private ProgressDialog pDialog;
    // private TypefaceCollection mTitilliumTypeface;
    NewsRealAdapter adapter;
    //  HeaderAdapter adapter2;
    RecyclerView rv;
    RecyclerView rv2;
    FloatingActionButton fab;
    Cache cache;
    int ty=0;
    NewsApi newsApi;
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Posts> postsList;
    List<Posts2> postsList2;
    ConnectivityManager cn;
    NetworkInfo nf;
    boolean success = true;
    int anim = 1;
    Button btn_try;
    //Retrofit retrofit;
    // com.squareup.okhttp.Response response2;


    public OneFragment() {
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
        success = true;


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

    /*    restadapter2 = new RestAdapter.Builder()
                .setEndpoint(ENDPOINT)
                        // .setExecutors(executor, executor)
                .setClient(new OkClient(okHttpClient))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build(); */

        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        imageLoading =(ImageView)rootView.findViewById(R.id.imageLoading);
        textView = (TextView)rootView.findViewById(R.id.textLoading);
        btn_try = (Button) rootView.findViewById(R.id.btn_try);
        btn_try.setVisibility(View.GONE);

        fab =(FloatingActionButton)getActivity().findViewById(R.id.fabup);
        fab.hide();
        callAsynchronousTask();
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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rv.smoothScrollToPosition(1);
                fab.hide();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);


        //Add Cache-Control Intercepter
        // okHttpClient.

        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        listUpdate();
                                        //     headerUpdate();
                                    }
                                }
        );
        //  listUpdate();

   /*     rv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                new Timer().schedule(
                        new TimerTask() {
                            @Override
                            public void run() {
                                int items = adapter2.getItemCount() - 1;

                                if (ty < adapter2.getItemCount()) {
                                    ty++;
                                    rv2.smoothScrollToPosition(ty);
                                    rv2.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                        @Override
                                        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                            super.onScrollStateChanged(recyclerView, newState);
                                        }
                                    });
                                    if (ty == adapter2.getItemCount() - 1) {
                                        ty = 0;
                                    }
                                }
                            }
                        },
                        2000
                );
            }
        });
*/

   btn_try.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View v) {
           page=1;
           listUpdate();
       }
   });
        return rootView;
    }

    @Override
    public void onRefresh() {
        page=1;
        listUpdate();
        // headerUpdate();
    }
    private void loadMoreData(){
        postsList.add(null);
        adapter.notifyItemInserted(postsList.size() - 1);

        newsApi = restadapter.create(NewsApi.class);
        newsApi.getNews(per_page,page=page+1,new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> posts, Response response) {
                // stopping swipe refresh
                adapter.removeItem(null);


                postsList.addAll(posts);
                adapter.notifyItemInserted(postsList.size());
                adapter.setLoaded();
                adapter.notifyDataSetChanged();
                fab.show();
                success = true;
                btn_try.setVisibility(View.GONE);


            }

            @Override
            public void failure(RetrofitError error) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                success= false;
                btn_try.setVisibility(View.VISIBLE);

                Snackbar.make(getView(),
                        "Failed.Check Your internet Connection",
                        Snackbar.LENGTH_SHORT).show();
                new AlertPopup().alertConnectError(getActivity());

            }
        });
    }
    public void listUpdate() {
        swipeRefreshLayout.setRefreshing(true);
        if(adapter==null) {
            textView.setVisibility(View.VISIBLE);
            imageLoading.setVisibility(View.VISIBLE);
         //   imageLoading.setImageResource(R.drawable.ic_happy);
            textView.setText("Loading...");
            success = true;
            btn_try.setVisibility(View.GONE);
        }
        if(adapter!=null){
            // adapter.clear();
            // adapter.notifyDataSetChanged();
            textView.setVisibility(View.GONE);
            imageLoading.setVisibility(View.GONE);
        }


        newsApi = restadapter.create(NewsApi.class);
        newsApi.getNews(per_page,page,new Callback<List<Posts>>() {
            @Override
            public void success(List<Posts> posts, Response response) {
                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
                postsList = posts;

                adapter = new NewsRealAdapter(getActivity(), postsList,rv);


                rv.setAdapter(adapter);
                if (adapter != null) {
                    // adapter.clear();
                    // adapter.notifyDataSetChanged();
                    textView.setVisibility(View.GONE);
                    imageLoading.setVisibility(View.GONE);
                }
                adapter.setOnLoadMoreListener(new RecycConnector.OnLoadMoreListener() {
                    @Override
                    public void onLoadMore() {
                        loadMoreData();
                    }
                });

                rv.addOnItemTouchListener(
                        new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                String postContent = postsList.get(position).getContent().getRendered();
                                String postCategory = postsList.get(position).getAuthormeta().getCategories();
                                String postTitle = postsList.get(position).getTitle().getRendered();
                                String featuredImage = postsList.get(position).getBetterFeaturedImage();
                                String postDate = postsList.get(position).getDate();
                                String postExcerpt = postsList.get(position).getExcerpt().getRendered();
                                String postLink = postsList.get(position).getLink();
                                String postAuthor = postsList.get(position).getAuthormeta().getName();
                                int postID = postsList.get(position).getId();

                                Intent intent = new Intent(getActivity(), StoryActivity.class);

                                intent.putExtra(POST_ID, postID);

                                intent.putExtra(POST_TITLE, postTitle);

                                intent.putExtra(POST_FEATURED, featuredImage);

                                intent.putExtra(POST_DATE, postDate);

                                intent.putExtra(POST_CONTENT, postContent);

                                intent.putExtra(POST_CATEGORY, postCategory);

                                intent.putExtra(POST_EXCERPT, postExcerpt);

                                intent.putExtra(POST_LINK, postLink);
                                intent.putExtra(POST_AUTHOR,postAuthor);
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
                    textView.setText("Opps! Please Check Your Internet Connection");
                    imageLoading.setVisibility(View.VISIBLE);
                  //  imageLoading.setImageResource(R.drawable.ic_sad);
                    btn_try.setVisibility(View.VISIBLE);
                    success = false;
                }
                Snackbar.make(getView(),
                        "Failed.Check Your internet Connection",
                        Snackbar.LENGTH_SHORT).show();
                new AlertPopup().alertConnectError(getActivity());

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

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                            if(success){

                                if(anim==1){
                                    imageLoading.setImageResource(R.drawable.ic_happy);
                                    anim = 0;
                                }else{
                                    imageLoading.setImageResource(R.drawable.ic_happy2);
                                    anim = 1;
                                }

                            }else{

                                if(anim==1){
                                    imageLoading.setImageResource(R.drawable.ic_sad);
                                    anim = 0;
                                }else{
                                    imageLoading.setImageResource(R.drawable.ic_angry2);
                                    anim = 1;
                                }

                            }


                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 500); //execute in every 50000 ms
    }
}
