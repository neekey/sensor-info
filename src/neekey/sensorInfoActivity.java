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
	 * 每个感应器信息TextView的容器
	 */
	private LinearLayout scrollInfo;
	
	/**
	 * 存储 sensor -> TextView 对应关系的哈希表
	 */
	private Map< Sensor, TextView > tvList;
	
	/**
	 * sensor 监听器
	 */
	private SensorEventListenerImpl sensorListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate( savedInstanceState );
        
        // 设置视图
        setContentView(R.layout.main);
        
        // 初始化变量
        
        scrollInfo = ( LinearLayout )findViewById( R.id.linearLayout1 );
        tvList = new HashMap<Sensor, TextView>();
        sensorListener = new SensorEventListenerImpl( tvList );
        
        //从系统服务中获得传感器管理器
        SensorManager sm = (SensorManager) getSystemService(Context.SENSOR_SERVICE);   
        //从传感器管理器中获得全部的传感器列表   
        List<Sensor> allSensors = sm.getSensorList(Sensor.TYPE_ALL);   
        
   
        // 遍历所有的感应器
        for (Sensor s : allSensors) {   
   
        	// 添加TextView
    		createView( s, scrollInfo, sm );

    		// 绑定监听器
            sm.registerListener( sensorListener, s, SensorManager.SENSOR_DELAY_NORMAL );
        }   
            
    }
    
    /**
     * 为sensor添加对应的视图
     * @param s
     * @param sInfo	容器
     * @param sm 
     */
    public void createView( Sensor s, LinearLayout sInfo, SensorManager sm ){
    
    	TextView tv = new TextView( this );
    	tvList.put( s, tv );
    	
    	sInfo.addView( tv );
    }
    
    /**
     * 获取感应器信息
     * @param s
     * @return
     */
    public String getSensorInfo( Sensor s ){
    	
    	String infoStr = "";
    	
    	switch ( s.getType() ) {   
        case Sensor.TYPE_ACCELEROMETER:   
            infoStr += s.getType() + " 加速度传感器accelerometer";   
            break;   
        case Sensor.TYPE_GRAVITY:   
        	infoStr += s.getType() + " 重力传感器gravity API 9";   
            break;   
        case Sensor.TYPE_GYROSCOPE:   
        	infoStr += s.getType() + " 陀螺仪传感器gyroscope";   
            break;   
        case Sensor.TYPE_LIGHT:   
        	infoStr += s.getType() + " 环境光线传感器light";   
            break;   
        case Sensor.TYPE_LINEAR_ACCELERATION:   
        	infoStr += s.getType() + " 线性加速器LINEAR_ACCELERATION API 9";   
            break;   
        case Sensor.TYPE_MAGNETIC_FIELD:   
        	infoStr += s.getType() + " 电磁场传感器magnetic field";   
            break;   
        case Sensor.TYPE_ORIENTATION:   
        	infoStr += s.getType() + " 方向传感器orientation";   
            break;   
        case Sensor.TYPE_PRESSURE:   
        	infoStr += s.getType() + " 压力传感器pressure";   
            break;   
        case Sensor.TYPE_PROXIMITY:   
        	infoStr += s.getType() + " 距离传感器proximity";   
            break;   
        case Sensor.TYPE_ROTATION_VECTOR:   
        	infoStr += s.getType() + " 旋转向量ROTATION";   
            break;   
        case Sensor.TYPE_TEMPERATURE:   
        	infoStr += s.getType() + " 温度传感器temperature";   
            break;   
        default:   
        	infoStr += s.getType() + " 未知传感器";   
            break;   
        }   
    	
    	infoStr += "\n" + "  设备名称：" + s.getName() + "\n" + "  设备版本：" + s.getVersion() + "\n" + "  供应商："   
		+ s.getVendor() + "\n";

    	return infoStr;
    }
    
    /**
     * 实现 感应器监听接口
     * @author neekey
     *
     */
    class SensorEventListenerImpl implements SensorEventListener{
		
    	/**
    	 * 存储 sensor -> TextView 对应关系的哈希表
    	 */
    	private Map< Sensor, TextView > tvList;
	    
		public SensorEventListenerImpl(){
		}
		
		public SensorEventListenerImpl( Map< Sensor, TextView > tvList ){
			this.tvList = tvList;
		}
		
		/**
		 * 监听 sensor 的变化
		 */
	    public void onSensorChanged(SensorEvent e) { 
	    	
	    	float x = e.values[SensorManager.DATA_X];   
            float y = e.values[SensorManager.DATA_Y];   
            float z = e.values[SensorManager.DATA_Z]; 
            
            // 更新视图
            this.tvList.get( e.sensor ).setText( getSensorInfo( e.sensor ) + "x=" + (int) x + "," + "y=" + (int) y + "," + "z="+ (int) z ) ;  
            
            // 使用 Html.fromHtml() 进行渲染 ，但是发现效率很低
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