package com.example.boxandwificleaned;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import viewPackage.ViewActions;

public class SocketToWifiModule extends AsyncTask<Object,Object,String> {
    String ip,dataSent;
    int port;
    Context context;
    Socket socket;
    int TCP_BUFFER_SIZE= 1024*1024;
    String response = "";
    private ViewActions taskCompleted;
    SocketToWifiModule(String ip, int port,String dataSent, Context context)
    {
        this.ip=ip;
        this.port=port;
        this.dataSent=dataSent;
        this.context=context;
        this.taskCompleted = (ViewActions) context;
    }
    @Override
    protected String doInBackground(Object... objects) {
        if(GeneralObjects.socketToWifiModule.isCancelled())     return "cancel";
        socket = null;
        DataOutputStream out = null;

        try {
            Log.d("aaa","connecting...");
            socket = new Socket(ip, port);
            socket.setSoTimeout(4000);
            socket.setSendBufferSize(TCP_BUFFER_SIZE);

            /// for recieving and must be for sending
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;
            InputStream inputStream = socket.getInputStream();

            /////  for sending
            out = new DataOutputStream(socket.getOutputStream());
            out.writeUTF(dataSent);

            // for recieving and must be for sending
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            }
            Log.d("aaa","connected");


        }
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();

        }
        catch (SocketTimeoutException e){
            e.printStackTrace();
            response = "SocketTimeoutException: " + e.toString();
        }
        catch (IOException e) {
            // TODO Auto-generated catch block

            e.printStackTrace();
            response = "ConnectException";

        }
        finally {
            if (socket != null)
            {

                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;

    }

    @Override
    protected void onPostExecute(String str) {
        taskCompleted.onTaskCompleted(str);
        super.onPostExecute(str);
    }
}
