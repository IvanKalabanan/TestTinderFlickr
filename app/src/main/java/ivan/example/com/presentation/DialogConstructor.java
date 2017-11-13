package ivan.example.com.presentation;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import ivan.example.com.R;
import ivan.example.com.databinding.DialogInfoBinding;

/**
 * Created by ivan on 13.11.17.
 */

public class DialogConstructor {

    public static void showAlbumInfoDialog(Context context, String createAt, String title, String description, int photoCount) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(context.getString(R.string.album_info));

        DialogInfoBinding dialogBinding = DialogInfoBinding.bind(View.inflate(context, R.layout.dialog_info, null));
        dialog.setView(dialogBinding.getRoot());

        dialogBinding.titleTv.setText(context.getString(R.string.dialog_info_title, title));
        dialogBinding.descriptionTv.setText(context.getString(R.string.dialog_info_description, description));
        dialogBinding.createAtTv.setText(context.getString(R.string.dialog_info_create_date, createAt));
        dialogBinding.photoCountTv.setText(context.getString(R.string.dialog_info_photo_count, String.valueOf(photoCount)));

        dialog
                .setPositiveButton(context.getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .create()
                .show();
    }

}
