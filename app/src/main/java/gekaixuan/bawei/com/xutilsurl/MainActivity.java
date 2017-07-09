package gekaixuan.bawei.com.xutilsurl;

import android.app.Application;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import org.xutils.common.Callback;
import org.xutils.http.RequestParams;

import org.xutils.image.ImageOptions;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends AppCompatActivity {
    private String urlPath = "http://qhb.2dyt.com/Bwei/news";
    private List<Databean.ListBean> list = new ArrayList<>();
    @ViewInject(R.id.mylist)
    private ListView lv;
    private Myadapter adapter;
    @ViewInject(R.id.tab)
    private TabLayout tab;
    UMShareAPI api;
    ImageView qqq;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        adapter = new Myadapter();
        loaddatta();
        addtab();
        // 创建菜单
        SlidingMenu menu = new SlidingMenu(this);
        // 设置菜单显示在左边
        menu.setMode(SlidingMenu.LEFT);
        // 设置菜单的触摸模式
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置菜单宽度
        menu.setBehindWidth(300);
        // 设置移动Activity的模式为Window
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        View view = View.inflate(this, R.layout.right, null);
        qqq = (ImageView) view.findViewById(R.id.qq);
        qqq.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View v) {
                                       APP app = (APP) getApplication();
                                       api = app.getUmShareAPI();
                                       api.doOauthVerify(MainActivity.this, SHARE_MEDIA.QQ, new UMAuthListener() {
                                           @Override
                                           public void onStart(SHARE_MEDIA share_media) {

                                           }

                                           @Override
                                           public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                               Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                                           }

                                           @Override
                                           public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                               Toast.makeText(MainActivity.this, "登录错误", Toast.LENGTH_SHORT).show();
                                           }

                                           @Override
                                           public void onCancel(SHARE_MEDIA share_media, int i) {
                                               Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
                                           }
                                       });

                                   }


                               }
        );


        // 设置菜单的布局
        menu.setMenu(view);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.onActivityResult(requestCode, resultCode, data);
    }

    private void addtab() {
        tab.addTab(tab.newTab().setText("热点"));
        tab.addTab(tab.newTab().setText("新闻"));
        tab.addTab(tab.newTab().setText("游戏"));
        tab.addTab(tab.newTab().setText("军事"));
        tab.addTab(tab.newTab().setText("科技"));
        tab.addTab(tab.newTab().setText("娱乐"));
        tab.addTab(tab.newTab().setText("生活 "));
        tab.addTab(tab.newTab().setText("学习"));
        tab.addTab(tab.newTab().setText("财经"));
        tab.addTab(tab.newTab().setText("汽车"));

    }

    private void loaddatta() {
        RequestParams params = new RequestParams(urlPath);
        params.addQueryStringParameter("page", "1");
        params.addQueryStringParameter("type", "5");
        params.addQueryStringParameter("postkey", "1503d");
        x.http().get(params, new Callback.CommonCallback<String>() {

            @Override
            public void onSuccess(String result) {
                Databean data = new Gson().fromJson(result, Databean.class);
                list.addAll(data.getList());
                adapter.notifyDataSetChanged();
                lv.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(MainActivity.this, ex.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(Callback.CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    class Myadapter extends BaseAdapter {
        ImageOptions options = new ImageOptions.Builder()
                .setLoadingDrawableId(R.mipmap.ic_launcher)
                .setFailureDrawableId(R.mipmap.ic_launcher)
                .build();

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(MainActivity.this, R.layout.item, null);
                holder = new ViewHolder();
                x.view().inject(holder, convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(list.get(position).getTitle());

            String url = list.get(position).getPic().split("\\|")[0];

            x.image().bind(holder.image, url, options);
            return convertView;

        }
    }

    class ViewHolder {
        @ViewInject(R.id.title)
        TextView title;
        @ViewInject(R.id.myimage)
        ImageView image;
    }
}
