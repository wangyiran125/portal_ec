package com.chinalbs.simulator.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import com.chinalbs.beans.Server;
import com.chinalbs.entity.Simulator;
import com.chinalbs.entity.Simulator.DeviceType;

public class MessageUtils {

	private static String obdReportFormat = "%.4f,E,%.4f,N,%.2f,%.2f";
	private static SimpleDateFormat sdf = new SimpleDateFormat("#ddMMyy#HHmmss.SSS##");
	private static Random random = new Random();

	public static String getMessage(Simulator simulator, int msgType) {
		if (simulator.getDeviceType().equals(DeviceType.MT90.value())) {
			return getMT90Message(simulator.getSn(), msgType, simulator.getLongitude(), simulator.getLatitude());
		} else if (simulator.getDeviceType().equals(DeviceType.M2616.value())) {
			return getM2616Message(simulator.getSn(), msgType, simulator.getLongitude(), simulator.getLatitude());
		}
		return null;
	}

	public static String getMT90Message(String imei, int instructionCode, double lng, double lat) {
		DecimalFormat df = new DecimalFormat("0.000000");
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
		sdf.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
		String time = sdf.format(new Date());
		
		StringBuffer sb = new StringBuffer();
		sb.append("," + imei);
		sb.append(",AAA,");
		sb.append(instructionCode);
		String latStr = df.format(lat);
		String lngStr = df.format(lng);
		sb.append("," + latStr + "," + lngStr + ",");
		sb.append(time);
		sb.append(",A,4,27," + String.valueOf(random.nextInt(120)) + ","
				+ String.valueOf(random.nextInt(360)));
		sb.append(",7,63,21207,272285,460|0|109F|CD2B,0000,0000|0000|0000|092A|0000,2,*");
		int tempLen = sb.toString().length() + 4;
		sb.insert(0, "$$A" + String.valueOf(tempLen));
		return validate(sb.toString());
	}

	
	
	public static String getM2616Message(String sn, int type, double lng, double lat) {

		double speed = (random.nextInt(120) + Math.random());
		double head = (random.nextInt(360) + Math.random());
		StringBuffer sb = new StringBuffer();
		sb.append("#" + sn + "##3#0000#");
		String typeStr = null;
		switch (type) {
		case 35:
			typeStr = "AUT";
			break;
		}
		sdf.setTimeZone(TimeZone.getTimeZone("Etc/Greenwich"));
		sb.append(typeStr + "#1#46000011ee9a52,11eed2d6,11eee6a9#");
		double tempLng = Math.floor(lng) * 100 + (lng - Math.floor(lng)) * 60;
		double tempLat = Math.floor(lat) * 100 + (lat - Math.floor(lat)) * 60;
		String tempStr = String.format(obdReportFormat, tempLng, tempLat, speed, head)
				+ sdf.format(Calendar.getInstance().getTime());
		sb.append(tempStr);
		return sb.toString();
	}

	/**
     * 生成消息
     * @param msg
     * @return
     */
    public static String validate(String msg) {
        byte[] added = msg.toString().getBytes();
        int temp = 0;
        for (byte b : added) {
            short ub = (short) (b & 0xFF);
            temp += ub;
        }
        byte check = (byte) temp;
        String hex = Integer.toHexString(check & 0xFF);
        if (hex.length() == 1)
            hex = '0' + hex;

        return msg + hex.toUpperCase() + "\r\n";
    }
    
    private void login(HttpSession session, Server server, String sn) {
      if (session.getAttribute("login-" + server.getDeviceType()) == null
              || !sn.equals(session.getAttribute("login"))) {
          byte[] bs = BytesConvert.decodeHex("676701000B00010" + sn + "00");
          WebConnectFactory.send(session.getId(), server, bs);
          session.setAttribute("login-" + server.getDeviceType(), sn);
          try {
              Thread.sleep(3000);
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
      }
  }
    
}
