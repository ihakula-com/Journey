package com.ihakula.journey.network;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.AsyncTask;

public abstract class NutsPlayAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	private LoadingDialog ld;
	protected Activity taskContext;
	private boolean isShow = true;
	
	public boolean isShow() {
		return isShow;
	}

	public void setShow(boolean isShow) {
		this.isShow = isShow;
	}

	public NutsPlayAsyncTask(Activity activity, final DialogInterface.OnCancelListener l, final boolean interruptTask,
			final boolean interruptIfRunning,boolean isShow, String message) {
		super();
		taskContext = activity;
		this.isShow = isShow;
		if(isShow){
			ld = new LoadingDialog(activity, message);
			ld.setOnCancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					if (interruptTask) {
						NutsPlayAsyncTask.this.cancel(interruptIfRunning);
					}
					if (l != null) {
						l.onCancel(dialog);
					}
				}
			});
		}
	}

	@Override
	protected Result doInBackground(Params... params) {
		return null;
	}

	@Override
	protected void onPostExecute(Result result) {
		super.onPostExecute(result);
		if(null != taskContext && !taskContext.isFinishing() && ld!=null){
			ld.close();
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		if(null != taskContext  && !taskContext.isFinishing() && ld!=null && isShow){
			ld.show();
		}
	}

}
