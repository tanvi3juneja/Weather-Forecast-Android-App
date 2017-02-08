package iberry.com.example.hw9;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class DetailActivity extends AppCompatActivity {

    JSONObject JSON;
    String dValue1;
    String state;
    String city;
    CallbackManager callbackManager;
    ShareLinkContent linkContent;
    JSONObject parent_obj;
    String s;
    long temp;
    String deg;
    String summ_icon;
    String lat, lng;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        final ShareDialog shareDialog = new ShareDialog(this);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView temperature = (TextView) findViewById(R.id.textView2);
        TextView summary = (TextView) findViewById(R.id.textView1);
        TextView ws=(TextView)findViewById(R.id.textView13);
        TextView dp=(TextView)findViewById(R.id.textView14);
        TextView vis=(TextView)findViewById(R.id.textView16);
        TextView lh=(TextView)findViewById(R.id.textView3);
        TextView rain_chance=(TextView)findViewById(R.id.textView12);
        TextView hum=(TextView)findViewById(R.id.textView15);
        TextView prec=(TextView)findViewById(R.id.textView);
        ImageView img=(ImageView) findViewById(R.id.summary);
        TextView sr=(TextView) findViewById(R.id.textView17);
        TextView ss=(TextView) findViewById(R.id.textView18);
        try {

            String wind_units;
            String vis_units;
            JSON = new JSONObject(getIntent().getStringExtra("json"));
            dValue1=getIntent().getStringExtra("degree");
            state= getIntent().getExtras().getString("State");
            city= getIntent().getExtras().getString("city");
            parent_obj = JSON.getJSONObject("currently");
            lat = JSON.getString("latitude");
            lng = JSON.getString("longitude");
            //String tz=random.getString("timezone");

            String icon=parent_obj.getString("icon");
            if(icon .equals("clear-day")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.clear));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/clear.png";
            }
            else if(icon.equals("clear-night")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.clear_night));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/clear_night.png";
            }
            else if(icon.equals("rain")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.rain));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/rain.png";
            }
            else if(icon.equals("snow")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.snow));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/snow.png";
            }
            else if(icon.equals("sleet")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.sleet));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/sleet.png";
            }
            else if(icon.equals("wind")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.wind));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/wind.png";
            }
            else if(icon.equals("fog")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.fog));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/fog.png";
            }
            else if(icon.equals("cloudy")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.cloudy));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/cloudy.png";
            }
            else if(icon.equals("partly-cloudy-day")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.cloud_day));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_day.png";
            }
            else if(icon.equals("partly-cloudy-night")) {
                img.setImageDrawable(getResources().getDrawable(R.drawable.cloud_night));
                summ_icon = "http://cs-server.usc.edu:45678/hw/hw8/images/cloud_night.png";
            }

            s =parent_obj.getString("summary");
            summary.setText(s + " in " + getIntent().getExtras().getString("city") + ", "+ getIntent().getExtras().getString("State"));
            String p =parent_obj.getString("precipIntensity");
            double pi=Double.parseDouble(p);
            String x;
            if(dValue1.equals("F"))
            {
            if (pi<0.002)
            x="None";
            else if (pi<0.017)
                x="Very Light";
            else if (pi<0.1)
                x="Light";
            else if (pi<0.4)
                x="Moderate";
            else
            x="Heavy";
            deg="F";
                wind_units="mph";
                vis_units="mi";
            }
            else
            {
                if (pi<0.0508)
                    x="None";
                else if (pi<0.4318)
                    x="Very Light";
                else if (pi<2.54)
                    x="Light";
                else if (pi<10.16)
                    x="Moderate";
                else
                    x="Heavy";
                deg="C";
                wind_units="m/s";
                vis_units="km";
            }
            prec.setText(x);
            temp = Math.round(Double.parseDouble(parent_obj.getString("temperature")));
            temperature.setText(Html.fromHtml(temp +"<sup><small>" +(char) 0x00B0 + deg+"</small></sup>"));
            String cof=parent_obj.getString("precipProbability");
            int cr= Integer.parseInt(cof);
            int chance_of_rain=cr*100;
            rain_chance.setText(chance_of_rain + "%");
            String h = parent_obj.getString("humidity");
            float hu= Float.parseFloat(h);
            int h1=Math.round(hu);
            int humid=h1*100;
            hum.setText(humid + "%");
            String wind_speed= parent_obj.getString("windSpeed");
            ws.setText(wind_speed + wind_units);
            String dew = Math.round(Double.parseDouble(parent_obj.getString("dewPoint"))) + "";
            //String dew =parent_obj.getString("dewPoint");
            dp.setText(dew + (char) 0x00B0 + deg);
            String visibility=parent_obj.getString("visibility");
            vis.setText(visibility + vis_units);
            JSONObject daily = JSON.getJSONObject("daily");
            JSONArray arr =daily.getJSONArray("data");
            JSONObject jd=arr.getJSONObject(0);
            String tmin=jd.getString("temperatureMin");
            float mint=Float.parseFloat(tmin);
            int min_temp=Math.round(mint);
            String tmax=jd.getString("temperatureMax");
            float maxt=Float.parseFloat(tmax);
            int max_temp=Math.round(maxt);
            lh.setText("L:"+ min_temp + (char) 0x00B0 + " | " + "H:" + max_temp + (char) 0x00B0);
            String sunrise=jd.getString("sunriseTime");
            String sunset=jd.getString("sunsetTime");
            sr.setText(sunrise);
            ss.setText(sunset);
        } catch (Exception e) {
           e.printStackTrace();
       }

        Button button1 = (Button) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),Main2Activity.class);
                intent.putExtra("json", JSON.toString());
                intent.putExtra("city", city);
                intent.putExtra("State", state);
                intent.putExtra("degree", dValue1);
                startActivity(intent);
            }
        });

        Button button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MapViewActivity.class);
                intent.putExtra("lat",lat);
                intent.putExtra("lng", lng);
                startActivity(intent);
            }
        });



        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
            @Override
            public void onSuccess(Sharer.Result shareResult) {
                // App code
                Log.d("Success", "Share");
                Toast.makeText(DetailActivity.this, "Facebook Post Successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                // App code
                Log.d("Failed", "Share");
                Toast.makeText(DetailActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(DetailActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        Button button3 = (Button) findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("STEP", "1");
                Log.d("STEP", "2");
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    linkContent = new ShareLinkContent.Builder()
                            .setContentTitle("Current Weather in " + city + "," + state)
                            .setContentUrl(Uri.parse("http://forecast.io"))
                            .setContentDescription(
                                    s + ", " + Html.fromHtml(temp + "<sup><small>" + (char) 0x00B0 + deg + "</small></sup>"))
                            .setImageUrl(Uri.parse(summ_icon))
                            .build();

                    shareDialog.show(linkContent);
                } else
                    Log.d("FATAL", "Share");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
