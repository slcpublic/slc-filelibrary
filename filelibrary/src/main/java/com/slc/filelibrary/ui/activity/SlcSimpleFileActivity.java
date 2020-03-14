package com.slc.filelibrary.ui.activity;


import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.slc.filelibrary.R;
import com.slc.filelibrary.entity.FileParameter;
import com.slc.filelibrary.ui.activity.base.SlcBaseFileActivity;
import com.slc.filelibrary.ui.fragment.SlcSimpleFileFragment;
import com.slc.filelibrary.utils.FragmentHelper;

public class SlcSimpleFileActivity extends SlcBaseFileActivity {
    protected Toolbar toolbar;

    @LayoutRes
    protected int getContentView() {
        return R.layout.slc_activity_base_file;
    }

    /**
     * 绑定视图
     *
     * @param savedInstanceState
     */
    protected void bindView(@Nullable Bundle savedInstanceState) {
        toolbar = findViewById(R.id.toolbar);
        initTitleTextView();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 初始化titleTextView
     */
    protected void initTitleTextView() {
        String titleFind = "titleFind";
        toolbar.setTitle(titleFind);
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof AppCompatTextView) {
                AppCompatTextView textView = (AppCompatTextView) view;
                if (titleFind == textView.getText()) {
                    textView.setEllipsize(TextUtils.TruncateAt.START);
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), toolbar.getTitle(), Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }
            }
        }
    }

    /**
     * 初始化数据
     */
    @Override
    public void initDate() {
        super.initDate();
        showFileFragment(slcBaseFileDelegate.getCurrentPath(), slcBaseFileDelegate.getFileParameter());
    }


    /**
     * 路径更改
     *
     * @param currentPath
     */
    @Override
    public void pathChange(String currentPath) {
        super.pathChange(currentPath);
        toolbar.setTitle(currentPath);
    }

    /**
     * 获取文件浏览器Fragment
     *
     * @return
     */
    protected SlcSimpleFileFragment getFileFragment() {
        return new SlcSimpleFileFragment();
    }

    /**
     * 显示文件浏览器fragment
     */
    protected void showFileFragment(String currentPath, FileParameter fileParameter) {
        SlcSimpleFileFragment.showDirectoryFragment(getSupportFragmentManager(),
                SlcSimpleFileFragment.getDirectoryFragment(getFileFragment(), currentPath, fileParameter));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // 处理返回逻辑
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        if (!FragmentHelper.back(this)) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }
}
