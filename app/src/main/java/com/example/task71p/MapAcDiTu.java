package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapAcDiTu extends AppCompatActivity implements OnMapReadyCallback {
    GoogleMap mapDiTu; //Declare a Google Map object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment mapFragmentDituZuJian = findFregByIdGengjuIDChazhao(R.id.map);
        mapFragmentDituZuJian.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapDiTu = googleMap; //Assign the Google Map object to the map variable

        DatabaseShuJuKuHelper dbHelpShuJuKu = new DatabaseShuJuKuHelper(MapAcDiTu.this);
        SQLiteDatabase dbShujU = dbHelpShuJuKu.getReadableDatabase();

        String[] projectionLie = {
                DatabaseShuJuKuHelper.ItemEntry._ID,
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME,
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_PHONE,
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION,
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_DATE,
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_LONGITUDE, //Added longitude
                DatabaseShuJuKuHelper.ItemEntry.COLUMN_LATITUDE //Added latitude
        };

        Cursor cursorZhiZhen = dbShujU.query(
                DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME,
                projectionLie,
                null,
                null,
                null,
                null,
                null
        );

        while (cursorZhiZhen.moveToNext()) {
            String nameMingZi = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME));
            String descriptionMiaoShu = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION));
            double longitudeJingDu = cursorZhiZhen.getDouble(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LONGITUDE));
            double latitudeWeiDu = cursorZhiZhen.getDouble(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LATITUDE));

            LatLng itemLocationXiangMuWeiDuJingDu = new LatLng(latitudeWeiDu, longitudeJingDu);

            mapDiTu.addMarker(new MarkerOptions().position(itemLocationXiangMuWeiDuJingDu).title(nameMingZi).snippet(descriptionMiaoShu)); //Add a marker to the map with the item name and description

            mapDiTu.moveCamera(CameraUpdateFactory.newLatLng(itemLocationXiangMuWeiDuJingDu)); //Move the camera to the item location
        }

        cursorZhiZhen.close(); //Close the cursor
    }

    SupportMapFragment findFregByIdGengjuIDChazhao(int id){
        SupportMapFragment Fragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(id);
        return Fragment;
    }
}
