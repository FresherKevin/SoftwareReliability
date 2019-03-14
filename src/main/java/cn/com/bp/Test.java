/**
 * @Title: Test.java
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年12月15日
 * @version 1.0
 */
package cn.com.bp;

import java.io.IOException; 
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

/**
 * @author Kevin
 * 判断一个数的区间所属（1000以内）
 */
public class Test {
 
	/**
	 * 开始训练
	 * @author Kevin
	 */
	public BP train(BP bp , List<Integer> list,int MAX) {
        //训练次数5000
        //修改为最大次数
        int times=1;
        int place;
        int value;
        Random k = new Random();
		while(true)
		{		
			//随机获取样本
			place=k.nextInt(list.size());
			value=list.get(place);

        	//获取实际real结果
            double[] real = new double[4];
            if (value >= 0 && value < 200)
            {
                real[0] = 1;    
            }
            else if (value >= 250 && value < 500)
            {                 
            	real[1] = 1;
            }
            else if (value >= 500 && value < 750) {
            	real[2] = 1;
			}
            else
                real[3] = 1;
            //一个样本的数据
            double[] binary = new double[32];
            int index = 31;
            do {
                binary[index--] = (value & 1);
                value >>>= 1;
            } while (value != 0);
         
            bp.Sam_num++;
            bp.train(binary, real);
            
			System.out.println(bp.checkerror());
			/**
			 * 
			 * 当误差达到预设精度或学习次数大于设定的最大次数，则结束算法。停止训练
			 */
			if (times > MAX || bp.checkerror()==true) {
				break;
			}
            times++;
            
		}
		return bp;
		
	}
	/**
	 * 生成1000以内的随机数，作为样本数据
	 */
	public List<Integer> getdata() {
        Random random = new Random();
       
		List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < 10000; i++) {
            int value = random.nextInt(1000);
            list.add(value);
        }
        //打乱集合中的顺序
        java.util.Collections.shuffle(list);
        System.out.println("size:"+list.size());
		return list;
	
	}
	
	/**
	 * @author Kevin
	 * 测试训练结果
	 * @throws IOException 
	 * 
	 */
	public void Test_result(BP bp) throws IOException {
        
        while (true) {
            String input;
            input=JOptionPane.showInputDialog("测试，输入一个1000以内的数");
            Integer value = Integer.parseInt(input.trim());
            int rawVal = value;
            double[] binary = new double[32];
            int index = 31;
            do {
                binary[index--] = (value & 1);
                value >>>= 1;
            } while (value != 0);
 
            double[] result =new double[4];
            bp.predict(binary,result);
            
            for (int i = 0; i < result.length; i++) {
				System.out.println(result[i]);
			}
            double max = -Integer.MIN_VALUE;
            int i_place = -1;
 
            for (int i = 0; i != result.length; i++) {
                if (result[i] > max) {
                    max = result[i];
                    i_place = i;
                }
            }
            System.out.println(max);
            switch (i_place) {
            case 0:
                JOptionPane.showMessageDialog(null, rawVal+"属于[0~249]");
                break;
            case 1:
            	JOptionPane.showMessageDialog(null, rawVal+"属于[250~499]");
                break;
            case 2:
            	JOptionPane.showMessageDialog(null, rawVal+"属于[500~749]");
                break;
            case 3:
            	JOptionPane.showMessageDialog(null, rawVal+"属于[750~999]");
                break;
            }
        }
	}
    /**
     *
     * @throws IOException
     * 
     */
    public static void main(String[] args) throws IOException {
        
    
        BP bp= new BP(32, 10, 4, 0.05,0.000001);
        Test BPtest = new Test();
        
        List<Integer> list = BPtest.getdata();
       
        bp=BPtest.train(bp, list, 5000);
        
        BPtest.Test_result(bp);

    }
}
