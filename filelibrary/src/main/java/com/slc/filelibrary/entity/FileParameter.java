package com.slc.filelibrary.entity;

import android.os.Environment;

import com.slc.filelibrary.filter.CompositeFilter;
import com.slc.filelibrary.filter.HiddenFilter;
import com.slc.filelibrary.filter.PatternFilter;

import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileParameter implements Serializable {
    private String mStartPath;
    private Pattern mPattern;
    private FileFilter mFileFilter;
    private CompositeFilter mCompositeFilter;
    private boolean mIsShowHiddenFile;
    private boolean mDirectoriesFilter;

    public FileParameter() {
        mStartPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        //mPattern = Pattern.compile(".+(.doc|.docx)$");
    }

    public void setFileFilter(FileFilter fileFilter) {
        this.mFileFilter = fileFilter;
    }

    public FileFilter getFileFilter() {
        return mFileFilter;
    }

    public void setPattern(Pattern pattern) {
        this.mPattern = pattern;
    }

    public Pattern getPattern() {
        return mPattern;
    }

    public void setStartPath(String startPath) {
        this.mStartPath = startPath;
    }

    public String getStartPath() {
        return mStartPath;
    }

    public CompositeFilter getCompositeFilter() {
        if (mCompositeFilter == null) {
            List<FileFilter> fileFilterList = new ArrayList<>();
            if (!mIsShowHiddenFile) {
                fileFilterList.add(new HiddenFilter());
            }
            PatternFilter patternFilter = new PatternFilter(mPattern, mDirectoriesFilter);
            fileFilterList.add(patternFilter);
            if (mFileFilter != null) {
                fileFilterList.add(mFileFilter);
            }
            mCompositeFilter = new CompositeFilter(fileFilterList);
        }
        return mCompositeFilter;
    }

    public boolean isIsShowHiddenFile() {
        return mIsShowHiddenFile;
    }

    public void setIsShowHiddenFile(boolean isShowHintFile) {
        this.mIsShowHiddenFile = isShowHintFile;
    }

    public void setDirectoriesFilter(boolean directoriesFilter) {
        this.mDirectoriesFilter = directoriesFilter;
    }

    public boolean isDirectoriesFilter() {
        return mDirectoriesFilter;
    }
}
