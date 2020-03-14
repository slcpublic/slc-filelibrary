package android.slc.filelibrary.ui.fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.slc.filelibrary.R;
import android.slc.filelibrary.entity.BaseFile;
import android.slc.filelibrary.entity.CommonlyFile;
import android.slc.filelibrary.entity.FileParameter;
import android.slc.filelibrary.ui.activity.base.SlcBaseFileDelegate;
import android.slc.filelibrary.ui.activity.base.SlcBaseFileView;
import android.slc.filelibrary.ui.adapter.BaseFileAdapter;
import android.slc.filelibrary.ui.adapter.SimpleFileAdapter;
import android.slc.filelibrary.ui.adapter.base.SlcBaseAdapter;
import android.slc.filelibrary.ui.fragment.base.SlcBaseFileListView;
import android.slc.filelibrary.ui.utils.Functions;
import android.slc.filelibrary.utils.FileComparator;
import android.slc.filelibrary.utils.FileConstant;
import android.slc.filelibrary.utils.FragmentHelper;
import android.slc.filelibrary.utils.SlcFileUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SlcSimpleFileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, SlcBaseFileListView,
        SlcBaseAdapter.OnItemClickListener, SlcBaseAdapter.OnItemChildLongClickListener {
    private String tag = "SlcSimpleFileFragment";
    private int contentId;
    protected Functions mFunctions;//方法
    protected SwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView recyclerView;
    protected BaseFileAdapter baseFileAdapter;
    protected FileParameter fileParameter;
    protected String filePath;
    protected ListFilesTask listFilesTask;
    protected List<BaseFile> fileListData = new ArrayList<>();

    public static SlcSimpleFileFragment getDirectoryFragment(SlcSimpleFileFragment slcSimpleFileFragment, String startPath,
                                                             FileParameter fileParameter) {
        if (slcSimpleFileFragment == null) {
            slcSimpleFileFragment = new SlcSimpleFileFragment();
        }
        Bundle bundle = slcSimpleFileFragment.getArguments();
        if (bundle == null) {
            bundle = new Bundle();
            slcSimpleFileFragment.setArguments(bundle);
        }
        bundle.putString(FileConstant.KEY_FILE_PATH, startPath);
        bundle.putSerializable(FileConstant.KEY_FILE_PARAMETER, fileParameter);
        return slcSimpleFileFragment;
    }

    public static void showDirectoryFragment(FragmentManager fragmentManager, SlcSimpleFileFragment slcSimpleFileFragment) {
        showDirectoryFragment(fragmentManager, slcSimpleFileFragment, R.id.cl_content);
    }

    public static void showDirectoryFragment(FragmentManager fragmentManager, SlcSimpleFileFragment slcSimpleFileFragment,
                                             @IdRes int contentId) {
        slcSimpleFileFragment.getArguments().putInt(FileConstant.KEY_CONTAINER, contentId);
        FragmentHelper.addFragment(fragmentManager, slcSimpleFileFragment, contentId);
    }

    /**
     * 设置方法
     *
     * @param functions
     */
    public void setFunctions(Functions functions) {
        this.mFunctions = functions;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getActivity() != null && getActivity() instanceof SlcBaseFileView) {
            ((SlcBaseFileView) getActivity()).setFunctionsForFragment(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.slc_fragment_directory, container, false);
        bindView(contentView, savedInstanceState);
        return contentView;
    }

    /**
     * 绑定视图
     *
     * @param contentView
     * @param savedInstanceState
     */
    protected void bindView(View contentView, Bundle savedInstanceState) {
        swipeRefreshLayout = contentView.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setOnRefreshListener(this);
        recyclerView = contentView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        initData();
    }

    /**
     * 初始化数据
     */
    private void initData() {
        contentId = getArguments().getInt(FileConstant.KEY_CONTAINER);
        filePath = getArguments().getString(FileConstant.KEY_FILE_PATH);
        fileParameter = (FileParameter) getArguments().getSerializable(FileConstant.KEY_FILE_PARAMETER);
        this.mFunctions.invokeFunc(SlcBaseFileDelegate.FUNCTION_NAME_PATH_REFRESH, filePath);
        swipeRefreshLayout.setRefreshing(true);
        refreshFileList();
    }

    /**
     * 刷新文件列表
     */
    protected void refreshFileList() {
        listFilesTask = new ListFilesTask(filePath, this);
        listFilesTask.execute(fileParameter);
    }

    @Override
    public void onRefresh() {
        fileListData.clear();
        refreshFileList();
    }

    /**
     * 接受传递过来的文件列表数据
     *
     * @param fileListData
     */
    @Override
    public void onPostExecute(List<BaseFile> fileListData) {
        this.fileListData.clear();
        this.fileListData.addAll(fileListData);
        if (baseFileAdapter == null) {
            baseFileAdapter = initAdapter();
            View headView = getLayoutInflater().inflate(R.layout.slc_item_file_content, null);
            headView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().onBackPressed();
                }
            });
            baseFileAdapter.addHeaderView(headView);
            baseFileAdapter.setOnItemClickListener(this);
            baseFileAdapter.setOnItemChildLongClickListener(this);
            recyclerView.setAdapter(baseFileAdapter);
        } else {
            baseFileAdapter.notifyDataSetChanged();
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    /**
     * 初始化adapter
     *
     * @return
     */
    protected BaseFileAdapter initAdapter() {
        return new SimpleFileAdapter(getContext(), fileListData);
    }

    @Override
    public void onItemClick(SlcBaseAdapter adapter, View view, int position) {
        BaseFile baseFile = SlcSimpleFileFragment.this.fileListData.get(position);
        if (baseFile.isIsDirectory()) {
            onItemDirectoryClick(baseFile, position);
        } else {
            onItemFileClick(baseFile, position);
        }
    }

    /**
     * 文件夹点击事件
     *
     * @param baseFile
     * @param position
     */
    protected void onItemDirectoryClick(BaseFile baseFile, int position) {
        showFileFragment(baseFile.getPath(), fileParameter);
    }

    /**
     * 文件点击事件
     *
     * @param baseFile
     * @param position
     */
    protected void onItemFileClick(BaseFile baseFile, int position) {
        // 对文件进行操作
    }

    @Override
    public boolean onItemChildLongClick(SlcBaseAdapter adapter, View view, int position) {
        return false;
    }

    /**
     * 获取文件DirectoryFragment
     *
     * @return
     */
    protected SlcSimpleFileFragment getFileFragment() {
        return new SlcSimpleFileFragment();
    }

    /**
     * 显示文件夹fragment
     */
    protected void showFileFragment(String currentPath, FileParameter fileParameter) {
        SlcSimpleFileFragment.showDirectoryFragment(getFragmentManager(),
                SlcSimpleFileFragment.getDirectoryFragment(getFileFragment(),
                        currentPath, fileParameter), contentId);
    }

    static class ListFilesTask extends AsyncTask<FileParameter, Void, List<BaseFile>> {
        private String filePath;
        private WeakReference<SlcBaseFileListView> directoryViewWeakReference;

        ListFilesTask(String filePath, SlcBaseFileListView directoryView) {
            this.filePath = filePath;
            directoryViewWeakReference = new WeakReference<>(directoryView);
        }

        @Override
        protected List<BaseFile> doInBackground(FileParameter... fileParameters) {
            FileParameter fileParameter = fileParameters[0];
            List<BaseFile> fileListData = new ArrayList<>();
            List<File> fileList = SlcFileUtils.listFilesInDirWithFilter(filePath, fileParameter.getCompositeFilter());
            Collections.sort(fileList, new FileComparator());
            for (File file : fileList) {
                if (file.isDirectory()) {
                    fileListData.add(new BaseFile(file));
                } else {
                    fileListData.add(new CommonlyFile(file));
                }
            }
            return fileListData;
        }

        @Override
        protected void onPostExecute(List<BaseFile> baseFiles) {
            super.onPostExecute(baseFiles);
            SlcBaseFileListView baseFilesView = directoryViewWeakReference.get();
            if (baseFilesView != null) {
                baseFilesView.onPostExecute(baseFiles);
            }
        }
    }

    @Override
    public void onDestroy() {
        if (listFilesTask != null && !listFilesTask.isCancelled()) {
            listFilesTask.cancel(true);
        }
        try {
            this.mFunctions.invokeFunc(SlcBaseFileDelegate.FUNCTION_NAME_PATH_REFRESH, new File(filePath).getParent());
        } catch (Exception e) {
            Log.i("onDestroy", "获取父类路径异常");
        }
        super.onDestroy();
        Log.i("onDestroy", "销毁");
    }
}
