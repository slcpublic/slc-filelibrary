package com.slc.filelibrary.ui.activity.base;

import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.slc.filelibrary.ui.fragment.base.SlcBaseFileListView;

public abstract class SlcBaseFileActivity extends AppCompatActivity implements SlcBaseFileView {
    protected SlcBaseFileDelegate slcBaseFileDelegate = new SlcBaseFileDelegate(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        bindView(savedInstanceState);
        slcBaseFileDelegate.onCreate();
    }

    @LayoutRes
    protected abstract int getContentView();

    /**
     * 绑定视图
     *
     * @param savedInstanceState
     */
    protected abstract void bindView(@Nullable Bundle savedInstanceState);

    /**
     * 初始化数据
     */
    @Override
    public void initDate() {
        slcBaseFileDelegate.initDate();
    }

    @Override
    public AppCompatActivity getAppCompatActivity() {
        return this;
    }

    /**
     * 路径更改
     *
     * @param currentPath
     */
    public void pathChange(String currentPath) {
        slcBaseFileDelegate.pathChange(currentPath);
    }

    @Override
    public void setFunctionsForFragment(SlcBaseFileListView fragment) {
        slcBaseFileDelegate.setFunctionsForFragment(fragment);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //通过requestCode来识别是否同一个请求
        slcBaseFileDelegate.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        slcBaseFileDelegate.onDestroy();
        super.onDestroy();
    }
}
