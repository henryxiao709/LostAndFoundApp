package com.example.task71p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DetailXingxi extends AppCompatActivity {

    TextView nameVMinziV, phoneVDianHua, descriptionVMiaoShu, dateVRiQi, locationVDiDian;
    Button removeBYiChuAnNiu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Find he  id of the/ChaZhao ID
        nameVMinziV = (TextView) findVBIDChaZhaoID(R.id.name_view);
        phoneVDianHua = (TextView) findVBIDChaZhaoID(R.id.phone_view);
        descriptionVMiaoShu = (TextView) findVBIDChaZhaoID(R.id.description_view);
        dateVRiQi = (TextView) findVBIDChaZhaoID(R.id.date_view);
        locationVDiDian = (TextView) findVBIDChaZhaoID(R.id.location_view);
        removeBYiChuAnNiu = (Button) findVBIDChaZhaoID(R.id.remove_button);

        Intent intent = getIntent();
        int itemId = intent.getIntExtra("itemId", -1);

        if (itemId == -1) {
            //Messge/Xiao xi
            showMessxiaoXi("Invalid item id");
            finish();
            return;
        }

        DatabaseShuJuKuHelper dbHelper = new DatabaseShuJuKuHelper(DetailXingxi.this);
        SQLiteDatabase dbShuJu = dbHelper.getReadableDatabase();
        Cursor cursorZhiZhen = dbShuJu.query(
                DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME,
                null,
                DatabaseShuJuKuHelper.ItemEntry._ID + "=?",
                new String[] {String.valueOf(itemId)},
                null,
                null,
                null
        );

        if (cursorZhiZhen.moveToFirst()) {
            String nameMingZi = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_NAME));
            String phoneDianHua = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_PHONE));
            String descriptionMiaoShu = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DESCRIPTION));
            String dateRiQi = cursorZhiZhen.getString(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_DATE));

            double longitudeJingDu = cursorZhiZhen.getDouble(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LONGITUDE));
            double latitudeWeiDu = cursorZhiZhen.getDouble(cursorZhiZhen.getColumnIndexOrThrow(DatabaseShuJuKuHelper.ItemEntry.COLUMN_LATITUDE));

            String strLongitudeJingDu = Location.convert(longitudeJingDu, Location.FORMAT_SECONDS);
            String strLatitudeWeiDu = Location.convert(latitudeWeiDu, Location.FORMAT_SECONDS);

            strLongitudeJingDu = strLongitudeJingDu.replaceFirst(":", "°");
            strLongitudeJingDu = strLongitudeJingDu.replaceFirst(":", "'");
            strLongitudeJingDu += "\"";

            strLatitudeWeiDu = strLatitudeWeiDu.replaceFirst(":", "°");
            strLatitudeWeiDu = strLatitudeWeiDu.replaceFirst(":", "'");
            strLatitudeWeiDu += "\"";

            String longitudeHemisphereJingDuBanQiu = longitudeJingDu > 0 ? "E" : "W";
            String latitudeHemisphereWeiDuBanQiu = latitudeWeiDu > 0 ? "N" : "S";

            strLongitudeJingDu += " " + longitudeHemisphereJingDuBanQiu;
            strLatitudeWeiDu += " " + latitudeHemisphereWeiDuBanQiu;

            nameVMinziV.setText(nameMingZi);
            phoneVDianHua.setText(phoneDianHua);
            descriptionVMiaoShu.setText(descriptionMiaoShu);
            dateVRiQi.setText(dateRiQi);

            locationVDiDian.setText(strLongitudeJingDu + ", " + strLatitudeWeiDu);

            removeBYiChuAnNiu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rowsDeleShanChu = dbShuJu.delete(DatabaseShuJuKuHelper.ItemEntry.TABLE_NAME, DatabaseShuJuKuHelper.ItemEntry._ID + "=?", new String[] {String.valueOf(itemId)});

                    if (rowsDeleShanChu == 0) {
                        //Message/Xiao xi
                        showMessxiaoXi("Faile to remve");
                    } else {
                        //Message/Xiao xi
                        showMessxiaoXi("Remov sucessfully");
                        finish();
                    }
                }
            });
        } else {
            //Message/Xia xi
            showMessxiaoXi("Item not found");
            finish();
        }

        cursorZhiZhen.close();
    }

    View findVBIDChaZhaoID(int id) {
        //Find the  i of the/ChaZhao ID
        View nView = findViewById(id);
        return nView;
    }

    void showMessxiaoXi(String mXiaoxi){
        //Mesage/Xiao xi
        Toast.makeText(this, mXiaoxi, Toast.LENGTH_SHORT).show();
    }
}

