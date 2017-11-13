package ivan.example.com.presentation.ui.view;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.mindorks.placeholderview.SwipePlaceHolderView;
import com.mindorks.placeholderview.annotations.Animate;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Position;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.swipe.SwipeCancelState;
import com.mindorks.placeholderview.annotations.swipe.SwipeIn;
import com.mindorks.placeholderview.annotations.swipe.SwipeInState;
import com.mindorks.placeholderview.annotations.swipe.SwipeOut;
import com.mindorks.placeholderview.annotations.swipe.SwipeOutState;

import ivan.example.com.R;
import ivan.example.com.domain.models.Photo;
import ivan.example.com.presentation.PhotoActionListener;

/**
 * Created by ivan on 13.11.17.
 */

@Layout(R.layout.item_card_view)
public class PhotoCard {
    @View(R.id.profileImageView)
    private ImageView profileImageView;

    @View(R.id.nameAgeTxt)
    private TextView nameAgeTxt;

    @Position
    private int position;

    private Photo photo;
    private PhotoActionListener photoActionListener;
    private OnPhotoActionListener onPhotoActionListener;

    private boolean isLastItem;

    public PhotoCard(Photo photo, boolean isLastItem, PhotoActionListener photoActionListener, OnPhotoActionListener onPhotoActionListener) {
        this.photo = photo;
        this.isLastItem = isLastItem;
        this.photoActionListener = photoActionListener;
        this.onPhotoActionListener = onPhotoActionListener;
    }

    @Resolve
    private void onResolved(){
        Log.d("EVENT", "onSwipedOut - " + position);
        photoActionListener.loadPhoto(profileImageView, photo.getPhotoUrl());
        nameAgeTxt.setText(photo.getTitle());
    }

    @SwipeOut
    private void onSwipedOut(){
        onPhotoActionListener.onDislike();
        if (isLastItem) {
            onPhotoActionListener.onLastItem();
        }
    }

    @SwipeIn
    private void onSwipeIn(){
        onPhotoActionListener.onLike();
        if (isLastItem) {
            onPhotoActionListener.onLastItem();
        }
    }

    public interface OnPhotoActionListener{
        void onLike();
        void onDislike();
        void onLastItem();
    }

}
