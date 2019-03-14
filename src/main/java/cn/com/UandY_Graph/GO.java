/**
 * @Title: GO.java
 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年11月28日
 * @version 1.0
 */
package cn.com.UandY_Graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Kevin
 *
 */
public class GO {
	
	//参数
	private  double ST=1;
	private  double e= 2.71828;
	private  int []data;
	

	public GO(int []array,double Tst) {
		// TODO Auto-generated constructor stub
		this.ST=Tst;
		this.data=array;
	}
 	/**
	 * @return the sT
	 */
	public  double getST() {
		return ST;
	}

	/**
	 * @param sT the sT to set
	 */
	public  void setST(double sT) {
		ST = sT;
	}

	
	public void deal_GO() throws NumberFormatException, IOException {
		double root=0;
		double D=0;
		double re_a=0,re_b=0,f=0;
		double xl=0,xr=0,xm=0;
		
		int n=data.length;

		n=n-1;
		for(int i=1;i<=n;i++)
		{
			D=D+data[i];
		}
	    D=D/(data[n]*n);
		if(D>0.5)	System.exit(0);
		else
		{
			xl=(1-2*D)/2;
			xr=1/D;
		}
		
		xm=(xl+xr)/2;
		while (Math.abs(xr-xl)>ST)
		{
			f=(double) ((1-D*xm)*Math.exp(xm)+(D-1)*xm-1);
			if(f>ST)
			{
				xl=xm;
			}
			else if(f<(0-ST))
			{
				xr=xm;
			}
			xm=(xl+xr)/2;
		}
		re_b=xm/data[n];
		root=0-(re_b*data[n]);
		re_a=(double) (n/(1-Math.exp(root)));
		System.out.println("b="+re_b+"	"+"a="+re_a);

	}

	public static void main(String[] args) throws NumberFormatException, IOException {
		DataReader dr = new DataReader();
		GO GO_Model = new GO(dr.deal("Source/SYS.txt"),0.1);
		GO_Model.deal_GO();
	}

}
