package com.ihakula.journey.network;

import com.ihakula.journey.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class LoadingDialog {
	private Dialog pd;
	private Activity mActivity;
	private TextView loadingText;

	public LoadingDialog(Activity context) {
		this(context, null);
		mActivity = context;
	}

	public LoadingDialog(Activity context, String message) {
		mActivity = context;
		pd = new Dialog(context, R.style.dialog);
		pd.setCancelable(false);
		View view = LayoutInflater.from(context).inflate(R.layout.loading, null);
		view.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		loadingText = (TextView) view.findViewById(R.id.loading_text);
		if (message != null) {
			loadingText.setText(message);
		}
		pd.setContentView(view);
	}

	public void setLoadingText(String text) {
		loadingText.setText(text);
	}

	public void show() {
		if (!mActivity.isFinishing() && !pd.isShowing()) {
			pd.show();
		}
	}

	public void close() {
		if (!mActivity.isFinishing()) {
			mActivity.runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (pd.isShowing()) {
						pd.dismiss();
					}
				}
			});
		}
	}

	public boolean isShow() {
		return pd.isShowing();
	}

	public void setOnCancelListener(DialogInterface.OnCancelListener l) {
		pd.setOnCancelListener(l);
	}
}
