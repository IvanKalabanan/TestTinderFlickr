package ivan.example.com.domain.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ivan on 12.11.17.
 */

public class Album {
    private String id;
    private int photoCount;
    private String title;
    private String description;
    private String createAt;
    private long uploadDate;
    private String photoUrl;
    private int likeCount;
    private int dislikeCount;

    public Album(String id, int photoCount, String title, String description, String createAt, String photoUrl) {
        this.id = id;
        this.photoCount = photoCount;
        this.title = title;
        this.description = description;
        this.createAt = convertDateToString(createAt);
        this.uploadDate = convertDateToLong(createAt);
        this.photoUrl = photoUrl;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    private String convertDateToString(String createAt) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        long convertDate = Long.valueOf(createAt) * 1000; // '*1000' because Date work with milliseconds
        return df.format(new Date(convertDate));
    }

    private Long convertDateToLong(String createAt) {
        return Long.valueOf(createAt) * 1000;
    }

    public String getId() {
        return id;
    }

    public int getPhotoCount() {
        return photoCount;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public long getUploadDate() {
        return uploadDate;
    }

    public int getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount(int dislikeCount) {
        this.dislikeCount = dislikeCount;
    }
}
