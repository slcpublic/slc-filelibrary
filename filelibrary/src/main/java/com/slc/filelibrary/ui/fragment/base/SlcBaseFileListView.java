package com.slc.filelibrary.ui.fragment.base;

import com.slc.filelibrary.entity.BaseFile;
import com.slc.filelibrary.ui.utils.Functions;

import java.util.List;

public interface SlcBaseFileListView {
    void onPostExecute(List<BaseFile> fileListData);

    void setFunctions(Functions functions);
}
