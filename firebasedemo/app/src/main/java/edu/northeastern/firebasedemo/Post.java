package edu.northeastern.firebasedemo;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.firebase.Timestamp;

// This class represents a Post object
// It implements Parcelable so that it can be passed between activities
// We have a id, poster, title, bodyText, imageUrl, and createdAt fields
// We have a constructor that takes all the fields except for createdAt

// You may notice in the logcat that there is a warning about no getter or setter for stability
// Stability is used by Firebase crashlytics to determine the document, essentially tracking the number of crashes for a specific document.
// I did not include the stability field in the Post class because it is not necessary for the functionality of the app.
public class Post implements Parcelable {

    private String id;
    private String poster;
    private String title;
    private String bodyText;
    private String imageUrl;
    private Timestamp createdAt;


    public Post() {
    }

    public Post(String id, String userId, String title, String bodyText, String imageUrl) {
        this.id = id;
        this.poster = userId;
        this.title = title;
        this.bodyText = bodyText;
        this.imageUrl = imageUrl;
        this.createdAt = Timestamp.now();
    }

    // Parcelable methods
    protected Post(Parcel in) {
        id = in.readString();
        poster = in.readString();
        title = in.readString();
        bodyText = in.readString();
        imageUrl = in.readString();
        createdAt = in.readParcelable(Timestamp.class.getClassLoader());
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    // Getters and setters
    public String getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getTitle() {
        return title;
    }

    public String getBodyText() { return bodyText; }

    public String getImageUrl() {
        return imageUrl;
    }

    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(String id) {
        this.id = id;
    }

    public void setPoster(String userId) {
        this.poster = userId;
    }

    public void setTitle(String title) { this.title = title; }

    public void setBodyText(String bodyText) { this.bodyText = bodyText; }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Post{" +
                "id='" + id + '\'' +
                ", userId='" + poster + '\'' +
                ", title='" + title + '\'' +
                ", bodyText='" + bodyText + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.poster);
        dest.writeString(this.title);
        dest.writeString(this.bodyText);
        dest.writeString(this.imageUrl);
        dest.writeParcelable(this.createdAt, flags);
    }
}
