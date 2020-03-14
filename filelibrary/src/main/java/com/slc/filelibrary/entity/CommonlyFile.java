package com.slc.filelibrary.entity;

import com.slc.filelibrary.utils.SlcFileUtils;

import java.io.File;

public class CommonlyFile extends BaseFile {
    private String mFileSize;
    private String mExtension;

    public CommonlyFile(File file) {
        super(file);
        mFileSize = SlcFileUtils.getFileSize(mFile);
        mExtension = SlcFileUtils.getFileExtension(mFile);
        if(mExtension.length()>0){
            mExtension = mExtension.toLowerCase();
        }
    }

    public String getFileSize() {
        return mFileSize;
    }

    public synchronized String getExtension() {
        return mExtension;
    }
}
