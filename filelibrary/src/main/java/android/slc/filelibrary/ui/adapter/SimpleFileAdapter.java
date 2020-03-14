package android.slc.filelibrary.ui.adapter;

import android.content.Context;
import android.slc.filelibrary.ui.adapter.base.BaseViewHolder;

import androidx.annotation.NonNull;

import android.slc.filelibrary.R;
import android.slc.filelibrary.entity.BaseFile;
import android.slc.filelibrary.entity.CommonlyFile;
import android.slc.filelibrary.utils.FileConstant;

import java.util.List;

public class SimpleFileAdapter extends BaseFileAdapter<BaseFile> {
    public SimpleFileAdapter(@NonNull Context context, @NonNull List<BaseFile> date) {
        super(context, date, R.layout.slc_item_file_content);
    }

    @Override
    protected void convert(BaseViewHolder helper, BaseFile item) {
        helper.setText(R.id.tv_slc_title, item.getFile().getName());
        if (item.isIsDirectory() || !(item instanceof CommonlyFile)) {
            helper.setText(R.id.tv_slc_sub_title, item.getLastModifiedTime());
            helper.setImageResource(R.id.iv_icon, R.drawable.ic_slc_folder);
        } else {
            CommonlyFile commonlyFile = (CommonlyFile) item;
            helper.setText(R.id.tv_slc_sub_title, item.getLastModifiedTime() + "  " + commonlyFile.getFileSize());
            helper.setImageResource(R.id.iv_icon, FileConstant.MediaType.getIconByMediaType(commonlyFile.getExtension()));
        }
    }
}
