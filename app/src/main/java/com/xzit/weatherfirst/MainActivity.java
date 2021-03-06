package com.xzit.weatherfirst;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkland.sdk.android.DataCallBack;
import com.thinkland.sdk.android.JuheData;
import com.thinkland.sdk.android.Parameters;
import com.xzit.weatherfirst.bean.QuestionBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private  List<QuestionBean>  questionBeanList =new ArrayList<>();
    private Context mContext;
    private TextView tv;
    private String radioAnswer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getJson();
        //initData();
    }

    private void initView() {
        viewPager=(ViewPager)findViewById(R.id.view_pager);
    }

    private void initData() {
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                Log.e("-->",""+questionBeanList.size());
                return questionBeanList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view=View.inflate(getApplicationContext(),R.layout.question,null);
                ImageView img=(ImageView)view.findViewById(R.id.imge);
                TextView question=(TextView)view.findViewById(R.id.question);
                RadioButton item1=(RadioButton)view.findViewById(R.id.item1);
                RadioButton item2=(RadioButton)view.findViewById(R.id.item2);
                RadioButton item3=(RadioButton)view.findViewById(R.id.item3);
                RadioButton item4=(RadioButton)view.findViewById(R.id.item4);
                TextView answer=(TextView)view.findViewById(R.id.answer);
                TextView explains=(TextView)view.findViewById(R.id.explains);
                img.setImageURI(Uri.parse(questionBeanList.get(position).getUrl()));
                question.setText(questionBeanList.get(position).getQuestion());
                item1.setText(questionBeanList.get(position).getItem1());
                item2.setText(questionBeanList.get(position).getItem2());
                item3.setText(questionBeanList.get(position).getItem3());
                item4.setText(questionBeanList.get(position).getItem4());
                answer.setText(questionBeanList.get(position).getAnswer());
                explains.setText(questionBeanList.get(position).getExplains());
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
    }

    private void getJson() {
      //  tv=(TextView)findViewById(R.id.tv);
        mContext=this;
        //??????????????????
        Parameters params=new Parameters();
        params.add("subject","1");
        params.add("model","c1");
        params.add("testType","rand");
        /**
         * ??????????????? ??????:
         * ??????????????? ???????????????context
         * ??????????????? ??????id
         * ??????????????? ???????????????url
         * ??????????????? ?????????????????????
         * ??????????????? ?????????????????????,?????????com.thinkland.sdk.android.Parameters??????;
         * ??????????????? ?????????????????????,com.thinkland.sdk.android.DataCallBack;
         *
         */
        JuheData.executeWithAPI(mContext, 183, "http://api2.juheapi.com/jztk/query",
                JuheData.GET, params, new DataCallBack() {
                    @Override
                    public void onSuccess(int i, String s) {
                        try {
                            //??????json
                            JSONObject  json=new JSONObject(s);
                            //????????????
                            JSONArray jsonArray=json.getJSONArray("result");
                            int length=jsonArray.length();
                           // tv.append("??????"+length);
                            //??????
                           for(int j=0;j<jsonArray.length();j++)   //i???j
                            {
                               JSONObject object=jsonArray.getJSONObject(j);  //??????i,j
//                              tv.append("question:"+object.getString("question")+" explains:"
//                                      +object.getString("explains")+"\n");
                                QuestionBean question=new QuestionBean(object.getString("url")
                                ,object.getString("question"),object.getString("item1"),object.getString("item2")
                                ,object.getString("item3"),object.getString("item4"),object.getString("answer")
                                ,object.getString("explains"));
                                questionBeanList.add(question);
                            }
                           // Log.e("-->",questionBeanList.size()+"??????");
                            //???ViewPager???????????????
                            viewPager.setAdapter(new PagerAdapter() {
                                @Override
                                public int getCount() {
                                    //Log.e("-->",""+questionBeanList.size());
                                    return questionBeanList.size();
                                }

                                @Override
                                public boolean isViewFromObject(View view, Object object) {
                                    return view==object;
                                }

                                @Override
                                public Object instantiateItem(ViewGroup container, final int position) {
                                    //??????????????????
                                    View view=View.inflate(getApplicationContext(),R.layout.question,null);
                                    //??????????????????????????? ??????view.findViewById??????????????????????????????
                                    //??????findViewById?????????setContentView???????????????
                                    //???????????????????????????Layout???????????????main.xml?????????<incloud layout="????????????"/>
                                    final TextView exTV=(TextView)view.findViewById(R.id.ex_tv);
                                    final TextView TOrF=(TextView)view.findViewById(R.id.TOrF);
                                    final ImageView img=(ImageView)view.findViewById(R.id.imge);
                                    TextView question=(TextView)view.findViewById(R.id.question);
                                    RadioGroup radioGroup=(RadioGroup)view.findViewById(R.id.rg);
                                    final RadioButton item1=(RadioButton)view.findViewById(R.id.item1);
                                    final RadioButton item2=(RadioButton)view.findViewById(R.id.item2);
                                    final RadioButton item3=(RadioButton)view.findViewById(R.id.item3);
                                    RadioButton item4=(RadioButton)view.findViewById(R.id.item4);
                                    final TextView answer=(TextView)view.findViewById(R.id.answer);
                                    final TextView explains=(TextView)view.findViewById(R.id.explains);
                                    //Log.e("-->",questionBeanList.get(position).getUrl());
                                   // Log.e("-->",questionBeanList.get(position).getQuestion());
//                                    img.setImageURI(Uri.parse(questionBeanList.get(position).getUrl()));
                                    //????????????
                                    if(questionBeanList.get(position).getUrl().equals("")) {
                                        //?????????????????????????????????????????????
                                       img.setVisibility(View.GONE);
                                    }else {
                                        img.setVisibility(View.VISIBLE);
                                        //????????????????????????
                                        new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                downloadBitmapFromServer(questionBeanList.get(position).getUrl(), img);
                                            }
                                        }).start();
                                    }
                                    //??????JSON????????????????????????
                                    question.setText(questionBeanList.get(position).getQuestion());
                                  //  System.out.println("sss" + questionBeanList.get(position).getQuestion());
                                    item1.setText(questionBeanList.get(position).getItem1());
                                    item2.setText(questionBeanList.get(position).getItem2());
                                    item3.setText(questionBeanList.get(position).getItem3());
                                    item4.setText(questionBeanList.get(position).getItem4());
                                    answer.setText(questionBeanList.get(position).getAnswer());
                                    explains.setText(questionBeanList.get(position).getExplains());
                                    //???????????? ????????????????????????
                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                        @Override
                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                            //checkedId??????????????????
                                           if(checkedId==item1.getId()) {
                                               radioAnswer="1";
                                           }else if(checkedId==item2.getId()) {
                                               radioAnswer="2";
                                           }else  if(checkedId==item3.getId()) {
                                               radioAnswer="3";
                                           }else {
                                               radioAnswer="4";
                                           }
                                            if(radioAnswer.equals(questionBeanList.get(position).getAnswer()))
                                            {
                                                TOrF.setText("????????????");
                                                TOrF.setTextColor(Color.DKGRAY);
                                                TOrF.setVisibility(View.VISIBLE);
                                            }
                                            else {
                                                TOrF.setText("????????????");
                                                TOrF.setTextColor(Color.RED);
                                                TOrF.setVisibility(View.VISIBLE);
                                                exTV.setVisibility(View.VISIBLE);
                                                explains.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    });
                                    //???view??????????????????
                                    container.addView(view);
                                    return view;
                                }

                                @Override
                                public void destroyItem(ViewGroup container, int position, Object object) {
                                    container.removeView((View) object);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFinish() {
                        Toast.makeText(getApplicationContext(), "finish",Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onFailure(int i, String s, Throwable throwable) {
                        tv.append(throwable.getMessage() + "\n");
                    }
                });
    }

    //??????Handler???????????????
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    //???url???????????????????????????
    public void downloadBitmapFromServer(String url, final ImageView imageView){
        imageView.setTag(url);
        InputStream is = null;
        try {
            //??????URL????????????
            URL httpUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
            conn.setRequestMethod("GET");
            if (conn.getResponseCode() == 200){
                //?????????????????????
                is =  conn.getInputStream();
                if (TextUtils.equals(url, imageView.getTag().toString())){
                    //??????Bitmap??????
                    final Bitmap bitmap = BitmapFactory.decodeStream(is);
                    //Handler????????????
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            //???imagView????????????
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                //???????????????
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
