/**
 * @Title: JM.java 
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年11月28日
 * @version 1.0
 */  
package cn.com.UandY_Graph;

import java.io.IOException;

/**
 * @author Kevin
 *
 */
public class JM {
	
	
	private  double ex= 0.1;
	private  double ey= 0.1;
	private  int []t;	
	private  double N;
	private  double ooo;
	
	/**
	 * 
	 */
	public JM(int []array,double tex,double tey) {
		this.t=array;
		this.ey=tey;
		this.ex=tex;
	}
	/**
	 * @return the n
	 */
	public  double getN() {
		return N;
	}
	/**
	 * @return the ooo
	 */
	public  double getOoo() {
		return ooo;
	}
	/**
	 * @param n the n to set
	 */
	public  void setN(double n) {
		this.N = n;
	}
	/**
	 * @param ooo the ooo to set
	 */
	public  void setOoo(double ooo) {
		this.ooo = ooo;
	}
	/**
	 * @return the ex
	 */
	public double getEx() {
		return ex;
	}
	/**
	 * @param ex the ex to set
	 */
	public void setEx(double ex) {
		this.ex = ex;
	}
	/**
	 * @return the ey
	 */
	public double getEy() {
		return ey;
	}
	/**
	 * @param ey the ey to set
	 */
	public void setEy(double ey) {
		this.ey = ey;
	}
	/*
	 * 求和函数
	 */
	public  double cac_sum(int n,int t[])
	{
		double sum=0;
		for (int i=1;i<=n;i++)
		{
			sum+=(i-1)*(t[i]-t[i-1]);
		}
		return sum;
	}
	/*
	 * f函数
	 */
	public  double Fx(int n,double right,double p)
	{
		double result=0;

		double be=0,af=0;
		for (int i=1;i<=n;i++)
		{
			be=(double)1/(right-i+1);
			
			result=result+be;
		}
		af=(double)n/(right-p);
		result=result-af;
		return result;
	}
	
	/*
	 * 
	 * 主要处理函数
	 * 
	 */
	public void deal_JM() throws NumberFormatException, IOException {
		double p=0,root=0,q=0;
		double left=0,right=0;

		
		/*读取文件
		String Filename=TFilename;

		BufferedReader reader=new BufferedReader(new FileReader(Filename));
		
		t[0]=0;
		for (int i = 1; i <= n; i++) {
			
			t[i]=Integer.parseInt(reader.readLine().split("\\s+")[1]);
			
		}
		*/
		int n=t.length;
		
		
		System.out.println(t.length);
		
		n=n-1;
		double N=0,re_K=0;
		double sum=cac_sum(n,t);

		p=(double)sum/t[n];
		

		if (p > (n - 1) / 2)
		{
			left = n - 1;
			right = n;
			double temp=Fx(n, right, p);
			while (Fx(n, right, p) > ey)
			{
				left = right;
				right = right + 1;
			}
			if (Fx(n, right, p) >= (-ex) && Fx(n, right, p) <= ey)
			{
				root = right;
				N = root;
				q = (double)n / (N * t[n] - sum);
			}
			else 
			{
				while (  (right - left) >= ex || (right - left) <= (-ex)  )
				{
							root = (double) ((right + left) / 2.0);
							if (Fx(n, root, p) > ey)
							{
								left = root;
								break;
							}
							else 
							{
								if (Fx(n, root, p) > (-ey) && Fx(n, root, p) < ey)
								{
									break;
								}
								else
								{
									right = root;
								}
							}
				}
				//********************
					root = (double) ((right + left) / 2.0);
					N = root;
					q = (double)n / (N * t[n] - sum);
			}
			System.out.println("N:"+N);
			System.out.println("ooo:"+q);
			setN(N);
			setOoo(q);
		}
		else 
		{
			System.out.println("出错了");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		DataReader dr = new DataReader();
		JM JM_Model = new JM(dr.deal("Source/SYS.txt"),0.1,0.1);
		JM_Model.deal_JM();

	}
}
