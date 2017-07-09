package gekaixuan.bawei.com.xutilsurl;

import android.app.Application;

import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.*;
import org.xutils.BuildConfig;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by 葛凯旋 on 2017/7/8.
 */
public class APP  extends Application {
    {
        PlatformConfig.setQQZone("1106201745","mlOFmnJgnvn6zNxA");
    }
    private  UMShareAPI  umShareAPI;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
      umShareAPI=  UMShareAPI.get(this);
    }
    public  UMShareAPI getUmShareAPI(){
        return  umShareAPI;
    }
}
