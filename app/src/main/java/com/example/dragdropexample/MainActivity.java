package com.example.dragdropexample;

import android.content.ClipData;
import android.content.ClipDescription;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.RelativeLayout;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private RelativeLayout parentLayout;
    private Button draggableButton;

    private static final int BASE_SIZE = 50; // 5dp grid size

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parentLayout = findViewById(R.id.parentLayout);
        draggableButton = findViewById(R.id.draggableButton);
		draggableButton.setTag("draggableButton");

        draggableButton.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
					if(v.getTag()!=null){
                ClipData.Item item = new ClipData.Item((CharSequence)v.getTag());
                String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
                ClipData dragData = new ClipData(v.getTag().toString(), mimeTypes, item);

                View.DragShadowBuilder myShadow = new View.DragShadowBuilder(draggableButton);
                v.startDragAndDrop(dragData, myShadow, null, 0);
                return true;
						}else{
							return false;
						}
            }
        });

        parentLayout.setOnDragListener(new OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                switch (event.getAction()) {
                    case DragEvent.ACTION_DROP:
                        float x = event.getX();
                        float y = event.getY();

                        int gridX = Math.round(x / dpToPx(BASE_SIZE));
                        int gridY = Math.round(y / dpToPx(BASE_SIZE));

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                            dpToPx(2 * BASE_SIZE), // Width 2 * 5dp
                            dpToPx(3 * BASE_SIZE)  // Height 3 * 5dp
                        );


                        draggableButton.setLayoutParams(layoutParams);
                        break;
                }
                return true;
            }
        });
    }

    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }
}