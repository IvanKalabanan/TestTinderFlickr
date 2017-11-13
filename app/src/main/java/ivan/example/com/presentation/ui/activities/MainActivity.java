package ivan.example.com.presentation.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ivan.example.com.R;
import ivan.example.com.domain.repository.base.RestAPICommunicator;
import ivan.example.com.presentation.FragmentRequestListener;
import ivan.example.com.presentation.PhotoActionListener;
import ivan.example.com.presentation.ui.fragments.AlbumsFragment;
import ivan.example.com.presentation.ui.fragments.PhotosFragment;

public class MainActivity extends AppCompatActivity implements FragmentRequestListener, PhotoActionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startAlbumsFragment();
    }

    @Override
    protected void onDestroy() {
        RestAPICommunicator.cancelAllCalls();
        super.onDestroy();
    }

    @Override
    public void startAlbumsFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer,
                        new AlbumsFragment(),
                        AlbumsFragment.TAG)
                .commit();
    }

    @Override
    public void startPhotosFragment(String albumId, int likeCount, int dislikeCount, PhotosFragment.OnFinishMarkPhotoListener onFinishMarkPhotoListener) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mainContainer,
                        PhotosFragment
                                .newInstance(albumId, likeCount, dislikeCount)
                                .setOnFinishMarkPhotoListener(onFinishMarkPhotoListener),
                        PhotosFragment.TAG)
                .addToBackStack(PhotosFragment.TAG)
                .commit();
    }

    @Override
    public void loadPhoto(ImageView imageView, Object photo) {
        if (photo == null) {
            return;
        }
        Glide
                .with(this)
                .load(photo)
                .into(imageView);
    }
}
