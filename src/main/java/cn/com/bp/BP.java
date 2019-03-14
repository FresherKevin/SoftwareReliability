/**
 * @Title: BP.java 
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年12月15日
 * @version 1.0
 */
package cn.com.bp;

import java.util.Random;

/**
 * @author Kevin
 *
 */
public class BP {
	
	/**
	 *  隐含层输入
	 */
	private double[] hide1_input;
	/**
	 *  隐含层权值
	 */
    private double[][] hide1_value;
    /**
     *  隐含层的误差
     */
    private double[] hide1_errors;
    /**
     *  输出层输入
     */
    private double[] out_input;
    /**
     *  输出层的权值 
     */
    private double[][] out_value;

    private double[] out_errors;// 输出层的误差 hide1_errors[节点个数]
 
    /*
     *  目标值，target[输出层的节点个数]
     */
    private double[] target;
 
    private double rate;// 学习速率
    
    private double error;//误差
    
    private double error_door;//误差阀值
    
    public int Sam_num;
 
    /**
     * 进行网络数据结构的声明
     * 每一层设置一个偏置量
     * 
     * 
     */
    public BP(int input_node, int hide1_node, int out_node, double rate , double error_door) {
        super();
 
        // 输入层即第一层隐含层的输入
        hide1_input = new double[input_node + 1];    //输入到隐含层的数据
 
        // 第一层隐含层
        hide1_value = new double[hide1_node][input_node + 1];//
        hide1_errors = new double[hide1_node];
 
        // 输出层
        out_input = new double[hide1_node + 1];		//输入到输出层的数据
        out_value = new double[out_node][hide1_node + 1];
        out_errors = new double[out_node];
 
        target = new double[out_node];
 
        // 学习速率
        this.rate = rate;
        //误差设置
        this.error= 0;
        this.error_door=error_door;
        Sam_num=0;
        // 1.初始化网络的权值
        init_weight();
       
        
    }
    /**
	 * @return the error
	 */
	public double getError() {
		return error;
	}
 
    /**
     * 初始化权值
     * 
     * 分别对隐含层和输出层进行初始化，赋予-1~1的随机值
     */
    public void init_weight() {
 
        set_weight(hide1_value);
        set_weight(out_value);
    }
 
    /**
     * 初始化权值
     * 设置-1到1的随机数
     * 
     * @param w
     */
    private void set_weight(double[][] w) {
    	Random random_weight = new Random();
    	
        for (int i = 0, len = w.length; i != len; i++)
            for (int j = 0, len2 = w[i].length; j != len2; j++) {
                w[i][j] = random_weight.nextInt(2)-1;
            }
    }
 
    
    private void sethide1_input(double[] Data) {
        System.arraycopy(Data, 0, hide1_input, 1, Data.length);
        hide1_input[0] = 1.0;
    }
 
    private void setTarget(double[] target) {
        this.target = target;
    }
 
    /**
     * 2.训练数据集
     * 
     * @param TrainData
     *            训练数据
     * @param target
     *            目标
     */
    public void train(double[] TrainData, double[] target) {
        // 2.1导入训练数据集和目标值
        sethide1_input(TrainData);
        setTarget(target);
 
        // 2.2：向前传播得到输出值；
        double[] output = new double[out_value.length + 1];
        forword2end(hide1_input, output);
 
        // 2.3、方向传播：
        backpropagation(output);
 
    }
 
    /**
     * 反向传播过程
     * 
     * @param output
     *            预测结果
     */
    public void backpropagation(double[] output) {
 
        // 2.3.1、获取输出层的误差；
        get_out_error(output, target, out_errors);
        // 2.3.2、获取隐含层的误差；
        get_hide_error(out_errors, out_value, out_input, hide1_errors);
        //// 2.3.3、更新隐含层的权值；
        update_weight(hide1_errors, hide1_value, hide1_input);
        // * 2.3.4、更新输出层的权值；
        update_weight(out_errors, out_value, out_input);
    }
 
    /**
     * 预测
     * 
     * @param data
     *            预测数据
     * @param output
     *            输出值
     */
    public void predict(double[] data, double[] output) {
 
        double[] out_y = new double[out_value.length + 1];
        sethide1_input(data);
        forword2end(hide1_input, out_y);
        System.arraycopy(out_y, 1, output, 0, output.length);
 
    }
 
    
    public void update_weight(double[] err, double[][] w, double[] x) {
 
        double newweight = 0.0;
        for (int i = 0; i < w.length; i++) {
            for (int j = 0; j < w[i].length; j++) {
                newweight = rate * err[i] * x[j];
               // System.out.println(err[i]);
                w[i][j] = w[i][j] + newweight;
            }
 
        }
    }
 
    /**
     * 获取输出层的误差
     * 
     * @param output
     *            预测输出值
     * @param target
     *            目标值
     * @param out_error
     *            输出层的误差
     */
    public void get_out_error(double[] output, double[] target, double[] out_error) {
    	
    	double temp_error = 0;
        for (int i = 0; i < target.length; i++) {
        
        	out_error[i] = (target[i] - output[i + 1]) * output[i + 1] * (1d - output[i + 1]);
         
        	temp_error=temp_error + (target[i] - output[i + 1])*(target[i] - output[i + 1]);
        }
        error=error+temp_error;
        
        error=error/(2d * Sam_num );
        System.out.println(error);
    }
 
    /**
     * 获取隐含层的误差
     * 
     * @param NextError
     *            下一层的误差
     * @param NextValue
     *            下一层的权值
     * @param output 下一层的输入
     * @param error
     *            本层误差数组
     */
    public void get_hide_error(double[] NextError, double[][] NextValue, double[] output, double[] error) {
 
        for (int k = 0; k < error.length; k++) {
            double sum = 0;
            for (int j = 0; j < NextValue.length; j++) {
                sum += NextValue[j][k + 1] * NextError[j];
            }
            error[k] = sum * output[k + 1] * (1d - output[k + 1]);
        }
    }
    
    
    /**
     * 检查是否已经误差够小
     * @return 判断条件
     */
    public boolean checkerror() {
    	
    	System.out.println(getError_door());
    	if (Math.abs(this.getError()) < this.getError_door()) {
			return true;
		}
		return false;
		
	}
    
    /**
	 * @return the error_door
	 */
	public double getError_door() {
		return error_door;
	}
	/**
	 * @param error_door the error_door to set
	 */
	public void setError_door(double error_door) {
		this.error_door = error_door;
	}
	/**
     * 向前传播
     * 
     * @param input
     *            输入值
     * @param output
     *            输出值
     */
    public void forword2end(double[] input, double[] output) {
 
        // 2.2.1、获取隐含层的输出
        get_net_out(input, hide1_value, out_input);
        // 2.2.2、获取输出层的输出
        get_net_out(out_input, out_value, output);
 
    }
 
    /**
     * 获取各节点的输出
     * 
     * @param input
     *            输入矩阵
     * @param value
     *            权值
     * @return 输出值
     */
    private double get_node_put(double[] input, double[] value) {
        double z = 0d;
 
        for (int i = 0; i < input.length; i++) {
            z += input[i] * value[i];
        }
        // 2.激励函数
        return 1d / (1d + Math.exp(-z));
    }
 
    private void get_net_out(double[] input, double[][] value, double[] net_out) {
 
        net_out[0] = 1d;
        for (int i = 0; i < value.length; i++) {
            net_out[i + 1] = get_node_put(input, value[i]);
        }
 
    }
}
