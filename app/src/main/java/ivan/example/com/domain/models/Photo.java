package ivan.example.com.domain.models;

/**
 * Created by ivan on 13.11.17.
 */

public class Photo {
    private String photoUrl;
    private String title;
    private long uploadDate;

    public Photo(String photoUrl, String title, String createAt) {
        this.photoUrl = photoUrl;
        this.title = title;
        this.uploadDate = convertDate(createAt);
    }

    private Long convertDate(String createAt) {
         return Long.valueOf(createAt);
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public long getUploadDate() {
        return uploadDate;
    }
}
