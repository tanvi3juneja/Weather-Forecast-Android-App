package iberry.com.example.hw9;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spin= (Spinner) findViewById(R.id.spinState);

        List<String> states = new ArrayList<String>();
        states.add("Select");
        states.add("AB");
        states.add("AK");
        states.add("AZ");
        states.add("AR");
        states.add("CA");
        states.add("CO");
        states.add("CT");
        states.add("DE");
        states.add("DC");
        states.add("FL");
        states.add("GA");
        states.add("HI");
        states.add("ID");
        states.add("IL");
        states.add("IN");
        states.add("IA");
        states.add("KS");
        states.add("KY");
        states.add("LA");
        states.add("ME");
        states.add("MD");
        states.add("MA");
        states.add("MI");
        states.add("MN");
        states.add("MS");
        states.add("MO");
        states.add("MT");
        states.add("NE");
        states.add("NV");
        states.add("NH");
        states.add("NJ");
        states.add("NM");
        states.add("OH");
        states.add("OK");
        states.add("OR");
        states.add("PA");
        states.add("RI");
        states.add("SC");
        states.add("SD");
        states.add("TN");
        states.add("TX");
        states.add("UT");
        states.add("VT");
        states.add("VA");
        states.add("WA");
        states.add("WV");
        states.add("WI");
        states.add("WY");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, states);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spin.setAdapter(dataAdapter);

        RadioButton rbf= (RadioButton) findViewById(R.id.rbFahrenheit);
        rbf.setChecked(true);

        ImageView img = (ImageView) findViewById(R.id.iForecast);
        img.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent viewIntent =
                        new Intent("android.intent.action.VIEW",
                                Uri.parse("http://forecast.io/"));
                startActivity(viewIntent);
            }
        });

        Button bClear= (Button) findViewById(R.id.bClear);
        bClear.setOnClickListener(this);
        Button bSearch= (Button) findViewById(R.id.bSearch);
        bSearch.setOnClickListener(this);
        Button bAbout= (Button) findViewById(R.id.bAbout);
        bAbout.setOnClickListener(this);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.bClear)
        {
            clearAll();
        }
        else if(v.getId()==R.id.bAbout)
        {
            Intent aboutIntent=new Intent(this,AboutActivity.class);
            startActivity(aboutIntent);
        }
        else if(v.getId()==R.id.bSearch)
        {
            search();
        }
    }
    public void clearAll()
    {
        EditText etStreet= (EditText) findViewById(R.id.etStreet);
        etStreet.setText("");
        EditText etCity= (EditText) findViewById(R.id.etCity);
        etCity.setText("");
        Spinner state=(Spinner)findViewById(R.id.spinState);
        state.setSelection(0);
        RadioButton rbf= (RadioButton) findViewById(R.id.rbFahrenheit);
        rbf.setChecked(true);
        RadioButton rbd= (RadioButton) findViewById(R.id.rbDegree);
        rbd.setChecked(false);
        TextView error= (TextView) findViewById(R.id.tvError);
        error.setText("");


    }
    public void search(){

        if(validateAll())
        {
            AsyncTask task=new AsyncTask() {
                @Override
                protected Object doInBackground(Object[] params) {
                    getJson();
                    return null;

                }
            };
            task.execute();
        }
    }

    public void getJson()
    {

        EditText etStreet= (EditText) findViewById(R.id.etStreet);
        EditText etCity= (EditText) findViewById(R.id.etCity);
        String street1 = URLEncoder.encode(etStreet.getText().toString());
        String city1 = URLEncoder.encode(etCity.getText().toString());
        Spinner state=(Spinner)findViewById(R.id.spinState);
        String State = state.getSelectedItem().toString();
        int id = ((RadioGroup) findViewById(R.id.radiogroup)).getCheckedRadioButtonId();
        String degreeValue;
        String units;
        //String query = URLEncoder.encode("apples oranges", "utf-8");


        if(id==R.id.rbDegree) {
            degreeValue = "C";
            units = "si";
        }
        else {
            degreeValue = "F";
            units = "us";
        }

        HttpClient httpClient=new DefaultHttpClient();
        //HttpGet httpGet=new HttpGet("http://hwork8-env.elasticbeanstalk.com/index.php?address=1204+west+adams+blvd&city=los+angeles&state=CA&degree=us");
        //http://webtech-csci571homework8-env.elasticbeanstalk.com/?street=2138&city=Los+Angeles&state=CA&degree=Celsius
        //HttpGet httpGet=new HttpGet("http://theweatherforecast.elasticbeanstalk.com/?streetAddress=2138+oak+st&City=Los+Angeles&stateDropDown=CA&Degree=us");
        HttpGet httpGet=new HttpGet("http://theweatherforecast.elasticbeanstalk.com/?streetAddress="+street1+"&City="+city1+"&stateDropDown="+State+"&Degree="+units);

        HttpResponse response;
        try{

            response=httpClient.execute(httpGet);
            String jsonSt= EntityUtils.toString(response.getEntity(),"UTF-8");
            JSONObject jsonObject=new JSONObject(jsonSt);
            Intent intent=new Intent(MainActivity.this,DetailActivity.class);
            // EditText etStreet= (EditText) findViewById(R.id.etStreet);
            //intent.putExtra("street", etStreet.getText().toString());
            // EditText etCity= (EditText) findViewById(R.id.etCity);
            intent.putExtra("city", etCity.getText().toString());
            // Spinner state=(Spinner)findViewById(R.id.spinState);
            intent.putExtra("State", state.getSelectedItem().toString());
            intent.putExtra("json",jsonObject.toString());
            intent.putExtra("degree", degreeValue);
            startActivity(intent);


        }
        catch (ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {

            e.printStackTrace();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public Boolean validateAll()
    {
        EditText street= (EditText) findViewById(R.id.etStreet);
        String streetaddr= String.valueOf(street.getText());
        EditText city= (EditText) findViewById(R.id.etCity);
        String cityval= String.valueOf(city.getText());
        Spinner state= (Spinner) findViewById(R.id.spinState);
        String stateval=state.getSelectedItem().toString();
        TextView error= (TextView) findViewById(R.id.tvError);
        if (!streetaddr.matches(".*\\w.*"))
        {
            error.setText("Please enter a Street Address.");
            return false;
        }
        if (!cityval.matches(".*\\w.*"))
        {
            error.setText("Please enter a City.");
            return false;
        }
        if (stateval.equals("Select"))
        {
            error.setText("Please choose a State.");
            return false;
        }
        error.setText("");
        return true;
    }
}

