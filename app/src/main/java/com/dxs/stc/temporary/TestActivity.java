package com.dxs.stc.temporary;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.dxs.lib_mvpbase_appcompat.presenter.BasePresenter;
import com.dxs.lib_mvpbase_appcompat.templet.helper.ToolbarHelper;
import com.dxs.stc.R;
import com.dxs.stc.temporary.contract.MainContract;
import com.dxs.stc.temporary.impl.JBasePresenter;

public class TestActivity extends TempletActivity<BasePresenter> implements MainContract.View {


    @Override
    protected int initView(Bundle savedInstanceState) {
        return R.layout.activity_test;
    }



    //这里偷懒,就不去单独写个PresenterImpl了
    @Override
    protected BasePresenter initPresenter() {
        return new JBasePresenter<MainContract.View>() {
            @Override
            public void onCreate() {
                mView.getToolbarHelper().setCanBack(false);
                mView.getToolbarHelper().setTitle("首页");
                mView.getToolbarHelper().setRightText("213", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(TestActivity.this, "点击了右边的按钮", Toast.LENGTH_SHORT).show();
                    }
                });
                //设置滑动效果
                mView.getToolbarHelper().setScrollFlag(R.id.tl_custom, AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL |
                        AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
            }

            @Override
            public void initData() {
//                NetWorkManager.putErrorMsg(NullPointerException.class, "数据为空");
//                NetWorkManager.putApiCallback(new APIExceptionCallBack() {
//                    @Override
//                    public String callback(BaseResponse baseResponse) {
//                        Toast.makeText(mView.getContext(), "错误100", Toast.LENGTH_SHORT).show();
//                        //返回null,则只处理code,不弹消息.
//                        return null;
//                    }
//                }, 100);
//                //假数据
//                BaseResponse<String> objectBaseResponse = new BaseResponse<>();
//                objectBaseResponse.setData(new String());
//                objectBaseResponse.setCode(100);
//
//                Observable.just(objectBaseResponse)
//                        .compose(new JsonParesTransformer<>(Object.class))
//                        .subscribe(new SimpleObserver<Object>(mCompositeDisposable) {
//                            @Override
//                            public void call(Object o) {
//
//                            }
//                        });
            }

            @Override
            public void cancelNetWork() {

            }
        };
    }


    //重写该方法,设置ToolbarLayout
    @Override
    public int getToolbarLayout() {
        return ToolbarHelper.TOOLBAR_TEMPLET_DEFUATL;
    }


    @Override
    public boolean isMaterialDesign() {
        return true;
    }

    /** 上次点击返回键的时间 */
    private long lastBackPressed;
    /** 两次点击的间隔时间 */
    private static final int QUIT_INTERVAL = 2000;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && event.getRepeatCount()==0) {
            long backPressed = System.currentTimeMillis();
            if (backPressed - lastBackPressed > QUIT_INTERVAL) {
                lastBackPressed = backPressed;
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            } else {

                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
