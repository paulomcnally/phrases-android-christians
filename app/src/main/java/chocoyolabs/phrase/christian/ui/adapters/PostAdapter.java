package chocoyolabs.phrase.christian.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import chocoyolabs.phrase.christian.R;
import chocoyolabs.phrase.christian.models.PostModel;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<PostModel> mItems;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CardView cardView;
        public ImageView image;
        public ImageButton imageButton;
        public Context parentContext;

        public ViewHolder(Context context, View view) {
            super(view);
            parentContext = context;
            cardView = (CardView) view.findViewById(R.id.cardView);
            image = (ImageView) view.findViewById(R.id.image);
            imageButton = (ImageButton) view.findViewById(R.id.imageButton);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PostAdapter(List<PostModel> items) {
        this.mItems = items;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new ViewHolder(parent.getContext(), view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final PostModel item = mItems.get(position);


        Glide.with(holder.parentContext)
            .load(item.getUrl())
                .asBitmap()
                .into(new BitmapImageViewTarget(holder.image) {
                    @Override
                    protected void setResource(final Bitmap resource) {
                        // Do bitmap magic here
                        super.setResource(resource);
                        holder.imageButton.setVisibility(View.VISIBLE);

                        holder.imageButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(Intent.ACTION_SEND);
                                intent.setType("image/*");
                                intent.putExtra(Intent.EXTRA_STREAM, getLocalBitmapUri(resource));

                                // Launch sharing dialog for image
                                holder.parentContext.startActivity(Intent.createChooser(intent, holder.parentContext.getString(R.string.share_title)));
                            }
                        });

                    }
                });
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addRow(List<PostModel> items) {
        mItems.addAll(items);
        this.notifyDataSetChanged();
    }

    public Uri getLocalBitmapUri(Bitmap bmp) {

        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }
}
