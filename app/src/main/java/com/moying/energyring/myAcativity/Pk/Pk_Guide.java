package com.moying.energyring.myAcativity.Pk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.moying.energyring.R;

public class Pk_Guide extends Activity {

    private ImageView guide_img;
    private int[] myGuideicon = {R.drawable.guide_one, R.drawable.guide_two};
    int imgID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk__guide);

        guide_img = (ImageView) findViewById(R.id.guide_img);

        Intent intent = getIntent();
        String guideId = intent.getStringExtra("guideId");

        if (guideId.equals("1")) {
//            guide_img.setBackgroundResource(myGuideicon[imgID]);
//            guide_img.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    imgID = imgID + 1;
//                    if (imgID == myGuideicon.length) {
//                        finish();
//                        Intent intent1 = new Intent(Pk_Guide.this, HasNewActivity.class);
//                        startActivity(intent1);
//                    } else {
//                        guide_img.setBackgroundResource(myGuideicon[imgID]);
//                    }
//                }
//            });
            guide_img.setBackgroundResource(R.drawable.guide_one);
            guide_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.putExtra("guideId", "0");
                    setResult(RESULT_OK, intent);
                    finish();
//                    finish();
                }
            });
        }else if (guideId.equals("3")){
            guide_img.setBackgroundResource(R.drawable.guide_three);
            guide_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }else if (guideId.equals("2")){
            guide_img.setBackgroundResource(R.drawable.guide_two);
            guide_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
        }
    }
}
