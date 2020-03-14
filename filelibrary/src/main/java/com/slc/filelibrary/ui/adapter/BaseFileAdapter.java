package com.slc.filelibrary.ui.adapter;

import android.content.Context;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.slc.filelibrary.entity.BaseFile;
import com.slc.filelibrary.ui.adapter.base.SlcBaseAdapter;

import java.util.List;

public abstract class BaseFileAdapter<T extends BaseFile> extends SlcBaseAdapter<T> {

    public BaseFileAdapter(@NonNull Context context, @NonNull List<T> date, @LayoutRes int layoutId) {
        super(context, date, layoutId);
    }

}
