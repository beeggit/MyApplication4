package as.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



    import java.io.IOException;
    import java.net.Authenticator;
    import java.net.PasswordAuthentication;
    import java.util.ArrayList;
    import java.util.List;

    import org.ksoap2.HeaderProperty;
    import org.ksoap2.SoapEnvelope;
    import org.ksoap2.serialization.PropertyInfo;
    import org.ksoap2.serialization.SoapObject;
    import org.ksoap2.serialization.SoapPrimitive;
    import org.ksoap2.serialization.SoapSerializationEnvelope;
    import org.ksoap2.transport.HttpTransportSE;
    import org.xmlpull.v1.XmlPullParserException;

    import android.app.Activity;
    import android.os.AsyncTask;
    import android.os.Bundle;
    import android.view.View;
    import android.view.View.OnClickListener;
    import android.widget.Button;
    import android.widget.TextView;


public class MainActivity extends AppCompatActivity {
        private final String namespace = "urn:microsoft-dynamics-schemas/codeunit/Letters";
        private final String url = "http://10.0.9.120:7047/DynamicsNAV/WS/EGI/Codeunit/Letters";
        private final String soap_action = "urn:microsoft-dynamics-schemas/codeunit/Letters:Capitalize";
        private final String method_name = "Capitalize";
        String great="";
        TextView txt;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            txt=(TextView)findViewById(R.id.text);
            Button bt=(Button)findViewById(R.id.btn);
            bt.setOnClickListener(new OnClickListener() {

                @Override
                public void onClick(View v) {

                    new Task().execute();


                }
            }) ;


        }

        public String maMethode(){
            try
            {
                SoapObject request = new SoapObject(namespace, method_name);


                //Property which holds input parameters
                PropertyInfo celsiusPI = new PropertyInfo();
                //Set Name
                celsiusPI.setName("inputstring");
                int n=123;
                //Set Value
                celsiusPI.setValue(n);
                celsiusPI.setType(int.class);
                request.addProperty(celsiusPI);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);


                //envelope.bodyOut=request;
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);
                HttpTransportSE transport = new HttpTransportSE(url);
                try{

                    List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
                    headerList.add(new HeaderProperty("Authorization", "Basic " + org.kobjects.base64.Base64.encode("brad:larry.12".getBytes())));
                    transport.call(soap_action, envelope);
                }catch(IOException e )
                {
                    e.printStackTrace();
                    // System.out.println(e.toString()+"1");
                    great=e.toString();
                }
                catch(XmlPullParserException e)
                {
                    e.printStackTrace();
                    //System.out.println(e.toString()+"2");
                    great=e.toString();
                }

                if(envelope.bodyIn != null){
                    SoapPrimitive result = (SoapPrimitive) envelope.getResponse();

                    great= result.toString();
                }
                return great;


            }
            catch (Exception e)
            {
                e.printStackTrace();
                // System.out.println(e.toString()+"" );
                great = e.toString();
                return great;

            }

        }



        public class Task extends AsyncTask<Void,Void,Void>
        {


            @Override
            protected Void doInBackground(Void... params) {
                // TODO Auto-generated method stub
                great=maMethode();
                return null;
            }


            @Override
            protected void onPostExecute(Void result) {
                //Log.i(TAG, "doInBackground");
                txt.setText(great);
            }



        }}
