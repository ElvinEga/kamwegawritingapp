package com.twisac.kamwegawritings.kamwegadb;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by beast on 4/27/18.
 */

public class AppDataStore {
    public static  void savePostData(String POST_TITLE,String POST_FEATURED,String POST_EXCERPT,String POST_CONTENT,String POST_DATE,String POST_AUTHOR,String POST_CATEGORY) {
        Post post = new Post(POST_TITLE, POST_FEATURED, POST_EXCERPT,POST_CONTENT,POST_DATE, POST_AUTHOR, POST_CATEGORY);
        post.save();
    }

    public static List<Post> getAllPosts() {
        return new Select()
                .all()
                .from(Post.class)
                .execute();
    }
    public static List<Post> getAllPosts(String title) {
        return new Select()
                .all()
                .from(Post.class)
                .where("title = ?",title).execute();
    }
    public static void deletePostData(long itemId) {

        new Delete()
                .from(Post.class)
                .where("id = ?",itemId).execute();
    }
    public static void deleteAllPostData() {
        // Log.d("DeletePos",productId);

        new Delete().from(Post.class).execute();
    }
}
