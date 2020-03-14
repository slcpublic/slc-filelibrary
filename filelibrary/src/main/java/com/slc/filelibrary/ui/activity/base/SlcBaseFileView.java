package com.slc.filelibrary.ui.activity.base;


import androidx.appcompat.app.AppCompatActivity;

import com.slc.filelibrary.ui.fragment.base.SlcBaseFileListView;

public interface SlcBaseFileView {
    void setFunctionsForFragment(SlcBaseFileListView slcBaseFileListView);
    void initDate();
    AppCompatActivity getAppCompatActivity();
    void pathChange(String currentPath);
}
