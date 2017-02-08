package iberry.com.example.hw9;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class Main2Activity extends AppCompatActivity {


    JSONArray data;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    ViewFlipper vf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = (TextView) findViewById(R.id.textView2);
        textView.setText(getIntent().getExtras().getString("city") + " ," + getIntent().getExtras().getString("State"));
        TextView row1 = (TextView) findViewById(R.id.textView5);
        String s = getIntent().getExtras().getString("degree");
        String units;
        if (s.equals("F")) {
            row1.setText("Temp(" + (char) 0x00B0 + "F)");
            units = "F";
        }

        else

        {

            row1.setText("Temp(" + (char) 0x00B0 + "C)");
            units = "C";
        }
        try {
            JSONObject JSON = new JSONObject(getIntent().getStringExtra("json"));

            JSONObject hour = JSON.getJSONObject("hourly");
             data = hour.getJSONArray("data");

            for (int i = 1; i < 25; i++) {

                // String main12 = time1.getString("icon");
                //int imageResource = getResources().getIdentifier(main12, null, getPackageName());
                // Drawable image = getResources().getDrawable(imageResource);
                // roww.setImageDrawable(image);
                //or
                // roww.setImageDrawable(getResources().getDrawable(R.drawable.clear_night));

                JSONObject time1 = data.getJSONObject(i);
                String main11 = time1.getString("time");
                String main12 = time1.getString("icon");

                int n = i + 5;
                String r1c1 = "textView" + n;
                int txt1 = getResources().getIdentifier(r1c1, "id", getPackageName());
                TextView row11 = (TextView) findViewById(txt1);
                String r1c2 = "imageView" + i;
                int txt2 = getResources().getIdentifier(r1c2, "id", getPackageName());
                ImageView row12 = (ImageView) findViewById(txt2);
                // ImageView row22 = (ImageView) findViewById(R.id.imageView2);
                //switchcase(main12);
                //String main13 = time1.getString("temperature");

                String main13 = Math.round(Double.parseDouble(time1.getString("temperature"))) + "";

                //String main13 = time1.getString("temperature");

                String r1c3 = "text1View" + i;
                int txt3 = getResources().getIdentifier(r1c3, "id", getPackageName());
                TextView row13 = (TextView) findViewById(txt3);
                // int myNum = Integer.parseInt(main13);
                row11.setText(main11);

                if (main12.equals("clear-day")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.clear));
                } else if (main12.equals("clear-night")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.clear_night));
                } else if (main12.equals("rain")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                } else if (main12.equals("snow")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                } else if (main12.equals("sleet")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.sleet));
                } else if (main12.equals("wind")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.wind));
                } else if (main12.equals("fog")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                } else if (main12.equals("cloudy")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                } else if (main12.equals("partly-cloudy-day")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.cloud_day));
                } else if (main12.equals("partly-cloudy-night")) {
                    row12.setImageDrawable(getResources().getDrawable(R.drawable.cloud_night));
                }

                //row12.setImageDrawable(getResources().getDrawable(R.drawable.clear));
                row13.setText(main13);

            }

            JSONObject daily = JSON.getJSONObject("daily");
            for (int j = 1; j <=7; j++) {
                JSONObject data = daily.getJSONArray("data").getJSONObject(j);
                String monthDate= data.getString("monthDate");
                String weekday=data.getString("weekday");
                String icon=data.getString("icon");
                String tMin=Math.round(Double.parseDouble(data.getString("temperatureMin")))+"";
                String tMax=Math.round(Double.parseDouble(data.getString("temperatureMax")))+"";String dayDate="<b>" +weekday+", "+monthDate+"</b><br><br>"+"Min:"+tMin+(char) 0x00B0+units+"| Max:"+tMax+(char) 0x00B0+units ;
                String tView = "text2View"+j;
                String iView = "image2View"+j;
                int id1 = getResources().getIdentifier(tView, "id", getPackageName());
                int id2 = getResources().getIdentifier(iView, "id", getPackageName());
                TextView row= (TextView) findViewById(id1);
                row.setText(Html.fromHtml(dayDate));
                ImageView roww= (ImageView) findViewById(id2);
                if(icon.equals("clear-day")) roww.setImageDrawable(getResources().getDrawable(R.drawable.clear));
                else if(icon.equals("clear-night")) roww.setImageDrawable(getResources().getDrawable(R.drawable.clear_night));
                else if (icon.equals("rain")) roww.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                else if (icon.equals("snow")) roww.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                else if (icon.equals("sleet")) roww.setImageDrawable(getResources().getDrawable(R.drawable.sleet));
                else if (icon.equals("wind")) roww.setImageDrawable(getResources().getDrawable(R.drawable.wind));
                else if (icon.equals("fog")) roww.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                else if (icon.equals("cloudy")) roww.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                else if (icon.equals("partly-cloudy-day")) roww.setImageDrawable(getResources().getDrawable(R.drawable.cloud_day));
                else if (icon.equals("partly-cloudy-night")) roww.setImageDrawable(getResources().getDrawable(R.drawable.cloud_night));
            }






        } catch (JSONException e) {

        }

        final Button button4 = (Button) findViewById(R.id.button4);
        final Button button5 = (Button) findViewById(R.id.button5);


        vf = (ViewFlipper) findViewById( R.id.viewFlipper );

        button4.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                button4.setBackgroundResource(R.drawable.mybutton);
                button5.setBackgroundResource(android.R.drawable.btn_default);
                if (vf.getDisplayedChild() == 0)
                    vf.showNext();
            }
        });

        button5.setOnClickListener(new View.OnClickListener(){
            public void onClick (View v){
                button5.setBackgroundResource(R.drawable.mybutton);;
                button4.setBackgroundResource(android.R.drawable.btn_default);
                if (vf.getDisplayedChild() == 1)
                    vf.showNext();
            }
        });
        final ImageView plus=(ImageView) findViewById(R.id.imageView25);

        plus.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

                //plus.setImageResource(android.R.color.transparent);
                plus.setBackgroundResource(0);
                for (int i = 25; i < 49; i++) {
                    try {
                        JSONObject time1 = data.getJSONObject(i);
                        String main11 = time1.getString("time");
                        String main12 = time1.getString("icon");

                        int n = i + 5;
                        String r1c1 = "textView" + n;
                        int txt1 = getResources().getIdentifier(r1c1, "id", getPackageName());
                        String tr = "tableRow" + i;
                        int tr1 = getResources().getIdentifier(tr, "id", getPackageName());
                        TextView row11 = (TextView) findViewById(txt1);
                        TableRow row111 = (TableRow) findViewById(tr1);
                        row111.setVisibility(View.VISIBLE);
                        String r1c2 = "imageView" + i;
                        int txt2 = getResources().getIdentifier(r1c2, "id", getPackageName());
                        ImageView row12 = (ImageView) findViewById(txt2);
                        String main13 = Math.round(Double.parseDouble(time1.getString("temperature"))) + "";
                        String r1c3 = "text1View" + i;
                        int txt3 = getResources().getIdentifier(r1c3, "id", getPackageName());
                        TextView row13 = (TextView) findViewById(txt3);
                        // int myNum = Integer.parseInt(main13);
                        row11.setText(main11);

                        if (main12.equals("clear-day")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.clear));
                        } else if (main12.equals("clear-night")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.clear_night));
                        } else if (main12.equals("rain")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                        } else if (main12.equals("snow")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                        } else if (main12.equals("sleet")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.sleet));
                        } else if (main12.equals("wind")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.wind));
                        } else if (main12.equals("fog")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                        } else if (main12.equals("cloudy")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                        } else if (main12.equals("partly-cloudy-day")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.cloud_day));
                        } else if (main12.equals("partly-cloudy-night")) {
                            row12.setImageDrawable(getResources().getDrawable(R.drawable.cloud_night));
                        }

                        row13.setText(main13);



                    }
                    catch (JSONException e)
                    {

                    }


                }


            }
        });


    }
}









