package com.dante.knowledge.news.other;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.dante.knowledge.R;
import com.dante.knowledge.net.DB;
import com.dante.knowledge.news.interf.OnListFragmentInteract;
import com.dante.knowledge.news.model.ZhihuData;
import com.dante.knowledge.news.model.ZhihuItem;
import com.dante.knowledge.news.model.ZhihuTop;
import com.dante.knowledge.news.view.NetworkImageHolderView;
import com.dante.knowledge.utils.ImageUtil;
import com.dante.knowledge.utils.StringUtil;

import java.util.List;

/**
 * Zhihu news' recyclerView adapter
 */

public class ZhihuListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_BANNER = 0;
    /**
     * header is a title to display date
     */
    private static final int TYPE_HEADER = 1;
    private static final int TYPE_ITEM = 2;
    /**
     * footer is to show load more hint
     */
    private static final int TYPE_FOOTER = 3;
    public static final String ZHIHU_ITEM = "zhihu_item";
    public static int textGrey;
    public static int textDark;
    private final Context context;

    private ZhihuData news;
    private List<ZhihuItem> zhihuItems;
    private List<ZhihuTop> tops;

    private OnListFragmentInteract mListener;
    private boolean hasFooter;
    private boolean showHeader;
    private List<ZhihuItem> newItems;

    public void setShowHeader(boolean showHeader) {
        this.showHeader = showHeader;
    }

    public boolean isHasFooter() {
        return hasFooter;
    }

    public void setHasFooter(boolean hasFooter) {
        this.hasFooter = hasFooter;
        notifyDataSetChanged();
    }

    public ZhihuListAdapter(Context context, OnListFragmentInteract listener) {
        this.context = context;
        mListener = listener;
        zhihuItems = DB.findAll(ZhihuItem.class);
        tops = DB.findAll(ZhihuTop.class);
    }

    public void addNews(ZhihuData news) {
        this.news = news;
        setHasFooter(news.getStories().size() != 0);
        notifyDataSetChanged();
    }

    public void clear() {
        showHeader = false;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_BANNER) {
            View view = inflater.inflate(R.layout.fragment_news_banner, parent, false);
            return new BannerViewHolder(view);

        } else if (viewType == TYPE_HEADER) {
            View view = inflater.inflate(R.layout.fragment_news_header, parent, false);
            return new HeaderViewHolder(view);

        } else if (viewType == TYPE_FOOTER) {
            View view = inflater.inflate(R.layout.footer_loading, parent, false);
            return new FooterViewHolder(view);

        } else {
            View view = inflater.inflate(R.layout.fragment_news_item, parent, false);
            return new ViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        Context context = holder.itemView.getContext();
        textGrey = ContextCompat.getColor(context, R.color.darker_gray);
        textDark = ContextCompat.getColor(context, android.support.design.R.color.abc_primary_text_material_light);

        if (holder instanceof ViewHolder) {
            final ViewHolder viewHolder = (ViewHolder) holder;
            if (position == 1) {
                viewHolder.header.setText(context.getString(R.string.hottest));
                viewHolder.header.setVisibility(View.VISIBLE);
                viewHolder.mItem.setVisibility(View.GONE);

                return;
            } else {
                viewHolder.zhihuItem = zhihuItems.get(position - 2);//position=0, 1 are occupied with banner, header
                //id == 1 means this item is added by me, so it's a header to show date.

                if (viewHolder.zhihuItem.getType() == 1) {
                    String date = StringUtil.getDisplayDate(viewHolder.zhihuItem.getId() + "");
                    viewHolder.header.setText(date);
                    viewHolder.header.setVisibility(View.VISIBLE);
                    viewHolder.mItem.setVisibility(View.GONE);
                    return;
                } else {
                    viewHolder.header.setVisibility(View.GONE);
                    viewHolder.mItem.setVisibility(View.VISIBLE);
                }
            }

            showHeader = true;//first header just shows stationary text (today), others use date.
            ImageUtil.load(context, viewHolder.zhihuItem.getImages().get(0).getVal(), viewHolder.mImage);
            viewHolder.mTitle.setText(viewHolder.zhihuItem.getTitle());
            viewHolder.mTitle.setTextColor(textDark);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mListener) {
                        mListener.onListFragmentInteraction(viewHolder);
                    }

                }
            });

        } else if (holder instanceof BannerViewHolder) {
            final BannerViewHolder itemHolder = (BannerViewHolder) holder;
            itemHolder.banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                @Override
                public NetworkImageHolderView createHolder() {
                    return new NetworkImageHolderView();
                }
            }, tops);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_BANNER;
        } else if (hasFooter && position == zhihuItems.size() + 1) {
            // position 0 is banner so
            // the footer appears the size+1 position
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {
        //items + banner + footer
        if (hasFooter) {
            return zhihuItems.size() + 2;
        }
        return zhihuItems.size() + 1;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View view) {
            super(view);
        }
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        public final TextView headerText;

        public HeaderViewHolder(View itemView) {
            super(itemView);
            headerText = (TextView) itemView.findViewById(R.id.story_header);
        }
    }

    public class BannerViewHolder extends RecyclerView.ViewHolder {
        public final ConvenientBanner<ZhihuTop> banner;

        public BannerViewHolder(View view) {
            super(view);
            banner = (ConvenientBanner) view.findViewById(R.id.convenientBanner);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView header;
        public final ImageView mImage;
        public final TextView mTitle;
        public final View mItem;
        public ZhihuItem zhihuItem;

        public ViewHolder(View view) {
            super(view);
            header = (TextView) view.findViewById(R.id.story_header);
            mImage = (ImageView) view.findViewById(R.id.story_img);
            mTitle = (TextView) view.findViewById(R.id.story_title);
            mItem = view.findViewById(R.id.story_item);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTitle.getText() + "'";
        }
    }
}
