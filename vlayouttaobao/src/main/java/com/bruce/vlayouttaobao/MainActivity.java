package com.bruce.vlayouttaobao;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StaggeredGridLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.bruce.vlayouttaobao.adapter.FloatLayoutAdapter;
import com.bruce.vlayouttaobao.adapter.GridHelperAdapter;
import com.bruce.vlayouttaobao.adapter.LinearAdapter;
import com.bruce.vlayouttaobao.adapter.OneToNAdapter;
import com.bruce.vlayouttaobao.adapter.SingleLayoutAdapter;
import com.bruce.vlayouttaobao.adapter.StaggeredGridLayoutAdapter;
import com.bruce.vlayouttaobao.adapter.StickyLayoutAdapter;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

//原文地址:https://blog.csdn.net/qq_15988951/article/details/78971307
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_list)
    RecyclerView rvList;

    private ArrayList<String> lists = new ArrayList<>();
    private ArrayList<Integer> imgSrc = new ArrayList<>();
    private ArrayList<Integer> goodSrc = new ArrayList<>();
    private ArrayList<Integer> stagSrc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initLinearData();

        /**
         * 步骤2：设置组件复用回收池
         * */
        RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
        rvList.setRecycledViewPool(viewPool);
        viewPool.setMaxRecycledViews(0, 10);

        // 创建VirtualLayoutManager对象
        // 同时内部会创建一个LayoutHelperFinder对象，用来后续的LayoutHelper查找
        VirtualLayoutManager layoutManager = new VirtualLayoutManager(this);
        rvList.setLayoutManager(layoutManager);

        DelegateAdapter adapters = new DelegateAdapter(layoutManager, true);

//        //FixLayoutHelper /**
//         设置固定布局
//         */
//        FixLayoutHelper fixHelper=new FixLayoutHelper(0,0);
//        adapters.addAdapter(new FixLayoutAdapter(this,fixHelper));
//
        //ColumnLayoutHelper
//        ColumnLayoutHelper columnLayoutHelper=new ColumnLayoutHelper();
//        adapters.addAdapter(new FixLayoutAdapter(this,columnLayoutHelper));


        //floatLayoutHelper
        FloatLayoutHelper layoutHelper = new FloatLayoutHelper();
        layoutHelper.setAlignType(FixLayoutHelper.BOTTOM_RIGHT);
        layoutHelper.setDefaultLocation(100, 400);

        adapters.addAdapter(new FloatLayoutAdapter(this,layoutHelper));


        //singleHelper
        SingleLayoutHelper singHelper=new SingleLayoutHelper();
        singHelper.setBgColor(R.color.colorPrimary);
        singHelper.setMargin(5,0,5,5);
        adapters.addAdapter(new SingleLayoutAdapter(this,singHelper));


        initGridData();
        // 进行Grid布局
        GridLayoutHelper gridHelper = new GridLayoutHelper(5);
        gridHelper.setMarginTop(30);
