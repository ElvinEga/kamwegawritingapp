package com.twisac.kamwegawritings.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.twisac.kamwegawritings.R;
import com.twisac.kamwegawritings.jsonpojo.category.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Elvin Ega on 11/4/2015.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<Category> categoryList = new ArrayList<>();
    Context context;
    private static final String ENDPOINT = "http://207.246.120.170/kamwega/wp-content/uploads/";


    private String [] images = {"2015/11/father-daughter2-300x125.jpg", "2016/06/5948c5ab9bffd136e3050c1829fb503e.jpg",
            "2016/01/o-WRITING-A-LETTER-facebook.jpg",
            "2016/09/collegestudents2-300x201.jpg",
            "2015/12/217747611821fd6bd8c6cee7596ee05c.jpg",
            "2017/01/sleeping-pills-500.jpg",
            "2017/06/IMG_20170610_083558_718.jpg",
            "2018/04/IDLE.png",
            "2017/10/the-boy-who-cried-wolf-img.jpg",
            "2017/01/sleeping-pills-500.jpg",
            "2017/07/email-button.jpg"};

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public CategoryAdapter(Context context, List<Category> categoryList){
        //super(context, resource, objects);
        this.context = context;
        this.categoryList = categoryList;

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private TextView tv_category;
        private ImageView iv_category;
        private RelativeLayout rl_foreground;


        public ViewHolder(View v) {
            super(v);

            tv_category =  v.findViewById(R.id.tv_category);
            iv_category=  v.findViewById(R.id.iv_category);
            rl_foreground=  v.findViewById(R.id.rl_foreground);



        }
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Category category = categoryList.get(position);

        holder.tv_category.setText(category.getName());

        Picasso.with(context)
                .load(ENDPOINT+images[position])
                .placeholder(R.drawable.kwback)// optional
                .error(R.drawable.kwback)     // optional

                .fit().centerCrop().into(holder.iv_category);

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //   context =parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_category, parent, false);
        ViewHolder viewHolder =new ViewHolder(view);
        return viewHolder;
    }


}
