package com.twisac.kamwegawritings.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.interfaces.RecycConnector;
import com.twisac.kamwegawritings.jsonpojo.Posts;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Elvin Ega on 10/30/2015.
 */
public class NewsRealAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Posts> mPosts;
    Context context;
    protected boolean showLoader;
    private final int VIEW_ITEM = 1;
    private final int VIEW_PROG = 0;

    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean loading;
    private RecycConnector.OnLoadMoreListener onLoadMoreListener;

    public NewsRealAdapter(Context context, List<Posts> objects,RecyclerView recyclerView){
        //super(context, resource, objects);
        this.context = context;
        this.mPosts= objects;
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {

            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();


            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView,
                                       int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager
                            .findLastVisibleItemPosition();
                    if (!loading
                            && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        // End has been reached
                        // Do something
                        if (onLoadMoreListener != null) {
                            onLoadMoreListener.onLoadMore();
                        }
                        loading = true;
                    }
                }
            });
        }

    }
    public void clear() {
        mPosts.clear();
        notifyDataSetChanged();
    }

    public static class PostViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private ImageView headerImage;
        private TextView textTopic;
        private TextView textHeader;
        private  TextView textInfo;
        private TextView textCat;
        private TextView textComments;
        private TextView textTime;
        private PostViewHolder(View v) {
            super(v);
            headerImage=(ImageView) v.findViewById(R.id.header_image);
             textTopic = (TextView) v.findViewById(R.id.textTopic);
             textHeader= (TextView) v.findViewById(R.id.textHeader);
            textCat= (TextView) v.findViewById(R.id.textcat);
             textInfo= (TextView) v.findViewById(R.id.textInfo);
             textTime= (TextView) v.findViewById(R.id.textTime);
            textComments= (TextView) v.findViewById(R.id.txtComments);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    headerImage=(ImageView) v.findViewById(R.id.header_image);
                }
            });
        }
    }
    @Override
    public int getItemViewType(int position) {
        return mPosts.get(position) != null ? VIEW_ITEM : VIEW_PROG;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        RecyclerView.ViewHolder vh;
        if (viewType == VIEW_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.homecard, parent, false);

            vh = new PostViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.progressbar_item, parent, false);

            vh = new ProgressViewHolder(v);
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
   Posts posts=mPosts.get(position);

        //imge
        if (holder instanceof PostViewHolder) {
            ImageView headerImage = ((PostViewHolder) holder).headerImage;
            String imageurl = posts.getBetterFeaturedImage().getSourceUrl();
            Picasso.with(context)
                    .load(posts.getBetterFeaturedImage().getSourceUrl())
                    .placeholder(R.drawable.kwback)// optional
                    .error(R.drawable.kwback)     // optional
                            //  .resize(600, 150)                        // optional
                            // .rotate(90)
                            // optional
                    .fit().centerCrop().into(headerImage);
            TextView txtTitle = ((PostViewHolder) holder).textTopic;
            txtTitle.setText(posts.getTitle().getRendered());

            TextView txtCat = ((PostViewHolder) holder).textCat;
            txtCat.setText(posts.getAuthormeta().getCategories());


            TextView txtExerpt = ((PostViewHolder) holder).textHeader;
            String excerpt = posts.getExcerpt().getRendered();
            Spanned htmlAsSpanned = Html.fromHtml(excerpt);
            txtExerpt.setMaxLines(4);
            txtExerpt.setText(htmlAsSpanned);

            //Textinfo
            TextView textInfo = ((PostViewHolder) holder).textInfo;
            String nameAuthor = "By: " + posts.getAuthormeta().getName();
            // nameAuthor.replaceAll("<","&lt;");
            TextView txtComments = ((PostViewHolder) holder).textComments;
            int comments = posts.getAuthormeta().getComments();
            txtComments.setText(comments +" comments ");
            textInfo.setText(nameAuthor);

            //TextTime
            TextView textTime = ((PostViewHolder) holder).textTime;
            ;
            String mDate = posts.getDate();
            mDate = getDate(mDate);

            textTime.setText(mDate);
        }else{
            ((ProgressViewHolder) holder).progressBar.setIndeterminate(true);
        }
    }
    public void setLoaded() {
        loading = false;
    }
    public void removeItem(RecyclerView.ViewHolder item) {
        int indexOfItem = mPosts.indexOf(item);
        if (indexOfItem != -1) {
            this.mPosts.remove(indexOfItem);
            notifyItemRemoved(indexOfItem);
        }
    }
    public void setOnLoadMoreListener(RecycConnector.OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    /*  @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
     //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.homecard, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);

        return viewHolder;
    }*/
    @Override
    public int getItemCount() {
        return mPosts.size();
    }
    private String getDate(String timeStamp) {
//2015-11-09T21:53:42
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            //sdf.parse("2015-11-09T21:53:42");
            Date netDate = sdf2.parse(timeStamp);
        //   return sdf.format(netDate);
            long timet=netDate.getTime();
            long now = System.currentTimeMillis();
          return  DateUtils.getRelativeTimeSpanString( timet, now, DateUtils.SECOND_IN_MILLIS).toString();
          //return  DateUtils.getRelativeDateTimeString(context, timet, DateUtils.SECOND_IN_MILLIS, DateUtils.WEEK_IN_MILLIS, DateUtils.FORMAT_ABBREV_ALL).toString();
        } catch (Exception ex) {
            return "xxx";
        }

    }
    public static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View v) {
            super(v);
            progressBar = (ProgressBar) v.findViewById(R.id.progressBar1);
        }
    }
}
