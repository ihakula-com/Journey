package com.example.sample;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nutsplay.api.BalanceVO;
import com.nutsplay.api.UserEvents;
import com.nutsplay.paysdk.NutsWebViewActivity;
import com.nutsplay.util.Base64;
import com.nutsplay.util.Base64DecoderException;
import com.nutsplay.util.LogUtil;
import com.nutsplay.util.ToastAlone;

public class MainActivity extends FragmentActivity{
	
	public static final String TAG = "MainActivity";

	private String FACEBOOK_EMAIL = "facebook";
    
	private Button  registTv , loginTv , obtainSerialNo , getAccounts;
	private Button consume;
	private TextView fbUsername;
	private int serialNo = -10000;
	private String ACCOUNT = "jerome";
	private String PASSWORD = "123456";
	private String OAUTH = "android";
	private String GAME = "roar";
	private String REFERENCE = "android_dt";
	private String PACKAGE_REFERENCE = "demo";
	public String SERIAL_NO = "13286";
	/**
	 * 游戏内昵称
	 */
	//roar s1 roar_r
	public String NICK_NAME = "jerome";
	public String SERVER = "s1";
	private String imei;
	
	
	@Override
	public void onResume() {
	    super.onResume();
//	    Session session = Session.getActiveSession();
//	    if (session != null &&
//	           (session.isOpened() || session.isClosed()) ) {
//	    	mainFragment.onSessionStateChange(session, session.getState(), null);
//	    }
	}
	
	@Override
	public void onPause() {
	    super.onPause();
	}

	@Override
	public void onDestroy() {
	    super.onDestroy();
	    userEvents.onRelease();
	}

	private UserEvents userEvents;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
            
        obtainSerialNo = (Button) findViewById(R.id.click_serialno);
        registTv = (Button) findViewById(R.id.click_regist);
        loginTv = (Button) findViewById(R.id.click_login);
        obtainSerialNo = (Button) findViewById(R.id.click_serialno);
        getAccounts = (Button)findViewById(R.id.get_accounts);
        consume = (Button)findViewById(R.id.consume);
        
//		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmjyGuODkYTRGLGgcAIZsvglwJOgb0yHS9Ce2hefWR05lYLoMcdP48YRFBMHvtYRRKNrTNOzI2TEiZfZOmsqe5h/xwHd0iV3sbxhVno78YWchKfTFVJGiaf7DpzNdxbMXU6svLca5Tj8TezJhXHxbeerCfm9mWk1Z28Bj9K++orPLdgLYVgTcZ1xBJLwFWDezDI2Bu3RGm442wG69Cj3gTZYHm83/CAg0G0vy5TZez3G5u1zKhhQJeYJLFT4szOlxWneweHhLiWw2aAOsxUjjn7E9Hc8RHCNv7OTkepBK6w57GM/QO5cOCnJr0p1X4A/zMY2PvNRqRf4eawCCzJeO9wIDAQAB";
		String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmjyGuODkYTRGLGgcAIZsvglwJOgb0yHS9Ce2hefWR05lYLoMcdP48YRFBMHvtYRRKNrTNOzI2TEiZfZOmsqe5h/xwHd0iV3sbxhVno78YWchKfTFVJGiaf7DpzNdxbMXU6svLca5Tj8TezJhXHxbeerCfm9mWk1Z28Bj9K++orPLdgLYVgTcZ1xBJLwFWDezDI2Bu3RGm442wG69Cj3gTZYHm83/CAg0G0vy5TZez3G5u1zKhhQJeYJLFT4szOlxWneweHhLiWw2aAOsxUjjn7E9Hc8RHCNv7OTkepBK6w57GM/QO5cOCnJr0p1X4A/zMY2PvNRqRf4eawCCzJeO9wIDAQAB";

        //roar roar s1 roar_r
//        userEvents = new UserEvents(getApplicationContext() , GAME, REFERENCE, SERVER , PACKAGE_REFERENCE , base64EncodedPublicKey);
//      userEvents = new UserEvents(getApplicationContext() , "sgl2", "sgl2", "s1" , "sgl2_googleplay" , base64EncodedPublicKey);
//        userEvents = new UserEvents(getApplicationContext() , "ANDROID_DT", "ANDROID_DT", "s1" , "demo" , base64EncodedPublicKey);
	      userEvents = new UserEvents(getApplicationContext() , "sgl2", "sgl2","sgl2_googleplay" , base64EncodedPublicKey);

		LogUtil.enableLoggingDebug(true);
        Log.e("", "serialNo : " + serialNo);
        if(serialNo == -10000){
    			TelephonyManager telephonyManager=(TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
    			imei=telephonyManager.getDeviceId(); 		
//    			userEvents.userRequestLoginWithoutAccount(imei,false , mLoginCallBack);
        }

    }
    
    public void onSetServerClick(View view){
    		ToastAlone.showToast(getApplicationContext(), "选择服务", Toast.LENGTH_LONG);
		userEvents.setServer("s1");
    }
    
    public void onLaunchPayClick(View view){
    		//all third google 
    		String store = "google";
    		userEvents.launchPay(this , SERIAL_NO , store);
    }
    
    public void onLaunchServiceClick(View view){
		userEvents.launchServicePage(this , SERIAL_NO , NICK_NAME);
   }    
    
    public void onLaunchRegClick(View view){
    		userEvents.userRequestReg(imei , ACCOUNT , PASSWORD  , mRegistCallBack);
    }  
    
    public void onLaunchLoginClick(View view){
//    	100001376954562
    		
    		userEvents.userRequestLoginWithThirdPart("100006226442792", "facebook", true, mLoginCallBack);
    		
//    		userEvents.userRequestLogin(ACCOUNT, PASSWORD, mLoginCallBack);
    }   
  
