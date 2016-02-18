package com.dante.knowledge.news.view;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.holder.Holder;
import com.dante.knowledge.MainActivity;
import com.dante.knowledge.R;
import com.dante.knowledge.net.Constants;
import com.dante.knowledge.news.model.ZhihuTop;
import com.dante.knowledge.utils.ImageUtil;

/**
 * deals with displaying the top banner
 */
public class NetworkImageHolderView implements Holder<ZhihuTop> {
    private View view;

    @Override
    public View createView(Context context) {
        view = LayoutInflater.from(context).inflate(R.layout.card_item_big, null);
        return view;
    }

    @Override
    public void UpdateUI(final Context context, int position, final ZhihuTop entity) {
        final ImageView imageView = (ImageView) view.findViewById(R.id.story_img);
        TextView textView = (TextView) view.findViewById(R.id.story_title);
        ImageUtil.load(context, entity.getImage(), imageView);
        textView.setText(entity.getTitle());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZhihuDetailActivity.class);
                intent.putExtra(Constants.ID, entity.getId());
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((MainActivity) context,
                        imageView, context.getString(R.string.shared_img));
                ActivityCompat.startActivity((MainActivity) context, intent, optionsCompat.toBundle());
            }
        });

    }
}