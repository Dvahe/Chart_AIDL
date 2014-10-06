package am.sleepservice;

 import java.util.Random;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MService extends Service {

	private static int TimeOfSleep;
	@Override
	public IBinder onBind(Intent intent) {
		return new IRemuteServiceImpl();
	}

	private static int generateRandomData(){
		Random random = new Random();
		return random.nextInt(201);
	}
	
	public static class IRemuteServiceImpl extends IRemuteService.Stub{

		@Override
		public int getresponse() throws RemoteException {
			TimeOfSleep = generateRandomData();
			Log.i("TimeOfSleep", ""+ TimeOfSleep);
			try {
				Thread.sleep(TimeOfSleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return TimeOfSleep;
		}
		
	}

}