    public void onGetSericalNoClick(View view){
    		userEvents.userRequestSerialNo(ACCOUNT, mObtainCallBack);
    }
    
    public void onGetAccountsClick(View view){
		String userId = "886806";	
		userEvents.getAccountAction(userId, mGetAccountsCallBack);
    }
       
    public void onConsumeClick(View view){
		userEvents.consumeAction(ACCOUNT , "consume_item" , "50" , mConsumeCallBack , true);
    }
            
    public void onBillingClick(View view){
    		userEvents.startBilling(this);
	}

	UserEvents.BaseListener  mRegistCallBack = new UserEvents.NutsRegistEventListener() {
		
		@Override
		public void handError(String error) {
			//TODO 处理异常情况
			if(error.equals(UserEvents.TIMEOUTEXCEPTION)){
				//请求超时
			}else if(error.equals(UserEvents.IOEXCEPTION)){
				//IO异常
			}else{
				//返回结果非纯数字
			}
		}
		
		@Override
		public void onRegistComplete(long result) {
			/**
			 * -1	没有访问权限
			 * -10  账号格式错误
			 *-11  查询参数错误
             *-12	链接已经失效
             *-13	账号不存在
             *-14 密码不正确
             *-16 加密验证错误
             *-20 用户名已注册
             *长度>=5的数字表示注册成功，即为账号对应的序列号
			 */
			registTv.setText("onRegistComplete : " + result);
		}

		@Override
		public void bindAccount(long result) {
			//处理绑定结果
		}
	};

	UserEvents.BaseListener mLoginCallBack = new UserEvents.NutsLoginEventListener() {
		
		@Override
		public void handError(String error) {
			//TODO 处理异常情况
			if(error.equals(UserEvents.TIMEOUTEXCEPTION)){
				//请求超时
			}else if(error.equals(UserEvents.IOEXCEPTION)){
				//IO异常
			}else{
				//返回结果非纯数字
			}
		}
		
		@Override
		public void onLoginComplete(long result) {
			/**
			 * -1	没有访问权限
			 *-10  账号格式错误
			 *-11  查询参数错误
			 *-12	链接已经失效
			 *-13	账号不存在
			 *-14 密码不正确
			 *-17 game参数未开通
			 *长度>=5的数字，账号对应的序列号
			 */
			loginTv.setText("onLoginComplete : " + result);
		}
	};
	
	UserEvents.BaseListener mObtainCallBack = new UserEvents.NutsObtainEventListener() {
		
		@Override
		public void handError(String error) {
			//TODO 处理异常情况
			if(error.equals(UserEvents.TIMEOUTEXCEPTION)){
				//请求超时
			}else if(error.equals(UserEvents.IOEXCEPTION)){
				//IO异常
			}else{
				//返回结果非纯数字
			}
		}
		
		@Override
		public void handleSerialNo(long serialNo) {
			/**
			 * -1	没有访问权限
			 *-10  账号格式错误
			 *-11  查询参数错误
			 *-12	链接已经失效
			 *-13	账号不存在
			 *-14 密码不正确
			 *长度>=5的数字，账号对应的序列号
			 */
			obtainSerialNo.setText("obtainSerialNo : " + serialNo);
		}
	};
	
	UserEvents.BaseListener mGetAccountsCallBack = new UserEvents.NutsGetAccountsListener() {
		
		@Override
		public void handError(String error) {
			/**
			 * -4 帐号不存在
			 */
			if(error != null && error.length() > 0){
				getAccounts.setText("getAccounts : " + error);
			}
		}
		
		@Override
		public void getAccounts(BalanceVO accounts) {
			Log.e(TAG, "accounts : " + accounts);
		}
	};
	
	UserEvents.BaseListener mConsumeCallBack = new UserEvents.NutsConsumeListener() {
		
		@Override
		public void handError(String error) {
			
		}
		
		@Override
		public void handleConsumeResult(boolean isSuccess) {
			/**
			 * true 消费成功
			 */
			consume.setText("consumeResult : " + (isSuccess?"true":"false"));
		}
	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(data == null) return;
		switch (requestCode) {
		case NutsWebViewActivity.REQUEST_CODE:
			if(data.hasExtra(NutsWebViewActivity.HANDLE_RESULT_KEY)){
				int payCode = data.getIntExtra(NutsWebViewActivity.HANDLE_RESULT_KEY, NutsWebViewActivity.PAY_ERROR);
				if(payCode == NutsWebViewActivity.PAY_SUCCESS){
					if(data.hasExtra(NutsWebViewActivity.HANDLE_RESULT_MODEL)){
						String model = data.getStringExtra(NutsWebViewActivity.HANDLE_RESULT_MODEL);
						if(model != null)
							try {
								Toast.makeText(getApplicationContext(), new String(Base64.decode(model)), Toast.LENGTH_LONG).show();
							} catch (Base64DecoderException e) {
								e.printStackTrace();
							}	
					}
					Toast.makeText(getApplicationContext(), "SUCCESS", Toast.LENGTH_SHORT).show();
					//TODO
				}else if(payCode == NutsWebViewActivity.PAY_FAILED){
					Toast.makeText(getApplicationContext(), "FAILED", Toast.LENGTH_SHORT).show();
					//TODO
				}else if(payCode == NutsWebViewActivity.PAY_ERROR){
					//TODO
				}else if(payCode == NutsWebViewActivity.GOOGLE_PURCHASE_FINISH){
					//TODO
				}
					
			}
			break;
		}
	}

}

