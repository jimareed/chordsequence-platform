package com.chordsequence.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class TextHttpFile extends TextFile {
	
	public boolean load(String path) {
		
		boolean result = true;

		contents = "";

        CloseableHttpClient httpclient = HttpClients.createDefault();
        try {
            HttpGet httpGet = new HttpGet(path);
            CloseableHttpResponse response1 = httpclient.execute(httpGet);
            try {
                HttpEntity entity1 = response1.getEntity();
                InputStream is = entity1.getContent();
                
    			DataInputStream	in = new DataInputStream(is);
    			BufferedReader 	br = null;
    			
    			br = new BufferedReader(new InputStreamReader(in));				

    			String strLine;
    			while ((strLine = br.readLine()) != null) {
    				contents += strLine + "\r\n";
    				
    				if (buffer != null) {
    					buffer.addLine(strLine);
    				}
    			}
    			in.close();
    			if (buffer != null) {
    				buffer.closeBuffer();
    			}
    			
            }
            finally {
                response1.close();
            }

        } catch (UnsupportedEncodingException e) {
			Log.println(getClass().getName() + ": unsupported encoding exception");
			e.printStackTrace();
			result = false;
		} catch (ClientProtocolException e1) {
			Log.println(getClass().getName() + ": client protocal exception");
			e1.printStackTrace();
			result = false;
		} catch (IOException e1) {
			Log.println(getClass().getName() + ": io exception");
			e1.printStackTrace();
			result = false;
		} finally {
            try {
				httpclient.close();
			} catch (IOException e) {
				Log.println(getClass().getName() + ": warning, io exception closing connection");
				e.printStackTrace();
			}
        }
		
		return result;
	}

}
