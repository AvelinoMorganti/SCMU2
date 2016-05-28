    package com.whaves.scmu;

    import android.Manifest;
    import android.annotation.TargetApi;
    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.BroadcastReceiver;
    import android.content.Context;
    import android.content.DialogInterface;
    import android.content.Intent;
    import android.content.IntentFilter;
    import android.content.pm.PackageManager;
    import android.location.Location;
    import android.location.LocationListener;
    import android.location.LocationManager;
    import android.net.Uri;
    import android.os.Bundle;
    import android.support.v4.app.ActivityCompat;
    import android.util.Log;
    import android.view.LayoutInflater;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.DatePicker;
    import android.widget.EditText;
    import android.widget.ScrollView;
    import android.widget.Switch;
    import android.widget.TimePicker;
    import android.widget.Toast;
    import android.widget.ToggleButton;

    import com.google.android.gms.appindexing.Action;
    import com.google.android.gms.appindexing.AppIndex;
    import com.google.android.gms.common.api.GoogleApiClient;
    import com.google.gson.Gson;

    import org.apache.commons.codec.binary.StringUtils;
    import org.apache.http.impl.client.BasicCookieStore;

    import java.text.DecimalFormat;
    import java.text.NumberFormat;
    import java.text.SimpleDateFormat;
    import java.util.Date;
    import java.util.List;
    import java.util.Timer;
    import java.util.TimerTask;

    /**
     * Created by avelino on 24/05/16.
     */
    public class AlarmClock extends Activity {

        //Request/response
        private Request request;
        private State state;
        CookieStoreImpl data;

        //Constants
        int time = 1000 * 5;             //call function every 5 seconds
        int i = 0;
        private Timer myTimer;
        private int GMT_DIFF = 4;

        private String d;
        private String t;
        private boolean lamp;
        private boolean soundAlarm;

        private GoogleApiClient client;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.alarm_clock);

            client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

            Intent i = getIntent();
            data = (CookieStoreImpl) i.getSerializableExtra("COOKIESTORE");

            /*myTimer = new Timer();
            myTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    TimerMethod();
                }

            }, 0, time);*/
        }

        public void setAlarmClock(View view){
            BasicCookieStore ck = Utils.createApacheCookieStore((List<CookiesImpl>) data.getData());
            request = new Request(ck);

            String response = request.getStateJSON(ck);
            Gson gson = new Gson();
            state = gson.fromJson(response, State.class);

            state.setAlarmSensors(soundAlarm);
            state.setLamp(lamp);

            if(!t.isEmpty()){
                state.setT(t);
            }

            if(!d.isEmpty()){
                state.setD(d);
            }

            String json = gson.toJson(state);
            request.setScheduleJSON(ck, json);

            //SimpleDateFormat toShow = new SimpleDateFormat("dd/MM/yyyy");
            showMessage("Alarm clock set successfully!\n"
                    +d+" "+t+"\n\nalarm = "+soundAlarm+"\nlamp = "+lamp,"OK");
        }



            public void setLamp(View v) {
                lamp = ((Switch) v).isChecked();
            }
        public void setAlarm(View v) {
            soundAlarm =  ((Switch) v).isChecked();
        }


        public void setTime(View view) {
            alertTimePicker();
        }

        public void setDate(View view) {
            alertDatePicker();
        }

        public void back(View v) {
            finish();
        }

       public void alertTimePicker() {

        /*
         * Inflate the XML view. activity_main is in res/layout/time_picker.xml
         */
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.time_picker, null, false);

            // the time picker on the alert dialog, this is how to get the value
            final TimePicker myTimePicker = (TimePicker) view
                    .findViewById(R.id.myTimePicker);
           myTimePicker.setIs24HourView(true);

        /*
         * To remove option for AM/PM, add the following line:
         *
         * operatingHoursTimePicker.setIs24HourView(true);
         */

            // the alert dialog
            new AlertDialog.Builder(AlarmClock.this).setView(view)
                    .setTitle("Set Time")
                    .setCancelable(false)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @TargetApi(11)
                        public void onClick(DialogInterface dialog, int id) {


                            //****************
                            DecimalFormat nf = new DecimalFormat("##");
                            nf.setMinimumIntegerDigits(2);
                            nf.setMaximumIntegerDigits(2);

                            String hour = String.valueOf(nf.format(myTimePicker.getCurrentHour()));
                            String minute = String.valueOf(nf.format(myTimePicker.getCurrentMinute()));

                            int hour_ = myTimePicker.getCurrentHour();

                            /*
                      AM    0 => (24 + 0) -4 = 20:00
                            1 => (24 + 1) -4 = 21:00
                            2 => (24 + 2) -4 = 22:00
                            3 => (24 + 3) -4 = 23:00
                            -------------------------
                            4 => (00 + 4) -4 = 00:00
                            5 => (00 + 5) -4 = 01:00
                            6 => (00 + 6) -4 = 02:00
                            7 => (00 + 7) -4 = 03:00
                            8 => (00 + 8) -4 = 04:00
                            9 => (00 + 9) -4 = 05:00
                            10 => (00 + 10) -4 = 06:00
                            11 => (00 + 11) -4 = 07:00
                            12 => (00 + 12) -4 = 08:00
                            13 => (00 + 13) -4 = 09:00
                            14 => (00 + 14) -4 = 10:00
                            15 => (00 + 15) -4 = 11:00
                            16 => (00 + 16) -4 = 12:00
                            17 => (00 + 17) -4 = 13:00
                            18 => (00 + 18) -4 = 14:00
                            19 => (00 + 19) -4 = 15:00
                            20 => (00 + 20) -4 = 16:00
                            21 => (00 + 21) -4 = 17:00
                            22 => (00 + 22) -4 = 18:00
                            23 => (00 + 23) -4 = 19:00
                            24 => (00 + 00)  -4 = 20:00
                            */

                            if (hour_ >= 0 && hour_ < GMT_DIFF) {
                                t = String.valueOf(nf.format(24 + hour_ - GMT_DIFF))
                                        + ":" + minute + ":00";
                            } else {
                                t = String.valueOf(nf.format(hour_ - GMT_DIFF))
                                        + ":" + minute + ":00";
                            }


                           /* SimpleDateFormat df = new SimpleDateFormat("hh:mm:ss");
                            SimpleDateFormat toShow = new SimpleDateFormat("hh:mm:ss");
                            Date time = new Date(2016, 0, 1, myTimePicker.getHour(), myTimePicker.getMinute());
                            String inspectionhour  = df.format(time);*/

                            showMessage(t, "OK");
                            showToast(hour + ":" + minute);
                            dialog.cancel();

                        }

                    }).show();
        }

        public void alertDatePicker() {

        /*
         * Inflate the XML view. activity_main is in res/layout/date_picker.xml
         */
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.date_picker, null, false);

            // the time picker on the alert dialog, this is how to get the value
            final DatePicker myDatePicker = (DatePicker) view.findViewById(R.id.myDatePicker);

            // so that the calendar view won't appear
            myDatePicker.setCalendarViewShown(false);

            // the alert dialog
            new AlertDialog.Builder(AlarmClock.this).setView(view)
                    .setTitle("Set Date")
                    .setCancelable(false)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    })
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @TargetApi(11)
                        public void onClick(DialogInterface dialog, int id) {

                        /*
                         * In the docs of the calendar class, January = 0, so we
                         * have to add 1 for getting correct month.
                         * http://goo.gl/9ywsj
                         */


                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat toShow = new SimpleDateFormat("dd/MM/yyyy");
                            Date date = new Date(myDatePicker.getYear() - 1900,
                                    myDatePicker.getMonth(), myDatePicker.getDayOfMonth());
                            String inspectiondate = df.format(date);

                            /*int month = myDatePicker.getMonth() + 1;
                            int day = myDatePicker.getDayOfMonth();
                            int year = myDatePicker.getYear();*/
                            //String month   = StringUtils.leftPad(Integer.toString(myDatePicker.getMonth() + 1), 2, "0");


                            d = inspectiondate;
                            showToast(toShow.format(date));
                            //showMessage(d, "OK");
                            //showMessage(inspectiondate,"OK");
                            dialog.cancel();

                        }

                    }).show();
        }

        public void showToast(final String msg) {
            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            });
        }

        public void showMessage(String msg, String button) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(AlarmClock.this);
            dialog.setMessage(msg);
            dialog.setNeutralButton(button, null);
            dialog.show();
        }

        /*private void TimerMethod() {
            //This method is called directly by the timer
            //and runs in the same thread as the timer.
            //We call the method that will work with the UI
            //through the runOnUiThread method.
            this.runOnUiThread(Timer_Tick);

        }


        private Runnable Timer_Tick = new Runnable() {
            //This method runs in the same thread as the UI.
            //Do something to the UI thread here
            public void run() {
                try {
                    actualizar();
                } catch (Exception e) {
                    //Toast.makeText(getApplicationContext(),
                    //      "Network error. Please, try again.", Toast.LENGTH_SHORT).show();
                }
            }
        };*/
    }