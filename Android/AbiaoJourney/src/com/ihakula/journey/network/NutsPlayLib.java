package com.ihakula.journey.network;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

import org.apache.http.HttpException;
import org.json.JSONException;

import android.content.Context;

import com.ihakula.journey.utils.LogUtil;

public class NutsPlayLib {
	private static NutsPlayLib nutsLib;
	private NutsPlayRequest nutsRequest;
	private NutsPlayParse nutsParse;
	protected Context mContext;

	private NutsPlayLib(Context context) {
		nutsRequest = new NutsPlayRequest(context);
		nutsParse = new NutsPlayParse(context);
		mContext = context;
	}

	public synchronized static NutsPlayLib getInstance(Context context) {
		if (nutsLib == null) {
			nutsLib = new NutsPlayLib(context);
		}
		return nutsLib;
	}
	
}
