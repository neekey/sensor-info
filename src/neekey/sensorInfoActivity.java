package neekey;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import neekey.sensorInfo.R;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

public class sensorInfoActivity extends Activity {
	
	/**
	 * ÿ����Ӧ����ϢTextView������
	 */
	private LinearLayout scrollInfo;
	
	/**
	 * �洢 sensor -> TextView ��Ӧ��ϵ�Ĺ�ϣ��
	 */
	private Map< Sensor, TextView > tvList;
	
	/**
	 * sensor ������
	 */
	private SensorEventListenerImpl sensorListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate( savedInstanceState );
        
        // ������ͼ
        setContentView(R.layout.main);
        
        // ��ʼ������
        
        scrollInfo = ( LinearLayout )findViewById( R.id.linearLayout1 );
        tvList = new HashMap<Sensor, TextView>();
        sensorListener = new SensorEventListenerImpl( tvList );
        
        //��ϵͳ�����л�ô�����������
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);   
        //�Ӵ������������л��ȫ���Ĵ������б�   
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);   
        
   
        // �������еĸ�Ӧ��
        for (Sensor s : allSensors) {   
   
        	// ���TextView
    		createView( s, scrollInfo, sm );

    		// �󶨼�����
            sm.registerListener( sensorListener, s, SensorManager.SENSOR_DELAY_NORMAL );
        }   
            
    }
    
    /**
     * Ϊsensor��Ӷ�Ӧ����ͼ
     * @param s
     * @param sInfo	����
     * @param sm 
     */
    public void createView( Sensor s, LinearLayout sInfo, SensorManager sm ){
    
    	TextView tv = new TextView( this );
    	tvList.put( s, tv );
    	
    	sInfo.addView( tv );
    }
    
    /**
     * ��ȡ��Ӧ����Ϣ
     * @param s
     * @return
     */
    public String getSensorInfo( Sensor s ){
    	
    	String infoStr = "";
    	
    	switch ( s.getType() ) {   
        case Sensor.TYPE_ACCELEROMETER:   
            infoStr += s.getType() + " ���ٶȴ�����accelerometer";   
            break;   
        case Sensor.TYPE_GRAVITY:   
        	infoStr += s.getType() + " ����������gravity API 9";   
            break;   
        case Sensor.TYPE_GYROSCOPE:   
        	infoStr += s.getType() + " �����Ǵ�����gyroscope";   
            break;   
        case Sensor.TYPE_LIGHT:   
        	infoStr += s.getType() + " �������ߴ�����light";   
            break;   
        case Sensor.TYPE_LINEAR_ACCELERATION:   
        	infoStr += s.getType() + " ���Լ�����LINEAR_ACCELERATION API 9";   
            break;   
        case Sensor.TYPE_MAGNETIC_FIELD:   
        	infoStr += s.getType() + " ��ų�������magnetic field";   
            break;   
        case Sensor.TYPE_ORIENTATION:   
        	infoStr += s.getType() + " ���򴫸���orientation";   
            break;   
        case Sensor.TYPE_PRESSURE:   
        	infoStr += s.getType() + " ѹ��������pressure";   
            break;   
        case Sensor.TYPE_PROXIMITY:   
        	infoStr += s.getType() + " ���봫����proximity";   
            break;   
        case Sensor.TYPE_ROTATION_VECTOR:   
        	infoStr += s.getType() + " ��ת����ROTATION";   
            break;   
        case Sensor.TYPE_TEMPERATURE:   
        	infoStr += s.getType() + " �¶ȴ�����temperature";   
            break;   
        default:   
        	infoStr += s.getType() + " δ֪������";   
            break;   
        }   
    	
    	infoStr += "\n" + "  �豸���ƣ�" + s.getName() + "\n" + "  �豸�汾��" + s.getVersion() + "\n" + "  ��Ӧ�̣�"   
		+ s.getVendor() + "\n";

    	return infoStr;
    }
    
    /**
     * ʵ�� ��Ӧ�������ӿ�
     * @author neekey
     *
     */
    class SensorEventListenerImpl implements SensorEventListener{
		
    	/**
    	 * �洢 sensor -> TextView ��Ӧ��ϵ�Ĺ�ϣ��
    	 */
    	private Map< Sensor, TextView > tvList;
	    
		public SensorEventListenerImpl(){
		}
		
		public SensorEventListenerImpl( Map< Sensor, TextView > tvList ){
			this.tvList = tvList;
		}
		
		/**
		 * ���� sensor �ı仯
		 */
	    public void onSensorChanged(SensorEvent e) { 
	    	
	    	float x = e.values[SensorManager.DATA_X];   
            float y = e.values[SensorManager.DATA_Y];   
            float z = e.values[SensorManager.DATA_Z]; 
            
            // ������ͼ
            this.tvList.get( e.sensor ).setText( getSensorInfo( e.sensor ) + "x=" + (int) x + "," + "y=" + (int) y + "," + "z="+ (int) z ) ;  
            
            // ʹ�� Html.fromHtml() ������Ⱦ �����Ƿ���Ч�ʺܵ�
            /*
            this.tvList.get( e.sensor ).setText(  
            		Html.fromHtml( "<div style=\"padding: 5px; background: yellow;\">"
            				+ "<p >" + getSensorInfo( e.sensor ) + "</p>"
            				+ "<p >x=" + (int) x + "," + "y=" + (int) y + "," + "z="+ (int) z + "<p>"
            				+ "</div>" 
            				) ) ;  
            */
	   }   

	    public void onAccuracyChanged(Sensor s, int accuracy) {   
	    	
	    }
	}
    
}