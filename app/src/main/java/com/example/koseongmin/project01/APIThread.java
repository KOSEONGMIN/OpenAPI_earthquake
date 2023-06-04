package com.example.koseongmin.project01;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;

public class APIThread extends Thread {
    private ArrayList<String> lon = new ArrayList<String>();
    private ArrayList<String> lat = new ArrayList<String>();
    private ArrayList<String> vt_acmdfclty_nm = new ArrayList<String>();
    private ArrayList<String> dtl_adres = new ArrayList<String>();
    private Double userLat;
    private Double userLon;

    public APIThread(Double userLat, Double userLon){
        super();
        this.userLat = userLat;
        this.userLon = userLon;
    }


    public void run() {
        try {
            final int STEP_NONE = 0 ;
            final int STEP_LON = 1 ;
            final int STEP_LAT = 2 ;
            final int STEP_NAME = 3 ;
            final int STEP_ADDRESS = 4 ;

            String url = "http://api.vworld.kr/req/data?service=data&request=GetFeature&data=LT_P_EDRSE002" +
                    "&key=F7E8FBF4-8507-3D6B-981D-80944E8FACC5" +
                    "&domain=http://api.vworld.kr/req/data&format=xml" +
                    "&geomFilter=BOX(10,10,200,200)" +
                    "&attrFilter=lat:between:" + (userLat-0.045) + "," + (userLat+0.045) + "|lon:between:" + (userLon-0.045) + "," + (userLon+0.045) +
                    "&size=100" +
                    "&columns=lat,lon,shel_nm,shel_ad";
            URL urlText = new URL(url);

                /*AssetManager am = getResources().getAssets();
                InputStream is = null;

                is = am.open("earthquake.xml");
                assets 폴더의 earthquake.xml 파일을 참조하려고 할때 썼다가 이젠 필요없네ㅎㅎ   */

            XmlPullParserFactory parserCreator = XmlPullParserFactory  //xmlpullparser객체를 생성하기위해 팩토리클래스의
                    .newInstance();            //newinstance메소드를 이용하여 newpullparset메소드를 통해 객체를 얻어온다

            //xml 파싱하기 위해서 풀 파서 쓴다.
            XmlPullParser parser = parserCreator.newPullParser(); // XMLPullParser 사용

            parser.setInput(urlText.openStream(), "UTF-8");   // 파싱하기위해서 스트림을 열어야한다. 이부분 수정#####################

            int eventType = parser.getEventType();  // 파싱할 데이터의 타입을 알려준다.


            int step = STEP_NONE;

            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                    // XML 데이터 시작
                } else if (eventType == XmlPullParser.START_TAG) {
                    String startTag = parser.getName() ;
                    if (startTag.equals("lon")) {
                        step = STEP_LON ;
                    } else if (startTag.equals("lat")) {
                        step = STEP_LAT ;
                    } else if (startTag.equals("shel_nm")) {
                        step = STEP_NAME ;
                    } else if (startTag.equals("shel_ad")) {
                        step = STEP_ADDRESS ;
                    } else {
                        step = STEP_NONE ;
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    String endTag = parser.getName() ;
                    if ((endTag.equals("xcord") && step != STEP_LON) ||
                            (endTag.equals("ycord") && step != STEP_LAT) ||
                            (endTag.equals("vt_acmdfclty_nm") && step != STEP_NAME) ||
                            (endTag.equals("dtl_adres") && step != STEP_ADDRESS))
                    {
                        // TODO : error
                    }
                    step = STEP_NONE ;
                } else if (eventType == XmlPullParser.TEXT) {
                    String text = parser.getText() ;
                    if (step == STEP_LON) {
                        lon.add(text);
                    } else if (step == STEP_LAT) {
                        lat.add(text) ;
                    } else if (step == STEP_NAME) {
                        vt_acmdfclty_nm.add(text);
                    } else if (step == STEP_ADDRESS) {
                        dtl_adres.add(text);
                    }
                }

                eventType = parser.next();
            }

            //

        } catch (Exception e) {
            Log.e("dd", "Error in network call", e);
            e.printStackTrace();
        }


    }

    public ArrayList<String> getLon() {
        return lon;
    }

    public ArrayList<String> getLat() {
        return lat;
    }

    public ArrayList<String> getVt_acmdfclty_nm() {
        return vt_acmdfclty_nm;
    }

    public ArrayList<String> getDtl_adres() {
        return dtl_adres;
    }
}
