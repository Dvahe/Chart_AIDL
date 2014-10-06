package com.example.client;

import org.achartengine.GraphicalView;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import am.sleepservice.IRemuteService;

public class MainActivity extends Activity {
	
	private IRemuteService dataService;
	private GraphicalView Gview;
	private BarChart barChart = new BarChart();
	private DataConnection conn;
	private static Thread thread;
	private static final String TAG = MainActivity.class.getSimpleName();
	private Object lock = new Object();
	private int timeafter, timebefore;
	private LinearLayout layout; 
	private static int countOfCall = 20;
	private static int TimeOfSleep = 200;
	
	private void Update(){
			conn = new DataConnection();
	        Intent intent = new Intent("am.sleepservice.IRemuteService");
	        bindService(intent, conn, Context.BIND_AUTO_CREATE);
			thread = new Thread(){
    		
			@Override
			public void run() {
				super.run();
				  synchronized (lock){
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				
				for(int i = 0; i < countOfCall; ++i){
					try {
						timebefore = (int) System.currentTimeMillis(); 
						dataService.getresponse();
						timeafter = (int) System.currentTimeMillis();
						
				} catch (RemoteException e) {
					e.printStackTrace();
				}	
					try {
						Thread.sleep(TimeOfSleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					barChart.addNewPoints(timeafter - timebefore);
					Gview.repaint();
				}
			}
    	};
    	thread.start();
    	
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = (LinearLayout)findViewById(R.id.chart);        
        Update();
    }

    //Button 
    public void GetDataHandler(View v){
    	MainActivity.this.setProgressBarIndeterminateVisibility(true);
    	layout.removeAllViews();
    	barChart.resetBChart();
    	Update();
    	layout.addView(Gview);
    }
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroyed");
		unbindService(conn);
		dataService = null;
	}
    
	@Override
	protected void onStart() {
		super.onStart();
		 Gview = barChart.getView(this);
		 layout.addView(Gview);
	}

	class DataConnection implements ServiceConnection{
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			dataService = IRemuteService.Stub.asInterface(service);
			synchronized (lock) {
			lock.notify();	
			}
			Log.i("MYService", "Connected");
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			dataService = null;			
		}
    	
    }
	
}
