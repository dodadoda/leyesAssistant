package com.hehao.hehaolibrary.http;


import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hehao.hehaolibrary.util.HHLog;
import com.hehao.hehaolibrary.view.LoadingDialog;
import java.util.Map;

/**
 * 网络请求基类，不能直接调用，只能被继承
 * @author HeHao
 * @time 2015/11/5 11:02
 * @email 139940512@qq.com
 */
public class HHHttpClient {
    /**
     * 1、创建一个RequestQueue
     * 2、创建一个StringRequest
     * 3、将StringRequest加入到RequestQueue中去
     */
    private static final int    TIME_OUT = 60*1000;     //超时连接时间

    private Context             context;        //上下文参数
    private HHHttpListener      listener;       //监听器
    private LoadingDialog       dialog ;        //友好提示框框

    private static RequestQueue queue;          //请求队列


    /**
     * 构造函数
     * @param context   上下文参数
     */
    public HHHttpClient(Context context){
        if(context==null){
            HHLog.e("HHHttpClient init error!");
            return;
        }
        this.context = context;

        if(queue == null)
            queue = Volley.newRequestQueue(context);
    }

    /**设置请求回调监听器*/
    public void setHttpListener(HHHttpListener listener) {
        this.listener = listener;
    }

    /**
     * 不带提示框的post请求方式
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     *
     * @see #sendPost(int, String, Map, boolean)
     */
    public void sendPost(int requestCode, String url, Map<String, String> params){
        this.sendPost(requestCode, url, params, false);
    }

    /**
     * 发送post请求
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     * @param showDialog    是否展示友好提示框
     */
    public void sendPost(final int requestCode, String url,
                         final Map<String, String> params , final boolean showDialog){

        //友好提示界面
        if(showDialog)
            showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestSuccess(requestCode, s, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestError(requestCode, volleyError, "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };

        //设置超时连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,1,1.0f));

        queue.add(stringRequest);
    }

    /**
     * 可以定义请求头的post请求
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     * @param header        请求头
     * @param showDialog    是否展示友好提示框
     */
    public void sendPost(final int requestCode, String url, final Map<String, String> params ,
                         final Map<String, String> header, final boolean showDialog){

        //友好提示界面
        if(showDialog)
            showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestSuccess(requestCode, s, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestError(requestCode, 0x100, "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        //设置超时连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,1,1.0f));

        queue.add(stringRequest);
    }

    /**
     * 不带参数GET请求
     * @param requestCode   请求代码
     * @param url           请求地址
     */
    public void sendGet(final int requestCode, String url){
        //友好提示界面
        showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                hideDialog();
                if(listener != null)
                    listener.onRequestSuccess(requestCode, s, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                hideDialog();
                if(listener != null)
                    listener.onRequestError(requestCode, 0x100, "error");
            }
        });
        //设置超时连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,1,1.0f));

        queue.add(stringRequest);
    }

    /**
     * 发送GET请求，不带提示框
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     * @see #sendGet(int, String, Map, boolean)
     */
    public void sendGet(int requestCode, String url, Map<String, String> params){
        this.sendGet(requestCode, url, params, false);
    }

    /**
     * 发送GET请求
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     * @param showDialog    是否需要提示框
     */
    public void sendGet(final int requestCode, String url, final Map<String, String> params,final boolean showDialog){
        //友好提示界面
        if(showDialog)
            showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestSuccess(requestCode, s, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestError(requestCode, 0x100, "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }
        };
        //设置超时连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,1,1.0f));

        queue.add(stringRequest);
    }

    /**
     * 可定义请求头的GET请求
     * @param requestCode   请求码
     * @param url           请求地址
     * @param params        请求参数
     * @param header        请求头
     * @param showDialog    是否需要提示框
     */
    public void sendGet(final int requestCode, String url, final Map<String, String> params,
                        final Map<String, String> header, final boolean showDialog){
        //友好提示界面
        if(showDialog)
            showDialog();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestSuccess(requestCode, s, "success");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                if(showDialog)
                    hideDialog();
                if(listener != null)
                    listener.onRequestError(requestCode, 0x100, "error");
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return header;
            }
        };
        //设置超时连接
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(TIME_OUT,1,1.0f));

        queue.add(stringRequest);
    }

    /**展示加载框框*/
    private void showDialog(){
        if(dialog == null)
            dialog = LoadingDialog.getInstance(context);
        dialog.showLoadingDialog();
    }

    /**隐藏加载框框*/
    private void hideDialog(){
        if(dialog != null)
            dialog.dismissLoadingDialog();
    }
}
