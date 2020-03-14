package android.slc.filelibrary.ui.activity.base;


import android.slc.filelibrary.ui.fragment.base.SlcBaseFileListView;

import androidx.appcompat.app.AppCompatActivity;

public interface SlcBaseFileView {
    void setFunctionsForFragment(SlcBaseFileListView slcBaseFileListView);
    void initDate();
    AppCompatActivity getAppCompatActivity();
    void pathChange(String currentPath);
}