//        gridHelper.setWeights(new float[]{20.0f,20.0f,20.0f,20.0f,20.0f});
        //设置垂直方向条目的间隔
        gridHelper.setVGap(5);
        //设置水平方向条目的间隔
        gridHelper.setHGap(5);
        gridHelper.setMarginLeft(30);
        gridHelper.setMarginBottom(30);
        //自动填充满布局
        gridHelper.setAutoExpand(true);
        adapters.addAdapter(new GridHelperAdapter(imgSrc, gridHelper));


        //吸顶的Helper
        StickyLayoutHelper stickyHelper = new StickyLayoutHelper();
        adapters.addAdapter(new StickyLayoutAdapter(stickyHelper));


        initOnePlusData();
        //onePlusNHelper
        OnePlusNLayoutHelper helper = new OnePlusNLayoutHelper();
        helper.setBgColor(R.color.colorPrimary);
        helper.setPadding(5, 5, 5, 5);
        helper.setColWeights(new float[]{50f});

        helper.setMargin(10, 20, 10, 10);
        adapters.addAdapter(new OneToNAdapter(goodSrc.subList(0, 2), helper));



        OnePlusNLayoutHelper helper2 = new OnePlusNLayoutHelper();
        helper2.setBgColor(R.color.colorPrimary);
        helper2.setPadding(5, 5, 5, 5);
        helper2.setColWeights(new float[]{65f,35f});

        helper.setMargin(10, 20, 10, 10);
        adapters.addAdapter(new OneToNAdapter(goodSrc.subList(2, 4), helper2));



        OnePlusNLayoutHelper helper3 = new OnePlusNLayoutHelper();
        helper3.setBgColor(0xffef8ba3);
        helper3.setAspectRatio(2.0f);
        helper3.setColWeights(new float[]{40f});
        helper3.setRowWeight(30f);
        helper3.setMargin(10, 20, 10, 20);
        helper3.setPadding(10, 10, 10, 10);
        adapters.addAdapter(new OneToNAdapter(goodSrc.subList(4,9), helper3));

         //Linear 布局
        LinearLayoutHelper linearHelper = new LinearLayoutHelper(10);
        adapters.addAdapter(new LinearAdapter(this,lists, linearHelper));

        //StaggerGridLayoutHelper
        initStagData();
        StaggeredGridLayoutHelper stagHelp=new StaggeredGridLayoutHelper(2);
        stagHelp.setHGap(5);
        stagHelp.setVGap(5);
        adapters.addAdapter(new StaggeredGridLayoutAdapter(this,stagSrc,stagHelp));


        rvList.setAdapter(adapters);

    }

    private void initStagData() {
        stagSrc.add(R.mipmap.g1);
        stagSrc.add(R.mipmap.zl);
        stagSrc.add(R.mipmap.ic);
        stagSrc.add(R.mipmap.g4);
        stagSrc.add(R.mipmap.g5);
        stagSrc.add(R.mipmap.g1);
        stagSrc.add(R.mipmap.zl);
        stagSrc.add(R.mipmap.ic);
        stagSrc.add(R.mipmap.g4);
        stagSrc.add(R.mipmap.g5);
    }

    private void initOnePlusData() {
        goodSrc.add(R.mipmap.img1);
        goodSrc.add(R.mipmap.img2);
        goodSrc.add(R.mipmap.tow1);
        goodSrc.add(R.mipmap.two2);
        goodSrc.add(R.mipmap.g1);
        goodSrc.add(R.mipmap.g2);
        goodSrc.add(R.mipmap.g3);
        goodSrc.add(R.mipmap.g4);
        goodSrc.add(R.mipmap.g5);
        goodSrc.add(R.mipmap.img1);
        goodSrc.add(R.mipmap.img2);
        goodSrc.add(R.mipmap.tow1);
        goodSrc.add(R.mipmap.two2);
        goodSrc.add(R.mipmap.img1);
        goodSrc.add(R.mipmap.img2);
        goodSrc.add(R.mipmap.tow1);
        goodSrc.add(R.mipmap.two2);
        goodSrc.add(R.mipmap.img1);
        goodSrc.add(R.mipmap.img2);
        goodSrc.add(R.mipmap.tow1);
        goodSrc.add(R.mipmap.two2);
    }

    private void initGridData() {
        imgSrc.add(R.mipmap.i1);
        imgSrc.add(R.mipmap.i2);
        imgSrc.add(R.mipmap.i3);
        imgSrc.add(R.mipmap.i4);
        imgSrc.add(R.mipmap.i5);
        imgSrc.add(R.mipmap.i6);
        imgSrc.add(R.mipmap.i7);
        imgSrc.add(R.mipmap.i8);
        imgSrc.add(R.mipmap.i9);
        imgSrc.add(R.mipmap.i10);
//        imgSrc.add(R.mipmap.i1);
//        imgSrc.add(R.mipmap.i2);
//        imgSrc.add(R.mipmap.i3);
//        imgSrc.add(R.mipmap.i4);
//        imgSrc.add(R.mipmap.i5);
//        imgSrc.add(R.mipmap.i6);
    }

    private void initLinearData() {
        for (int i = 0; i < 40; i++) {
            lists.add(" LinearHelper :" + i);
        }
    }


    static class SubAdapter extends DelegateAdapter.Adapter<MainViewHolder> {

        private Context mContext;

        private LayoutHelper mLayoutHelper;


        private VirtualLayoutManager.LayoutParams mLayoutParams;
        private int mCount = 0;


        public SubAdapter(Context context, LayoutHelper layoutHelper, int count) {
            this(context, layoutHelper, count, new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        }

        public SubAdapter(Context context, LayoutHelper layoutHelper, int count, @NonNull VirtualLayoutManager.LayoutParams layoutParams) {
            this.mContext = context;
            this.mLayoutHelper = layoutHelper;
            this.mCount = count;
            this.mLayoutParams = layoutParams;
        }

        @Override
        public LayoutHelper onCreateLayoutHelper() {
            return mLayoutHelper;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MainViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            // only vertical
            holder.itemView.setLayoutParams(
                    new VirtualLayoutManager.LayoutParams(mLayoutParams));
        }


        @Override
        protected void onBindViewHolderWithOffset(MainViewHolder holder, int position, int offsetTotal) {
            ((TextView) holder.itemView.findViewById(R.id.title)).setText(Integer.toString(offsetTotal));
        }

        @Override
        public int getItemCount() {
            return mCount;
        }
    }


    static class MainViewHolder extends RecyclerView.ViewHolder {

        /**
         * 滑动退出了多少个条目
         */
        public static volatile int existing = 0;
        public static int createdTimes = 0;

        public MainViewHolder(View itemView) {
            super(itemView);
            createdTimes++;
            existing++;
        }

        @Override
        protected void finalize() throws Throwable {
            existing--;
            super.finalize();
        }
    }


}
