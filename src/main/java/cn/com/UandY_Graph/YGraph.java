/**
 * @Title: YGraph.java
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年11月29日
 * @version 1.0
 */
package cn.com.UandY_Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.jfree.data.xy.XYSeries;

/**
 * @author Kevin
 *
 */
public class YGraph {
    public XYSeries createYGraph() throws IOException {
    	System.out.println("Y图");
        String url = "Source/SYS.txt";
        DataReader dr = new DataReader();
        int[] data = dr.deal(url);
        int dataLen = data.length;
        int trainDataLen = (int) (dataLen * 0.7);
        int[] trainData = new int[trainDataLen];
        int[] testData = new int[dataLen - trainDataLen];
        for (int i = 0; i < trainDataLen; i++) {
            trainData[i] = data[i];
            if (i + trainDataLen < dataLen) {
                testData[i] = data[i + trainDataLen];
            }
        }
        System.out.println("traindata："+trainDataLen);
        //训练得到ooo N;
        JM JM_Model = new JM(trainData,0.1,0.1);
        JM_Model.deal_JM();
        double N = JM_Model.getN();
        double ooo = JM_Model.getOoo();


        int testDataLen = testData.length;
        System.out.println("testdata："+testDataLen);
//        System.out.println(testDataLen);
        TreeSet<Double> xData = new TreeSet<Double>();

        for (int i = 0; i < testDataLen; i++) {
        	
            xData.add(1 - Math.exp(-ooo * (N - trainDataLen + 1) * testData[i]));
        }
        System.out.println("xData.size:"+xData.size());
        System.out.println("testdata.size:"+testDataLen);
        
        List<Double> xDataList = new ArrayList<Double>();

        while (xData.size() != 0) {
            xDataList.add(xData.pollFirst());
        }

        testDataLen = xDataList.size();
        System.out.println("testdata："+testDataLen);
        
        for (int i = 0; i < testDataLen - 1; i++) {
//            System.out.println(xDataList.get(i));
            xData.add(-Math.log(1 - xDataList.get(i)));
        }
//        System.out.println(xDataList.size());
        xDataList.clear();

        while (xData.size() != 0) {
            xDataList.add(xData.pollFirst());
        }
        testDataLen = xDataList.size();
//        System.out.println(xDataList.size());
//        System.out.println(testDataLen);
        double sumBase = 0;
        for (int i = 0; i < testDataLen; i++) {
            sumBase += xDataList.get(i);
        }
        System.out.println("size:"+xData.size());
        for (int i = 0; i < testDataLen; i++) {
            double sumTop = 0;
            for (int j = 0; j <= i; j++) {
                sumTop += xDataList.get(j);
            }
            xData.add(sumTop / sumBase);
        }

        TreeSet<Double> yData = new TreeSet<Double>();
        for (int i = 0; i < xData.size(); i++) {
            yData.add(i / (double) (xData.size() + 1));
        }

        XYSeries uSeries = new XYSeries("Y-graph");
        double x, y, y2;
        while (xData.size() != 1) {
            x = xData.pollFirst();
            y = yData.pollFirst();
            uSeries.add(x, y);
            
            y2 = yData.pollFirst();
            uSeries.add(x, y2);
            yData.add(y2);
            
        }
        x = xData.pollFirst();
        y = yData.pollFirst();
        uSeries.add(x, y);
        return uSeries;
    }

}
