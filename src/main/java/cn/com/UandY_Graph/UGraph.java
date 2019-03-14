/**
 * @Title: UGraph.java 
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年11月28日
 * @version 1.0
 */
package cn.com.UandY_Graph;

import java.io.IOException;
import java.util.TreeSet;

import org.jfree.data.xy.XYSeries;

/**
 * @author Kevin
 *
 */
public class UGraph {
	   public XYSeries createUGraph() throws IOException {
		   /*
		    * 读取数据
		    */
	        String url = "Source/SYS.txt";
	        DataReader dr = new DataReader();
	        int[] data = dr.deal(url);
	        
	        int dataLen = data.length;
	        
	      //  System.out.println(dataLen);
	        int trainDataLen = (int) (dataLen * 0.7);
	        int[] trainData = new int[trainDataLen];
	        int[] testData = new int[dataLen - trainDataLen];
	        for (int i = 0; i < trainDataLen; i++) {
	            trainData[i] = data[i];
	            if (i + trainDataLen < dataLen) {
	                testData[i] = data[i + trainDataLen];
	            }
	        }
	        System.out.println("u图");
	        //训练得到ooo N;
	        JM JM_Model = new JM(trainData,0.1,0.1);
	        JM_Model.deal_JM();
	        double N = JM_Model.getN();
	        double ooo = JM_Model.getOoo();


	        int testDataLen = testData.length;
	        TreeSet<Double> xData = new TreeSet<Double>();

	        for (int i = 0; i < testDataLen; i++) {
	        	System.out.println(1 - Math.exp(-ooo * (N - i + 1) * testData[i]));
	        	
	            xData.add(1 - Math.exp(-ooo * (N - trainDataLen + 1) * testData[i]));
	        }
	        /*
	        System.out.println("xData.size:"+xData.size());
	        System.out.println("testdata.size:"+testDataLen);
	        */
	        TreeSet<Double> yData = new TreeSet<Double>();
	        

	        for (int i = 0; i < xData.size(); i++) {
	            yData.add(i / (double) (xData.size() + 1));
	        }
	        XYSeries uSeries = new XYSeries("U-graph");
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
