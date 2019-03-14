/**
 * @Title: My_SVM.java

 * @Description:
 * @Copyright: Copyright (c) 2018 
 * @Company:nuaa
 * @author xck&kevin
 * @date 2018年12月16日
 * @version 1.0
 */
package cn.com.svm;

import java.io.IOException;

/**
 * @author Kevin
 *
 */
public class My_SVM {
		/**
		* 训练集路径
		*/
		private String trainFilePath = "F:\\train.data";
		/**
		* 测试集路径
		*/
		private String testFilePath =   "F:\\test.data";
		/**
		* 模型名称
		*/
		private String modelFilePath =  "F:\\model";
		/**
		* 测试结果输出
		*/
		private String testResultPath =  "F:\\result";

		private String[] trainParamStringArray = new String[]{
			"-s", "3",
			"-t", "0",
			trainFilePath,
			modelFilePath
		};
		/**
		* 测试参数集合
		*/
		private String[] testParamStringArray = new String[] {
			testFilePath,
			modelFilePath,
			testResultPath
		};
		
		public void train() throws IOException {
			svm_train.main(trainParamStringArray);
		}
		
		public void test() throws IOException {
			
			svm_predict.main(testParamStringArray);
		} 
		
		
		public static void main(String[] args) throws IOException {
			My_SVM model = new My_SVM();
			model.train();
			model.test();
		}	
	}

