package com.twisac.kamwegawritings;

/**
 * Created by Elvin Ega on 10/22/2015.
 */
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.twisac.kamwegawritings.adapter.FavouriteAdapter;
import com.twisac.kamwegawritings.egaview.RecyclerItemClickListener;
import com.twisac.kamwegawritings.kamwegadb.AppDataStore;
import com.twisac.kamwegawritings.kamwegadb.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class TwoFragment extends Fragment{
    FavouriteAdapter adapter;
    RecyclerView rv;
    List<Post> postList = new ArrayList<>();
    private static final  String POST_CATEGORY = "category";
    private static final String POST_FEATURED = "featured";
    private static final String POST_CONTENT = "content";
    private static final String POST_TITLE = "title";
    private static final String POST_DATE = "date";
    private static final String POST_EXCERPT = "excerpt";
    private static final String POST_LINK = "link";
    private static final String POST_ID = "id";

    int anim = 1;
    Button btn_back;
    ImageView imageLoading;
    TextView textView;
    RelativeLayout relativeLayout;
    public TwoFragment() {
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
        View rootView = inflater.inflate(R.layout.fragment_fav, container, false);

        imageLoading =(ImageView)rootView.findViewById(R.id.imageLoading);
        textView = (TextView)rootView.findViewById(R.id.textLoading);
        btn_back = (Button) rootView.findViewById(R.id.btn_back);
        relativeLayout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        relativeLayout.setVisibility(View.GONE);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        
        
        
        rv = (RecyclerView)rootView.findViewById(R.id.rv);
        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        //rv.addItemDecoration(new DividerItemDecoration(getActivity(),LinearLayoutManager.VERTICAL));
        //   AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(adapter);
        //   alphaAdapter.setDuration(1000);
        try {
             postList = AppDataStore.getAllPosts();
            adapter = new FavouriteAdapter(getActivity(), postList);
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            e.printStackTrace();
         //   relativeLayout.setVisibility(View.VISIBLE);

        }
        if(postList.isEmpty()){
            relativeLayout.setVisibility(View.VISIBLE);
            callAsynchronousTask();
        }
        rv.setAdapter(adapter);
        rv.addOnItemTouchListener(
                new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        String postContent = postList.get(position).getContent();
                        String postCategory = postList.get(position).getCategory();
                        String postTitle =postList.get(position).getTitle();
                        String featuredImage =postList.get(position).getFeature();
                        String postDate = postList.get(position).getDate();
                        String postExcerpt = postList.get(position).getExcerpt();
                        String postLink = postList.get(position).getLink();
                        Long postId = postList.get(position).getId();

                        Intent intent = new Intent(getActivity(), OfflineStoryActivity.class);


                        intent.putExtra(POST_TITLE, postTitle);

                        intent.putExtra(POST_FEATURED, featuredImage);

                        intent.putExtra(POST_DATE, postDate);

                        intent.putExtra(POST_CONTENT, postContent);

                        intent.putExtra(POST_CATEGORY, postCategory);

                        intent.putExtra(POST_EXCERPT, postExcerpt);

                        intent.putExtra(POST_LINK, postLink);
                        intent.putExtra(POST_ID,postId);
                        startActivity(intent);

                    }
                })
        );
        return rootView;
    }
    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                            if(anim==1){
                                imageLoading.setImageResource(R.drawable.ic_suspicious);
                                anim = 0;
                            }else{
                                imageLoading.setImageResource(R.drawable.ic_surprised);
                                anim = 1;
                            }



                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 800); //execute in every 50000 ms
    }
}
