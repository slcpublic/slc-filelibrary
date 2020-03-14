package android.slc.filelibrary.entity;

import android.slc.filelibrary.ui.adapter.base.SlcBaseAdapter;
import android.slc.filelibrary.utils.SlcFileUtils;

import java.io.File;
import java.io.Serializable;

public class BaseFile implements SlcBaseAdapter.ItemEntity, Serializable {
    protected File mFile;//文件
    protected String mPath;//文件的绝对路径
    protected boolean mIsDirectory;//是否是文件夹
    private String mLastModifiedTime;

    public BaseFile(File file) {
        this.mFile = file;
        mIsDirectory = this.mFile.isDirectory();
        mPath = mFile.getPath();
        mLastModifiedTime = SlcFileUtils.getFileLastModifiedFormat(mFile);
    }

    public File getFile() {
        return mFile;
    }

    public void setFile(File file) {
        this.mFile = file;
    }

    public String getPath() {
        return mPath;
    }

    public boolean isIsDirectory() {
        return mIsDirectory;
    }

    public String getLastModifiedTime() {
        return mLastModifiedTime;
    }

    @Override
    public int getViewType() {
        return 0;
    }
}
