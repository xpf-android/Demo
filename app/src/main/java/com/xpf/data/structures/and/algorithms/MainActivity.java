package com.xpf.data.structures.and.algorithms;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import static com.xpf.data.structures.and.algorithms.MapUtils.endCol;
import static com.xpf.data.structures.and.algorithms.MapUtils.endRow;
import static com.xpf.data.structures.and.algorithms.MapUtils.map;
import static com.xpf.data.structures.and.algorithms.MapUtils.startCol;
import static com.xpf.data.structures.and.algorithms.MapUtils.result;
import static com.xpf.data.structures.and.algorithms.MapUtils.startRow;
import static com.xpf.data.structures.and.algorithms.MapUtils.touchFlag;
public class MainActivity extends AppCompatActivity {

    ShowMapView showMapView;
    Handler handler=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        handler=new Handler(Looper.getMainLooper());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showMapView = (ShowMapView) findViewById(R.id.show);
    }


    public void cal(View view) {
        MapInfo info = new MapInfo(map, map[0].length, map.length, new Node( startCol,startRow), new Node(endCol, endRow));
        Log.i("jett","start="+startRow+" "+startCol);
        Log.i("jett","end="+endRow+" "+endCol);
        new Astar().start(info);
        new MoveThread(showMapView).start();

    }

    public void reset(View view){
        MapUtils.path=null;
        MapUtils.result.clear();
        touchFlag=0;
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[i].length;j++){
                if(map[i][j]==2){
                    map[i][j]=0;
                }
            }
        }
        showMapView.invalidate();

    }


    class MoveThread extends Thread{
        ShowMapView showMapView;
        public MoveThread(ShowMapView showMapView){
            this.showMapView=showMapView;
        }
        @Override
        public void run() {
            while(result.size()>0){
                Log.i("jett",result.size()+"");
                Node node= result.pop();
                map[node.coord.y][node.coord.x]=2;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showMapView.invalidate();
                    }
                });

                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map[node.coord.y][node.coord.x] = 0;

            }
            MapUtils.touchFlag=0;
        }
    }

}
