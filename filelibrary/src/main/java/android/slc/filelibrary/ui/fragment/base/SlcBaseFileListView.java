package android.slc.filelibrary.ui.fragment.base;

import android.slc.filelibrary.entity.BaseFile;
import android.slc.filelibrary.ui.utils.Functions;

import java.util.List;

public interface SlcBaseFileListView {
    void onPostExecute(List<BaseFile> fileListData);

    void setFunctions(Functions functions);
}
