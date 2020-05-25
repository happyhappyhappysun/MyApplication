package com.example.notificationservicetest;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.webkit.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import javax.xml.parsers.SAXParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownLoadTask extends AsyncTask<String,Integer,Integer> {
    //定义四个常量来标志下载结果(因为该异步任务的结果接收的是Integer类型的，所以这里需要定义好结果)
    public static final int TYPE_SUCCESS =0;
    public static final int TYPE_FAILED =1;
    public static final int TYPE_PAUSED =2;
    public static final int TYPE_CANCELED =3;
    public boolean isCancelled = false;
    public boolean isPaused = false;
    private DownLoadListener listener;
    private int lastProgress;

    public DownLoadTask(DownLoadListener listener) {
        this.listener = listener;
    }

    /**
     * 执行下载任务的核心代码，在子线程中进行
     * @param strings
     * @return
     */
    @Override
    protected Integer doInBackground(String... strings) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        long downloadLength = 0;
        String downloadUrl = strings[0];
        String filename = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
        String directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
        Log.d("notification",directory+filename);
        File file = new File(directory+filename);
        if(file.exists()){
            downloadLength = file.length();
        }
        long contentLength = 0;
        try {
            contentLength = getContentLength(downloadUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(contentLength==downloadLength){
            return TYPE_SUCCESS;
        }else if(contentLength==0){
            return TYPE_FAILED;
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().addHeader("RANGE","bytes="+downloadLength+"-").url(downloadUrl).build();

        try {
            Response response = client.newCall(request).execute();
            if(response!=null){
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength);//跳过已经下载的内容
                byte[] b = new byte[1024];
                int total = 0;
                int len;//每次读的文字个数
                while ((len=is.read(b))!=-1){
                    if(isCancelled){
                        return TYPE_CANCELED;
                    }else if(isPaused){
                        return TYPE_PAUSED;
                    }else{
                        total+=len;
                        savedFile.write(b,0,len);
                        //计算进度并且进行显示
                        int progress = (int) ((total+downloadLength)*100/contentLength);
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
            if(is!=null){
                    is.close();
                }

            if(savedFile!=null){
                savedFile.close();
            }
            if(isCancelled && file!=null){
                file.delete();
            }
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return TYPE_FAILED;
    }

    /**
     * 获取要下载的内容的具体长度
     * @param downloadUrl
     * @return
     */
    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(downloadUrl).build();
        Response response = client.newCall(request).execute();
        if(response!=null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            response.body().close();
            return contentLength;
        }
        return 0;
    }

    public void pauseDownLoad() {
        isPaused = true;
    }

    public void cancelDownLoad() {
        isCancelled=true;
    }

    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            case TYPE_PAUSED:
                listener.onPause();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        //listener.onProcess(progress);
        //这里要是不这样写的话就没法实现暂停功能，其他的按钮就不能按了
        if(progress>lastProgress){
            listener.onProcess(progress);
            lastProgress = progress;
        }

    }
}
