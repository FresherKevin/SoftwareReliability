/**
 * @Title: Draw.java 
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年11月29日
 * @version 1.0
 */
package cn.com.UandY_Graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame; 

/**
 * @author Kevin
 *绘制U，Y图以及K-S距离
 */
public class Draw {
	public static class DrawUtil extends ApplicationFrame{
		   private XYSeriesCollection dataset;

		    public DrawUtil(String title) {
		        super(title);
		    }

		    public void addDataset(XYSeries series){
		        if(dataset == null){
		            dataset = new XYSeriesCollection();
		        }
		        dataset.addSeries(series);
		    }

		    public void drawGraph(){

		        JFreeChart chart = ChartFactory.createXYLineChart("",
		                "", "", dataset,
		                PlotOrientation.VERTICAL, true, true, false
		        );
		        ChartPanel panel = new ChartPanel(chart);
		        panel.setPreferredSize(new Dimension(1400, 1200));
		        XYPlot plot = chart.getXYPlot();
		        XYLineAndShapeRenderer XY_renderer = new XYLineAndShapeRenderer();
		        /*
		         * 设置图表参数
		         */
		        XY_renderer.setSeriesPaint(0, Color.GREEN);
		        XY_renderer.setSeriesStroke( 0, new BasicStroke(2.0f));
		        XY_renderer.setSeriesPaint(1, Color.orange);
		        XY_renderer.setSeriesStroke( 1, new BasicStroke(2.0f));
		        XY_renderer.setSeriesPaint(2, Color.RED);
		        XY_renderer.setSeriesStroke( 2, new BasicStroke(2.0f));
		        XY_renderer.setSeriesPaint(3, Color.BLACK);
		        XY_renderer.setSeriesStroke( 3, new BasicStroke(2.0f));
		        XY_renderer.setSeriesPaint(4, Color.BLUE);
		        XY_renderer.setSeriesStroke( 4, new BasicStroke(2.0f));
		        plot.setRenderer(XY_renderer);
		        setContentPane(panel);
		    }

	}
	public static void main(String[] args) throws IOException {
		DrawUtil drawUtil = new DrawUtil("");
		//U图
	    UGraph uGraph = new UGraph();
	    XYSeries uSeries = uGraph.createUGraph();
	    
	    
	    drawUtil.addDataset(uSeries);
	    //Y图
	    YGraph yGraph = new YGraph();
	    XYSeries ySeries = yGraph.createYGraph();
	    drawUtil.addDataset(ySeries);
	
	    //一次函数图像
	    XYSeries xySeries = new XYSeries("Y=X");
	    for(int i = 0;i<uSeries.getItemCount();i++){
	        xySeries.add(uSeries.getX(i),uSeries.getX(i));
	    }
	    drawUtil.addDataset(xySeries);
	
	    //U图KS距离
	    XYSeries uGraphKSDis = new XYSeries("UGraphKSDis");
	    double maxDis = 0;
	    double maxXData = 0;
	    double maxYData =0;
	    for(int i = 0;i<uSeries.getItemCount();i++){
	        if(Math.abs(uSeries.getX(i).doubleValue() - uSeries.getY(i).doubleValue())> maxDis){
	        	
	            maxDis = Math.abs(uSeries.getX(i).doubleValue() - uSeries.getY(i).doubleValue());
	            maxXData = uSeries.getX(i).doubleValue();
	            maxYData = uSeries.getY(i).doubleValue();
	            
	        }
	    }
	    uGraphKSDis.add(maxXData,maxXData);
	    uGraphKSDis.add(maxXData,maxYData);
	    drawUtil.addDataset(uGraphKSDis);
	    
	    // Y图KS距离
	    maxDis = 0;
	    XYSeries yGraphKSDis = new XYSeries("YGraphKSDis");
	    for(int i = 0;i<ySeries.getItemCount();i++){
	        if(Math.abs(ySeries.getX(i).doubleValue() - ySeries.getY(i).doubleValue())> maxDis){
	        	
	            maxDis = Math.abs(ySeries.getX(i).doubleValue() - ySeries.getY(i).doubleValue());
	            maxXData = ySeries.getX(i).doubleValue();
	            maxYData = ySeries.getY(i).doubleValue();
	        }
	    }
	    yGraphKSDis.add(maxXData,maxXData);
	    yGraphKSDis.add(maxXData,maxYData);
	    drawUtil.addDataset(yGraphKSDis);
	
	    //显示
	    drawUtil.drawGraph();
	    drawUtil.pack();
	    drawUtil.setVisible(true);

	}

}
