package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.location.Location;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import android.Manifest;

public class CreateAcAdShangBao extends AppCompatActivity {

    EditText nameTMingZi, phoneTDianHua, descriptionTMiaoShu, dateTRiQi, locationTDiDian;
    Button saveBChuCunAnNiu, locationBDangQianWeiZhiAnNiu; //Added a button for getting current location

    FusedLocationProviderClient fusedLocationClient; //Added a client for getting location

    //Declare a constant for the location permission request code
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        //find ID/Cha Zhao ID
        nameTMingZi = (EditText) findVBIDChaZhaoID(R.id.name_text);
        phoneTDianHua = (EditText) findVBIDChaZhaoID(R.id.phone_text);
        descriptionTMiaoShu = (EditText) findVBIDChaZhaoID(R.id.description_text);
        dateTRiQi = (EditText) findVBIDChaZhaoID(R.id.date_text);
        locationTDiDian = (EditText) findVBIDChaZhaoID(R.id.location_text);
        saveBChuCunAnNiu = (Button) findVBIDChaZhaoID(R.id.save_button);
        locationBDangQianWeiZhiAnNiu = (Button) findVBIDChaZhaoID(R.id.location_button);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        saveBChuCunAnNiu.setOnClickListener(v -> createInfoCuChunXingXi());

        locationBDangQianWeiZhiAnNiu.setOnClickListener(v -> saveCurrentPosCuChunDangQianWeiZhi());
    }

    private void saveCurrentPosCuChunDangQianWeiZhi() {
        //Check if the app has permission to access fine location
        if (ContextCompat.checkSelfPermission(CreateAcAdShangBao.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            //If not, request permission from the user
            ActivityCompat.requestPermissions(CreateAcAdShangBao.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);

        } else {
            //If permission is already granted, get the last known location of the device using Google Play Services Location API
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(CreateAcAdShangBao.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double longitudeJingDu = location.getLongitude(); //Get the longitude of the location
                                double latitudeWeiDu = location.getLatitude(); //Get the latitude of the location

                                String locationStringWeiDuJingDuString = longitudeJingDu + "," + latitudeWeiDu; //Format the location as a string separated by comma

                                locationTDiDian.setText(locationStringWeiDuJingDuString); //Set the text of the location edit text to the current location string

                                showMessxiaoXi("Current location obtained"); //Message/Xiao xi

                            } else {
                                showMessxiaoXi("Unable to get current location"); //Message/Xiao xi
                            }
                        }
                    });
        }
    }

    private void createInfoCuChunXingXi() {
        //To String/ZhuanHuan wei String
        String nameMingZi = nameTMingZi.getText().toString();
        //To String/ZhuanHuan wei String
        String phoneDianHua = phoneTDianHua.getText().toString();
        //To String/ZhuanHuan wei String
        String descriptionMiaoShu = descriptionTMiaoShu.getText().toString();
        //To String/ZhuanHuan wei String
        String dateRiQi = dateTRiQi.getText().toString();
        //To String/ZhuanHuan wei String
        String locationDiDian = locationTDiDian.getText().toString();

        if (nameMingZi.isEmpty() || phoneDianHua.isEmpty() || descriptionMiaoShu.isEmpty() || dateRiQi.isEmpty() || locationDiDian.isEmpty()) {
            //Message/Xiao xi
            showMessxiaoXi("Please Enter all fields/Don't leave it empty");
            return;
        }

        DatabaseShuJuKuHelper dbHelpShuJuKu = new DatabaseShuJuKuHelper(CreateAcAdShangBao.this);
        SQLiteDatabase dbShujU = dbHelpShuJuKu.getWritableDatabase();
        ContentValues valuesZhi = new ContentValues();
        valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME, nameMingZi);
        valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_PHONE, phoneDianHua);
        valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION, descriptionMiaoShu);
        valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DATE, dateRiQi);

        //Split the location string into longitude and latitude parts
        String[] locationPartsWeiDuJingDu = locationDiDian.split(",");
        if (locationPartsWeiDuJingDu.length != 2) {
                //Message/Xiao xi
                showMessxiaoXi("Invalid location format/Please enter longitude and latitude separated by comma");
            return;
        }

        try {
            double longitudeJingDu = Double.parseDouble(locationPartsWeiDuJingDu[0]); //Parse the longitude part as double
            double latitudeWeiDu = Double.parseDouble(locationPartsWeiDuJingDu[1]); //Parse the latitude part as double

            valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LONGITUDE, longitudeJingDu); //Put the longitude value in the database
            valuesZhi.put(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LATITUDE, latitudeWeiDu); //Put the latitude value in the database

            long newRowId = dbShujU.insert(DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME, null, valuesZhi);

            if (newRowId == -1) {
                //Message/Xiao xi
                showMessxiaoXi("Failed to save/Can't Save");
            } else {
                //Message/Xiao xi
                showMessxiaoXi("Saved sucess");
                finish();
            }
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }

    View findVBIDChaZhaoID(int id) {
        //Find the  id of the/ChaZhao ID
        View nView = findViewById(id);
        return nView;
    }

    void showMessxiaoXi(String mXiaoxi){
        //Message/Xiao xi
        Toast.makeText(this, mXiaoxi, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int retCode, String[] permit, int[] gtRes) {
        super.onRequestPermissionsResult(retCode, permit, gtRes);
        switch (retCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                //Curren Pos requir/Wei Zhi xing xi xu ke qing qiu
                if (gtRes.length > 0 && gtRes[0] == PackageManager.PERMISSION_GRANTED) {

                    showMessxiaoXi("Permission granted"); //Message/Xiao xi

                } else {

                    showMessxiaoXi("Permission denied"); //Message/Xiao xi

                }
            }
        }
    }

}
