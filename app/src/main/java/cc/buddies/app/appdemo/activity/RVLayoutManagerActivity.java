package cc.buddies.app.appdemo.activity;

import android.os.Bundle;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import cc.buddies.app.appdemo.BaseActivity;
import cc.buddies.app.appdemo.R;
import cc.buddies.app.appdemo.listener.OnViewPagerListener;
import cc.buddies.app.appdemo.listener.ViewPagerLayoutManager;
import cc.buddies.app.treasury.utils.LogUtils;

public class RVLayoutManagerActivity extends BaseActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rvlayout_manager);

        recyclerView = findViewById(R.id.recycle_view);

        ViewPagerLayoutManager layoutManager = new ViewPagerLayoutManager(this, OrientationHelper.VERTICAL);
        MyAdapter myAdapter = new MyAdapter();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myAdapter);

        layoutManager.setOnViewPagerListener(new OnViewPagerListener() {
            @Override
            public void onInitComplete() {
                LogUtils.e("OnViewPagerListener----onInitComplete()");
            }

            @Override
            public void onPageRelease(boolean isNext, int position) {
                LogUtils.e("OnViewPagerListener----onPageRelease()  isNext: " + isNext + "  position: " + position);
            }

            @Override
            public void onPageSelected(int position, boolean isBottom) {
                LogUtils.e("OnViewPagerListener----onPageSelected()  position: " + position + "     isBottom: " + isBottom);
            }
        });
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private int[] imgs = {R.mipmap.ic_launcher,R.mipmap.ic_launcher};

        public MyAdapter(){ }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_pager,parent,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.img_thumb.setImageResource(imgs[position%2]);
        }

        @Override
        public int getItemCount() {
            return 20;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            ImageView img_thumb;
            public ViewHolder(View itemView) {
                super(itemView);
                img_thumb = itemView.findViewById(R.id.img_thumb);
            }
        }
    }
}
